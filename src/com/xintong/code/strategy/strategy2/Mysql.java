package com.xintong.code.strategy.strategy2;

/**
 * @ClassName Mysql
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 11:50
 * @Version 1.0
 */
public class Mysql implements DB {
    @Override
    public void connection() {
        System.out.println("开始连接Mysql");
    }
}
