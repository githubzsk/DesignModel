package com.xintong.code.dm.factory.abstractfactory;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 13:25
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        DefaultFactory defaultFactory = new DefaultFactory();
        String name = defaultFactory.getCar("Aud").getName();
        System.out.println(name);
    }
}
