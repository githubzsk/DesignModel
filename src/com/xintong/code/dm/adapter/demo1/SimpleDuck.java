package com.xintong.code.dm.adapter.demo1;

public class SimpleDuck implements Duck {
    @Override
    public void quack() {
        System.out.println("鸭子呱呱叫");
    }

    @Override
    public void fly() {
        System.out.println("鸭子会飞");
    }
}
