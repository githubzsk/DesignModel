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

先讲一波ZAB架构

拿初始化来说

当ZK启动的时候他有一个main方法，经过一连串的调用最后调用到了一个start方法，这个start里面做了四件事

```java
this.loadDataBase();//加载数据库
this.cnxnFactory.start();//初始化网络相关
this.startLeaderElection();//初始化Leader选举相关
super.start();//线程start
```

第一件事加载数据库，zk数据库这块源码我也看过，包括他临时节点实现的原理什么的，这里就不多讲了，要不没完没了了，

第二件事初始化网络相关，这里它默认使用的是nio，当然你也可以取配置让他使用Netty，都行

第三件事初始化选举相关，这个里面干了两件比较重要的事，第一构造Vote当前投票，这个Vote构造有三个参数（myid，zxid，epoch），另外一件事是设置选举算法，默认使用的是FastLeaderElection，其他的选举算法我看了一下都过期了

第四件事调用线程的start方法，正儿八经的开始了 Leader选举以及数据同步

它的run方法里面是用while循环配合Switch case了四种状态，这个While非常重要，牵扯到挂掉之后的选举，这个等会再说。Looking/Leading/Following/Observing，这里我直说Looking状态下，因为选举核心逻辑都在这个状态下。

Looking下游一个lookForLeader的方法，在这个方法里面

首先使用cas给他的逻辑时钟+1，在投上自己一票

然后把自己的投票信息进行广播给其他zk实例

接下来他会从一个队列，具体来说是一个LinkedBlockQuque，里面拿出来别人的投票，这个队列里面的票是别的zk实例通过网络塞进去的。

接下来会把自己的票和别人的票去比大小，具体怎么比待会我再说

如果自己的大，就用自己的，如果别人的大，就用别人的。

完了再次把票广播出去，

接下来再从队列里面拿出来比较，如果谁的票过半，那么谁就是Leader，如果没投出来，进入一个while循环，延时200ms进行循环处理，直到找到过半的。

那么我再说一下他比大小的那个细节，先比epoch，在zxid，再比myid，实际上跟我们想象的不太一样，他并没有用ifelse这种逻辑，只用了一行代码使用||连接了三个比较条件，.....，因为是初次启动，所以到最后一般都比较的是myid，所以有一个结论就是说，在启动过半那一刻，谁的myid最大，谁就是Leader

这就是初始化启动，还有运行时挂掉的Leader选举

刚才我说过，线程启动会使用一个while循环和switch case很重要，正常运行该是Leader就是Leading状态，该是.....，但是如果说Leader挂掉之后，所有的Follower就会变成Looking状态，然后再While循环里面去重复我刚才说的那整个流程，其实很初始化差不多，唯一不同的是，在比大小的时候，基本上在zxid这里就比出来了，一般比不到他的myid。

这就是Leader选举

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

原子广播 参考10.2

##### 12.事务ID zxid

事务编号zxid是一个64位的数组，高32位表示epoch 表示任期，低32位是递增计数器，针对每一个事务消息 计数器+1，选举出一个leader，该leader的zxid中高32位的epoch+1，低32位的递增计数器恢复0

##### 13.数据同步

- 已处理的消息不能丢
- 被丢弃的消息不能留

