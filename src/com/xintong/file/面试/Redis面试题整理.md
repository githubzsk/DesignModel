# Redis面试题

##### 1. 为什么使用缓存？使用缓存会带来什么问题？

使用缓存的目的 

-  高性能 
- 高并发

带来的问题 

-  缓存、数据库双写不一致
- 穿透、击穿、雪崩
- 并发竞争

##### 2. *Redis数据结构

redis支持五种数据结构 string (字符串)、list (列表)、set (集合)、hash (哈希) 和 zset (有序集合)

- string（字符串）： 这个是最基本redis最基本的一个数据结构，也是我们用的最多的一个类型
- list（列表）：参考Java里的LinkedList，由一个双向链表实现，根据链表的特点，它增删快查询慢
- set（集合）：参考Java里的HashSet,同样也是无序的
- zset（有序集合）：首先它是一个set，保证了每个value的唯一性，另外他可以给每个value设置一个score而根据score排序
- hash（字典）：参考Java的HashMap，数组+链表实现

##### 3. *Redis持久化方式

1. ***RDB（snapshot快照）***：保存了某一时刻Redis内存中的数据持久化到一个rump.rdb文件中，当然这个可以设置自动，也可以手动

   - 自动：在redis.conf文件中可以配置save  num num，它的意思是说在多少秒之内产生过多少次数据变更就会触发rdb

   - 手动：可以使用sava 或者bgsave指令 但是save会发生线程阻塞，所以一般不使用，而bgsave是进行异步rdb在后台操作，所以说如果手动的话还是建议使用bgsave

     RDB特点

     另外在使用redis-cli shutdown这个指令的时候，他也会默认做一次rdb

     **优点**：1. 使用子线程持久化，不影响任何IO操作，保证了高性能

     ​			2.恢复数据的速度也比较快

     ​			文件小、恢复快

     **缺点**：1. 存在数据丢失，随后一次的数据

     ​			2. 内存占用问题

     ​			无法恢复最后一次快照之后的数据

2. ***AOF（Append only file）***：事实上是在aof文件中追加数据改变的指令，准确的来说是追加**RESP协议**的命令，如果要使用的话需要在redis.conf中开启，有三种策略

   - always：每次修改数据指令都会去记录，性能比较差但是数据完整性比较好
   - everysec：每秒保存一次，性能稍微好点，但是会存在数据丢失
   - no：交给操作系统决定，linux下默认是30秒记录一次

   **优点**：可以较好保证数据完整性

   **缺点**：恢复速度慢，文件更大，记录了每条操作语句

3. ***AOF重写***：随着aof文件不断增大，为了减少无用数据，所以需要aof重写

   ***重写原理***：①创建一个子线程②基于当前内存快照把新的aof写到一个临时文件里，③同时主线程继续把新的变动写进原来的aof中④主线程获取子线程重写完成的信号，把新的增量写到新的aof中⑤ 替换旧的aof

4. ***混合持久化***：以bgsave做全量持久化、aof做增量持久化，生成的文件叫做aof，集成了rdb的文件小、速度快、和aof的数据完整特点

5. ***数据恢复***：去看aof存在不存在。如果aof存在的话加载aof忽略rdb

##### 4. 什么是RESP协议？

RESP(Redis Serialization Protocol)，直译过来是Redis的序列化协议，事实上它是redis服务端和客户端的通讯协议，特点是实现简单，容易读懂、解析高效

##### 5 *Redis单线程为什么这么快

原因有二

1. ***基于内存***：因为Redis读写基于内存，一般情况下一个请求响应的速度是用纳秒来衡量，纳秒什么概念，我们平时说的毫秒是10的-3次方，纳秒是10的-9次方
2. ***单线程***：单线程它可以避免线程上下文切换的时间

##### 7.Redis单线程如何处理客户端并发连接

1. 基于内存速度快
2. 单线程避免上下文切换时间
3. 基于IO多路复用实现并发处理

##### 8.Redis为什么是单线程，一定是单线程吗？

redis只是在处理客户端网络请求这一块使用的是单线程，其他地方并不是，我举一个简单的里，redis持久化rdb，bgsave就是重开子线程去处理持久化事件，而主线程照样运行

##### 8. Redis为什么不适用多线程

1. 多线程可能涉及到锁，一旦涉及到锁，对性能或多或少都会有影响
2. 多线程会有线程切换而消耗

##### 9.什么是IO多路复用（看视频）

多路指的是多个Socket连接，复用指的是复用同一个线程，实现多路复用可以有select、poll、epoll，而在Redis实现IO多路复用使用的是epoll

##### 11.哨兵如何检查Redis实例

Sentinel发送ping，Reds实例回复pong

