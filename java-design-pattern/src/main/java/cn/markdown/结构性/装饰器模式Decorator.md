# 装饰器模式Decorator
# 一 概述
>装饰器：顾名思义为将某个对象，通过不同的装饰后，产出不同的结果；

# 二 使用示例
>构造一个抽象装饰器、需要装饰的行为
```
package cn.http.test;

/**
 * 提供cooking的行为
 *
 * @author:wjm
 * @date:2020/6/17 20:07
 */
public interface Cook {
    /**
     * 烹饪行为
     */
    void cooking();
}
```
```
package cn.http.test;

/**
 * 装饰器：调味料
 *
 * @author:wjm
 * @date:2020/6/17 20:07
 */
public abstract class Spices implements Cook {
    protected Cook cook;

    public Spices(Cook cook) {
        this.cook = cook;
    }

    /**
     * 装饰器不提供任何装饰，而是由装饰器的子类进行具体的装饰
     */
    @Override
    public void cooking() {
        cook.cooking();
    }
}
```
>构造具体的装饰器
```
package cn.http.test;

/**
 * 具体装饰：加盐
 *
 * @author:wjm
 * @date:2020/6/17 20:07
 */
public class Salt extends Spices {

    public Salt(Cook cook) {
        super(cook);
    }

    @Override
    public void cooking() {
        System.out.println("加盐");
        cook.cooking();
    }
}
```
```
package cn.http.test;

/**
 * 具体装饰：加酱油
 *
 * @author:wjm
 * @date:2020/6/17 20:07
 */
public class Sauce extends Spices {

    public Sauce(Cook cook) {
        super(cook);
    }

    @Override
    public void cooking() {
        System.out.println("加酱油");
        cook.cooking();
    }
}
```
```
package cn.http.test;

/**
 * 具体装饰：加糖
 *
 * @author:wjm
 * @date:2020/6/17 20:07
 */
public class Sugar extends Spices {

    public Sugar(Cook cook) {
        super(cook);
    }

    @Override
    public void cooking() {
        System.out.println("加糖");
        cook.cooking();
    }
}
```
>被装饰的对象
```
package cn.http.test;

/**
 * 被装饰的对象：饭
 *
 * @author:wjm
 * @date:2020/6/17 20:07
 */
public class Rice implements Cook {
    @Override
    public void cooking() {
        System.out.println("饭");
    }
}
```
>应用
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/17 20:07
 */
public class Test {
    public static void main(String[] args) {
        Cook rice1 = new Salt(new Sauce(new Sugar(new Rice())));
        Cook rice2 = new Sauce(new Sugar(new Rice()));
        Cook rice3 = new Sugar(new Salt(new Sugar(new Rice())));
        Cook rice4 = new Sugar(new Rice());
        rice1.cooking();
        rice2.cooking();
        rice3.cooking();
        rice4.cooking();
    }
}
```

# 三 总结
>Java IO包里面的流处理类，就是装饰器模式的典型应用，如：  
```
 new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
```
>装饰器与建造者：两者都是通过组合不同的过程（装饰过程、建造过程），从而产生不同的结果：每个具体的装饰器各司其职，不做和自己不相关的事，然后把部件层层叠加，并根据需求组装成型，以达最终的装饰目的。

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/v4jEeegy911hqhqnIC8s5w)