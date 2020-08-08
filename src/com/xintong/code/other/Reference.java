package com.xintong.code.other;

import java.lang.ref.SoftReference;

public class Reference {
    public static void main(String[] args) {
        SoftReference<String> reference = new SoftReference<>("aaa");
        System.out.println(reference.get());
    }
}
