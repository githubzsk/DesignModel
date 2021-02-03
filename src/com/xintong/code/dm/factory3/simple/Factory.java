package com.xintong.code.dm.factory3.simple;

/**
 * @ClassName Factory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 9:17
 * @Version 1.0
 */
public class Factory {
    public static Food getFood(String type){
        if (type.equalsIgnoreCase("rice"))return new Rice();
        if (type.equalsIgnoreCase("bread"))return new Bread();
        if (type.equalsIgnoreCase("noodle"))return new Noodle();
        return null;
    }
}
