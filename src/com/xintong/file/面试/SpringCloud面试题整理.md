# SpringCloud面试题

##### 1 *SpringCloud 限流组件

##### 2 *eureka服务注册原理

##### 3 *eureka和zookeeper作为注册中心的区别

首先说一下CAP理论

zk保证CP，Eureka保证AP	

zk集群模型是一主多从，Leader挂了必须要进行选举，而Leader选举期间对资源进行锁定不对外提供服务，目的就是为了保证一致性。

Eureka模型是去中心化、人人平等，挂了一台还有其他的对外提供服务，只不过可能数据不是最新，但是他保证了可用性

##### 4.Eureka自我保护机制

当 Eureka Server 节点在短时间内丢失了过多实例的连接，Eureka会触发自我保护机制，一旦启动Eureka的自我

##### 4 主要组件以及如何运用

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

##### 5Hystrix降级方法

具备服务降级，服务熔断，依赖隔离，监控（Hystrix Dashboard）

 提示 哎哟喂，被挤爆了。 app秒杀 网络开小差了，请稍后再试。

##### 6. SpringCloud和Dubbo区别

1. 最重要的区别是Dubbo是RPC，SpringCloud是HTTP Rest  这是通讯方式
2. SpringCloud生态完善，Dubbo我曾经为了做网关，Soul、Zuul都不太理想，最后还是使用了Springcloud Gateway
3. Dubbo一般注册中心使用ZK，当然也可以用其他的比如cansul，而SpringCloud更多使用Eureka

网关token如何校验

##### 6网关token如何校验

##### 7 Ribbon负载均衡策略

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

##### 8 服务扇出

A调用B、C，BC又调用其他，这叫服务扇出

##### 9 服务雪崩

如果扇出的链路上某个由于种种原因不可用了，而一直请求的话，会造成整个调用链占用资源越来越多，从而整个调用链的崩溃

##### 10 如何设计一个高并发项目

