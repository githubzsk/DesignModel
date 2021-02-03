package com.xintong.code.dm.singleton;

/**
 * 线程安全饿汉式
 */
public class Singleton2 {
    private static Singleton2 singleton2 = new Singleton2();
    private Singleton2(){}
    public static Singleton2 getInstance(){
        return singleton2;
    }
}
