package com.xintong.code.dm.observer.o1;

public interface Subject {
    void registerObserver(Observer o);
    void notifyObservers(String tweet);
}
