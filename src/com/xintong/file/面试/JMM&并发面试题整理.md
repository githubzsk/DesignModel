# JMM/并发面试题

##### 1.  什么是线程？线程和进程的区别

线程是操作系统能够进行运算调度的最小单位，它被包含在进程之中，是进程中的实际运作单位,  一个进程可以看做一个应用程序，而进程中包含着一个或者多个线程

##### 2. Java中如何实现线程

1. 继承Thread类：实现简单可用性不强，继承了Thread就不能继承其他类来进行功能拓展

2. 实现Runnable接口：实现较继承Thread类稍复杂，但是java可以多实现，可拓展。

3. 实现Callable接口：实现较为实现Runnable稍复杂，可拓展，如果说需要得到线程处理后的返回值，优先考虑Callable接口

4. 使用线程池

   1和2本质相同，实现Runnable最后传入Thread调用start方法


##### 3. 如何停止一个线程

终止一个线程有很多种方法，大体的思想可以分为下面几个方向

1. **stop()**（不推荐）：线程的stop方法可以暴力终止一个线程，但是可能会出现数据不一致，资源未释放等问题，在设计上存在缺陷，现在已经被弃用
2. **boolean标记法**（推荐）：在线程标记一个boolean型变量比如叫flag=true，作为while循环的条件执行业务逻辑，在需要跳出的逻辑处修改flag为false，则会跳出循环执行完run方法从而结束线程运行。当然如果业务需要在其他线程中修改flag的值的话，我们需要将flag变量用volatile修饰而保证可见性。    
3. **抛异常法**（推荐）：需要用到interrupt()方法将需要终止的线程打上中断状态，使用interrupted()或者isInterrupted()方法去检查中断状态然后抛出InterruptedException,然后再捕获这个异常去做业务处理，从而达到终止线程的目的，注意区别inertrupted和isInterrupted的区别    
4. **return法**（推荐）：需要用到interrupt()方法将需要终止的线程打上中断状态，使用interrupted()或者isInterrupted()方法去检查中断状态然后直接return也可以达到安全终止一个线程 具体要使用那个方法还得看具体的业务需要


##### 4.  volatile关键字作用及其原理

1. **作用**

   volitale可以保证可见性和有序性，在特定的条件下可以实现原子性

   - _原子性_：原子性指的是一个操作不可被中断，volatile实现原子性只是特定的情况下，在32位的jdk中，long跟double都没有实现原子性，想要实现原子性就得用volatile修饰，其余情况下，volatile并不能实现原子性
   - _可见性_：是指A线程修改了变量的值，B线程立马就能看到
   - _禁止代码重排序_:我们知道在java代码运行的时候，JIT（Just In Time）即时编译器会起作用，它会优化代码优先执行轻耗代码从而进行代码重排序，它的优化排序并不会影响单线程下的代码逻辑，如果使用了volatile，那么volatile就像一道屏障一样，把volatile之前和之后的代码隔开不会被重排序

2. **可见性原理**

   volatile修饰的变量在转化为汇编指令的时候会在前面加一个LOCK指令，当CUP执行这个指令的时候会立即做两件事情

   1. 把当前内核高速缓存中的数据立即写回主存并锁定缓存区域
   2. 基于MESI协议让其他内核中缓存的数据立即失效

   操作当前内核里面高速缓存中的数据写会主存简单，但是操作其他内核缓存的数据失效就没那么容易，所以这里要借助MESI协议，MESI协议规定了缓存变量的4中状态，M被修改状态 E独占状态 S共享状态 I无效状态，多线程操作被volatile修饰的变量属于共享数据，共享状态意味着时刻被监控，一旦发现共享数据发生改变，立马把共享数据的状态改为I 也就是无效状态，当别的线程去读取这个volatile修饰的数据时候发现他是无效状态，那么它会去主存中重新读取，读取到的则为最新的数据
   
3. **有序性原理**

   volatile会产生一个叫做StoreLoad的内存屏障指令，排在这个指令之前的代码不能跑到这个指令之后去执行，排在这个指令之后的代码不能跑到指令之前，就保证了有序性

