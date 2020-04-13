package com.xintong.code.temp;

public class Exec {
    public static void main(String[] args) {
        double random = Math.random();
        String message = Item.getMessage((int) (random * 26));
        System.out.println(message);
    }
}
