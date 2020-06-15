package com.xintong.code.dm.single;

/**
 * 饿汉式
 * 优点：线程安全
 * 缺点：如果没使用，会造成内存浪费
 */
public class Singleton1 {
    private static Singleton1 singleton = new Singleton1();
    private Singleton1(){}
    public static Singleton1 getInstance(){
        return singleton;
    }
}
