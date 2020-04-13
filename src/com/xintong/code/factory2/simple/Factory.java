package com.xintong.code.factory2.simple;

/**
 * @ClassName Factory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 16:03
 * @Version 1.0
 */
public class Factory {
    public static int A = 1;
    public static int B = 2;
    public static int C = 3;
    public static Product getProduct(int type){
        if (type == A) return new AProduct();
        if (type == B) return new BProduct();
        if (type == C) return new CProduct();
        return null;
    }
}