##### 5. Synchroized锁方法、代码块的区别

- 修饰普通方法：

  ```java
  public synchronized  void method2(){
   //...
  }
  //是对当前对象加锁，这种写法相当于
  synchronized(this){
      //....
  }
  ```

- 修饰静态方法

  ```java
  public static synchronized void method1(){
      //...
  }
  //是对该类对象加锁，这种写法相当于
  synchronized(ClassName.class){
      //....
  }
  ```

先说**作用**、再说**使用**、再说**原理**

**作用**：保证并发的 原子性 、可见性 、以及禁止指令重排序

**使用**：synchronize不管是对于方法还是同步代码块，它锁的都是对象，可能是实例对象或者是类对象

用来修饰普通方法锁定的是当前对象，相当于代码块括号中写（this） 

用来修饰静态方法锁定的是类对象，相当于代码块括号中写（ClassName.class）

**原理**

使用javap -c命令对class文件反汇编后去看的时候，会发现同步代码块里的代码会处于monitorenter和monitorexit指令之间，每个对象都有一个Monitor对象与之关联，线程执行到monitorenter的时候会去尝试获得对象对于的Monitor，说的简单一点就是尝试获得对象的锁，当执行到monitorexit的时候就去释放对象对应的monitor也就是释放锁，这就是synchronize同步代码块的实现原理。

那再稍微深入一下，说一下这个Monitor对象的实现原理，事实上这个Monitor对象的原型是一个叫做ObjectMonitor的类，我们一般看不到因为这个得编译JDK源码之后才能看得到，这个类由C++实现，这里面有几个关键的属性 owner 、count、EntryList、WaitSet、 当线程执行到monitorenter，该monitor对象的count+1  owner设置为当前线程，这个线程执行到了monitorexit，monitor对象的count-1 owner设为null，所有尝试进入同步代码块的线程都会进入EntryList中，当一个线程调用了wait()方法的线程会进入WaitSet中，owner改为null，count-1，也就意味着该线程释放了当前monitor，这个也能顺便解释了调用wait()方法会释放锁的问题

而这个Monitor对象也就是这个由C++实现的ObjectMonitor的底层是调用操作系统的mutex lock这个互斥锁来实现，一旦涉及到mutex lock必然会引起用户态和内核态之间的切换，这也就解释了为什么把synchronized叫做重量级锁的原因

以上是对于synchronize实现同步代码块原理，那么我在说一下synchronize修饰方法的实现原理，同样反编译class文件会发下，被synchronize修饰方法多了一个ACC_SYNCHRONIZED标识符，当线程执行方法的时候检查方法有没有被标记ACC_SYNCHRONIZED标识符，如果没有标记那么正常执行，如果标记了，那么该线程就去尝试获取monitor对象，也就是获取对象锁，事实上在本质上和使用monitorenter/monitorexit没有区别

##### 6. synchronized为什么叫重量级锁

在jdk1.6之前synchronized成为重量级锁，因为synchronize实现原理是**参考上条**，操作系统层面使用的是 Mutex lock这种互斥锁，使用互斥锁引起大量线程上下文切换，必然会需要用户态到内核态之间相互转换，消耗大量CPU，所以会影响到性能

1.6之后对synchronize进行了优化，有了锁升级（膨胀）概念，synchronize就没有以前那么重了

##### 7. Synchroized锁升级过程

jdk1.6为synchronize引入了无锁，偏向锁，轻量级锁、重量级锁

偏向锁01：适用于一个线程反复进入同一同步块

轻量级锁00：轻量级锁适用于线程交替执行的场景

重量级锁11：适用于高并发场景

**无锁**：初始化没有现成去执行同步块的时候，处于无锁状态

**无锁-偏向锁**：当第一个线程执行同步块，他会先查看对象的锁状态标志，如果状态标志是01，则说明是无所或者偏向锁状态（当然第一个线程肯定是无锁），然后他再去检查偏向线程ID，（当然第一个线程初次执行肯定也没有设置偏向线程ID），然后他会利用cas将自己线程ID设置到对象头/MarkWorld中的偏向线程id，此时进入偏向锁状态

