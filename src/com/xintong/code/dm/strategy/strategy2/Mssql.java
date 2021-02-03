package com.xintong.code.dm.strategy.strategy2;

/**
 * @ClassName Mssql
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 11:52
 * @Version 1.0
 */
public class Mssql implements DB {
    @Override
    public void connection() {
        System.out.println("开始连接Sql server...");//
    }
}
