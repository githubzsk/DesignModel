package com.xintong.code.factory2.abstractfactory;

/**
 * @ClassName Red
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 17:32
 * @Version 1.0
 */
public class Red implements Color{
    @Override
    public void fill() {
        System.out.println("Inside Red::fill() method.");
    }
}
