package com.xintong.code.dm.delegate.d1;

import java.util.Random;

/**
 * 代理角色
 */
public class TaskDelegate implements Task{
    public void doTask() {
        System.out.println("代理执行开始....");

        Task task = null;
        if (new Random().nextBoolean()){
            task = new ConcreteTaskA();
            task.doTask();
        }else{
            task = new ConcreteTaskB();
            task.doTask();
        }

        System.out.println("代理执行完毕....");
    }
}
