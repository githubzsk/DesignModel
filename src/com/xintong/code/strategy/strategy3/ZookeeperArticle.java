package com.xintong.code.strategy.strategy3;

public enum  ZookeeperArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1 说说你对zookeeper的理解?"),//1
    ITEM2(2,"2 为什么zookeeper能作为注册中心"),//1
    ITEM3(3,"3 zookeeper节点类型"),//
    ITEM4(4,"4 zookeeper临时节点实现原理"),//1
    ITEM5(5,"5 zookeeper角色"),//1
    ITEM6(6,"6 Leader选举"),//
    ITEM7(7,"7 zookeeper集群为什么要搭建奇数台"),//1
    ITEM8(8,"8 Zookeeper的Watch机制原理"),//
    ITEM9(9,"9 zookeeper节点的4种状态"),//1
    ITEM10(10,"10 ZAB协议"),//
    ITEM11(11,"11 数据一致性原理"),//
    ITEM12(12,"12 事务ID zxid"),//1
    ITEM13(13,"13 数据同步");//
    ZookeeperArticle(int id,String name){
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
        ZookeeperArticle[] values = ZookeeperArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
