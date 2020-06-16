package com.xintong.code.dm.observer.o3;

public class PersionA implements Article {
    @Override
    public void notify(String str) {
        System.out.println("I am persionA i like "+str);
    }
}
