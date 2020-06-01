package com.xintong.code.strategy.strategy3;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

public class Application {
   static Semaphore semaphore = new Semaphore(5);
    public static void main(String[] args) throws InterruptedException {
        semaphore.acquire();
        semaphore.release();
        ArticleCenter center = new ArticleCenter( JMMArticle.GETINSTANCE);
        String message = center.getMessage((int) (Math.random() * JMMArticle.values().length+1));
        System.out.println(message);

        HashMap map = new HashMap();
        map.put("","");

    }
}
