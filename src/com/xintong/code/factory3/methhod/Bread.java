package com.xintong.code.factory3.methhod;

/**
 * @ClassName Rice
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 9:14
 * @Version 1.0
 */
public class Bread implements Food {
    @Override
    public void getFood() {
        System.out.println("This is bread impl food");
    }
}
