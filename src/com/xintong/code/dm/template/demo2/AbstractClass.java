package com.xintong.code.dm.template.demo2;

public  abstract class AbstractClass {
    //模板方法，用来控制炒菜的流程 （炒菜的流程是一样的-复用）
//申明为final，不希望子类覆盖这个方法，防止更改流程的执行顺序
    final void cookProcess(){
        //第一步：倒油
        pourOil();
        //第二步：热油
        HeatOil();
        //第三步：倒蔬菜
        pourVegetable();
        //第四步：倒调味料
        pourSauce();
        //第五步：翻炒
        fry();
    }

//定义结构里哪些方法是所有过程都是一样的可复用的，哪些是需要子类进行实现的

    //第一步：倒油是一样的，所以直接实现
    protected void pourOil(){
        System.out.println("倒油");
    }

    //第二步：热油是一样的，所以直接实现
    protected void  HeatOil(){
        System.out.println("热油");
    }

    //第三步：倒蔬菜是不一样的（一个下包菜，一个是下菜心）
//所以声明为抽象方法，具体由子类实现
    abstract void  pourVegetable();

    //第四步：倒调味料是不一样的（一个下辣椒，一个是下蒜蓉）
//所以声明为抽象方法，具体由子类实现
    abstract void  pourSauce();


    //第五步：翻炒是一样的，所以直接实现
    protected void fry(){
        System.out.println("炒啊炒啊炒到熟啊");
    }
}