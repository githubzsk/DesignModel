# Java基础知识面试题整理

#### 1. 集合



- **为什么要重写hashcode和equals/重写equals为什么一定要重写hashCode**

  如果说确定对象不会作为哈希表的key，那你完全可以不用重写，相反的那一定要重写

  如果你不重写的话，那么产生的严重后果就是，你可以把一个对象put进去，但是你没办法用另一个跟他equals的对象把他get出来，因为没有重写hashCode，Object的hashCode它的实现实际上是返回对象的内存地址，那两个对象的内存地址肯定不一样，那肯定能存进去取不出来，当然这个也跟Hashmap的数据结构以及hash算法有关（引导面试官问HashMap相关）

- **遍历HashMap**

  ```java
  Set<String> strings = map.keySet();
  Collection<Integer> values = map.values();
  Set<Map.Entry<String, Integer>> entries = map.entrySet();
  ```

- **对象做HashMap的Key值，需要注意什么？**

  必须要重写hashCode和equals方法，hashCode参与HashMap的哈希算法，而

- **HashMap的理解**

  从HashMap数据结构、hash算法去谈。

- **hashcode的作用**

  1.可以返回对象哈希码值，也叫作散列码，可以用来支持哈希表，比如说HashMap、Hashtable等

  2.可以提高哈希表的性能

  

- **为什么要有迭代器**

  迭代器提供了遍历集合的方式，使用迭代器不必关心容器的具体实现，只要属于Collection都可以使用迭代器去遍历，像List可以for循环去遍历，而set就没办法for循环了，只能用迭代器遍历。

- **HashMap的hash算法**

  

  ```
  static final int hash(Object key) {
      int h;
      return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
  }
  ```

  

  在jdk8的时候，HashMap中他的hash算法是这样实现的，它被定义成了返回值为int类型名字就叫hash的一个方法。这个方法看着很简单只有两行,第一行声明了一个int类型的一个变量，第二行呢是一个三目运算，这个三木运算是这样表达的：它说你传进来的key如果是null的话那我直接给你返回一个0，你传进来的key如果不是null的话，那我取你key的hashCode值，与你key的hashCode值右移十六位做一个异或操作，从这个三目运算里面我们至少可以得出两个结论，第一点HashMap中可以储存key值为null的键值对，而且可以确定的是它的位置就在数组下标为0的那个位置上。第二点呢key值不为null的话，它又是取hashCode值，又是右移，又是异或的，事实上他这样做只有一个目的，让高低位都参与运算让最终的结果尽可能的均匀散列，从空间的角度上提高HashMap的效率。

- **HashMap结构**

  在jdk7以及之前，HashMap由数组＋链表构成，

  在jdk8的时候，HashMap由数组+链表+红黑树构成

  事实上HashMap的本质就是一个动态数组，而这个数组的每个格子上存的可能是一对键值对，由可能是多对键值对构成的链表或者红黑树，当然呢我这里说成键值对只是为了好表达而已。事实上这个键值对他是被Node或者TreeNode这种对象封装起来的。

- **HashMap的put方法流程**

- **HashMap的扩容方法**

- **ConcurrentHashMap相关**

- 

- 

  

  

  

##### 2.异常

- **异常如何设计？**

  在项目里写一个ServerException继承RuntimeException，再根据不同业务创建不同的Exception继承这个ServerException，在定义一些这类业务出现的常见错误代码，

- **Java的异常类型**

  Throwable

   		Error

  ​				AWTErrot

  ​				VirtualMachineError

  ​						StackOverflowError

  ​						OutOfMemoryError

  ​	 	Exception

  ​				RuntimeExcrption

  ​						NullPointException

  ​						ClassNotFoundException

  ​						IllegalArgumentException

  ​						IndexOutOfBoundException

  ​				IOException

  ​						ClassNotFoundException

  ​						

  ​	

##### 3. 大杂烩

