package com.xintong.code.other;

import com.sun.scenario.effect.impl.sw.java.JSWRendererDelegate;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.*;

public class ListTest {
    public static void main(String[] args) {
        StackOverflowError error;
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        list.add("e");
        list.add("f");
        list.add("f");
        list.add("h");
       Integer.parseInt("5");
        Object object;
        HashMap map = new HashMap();
        map.put("","");
  //      list.stream().map(x ->x+" ").distinct().forEach(System.out::print);
        File file = new File("/cats");
        FileNotFoundException exception;
        try {
            new RandomAccessFile("","");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Class.forName("");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(file);
        HashSet<String> strings = new HashSet<>(list);
        strings.stream().map(x ->x+" ").forEach(System.out::print);

    }
}
