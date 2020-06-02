# Java基础之集合

##### 1. 集合的分类

1. Collection顶级接口
   - List接口
     - Vector
     - ArrayList
     - LinkedList
   - Set接口
     - HashSet
     - LinkedHashSet
     - TreeSet
2. Map顶级接口
   - Hashtable
   - HashMap
   - LinkedHashMap
   - TreeMap

##### 2. 集合和数组的区别

1. 数组在声明的时候就已经规定了数据类型，而集合不需要
2. 数组只能存一种数据类型，而集合可以存Object类型
3. 数组可以存基本类型，而集合不可以存基本类型
4. 数组的大小在初始化的时候就已经固定，而集合容量是可以动态增减

##### 3. Comparable 和 Comparator

Comparable叫做内部比较器，由需要排序的类实现Comparable并重写它的compareTo方法，之后就可以利用集合的工具类Collecttions.sort（list）进行排序了

```` java
public class Persion implements Comparable<Persion>{
    private Integer age;
    private String name;
    private Integer score;
     @Override
    public int compareTo(Persion o) {
        return this.getAge() - o.getAge();
    }
}
 List<Persion> list = new ArrayList<>();
        list.add(new Persion(22,"zsk",89));
        list.add(new Persion(18,"zsk",20));
        list.add(new Persion(27,"zsk",83));
        list.add(new Persion(11,"zsk",75));
        list.add(new Persion(31,"zsk",98));
        Collections.sort(list);
````

Comparator叫做外部比较器，不需要有需要被排序的类实现这个接口，对代码的侵入性更小

```java
 Collections.sort(list, new Comparator<Persion>() {
            @Override
            public int compare(Persion o1, Persion o2) {
                return o1.getScore()-o2.getScore();
            }
        });
```

##### 4. Vector & ArrayList 

相同点：都是基于数组实现，初始化都是10	

不同点：Vector对外的方法都是假synchronized的，线程安全，而ArrayList线程不安全

​				Vector扩容的话是翻倍，ArrayList扩容的话是+50%

##### 5. ArrayList & LinkedList

ArrayList：基于数组，查找的话基于简单容易，而插入删除的话，会使得后面的数据整体移动，所以增删性能差

LinkedList：基于链表，刚好增删查性能和数组相反

##### 6. List和Set

第一点： list有序而set无序，之所以有这个特点，是因为它实现方式决定

List的实现类有ArrayList、LinkedList、Vector、数组跟链表，当然是有序了

Set的实现类有HashSet、TreeSet，哈希和树有它自己的算法，必然是无序了

第二点：List它是可以去存重复对象的，而Set则不能存重复，我们可以利用这一个特性来对list集合去重

第三点：List是可以存多个null值的，而Set值能存一个null值，这个也是因为它的实现方式决定的

##### 7. 遍历集合删除元素

1. 普通for循环

   对于连续重复的元素，删不完，因为存在删除之后长度减小，下标改变的情况，如果必须用普通for循环，i--

2. 增强for循环(for each)

   如果使用foreach遍历删除的话直接就抛异常了，不能使用，它的原理是foreach底层使用了迭代器，而且维护了一个expectedModCount，一旦发现modCount和expectedModCount不一致，直接抛异常

3. 迭代器

   推荐使用迭代器，对于List来说，可以使用普通for循环+迭代器，对于Set集合来说，只能使用迭代器(Set中没有get方法，无法使用普通for)

##### 8. Arraylist排序

通过内部比较器Comparable和外部比较器Comparator去实现

##### 9. ArrayList去重

想那种用循环来处理的，我就不说了，我说点其他形式的。

第一种 Java8新特性，Stream的distinct方法去重，简单方便

```java
 List list = new ArrayList();
        list.add("zsk");
        list.add("zz");
        list.add("zsk");
        list.add("zsk");
        list.add("zz");
        list.add("zsk");
        list.stream().map(x ->x+" ").distinct(). forEach(System.out::print);
```

第二种 基于HashSet的特性，构造一个HashSet将List传进去

##### 10.为什么要有迭代器

迭代器提供了遍历集合的方式，使用迭代器不必关心容器的具体实现，只要属于Collection都可以使用迭代器去遍历，像List可以for循环去遍历，而set就没办法for循环了，只能用迭代器遍历。

##### 11. 为什么要重写hashcode和equals/重写equals为什么一定要重写hashCode

如果说确定对象不会作为哈希表的key，那你完全可以不用重写，相反的那一定要重写

