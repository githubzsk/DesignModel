package com.xintong.code.dm.singleton;

public class DoubleCheckSingleton {
    private static volatile  DoubleCheckSingleton singleton;
    private DoubleCheckSingleton(){}
    public static DoubleCheckSingleton getInstance(){
        if (singleton == null){
            synchronized (DoubleCheckSingleton.class){
                if (singleton == null){
                    //1 分配内存
                    //2 实例化对象
                    //3 将对象指向刚分配的内存
                    // 如果指令重排序 2 3 重排序   A线程执行 132  走到3时候，B线程判断外层singleton不为null
                    // 直接return singleton，便出现问题
                    singleton = new DoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
