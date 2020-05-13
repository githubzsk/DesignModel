package com.xintong.code.strategy.strategy3;

public enum BaseArticle implements Article {

    GETINSTANCE(0,""),
    ITEM1(1,"遍历arraylist时候安全删除其中一个元素"),
    ITEM2(2,"集合和数组的区别"),
    ITEM3(3,"为什么要重写hashcode和equals/重写equals为什么一定要重写hashCode"),
    ITEM4(4,"遍历HashMap"),
    ITEM5(5,"对象做HashMap的Key值，需要注意什么"),
    ITEM6(6,"HashMap的理解"),
    ITEM7(7,"hashcode的作用"),
    ITEM8(8,"List实现排序有几种方式？"),
    ITEM9(9,"Comparable 和 Comparator"),
    ITEM10(10,"List和Set"),
    ITEM11(11,"集合排序  arraylist排序"),
    ITEM12(12,"ArrayList去重"),
    ITEM13(13,"为什么要有迭代器"),
    ITEM14(14,"HashMap的hash算法"),
    ITEM15(15,"HashMap结构"),
    ITEM16(16,"HashMap的put方法流程"),
    ITEM17(17,"HashMap的扩容方法"),
    ITEM18(18,"ConcurrentHashMap相关"),
    ITEM19(19,"异常如何设计？"),
    ITEM20(20,"Java的异常类型"),
    ITEM21(21,"线程安全的单例模式"),
    ITEM22(22,"日志怎么打，有什么规范"),
    ITEM23(23,"Object基类常见方法"),
    ITEM24(24,"==和equals"),
    ITEM25(25,"String的equals实现"),
    ITEM26(26,"final关键字"),
    ITEM27(27,"重写重载的区别"),
    ITEM28(28,"final finally finalize区别"),
    ITEM29(29,"String为什么是final的"),
    ITEM30(30,"String   StringBuilder StringBuffer区别"),
    ITEM31(31,"枚举场景"),
    ITEM32(32,"模板模式"),
    ITEM33(33,"项目里用到的设计模式");
    BaseArticle(int id,String name){
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
        BaseArticle[] values = BaseArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
