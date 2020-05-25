# SpringCloud面试题

##### 

##### 1. EurekaServer

从EnableEurekaServer注解开始说起，这个注解比我们想象的要简单得多，简单来说他只干了一件事就是new了一个叫做marker对象。通过Import加载了另外一个类然后new 了一个Marker对象注入到IOC容器中去，至此EnableEurekaServer使命已经完成了

marker对象我么可以理解为激活开关，接下来基于SpringBoot的自动装配去一个叫做EurekaServerAutoConfiguration的类，至于自动装配我这里就不讲了，这个类我们可以简单的理解为是一个配置类，跟我们平时写的配置类一样，在他里面初始化了EurekaServer，这里的EurekaServer事实上是对NetFilxEureka的封装，

EurekaServer里面干了两件非常重要的事，一个是初始化他的运行环境，一个是初始化他的上下文在他的初始化上下文中又干了两件非常重要的事儿，一个是服务同步，一个是服务剔除

##### 2. EurekaClient

EnableEurekaClient注解通过一系列操作创建了一个叫做Marker的对象，可以理解为激活对象或者开关，然后基于SpringBoot的自动装配，启动一个叫做EurekaClientAutoConfiguration的类，这个类

中会初始化他所需要的所有东西，我只挑重点说，他会初始化一个EurekaClient，经过一连串调用活初始化DiscoveryClient，在DiscoveryClient中有一个定时任务，定时任务任务中实现了服务获取、服务注册、以及服务续约

##### 3. Eureka

- 服务端

  - **服务同步**：registry.syncUp()，这个方法实现是这样的，他会通过Http调用去获取相邻节点的所有服务实例，然后再把这个服务实例注册到自己的注册表中，从而实现服务同步
  - **服务剔除**：服务剔除是一个定时任务，默认60秒检查一遍，它实现的原理是这样，他会去检查你的服务续约，啊也就是我们说的心跳，如果你这个续约时间过期了(超过90秒)，那么直接从注册表中把你remove掉
  - **服务保护**：短时间内他会统计续约失败比例，如果超过默认阈值，他会启动自我保护，因为他可能认为是不是我自己网络的问题？这个时候他就不再进行服务剔除。

- 客户端

  

  - **服务注册**：EurekaClient在启动的时候会通过HttpRest请求将自己本身信息注册到EurekaServer，提供自身的元数 据，比如ip地址、端口、运行状况指标的url、主页地址等信息。Eureka Server接收到注册请求后，对这些信息进行封装，然后存储在一个双层的Map
  - **服务获取**：
    - 初次全量获取：Client初始化的时候会基于Http，REST调用服务端的一个接口，然后服务端把整个注册表给他，然后他把这个注册表拿过来缓存到本地，这是一个定时任务默认30秒执行一次，同样他会默认缓存30秒
    - 后续增量获取：增量就是服务端后注册的服务，但是由于服务端的缓存机制，可能会造成增量拉取数据不一致，**而Eureka里面有一个很好的解决思路**，就是每次增量的时候顺便把服务端全量的HashCode顺便携带过来，获取到增量之后然后进行合并，合并完之后计算全量的HashCode与服务端传来的HashCode进行对比，如果对比一致说明增量没有问题，如果对比不一致，说明增量获取有问题，他会立马进行一次全量拉取。
  - **服务续约**：服务续约也就是我们说的心跳，他默认是30秒一次，通过HttpREST调用服务端续约的接口，而这个接口的实现原理并没有想象的多复杂，很简单，就是更新该服务的最后上线时间，实现了服务续约，**而服务续约有一个很巧妙的设计**，它采用了**动态时间定时任务**去续约，默认30秒，但是这个30秒不是定值，当然我这里说的不是通过我们配置去改变，他是一个根据程序运行状况而变化的变量，设计思路值得我们学习，定时任务初始值30秒，而他这个定时任务跟我们的定时任务不一样，他是递归调用本本身，如果网络出现什么问题导致续约超时，这个时候他会改变续约时间*2，之后等网络恢复正常之后再回去。
  - **服务调用**：获取到服务注册表之后，就可以通过实现的负载均衡机制进行调用了
  - **服务下线**

##### 4. EurekaServer缓存机制

注册表

只读缓存表 ConcurrentHashMap 一级缓存

读写缓存表  二级缓存

获取注册表先从一级缓存获取，一级缓存没有，再从二级缓存获取，二级缓存有的话，返回并写入一级缓存，二级缓存没有的话再去读注册表，

写入注册表的时候，先写入注册表，在写入二级缓存

定时任务30秒将二级缓存的数据写入一级缓存，

##### 5. 注册表

{   MICROSERVICE - PROVIDER - USER = {     DESKTOP - 1 SLJLB7: microservice - provider - user: 8002 = com.netflix.eureka.lease.Lease  @2cd36af6,     DESKTOP - 1 SLJLB7: microservice - provider - user: 8001 = com.netflix.eureka.lease.Lease  @600b7073   } }

注册表实际上是一个双层的ConcurrentHashMap，外层的key是应用名称，内层是你要调用的接口实例以及他对应的详细信息

##### 6. Eureka高可用原理

1. 二级缓存 ：首先注册表、一、二级缓存都是基于纯内存操作，然后有两级缓存在前面挡着
2. 读写分离：把业务分离开，读只读，写只写。

##### 7.Eureka AP原理

