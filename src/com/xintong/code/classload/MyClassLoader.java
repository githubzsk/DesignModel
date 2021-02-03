package com.xintong.code.classload;

public class MyClassLoader extends ClassLoader {
    @Override
    protected Class<?> findClass(String s) throws ClassNotFoundException {
        return super.findClass(s);
    }
}
