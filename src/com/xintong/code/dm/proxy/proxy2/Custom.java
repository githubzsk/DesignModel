package com.xintong.code.dm.proxy.proxy2;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 16:37
 * @Version 1.0
 */
public class Custom {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        //s为被代理的对象，某些情况下 我们不希望修改已有的代码，我们采用代理来间接访问
        Student s = new Student();
        //创建代理类对象
        ProxyTest proxy = new ProxyTest(s);
        //调用代理类对象的方法
        proxy.sayHello("welcome to java", 20);
        System.out.println("******");
        //调用代理类对象的方法
        proxy.sayGoodBye(true, 100);//zhuangbei
       // proxy.sayGoodBye();

    }
}
