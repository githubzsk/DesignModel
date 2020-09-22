package com.xintong.code.strategy.strategy3;

public enum MyBatisArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"#{}和${}的区别是什么"),//1
    ITEM2(2,"MyBatis和Jpa/Hibernate/Spring data jpa的区别"),//1
    ITEM3(3,"Mybatis 中一级缓存与二级缓存的区别？"),//1
    ITEM4(4,"MyBatis动态sql执行原理"),//1
    ITEM5(5,"MyBatis分页"),//1
    ITEM6(6,"当实体类中的属性名和表中的字段名不一样，如果将查询的结果封装到指定pojo"),//1
    ITEM7(7,"在mapper中如何传递多个参数？"),//1
    ITEM8(8,"resultType resultMap的区别？"),//1
    ITEM9(9,"如何获取自动生成的(主)键值?"),//1
    ITEM10(10,"一对一、一对多的关联查询 ？"),//1
    ITEM11(11,"MyBatis没有实现类如何运行？");//1

    MyBatisArticle(int id,String name){
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

    @Override
    public String getMessage(int id) {
        MyBatisArticle[] values = MyBatisArticle.values();

        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return id+":"+values[i].getName();
            }
        }
        return "null";
    }
}
