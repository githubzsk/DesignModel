package com.xintong.code.dm.factory.simplefatory;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/27 14:12
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        Car car = SimpleFactory.getCar("BWM");
        String carName = car.getCarName();
        System.out.println(carName);
    }
}
