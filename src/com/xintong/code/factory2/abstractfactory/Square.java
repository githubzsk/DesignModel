package com.xintong.code.factory2.abstractfactory;

/**
 * @ClassName Rectangle
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 17:28
 * @Version 1.0
 */
public class Square implements Shape {
    @Override
    public void draw() {
        System.out.println("Inside Square::draw() method.");
    }
}
