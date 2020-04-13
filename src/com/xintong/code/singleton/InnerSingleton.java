package com.xintong.code.singleton;

public class InnerSingleton {

    private InnerSingleton(){}

    public static InnerSingleton getInstance(){
        return MySingleton.singleton;
    }

    static  class MySingleton{
        private static InnerSingleton singleton = new InnerSingleton();
    }

}
