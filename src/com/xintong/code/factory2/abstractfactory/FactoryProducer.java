package com.xintong.code.factory2.abstractfactory;

/**
 * @ClassName FactoryProducer
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 17:34
 * @Version 1.0
 */
public class FactoryProducer {
    public static AbstractFactory getFactory(String choice){
        if(choice.equalsIgnoreCase("SHAPE")){
            return new ShapeFactory();
        } else if(choice.equalsIgnoreCase("COLOR")){
            return new ColorFactory();
        }
        return null;
    }
}
