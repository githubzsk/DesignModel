package com.xintong.code.strategy.strategy3;

import java.util.HashMap;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Application {
   static Semaphore semaphore = new Semaphore(5);
   static CountDownLatch latch = new CountDownLatch(3);
   static ReentrantLock lock = new ReentrantLock();
   static CyclicBarrier barrier = new CyclicBarrier(5);
   static ThreadLocal local = new ThreadLocal();
    public static void main(String[] args) throws InterruptedException {
        local.set(new Object());
        local.get();
        lock.lock();
        lock.unlock();
//        latch.countDown();
//        latch.await();
        semaphore.acquire();
        semaphore.release();//
//        try {
//            barrier.await();
//        } catch (BrokenBarrierException e) {
//            e.printStackTrace();
//        }
        AtomicInteger integer = new AtomicInteger(10);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicLong atomicLong = new AtomicLong(5l);
        ArticleCenter center = new ArticleCenter(JMMArticle.GETINSTANCE);
        String message = center.getMessage((int) (Math.random() * JMMArticle.values().length+1));
        System.out.println(message);
        HashMap map = new HashMap();
        map.put("","");
        ConcurrentHashMap chm = new ConcurrentHashMap();
        chm.put("","");
        Thread thread;
       // thread.isInterrupted();
       // thread.join();
        Thread.interrupted();

    }
}
