package com.xintong.code.dm.observer.o2;

public interface Subject {
    void doRegister(Observe observe);
    void doNotify(String food);
}
