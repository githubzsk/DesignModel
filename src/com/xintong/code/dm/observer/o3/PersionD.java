package com.xintong.code.dm.observer.o3;

public class PersionD implements Article {
    @Override
    public void notify(String str) {
        System.out.println("I am PersionD i like "+str);
    }
}