当客户端剔除服务之后，写进服务端注册表，然后在写进二级缓存，而二级缓存定时30秒回刷新进一级缓存，但是请求的时候是先是经过一级缓存，一级缓存有的话直接返回，这个时候比如请求到了一级缓存，一级缓存已经有了，那么他直接返回，但是实际上这个时候二级缓存已经变，所以会出数据不一致，也就是说他这个不一致状态最极端情况下会保持30秒

##### 8 *Eureka服务注册原理

客户端基于HttpRest调用服务端注册接口，把自己的元数据提供给服务端，比如服务名、ip、端口、运行状况指标url、然后服务端把这些信息进行封装之后存入双层map，接着把在把这些数据写进二级缓

##### 9 *eureka和zookeeper作为注册中心的区别

首先说一下CAP理论

zk保证CP，Eureka保证AP	

zk集群模型是一主多从，Leader挂了必须要进行选举，而Leader选举期间对资源进行锁定不对外提供服务，目的就是为了保证一致性。

Eureka模型是去中心化、人人平等，挂了一台还有其他的对外提供服务，只不过可能数据不是最新，但是他保证了可用性



##### 10.Eureka自我保护机制

当 Eureka Server 节点在短时间内丢失了过多实例的连接，Eureka会触发自我保护机制，一旦启动Eureka的自我

##### 11 主要组件以及如何运用

- *Eureka*

  Eureka的使用相对简单，重点是它的原理，使用的话在在启动类上标注EnableEurekaServer，一般都是搭建Eureka集群，几台Eureka相互注册就OK

- *Feign/Ribbon*

  - Ribbon：可以实现客户端负载均衡，Ribbon配合RestTemplate使用可以实现负载均衡，用RestTemplate进行远程调用  post/get For Object/Entity 
  - Feign：使用Feign的话要在调用方抽象出来接口，标注@FeignClient注解选择你对应的服务，然后通过Requestmapping在方法上映射你要调用的路径

- *zuul*

  SpringCloud封装了NetFilx的zuul，做了SpringCloud-zuul，引入Jar包，配置路由规则就可以使用

  ```yaml
  zuul:
    routes:   #路由
      api-a:
        path: /ribbon/**    #访问路径一  访问localhost:8769/ribbon/hi是访问ribbon
        serviceId: maintain-ribbon-consumer   # ribbon注册在eureka的服务名
      api-b:
        path: /feign/**      #/访问路径二  访问localhost:8769/feign/hi是访问feign
        serviceId: maintain-feign-consumer    # //feign注册在eureka的服务名
  ```

- *Hystrix*

  ```java
  @EnableHystrix//开启Hystrix
  //指定对应熔断处理类
  @FeignClient(name = "maintain-provider",fallback = UserServiceHystrix.class)
  
  //类中处理熔断方法
  @Component
  public class UserServiceHystrix implements IUSerService {
      @Override
      public String getHi(String name) {
          return "get Hi Error";
      }
  
      @Override
      public String getHello(String name) {
          return "get Hello Error";
      }
  }
  
  ```

  ```yaml
  #开启Hystrix
  feign:
    hystrix:
      enabled: true
  ```

  

- *Config*

##### 12. Hystrix降级方法

具备服务降级，服务熔断，依赖隔离，监控（Hystrix Dashboard）

 提示 哎哟喂，被挤爆了。 app秒杀 网络开小差了，请稍后再试。

1 *SpringCloud 限流组件

##### 13. SpringCloud和Dubbo区别

1. 最重要的区别是Dubbo是RPC，SpringCloud是HTTP Rest  这是通讯方式
2. SpringCloud生态完善，Dubbo我曾经为了做网关，Soul、Zuul都不太理想，最后还是使用了Springcloud Gateway
3. Dubbo一般注册中心使用ZK，当然也可以用其他的比如cansul，而SpringCloud更多使用Eureka

##### 14. 网关token如何校验

##### 15. Ribbon负载均衡策略

通过对请求的拦截以及替换实现

1.  轮询策略（默认）

   轮询策略表示每次都顺序取下一个 provider，比如一共有 5 个 provider，第 1 次取第 1 个，第 2 次取第 2 个，第 3 次取第 3 个，以此类推。

2.  权重轮询策略

   根据每个 provider 的响应时间分配一个权重，响应时间越长，权重越小，被选中的可能性越低。

3.  随机策略

4.  最少并发数策略

   选择正在请求中的并发数最小的 provider

   1.  重试策略

5.  可用过滤策略

      　　策略对应类名：`AvailabilityFilteringRule`

      　　实现原理：过滤性能差的 provider

   - 第一种：过滤掉在 Eureka 中处于一直连接失败的 provider。
   - 第二种：过滤掉高并发（繁忙）的 provider。

6.  区域权衡策略

   - 以一个区域为单位考察可用性，对于不可用的区域整个丢弃，从剩下区域中选可用的 provider。
   - 如果这个 ip 区域内有一个或多个实例不可达或响应变慢，都会降低该 ip 区域内其他 ip 被选中的权
     重。

##### 16. 服务扇出

A调用B、C，BC又调用其他，这叫服务扇出

##### 17. 服务雪崩

如果扇出的链路上某个由于种种原因不可用了，而一直请求的话，会造成整个调用链占用资源越来越多，从而整个调用链的崩溃

##### 18. 如何设计一个高并发项目

