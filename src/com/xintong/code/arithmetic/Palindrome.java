package com.xintong.code.arithmetic;

import com.sun.javaws.security.AppContextUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 回文检索
 */
public class Palindrome {
    public static void main(String[] args) {
        testNum();
    }
    public static void testNum(){
        int a,b,c;// 个/十/百
        for(int i = 100; i <= 1000; i++){
            a = i%10;
            b = i/10%10;
            c = i/10/10%10;
            if(a*a*a+b*b*b+c*c*c == i){
                System.out.println(i);
            }
        }
    }

}