**偏向锁-轻量级锁**：同样第二个线程来的时候同样会检查锁状态标志以及偏向线程ID，如果发现偏向线程ID是其他线程，然后去看持有锁的这个线程是否处于活动状态，如果不处于活动状态，那么**偏向锁撤销到无锁**，由线程2获取锁重新进入偏向锁，如果处于活动状态，那么便产生锁竞争，进入轻量级锁。

**轻量级锁-重量级锁**：轻量级锁的情况下，当线程来执行代码时，会把对象头中的MarkWorld复制到栈中叫做Displaced Mark Word，然后用CAS将对象头中的MarkWorld替换成指针指向栈中MarkWorld的复制品，CAS操作成功则进入轻量级锁，没操作成功则进入自旋获取锁，

轻量级锁进入重量级锁是在轻量级锁解锁的过程中，会使用原子的CAS操作将Displaced Mark Word替换回到对象头，如果解锁成功则说明没有竞争，如果解锁失败则说明有其他线程参与了竞争，进入重量级锁

**重量级锁**：进入重量级锁会使用monitorenter/monitorexit加锁解锁，~是由C++的ObjectMonitor实现，而C++的ObjectMonitor底层由操作系统的Mutext lock实现，一旦涉及到Mutext lock肯定需要用户态和内核态不断切换，耗能比较大，所以是重量级锁

无锁 ---> 偏向锁  --->轻量级锁

应用程序初始化的时候，所有的对象处于无锁状态，当第一个线程访问同步代码块的时候，回去查看对象的锁状态标识，锁状态标识如果是01的话，那么说明他是无锁或者是偏向锁状态，那么他再去检查偏向线程ID，若果没有的话他话他会使用CAS修改偏向线程id修改为自己的，这个时候如果有的话说明已经是偏向锁状态了，那么他也用CAS操作设置偏向线程ID，一旦失败便会撤销偏向锁，他会等到安全点的时候再去检查第一个线程时候还是不是出于活动状态，如果不的那么恢复为无锁状态，这叫撤销偏向锁，然后再重新标记线程2自己的线程ID进入偏向锁状态，如果线程A出于活动状态，那么这个时候，偏向锁升级为轻量级锁讲锁状态为标志改为00

轻量级锁 ---> 重量级锁

升级为轻量级锁之后，线程在执行同步代码块之前，会将对象的Mark World 复制一份复制到栈帧中去，这个称谓Displaced Mark World，然后再用CAS讲对象的Mark World替换为一个指针指向Displaced Mark World，如果成功这个线程就获得了轻量级锁，如果失败了，虚拟机会检查对象的Mark World的那个指针是否指向线程栈中的Displaced Mark World，如果指向说明当前线程已经获得了轻量级锁，那么就可以进入同步代码块执行，如果不是的话说明存在锁竞争，轻量级锁要膨胀为重量级锁，锁状态为标志改为10

重量级锁

重量级锁实现原理就Monitor对象的monitorenter和monitorexit指令进行加锁解锁，而Monitor是基于C++的ObjectMonitor这个类实现，而ObjectMonitor是基于操作系统的Mutex lock实现，Mutext lock需要用户态和内核态的不断切换，开销比较大，所以称为重量级锁

##### 8. 介绍CAS

CAS全称叫做Compare And Swap 直译过来叫做，比较并交换他是一种乐观锁的思想，juc下的atomic系列几乎全都是用CAS实现的

CAS需要三个参数，一个是缓存值或者叫他原值，一个是当前主存中实际的值，另外一个是期望更新到的值。他在把主存中值更新成期望的值的时候，会先去判断缓存值和当前主存中的值是否相等，如果不相等说明主存中的值已经被别的线程所更新，则放弃这次更新，并且该线程重新load主存中的值到缓存去准备做下一次比较，直到原值与主存中的值相等，在做更新操作，CAS能做到让比较交换这两个步骤具有原子性实际上是基于硬件使用了一个汇编指令cmpchxg指令去实现的

CAS的问题