如果你不重写的话，那么产生的严重后果就是，你可以把一个对象put进去，但是你没办法用另一个跟他equals的对象把他get出来，因为没有重写hashCode，Object的hashCode它的实现实际上是返回对象的内存地址，那两个对象的内存地址肯定不一样，那肯定能存进去取不出来，当然这个也跟Hashmap的数据结构以及hash算法有关（引导面试官问HashMap相关）

##### 12. 遍历HashMap

```java
Set<String> strings = map.keySet();
Collection<Integer> values = map.values();
Set<Map.Entry<String, Integer>> entries = map.entrySet();
```

##### 13.对象做HashMap的Key值，需要注意什么？

必须要重写hashCode和equals方法，hashCode参与HashMap的哈希算法

##### 14. HashMap的理解

从HashMap数据结构、hash算法去谈。

##### 15. hashcode的作用

1.可以返回对象哈希码值，也叫作散列码，可以用来支持哈希表，比如说HashMap、Hashtable等

2.可以提高哈希表的性能

##### 16.HashMap的hash算法

```
static final int hash(Object key) {
    int h;
    return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
}
```



在jdk8的时候，HashMap中他的hash算法是这样实现的，它被定义成了返回值为int类型名字就叫hash的一个方法。这个方法看着很简单只有两行,第一行声明了一个int类型的一个变量，第二行呢是一个三目运算，这个三木运算是这样表达的：它说你传进来的key如果是null的话那我直接给你返回一个0，你传进来的key如果不是null的话，那我取你key的hashCode值，与你key的hashCode值右移十六位做一个异或操作，从这个三目运算里面我们至少可以得出两个结论，第一点HashMap中可以储存key值为null的键值对，而且可以确定的是它的位置就在数组下标为0的那个位置上。第二点呢key值不为null的话，它又是取hashCode值，又是右移，又是异或的，事实上他这样做只有一个目的，让高低位都参与运算让最终的结果尽可能的均匀散列，从空间的角度上提高HashMap的效率。

##### 17. HashMap结构

在jdk7以及之前，HashMap由数组＋链表构成，

在jdk8的时候，HashMap由数组+链表+红黑树构成

事实上HashMap的本质就是一个动态数组，而这个数组的每个格子上存的可能是一对键值对，由可能是多对键值对构成的链表或者红黑树，当然呢我这里说成键值对只是为了好表达而已。事实上这个键值对他是被Node或者TreeNode这种对象封装起来的。

