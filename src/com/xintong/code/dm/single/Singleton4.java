package com.xintong.code.dm.single;

/**
 * 懒加载：双重检查
 */
public class Singleton4 {
    // 使用volatile禁止指令重排序，避免出现问题
    private volatile static Singleton4 singleton4;
    private Singleton4(){}
    public static Singleton4 getInstance(){
        if (singleton4 == null){
            synchronized (Singleton4.class){
                if (singleton4 == null){
                    singleton4 = new Singleton4();
                }
            }
        }
        return singleton4;
    }

}
