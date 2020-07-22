# 观察者模式Observer
# 一 概述
>观察者模式（Observer），又叫发布-订阅模式（Publish/Subscribe），定义对象间一对多的依赖关系；每当一个对象改变状态，所有依赖于它的对象都会得到通知并进行相应的处理；

# 二 使用示例
```
package cn.http.test;

import java.util.Vector;

/**
 * 主题：管理观察者(实现新增、删除、通知操作)
 *
 * @author:wjm
 * @date:2020/6/28 21:06
 */
public class Subject {
    /**
     * 使用一个数组存放所有观察者，用Vector是线程同步的，比较安全，也可以使用ArrayList，是线程异步的，但不安全；
     */
    private Vector<Observer> observers = new Vector<>();

    /**
     * 新增观察者
     *
     * @param observer
     */
    public void add(Observer observer) {
        this.observers.add(observer);
    }

    /**
     * 移除观察者
     *
     * @param observer
     */
    public void delete(Observer observer) {
        this.observers.remove(observer);
    }

    /**
     * 通知所有观察者
     */
    public void notifyAllObserver() {
        for (Observer observer : observers) {
            observer.update();
        }
    }
}
```
```
package cn.http.test;

/**
 * 具体业务，继承了管理观察者的Subject
 *
 * @author:wjm
 * @date:2020/6/28 21:08
 */
public class BusinessSubject extends Subject {
    /**
     * 具体业务，处理完后通知所有观察者
     */
    public void notifyAllBusinessObserver() {
        System.out.println("do something...");
        super.notifyAllObserver();
    }
}
```
```
package cn.http.test;

/**
 * 观察者
 *
 * @author:wjm
 * @date:2020/6/28 21:06
 */
public interface Observer {
    /**
     * 被观察对象发生预期变化后的执行逻辑
     */
    void update();
}
```
```
package cn.http.test;

/**
 * 具体观察者1
 *
 * @author:wjm
 * @date:2020/6/28 21:10
 */
public class Test1Observer implements Observer {

    @Override
    public void update() {
        System.out.println("Test1Observer收到，进行处理");
    }
}
```
```
package cn.http.test;

/**
 * 具体观察者2
 *
 * @author:wjm
 * @date:2020/6/28 21:10
 */
public class Test2Observer implements Observer {

    @Override
    public void update() {
        System.out.println("Test2Observer收到，进行处理");
    }
}
```
```
package cn.http.test;

/**
 * 具体观察者3
 *
 * @author:wjm
 * @date:2020/6/28 21:10
 */
public class Test3Observer implements Observer {

    @Override
    public void update() {
        System.out.println("Test3Observer收到，进行处理");
    }
}
```
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/28 21:10
 */
public class Test {
    public static void main(String[] args) {
        BusinessSubject subject = new BusinessSubject();

        Observer test1Observer = new Test1Observer();
        Observer test2Observer = new Test2Observer();
        Observer test3Observer = new Test3Observer();

        subject.add(test1Observer);
        subject.add(test2Observer);
        subject.add(test3Observer);

        //具体业务执行完毕后，会通知所有观察者
        subject.notifyAllBusinessObserver();
    }
}
```

# 三 总结
>优点：  
>* 观察者和被观察者是抽象并且互相耦合的；  
>* 可以自定义一套触发机制；

>缺点：  
>* 如果一个被观察者对象有很多的直接和间接的观察者的话，要通知完所有的观察者会花费很多时间；  
>* 如果在观察者和观察目标之间有循环依赖的话，观察目标会触发它们之间进行循环调用，可能导致系统崩溃；  
>* 观察者模式没有相应的机制让观察者知道所观察的目标对象是怎么发生变化的，而仅仅只是知道观察目标发生了变化；

>使用场景：
>* 一个抽象模型有两个方面，其中一个方面依赖于另一个方面，将这些方面封装在独立的对象中使它们可以各自独立地改变和复用；  
>* 一个对象的改变将导致其他一个或多个对象也发生改变，而不知道具体有多少对象将发生改变，可以降低对象之间的耦合度；
>* 一个对象必须通知其他对象，而并不知道这些对象是谁；
>* 需要在系统中创建一个触发链，A对象的行为将影响B对象，B对象的行为将影响C对象……，可以使用观察者模式创建一种链式触发机制；  

>注意事项： 
>* JAVA 中已经有了对观察者模式的支持类；  
>* 避免循环引用；  
>* 如果顺序执行，某一观察者错误会导致系统卡壳，一般采用异步方式；

笔记整理来源：  
[技术文档1](https://www.cnblogs.com/adamjwh/p/10913660.html)  
[技术文档2](https://www.runoob.com/design-pattern/observer-pattern.html)