# Spring面试题

##### 1 *Spring中事物的理解

Spring中的事务事实上也就是对数据库的事务操作，当然Spring中的事务也有自己的各种传播行为的特点

##### 2 *SpringMVC的请求全流程

##### 3 *Aop解决了什么问题

可以使用Aop把项目里面那些统一的代码比如处理日志，处理异常，提交事务事务回滚什么的统一抽取出来在Aop中实现，代码结构简单，更容易维护，总结起来就是说，Aop可以让我们在不影响原有的功能下，横向的拓展功能

##### 4 * Aop使用场景，Aop原理，失效场景

使用场景：日志、处理异常、处理事务

Aop原理：基于JDK动态代理和CGLib动态代理去实现

Aop失效场景

- ```java
  //在同一个类中两个aop方法嵌套调用会导致 被调用的方法失效
  //原理：导致被调用方法直接由目标对象调用而不是代理对象
  @Service
  public class AServiceImpl implements AService {
      @Transactional
      public void serviceA() {
          System.out.println("serviceA");
          //db
          serviceB();
  
      }
  
      @Transactional
      public void serviceB() {
          System.out.println("serviceB");
          //db
  
      }
  }
  
  ```



##### 5. IOC是什么

IOC （Inversion  of control）是一种思想，直译过来叫做控制反转，使用IOC的话可以达到控制反转和依赖注入，最终的目标可以实现对象之间的解除对象之间的高度耦合关系，比如说A对象需要B对象，如果不使用IOC容器的话，A对象得自己new，使用了IOC容器，A对象把自己创建B对象的这种控制权交给了IOC容器，让IOC容器帮它去new，然后注入到A对象中，从而实现解耦

##### 5. *注入方式

- 构造函数方法注入
- Setter方法注入
- 接口注入

##### 5 *Spring事务失效原因场景

##### 6 *Spring bean生命周期

##### 7 *Spring如何解决bean的循环依赖

##### 9. Spring bean 的注入过程

##### 10. Spring的优点是什么

##### 为什么有了JDK动态代理还要CGLIB

