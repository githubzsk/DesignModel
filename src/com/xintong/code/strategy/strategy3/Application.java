package com.xintong.code.strategy.strategy3;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

public class Application {
   static Semaphore semaphore = new Semaphore(5);
   static CountDownLatch latch = new CountDownLatch(3);
   static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        lock.lock();
        lock.unlock();
        latch.countDown();
        latch.await();
        semaphore.acquire();
        semaphore.release();//
        AtomicInteger integer = new AtomicInteger(10);
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicLong atomicLong = new AtomicLong(5l);
        ArticleCenter center = new ArticleCenter(JVMArticle.GETINSTANCE);
        String message = center.getMessage((int) (Math.random() * JVMArticle.values().length+1));
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
