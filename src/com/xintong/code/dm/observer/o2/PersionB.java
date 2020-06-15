package com.xintong.code.dm.observer.o2;

public class PersionB implements Observe {
    @Override
    public void notify(String food) {
        if (food != null && food.equals("米饭"))
            System.out.println("i am B, i like "+food);
    }
}
