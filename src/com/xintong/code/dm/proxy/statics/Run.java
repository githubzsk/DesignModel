package com.xintong.code.dm.proxy.statics;

public class Run {
    public static void main(String[] args) {
        Eat eat = new ProxyEat(new PersionB());
        eat.doEat();
    }
}
