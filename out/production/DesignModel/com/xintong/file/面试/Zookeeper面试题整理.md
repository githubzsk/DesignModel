# Zookeeper面试题

##### 1 说说你对zookeeper的理解?

官网对zookeeper的描述是一个分布式协调服务，其实翻译过来就是说可以用它来维护多个系统之间的状态，以及一些数据的一致性。基于zk的核心：***文件系统***、***通知机制***，我们可以去实现注册中心、配置中心、分布式锁、====功能

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

##### 6 Leader选举

1. 初始化选举

   首先他会用到两个id，一个是myid，另一个是zxid

   - 投自己一票
   - 节点之间交换投票，先比zxid再比myid，调整自己的新投票
   - 交换投票，投票过半的为leader

2. Leader挂掉之后选举

   - 投自己一票
   - 互换投票进行对比，先比较zxid，谁的zxid大谁就是leader，在比较myid
   - 交换投票，投票过半为Leader
   
   区别在于，初始化一般比较myid，挂掉恢复一般比较zxid

##### 7 zookeeper集群为什么要搭建奇数台

zookeeper集群有这样一个特性，只要过半机器正常，那zk集群就对外可用。

1. 容错：三台和四台都是最多容忍一台挂掉，那么就没有必要用4台，当然你的读请求负载特别高的情况下至少要用4台，那么你可以选择将第四台设置成Observe，挂掉一个Observe再挂一个Follower，集群照样可以用，这样可以提高集群的容错性。
2. 防脑裂：过半不可能出现第二个Leader

##### 8 Zookeeper的Watch机制原理

watch机制有客户端、服务端、客户端的ZKWatchManage对象协同完成

##### 9 zookeeper节点的4种状态

- LOOKING：刚启动或者leader挂掉
- FOLLOWING：选举结束，身份已经定位代follower
- LEADING：选举结束，身份已经定位到leader
-  OBSERVING：类似follower，但是不选举不投票

##### 10.ZAB协议 

1. **崩溃恢复**

   - **Leader选举**：当集群初始化启动、或者Leader节点网络故障或者挂掉的时候，ZAB协议便会进入恢复模式并选举Leader
   - **数据同步**：接下来数据同步，过半节点状态同步完成之后，ZAB就退出恢复模式，进入消息广播模式

2. **原子广播**（消息广播）

   原子广播实际上是一个2pc修改版，标注2pc 的等到所有节点ack之后才最终提交，而zk的是过半提交

   - leader收到消息请求之后，给消息分配一个全局自增唯一的zxid
   - leader给每一个follower准备一个FIFO的队列，把消息和zxid作为一个提案发送给follower，这步会开启事务
   - follower接收到消息之后开始写，写成功之后回复leader一个ack
   - leader收到的ack过半之后，leader在给回复过ack的节点发送commit指令提交事务，
   - follower提交事务

   剩下未提交的会不断检查自己zxid和leader大小比较，在实现同步

##### 11.数据一致性原理

原子广播 参考11.2

##### 12.事务ID zxid

事务编号zxid是一个64位的数组，高32位表示epoch 表示任期，低32位是递增计数器，针对每一个事务消息 计数器+1，选举出一个leader，该leader的zxid中高32位的epoch+1，低32位的递增计数器恢复0

##### 13.数据同步

- 已处理的消息不能丢
- 被丢弃的消息不能留

