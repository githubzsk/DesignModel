# Dubbo面试题

##### 1 *完整调用链路（生成到消费）

##### 2 如何看待分布式架构

##### 3 dubbo 和 Spring Cloud 有什么区别？

##### 4 dubbo服务暴露流程

##### 5 dubbo的负载均衡策略

在Dubbo中共有4种负载均衡策略，对于Dubbo源码来说，负载均衡的顶级接口就叫做LoadBalance，而有一个抽象类AbstractLoadBalance实现了这个接口，而这个AbstractLoadBalance有4个子类，这四个子类就对应的是4中负载均衡策略

1. **RandomLoadBalance随机**：按权重随机，这也是Dubbo默认的负载均衡策略
2. **RoundRobin轮询**：按照权重轮询
3. **LeastActiveLoadBalance最小活跃调用数**：让比较慢的提供者接收到更少的请求，就是能者多劳的意思
4. **ConsistentHashLoadBalance一致性Hash**：相同参数的请求发到同一个提供者，如果说这个提供者挂掉，那它会平摊到其他提供者不会引起剧烈变动。

##### 6 dubbo支持的协议

##### 7 dubbo的集群容错

1. **Failover Cluster（失败重试）**：这个也是Dubbo默认的容错机制，默认会重试两遍，配置retries="2"。
2. **FailSafe（失败安全）**：即使失败了也不影响核心流程的服务，失败直接返回空结果，在调用方看起来是成功的
3. **FailFast（快速失败）**：对于一些非幂等性操作需要快速失败，否则可能出现脏数据，这种情况直接抛给调用方去处理业务逻辑
4. **Failback(失败自动恢复)**：Failback维护了一个失败列表，它首先使用FailSafe，然后再将这次调用加入到失败列表中，重开一个线程做异步重试，
5. **Forking(并行调用)**：一次性发起多个调用，只要其中一条调用成功就认为成功
6. **Broadcast(广播调用)**：一次发起多个调用，只要其中一条调用失败就认为失败，场景比如通知所有提供者更新缓存或日志

##### 8 为什么zookeeper能作为注册中心

##### 9 使用分布式碰到的bug

##### 10 服务失效踢出zookeeper中临时节点的原理

##### 11. Zookeeper（注册中心）在Dubbo中的作用，不要行不行？

##### 12. Dubbo支持的注册中心

1. ***Zookeeper***：
2. Redis：
3. Simple：
4. Multicast：
5. Nacos：