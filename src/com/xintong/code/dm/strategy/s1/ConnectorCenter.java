package com.xintong.code.dm.strategy.s1;

public class ConnectorCenter{
    private Connection connection;
    public ConnectorCenter(Connection connection){
        this.connection = connection;
    }
    public void doConn(){
        connection.doConn();
    }
}
