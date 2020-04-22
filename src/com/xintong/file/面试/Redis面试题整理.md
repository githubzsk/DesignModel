# Redis面试题

##### 1. *Redis和Mysql数据不一致解决

##### 2. *Redis数据结构

redis支持五种数据结构 string (字符串)、list (列表)、set (集合)、hash (哈希) 和 zset (有序集合)

- string（字符串）： 这个是最基本redis最基本的一个数据结构，也是我们用的最多的一个类型
- list（列表）：参考Java里的LinkedList，由一个双向链表实现，根据链表的特点，它增删快查询慢
- set（集合）：参考Java里的HashSet,同样也是无序的
- zset（有序集合）：首先它是一个set，保证了每个value的唯一性，另外他可以给每个value设置一个score而根据score排序
- hash（字典）：参考Java的HashMap，数组+链表实现

##### 3. *Redis持久化方式

1. ***RDB***：

##### 4 *Redis单线程为什么这么快

原因有三

1. ***基于内存***：因为Redis读写基于内存，一般情况下一个请求响应的速度是用纳秒还衡量，纳秒什么概念，我们平时说的毫秒是10的-3次方，纳秒是10的-9次方
2. ***单线程***：单线程它可以避免线程上下文切换的时间

##### 5.Redis单线程如何处理客户端并发连接



##### 5. *Redis一定是单线程吗

##### 6.什么是IO多路复用

多路指的是多个Socket连接，复用指的是复用同一个线程，实现多路复用可以有select、poll、epoll，而在Redis实现IO多路复用使用的是epoll

##### 6. *哨兵模式是否有了解

##### 7. *缓存击穿、缓存穿透、缓存雪崩

dubbo 服务暴露流程

首先spring容器启动之后的时候会解析dubbo：service标签或者@Service注解标注的类，然后生成一个ServiceBean，再说这个ServiceBean之前，我得先说一下

