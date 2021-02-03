package com.xintong.code.dm.adapter.demo1;

public class AdapterCock implements Cock {


    private Duck duck;

    public AdapterCock(Duck duck){
        this.duck = duck;
    }

    @Override
    public void gobble() {
        duck.quack();
    }

    @Override
    public void fly() {
        duck.fly();
    }
}
