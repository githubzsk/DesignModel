package com.xintong.code.strategy.strategy3;

public enum DatabaseArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"事务（Transaction）及其ACID属性"),
    ITEM2(2,"数据库锁的分类"),
    ITEM3(3," 脏读 幻读 可重复读"),
    ITEM4(4,"事务的隔离级别"),
    ITEM5(5,"char和varchar的区别"),
    ITEM6(6,"如何理解索引"),
    ITEM7(7,"Mysql索引类型"),
    ITEM8(8,"为什么InnoDB必须有主键而且最好自增"),
    ITEM9(9,"为什么非主键叶子结点存储主键值"),
    ITEM10(10,"创建索引的依据"),
    ITEM11(11,"什么情况下索引失效"),
    ITEM12(12,"覆盖索引"),
    ITEM13(13,"数据库性能优化"),
    ITEM14(14,"分表主键怎么设计？"),
    ITEM15(15,"批量导入MySql1000万条数据，实现思路/大量数据插入数据库速度慢如何优化"),
    ITEM16(16,"数据库乐观锁悲观锁有用过吗，实现？"),
    ITEM17(17,"行锁跟表锁区别 "),
    ITEM18(18,"为什么用B+树，不用其他类型"),
    ITEM19(19,"MyiSAM和innodb区别"),
    ITEM20(20,"磁盘读取原理");
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
