package com.xintong.code.dm.strategy.strategy1;

/**
 * @ClassName StrategyPatternDemo
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 11:24
 * @Version 1.0
 */
public class StrategyPatternDemo {
    public static void main(String[] args) {//
        Context context = new Context(new OperationAdd());
        System.out.println("10 + 5 = " + context.executeStrategy(10, 5));
        Context context1 = new Context(new OperationAdd());

        System.out.println(context1.executeStrategy(1,8));
        context = new Context(new OperationSubstract());
        System.out.println("10 - 5 = " + context.executeStrategy(10, 5));

        context = new Context(new OperationMultiply());
        System.out.println("10 * 5 = " + context.executeStrategy(10, 5));
    }
}
