package com.xintong.code.other;

import java.util.*;

public class ListTest {
    public static void main(String[] args) {
        Set<String> set = new HashSet<>();
        set.add("hah哈哈");
        set.add("88");
        set.add("bvcbv");
        set.add("sdsd"); set.add("你好");
        set.add("ads");
        Iterator<String> iterator = set.iterator();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }

    }
}
