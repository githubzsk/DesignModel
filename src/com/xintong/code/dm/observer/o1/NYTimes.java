package com.xintong.code.dm.observer.o1;

/**
 * 纽约时报
 */
public class NYTimes implements Observer {
    public void notify(String tweet) {
        if(tweet != null && tweet.contains("money")){
            System.out.println("Breaking news in NY! " + tweet);
        }
    }
}
