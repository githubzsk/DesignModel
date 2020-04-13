package com.xintong.code.temp;

import java.util.*;

public class G1GCTest {
    public static void main(String[] args) {
        List<Demo> list = new ArrayList<>();
        list.add(new Demo(9,"name9"));
        list.add(new Demo(4,"name9"));
        list.add(new Demo(93,"name9"));
        list.add(new Demo(33,"name9"));
        list.add(new Demo(25,"name9"));
        list.add(new Demo(8,"name9"));
        Collections.sort(list, new Comparator<Demo>() {
            @Override
            public int compare(Demo o1, Demo o2) {
                return o1.getId()-o2.getId();
            }
        });
        list.stream().forEach(System.out::println);
    }
}
class Demo{
    private int id;
    private String name;
    public Demo(int id,String name){
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Demo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
