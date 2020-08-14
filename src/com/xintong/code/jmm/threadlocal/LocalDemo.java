package com.xintong.code.jmm.threadlocal;

public class LocalDemo {

    public static void main(String[] args) {
        ThreadLocal<String> local = new ThreadLocal<>();
        local.set("aaa");
        String s = local.get();

        System.out.println(s);
    }
}