1. ABA问题：  A线程读取内存1    B线程读取内存1修改成2 C线程读取内存2修改成1 A线程比较1=1但是实际已经被修改过了

   解决： 在变量前＋版本号，每次修改版本+1，每次比较不但比较缓存值和内存值，还去比较版本号

2. 只能保证一个共享变量的原子操作

3. 循环CPU开销

##### 9.  CAS应用（乐观锁）

version+1

##### 10.自旋锁

指的是一个线程在获取锁的时候，如果锁已经被其他线程获取到，那么该线程讲循环等待，然后不断的去判断能不能获取锁成功，知道获取成功才会退出循环

优点:避免了线程阻塞和唤醒需要系统去切换CPU状态带来的时间开销

应用：用于同步代码块逻辑简单，执行时间很短

自适应自旋锁：根据上一次自旋时间和结果调整下一次自旋的时间，比如上一次自旋10次操作成功，那么我下一次可能在10上加几次比如15  20 等，自动调整

JDK1.7之后，取消了自旋锁的JVM参数，自旋锁会保持打开并且自选次数也有jvm自动调整

##### 11.线程状态

很多资料说线程是5种状态，New新建  Runnable可运行 Running运行 Block阻塞 Dead死亡状态，我觉得这种分法太过于粗糙，无论是你用jstack打印堆栈信息还是去看线程的源码，实际上线程的状态并不是这样分的

New:在线程new出来，还没调用start方法之前都是new状态

Runnable：在JDK中认为 可能是可运行，也可能是正在运行

Bolck：一般是由于没竞争到锁而被挂起便会进入Block状态

Waiting：调用了无参的wait()、或者调用了join之类的、事实上jion内部也是用wait实现的，就会进入状态

Timed_Waiting：sleep(long time)、 wait(long time)、 join(long time)

Terminated：死亡状态 /'tɝmə,net/

##### 12 sleep、wait区别

sleep是Thread的一个native本地方法，它是使用C语言基于硬件层面上使用的一个定时器去实现的整个过程不会释放掉线程持有的对象锁

wait是属于Object，基于synchronized底层原理去实现，synchronized是由Monitor对象实现，Monitor是由C++的ObjectMonitor实现，ObjectMonitor有owner、count、EntryList、WaitSet，当对象调用了wait方法，owner设置为null count-1 线程进入WaitSet，也就意味着该线程失去了锁对象，即释放了对象锁的原理。

##### 13 为什么wait在Object中

因为wait基于synchronized实现，而synchronized作用的范围是所有对象，所有wait放在Object中比较合适

##### 14 Runnable和Callable区别

Runnable没有返回值，不能抛出来异常只能在内部处理异常

Callable有返回值，call方法可以抛出异常

##### 15. 线程池原理 核心参数

线程池的核心类ThreadPoolExecutor，先从构造说起，这个类对外提供了4个构造方法，事实上准确的来说这个类只有一个构造，因为其他三个构造都是调用它的最后一个构造

```java
public ThreadPoolExecutor(int corePoolSize,
                          int maximumPoolSize,
                          long keepAliveTime,
                          TimeUnit unit,
                          BlockingQueue<Runnable> workQueue,
                          ThreadFactory threadFactory,
                          RejectedExecutionHandler handler) {
    if (corePoolSize < 0 ||
        maximumPoolSize <= 0 ||
        maximumPoolSize < corePoolSize ||
        keepAliveTime < 0)
        throw new IllegalArgumentException();
    if (workQueue == null || threadFactory == null || handler == null)
        throw new NullPointerException();
    this.acc = System.getSecurityManager() == null ?
            null :
            AccessController.getContext();
    this.corePoolSize = corePoolSize;
    this.maximumPoolSize = maximumPoolSize;
    this.workQueue = workQueue;
    this.keepAliveTime = unit.toNanos(keepAliveTime);
    this.threadFactory = threadFactory;
    this.handler = handler;
}
```

_corePoolSize_: 核心线程数，默认情况下创建了线程池之后，线程池中并没有线程，而是有任务来的时候去创建线程，哪怕核心线程空闲，也要去创建核心线程，当池中的线程数到达corePoolSize的时候，再接受任务的时候不是创建新的线程，而是将线程放到工作队列中去；

