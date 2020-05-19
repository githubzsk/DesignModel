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

相同点：都是基于数组实现

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

第二种