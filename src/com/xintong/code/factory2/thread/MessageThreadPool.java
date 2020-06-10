package com.xintong.code.factory2.thread;

import java.util.concurrent.*;

public class MessageThreadPool implements ThreadPool {


    private ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,1000, TimeUnit.SECONDS,new ArrayBlockingQueue(50),Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    @Override
    public ThreadPoolExecutor getThreadPool() {
        return pool;
    }
}
