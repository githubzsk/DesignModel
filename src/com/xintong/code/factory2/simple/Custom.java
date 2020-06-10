package com.xintong.code.factory2.simple;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 16:06
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        Product product = Factory.getProduct(Factory.USB);
        product.intro();
    }
}
