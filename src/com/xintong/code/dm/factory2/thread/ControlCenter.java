package com.xintong.code.dm.factory2.thread;


import java.util.concurrent.ThreadPoolExecutor;

public class ControlCenter {
    public static final int MESSAGE = 1;
    public static final int USB = 1;
    public static final int INTER = 1;


    static ThreadPoolExecutor getThreadPool(int type){
        if (type == MESSAGE) return new MessageThreadPool().getThreadPool();
        if (type == USB) return new USBThreadPool().getThreadPool();
        if (type == INTER) return new InterThreadPool().getThreadPool();
        return null;
    }
}
