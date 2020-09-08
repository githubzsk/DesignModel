package com.xintong.code.jmm;

import java.util.concurrent.Callable;

public class Run {
    public static void main(String[] args) {
       TestCall call = new TestCall();
        try {
            Integer call1 = call.call();
            System.out.println(call1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class  TestCall implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        return 1;
    }
}
