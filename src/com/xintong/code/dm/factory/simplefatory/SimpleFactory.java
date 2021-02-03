package com.xintong.code.dm.factory.simplefatory;

/**
 * @ClassName SimpleFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/27 14:08
 * @Version 1.0
 */
public class SimpleFactory {

    public static Car getCar(String name){


        switch (name){
            case "Benz":
                return new Benz();
            case "Audi":
                return new Audi();
            case "BWM":
                return new BWM();
                default:return null;
        }
    }
}
