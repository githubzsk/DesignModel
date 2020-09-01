package com.xintong.code.strategy.strategy3;

public enum SpringArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1Spring中事物的理解"),//1
    ITEM2(2,"2SpringMVC的请求全流程"),//1
    ITEM3(3,"3Aop解决了什么问题"),//1
    ITEM4(4,"4Aop使用场景，Aop原理，失效场景"),//1
    ITEM5(5,"5IOC是什么"),//1
    ITEM6(6,"6注入方式 "),//1
    ITEM7(7,"7自动装配"),//1
    ITEM8(8,"8Spring bean生命周期"),//1
    ITEM9(9,"9Spring如何解决bean的循环依赖"),//1
    ITEM10(10,"10Spring bean 的注入过程"),//1
    ITEM11(11,"11 Spring的优点是什么"),//1
    ITEM12(12,"12 为什么有了JDK动态代理还要CGLIB"),//1
    ITEM13(13,"13 Spring 框架中都用到了哪些设计模式"),
    ITEM14(14,"14 Spring事务实现原理"),//1
    ITEM15(15,"15 Spring事务失效原因场景"),//1
    ITEM16(16,"16 Spring事务的种类"),//1
    ITEM17(17,"17 spring的事务传播行为"),//1
    ITEM18(18,"18 Spring中的隔离级别"),//1
    ITEM19(19,"19 Spring支持的几种bean的作用域"),//1
    ITEM20(20,"20 BeanFactory 和FactoryBean"),//1
    ITEM21(21,"21 BeanFactory和ApplicationContext");//1
    SpringArticle(int id,String name){
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
        SpringArticle[] values = SpringArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
