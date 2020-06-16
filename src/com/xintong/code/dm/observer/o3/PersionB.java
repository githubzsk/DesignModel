package com.xintong.code.dm.observer.o3;

public class PersionB implements Article {
    @Override
    public void notify(String str) {
        System.out.println("I am PersionB i like "+str);
    }
}
