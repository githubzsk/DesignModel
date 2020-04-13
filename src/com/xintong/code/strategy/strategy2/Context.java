package com.xintong.code.strategy.strategy2;

/**
 * @ClassName Context
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 11:55
 * @Version 1.0
 */
public class Context {
    private DB db;
    public Context(DB db){
        this.db = db;
    }
//
    public void doConn(){
        db.connection();
    }
}
