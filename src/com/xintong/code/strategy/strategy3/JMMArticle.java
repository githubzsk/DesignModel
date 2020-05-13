package com.xintong.code.strategy.strategy3;

public enum JMMArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"什么是线程？线程和进程的区别"),
    ITEM2(2," Java中如何实现线程"),
    ITEM3(3,"如何停止一个线程"),
    ITEM4(4,"volatile关键字作用及其原理"),
    ITEM5(5,"Synchroized锁方法、代码块的区别"),
    ITEM6(6,"synchronized为什么叫重量级锁"),
    ITEM7(7,"Synchroized锁升级过程"),
    ITEM8(8,"介绍CAS"),
    ITEM9(9,"自旋锁"),
    ITEM10(10,"线程状态"),
    ITEM11(11,"sleep、wait区别"),
    ITEM12(12,"为什么wait在Object中"),
    ITEM13(13,"Runnable和Callable区别"),
    ITEM14(14," 线程池原理 核心参数？"),
    ITEM15(15,"线程池线程复用原理"),
    ITEM16(16,"四种线程池区别"),
    ITEM17(17,"线程池使用的队列"),
    ITEM18(18,"线程池拒绝策略"),
    ITEM19(19,"execute和submit的区别"),
    ITEM20(20,"submit实现原理"),
    ITEM21(21,"AQS"),
    ITEM22(22,"Semaphore"),
    ITEM23(23,"CountDownLatch");
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
