package com.xintong.code.strategy.strategy2;

import java.util.Comparator;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 13:14
 * @Version 1.0
 */
public class Custom {

//
    public static void main(String[] args) {
        Context context = new Context(new Mysql());//
        context.doConn();
       // Executors.newCachedThreadPool();
    }
}
