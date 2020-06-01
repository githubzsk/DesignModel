package com.xintong.code.jmm;

import com.xintong.code.proxy.proxy3.Run;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPool{
    public static void main(String[] args) throws Exception {
        B b = new B();
        b.start();
        new Thread(new A()).start();
        C c = new C();
        c.call();
        ExecutorService executorService = Executors.newWorkStealingPool();

        ThreadPoolExecutor executor = null;
        executor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return null;
            }
        });
        executor.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
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
