package com.xintong.code.strategy.strategy3;

public enum JMMArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1什么是线程？线程和进程的区别"),//
    ITEM2(2," 2Java中如何实现线程"),//1
    ITEM3(3,"3如何停止一个线程"),//1
    ITEM4(4,"4volatile关键字作用及其原理"),//1
    ITEM5(5,"5Synchroized锁方法、代码块的区别"),//1
    ITEM6(6,"6synchronized为什么叫重量级锁"),//1
    ITEM7(7,"7Synchroized锁升级过程"),//1
    ITEM8(8,"8介绍CAS"),//1
    ITEM9(9,"9自旋锁"),//
    ITEM10(10,"10线程状态"),//1
    ITEM11(11,"11sleep、wait区别"),//1
    ITEM12(12,"12为什么wait在Object中"),//1
    ITEM13(13,"13Runnable和Callable区别"),//1
    ITEM14(14," 14线程池原理 核心参数？"),//
    ITEM15(15,"15线程池线程复用原理"),//1
    ITEM16(16,"16四种线程池区别"),//1
    ITEM17(17,"17线程池使用的队列"),//
    ITEM18(18,"18线程池拒绝策略"),//1
    ITEM19(19,"19execute和submit的区别"),//1
    ITEM20(20,"20submit实现原理"),//
    ITEM21(21,"21AQS"),//
    ITEM22(22,"22Semaphore"),//1
    ITEM23(23,"23CountDownLatch");//
    JMMArticle(int id,String name){
        this.id = id;
        this.name = name;
    }
    private String getName() {
        return name;
    }
    private  int getId() {
        return id;
    }
    private String name;
    private int id;

    @Override
    public String getMessage(int id) {
        JMMArticle[] values = JMMArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
