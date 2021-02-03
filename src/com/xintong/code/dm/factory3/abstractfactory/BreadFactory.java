package com.xintong.code.dm.factory3.abstractfactory;

/**
 * @ClassName FoodFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 10:38
 * @Version 1.0
 */
public class BreadFactory extends AbstractFactory{
    @Override
    Food getFood() {
        return new Bread();
    }
}
