package com.xintong.code.factory.factorymethod;

/**
 * @ClassName BenzFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/27 14:16
 * @Version 1.0
 */
public class BenzFactory implements Factory {
    @Override
    public Car getCar() {
        return new Benz();
    }
}
