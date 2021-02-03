package com.xintong.code.dm.factory3.methhod;

/**
 * @ClassName NoodleFacctory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 9:24
 * @Version 1.0
 */
public class RiceFacctory implements Factory {
    @Override
    public Food getFood() {
        return new Rice();
    }
}
