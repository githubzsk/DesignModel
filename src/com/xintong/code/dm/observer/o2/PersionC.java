package com.xintong.code.dm.observer.o2;

public class PersionC implements Observe {
    @Override
    public void notify(String food) {
        if (food != null && food.equals("火锅"))
            System.out.println("i am C, i like "+food);
    }
}
