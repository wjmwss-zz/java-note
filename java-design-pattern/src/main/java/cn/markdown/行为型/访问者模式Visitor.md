# 访问者模式Visitor
# 一 概述
>场景：通常来说，用于封装数据所用到的pojo类，其只包含get、set，对应的业务逻辑是在Service上完成的；但如果出现多个pojo类都共用一套逻辑时，则应该考虑将逻辑进行抽象，不同类型的pojo类使用不同的业务逻辑，这就是访问者模式：

# 二 使用示例
>阅读笔记时，应先从产品（即main方法）出发，了解产物是如何运作的，从而反推设计；
```
package cn.http.test;

import java.util.Arrays;
import java.util.List;

/**
 * 访问者模式应用
 *
 * @author:wjm
 * @date:2020/7/6 00:13
 */
public class Test {
    public static void main(String[] args) {

        /**
         * 需求：需要对不同的实体类（接收者）进行不同的业务逻辑处理（访问者）：
         * 1、
         *  1)、糖果（实体类）打8折；
         *  2)、水果（实体类）打7折；
         *  3)、饼干（实体类）打6折；
         * 2、
         *  1)、糖果（实体类）检测是否已过期；
         *  2)、水果（实体类）检测是否已过期；
         *  3)、饼干（实体类）检测是否已过期；
         */

        /**
         * 访问者：其访问方法是传入访问者所需的实体类对象，
         * “通过方法重载实现功能上的多态性，即根据不同的实体类对象，用重载来区分不同的逻辑实现方法”；
         */
        Visitor discountVisitor = new DiscountVisitor();
        Visitor overdueVisitor = new OverdueVisitor();

        /**
         * 实体类：既具有商品属性（商品命名、价格等），也具有接收者的功能；
         * 接收者：主动接收访问者（要访问实体类进行业务逻辑处理的类），接收了哪个访问者，则进行哪个访问者的业务逻辑处理；
         */
        Accepter candyProductAccept = new CandyProductAccept("草莓软糖", "3元");
        Accepter cookieProductAccept = new CookieProductAccept("蓝罐曲奇", "98元");
        Accepter fruitProductAccept = new FruitProductAccept("香蕉", "2元");
        List<Accepter> productAccepts = Arrays.asList(candyProductAccept, cookieProductAccept, fruitProductAccept);

        /**
         * 如果实体类不实现接收者接口，而是通过访问者主动访问不同的实体类来进行相应的逻辑处理时，由于方法的重载，会无法确认对应实体类的类型，在编译时就会报错；
         * 例子：
         */

        //没有实现接收者的实体类
        // CandyProduct candyProduct = new CandyProduct();
        // FruitProduct fruitProduct = new FruitProduct();
        // CookieProduct cookieProduct = new CandyProduct();
        // List<Product> products = Arrays.asList(candyProduct, fruitProduct, cookieProduct);

        /**
         * 此时编译就会报错，visit是无法确认实体类的类型的；
         * 假设多了一个实体类类型：VegetableProduct蔬菜商品，但访问者却并没有针对该实体类的重载方法，此时该找哪个重载方法来进行对蔬菜商品的逻辑处理呢？
         * 因此，编译阶段就需要报错；
         */
        // for(Product product:products){
        //     discountVisitor.visit(product);
        //     overdueVisitor.visit(product);
        // }

        /**
         * 为了解决以上的问题：应该想办法把实体类“主动派发”给访问者：
         * 既然访问者无法通过重载确定实体类的类型，那就从实体类（被访问者）入手，
         * 让实体类成为接收者（被访问者），主动接收不同的访问者：
         * 这种方式属于“双派发”：先派发实体类去主动接收访问者（业务逻辑处理），然后在接收的方法里，又把自己派发回给访问者，从而解决“主动派发”的问题
         * 例子：
         */

        for (Accepter productAccepter : productAccepts) {
            productAccepter.accept(discountVisitor);
            productAccepter.accept(overdueVisitor);
        }
    }
}
```
>pojo类（接收者）；
```
package cn.http.test;

/**
 * 接收者（被访问者）
 *
 * @author:wjm
 * @date:2020/7/5 23:37
 */
public interface Accepter {
    /**
     * 接收所有要对实体类进行业务逻辑处理的访问者
     *
     * @param visitor
     */
    void accept(Visitor visitor);
}
```
```
package cn.http.test;

import lombok.Getter;
import lombok.Setter;

/**
 * 实体类：商品
 *
 * @author:wjm
 * @date:2020/7/5 23:24
 */
@Getter
@Setter
public abstract class Product {
    private String name;
    private String price;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }
}
```
```
package cn.http.test;

/**
 * 实体类：糖果类商品
 *
 * @author:wjm
 * @date:2020/7/5 23:24
 */
public class CandyProductAccept extends Product implements Accepter {
    public CandyProductAccept(String name, String price) {
        super(name, price);
    }

    /**
     * 接收不同业务的访问者，然后让访问者对该实体类进行逻辑处理
     *
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```
```
package cn.http.test;

/**
 * 实体类：饼干类商品
 *
 * @author:wjm
 * @date:2020/7/5 23:24
 */
public class CookieProductAccept extends Product implements Accepter {
    public CookieProductAccept(String name, String price) {
        super(name, price);
    }

    /**
     * 接收不同业务的访问者，然后让访问者对该实体类进行逻辑处理
     *
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```
```
package cn.http.test;

/**
 * 实体类：水果类商品
 *
 * @author:wjm
 * @date:2020/7/5 23:24
 */
public class FruitProductAccept extends Product implements Accepter {
    public FruitProductAccept(String name, String price) {
        super(name, price);
    }

    /**
     * 接收不同业务的访问者，然后让访问者对该实体类进行逻辑处理
     *
     * @param visitor
     */
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
```
>对pojo进行业务逻辑处理（访问者）
```
package cn.http.test;

/**
 * 访问者
 *
 * @author:wjm
 * @date:2020/7/5 23:56
 */
public interface Visitor {
    /**
     * 访问糖果商品（针对糖果商品进行业务逻辑处理）
     *
     * @param candyProductAccept
     */
    void visit(CandyProductAccept candyProductAccept);

    /**
     * 访问水果商品（针对水果商品进行业务逻辑处理）
     *
     * @param fruitProductAccept
     */
    void visit(FruitProductAccept fruitProductAccept);

    /**
     * 访问饼干商品（针对饼干商品进行业务逻辑处理）
     *
     * @param cookieProductAccept
     */
    void visit(CookieProductAccept cookieProductAccept);
}
```
```
package cn.http.test;

/**
 * 访问者（打折业务）：专门对不同商品实体类进行打折
 *
 * @author:wjm
 * @date:2020/7/5 23:56
 */
public class DiscountVisitor implements Visitor {

    @Override
    public void visit(CandyProductAccept candyProductAccept) {
        System.out.println("对【" + candyProductAccept.getName() + "】打8折");
    }

    @Override
    public void visit(FruitProductAccept fruitProductAccept) {
        System.out.println("对【" + fruitProductAccept.getName() + "】打7折");
    }

    @Override
    public void visit(CookieProductAccept cookieProductAccept) {
        System.out.println("对【" + cookieProductAccept.getName() + "】打6折");
    }
}
```
```
package cn.http.test;

/**
 * 访问者（是否过期业务）：专门对不同商品实体类检测是否已经过期
 *
 * @author:wjm
 * @date:2020/7/5 23:56
 */
public class OverdueVisitor implements Visitor {

    @Override
    public void visit(CandyProductAccept candyProductAccept) {
        System.out.println("【" + candyProductAccept.getName() + "】未过期，可以进行销售");
    }

    @Override
    public void visit(FruitProductAccept fruitProductAccept) {
        System.out.println("【" + fruitProductAccept.getName() + "】已过期，不允许进行销售");
    }

    @Override
    public void visit(CookieProductAccept cookieProductAccept) {
        System.out.println("【" + cookieProductAccept.getName() + "】未过期，可以进行销售");
    }
}
```

# 三 总结
>访问者模式：
>*  核心：让接收者（例如pojo类）主动接收访问者（例如对pojo进行逻辑处理的类），这样设计的目的是为了解决访问者在运行时不清楚是哪个实体类的问题-->“双派发”：先派发实体类去主动接收访问者（业务逻辑处理），然后在接收的方法里，又把自己派发回给访问者，让访问者对其进行逻辑处理；这样一来，在程序运行时，实体类是认识自己的，就自然可以将自己派发给访问者；  

>优点：
>* 巧妙地用双派发解决了方法重载的多态派发问题；
>* 数据资源（接收者，例如pojo）与业务（访问者，例如Service）彻底解耦，使得业务处理集中化、多态化、亦可扩展；作为数据资源，不应该多才多艺；

源码地址：[我的GitHub](https://github.com/wjmwss/java-program/tree/master/java-design-pattern)  