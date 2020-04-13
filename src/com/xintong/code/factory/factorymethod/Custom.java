package com.xintong.code.factory.factorymethod;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/27 14:18
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        BenzFactory benzFactory = new BenzFactory();
        Car car = benzFactory.getCar();
        System.out.println(car.getCarName());

    }
}
