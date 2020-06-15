package com.xintong.code.dm.observer.o2;

public class Run {
    public static void main(String[] args) {
        Waiter waiter = new Waiter();
        PersionA a = new PersionA();
        PersionB b = new PersionB();
        PersionC c = new PersionC();
        waiter.doRegister(a);
        waiter.doRegister(b);
        waiter.doRegister(c);
        waiter.doNotify("米饭");
    }

}
