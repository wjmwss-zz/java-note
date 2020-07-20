# 桥接模式Bridge
# 一 概述
>桥接模式，是把***抽象类*** 与 ***接口***连接起来，这两种对象可以拥有无数个继承、实现对象，这两种对象之间可以任意调换，产生不同的功能；  

# 二 使用示例
>被桥接的接口；
```
package cn.http.test;

/**
 * 形状，提供不同的形状功能
 *
 * @author:wjm
 * @date:2020/7/1 00:12
 */
public interface Shape {
    /**
     * 创建形状
     */
    void createShape();
}
```
```
package cn.http.test;

/**
 * 提供一个圆形
 *
 * @author:wjm
 * @date:2020/7/1 00:12
 */
public class Circular implements Shape{
    @Override
    public void createShape() {
        System.out.println("圆");
    }
}
```
```
package cn.http.test;

/**
 * 提供一个椭圆形
 *
 * @author:wjm
 * @date:2020/7/1 00:12
 */
public class Oval implements Shape{
    @Override
    public void createShape() {
        System.out.println("椭圆");
    }
}
```
```
package cn.http.test;

/**
 * 提供一个三角形
 *
 * @author:wjm
 * @date:2020/7/1 00:12
 */
public class Square implements Shape{
    @Override
    public void createShape() {
        System.out.println("三角形");
    }
}
```
>被桥接的抽象类，注：***抽象类中引入接口的依赖***；
```
package cn.http.test;

/**
 * 画笔，提供可以画出不同形状的画笔
 *
 * @author:wjm
 * @date:2020/7/1 00:18
 */
public abstract class Pen {
    /**
     * 桥接模式的核心：抽象类注入接口，两者进行桥接，两者可以相互变换调用，产生不同的结果<p>
     * 声明接口为protected，只供其子类调用
     */
    protected Shape shape;

    public Pen(Shape shape) {
        this.shape = shape;
    }

    public abstract void draw();
}
```
```
package cn.http.test;

/**
 * 字迹为黑色的画笔
 *
 * @author:wjm
 * @date:2020/7/1 00:18
 */
public class BlackPen extends Pen {
    public BlackPen(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        System.out.print("黑画笔绘画了：");
        shape.createShape();
    }
}
```
```
package cn.http.test;

/**
 * 字迹为白色的画笔
 *
 * @author:wjm
 * @date:2020/7/1 00:18
 */
public class WhitePen extends Pen {
    public WhitePen(Shape shape) {
        super(shape);
    }

    @Override
    public void draw() {
        System.out.print("白画笔绘画了：");
        shape.createShape();
    }
}
```
>应用；
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/7/1 00:21
 */
public class Test {
    public static void main(String[] args) {
        /**
         * 桥接模式的好处：被桥接的两者可以相互变换调用，产生：<p>
         * 笛卡儿积组合：<p>
         * 颜色集合={黑，白} <p>
         * 形状集合={圆形，椭圆形，三角形} <p>
         * 那么这两个集合的笛卡尔积为 <p>
         * 【 <p>
         *  (黑，圆形)， (黑，椭圆形)， (黑，三角形)， <p>
         *  (白，圆形)， (白，椭圆形)， (白，三角形) <p>
         *  】
         */

        //白画笔能画出的所有图案
        new WhitePen(new Circular()).draw();
        new WhitePen(new Oval()).draw();
        new WhitePen(new Square()).draw();

        //黑画笔能画出的所有图案
        new BlackPen(new Circular()).draw();
        new BlackPen(new Oval()).draw();
        new BlackPen(new Square()).draw();
    }
}
```

# 三 总结
>桥接模式之所以可以实现被桥接的对象之间相互变换调用，核心在于抽象类里注入了接口对象；  

>区别于策略模式：  
>策略模式与桥接模式高度相似；
>* 策略模式：类似电脑上的USB接口，USB接口可以与符合USB规范的任何设备连接，例如键盘、鼠标等，但USB接口却耦合于宿主机（USB接口需注入宿主机对象），***两者之间只有一边是解耦的***（USB接口），宿主机无法替换；
>* 桥接模式：例如 画笔（抽象类）与绘图形状（接口）相结合，可以产生无数支画笔，如专门画圆的画笔、专门画正方形的画笔等，桥接的两方可以任意替换，从而产生不同的结果，***实现双边解耦、分离、脱钩***；

>策略与桥接各有特点，不分好坏，需根据实际情况使用；  

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/_9zDX0ljV4mr2NwiUd83kg)