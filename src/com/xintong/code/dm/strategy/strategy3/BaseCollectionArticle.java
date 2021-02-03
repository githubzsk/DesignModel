package com.xintong.code.dm.strategy.strategy3;

public enum BaseCollectionArticle implements Article {

    GETINSTANCE(0,""),
    ITEM1(1,"1集合的分类"),//1
    ITEM2(2,"2集合和数组的区别"),//1
    ITEM3(3,"3Comparable 和 Comparator"),//1
    ITEM4(4,"4Vector & ArrayList "),//1
    ITEM5(5,"5ArrayList & LinkedList"),//1
    ITEM6(6,"6List和Set"),//1
    ITEM7(7,"7遍历集合删除元素"),//1
    ITEM8(8,"8Arraylist排序"),//1
    ITEM9(9,"9ArrayList去重"),//1
    ITEM13(13,"13为什么要有迭代器"),//1
    ITEM14(14,"14为什么要重写hashcode和equals/重写equals为什么一定要重写hashCode"),
    ITEM15(15,"15遍历HashMap"),//
    ITEM16(16,"16对象做HashMap的Key值，需要注意什么？"),//1
    ITEM17(17,"17HashMap的理解"),//1
    ITEM18(18,"18hashcode的作用"),//1
    ITEM19(19,"19HashMap的hash算法"),//1
    ITEM20(20,"20HashMap结构"),//1
    ITEM21(21,"21HashMap的put方法流程"),//1
    ITEM22(22,"22HashMap的resize()扩容方法"),//1
    ITEM23(23,"23HashMap长度为什么2的n次方"),//1
    ITEM24(24,"24HashMap的默认负载因子为什么是0.75，初始容量为什么是16"),
    ITEM25(25,"25JDK8中HashMap为什么引入红黑树？"),
    ITEM26(26,"26HashMap中产生Hash冲突时，插入链表头部还是尾部"),//1
    ITEM27(27,"27HashMap中链表和红黑树转换的条件是什么？"),//1
    ITEM28(28,"28为什么大于8链表转红黑树，而不是7或者9"),
    ITEM29(29,"29jdk7并发条件下HashMap存在什么问题"),
    ITEM30(30,"30jdk7HashMap中为什么会产生死循环（链表闭环）,形成的过程是什么"),
    ITEM31(31,"31ConcurrentHashMap相关");
    BaseCollectionArticle(int id,String name){
        Object o;
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
        BaseCollectionArticle[] values = BaseCollectionArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
