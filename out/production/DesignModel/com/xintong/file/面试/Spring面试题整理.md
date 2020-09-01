# Spring面试题

##### 1 *Spring中事物的理解

Spring中的事务事实上也就是对数据库的事务操作，当然Spring中的事务也有自己的各种传播行为的特点

##### 2 *SpringMVC的请求全流程

SpringMVC的核心是DispatchServlet，既然是一个Servlet，那么他必然有init方法以及service方法，事实上他的核心流程就他的service方法里面处理的，但是在说service的核心流程之前，我觉得我有必要说一下他的init方法，因为他的init方法初始化了一些核心流程所需要的数据

实际上DispatcherServlet这个类中直接重写init方法，是在他的某一层父类中种重写的init，但是父类中的init方法经过一系列的调用，调用到了DispatcherServlet中的一个init开头的一个方法，这个方法完成了DispatcherServlet的初始化，初始化的内容很多，这里我只说重点，	比如说

```java
//初始化handlerMapping处理器映射器
initHandlerMappings(context);
//初始化handlerAdaptcher参数适配器
initHandlerAdapters(context);
//初始化viewResolvers视图转换器
initViewResolvers(context);
```

初始化它主要就干这些事情

初始化完成之后，当来了Http请求的时候，便执行他的service方法，service方法的核心是一个叫做doDispatch的方法，具体请求流程他是这里开始

1. 通过request请求中解析出url，然后通过url在Handlermapping中寻找对于的Controller，在源码层面叫做handle处理器，实际上在HandlerMapping中用一个map来保存了url与controller的对应关系，具体来说是LinkedHashMap，然后将handle封装成一个HandlerExecutionChain处理器执行链返回，注意这里并没有直接返回一个处理器
2. 拿到这个HandleExecutionChain之后会进行判断，如果说你这个执行链为空，也就是没找到对应处理器，那么他直接return，说白了就是就是我们常见的404，如果不为空，那么继续往下走
3. 他会从HandlerExecutionChain中拿出handle，调用特定的方法寻找一个合适的处理器适配器，注意我说这里只是寻找出来，并没有使用HandleAdapter去执行。
4. 我们都知道，mvc中有拦截器，拦截器中有前置处理器后置处理器，接下来就是我们写的拦截器的前置处理器作用的时候了，如果不符合，在这里直接return掉，如果符合，继续往下走
5. 这个时候调用处理器适配器HandleAdapter去处理HandlerExecutionChain中的handle，处理完成之后会返回一个ModelAndView
6. 接着调用拦截器的后置处理器的业务逻辑可能会对ModelAndView进行处理
7. 然后在调用ViewResolver对ModelAndView进行处理，处理完成之后返回一个View
8. 接下来就是对这个View进行渲染以及进行response

##### 3 *Aop解决了什么问题

可以使用Aop把项目里面那些统一的代码比如处理日志，处理异常，提交事务事务回滚什么的统一抽取出来在Aop中实现，代码结构简单，更容易维护，

// 总结起来就是说，Aop可以让我们在不影响原有的功能下，横向的拓展功能

