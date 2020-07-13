# 简单工厂模式与抽象工厂模式Factory
# 一 简单工厂模式
## 1 概述
>工厂，顾名思义，是用来生产对象的，其好处是实现了类与类实例化之间的解耦，类实例化的过程可能是无比复杂的，当这种复杂实例化的类多起来之后，每次实例化都要重复的编写过程代码；有了工厂模式后，工厂隐藏了对象实例化的过程，用户只需通知工厂要生产的类，即可获得要生产的类；

## 2 使用示例
>阅读笔记时，应先从产品（即main方法）出发，了解产物是如何运作的，从而反推设计；  
```
package cn.pattern.factory;

import cn.pattern.factory.simple_factory.producer.AppleFactory;
import cn.pattern.factory.simple_factory.producer.CherryFactory;
import cn.pattern.factory.simple_factory.product.Food;

/**
 * 简单工厂模式应用
 *
 * @author:wjm
 * @date:2020/6/30 23:02
 */
public class Test {
    public static void main(String[] args) {
        /**
         * 需求：工厂隐藏对象复杂的实例化方法，只对外提供一个实例化对象的方法，便可以简单的使用对象工厂生产对象的实例；
         */

        /**
         * 使用不同的对象工厂生产不同的实例，不同的实例都实现/抽象自同一接口/抽象类（多态化的体现）
         */
        Food apple = new AppleFactory().create();
        Food cherry = new CherryFactory().create();

        apple.description();
        cherry.description();
    }
}
```
>一个子工厂生产一个类对象，通过对外提供create()进行实例化，隐藏了复杂的创建对象细节；  

>接下来是工厂模式要生产对象的介绍；
```
package cn.pattern.factory.simple_factory.product;

/**
 * 产出物：食品接口
 *
 * @author:wjm
 * @date:2020/6/30 22:50
 */
public interface Food {
    /**
     * 提供一个方法
     */
    void description();
}
```
```
package cn.pattern.factory.simple_factory.product;

/**
 * 产出物：苹果
 *
 * @author:wjm
 * @date:2020/6/30 22:54
 */
public class Apple implements Food {
    @Override
    public void description() {
        System.out.println("这是苹果");
    }
}
```
```
package cn.pattern.factory.simple_factory.product;

/**
 * 产出物：樱桃
 *
 * @author:wjm
 * @date:2020/6/30 22:54
 */
public class Cherry implements Food {
    @Override
    public void description() {
        System.out.println("这是樱桃");
    }
}
```
>接下来对简单工厂模式中工厂的介绍；
```
package cn.pattern.factory.simple_factory.producer;

import cn.pattern.factory.simple_factory.product.Food;

/**
 * 生产者：工厂
 *
 * @author:wjm
 * @date:2020/6/30 22:57
 */
public interface Factory {
    /**
     * 可以生产所有实现了Food的子类
     *
     * @return
     */
    Food create();
}
```
```
package cn.pattern.factory.simple_factory.producer;

import cn.pattern.factory.simple_factory.product.Apple;
import cn.pattern.factory.simple_factory.product.Food;

/**
 * 生产苹果的工厂
 *
 * @author:wjm
 * @date:2020/6/30 22:58
 */
public class AppleFactory implements Factory {
    @Override
    public Food create() {
        return new Apple();
    }
}
```
```
package cn.pattern.factory.simple_factory.producer;

import cn.pattern.factory.simple_factory.product.Cherry;
import cn.pattern.factory.simple_factory.product.Food;

/**
 * 生产樱桃的工厂
 *
 * @author:wjm
 * @date:2020/6/30 22:58
 */
public class CherryFactory implements Factory {
    @Override
    public Food create() {
        return new Cherry();
    }
}
```
## 3 总结
>* 工厂隐藏了对象实例化的过程，用户只需通知工厂要生产的类，即可获得要生产的类；  
>* 一个类对象对应一个子工厂，若生产的类对象繁多，则对应的子工厂也会很多；

# 二 抽象工厂模式
## 1 概述
>抽象工厂，意味着工厂的泛化，也就是说对多个工厂共通行为的抽取及概括；  

>抽象工厂与工厂的区别：抽象工厂相比普通工厂定义了更多的抽象行为，也就是多个工厂方法于抽象工厂中，抽象工厂模式是工厂模式的变种；

## 2 使用示例
>需求：  

食品 | 一级品 | 二级品 | 三级品
:-: | :-: | :-: | :-: 
水果 | 一级水果 | 二级水果 | 三级水果 
牛排 | 一级牛排 | 二级牛排 | 三级牛排 

>根据以上需求，如果按照普通工厂来生产食品，那就需要每个等级每种食品都分别有相应的工厂来创建；  
>由于等级和食品是有关联的，可以抽象为一级食品、二级食品、三级食品；
>使用抽象工厂：抽象为水果工厂、牛排工厂，每个工厂分别提供生产一级、二级、三级对应工厂的食品，代码示例：

