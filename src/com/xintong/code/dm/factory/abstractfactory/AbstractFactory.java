package com.xintong.code.dm.factory.abstractfactory;

/**
 * @ClassName AbstractFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 13:09
 * @Version 1.0
 */
public abstract class AbstractFactory {
    protected abstract Car getCar();//

    public Car getCar(String carName){
        if ("BWM".equals(carName)) return new BWMFactory().getCar();
        if ("Audi".equals(carName)) return new AudiFactory().getCar();
        if ("Benz".equals(carName)) return new BenzFactory().getCar();
        System.out.println("未找到该型号");
        return null;
    }

}