说白话就是吧功能相同的代码抽离出来，在通过理的这种形式把这些代码织入到需要织入的地方，实现功能增强

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
  
  //解决
  	@Transactional
      public void serviceA() {
          System.out.println("serviceA");
          //db
          //获取Aop代理上下文获取当前代理对象，通过代理对象强制代理
          AService aService = (AService)AopContext.currentProxy();
          aService.serviceB();
  
      }
  
  ```



##### 5. IOC是什么

IOC本身是一种控制反转的思想，而具体到我们Java 或者说Spring中来说的话，可以理解为Spring的IOC容器，这个容器给我们提供了创建并管理对象的功能，我们不用处理对象之间错综复杂的依赖关系，只需要告诉IOC容器在初始化的时候帮我加载一些我要用的bean，用的时候直接从IOC容器中拿

##### 6. *注入方式

- 构造函数方法注入 xml中  constructor-arg
- Setter方法注入 xml中  property 
- 接口注入
- 静态工厂
- 实例工厂

##### 7. 自动装配

@Autowired，@Resource，@Inject，@Qualifier，@Named

Autowired（spring） 默认按照byType

Resource（javaee） 默认按照byName

Inject（javaee）默认按照byType

##### 9 *Spring bean生命周期

大致分为四个步骤

1. 实例化
2. 属性赋值
3. 初始化
4. 销毁

讲这些步骤之前，说一个额外知识点，spring容器在启动的时候，首先它会创建一个beanFactory，具体的实现叫做DefaultListableBeanFactory，还会去加载解析资源文件把所有的需要注入的bean信息采集过来封装成一个个beanDefination，存储到一个ConcurrentHashMap当中，这是容器刚开始启动要做的事儿，容器启动到尾声的时候，他才会去初始化一个个单例bean，spring对外提供了getBean的方法供我们获取bean，事实上在spring内部也是通过getBean来开始产生一个个bean的

 getBean -> doGetBean ->createBean ->doCreateBean到这个doCreateBean方法的时候，springBean的生命周期正式开始

1. 首先他会进行实例化，实例化的时候调用了一个叫做createBeanInstance的方法，这个方法通过反射机制，然后去创建出一个BeanWrapper对象，注意这个时候他只是一个bean的包装对象，而且是个空壳子，里面啥都没有

   接下来会使用beanpostprocesser处理beanDefination还有进行缓存等等业务，我就不展开讲了

2. 实例化之后会进行属性赋值，这一步调用了populateBean方法，这个方法调用栈很深，里面还会去递归的调用getBean方法解决对象之间的依赖问题，事实上bean之间的循环依赖，就是populateBean再加上三级缓存完成的

3. 赋完值之后进行初始化，初始化的对应方法叫做initializeBean，也在这个doCreateBean方法内部，而这个初始化话中细分的话步骤还是比较多的

   - ```java
     //如果实现了Aware系列接口，首先他会处理这些 BeanNameAware BeanFactoryAware MessageResourceAware 等等
     if (System.getSecurityManager() != null) {
     			AccessController.doPrivileged((PrivilegedAction<Object>) () -> {
     				invokeAwareMethods(beanName, bean);
     				return null;
     			}, getAccessControlContext());
     		}
     		else {
     			invokeAwareMethods(beanName, bean);
     		}
     //处理完Aware系列的问题，接着他会去处理BeanPostProcessorBefore
     //至于BeanPostProcessor是Spring预留给我们的接口，我么可以利用这个接口启动容器的时候对bean进行
     //特殊的修改，如果你需要的话
     		Object wrappedBean = bean;
     		if (mbd == null || !mbd.isSynthetic()) {
     			wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
     		}
     //接着处理init方法相关的事情
     		try {
     			invokeInitMethods(beanName, wrappedBean, mbd);
     		}
     		catch (Throwable ex) {
     			throw new BeanCreationException(
     					(mbd != null ? mbd.getResourceDescription() : null),
     					beanName, "Invocation of init method failed", ex);
     		}
     //之后会去处理BeanPostProcessorAfter
     		if (mbd == null || !mbd.isSynthetic()) {
     			wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
     		}
     //刚说的这几步都是在initializeBean方法内部去执行的，也就是说这几部都属于初始化
     ```

4. 初始化完成之后会进行销毁的方法回调注册，注意这里只是注册，并不是销毁，销毁的时候会回调

   这个步骤完成之后，bean就可以对外提供正常使用了

   如果说bean要销毁的话，如果说你的bean实现了DisposableBean  /dɪˈspəʊzəbl/  接口，那么会执行你重写的destory方法，如果说你没实现这个接口，那么久执行你指定的destory方法，如果没有指定，啥都不做

##### 10 *Spring如何解决bean的循环依赖

spring容器在启动的时候，首先它会创建一个beanFactory，具体的实现叫做DefaultListableBeanFactory，还会去加载解析资源文件把所有的需要注入的bean信息采集过来封装成一个个beanDefination，存储到一个ConcurrentHashMap当中，这是容器刚开始启动要做的事儿，容器启动到尾声的时候，他才会去初始化一个个单例bean，spring对外提供了getBean的方法供我们获取bean，事实上在spring内部也是通过getBean来开始产生一个个bean的。

spring 解决循环的依赖的所有代码都在getBean之中，具体是基于bean三级缓存和getBean中至少三处对于这个三级缓存操作的地方再加上一个populateBean这样一个方法来实现的

我先说一下三级缓存

```java
// singletonObjects 单例缓存池
// earlySingletonObjects 早期提前暴露的对象缓存
// singletonFactories 单例对象工厂缓存
```

三个地方：两处doGetBean中，一处doCreateBean中刚创建完Bean外壳之后，

populateBean：给bean注入属性的方法，位于doGetBean中，调用栈很深会递归调用getBean

网上几乎绝大多数都是基于这三级缓存去讲，如果我也按照这样讲的话可能会听着有点绕，我呢可以从对getBean的宏观认识上，从另外一个角度去说一下处理循环依赖的问题，事实上它是基于递归的思想解决的

比如说有三个对象 A 依赖 B 依赖 C 依赖 A，我们从A入手

getBean（A） 实例化A  缓存A  populateBeanA 的时候发现了 B 

getBean（B） 实例化B  缓存A populateBeanB 的时候发现了 C 

getBean（C） 实例化C  缓存A populateBean 的时候发现了 A

直接缓存中获取A 完成 populateBean C，完成实例化 C

完成 populateBean B ，完成 populateBean A

至此，解决完毕

##### 11. Spring bean 的注入过程

getBean走一波

##### 12. Spring的优点是什么

1. 首先它是一个非侵入性的，就意思你不用去实现它的接口或者继承他的类，当然你要是去实现特殊功能的bean，比如像实现spring的Aware系列接口去获取Spring提供的资源，又或者实现BeanPostProcessor之类的接口去实现特殊功能，这种情况我们不做讨论
2. 第二呢就是他的核心之一IOC，他的ioc可以让我们代码之间的耦合度降到最低
3. 第三就是他的Aop，打破OOP的限制，可以从切面入手统一实现功能增强
4. 对事务操作有很好的支持
5. 与其他框架很容易集成
6. 生态圈大，基本上提供了一站式解决方案

##### 13. 为什么有了JDK动态代理还要CGLIB

jdk动态代理不支持类的代理，必须要你去实现接口

##### 14. Spring 框架中都用到了哪些设计模式？ 

（1）工厂模式：BeanFactory就是简单工厂模式的体现，用来创建对象的实例；

（2）单例模式：Bean默认为单例模式。

（3）代理模式：Spring的AOP功能用到了JDK的动态代理和CGLIB字节码生成技术；

（4）模板方法：用来解决代码重复的问题。比如. RestTemplate, JmsTemplate, JpaTemplate。

##### 15. Spring事务实现原理

##### 16 *Spring事务失效原因场景

1. 使用类

   - 非public方法会失效 如果走cglib动态代理，导致非public访问不到

   - 被final修饰  导致cglib无法生成代理类集成该类，也会失效

   - 通类中A调用B，A没有事务B有事务，那么B也会失效

     原理是：事务基于Aop，Aop基于动态代理，这样调用的话，B方法相当于没走代理对象，所以失效

2. 配置错误失效

   - 使用了support  结果外围没事务，该方法也不走事务
   - 使用了not_support 不管外层是否有事务，结果以非事务运行
   - 使用了never
   -  ssm项目 配置 context:component-scan包扫描重复扫描

##### 17. Spring事务的种类

支持声明式事务和编程式事务，更多的时候用于声明式事务使用注解搞定

声明式事务 优点是基于注解，对代码没有侵入性，使用简单，缺点 最小粒度方法级别

编程式事务 优点 可以细粒度到代码块级别，缺点 具有侵入性

##### 18. spring的事务传播行为

required        required_new 

supports 	  not_supports

mandatory    never

nested

事务传播行为指的是 事务方法被嵌套进另一个方法的时候，应该如何传播

通过Transactional注解的propagation  /ˌprɒpəˈɡeɪʃn/设置传播行为

```java
@Transactional(propagation = Propagation.REQUIRED)
    REQUIRED(0),
	// 支持外围事务，如果当前没有事务，就新建一个事务，这是最常见的选择
	//1. 比如说A没事务调用B B传播行为是REQUIRED，那么A中出现的异常与B无关，B新建自己的事务，当然如果说你B
	//   中自己抛异常了，那么肯定事务回滚
	//2  比如说A有事务调用B B传播行为是REQUIRED，那么便会加入A的事务之中（内外事务形成整体）
	//   任意一个事务出现异常都会回滚
    SUPPORTS(1),
	// 支持外围事务，或者说跟随外围事务，外围有则有，外围没有则没有
    MANDATORY(2),// /ˈmændətəri;/ 强制的
	// 强制开启事务
    REQUIRES_NEW(3),
	// 不管你外围方法有没有开始事务，内部方法始终都会新建自己的事务，不会跟你的外围事务产生关联
	// 比如说A调用B，不管你A有没有事务，我B都会新建自己的事务，A中的异常和B无关，
	// 当然B自己出现了异常回滚如果处理好了不会被外围感知，如果处理抛出来了肯定会影响A
    NOT_SUPPORTED(4),
	// 不支持外围事务而且自身以非事务形式运行，也就是说不管你外围有没有事务，内层都是非事务
    NEVER(5),
	// 禁止外围方法开启事务，一旦外围开启事务便抛异常
    NESTED(6); /'nestɪd/ 嵌套事务
	// 外围方法没有开启事务，内部方法则新建自己的事务，如果外围方法开启事务，一旦外围方法回滚，内部方法也回滚
