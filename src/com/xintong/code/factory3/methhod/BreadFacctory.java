package com.xintong.code.factory3.methhod;

/**
 * @ClassName NoodleFacctory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 9:24
 * @Version 1.0
 */
public class BreadFacctory implements Factory {
    @Override
    public Food getFood() {
        return new Bread();
    }
}