>阅读笔记时，应先从产品（即main方法）出发，了解产物是如何运作的，从而反推设计；  
```
package cn.pattern.factory.abstract_factory;

import cn.pattern.factory.abstract_factory.producer.Factory;
import cn.pattern.factory.abstract_factory.producer.FruitFactory;
import cn.pattern.factory.abstract_factory.producer.SteakFactory;
import cn.pattern.factory.abstract_factory.product.Food;

/**
 * 抽象工厂模式应用
 *
 * @author:wjm
 * @date:2020/6/30 23:02
 */
public class Test {
    public static void main(String[] args) {
        /**
         * 根据需求，产出物的结构为：xx级xx食品；<p>
         * 先在创建工厂的时候，确定食品的类型，再通过工厂创建xx级别的食品，当然，也可以反过来；
         */
        Factory fruitFactory = new FruitFactory();
        Food bestFruit = fruitFactory.createBestFood();
        Food mediumFruit = fruitFactory.createMediumFood();
        Food worstFruit = fruitFactory.createWorstFood();

        Factory steakFactory = new SteakFactory();
        Food bestSteak = steakFactory.createBestFood();
        Food mediumSteak = steakFactory.createMediumFood();
        Food worstSteak = steakFactory.createWorstFood();

        bestFruit.description();
        mediumFruit.description();
        worstFruit.description();
        bestSteak.description();
        mediumSteak.description();
        worstSteak.description();
    }
}
```
>抽象工厂利用了两两组合的特性，从而可以产生更多类型的对象，相比简单工厂一个类需要对应一个工厂要更友好；

>接下来是工厂模式要生产对象的介绍；
```
package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：食品接口
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public interface Food {
    /**
     * 提供一个方法
     */
    void description();
}
```
```
package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：一级水果
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class FruitBest implements Food {
    @Override
    public void description() {
        System.out.println("这是一级水果");
    }
}
```
```
package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：二级水果
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class FruitMedium implements Food {
    @Override
    public void description() {
        System.out.println("这是二级水果");
    }
}
```
```
package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：三级水果
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class FruitWorst implements Food{
    @Override
    public void description() {
        System.out.println("这是三级水果");
    }
}
```
```
package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：一级牛排
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class SteakBest implements Food{
    @Override
    public void description() {
        System.out.println("这是一级牛排");
    }
}
```
```
package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：二级牛排
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class SteakMedium implements Food{
    @Override
    public void description() {
        System.out.println("这是二级牛排");
    }
}
```
```
package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：三级牛排
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class SteakWorst implements Food{
    @Override
    public void description() {
        System.out.println("这是三级牛排");
    }
}
```
>接下来对抽象工厂模式中工厂的介绍；
```
package cn.pattern.factory.abstract_factory.producer;

import cn.pattern.factory.abstract_factory.product.Food;

/**
 * 生产者：工厂
 *
 * @author:wjm
 * @date:2020/7/1 20:18
 */
public interface Factory {
    /**
     * 生产一级食品
     *
     * @return
     */
    Food createBestFood();

    /**
     * 生产二级食品
     *
     * @return
     */
    Food createMediumFood();

    /**
     * 生产三级食品
     *
     * @return
     */
    Food createWorstFood();
}
```
```
package cn.pattern.factory.abstract_factory.producer;

import cn.pattern.factory.abstract_factory.product.Food;
import cn.pattern.factory.abstract_factory.product.FruitBest;
import cn.pattern.factory.abstract_factory.product.FruitMedium;
import cn.pattern.factory.abstract_factory.product.FruitWorst;

/**
 * 生产者：不同级别的水果
 *
 * @author:wjm
 * @date:2020/7/1 20:18
 */
public class FruitFactory implements Factory{

    @Override
    public Food createBestFood() {
        return new FruitBest();
    }

    @Override
    public Food createMediumFood() {
        return new FruitMedium();
    }

    @Override
    public Food createWorstFood() {
        return new FruitWorst();
    }
}
```
```
package cn.pattern.factory.abstract_factory.producer;

import cn.pattern.factory.abstract_factory.product.Food;
import cn.pattern.factory.abstract_factory.product.SteakBest;
import cn.pattern.factory.abstract_factory.product.SteakMedium;
import cn.pattern.factory.abstract_factory.product.SteakWorst;

/**
 * 生产者：不同级别的牛排
 *
 * @author:wjm
 * @date:2020/7/1 20:18
 */
public class SteakFactory implements Factory {

    @Override
    public Food createBestFood() {
        return new SteakBest();
    }

    @Override
    public Food createMediumFood() {
        return new SteakMedium();
    }

    @Override
    public Food createWorstFood() {
        return new SteakWorst();
    }
}
```

## 3 总结
>使用各族工厂对种类繁多的产品进行了划分、归类，产品虽然繁多，但总得有等级、品牌、型号之分，以各族工厂和产品线划界，分而治之，***横向拆分产品家族，纵向则拆分产品等级***。

源码地址：[我的GitHub](https://github.com/wjmwss/java-program/tree/master/java-design-pattern)  
