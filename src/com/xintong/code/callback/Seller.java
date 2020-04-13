package com.xintong.code.callback;

/**
 * @ClassName Seller
 * @Description TODO
 * @Author zsk
 * @Date 2020/1/6 15:10
 * @Version 1.0
 */
public class Seller {
    private String name = null;

    public Seller(String name) {
        // TODO Auto-generated constructor stub
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public class doHomeWork implements DoJob {

        @Override
        public void fillBlank(int a, int b, int result) {
            // TODO Auto-generated method stub
            System.out.println(name + "求助小红算账:" + a + " + " + b + " = " + result + "元");
        }

    }

    public void callHelp(int a, int b) {
        new SuperCalculator().add(a, b, new doHomeWork());
    }
}