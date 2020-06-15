package com.xintong.code.dm.strategy.s1;

public class DatabaseOracle implements Connection {
    @Override
    public void doConn() {
        System.out.println("oracle连接.....");
    }
}
