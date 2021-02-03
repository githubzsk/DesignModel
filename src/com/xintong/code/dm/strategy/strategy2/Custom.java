package com.xintong.code.dm.strategy.strategy2;

/**
 * @ClassName Custom
 * @Description TODO
 * @Author zsk
 * @Date 2019/12/31 13:14
 * @Version 1.0
 */
public class Custom {

//
    public static void main(String[] args) {
//        Context context = new Context(new Mysql());//
//        context.doConn();
       // Executors.newCachedThreadPool();
        String s1 = "abc";
        String s2 = "abc";
        String s3= new String("abc");

        System.out.println("s1 == s2 ? "+(s1==s2));
        System.out.println("s1 == s3 ? "+(s1==s3));
        System.out.println("s1 equals s3 ? "+(s1.equals(s3)));
    }
}
