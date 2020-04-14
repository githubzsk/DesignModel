package com.xintong.code.strategy.strategy3;

public enum DubboArticle implements Article {
    GETINSTANCE(0,""),
    ITEM1(1,"dubbo1"),//√
    ITEM2(2,"dubbo2"),//√
    ITEM3(3,"dubbo3"),//√
    ITEM4(4,"dubbo4");
    DubboArticle(int id,String name){
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
        DubboArticle[] values = DubboArticle.values();
        for (int i = 0; i < values.length; i++) {
            if ( id == values[i].getId()){
                return values[i].getName();
            }
        }
        return "null";
    }
}
