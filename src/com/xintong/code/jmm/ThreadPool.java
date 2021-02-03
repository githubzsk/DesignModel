package com.xintong.code.jmm;

import com.xintong.code.dm.proxy.proxy3.Run;

import java.util.concurrent.*;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class ThreadPool{

    private static AbstractQueuedSynchronizer aqs;
    public static void main(String[] args) throws Exception {
//        B b = new B();
//        b.start();
//        new Thread(new A()).start();
//        C c = new C();
//        c.call();
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        ThreadPoolExecutor executor = null;
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                throw new RuntimeException();

            }
        });
        future.get();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException();
            }
        });
        executorService.shutdown();
    }

}
class A implements Runnable{

    @Override
    public void run() {

    }
}
class B extends Thread{
    @Override
    public void run() {
        super.run();
    }
}
class C implements Callable<Integer>{


    @Override
    public Integer call() throws Exception {
        return null;
    }
}
