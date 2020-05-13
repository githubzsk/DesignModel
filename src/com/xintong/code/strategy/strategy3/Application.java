package com.xintong.code.strategy.strategy3;

public class Application {
    public static void main(String[] args) {
        ArticleCenter center = new ArticleCenter( BaseArticle.GETINSTANCE);
        String message = center.getMessage((int) (Math.random() * BaseArticle.values().length+1));
        System.out.println(message);

    }
}
