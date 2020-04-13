package com.xintong.code.factory3.methhod;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 9:25
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        Factory noodleFactory = new NoodleFacctory();
        noodleFactory.getFood().getFood();
        noodleFactory = new RiceFacctory();
        noodleFactory.getFood().getFood();
        noodleFactory = new BreadFacctory();
        noodleFactory.getFood().getFood();
    }
}
