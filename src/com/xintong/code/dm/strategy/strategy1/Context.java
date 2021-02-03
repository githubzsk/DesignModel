package com.xintong.code.dm.strategy.strategy1;

/**
 * @ClassName Context
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 11:24
 * @Version 1.0
 */
public class Context {
    private Strategy strategy;

    public Context(Strategy strategy){
        this.strategy = strategy;
    }

    public int executeStrategy(int num1, int num2){
        return strategy.doOperation(num1, num2);
    }
}
