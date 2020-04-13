package com.xintong.code.strategy.strategy2;

/**
 * @ClassName Oracle
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 13:16
 * @Version 1.0
 */
public class Oracle implements DB {
    @Override
    public void connection() {
        System.out.println("开始连接Oracle..");//
    }
}
