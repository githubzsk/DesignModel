package com.xintong.code.factory2.thread;

import java.util.concurrent.ThreadPoolExecutor;

public class Run {
    public static void main(String[] args) {
        ThreadPoolExecutor InterthreadPool = ControlCenter.getThreadPool(ControlCenter.INTER);
    }
}
