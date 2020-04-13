package com.xintong.code.factory.factorymethod;

/**
 * @ClassName BWMFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/27 14:17
 * @Version 1.0
 */
public class BWMFactory implements Factory {
    @Override
    public Car getCar() {
        return new BWM();
    }
}
