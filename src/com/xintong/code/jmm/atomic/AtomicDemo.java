package com.xintong.code.jmm.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    private static AtomicInteger integer = new AtomicInteger(6);
    public static void main(String[] args) {
        int andIncrement = integer.getAndIncrement();
        System.out.println(andIncrement);
        System.out.println(integer);
    }
}
