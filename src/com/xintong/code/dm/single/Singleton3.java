package com.xintong.code.dm.single;

/**
 * 懒加载
 * 优点：懒加载，解决了线程安全问题
 * 缺点：性能
 */
public class Singleton3 {
    private static Singleton3 singleton3;
    private Singleton3(){}
    public static synchronized Singleton3 getInstance(){
        if (singleton3 == null){
            singleton3 = new Singleton3();
        }
        return singleton3;
    }

}
