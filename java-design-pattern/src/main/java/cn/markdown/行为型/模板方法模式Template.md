# 模板方法模式Template
# 一 概述
>模板方法的本质：面向对象，是对事物属性与行为的封装，方法，指的就是行为。模板方法，显而易见是说某个方法充当了模板的作用，其充分利用了抽象类虚实结合的特性：
>* 虚部抽象预留：即抽象类中的抽象方法；
>* 实部固定延续：即抽象类中的protected final method()，子类无法重写，以达到将某种固有行为延续至子类的目的；

>在jdk1.8后，接口中也可以存在default的方法，和抽象类的实部是同样的，但接口的意义并不适合像抽象类虚实结合的特性，因此模板方法模式里面讨论接口，意义不大；

# 二 使用示例
>定义抽象类，其中包含模板方法：feedMilk()-->实部固定延续：
```
package cn.http.test;

/**
 * 描述哺乳动物
 *
 * @author:wjm
 * @date:2020/6/17 12:29
 */
public abstract class Mammal {
    /**
     * 是否是女性
     */
    private boolean female;

    public void setFemale(boolean female) {
        this.female = female;
    }

    /**
     * 使用protected final，使得抽象类的实部固定延续，子类无法重写；<p>
     * 实部固定延续：<p>
     * 1、通常是用来规范虚部，即可被重写的抽象方法，使得虚部是按照一定的逻辑/流程运行；<p>
     * 2、抽象类是描述对象是什么的类，实部固定延续，是将某种固有行位延续至子类；
     */
    protected final void feedMilk() {
        //母的才能喂奶
        if (female) {
            System.out.println("喂奶");
        } else {
            System.out.println("公的不会");
        }
    }

    /**
     * 抽象方法<p>
     * 虚部抽象预留：不是所有哺乳动物move的方式都一样，因此这不属于固有行位，应由其子类实现
     */
    public abstract void move();
}
```
>虚部move()固定延续到子类：
```
package cn.http.test;

/**
 * 人类
 *
 * @author:wjm
 * @date:2020/6/17 12:29
 */
public class Human extends Mammal {
    @Override
    public void move() {
        System.out.println("人类通过走路实现move行为……");
    }
}
```
```
package cn.http.test;

/**
 * 鲸鱼
 *
 * @author:wjm
 * @date:2020/6/17 12:29
 */
public class Whale extends Mammal {
    @Override
    public void move() {
        System.out.println("鲸鱼通过游泳实现move行为……");
    }
}
```
>测试
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/17 12:29
 */
public class Test {
    public static void main(String[] args) {
        Human human = new Human();
        Whale whale = new Whale();
        human.setFemale(false);
        human.feedMilk();
        human.setFemale(true);
        human.feedMilk();
        whale.feedMilk();
        human.move();
        whale.move();
    }
}
```

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/iRnihhHtvx1JYcI1hrd9vg)