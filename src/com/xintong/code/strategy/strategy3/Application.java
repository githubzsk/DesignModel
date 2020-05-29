package com.xintong.code.strategy.strategy3;

import java.util.HashMap;

public class Application {
    public static void main(String[] args) {
        ArticleCenter center = new ArticleCenter( DatabaseArticle.GETINSTANCE);
        String message = center.getMessage((int) (Math.random() * DatabaseArticle.values().length+1));
        System.out.println(message);

        HashMap map = new HashMap();
        map.put("","");

    }
}
