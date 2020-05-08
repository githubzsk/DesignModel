# JMM/并发面试题

##### 1. *线程池原理 核心参数

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

handler：拒绝策略，拒绝策略一共有4中

1. AbortPolicy： 直接拒绝所提交的任务，并抛出**RejectedExecutionException**异常；
2. DiscardPolicy：不处理直接丢弃掉任务
3. DiscardOldestPolicy：丢弃掉阻塞队列中存放时间最久的任务，执行当前任务
4. CallerRunsPolicy：让调用者线程去执行该任务

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

##### 2. *线程池线程复用原理

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

##### 3.  *如何实现线程同步

##### 4. *单机环境并发控制

##### 5. *创建线程的几种方式

##### 6.  *volatile关键字作用及其原理

1. **作用**

   - _原子性_：原子性指的是一个操作不可被中断，volatile实现原子性只是特定的情况下，在32位的jdk中，long跟double都没有实现原子性，想要实现原子性就得用volatile修饰，其余情况下，volatile并不能实现原子性
   - _可见性_：是指A线程修改了变量的值，B线程立马就能看到
   - _禁止代码重排序_:我们知道在java代码运行的时候，JIT（Just In Time）即时编译器会起作用，它会优化代码优先执行轻耗代码从而进行代码重排序，它的优化排序并不会影响单线程下的代码逻辑，如果使用了volatile，那么volatile就像一道屏障一样，把volatile之前和之后的代码隔开不会被重排序

2. **原理**

   volatile修饰的变量在转化为汇编指令的时候会在前面加一个LOCK指令，当CUP执行这个指令的时候会立即做两件事情

   1. 把当前内核高速缓存中的数据立即写回主存
   2. 让其他内核中缓存的数据立即失效

   操作当前内核里面高速缓存中的数据写会主存简单，但是操作其他内核缓存的数据失效就没那么容易，所以这里要借助MESI协议，MESI协议规定了变量的4中状态，M被修改状态 E独占状态 S共享状态 I无效状态，多线程操作被volatile修饰的变量属于共享数据，共享状态意味着时刻被监控，一旦发现共享数据发生改变，立马把共享数据的状态改为I 也就是无效状态，当别的线程去读取这个volatile修饰的数据时候发现他是无效状态，那么它会去主存中重新读取，读取到的则为最新的数据

##### 7. *Synchroized和lock的底层原理

##### 8. *Synchroized锁方法、对象头、代码块的区别

##### 9. *Synchroized锁升级过程

##### 10. *介绍CAS

##### 11. * CAS应用（乐观锁）

