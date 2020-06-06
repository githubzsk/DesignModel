package com.xintong.code.strategy.strategy3;

public enum SpringCloudArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1 EurekaServer"),//
    ITEM2(2,"2 EurekaClient"),//
    ITEM3(3,"3 Eureka"),//
    ITEM4(4,"4 EurekaServer缓存机制"),//
    ITEM5(5,"5 注册表"),//
    ITEM6(6,"6 Eureka高可用原理"),//
    ITEM7(7,"7 Eureka AP原理"),//
    ITEM8(8,"8 Eureka服务注册原理"),//1
    ITEM9(9,"9 eureka和zookeeper作为注册中心的区别"),//
    ITEM10(10,"10 Eureka自我保护机制"),//
    ITEM11(11,"11 主要组件以及如何运用"),//
    ITEM12(12,"12 Hystrix降级方法"),//
    ITEM13(13,"13 SpringCloud和Dubbo区别"),//
    ITEM14(14,"14 Ribbon负载均衡策略"),//
    ITEM15(15,"15 服务扇出");//
//    ITEM16(16,"16 数据库乐观锁悲观锁有用过吗，实现？"),
//    ITEM17(17,"17 行锁跟表锁区别 "),//1
//    ITEM18(18,"18 为什么用B+树，不用其他类型"),//1
//    ITEM19(19,"19 MyiSAM和innodb区别"),//1
//    ITEM20(20,"20 磁盘读取原理"),//
//    ITEM21(20,"21 left join，right join，inner join");
    SpringCloudArticle(int id,String name){
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
        SpringCloudArticle[] values = SpringCloudArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