##### 12.哨兵之间如何通讯？

1. _相互发现_：Sentinel跟Master之间通过发布订阅的形式去相互发现 "PUBLISH" "sentinel:hello"
2. _相互传递信息_:

##### 13. 哨兵之间Leader选举机制

​		自己选一个哨兵中run id最小的

​		自己再轮流看别人选的是谁

##### 14 哨兵如何选一个Slave作为新的Master

1. 主观下线
2. 客观下线
3. Sentinel的Leader选举
4. 故障转移
   - 过滤掉故障节点
   - 优先级最高的
   - 选择复制偏移量更大的从作为主
   - 选择run id最小的作为主

##### 11. Redis有哪些架构模式及其特点？

单机、主从、哨兵、集群

##### 12. 缓存穿透、缓存击穿、缓存雪崩

查询redis

​	存在  ： 返回数据

​	不存在：

​				查询数据库mysql

​				存入redis 并返回

​				未查询到，返回空

1. _缓存穿透_：查询一个一定不存在的值，会一直去查询数据库

   解决方案：1、接口处理基础校验，2、前台加密传参，后台解密参数 ，3 、数据库读不到的数据，也进行缓存，缓存成key “null”

   - 接口基础校验
   - 传参加密，后台解密
   - 缓存null值
   - 布隆过滤器

2. _缓存击穿_:  热点key突然到期或者其他原因，缓存中没有，数据库中有，并发用户有特别多，在缓存中没查到又同时去查数据库，导致数据库压力增

   解决方案：1、特别高的并发下可以考虑分布式锁实现

   ​					2、数百或者千的并发，直接使用互斥锁就OK，最好不要使用简单的Synchroized或者双重判断，						  效率有问题

3. _缓存雪崩_：

   _原因_: 缓存服务器down掉，或者缓存集体到期？总之就是缓存大面积失效，大量的请求直接打到数据库

   _解决方案_:

   - 首先主备、集群是必须的
   - 热点数据永不过期
   - 失效时间Random一下

##### 13. 布隆过滤器

它的模型是这样的，由映射函数和一个二进制向量组构成，对于一个key值，经过映射函数计算出该key值在二进制向量组中对应的位置，将该位置的0改为1，查询某个key是否存在的时候，先经过映射函数计算出二进制向量对应的位置是否为1，如果为1，则该key可能存在，如果为0则该key一定不存在

_优点_：占用内存小，查询速度快

_缺点_：误判率的问题

##### 14.Redis过期策略？内存淘汰机制？

_过期策略_：定期删除+惰性删除

- 定期删除：redis每隔几百毫秒就随机抽取设置过期时间的key检查，如果过期就会删除了
- 惰性删除：定期删除引发的问题，在你get这个key之前他会发现这个key本该已经过期了，然后这个时候在去删除这个key并返回空。

_内存淘汰机制_：

- `noeviction`: 新写入操作会报错，不用
- `allkey-lru`: 移除最近最少使用的 key 最常用
- `allkeys-random`: 随机移除某个 key  不用
- `volatile-lru`
- `volatile-random`
- `volatile-ttl`

##### 15.数据库和缓存双写不一致

_原因1_： 先修改数据库，在删除缓存，各种原因缓存删除失败，导致数据库为新，缓存为旧

解决： 先删除缓存，在修改数据库

原因2：并发或者高并发情况下，A线程更新数据库，B线程读取数据，A线程删除了缓存，B线程没读到缓存区读			  数据库，读完之后进行缓存，这个时候A线程修改了数据库，这个时候缓存和数据库又不一致了

解决：

我先说三种更新策略

1. 先更新数据库，在更新缓存
2. 先删除缓存，在更新数据库
3. 先更新数据库，在删除缓存

（1）对于_先更新数据库，再更细缓存_

​		问题1： 更新数据库成功，更新缓存失败       数据不一致

​		问题2： A线程更新数据库，B线程更新数据库，B线程更新缓存，A线程更新缓存  数据不一致

​		问题3： 每次更新数据库都要更新缓存，对于读请求不多的场景，属于浪费性能

 	**所以这种方案不考虑**

（2）对于先删除缓存，再更新数据

​		问题: 两线程A写B读，A 删除缓存 B读缓存，B读数据库，B写缓存，A更新数据库  脏数据

​		解决方案1：① 延时双删 ,写线程在更新完数据库之后 重开一个线程延时一定时间之后，再次删除缓存

​		缺点： 在延时期间内，依旧能读到脏数据         不适用于要求强一致性的业务 

```java
public void write(String key,Object data){
        redis.delKey(key);
        db.updateData(data);
        Thread.sleep(1000);
        redis.delKey(key);
    }
```

