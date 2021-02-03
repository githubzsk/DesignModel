package com.xintong.code.dm.factory2.method;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 16:17
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        BaseFactory a = new AFactory();
        a.getProduct().intro();
        BaseFactory b = new BFactory();
        b.getProduct().intro();
        BaseFactory c = new CFactory();
        c.getProduct().intro();
    }
}
