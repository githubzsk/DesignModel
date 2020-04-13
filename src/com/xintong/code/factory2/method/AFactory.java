package com.xintong.code.factory2.method;

/**
 * @ClassName AFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 16:13
 * @Version 1.0
 */
public class AFactory extends BaseFactory {

    @Override
    Product getProduct() {
        return new AProduct();
    }
}
