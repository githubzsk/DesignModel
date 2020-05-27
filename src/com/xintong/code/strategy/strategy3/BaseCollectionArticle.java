package com.xintong.code.strategy.strategy3;

public enum BaseCollectionArticle implements Article {

    GETINSTANCE(0,""),
    ITEM1(1,"集合的分类"),
    ITEM2(2,"集合和数组的区别"),
    ITEM3(3,"Comparable 和 Comparator"),
    ITEM4(4,"Vector & ArrayList "),
    ITEM5(5,"ArrayList & LinkedList"),
    ITEM6(6,"List和Set"),
    ITEM7(7,"遍历集合删除元素"),
    ITEM8(8,"Arraylist排序"),
    ITEM9(9,"ArrayList去重"),
    ITEM10(10,"List和Set"),
    ITEM11(11,"集合排序  arraylist排序"),
    ITEM12(12,"ArrayList去重"),
    ITEM13(13,"为什么要有迭代器"),
    ITEM14(14,"为什么要重写hashcode和equals/重写equals为什么一定要重写hashCode"),
    ITEM15(15,"遍历HashMap"),
    ITEM16(16,"对象做HashMap的Key值，需要注意什么？"),
    ITEM17(17,"HashMap的理解"),
    ITEM18(18,"hashcode的作用"),
    ITEM19(19,"HashMap的hash算法"),
    ITEM20(20," HashMap结构"),
    ITEM21(21,"HashMap的put方法流程"),
    ITEM22(22,"HashMap的resize()扩容方法"),
    ITEM23(23,"HashMap长度为什么2的n次方"),
    ITEM24(24,"HashMap的默认负载因子为什么是0.75，初始容量为什么是16"),
    ITEM25(25,"JDK8中HashMap为什么引入红黑树？"),
    ITEM26(26,"HashMap中产生Hash冲突时，插入链表头部还是尾部"),
    ITEM27(27,"HashMap中链表和红黑树转换的条件是什么？"),
    ITEM28(28,"为什么大于8链表转红黑树，而不是7或者9"),
    ITEM29(29,"jdk7并发条件下HashMap存在什么问题"),
    ITEM30(30,"jdk7HashMap中为什么会产生死循环（链表闭环）,形成的过程是什么"),
    ITEM31(31,"ConcurrentHashMap相关");
    BaseCollectionArticle(int id,String name){
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
