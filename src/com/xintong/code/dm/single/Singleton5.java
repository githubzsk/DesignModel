package com.xintong.code.dm.single;

/**
 * 内部类实现懒加载
 * 只有在调用getInstance的时候才会加载SingletonHolder
 */
public class Singleton5 {
    private static class SingletonHolder{
        private static Singleton5 singleton5 = new Singleton5();
    }
    private Singleton5(){}
    public static Singleton5 getInstance(){
        return SingletonHolder.singleton5;
    }
}
