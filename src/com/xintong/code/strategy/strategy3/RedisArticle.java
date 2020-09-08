package com.xintong.code.strategy.strategy3;

public enum RedisArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1为什么使用缓存？使用缓存会带来什么问题"),//1
    ITEM2(2,"2Redis数据结构"),//1
    ITEM3(3,"3Redis持久化方式"),//1
    ITEM4(4,"4什么是RESP协议"),//1
    ITEM5(5,"5Redis单线程为什么这么快"),//1
    ITEM6(6,"6Redis单线程如何处理客户端并发连接"),//1
    ITEM7(7,"7Redis为什么是单线程，一定是单线程吗？"),//1
    ITEM8(8,"8Redis为什么不适用多线程"),//1
    ITEM9(9,"9什么是IO多路复用"),//1
    ITEM10(10,"10哨兵如何检查Redis实例"),//1
    ITEM11(11,"11哨兵之间如何通讯？"),//1
    ITEM12(12,"12哨兵之间Leader选举机制"),//1
    ITEM13(13,"13哨兵如何选一个Slave作为新的Master"),
    ITEM14(14,"14Redis有哪些架构模式及其特点？"),
    ITEM15(15,"15缓存穿透、缓存击穿、缓存雪崩及其解决方案"),
    ITEM16(16,"16布隆过滤器"),
    ITEM17(17,"17Redis过期策略？内存淘汰机制？"),
    ITEM18(18,"18数据库和缓存双写不一致"),
    ITEM19(19,"19Redis实现分布式锁"),
    ITEM20(20,"20Redis并发竞争问题"),
    ITEM21(21,"21Redis主从原理");
    RedisArticle(int id,String name){
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
        RedisArticle[] values = RedisArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
