package com.xintong.code.factory2.abstractfactory;

/**
 * @ClassName AbstractFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 17:33
 * @Version 1.0
 */
public abstract class AbstractFactory {
    public abstract Color getColor(String color);
    public abstract Shape getShape(String shape) ;
}
