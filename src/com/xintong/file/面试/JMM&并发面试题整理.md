# JMM/并发面试题

##### 1. *线程池原理 核心参数

线程池的核心类ThreadPoolExecutor，先从构造说起，这个类对外提供了4个构造方法，事实上准确的来说这个类之后一个构造，因为其他三个构造都是调用它的最后一个构造

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

_threadFactory_:线程工程，用来创建线程

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

##### 2. *项目中线程安全如何控制

##### 3. *如何实现线程同步

##### 4. *单机环境并发控制

##### 5. *创建线程的几种方式

##### 6. *volatile关键字

##### 7. *Synchroized和lock的底层原理

##### 8. *Synchroized锁方法、对象头、代码块的区别

##### 9. *Synchroized锁升级过程

##### 10. *介绍CAS

##### 11. * CAS应用（乐观锁）

##### 

