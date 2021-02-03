package com.xintong.code.dm.strategy.strategy3;

public enum DubboArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"dubbo1"),//√
    ITEM2(2,"dubbo2"),//√
    ITEM3(3,"dubbo3"),//√
    ITEM4(4,"dubbo4"),
    ITEM5(5,"Redis单线程为什么这么快"),
    ITEM6(6,"Redis单线程如何处理客户端并发连接"),//√
    ITEM7(7,"Redis为什么是单线程，一定是单线程吗？"),
    ITEM8(8,"常用的垃圾收集算法"),//√
    ITEM9(9,"为什么年轻代使用复制算法而老年代使用标记整理/标记清除"),//√
    ITEM10(10,"空间分配担保机制"),//√
    ITEM11(11,"对象分配规则"),
    ITEM12(12,"垃圾收集器分类"),
    ITEM13(13,"G1和CMS的区别"),//√
    ITEM14(14,"方法区会被回收吗？"),
    ITEM15(15,"内存溢出"),
    ITEM16(16,"内存泄漏"),//√
    ITEM17(17,"四种引用类型"),
    ITEM18(18,"对象的定位方式有哪些"),//√
    ITEM19(19,"什么是STW，为什么要STW"),
    ITEM20(20,"什么是类加载机制"),//√
    ITEM21(21,"类加载过程"),//√
    ITEM22(22,"说说你了解的类加载器"),
    ITEM23(23,"什么是双亲委派模型"),//√
    ITEM24(24,"为什么使用双亲委派模型？"),
    ITEM25(25,"打破双亲委派模型"),
    ITEM26(26,"什么是SPI机制");
    DubboArticle(int id,String name){
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
        DubboArticle[] values = DubboArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
