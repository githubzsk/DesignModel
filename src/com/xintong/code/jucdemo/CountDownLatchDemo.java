package com.xintong.code.jucdemo;

import java.util.concurrent.*;

public class CountDownLatchDemo {
    private static CountDownLatch countDownLatch = new CountDownLatch(3);
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(6, 6, 1, TimeUnit.SECONDS, new ArrayBlockingQueue(10), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) throws InterruptedException {
        doEat();
        countDownLatch.await();
        System.out.println("服务员上菜");
        executor.shutdown();
    }

    private static void doEat() {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("1号客人入场，正在入座....");
                try {
                    Thread.sleep(1000);
                    System.out.println("1号客人入场，等待上菜");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("2号客人入场，正在入座....");
                try {
                    Thread.sleep(2000);
                    System.out.println("2号客人入场，等待上菜");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("3号客人入场，正在入座....");
                try {
                    Thread.sleep(3000);
                    System.out.println("3号客人入场，等待上菜");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                countDownLatch.countDown();
            }
        });
        executor.shutdown();

    }

}
