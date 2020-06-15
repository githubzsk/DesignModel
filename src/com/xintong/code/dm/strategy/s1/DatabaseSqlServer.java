package com.xintong.code.dm.strategy.s1;

public class DatabaseSqlServer implements Connection {
    @Override
    public void doConn() {
        System.out.println("mssql连接.....");
    }
}