_maximumPoolSize_:最大线程数，表示线程池中最多能创建多少个线程

_keepAliveTime_:空闲线程存活时间，只生效于除了核心线程以外的线程，如果说这些线程空闲时间超过了这个存活时间，那么就将这些线程销毁

_unit_:时间单位，就是针对于空闲线程存活时间的单位

_workQueue_：这是用于存放任务的一个阻塞队列，它可以使用ArrayBlockingQueue，LinkedBlockingQueue，SynchronousBlockingQueue，PriorityBlockingQueue

_threadFactory_:线程工厂，用来创建线程

handler：拒绝策略

执行逻辑（源码），execute执行逻辑非常清晰

```java
			public void execute(Runnable command) {
    //1. 首先任务为空的话，直接抛NPE
    if (command == null)
        throw new NullPointerException();
    int c = ctl.get();
    //2. 然后判断去判断当先线程数是否小于核心，如果小于的话直接调用一个addWork方法来创建线程执行任务
    if (workerCountOf(c) < corePoolSize) {
        if (addWorker(command, true))
            return;
        c = ctl.get();
    }
    //3. 如果核心线程满了 便会 workQueue.offer把任务添加到阻塞队列里面去
    if (isRunning(c) && workQueue.offer(command)) {
        int recheck = ctl.get();
        if (! isRunning(recheck) && remove(command))
            reject(command);
        else if (workerCountOf(recheck) == 0)
            addWorker(null, false);
    }
    //4. 如果放到阻塞队列失败，说明阻塞队列满了，便重开新线程执行任务，如果执行任务失败（前面的非 ！）
    else if (!addWorker(command, false))
    //5. 执行拒绝策略
        reject(command);
}
```

执行逻辑（流程图）

