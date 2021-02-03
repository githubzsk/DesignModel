package com.xintong.code.dm.factory2.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class InterThreadPool implements ThreadPool {
    private ThreadPoolExecutor pool = new ThreadPoolExecutor(2,5,1000, TimeUnit.SECONDS,new ArrayBlockingQueue(50), Executors.defaultThreadFactory(),new ThreadPoolExecutor.AbortPolicy());

    @Override
    public ThreadPoolExecutor getThreadPool() {
        return pool;
    }
}
