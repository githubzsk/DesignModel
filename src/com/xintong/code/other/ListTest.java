package com.xintong.code.other;

import java.util.*;

public class ListTest {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("f");
        list.add("h");
        HashMap map = new HashMap();
        map.put("","");
  //      list.stream().map(x ->x+" ").distinct().forEach(System.out::print);

        HashSet<String> strings = new HashSet<>(list);
        strings.stream().map(x ->x+" ").forEach(System.out::print);

    }
}
