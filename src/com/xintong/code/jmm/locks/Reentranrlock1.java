package com.xintong.code.jmm.locks;

import java.util.concurrent.locks.ReentrantLock;

public class Reentranrlock1 {
    private static ReentrantLock lock = new ReentrantLock(true);

    public static void main(String[] args) {
        new Thread(() -> test(), "线程1").start();
        new Thread(() -> test(), "线程2").start();
    }//

    public static void test() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " 获得了锁");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {
            lock.unlock();
        }
    }

}
