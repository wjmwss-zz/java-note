# 命令模式Command
# 一 概述
>建造者模式：用于对复杂对象的建造，与工厂模式不同的是，建造者的目的在于把复杂构造过程从不同对象展现中抽离出来，使得同样的构造工序可以展现出不同的产品对象。

# 二 使用示例

## 2.1 包含指挥者，最终使用指挥者建造产品；
>由最终建造的产品、负责建造的角色、指挥具体如何建造的角色组成；
```
package cn.http.test;

/**
 * 产品：汽车
 *
 * @author:wjm
 * @date:2020/7/1 22:52
 */
public class Car {
    /**
     * 轮胎
     */
    private String tyre;

    /**
     * 窗户
     */
    private String window;

    /**
     * 座椅
     */
    private String chair;

    /**
     * 发动机
     */
    private String engine;

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
```
```
package cn.http.test;

/**
 * 产品建造者
 *
 * @author:wjm
 * @date:2020/7/1 22:53
 */
public interface CarBuilder {
    /**
     * 建造轮胎
     */
    void buildTyre();

    /**
     * 建造窗户
     */
    void buildWindow();

    /**
     * 建造座椅
     */
    void buildChair();

    /**
     * 建造发动机
     */
    void buildEngine();

    /**
     * 返回构建好的汽车
     *
     * @return
     */
    Car createCar();
}
```
```
package cn.http.test;

/**
 * 奔驰建造者
 *
 * @author:wjm
 * @date:2020/7/1 22:53
 */
public class BenZCarBuilder implements CarBuilder {
    /**
     * 建造者与产品进行沟通
     */
    private Car car = new Car();

    @Override
    public void buildTyre() {
        String tyre = "轮胎";
        System.out.println("建造了【" + tyre + "】");
        car.setTyre(tyre);
    }

    @Override
    public void buildWindow() {
        String window = "窗户";
        System.out.println("建造了【" + window + "】");
        car.setWindow(window);
    }

    @Override
    public void buildChair() {
        String chair = "座椅";
        System.out.println("建造了【" + chair + "】");
        car.setChair(chair);
    }

    @Override
    public void buildEngine() {
        String engine = "发动机";
        System.out.println("建造了【" + engine + "】");
        car.setEngine(engine);
    }

    @Override
    public Car createCar() {
        System.out.println("奔驰已建造完毕");
        return car;
    }
}
```
```
package cn.http.test;

/**
 * 宝马建造者
 *
 * @author:wjm
 * @date:2020/7/1 22:53
 */
public class BMWCarBuilder implements CarBuilder {
    /**
     * 建造者与产品进行沟通
     */
    private Car car = new Car();

    @Override
    public void buildTyre() {
        String tyre = "轮胎";
        System.out.println("建造了【" + tyre + "】");
        car.setTyre(tyre);
    }

    @Override
    public void buildWindow() {
        String window = "窗户";
        System.out.println("建造了【" + window + "】");
        car.setWindow(window);
    }

    @Override
    public void buildChair() {
        String chair = "座椅";
        System.out.println("建造了【" + chair + "】");
        car.setChair(chair);
    }

    @Override
    public void buildEngine() {
        String engine = "发动机";
        System.out.println("建造了【" + engine + "】");
        car.setEngine(engine);
    }

    @Override
    public Car createCar() {
        System.out.println("宝马已建造完毕");
        return car;
    }
}
```
```
package cn.http.test;

/**
 * 指挥者：控制产品的建造流程
 *
 * @author:wjm
 * @date:2020/7/1 23:02
 */
public class Direct {
    private CarBuilder carBuilder;

    /**
     * 指挥者与产品建造者进行沟通
     *
     * @param carBuilder
     */
    public Direct(CarBuilder carBuilder) {
        this.carBuilder = carBuilder;
    }

    /**
     * 指挥建造过程
     *
     * @return
     */
    public Car createCar() {
        carBuilder.buildChair();
        carBuilder.buildEngine();
        carBuilder.buildWindow();
        carBuilder.buildTyre();
        return carBuilder.createCar();
    }
}
```
>通过指挥者来建造产品，只需告知产品的类型，无需了解复杂的建造细节，便可以建造出产品；
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/7/1 23:05
 */
public class Test {
    public static void main(String[] args) {
        Direct direct1 = new Direct(new BenZCarBuilder());
        direct1.createCar();

        System.out.println();

        Direct direct2 = new Direct(new BMWCarBuilder());
        direct2.createCar();
    }
}
```

## 2.2 不包含指挥者，最终建造者使用链式调用来建造产品；
>产品、建造者；
```
package cn.http.test;

/**
 * 产品：汽车
 *
 * @author:wjm
 * @date:2020/7/1 22:52
 */
public class Car {
    /**
     * 轮胎
     */
    private String tyre;

    /**
     * 窗户
     */
    private String window;

    /**
     * 座椅
     */
    private String chair;

