package com.xintong.code.strategy.strategy3;

public enum  ZookeeperArticle implements Article {
    GETINSTANCE(0,"");
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
