package com.xintong.code.annotation;

public class Student {
    @PersonAnnotation(name = "丽丽",age = 23,isMan = false)
    public void study(int times){
        for(int i = 0; i < times; i++){
            System.out.println("Good Good Study, Day Day Up!");
        }
    }

}
