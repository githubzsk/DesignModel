package com.xintong.code.strategy.strategy1;

/**
 * @ClassName OperationAdd
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 11:19
 * @Version 1.0
 */
public class OperationMultiply implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 * num2;
    }
}
