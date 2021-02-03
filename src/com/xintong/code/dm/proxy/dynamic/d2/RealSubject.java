package com.xintong.code.dm.proxy.dynamic.d2;

public class RealSubject implements Subject {
    public void request() {
        System.out.println("RealSubject execute request()");
    }
}
