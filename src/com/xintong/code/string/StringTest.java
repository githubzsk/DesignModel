package com.xintong.code.string;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName StringTest
 * @Description TODO
 * @Author zsk
 * @Date 2020/1/2 10:41
 * @Version 1.0
 */
public class StringTest {

    public static void main(String[] args) {
        F f = new F();
        B bf = (B)f;

    }
//
}

abstract class A implements C{
    abstract void methodA1();
}
abstract class B implements C{
    abstract void methodB1();
    protected void methodB2(){
        System.out.printf("This is abstract class B'methodB3 ");
    }
}
interface C extends D {
    void methdodC1();
}
interface D{
    void methodD1();
}

class F extends B implements D{


    @Override
    void methodB1() {
        System.out.printf("F extends B' B1");
    }

    @Override
    public void methdodC1() {
        System.out.printf("F extends B' extends C");
    }

    @Override
    public void methodD1() {
        System.out.printf("F impl D1");
    }
}

class E extends B{


    @Override
    public void methdodC1() {
        System.out.printf("C1");
    }

    @Override
    public void methodD1() {
        System.out.printf("D1");
    }

    @Override
    void methodB1() {
        System.out.printf("B1");
    }
    void methodE(){
        this.methodB2();
    }
}


