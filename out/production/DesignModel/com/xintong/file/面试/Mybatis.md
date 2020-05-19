# MyBatis常见面试题

##### 1. #{}和${}的区别是什么？

1. #{} 采用预编译，使用PrepareStatment来处理，可以有效地防止Sql注入
2. ${} 直接就是字符换替换，比如要写order by/group by什么的就用${}

##### 2. MyBatis和Jpa/Hibernate/Spring data jpa的区别

Hibernate是一种标准的ORM （Object Relational Mapping）对象关系映射框架，复杂度高，全自动可以通过操作对象实现对数据库的操作

而MyBatis是一种不完全ORM对象关系映射，使用相对于原生的Hibernate来说简单，使用上不像Hibernate，需要自己写sql去实现

对于表关系复杂度不高的项目，也就是单表比较多的项目我们可以选择Jpa，从而快速搭建持久层

表关系复杂点的项目我们就可以选择Mybatis，灵活性高，可控性高

##### 3. Mybatis 中一级缓存与二级缓存的区别？

一级缓存默认开启，是sqlSession层面，而二级缓存可以跨session但是二级缓存比较鸡肋，项目中没有用过，如果需要使用缓存的话我们一般是直接上redis

##### 4.MyBatis动态sql执行原理

rim|where|set|foreach|if|choose|when|otherwise|bind

iif where when otherwise foreach，是基于OGNL实现

##### 5. MyBatis分页

MyBatis可以使用RowBounds对象分页，也可以使用pagehelper，也可以自己手动写limit语句

##### 6.当实体类中的属性名和表中的字段名不一样，如果将查询的结果封装到指定pojo

1. 使用resultmap配置数据库字段和实体属性对应关系
2. 写sql用别名

##### 7. 在mapper中如何传递多个参数？

1. 直接在方法中传递参数，xml文件用#{0} #{1}来获取
2. 使用 @param 注解:这样可以直接在xml文件中通过#{name}来获取

##### 8. resultType resultMap的区别？

当数据库字段和实体属性完全相同的时候，可以直接使用resultType，不同的时候可以写Resultmap进行对应,或者配置一对多关系的时候也可以写ResultMap		

##### 9. 如何获取自动生成的(主)键值?

使用usegeneratedkeys为true，再指定一下主键是啥

```xml

<insert id=”insertname” usegeneratedkeys=”true” keyproperty=”id”>
     insert into names (name) values (#{name})
</insert>
```

##### 10. 一对一、一对多的关联查询 ？

一对一再ResultMap中使用association 

一对多再ResultMap中使用Collection

##### 11. MyBatis没有实现类如何运行？

是基于JDK的动态代理创建出一个代理的实现类去操作的。



 