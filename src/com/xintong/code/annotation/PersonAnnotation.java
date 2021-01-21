package com.xintong.code.annotation;

import java.lang.annotation.*;

/**
 * @Target 决定该注解用于那些Java元素之上 取值如下
 * TYPE 类，接口（包括注解类型）或枚举
 * FIELD 属性
 * METHOD 方法
 * PARAMETER 方法形式参数
 * CONSTRUCTOR 构造方法
 * LOCAL_VARIABLE 局部变量
 * ANNOTATION_TYPE 注解类型
 * PACKAGE 包
 * TYPE_PARAMETER,
 * TYPE_USE;
 */
@Target(value = {ElementType.METHOD})

/**
 * @Retention 决定注解生效的时机
 * SOURCE 用于源码中  不参与编译
 * CLASS 编译器生效
 * RUNTIME 实际开发几乎都是RUNTIME  运行时生效
 */
@Retention(RetentionPolicy.RUNTIME)

/**
 * @Documented
 * 是被用来指定自定义注解是否能随着被定义的java文件生成到JavaDoc文档当中
 */
@Documented

/**
 * @Inherited
 * 如果本注解中使用了Inherited，那么使用该注解的类，如果被别的类继承，那么别的类也拥有该注解
 * 例如:  Father类使用@Inherited标注， Xiaoming extends Father ，那么Xiaoming这个类也是被@Inherited标注的
 */
@Inherited
public @interface PersonAnnotation {

    String name() default "root";

    int age() default 18;

    boolean isMan() default true;
}
