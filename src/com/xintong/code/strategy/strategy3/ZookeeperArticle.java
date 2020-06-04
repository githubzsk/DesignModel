package com.xintong.code.strategy.strategy3;

public enum  ZookeeperArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1说说你对zookeeper的理解?"),
    ITEM2(2,"2为什么zookeeper能作为注册中心"),//1
    ITEM3(3,"3zookeeper节点类型"),//1
    ITEM4(4,"4zookeeper临时节点实现原理"),//1
    ITEM5(5,"5zookeeper角色"),//1
    ITEM6(6,"6Leader选举"),//1
    ITEM7(7,"7zookeeper集群为什么要搭建奇数台"),//
    ITEM8(8,"8Zookeeper的Watch机制原理"),//1
    ITEM9(9,"9zookeeper节点的4种状态");//1
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
