package com.xintong.code.dm.factory2.abstractfactory;

/**
 * @ClassName Rectangle
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 17:28
 * @Version 1.0
 */
public class Rectangle implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
