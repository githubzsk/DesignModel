package com.xintong.code.proxy.proxy2;

/**
 * @ClassName Student
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 16:36
 * @Version 1.0
 */
public class Student implements Person {
    @Override
    public void sayHello(String content, int age) {
        System.out.println("student say hello" + content + " "+ age);
    }

    @Override
    public void sayGoodBye(boolean seeAgin, double time) {
        System.out.println("student sayGoodBye " + time + " "+ seeAgin);
    }
}
