package com.xintong.code.dm.adapter.demo1;

public class AdapterTest {
    public static void main(String[] args) {
        Cock cock = new AdapterCock(new SimpleDuck());
        cock.fly();
    }
}
