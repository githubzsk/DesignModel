package com.xintong.code.dm.observer.o1;

import java.util.ArrayList;
import java.util.List;

public class Feed implements Subject {

    private final List<Observer> list = new ArrayList<>();
    @Override
    public void registerObserver(Observer o) {
        list.add(o);
    }

    @Override
    public void notifyObservers(String tweet) {
        list.forEach(x -> x.notify(tweet));
    }
}
