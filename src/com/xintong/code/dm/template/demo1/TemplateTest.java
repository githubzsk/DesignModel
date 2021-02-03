package com.xintong.code.dm.template.demo1;

public class TemplateTest {

    public static void main(String[] args) {
        AbstractTemplate t = new ConcreteTemplate();
        // 调用模板方法
        t.templateMethod();
    }
}