![img](https://upload-images.jianshu.io/upload_images/2615789-2d3eb90c8e2cf51f.jpg?imageMogr2/auto-orient/strip|imageView2/2/w/1200/format/webp)

##### 16. 线程池线程复用原理

首先我说一个基本概念，线程在调用了start方法之后进入Runable状态，一旦获取到cup时间片，便开始执行run方法，一旦run方法的业务执行完毕，线程死亡。

按照这个理论，线程想要达到复用必须解决两个问题

1. 不要让他执行完run方法，因为一旦执行完线程就死了
2. 执行不同任务的run方法，也就是处理不同任务

如果解决了这个问题，那么线程复用应该就可以实现，接下来我们看一下JDK中的实现

```java
//我们知道使用线程池会调用execute或者submit，至于这两者的区别我就不多说了，
//事实上submit内部还是调用了execute，那么我就把execute展开讲一下复用原理
//首先execute把你要执行的task传进去，这里面其他的逻辑我不讲了，我就只讲线程复用相关的逻辑
execute(Runnable task)
    //里面会调用一个addWorker的方法
	addWorker(Runnable firstTask, boolean core)
    //addWorker里面new了一个Worker对象，事实上这个Worker对象才是真正干活的，
    //也就是我们要复用的线程对象
    	 w = new Worker(firstTask)

```

```java
//我们来看一下Worker的实现
 private final class Workerextends AbstractQueuedSynchronizerimplements Runnable{
     
     //构造方法
    Worker(Runnable firstTask) {
        setState(-1); // inhibit interrupts until runWorker
        this.firstTask = firstTask;
        this.thread = getThreadFactory().newThread(this);
     }
     //业务run方法
    public void run() {
        runWorker(this);
     }
    final void runWorker(Worker w) {
	//.....省略其余代码，只保留核心代码
    try {
        while (task != null || (task = getTask()) != null) {      
                    task.run();
        }
        completedAbruptly = false;
    } finally {
        processWorkerExit(w, completedAbruptly);
    }
  }
      
}
```


​		这个Worker对象呢事实上是ThreadPoolExecutor的一个内部类，它实现了Runnable也就意味着它本身也是一个线程，它的run方法调用了一个叫做runWorker的方法，这个runWorker的主要逻辑是一个while循环，循环里面是task.run()，这个操作看起来有点奇怪，因为我们从来不会显示调用run方法，只会调用start()方法，这个问题我待会展开再说。

​		刚才我们说过实现线程复用要解决的其中一个问题是，让复用的线程的run方法永远不要运行结束，那么也就是说Worker的run方法不能结束，也就是run方法里面的runWorker方法不能结束，也就是说runWorker里面的while循环不能结束，那么我们来看一下while循环的条件是如何实现的

```java
while (task != null || (task = getTask()) != null) {
  //....省略
  task.run();
  //....省略
}

private Runnable getTask() {
    //...各种省略
        for (;;) {
         Runnable r = timed ?
             //如果数量超过核心线程数，从队列中获取，获取不到超时返回null，导致外层while循环中断，
             //run方法结束,该Worker线程死亡，也就是配置的那个参数空闲线程超时时间，超时则死亡的原理。
             workQueue.poll(keepAliveTime, TimeUnit.NANOSECONDS) :
            //如果未超过核心线程数量，从队列中获取，获取不到一直阻塞，导致外层while循环一直阻塞，
            //实现Worker的run方法不结束，解决了要复用的Worker线程永不结束这一问题
             workQueue.take();
             return r;
        }
    }


```

​		那么解决了复用线程永不结束这个问题，还剩下一个问题，就是让复用的线程去执行我们传入的每个task的不同run方法的逻辑

​		还记得我上面说的while循环内部的奇怪操作task.run()吗？现在还觉得它奇怪吗？没错！task是任务队列中获取的我们从executer中传进来要执行的任务task，在Worker的run方法中显示调用task的run方法，实现了在复用的线程中执行不同业务的run方法，有点移花接木的意思

##### 17 四种线程池区别

```java
//可缓存线程池
ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
//固定数量线程池
ExecutorService fixedThreadPool = Executors.newFixedThreadPool(23);
//单线程池
ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
//定长线程池
ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(12);
```

1. 可缓存线程池：

   ```java
   (0, Integer.MAX_VALUE,60L, TimeUnit.SECONDS,new SynchronousQueue<Runnable>())
   //最大数量是int的最大值，空闲线程生存时间60s   适合低并发时间快的任务，高并发耗时任务很容易OOM
   ```

2. 固定数量线程池：

   ```java
   (nThreads, nThreads,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
   //核心数和最大数一样，它使用了一个LinkedBlockingQueue，一旦线程数满了就会不断的进队列，也可能会引发OOM
   ```

3. 单线程池：

   ```java
   (1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
   //是一个数量为1的固定长度线程池
   ```

4. 定时线程池：

   ```java
   public ScheduledThreadPoolExecutor(int corePoolSize) {
       super(corePoolSize, Integer.MAX_VALUE, 0, NANOSECONDS,
             new DelayedWorkQueue());
   }
   //可以设置延时执行或者延时重复执行
   ```

##### 18 线程池使用的队列

1. **ArrayBlockingQueue**：从名字就可以出来它是由数组实现，构造必须指定长度
2. **LinkedBlockingQueue**：
3. **PriorityBlockingQueue**
4. **SynchronousQueue**
5. **DelayQueue**

##### 19 线程池拒绝策略

1. `AbortPolicy`： 直接拒绝所提交的任务，并抛出**RejectedExecutionException**异常；
2. `DiscardPolicy`：不处理直接丢弃掉任务
3. `DiscardOldestPolicy`：丢弃掉阻塞队列中存放时间最久的任务，执行当前任务
4. `CallerRunsPolicy`：让调用者线程去执行该任务

##### 20 execute和submit的区别

最主要的区别是execute没有返回值自能传Runnable，而submit有返回值可以穿Runnable和Callable，事实上给submit传一个Runnable在内部也会转化成Callable

另外一个区别execute在执行的过程中如果有异常的话会抛出来，而submit不会抛出异常除非调用get()

##### 21 submit实现原理

传入一个Callable类型的task,他会调用一个newtaskFor的方法，这个内部实际上是把这个task封装成了一个FutureTask对象，

接下来他会把这个FutureTask传给execute去执行，最后把FutureTask对象返回

之后调用get方法内部会调用一个叫做awaitDone的方法等待线程执行完毕，而这个awaitDone内部是一个for的死循环一直等到你任务结束/异常/超时

```java
public <T> Future<T> submit(Callable<T> task) {
    if (task == null) throw new NullPointerException();
    RunnableFuture<T> ftask = newTaskFor(task);
    execute(ftask);
    return ftask;
}
protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
        return new FutureTask<T>(callable);
}
```





##### 21. 什么是AQS

AQS全称叫做 **AbstractQueuedSynchronizer**  `/kjuː/ /'sɪnkrənaɪzɚ/` 直译过来叫做**抽象队列同步器** ，而基于AQS实现了juc下很多重要组件，比如ReentrantLock可重入锁、ReentrantReadWriteLock可重入读写锁、Semaphore信号量、CountDownLatch等等的重要组件

它的实现原理是这样：

AQS这个类中有这么几个重要属性  一个header 一个tail 是Node类型的表示队列头和尾，state

加锁的时候直接使用cas改state，改成功了把自己设置为当前线程，如果失败了就会忘CLH队列里面塞

CLH队列:没抢到锁的线程会被封装成一个个Node节点，塞进CLH队列的尾巴，往进塞的时候它是使用For死循环配合CAS进入的，一旦进入成功，这个线程就会被LockSupport.park的方法把这个线程挂起，

当一个线程释放锁的时候他会去唤醒CLH队列头部的那个节点





##### 22. Semaphore

它的作用是通过acquire和release来对某段代码进行限流限制，也就是说可以去限制同时执行这块代码的线程数量

```java
private static Semaphore semaphore =  new Semaphore(3);
public static  void active(){
    try {
        semaphore.acquire();
        System.out.println("线程 "+Thread.currentThread().getName()+"进入 ");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程 "+Thread.currentThread().getName()+"退出 ");
    } catch (InterruptedException e) {
        e.printStackTrace();
    }finally {
        semaphore.release();
    }

}
```

##### 23. CountDownLatch

主要可以用于控制线程的执行顺序，比如CountDownLatch设置为2次。那么在AB线程中调用countDown，在C线程中调用await，那么C线程会阻塞在await方法处，直到AB线程都执行了countDown之后。

```java
private static CountDownLatch countDownLatch = new CountDownLatch(2);
public static void main(String[] args) {
    poolExecutor.execute(new Runnable() {
        @Override
        public void run() {
            System.out.println("线程1 进入");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("线程1 结束");
        }
    });
    poolExecutor.execute(new Runnable() {
        @Override
        public void run() {
            System.out.println("线程2 进入");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            countDownLatch.countDown();
            System.out.println("线程2 结束");
        }
    });
    
    try {
        System.out.println("主 等待");
        countDownLatch.await();
        System.out.println("主 等待完毕");
        poolExecutor.shutdown();
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
}
```

##### 22. JUC下常见组件

并发容器 ConcurrentHashMap CopyOnWriteArrayList

同步工具 Semaphore CountDownLatch CyclicBarrier

原子变量类 Atomic系列

##### 23.  thread.isInterrupted() Thread.interrupted()区别

Thread.interrupted 调用当前线程的isInterrupted（true）

thread.isInterrupted()调用isInterrupted（false）

##### 24. 什么是JMM

jmm是Java内存模型，定义了主存以及线程本地私有内存，规定了线程应该如何以及何时能够操作或者访问那些共享变量

##### 25 Atomic系列实现

构造传参进去，赋值给一个被volatile修饰的成员变量value

普通的get/set直接就是对这个value操作，因为他是volatile修饰的，即时可见。

想那些getAndIncrement getAndDecrement getAndAdd 那一系列API基本都是操作Unsafe这个类使用CAS去操作了

##### 26 Semaphore CountDownLatch CyclicBarrier 实现

内部基于一个Sync的一个内部类，而这个Sync这个类继承了AQS，实际上也就等于是基于AQS实现的

