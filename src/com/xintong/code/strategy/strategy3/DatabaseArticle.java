package com.xintong.code.strategy.strategy3;

public enum DatabaseArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1事务（Transaction）及其ACID属性"),//1
    ITEM2(2,"2数据库锁的分类"),
    ITEM3(3,"3脏读 幻读 可重复读"),//1
    ITEM4(4,"4事务的隔离级别"),//1
    ITEM5(5,"5char和varchar的区别"),//1
    ITEM6(6,"6如何理解索引"),//1
    ITEM7(7,"7Mysql索引类型"),//1
    ITEM8(8,"8为什么InnoDB必须有主键而且最好自增"),//1
    ITEM9(9,"9为什么非主键叶子结点存储主键值"),
    ITEM10(10,"10创建索引的依据"),//1
    ITEM11(11,"11什么情况下索引失效"),//1
    ITEM12(12,"12覆盖索引"),//1
    ITEM13(13,"13数据库性能优化"),//1
    ITEM14(14,"14分表主键怎么设计？"),
    ITEM15(15,"15批量导入MySql1000万条数据，实现思路/大量数据插入数据库速度慢如何优化"),//1
    ITEM16(16,"16数据库乐观锁悲观锁有用过吗，实现？"),//1
    ITEM17(17,"17行锁跟表锁区别 "),//
    ITEM18(18,"18为什么用B+树，不用其他类型"),//1
    ITEM19(19,"19MyiSAM和innodb区别"),//1
    ITEM20(20,"20磁盘读取原理"),
    ITEM21(20,"21left join，right join，inner join"),//1
    ITEM22(21,"21MySQL默认隔离级别，为什么，你们项目用什么？");//1
    DatabaseArticle(int id,String name){
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
        DatabaseArticle[] values = DatabaseArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
