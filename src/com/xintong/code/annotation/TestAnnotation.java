package com.xintong.code.annotation;

import java.lang.reflect.Method;

public class TestAnnotation {
    public static void main(String[] args) {
        try {
            //获取Student的Class对象
            Class stuClass = Class.forName("com.xintong.code.annotation.Student");

            //说明一下，这里形参不能写成Integer.class，应写为int.class
            Method stuMethod = stuClass.getMethod("study",int.class);

            if(stuMethod.isAnnotationPresent(PersonAnnotation.class)){
                System.out.println("Student类上配置了PersonAnnotation注解！");
                //获取该元素上指定类型的注解
                PersonAnnotation personAnnotation = stuMethod.getAnnotation(PersonAnnotation.class);
                System.out.println("name: " + personAnnotation.name() + ", age: " + personAnnotation.age()
                        + ", sex: " + personAnnotation.isMan());
            }else{
                System.out.println("Student类上没有配置PersonAnnotation注解！");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
