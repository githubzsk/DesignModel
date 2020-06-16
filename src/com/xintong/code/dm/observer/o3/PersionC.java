package com.xintong.code.dm.observer.o3;

public class PersionC implements Article {
    @Override
    public void notify(String str) {
        System.out.println("I am PersionC i like "+str);
    }
}
