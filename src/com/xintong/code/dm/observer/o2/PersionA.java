package com.xintong.code.dm.observer.o2;

public class PersionA implements Observe{
    @Override
    public void notify(String food) {
        if (food != null && food.equals("面条"))
            System.out.println("i am A, i like "+food);
    }
}
