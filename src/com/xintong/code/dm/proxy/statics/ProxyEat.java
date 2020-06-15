package com.xintong.code.dm.proxy.statics;

public class ProxyEat implements Eat {

    private Eat eat;

    public ProxyEat(Eat eat){
        this.eat = eat;
    }
    @Override
    public void doEat() {
        System.out.println("洗手");
        eat.doEat();
        System.out.println("擦嘴");
    }
}
