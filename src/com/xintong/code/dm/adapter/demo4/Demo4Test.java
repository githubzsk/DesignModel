package com.xintong.code.dm.adapter.demo4;

public class Demo4Test {
    public static void main(String[] args) {
        Animal animal = new AnimalAdapter(new XiaoMing());
        animal.speak();
    }
}
