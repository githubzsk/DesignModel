package com.xintong.code.dm.adapter.demo4;

public class XiaoMing implements Person
{
    @Override
    public void move() {
        System.out.println("人会走路");
    }

    @Override
    public void speak() {
        System.out.println("人会讲话");
    }
}
