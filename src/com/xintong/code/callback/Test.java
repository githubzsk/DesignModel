package com.xintong.code.callback;

/**
 * @ClassName Test
 * @Description TODO
 * @Author zsk
 * @Date 2020/1/6 15:11
 * @Version 1.0
 */
public class Test {
    public static void main(String[] args) {
        Student s1 = new Student("小明");
        Seller s2 = new Seller("老婆婆");
        int a = 56;
        int b = 31;
        int c = 26497;//
        int d = 11256;
        s1.callHelp(a, b);
        s2.callHelp(c, d);
    }
}
