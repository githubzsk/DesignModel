package com.xintong.code.dm.template.demo2;

public class BaiCai extends AbstractClass{


    @Override
    protected void pourOil() {
        System.out.println("倒水");
    }

    @Override
    protected void HeatOil() {
        System.out.println("热水");
    }

    @Override
    void pourVegetable() {
        System.out.println("加白菜");
    }

    @Override
    void pourSauce() {
        System.out.println("加白菜料");
    }
}
