package com.xintong.code.dm.factory.abstractfactory;

/**
 * @ClassName BWMFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 13:14
 * @Version 1.0
 */
public class AudiFactory extends AbstractFactory {
    @Override
    protected Car getCar() {
        return new AudiCar();
    }
}
