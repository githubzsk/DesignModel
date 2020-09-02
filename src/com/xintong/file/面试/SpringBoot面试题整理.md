# SpringBoot面试题

##### 1 *SpringBoot和Spring区别

Spring的核心是IOC和AOP，IOC可以实现控制反转，可以使我们的对象之间高度解耦，而AOP可以弥补OOP的弊端，可以从切面入手对大量的非业务逻辑的代码统一处理，

而SpringBoot是对Spring框架系列更高层次的封装，Spring能实现的Springboot都能实现，更重要的是SpringBoot的理念是约定大于配置，可以提升开发效率，而实现这种约定大于配置思想的具体实现，也就是SpringBoot的核心自动装配

##### 2 *Springboot和springCloud区别

SpringBoot是对Spring系列高层次封装，使用约定大于配置的思想提升开发效率，可以建立单个微服务

Springcloud基于Springboot，但是他注重的是各个微服务之间的协调配置，并且还整合了许多第三方组件来提供一系列的微服务解决方案，生态很完整

##### 3 @SpringBootApplication注解干了些啥

SpringBootApplication是一个复合注解，集成了SpringBootConfiguration和EnableAutoConfiguration和ComponentScan这几个注解

SpringBootConfiguration直接把他就当做Configuation来对待

ComponentScan这个就不展开讲了，做ssm时候经常配扫描范围

重点主要讲一下EnableAutoConfiguration开启自动装配 参考

##### 4 Configuration注解

**作用**：Configuration主要配合@Bean注解使用，多用于配置文件

**原理**：对于标注了Configuration的类，Spring会基于CGLib对这个类进行动态代理进行增强  ConfigurationClassPostProcessor

**源码**：这里涉及到一个知识点BeanFactoryPostProcessor，BeanFactoryPostProcessor可以拿到BeanFactory在Bean实例化之前对bean的属性进行修改，处理的类实现了BeanFactoryPostProcessor，然后对BeanDefination进行遍历，找到所有标注Configruation的类，然后对这些类使用CGLib进行动态代理对它的方法进行增强，而增强的依据就是看你对应 的方法有没有标注@Bean注解，如果标注了他就给你把该方法的返回值Bean注入到IOC容器里面去

##### 5 EnableAutoConfiguration注解

**作用**： 自动装配

**原理**：SpringBoot基于SPI对需要装配的类做了穷举，然后与依赖匹配，如果存在依赖就会给他进行配置。

**源码**：那我再从源码角度说一下它的实现吧

大体上分两步，第一步匹配，第二步配置，这个是我自己对他的理解总结出来的叫法，网上也没有说法，我就按照我的理解聊一下

1. *匹配* ：EnableAutoConfiguration这个注解重点在于他被一个Import注解标注，Import注解作用是加载并注入对应的类，而他加载的哪个类会去读取META-INF下Spring.factories中所有能够自动装配的类的名字，然后跟你依赖的Jar去做匹配，匹配上了就会做配置

2. *配置*：比如 说它匹配上了redis的配置，那么他就会加载它的配置类RedisAutoConfiguration，或者数据库的配置DataSourceAutoConfiguration，这种类就是SpringBoot给我们提供的内部配置类，

   这种类必然在至少会被两种注解标注，一是Configuration，另一个是什么什么properties，Configuration不用说了就是我们平时用的配置注解，而什么什么properties他会为我们

   引入了一个类，我们在yml或者properties文件中配置事实上就是对这个类进行属性配置，这个也是他加载我们自定义配置的一个原理
   
   另外这种DataSourceAutoConfiguration类他还会使用@Bean注解去注入一些Bean，比如RedisTemplate我们不用自己注入就可以用，但是一般来他还会有ConditionalOnMissingBean的注解，这也就说明了我们如果自己配置了，就使用我们自己配置的类，不会使用它提供的默认的原理



##### 6 BeanFactoryPostProcessor和BeanPostProcessor

BeanFactoryPostProcessor可以拿到BeanFactory在Bean实例化之前对bean的属性进行修改

BeanPostProcessor在Bean初始化之后对bean进行修改

