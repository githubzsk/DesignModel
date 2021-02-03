package com.xintong.code.dm.factory2.simple;

/**
 * @ClassName Factory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 16:03
 * @Version 1.0
 */
public class Factory {
    public static int Message = 1;
    public static int USB = 2;
    public static int Inter = 3;
    public static Product getProduct(int type){
        if (type == Message) return new AProduct();
        if (type == USB) return new BProduct();
        if (type == Inter) return new CProduct();
        return null;
    }
}