```

##### 18. Spring中的隔离级别

通过Transactional注解的isolation  /ˌaɪsəˈleɪʃn/设置隔离级别

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
DEFAULT(-1),//默认使用数据库的隔离级别
READ_UNCOMMITTED(1),//读已提交
READ_COMMITTED(2),//读未提交
REPEATABLE_READ(4),//可重复读
SERIALIZABLE(8);//串行化
```

##### 19 Spring支持的几种bean的作用域

使用@Scope()注解配置

1. singleton 单例默认
2. prototype  多例
3. request 一个请求期间
4. session 一个会话期间
5. global-session

##### 20.BeanFactory 和FactoryBean

BeanFactory是bean工厂，在spring中的具体实现是DefaultListableBeanFactory，内部持有了一个ConcurrentHashMap中存了所有的beanDefination

而FactoryBean是一个bean，一个特殊的bean，利用FactoryBean的id，getBean出来并不是FactoryBean本身，而是FactoryBean的getObject方法返回的对象，如果你想要FactoryBean本身，在id前加&

FactoryBean具体应用 MyBatis的SQLSessionFactory，但是我们并不需要直接操作它，我们可以通过SqlSessionFactoryBean来获取 SQLSessionFactory，因为他的getObject方法就返回的是SQLSessionFactory

##### 21.BeanFactory和ApplicationContext

BeanFactory可以bean的加载，实例化，维护bean之间的依赖关系等基本功能

而ApplicationContext也是间接的继承了这个BeanFactory并且还进行拓展比如国际化、Aware系列功能

##### 18.

##### 18.

##### 18.

##### 18.