- **线程安全的单例模式**

  ①懒汉式（非线程安全）

  ```java
  
  public class Singleton{
      private static  Singleton singleton;
      private Singleton{}
      public static Singleton getInstance(){
          if(singleton == null){
             singleton = new Singleton();
          }
          return singleton;
      }
  }
  ```

  ②饿汉式

  ```java
  public class Singleton{
      private static Singleton singleton = new Singleton();
      private Singleton{}
      public Singleton getInstance(){
          return this.singleton;
      }
  }
  ```

  ③普通双重检查锁-存在安全隐患

  ```java
  public class DoubleCheckSingleton {
      private static DoubleCheckSingleton singleton;
      private DoubleCheckSingleton(){}
      public static DoubleCheckSingleton getInstance(){
          if (singleton == null){
              synchronized (DoubleCheckSingleton.class){
                  if (singleton == null){
                      //1 分配内存
                      //2 实例化对象
                      //3 将对象指向刚分配的内存
                      // 如果指令重排序 2 3 重排序   A线程执行 132  走到3时候，B线程判断外层						// singleton不为null
                      // 直接return singleton，便出现问题
                      singleton = new DoubleCheckSingleton();
                  }
              }
          }
          return singleton;
      }
  }
  ```

  ④改进版双重检查锁-volatile

  禁止重排序

  ```java
  public class DoubleCheckSingleton {
      private static volatile  DoubleCheckSingleton singleton;
      private DoubleCheckSingleton(){}
      public static DoubleCheckSingleton getInstance(){
          if (singleton == null){
              synchronized (DoubleCheckSingleton.class){
                  if (singleton == null){
                      //1 分配内存
                      //2 实例化对象
                      //3 将对象指向刚分配的内存
                      // 如果指令重排序 2 3 重排序   A线程执行 132  走到3时候，B线程判断外层singleton不为null
                      // 直接return singleton，便出现问题
                      singleton = new DoubleCheckSingleton();
                  }
              }
          }
          return singleton;
      }
  }
  ```

  ⑤静态内部类 

  ```java
  public class InnerSingleton {
  	//静态内部类不会随着外部类的加载而加载，只要getInstance的时候才会加载
      private InnerSingleton(){}
  
      public static InnerSingleton getInstance(){
          return MySingleton.singleton;
      }
  
      static  class MySingleton{
          private static InnerSingleton singleton = new InnerSingleton();
      }
  
  }
  ```

  

- **日志怎么打，有什么规范**

- **Object基类常见方法**

  像我们最熟悉的用来比较对象的hashCode(),equals()

  还有线程通知机制的wait(),notifiy(),notify All()

  还有对象被GC回收前调用的finalize方法()

- **==和equals**

  对于基本类型的话使用==去比较

  对于对象类型的话可以使用==，也可以使用equals去比较

  对象使用==比较的是两个引用是否指向的是同一个对象实例，说白了也就是比较内存地址

  对象使用equals得看他有没有重写equals，重写了的话按照它的重写规则去比较，没有重写的话他会调用Object类的equals方法，事实上Object的equals使用的是==去作比较

- **String的equals实现**

  ```java
   public boolean equals(Object anObject) {
       //首先跟自己比较
          if (this == anObject) {
              return true;
          }
       //在看传进来的是不是String类型
          if (anObject instanceof String) {
              String anotherString = (String)anObject;
              int n = value.length;
              //再判断长度是否相等
              if (n == anotherString.value.length) {
                  char v1[] = value;
                  char v2[] = anotherString.value;
                  int i = 0;
                  //转换为char数组，逐个字符进行比较
                  while (n-- != 0) {
                      if (v1[i] != v2[i])
                          return false;
                      i++;
                  }
                  return true;
              }
          }
          return false;
      }
  ```

  

- **final关键字**

  可以修饰类、方法、变量

  1. final修饰的类不能被继承
  2. final修饰的方法不能被重写
  3. final修饰的变量不能被修改，也叫常量

- **重写重载的区别**

  1. 方法重载
     * 方法名、返回值类型（可以是父返回值的子类）、参数列表 全都相同
     * 子类方法抛出的异常不能大于父类的异常
     * 子类访问修饰符的权限不能小于父类的访问权限修饰符
  2. 方法重写
     + 方法名必须相同、参数列表必须不同
     + 访问修饰符，返回值类型、抛出的异常随意（无关）

- **final finally finalize区别**

  1. final是用于修饰类、方法、变量的
  2. finally是用于处理异常最去做一个一定要做的动作，当然极端情况下可能不会做，比如说线程被interrupt或者虚拟机停止
  3. finalize是Object的方法，gc会在回收这个对象之前调用它的finalize方法

- **String为什么是final的**

  1. 安全性
     + 避免了继承而随意重写方法的安全隐患
     + 避免对String进行修改造成的安全隐患
  2. 效率
     + 不能被修改可以实现字符串常量池，达到复用效果
     + 不能被修改hashCode就不会变，

- **String   StringBuilder StringBuffer区别**

  1. String是不可变的，StringBuilder和StringBuilder是可变的，对于同一个对象可以进行append操作
  2. StringBuffer是线程安全的而StringBuilder多线程环境下是不安全的

- **枚举场景**

##### 4. 设计模式

- **线程安全的单例模式**
- **模板模式**
- **项目里用到的设计模式**