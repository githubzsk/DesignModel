package com.xintong.code.dm.proxy.proxy2;

/**
 * @ClassName ProxyTest
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 16:37
 * @Version 1.0
 */
public class ProxyTest implements Person {
    private Person o;

    public ProxyTest(Person o){
        this.o = o;
    }


    @Override
    public void sayHello(String content, int age) {
        // TODO Auto-generated method stub
        System.out.println("ProxyTest sayHello begin");
        //在代理类的方法中 间接访问被代理对象的方法
        o.sayHello(content, age);
        System.out.println("ProxyTest sayHello end");//
    }

    @Override
    public void sayGoodBye(boolean seeAgin, double time) {
        // TODO Auto-generated method stub
        System.out.println("ProxyTest sayHello begin");
        //在代理类的方法中 间接访问被代理对象的方法
        o.sayGoodBye(seeAgin, time);
        System.out.println("ProxyTest sayHello end");
    }
}
