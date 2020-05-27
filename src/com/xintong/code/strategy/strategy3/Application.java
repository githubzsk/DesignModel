package com.xintong.code.strategy.strategy3;

public class Application {
    public static void main(String[] args) {
        ArticleCenter center = new ArticleCenter( BaseCollectionArticle.GETINSTANCE);
        String message = center.getMessage((int) (Math.random() * BaseCollectionArticle.values().length+1));
        System.out.println(message);

    }
}
