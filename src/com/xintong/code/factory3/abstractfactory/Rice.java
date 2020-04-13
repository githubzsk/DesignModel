package com.xintong.code.factory3.abstractfactory;

/**
 * @ClassName Noodle
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 10:33
 * @Version 1.0
 */
public class Rice implements Food {
    @Override
    public void introFood() {
        System.out.println("This is rice impl food");
    }
}