##### 18. HashMap的put方法流程

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        //如果首次put值，先初始化数组 ，然后再存值
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        //如果没出现hash冲突，直接往这个位置放就完事儿
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
     	//如果出现了hash冲突，请往下看
        else {
            Node<K,V> e; K k;
            //如果key值一样，做替换操作
            if (p.hash == hash &&
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
             //如果key值不一样，并且数组的这个格子放的是一个红黑树，那么就按树的方式存值就完事   
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            //如果key值不一样，而且也不是红黑树，他只能是一条链表
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                    //一直循环到链表的next节点为空，便把这个值存到next节点
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            //如果链表长度大于8了，可能把它转化为红黑树，注意这里是可能，而不是一定，
                            //因为treeifyBin方法内部判断HashMap存的键值对的个数是不是大于MIN_TREEIFY_CAPACITY（64）
                            //如果超过64个了再转换成树，如果不够64个键值对，只是做了一个resize()操作，
                            //所以链表转红黑树的条件有两个，一 链表长度大于8，二 容量大于64 
                            treeifyBin(tab, hash);
                        break;
                    }
                    //如果key重复，做替换操作
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            //如果做了替换成
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
```

##### 19. HashMap的resize()扩容方法

```java
final Node<K,V>[] resize() {
    Node<K,V>[] oldTab = table;
    int oldCap = (oldTab == null) ? 0 : oldTab.length;
    int oldThr = threshold;
    int newCap, newThr = 0;
    if (oldCap > 0) {
        //看容量是超过2的30次方，超过不用翻倍，否则就溢出了，直接改为2的31次方减1
        if (oldCap >= MAXIMUM_CAPACITY) {
            threshold = Integer.MAX_VALUE;
            return oldTab;
        }
        //如果没有达到最大，容量扩大二倍
        else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                 oldCap >= DEFAULT_INITIAL_CAPACITY)
            newThr = oldThr << 1; // double threshold
    }
    else if (oldThr > 0) // initial capacity was placed in threshold
        newCap = oldThr;
    else {               // zero initial threshold signifies using defaults
        newCap = DEFAULT_INITIAL_CAPACITY;
        newThr = (int)(DEFAULT_LOAD_FACTOR * DEFAULT_INITIAL_CAPACITY);
    }
    if (newThr == 0) {
        float ft = (float)newCap * loadFactor;
        newThr = (newCap < MAXIMUM_CAPACITY && ft < (float)MAXIMUM_CAPACITY ?
                  (int)ft : Integer.MAX_VALUE);
    }
    threshold = newThr;
    @SuppressWarnings({"rawtypes","unchecked"})
        Node<K,V>[] newTab = (Node<K,V>[])new Node[newCap];
    table = newTab;
    if (oldTab != null) {
        for (int j = 0; j < oldCap; ++j) {
            Node<K,V> e;
            if ((e = oldTab[j]) != null) {
                oldTab[j] = null;
                if (e.next == null)
                    newTab[e.hash & (newCap - 1)] = e;
                else if (e instanceof TreeNode)
                    ((TreeNode<K,V>)e).split(this, newTab, j, oldCap);
                else { // preserve order
                    Node<K,V> loHead = null, loTail = null;
                    Node<K,V> hiHead = null, hiTail = null;
                    Node<K,V> next;
                    do {
                        next = e.next;
                        if ((e.hash & oldCap) == 0) {
                            if (loTail == null)
                                loHead = e;
                            else
                                loTail.next = e;
                            loTail = e;
                        }
                        else {
                            if (hiTail == null)
                                hiHead = e;
                            else
                                hiTail.next = e;
                            hiTail = e;
                        }
                    } while ((e = next) != null);
                    if (loTail != null) {
                        loTail.next = null;
                        newTab[j] = loHead;
                    }
                    if (hiTail != null) {
                        hiTail.next = null;
                        newTab[j + oldCap] = hiHead;
                    }
                }
            }
        }
    }
    return newTab;
}
```

##### 20. HashMap长度为什么2的n次方

- 第一点 ：只有当length长度为2的n次方时候,hash &（length-1）与hash % length（取模运算）是等价的，用位运算来代替取模运算，效率时间提高。
-  第二点 ：当length为2的n次方时，length-1的二进制全为1，与hash值做与运算时，才能是最终的结果尽可能的均匀散列，空间效率提高

##### 21. HashMap的默认负载因子为什么是0.75，初始容量为什么是16

应该是空间利用率和hash碰撞的一个折中，网上有技术文档称是根据泊松分布公式计算出来的

##### 22. JDK8中HashMap为什么引入红黑树？

当hash冲突严重时候，链表太长查询效率变差，不如红黑树的查询效率，所以引入红黑树弥补链表的短板

##### 23. HashMap中产生Hash冲突时，插入链表头部还是尾部

```java
//选自jdk7下HashMap的  在头
//put(K key, V value)-->
//addEntry(hash, key, value, i)-->
//createEntry(hash, key, value, bucketIndex);
 void createEntry(int hash, K key, V value, int bucketIndex) {
 		//取出当前的这个链表，并将其赋值给临时变量e
        Entry<K,V> e = table[bucketIndex];
        //将新的值放在数组的这个位置，并把刚才拿出来之前的e（那条链表），放在这个新来的next节点上
        //既 插入的新值在链表头部
        table[bucketIndex] = new Entry<>(hash, key, value, e);
        size++;
    }



//选自jdk8下HashMap的putVal方法片段 在尾
for (int binCount = 0; ; ++binCount) {
  if ((e = p.next) == null) {
      //一直循环到链表的next节点为空，便把这个值存到next节点，既插入到该链表尾部。
          p.next = newNode(hash, key, value, null);
           if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                     treeifyBin(tab, hash);
                       break;
      }

```

##### 24. HashMap中链表和红黑树转换的条件是什么？

- 链表 -> 红黑树 ：链表的长度大于8 并且整个HashMap中元素个数大于64，网上很多博客都只说大于8，没说全部元素的问题，事实上如果元素个数不超过64，如果链表长度大于8了，他也只是做一个resize操作
- 红黑树->链表 ： 当红黑树中元素的个是退化到6的时候就转为链表

##### 25. 为什么大于8链表转红黑树，而不是7或者9

当然如果冲突比较小就用红黑树的话，红黑树的左右旋也是相当耗性能的，所以经过计算权衡得出了8

##### 26. jdk7并发条件下HashMap存在什么问题

##### 27. jdk7HashMap中为什么会产生死循环（链表闭环）,形成的过程是什么

##### 28. ConcurrentHashMap相关