package com.xintong.code.dm.single;

/**
 * 不安全的懒汉式
 * 优点：懒加载
 * 缺点：线程不安全
 */
public class Singleton2 {
    private static Singleton2 singleton2;
    private Singleton2(){}
    public static Singleton2 getInstance(){
        if (singleton2 == null){
            singleton2 = new Singleton2();
        }
        return singleton2;
    }
}
