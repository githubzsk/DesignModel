package com.xintong.code.dm.strategy.s1;

public class DatabaseMysql implements Connection {
    @Override
    public void doConn() {
        System.out.println("mysql连接.....");
    }
}
