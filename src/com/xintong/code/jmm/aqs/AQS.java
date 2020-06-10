package com.xintong.code.jmm.aqs;

import java.util.concurrent.locks.ReentrantLock;

public class AQS {
    private static ReentrantLock lock = new ReentrantLock();
    public static void main(String[] args) {
        lock.lock();
        try {
            System.out.println("执行业务1");
            lock.lock();
            try{
                System.out.println("执行业务2");
            }finally {
                lock.unlock();
            }
        }finally {
            lock.unlock();
        }

    }




}
