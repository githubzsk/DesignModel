package com.xintong.code.callback;

/**
 * @ClassName SuperCalculator
 * @Description TODO
 * @Author zsk
 * @Date 2020/1/6 14:58
 * @Version 1.0
 */
public class SuperCalculator {
    public void add(int a, int b, DoJob  customer) {
        int result = a + b;
        customer.fillBlank(a, b, result);//
    }
}