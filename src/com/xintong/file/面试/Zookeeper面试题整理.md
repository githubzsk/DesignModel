# Zookeeper面试题

##### 1 说说你对zookeeper的理解?

官网对zookeeper的描述是一个分布式协调服务，其实翻译过来就是说可以用它来维护多个系统之间的状态，以及一些数据的一致性。基于zk的核心：***文件系统***、***通知机制***，我们可以去实现注册中心、配置中心、分布式锁、分布式事务====功能

##### 2 为什么zookeeper能作为注册中心

简单的注册中心的本质就是去维护服务的提供者消费者对应的关系，而zk的这种文件系统又特别适合

我之前项目有用到zk给dubbo做注册中心，那我就说说zk作为dubbo注册中心的原理

首先我得说一下，用zk给dubbo做注册中心时，zk内部node节点的父子层级关系，

![image-20200418141633802](https://img-blog.csdnimg.cn/20200420154523572.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3dlaXhpbl80MzAxMjkzNw==,size_16,color_FFFFFF,t_70#pic_center)

会在根目录下创建dubbo节点，这个dubbo节点节点下又有好多节点，其中有一个metadata节点这个我们不关心，其余的都是类名节点，这个类名节点下又有4个节点，其中两个一个是consumers另一个是providers，也就是提供者和消费者，剩下的两个节点我们先不去关心，consumers里面的每个节点都代表这个服务的每个消费者信息，providers里面的每个节点都代表着每个消费者信息。

当提供者启动的时候，他会在zk中创建/dubbo/类名/providers/提供者信息 这样的一个节点

当消费者启动的时候，他会在zk中创建/dubbo/类名/consumers/消费者信息 这样的一个节点

当消费者首次调用的时候，他回去注册中心拿到对应的所有提供者列表并本地缓存，后续调用便不再请求注册中心

当提供者断开或者新增的时候，基于zk的Watcher机制。会去通知对应的消费者重新去缓存提供者的信息

##### 3 zookeeper节点类型

1. 临时有序
2. 临时无序
3. 持久有序
4. 持久无序

##### 4 zookeeper临时节点实现原理

事实上zk有一个内置数据库叫做ZKDatabase，这个ZKDatabase里面封装了一个叫做DataTree，这个DataTree里面就封装了zk的所有节点包括临时节点，临时节点使用一个ConcurrentHashMap来储存，我们知道zk客户端连接zk服务端的时候会保持一个session，当断开连接的时候会调用一个叫做killSession的方法把你的sessionid传进去，直接从这个ConcurrentHashMap中remove掉，这样就实现了临时节点的功能。

##### 5 zookeeper角色

1. ***Leader***：zk集群中的主节点，主要负责的是事务操作，换句话说就是主要负责写操作（增删改）
2. ***Follower***：主要负责非事务操作（读操作），另外还会参加选举以及相应提议
3. ***Observer***：一般来说这个角色不必存在，只有在读请求负载特别高的时候去配置Observer，但是这个Observer不选举也不提议，只负责读操作

##### 6 CAP理论

CAP原则又称CAP定理，指的是在一个分布式系统中，[一致性](https://baike.baidu.com/item/一致性/9840083)（Consistency）、[可用性](https://baike.baidu.com/item/可用性/109628)（Availability）、分区容错性（Partition tolerance）。CAP 原则指的是，这三个要素最多只能同时实现两点，不可能三者兼顾。

##### 7 Leader选举

1. 初始化选举

   首先他会用到两个id，一个是myid，另一个是zxid（事务id，每次leader被选举出来都会在他原来的基础上+1）

   每台机器都会先投自己一票，然后相互交换投票，然后投zxid最大的那个，当然刚开始的话zxid都一样，在zxid一样的情况下，会投myid较大的那个，所以有个结论，在初始化选举的时候，当集群中启动的数量超过一半的时候，这时谁的myid最大谁就是leader

2. 重启动选举

   

##### 8 zookeeper集群为什么要搭建奇数台

zookeeper集群有这样一个特性，只要过半机器正常，那zk集群就对外可用。

三台和四台都是最多容忍一台挂掉，那么就没有必要用4台，当然你的读请求负载特别高的情况下至少要用4台，那么你可以选择将第四台设置成Observe，挂掉一个Observe再挂一个Follower，集群照样可以用，这样可以提高集群的容错性。

##### 9 Zookeeper的Watch机制原理

watch机制有客户端、服务端、客户端的ZKWatchManage对象协同完成

##### 10

