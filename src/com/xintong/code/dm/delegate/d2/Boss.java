package com.xintong.code.dm.delegate.d2;

public class Boss {

    public void drink(String drinks) {
        ZhangManager manager;
        if("水".equals(drinks)) {
            manager = new ZhangManager(new XiaoLiEmployee());
            manager.work();
        }
        if("茶".equals(drinks)) {
            manager = new ZhangManager(new XiaoLiuEmployee());
            manager.work();
        }
    }
}
