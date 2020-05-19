# Java基础之乱七八糟

##### 1. 线程安全的单例模式

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

##### 2. Object基类常见方法

像我们最熟悉的用来比较对象的hashCode(),equals()

还有线程通知机制的wait(),notifiy(),notify All()

还有对象被GC回收前调用的finalize方法()

##### 3. ==和equals

对于基本类型的话使用==去比较

对于对象类型的话可以使用==，也可以使用equals去比较

对象使用==比较的是两个引用是否指向的是同一个对象实例，说白了也就是比较内存地址

对象使用equals得看他有没有重写equals，重写了的话按照它的重写规则去比较，没有重写的话他会调用Object类的equals方法，事实上Object的equals使用的是==去作比较

##### 4. String的equals实现

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



##### 5. final关键字

可以修饰类、方法、变量

1. final修饰的类不能被继承
2. final修饰的方法不能被重写
3. final修饰的变量不能被修改，也叫常量

##### 6. 重写重载的区别

1. 方法重载
   * 方法名、返回值类型（可以是父返回值的子类）、参数列表 全都相同
   * 子类方法抛出的异常不能大于父类的异常
   * 子类访问修饰符的权限不能小于父类的访问权限修饰符
2. 方法重写
   + 方法名必须相同、参数列表必须不同
   + 访问修饰符，返回值类型、抛出的异常随意（无关）

##### 7. final finally finalize区别

1. final是用于修饰类、方法、变量的
2. finally是用于处理异常最去做一个一定要做的动作，当然极端情况下可能不会做，比如说线程被interrupt或者虚拟机停止
3. finalize是Object的方法，gc会在回收这个对象之前调用它的finalize方法

##### 8. String为什么是final的

1. 安全性
   + 避免了继承而随意重写方法的安全隐患
   + 避免对String进行修改造成的安全隐患
2. 效率
   + 不能被修改可以实现字符串常量池，达到复用效果
   + 不能被修改hashCode就不会变，

##### 9. String   StringBuilder StringBuffer区别

1. String是不可变的，StringBuilder和StringBuilder是可变的，对于同一个对象可以进行append操作
2. StringBuffer是线程安全的而StringBuilder多线程环境下是不安全的

- 

##### 4. 设计模式

- **模板模式**
- **项目里用到的设计模式**