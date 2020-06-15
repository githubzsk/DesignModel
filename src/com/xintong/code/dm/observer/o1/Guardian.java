package com.xintong.code.dm.observer.o1;

/**
 * 环球卫报
 */
public class Guardian implements Observer {
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("queen")){
            System.out.println("Yet another news in London... " + tweet);
        }
    }
}
