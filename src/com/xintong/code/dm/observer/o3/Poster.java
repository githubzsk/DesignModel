package com.xintong.code.dm.observer.o3;

import java.util.ArrayList;
import java.util.List;

public class Poster implements Subject {

    private List<Article> list = new ArrayList<>();
    @Override
    public void registCustom(Article article) {
        list.add(article);
    }

    @Override
    public void notifyCustm(String str) {
        list.forEach(x -> x.notify(str));
    }
}
