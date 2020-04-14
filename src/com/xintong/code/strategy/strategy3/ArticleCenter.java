package com.xintong.code.strategy.strategy3;

public class ArticleCenter {
    private Article article;

    public ArticleCenter(Article article){
        this.article = article;
    }

    public String getMessage(int id){
        return this.article.getMessage(id);
    }
}