    /**
     * 发动机
     */
    private String engine;

    public void setTyre(String tyre) {
        this.tyre = tyre;
    }

    public void setWindow(String window) {
        this.window = window;
    }

    public void setChair(String chair) {
        this.chair = chair;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
```
```
package cn.http.test;

/**
 * 产品建造者
 *
 * @author:wjm
 * @date:2020/7/1 22:53
 */
public interface CarBuilder {
    /**
     * 建造轮胎
     */
    CarBuilder buildTyre();

    /**
     * 建造窗户
     */
    CarBuilder buildWindow();

    /**
     * 建造座椅
     */
    CarBuilder buildChair();

    /**
     * 建造发动机
     */
    CarBuilder buildEngine();

    /**
     * 返回构建好的汽车
     *
     * @return
     */
    Car createCar();
}
```
```
package cn.http.test;

/**
 * 宝马建造者
 *
 * @author:wjm
 * @date:2020/7/1 22:53
 */
public class BMWCarBuilder implements CarBuilder {
    /**
     * 建造者与产品进行沟通
     */
    private Car car = new Car();

    /**
     * @return
     */
    @Override
    public BMWCarBuilder buildTyre() {
        String tyre = "轮胎";
        System.out.println("建造了【" + tyre + "】");
        car.setTyre(tyre);
        return this;
    }

    @Override
    public BMWCarBuilder buildWindow() {
        String window = "窗户";
        System.out.println("建造了【" + window + "】");
        car.setWindow(window);
        return this;
    }

    @Override
    public BMWCarBuilder buildChair() {
        String chair = "座椅";
        System.out.println("建造了【" + chair + "】");
        car.setChair(chair);
        return this;
    }

    @Override
    public BMWCarBuilder buildEngine() {
        String engine = "发动机";
        System.out.println("建造了【" + engine + "】");
        car.setEngine(engine);
        return this;
    }

    @Override
    public Car createCar() {
        System.out.println("宝马已建造完毕");
        return car;
    }
}
```
```
package cn.http.test;

/**
 * 奔驰建造者
 *
 * @author:wjm
 * @date:2020/7/1 22:53
 */
public class BenZCarBuilder implements CarBuilder {
    /**
     * 建造者与产品进行沟通
     */
    private Car car = new Car();

    @Override
    public BenZCarBuilder buildTyre() {
        String tyre = "轮胎";
        System.out.println("建造了【" + tyre + "】");
        car.setTyre(tyre);
        return this;
    }

    @Override
    public BenZCarBuilder buildWindow() {
        String window = "窗户";
        System.out.println("建造了【" + window + "】");
        car.setWindow(window);
        return this;
    }

    @Override
    public BenZCarBuilder buildChair() {
        String chair = "座椅";
        System.out.println("建造了【" + chair + "】");
        car.setChair(chair);
        return this;
    }

    @Override
    public BenZCarBuilder buildEngine() {
        String engine = "发动机";
        System.out.println("建造了【" + engine + "】");
        car.setEngine(engine);
        return this;
    }

    @Override
    public Car createCar() {
        System.out.println("奔驰已建造完毕");
        return car;
    }
}
```
>可以看到建造者的建造方法里面都返回了建造者对象，因此可以进行链式调用，最终的create结束整条链条；
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/7/1 23:05
 */
public class Test {
    public static void main(String[] args) {
        BenZCarBuilder benZCarBuilder = new BenZCarBuilder();
        Car benZ = benZCarBuilder.buildChair().buildEngine().buildWindow().buildTyre().createCar();

        System.out.println();

        BMWCarBuilder bmwCarBuilder = new BMWCarBuilder();
        Car bmw = bmwCarBuilder.buildTyre().buildWindow().buildEngine().buildChair().createCar();
    }
}
```

# 三 总结
>* 使用场景
>   * 隔离复杂对象的创建和使用，相同的方法，不同执行顺序，产生不同事件结果；
>   * 多个部件都可以装配到一个对象中，但产生的运行结果不相同；
>   * 产品类非常复杂或者产品类因为调用顺序不同而产生不同作用；
>   * 初始化一个对象时，构造函数的参数过多，或者很多参数具有默认值，可以使用链式调用的方式设置对象参数；
>   * 建造者模式不适合创建差异性很大的产品类；
>   * 产品内部变化复杂，会导致需要定义很多具体建造者类来实现变化，这增加了项目中类的数量，增加了系统的理解难度和运行成本；
>   * 需要生成的产品对象有复杂的内部结构，这些产品对象具备共性；

>* 作用：在用户不知道对象的建造过程和细节的情况下就可以直接创建复杂的对象；
>   * 用户只需要给出指定复杂对象的类型和内容；
>   * 建造者模式负责按顺序创建复杂对象（把内部的建造过程和细节隐藏了起来）；

笔记整理来源：[技术文档](https://www.jianshu.com/p/3d1c9ffb0a28)