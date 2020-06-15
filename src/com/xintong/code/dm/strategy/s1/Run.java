package com.xintong.code.dm.strategy.s1;

public class Run {
    public static void main(String[] args) {
        ConnectorCenter center = new ConnectorCenter(new DatabaseOracle());
        center.doConn();
    }
}
