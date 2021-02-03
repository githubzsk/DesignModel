package com.xintong.code.dm.strategy.strategy3;

public enum SpringBootArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"1 SpringBoot和Spring区别"),//1
    ITEM2(2,"2 Springboot和springCloud区别"),//1
    ITEM3(3,"3 @SpringBootApplication注解干了些啥?"),//1
    ITEM4(4,"4 @Configuration注解"),//1
    ITEM5(5,"5 @EnableAutoConfiguration注解"),//1
    ITEM6(6,"6 BeanFactoryPostProcessor和BeanPostProcessor");//
    SpringBootArticle(int id,String name){
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
        SpringBootArticle[] values = SpringBootArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
