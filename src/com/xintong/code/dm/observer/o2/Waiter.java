package com.xintong.code.dm.observer.o2;

import java.util.ArrayList;
import java.util.List;

public class Waiter implements Subject {
    private final List<Observe> list = new ArrayList<>();
    @Override
    public void doRegister(Observe observe) {
        list.add(observe);
    }

    @Override
    public void doNotify(String food) {
        list.forEach(x -> x.notify(food));
    }
}
