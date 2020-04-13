package com.xintong.code.factory.abstractfactory;

/**
 * @ClassName DefaultFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 13:18
 * @Version 1.0
 */
public class DefaultFactory extends AbstractFactory {

    private AudiFactory defaultFactory = new AudiFactory();
    @Override
    protected Car getCar() {
        return defaultFactory.getCar();//
    }
}
