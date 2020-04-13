package com.xintong.code.temp;

public enum Item {
    ITEM1(1,"垃圾回收过程"),//√
    ITEM2(2,"new对象的过程"),//√
    ITEM3(3,"对象的构成"),//√
    ITEM4(4,"描述jvm内存结构"),
    ITEM5(5,"解释一下方法区、永久代、元空间"),
    ITEM6(6,"如何判断一个对象是否可以被收回"),//√
    ITEM7(7,"什么对象可作为GC Roots对象"),
    ITEM8(8,"常用的垃圾收集算法"),//√
    ITEM9(9,"为什么年轻代使用复制算法而老年代使用标记整理/标记清除"),//√
    Item10(10,"空间分配担保机制"),//√
    Item11(11,"对象分配规则"),
    Item12(12,"垃圾收集器分类"),
    Item13(13,"G1和CMS的区别"),//√
    Item14(14,"方法区会被回收吗？"),
    Item15(15,"内存溢出"),
    Item16(16,"内存泄漏"),//√
    Item17(17,"四种引用类型"),
    Item18(18,"对象的定位方式有哪些"),//√
    Item19(19,"什么是STW，为什么要STW"),
    Item20(20,"什么是类加载机制"),//√
    Item21(21,"类加载过程"),//√
    Item22(22,"说说你了解的类加载器"),
    Item23(23,"什么是双亲委派模型"),//√
    Item24(24,"为什么使用双亲委派模型？"),
    Item25(25,"打破双亲委派模型"),
    Item26(26,"什么是SPI机制");
    private Item(int id,String name){
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public  int getId() {
        return id;
    }
    private String name;
    private int id;
    public static String getMessage(int id){
        Item[] values = Item.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
