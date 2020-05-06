package com.xintong.code.strategy.strategy3;

public enum RedisArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"为什么使用缓存？使用缓存会带来什么问题"),
    ITEM2(2,"Redis数据结构"),
    ITEM3(3,"Redis持久化方式"),
    ITEM4(4,"什么是RESP协议"),
    ITEM5(5,"Redis单线程为什么这么快"),
    ITEM6(6,"Redis单线程如何处理客户端并发连接"),
    ITEM7(7,"Redis为什么是单线程，一定是单线程吗？"),
    ITEM8(8,"Redis为什么不适用多线程"),
    ITEM9(9,"什么是IO多路复用"),
    ITEM10(10,"哨兵如何检查Redis实例"),
    ITEM11(11,"哨兵之间如何通讯？"),
    ITEM12(12,"哨兵之间Leader选举机制"),
    ITEM13(13,"哨兵如何选一个Slave作为新的Master"),
    ITEM14(14,"Redis有哪些架构模式及其特点？"),
    ITEM15(15," 缓存穿透、缓存击穿、缓存雪崩及其解决方案"),
    ITEM16(16,"布隆过滤器"),
    ITEM17(17,"Redis过期策略？内存淘汰机制？"),
    ITEM18(18,"数据库和缓存双写不一致"),
    ITEM19(19,"Redis实现分布式锁"),
    ITEM20(20,"Redis并发竞争问题");
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
