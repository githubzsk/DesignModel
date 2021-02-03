package com.xintong.code.dm.factory2.method;

/**
 * @ClassName AFactory
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/30 16:13
 * @Version 1.0
 */
public class BFactory extends BaseFactory {

    @Override
    Product getProduct() {
        return new BProduct();
    }
}