​		解决方案2： 对于同一数据路由到同一服务上，然后把对数据的写请求，以及对数据的缓存添加到内存队列中，后面来的请求去查看内存队列中有没有写请求，有的话说明写还没有处理完，后来的请求继续往队列里面放，如果没有写请求，那么就说明写请求已经处理完毕，不用进队列，直接读就可以

​		有点:  能够保障数据强一致性

​		缺点，高并发+写操作频繁的情况下，性能不是很好

##### 16. Redis实现分布式锁

redis实现的分布式锁是基于redis的setnx指令，setnx和set的区别就是setnx不会再设置已有的key

可以使用jedis，也可以使用spring data redis 的RedisTemplate我们在项目里面更多使用RedisTemplate，那么我就用RedisTemplate来说吧，其实原理都是一样的

```java
public String getGoods(int goodsId){
    //以唯一资源id为key
    String lockKey = "goods_"+goodsId;
   try{
    //用redisTemplate的setIfAbsent API 去setnx数据，lockKey唯一，value随意，超时时间根据业务而定
    // 超时是为了避免程序崩了锁永远无法释放   
    Boolean isLock = redisTemplate.opsForValue().setIfAbsent(lockKey, "zsk", 10, 							 TimeUnit.SECONDS);
       if (!isLock){
           //如果没有获取到就通过循环等方式再去尝试获取，同时可以限定超时时间，超过多久就不在获取直接退出
           return "failed";
       }
       //如果获取到了之后就执行业务逻辑
       int count = Integer.parseInt(redisTemplate.opsForValue().get("count"));
       if (count > 0){
           count --;
           System.out.println("扣除库存成功");
       }else{
           System.out.println("库存不足");
       }
   }finally {
       //最后在finally中去删除这lockKey释放锁
       redisTemplate.delete(lockKey);
   }
    return "success";
}
```

这种锁并发并不高的情况下可以使用，但是高并发情况下肯定是不适用的，因为他有缺陷，第一个线程获取了锁，如果业务还没执行完，锁就失效了，第二个线程就可以拿到锁，然后第一个线程执行到finally去删除lockKey，这个时候删除的 是第二个线程的锁子，肯定是不行的，所有为了解决高并发下的这个问题，

```java
public String getGoods(int goodsId){
    //以唯一资源id为key
    String lockKey = "goods_"+goodsId;
    //生成唯一value，防止别的线程误删当前线程的lockKey
    String value = UUID.randomUUID().toString();
    try{
        //用redisTemplate的setIfAbsent API 去setnx数据，lockKey唯一，value随意，超时时间根据业务而定
        // 超时是为了避免程序崩了锁永远无法释放   
        Boolean isLock = redisTemplate.opsForValue().setIfAbsent(lockKey, value, 10,                      TimeUnit.SECONDS);
        if (!isLock){
            //如果没有获取到就通过循环等方式再去尝试获取，同时可以限定超时时间，超过多久就不在获取直接退出
            return "failed";
        }
        //如果获取到了之后就执行业务逻辑，同时新开线程对超时时间进行续时
        //新开线程续时，避免业务未执行完而lockKey失效
        //处理业务
        int count = Integer.parseInt(redisTemplate.opsForValue().get("count"));
        if (count > 0){
            count --;
            System.out.println("扣除库存成功");
        }else{
            System.out.println("库存不足");
        }
    }finally {
        //保证了只由当前线程删除当前lockKey
        if(redisTemplate.opsForValue().get(lockKey).equals(value)){
            //最后在finally中去删除这lockKey释放锁
       		 redisTemplate.delete(lockKey);
        }
       
    }
    return "success";
}
```

这样就可以在高并发环境下使用了，但是还有更简便的写法，使用redisson相关Api

```java
 public String getProduct2(int product_id){
        String lockKey = "product"+product_id;
        
        // 第一步 创建锁
        RLock lock = redisson.getLock(lockKey);
        try{
        	//第二步 加锁
            lock.lock(10,TimeUnit.SECONDS);
            //获取库存
            int stock = Integer.parseInt(redisTemplate.opsForValue().get("stock"));
            if (stock > 0){
                //减少库存
                stock--;
                redisTemplate.opsForValue().set("stcok",stock+"");
                System.out.println("扣件成功，库存剩余： + " + stock);
            }else {
                System.out.println("扣减失败,库存不足");
            }
        }finally {
            //第三步 释放锁
            lock.unlock();
        }
        return "success";
    }
```

这种写法和上面我说的那种写法的底层原理是一模一样的，事实上就是redisson对原理做的封装

这个就是redis做的分布式锁，但是他也有一点问题，主备模式下



##### 17 Redis实现分布式事务







Redis高可用集群问题

