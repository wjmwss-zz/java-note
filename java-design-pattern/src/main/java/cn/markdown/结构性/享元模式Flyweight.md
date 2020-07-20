# 享元模式Flyweight
# 一 概述
>享元，即共享元属性；在相似的对象中，抽象出可以共享（重复）的元属性，使用享元模式提高处理性能，减少不必要的开支；

# 二 使用示例
>需求：定义一套绘图工具，使用该工具可以直接画出河流、石路、草坪、房子等；  
>设计：由于工具画出的图案具有高度的相似性，例如多个河流、多个石路，因此提取绘制图案时，只需要加载一次图案类，就可以一直使用，而不是每一次提取都重新加载一次；在以上描述中，每一种图案类都是共享元属性，代码示例：
```
package cn.http.test;

/**
 * 绘图工具具有的能力
 *
 * @author:wjm
 * @date:2020/6/28 20:17
 */
public interface Drawable {
    /**
     * 在指定坐标上进行画图
     *
     * @param x
     * @param y
     */
    void draw(int x, int y);
}
```
```
package cn.http.test;

/**
 * 河流图案提取，拥有画图能力
 *
 * @author:wjm
 * @date:2020/6/28 20:21
 */
public class River implements Drawable {

    /**
     * 绘制的图片类型
     */
    private String image;

    public River() {
        this.image = "河流";
        System.out.println("成功加载【" + image + "】图片，耗时半秒...");
    }

    @Override
    public void draw(int x, int y) {
        System.out.println("在位置【" + x + "," + y + "】上绘制图片：【" + image + "】");
    }
}
```
```
package cn.http.test;

/**
 * 草坪图案提取，拥有画图能力
 *
 * @author:wjm
 * @date:2020/6/28 20:21
 */
public class Grass implements Drawable {

    /**
     * 绘制的图片类型
     */
    private String image;

    public Grass() {
        this.image = "草坪";
        System.out.println("成功加载【" + image + "】图片，耗时半秒...");
    }

    @Override
    public void draw(int x, int y) {
        System.out.println("在位置【" + x + "," + y + "】上绘制图片：【" + image + "】");
    }
}
```
```
package cn.http.test;

/**
 * 石路图案提取，拥有画图能力
 *
 * @author:wjm
 * @date:2020/6/28 20:21
 */
public class Stone implements Drawable {

    /**
     * 绘制的图片类型
     */
    private String image;

    public Stone() {
        this.image = "石路";
        System.out.println("成功加载【" + image + "】图片，耗时半秒...");
    }

    @Override
    public void draw(int x, int y) {
        System.out.println("在位置【" + x + "," + y + "】上绘制图片：【" + image + "】");
    }
}
```
```
package cn.http.test;

/**
 * 房子图案提取，拥有画图能力
 *
 * @author:wjm
 * @date:2020/6/28 20:21
 */
public class House implements Drawable {

    /**
     * 绘制的图片类型
     */
    private String image;

    public House() {
        this.image = "房子";
        System.out.println("成功加载【" + image + "】图片，耗时半秒...");
    }

    @Override
    public void draw(int x, int y) {
        System.out.println("将图层切到最上层...");
        System.out.println("在位置【" + x + "," + y + "】上绘制图片：【" + image + "】");
    }
}
```
>注：房子图案类的绘制实现与别的不同，这就是用接口或抽象类来做引用的好处，使实现类可以有自己独特的行为方式，多态的好处立竿见影；    

>接下来就是元属性的共享：抛弃了利用new关键字肆意妄为地制造对象，而是改用这个图件工厂去帮我们把元构建并共享起来，享元的对象只需一次加载，后续可以一直使用；
```
package cn.http.test;

import java.util.HashMap;
import java.util.Map;

/**
 * 绘图工具工厂
 *
 * @author:wjm
 * @date:2020/6/28 20:29
 */
public class DrawFactory {
    /**
     * 图库，Drawable及其实现类，即为共享元属性
     */
    private Map<String, Drawable> images;

    public DrawFactory() {
        images = new HashMap<>();
    }

    public Drawable getDrawable(String image) {
        //若容器不存在绘图工具，则实例化放进容器；若已存在，直接获取绘图工具并返回
        if (!images.containsKey(image)) {
            switch (image) {
                case "河流":
                    images.put(image, new River());
                    break;
                case "草坪":
                    images.put(image, new Grass());
                    break;
                case "石路":
                    images.put(image, new Stone());
                    break;
                case "房子":
                    images.put(image, new House());
                    break;
            }
        }
        return images.get(image);
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
 * @date:2020/6/28 20:31
 */
public class Test {
    public static void main(String[] args) {
        DrawFactory drawFactory = new DrawFactory();
        drawFactory.getDrawable("河流").draw(10,20);
        drawFactory.getDrawable("河流").draw(10,20);
        drawFactory.getDrawable("石路").draw(10,20);
        drawFactory.getDrawable("石路").draw(10,20);
        drawFactory.getDrawable("石路").draw(10,20);
        drawFactory.getDrawable("草坪").draw(10,20);
        drawFactory.getDrawable("草坪").draw(10,20);
        drawFactory.getDrawable("房子").draw(10,20);
        drawFactory.getDrawable("房子").draw(10,20);
        //通过输出可以获知：第一次获取同一种图案的绘图工具时，需要实例化绘图工具，才能进行绘图；第二次时省略了实例化的过程，把绘图工具抽象成了共享元属性
    }
}
```

# 三 总结
>享元的精髓当然重点不止于”享“，更重要的是对于元的辨识；  
>享元模式中的内蕴状态，即共享元属性，外蕴状态：即变化的属性；  
>例如上述的代码示例中，如果把传入的坐标参数也当作共享对象元数据（内蕴状态）的话，那么这个结构将无元可享，大量的对象就如同世界上没有相同的两片树叶一样多不胜数，最终会导致图库池被撑爆，享元将变得毫无意义；  
>所以，对于整个系统数据结构的分析、设计、规划显得尤为重要；

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/23Xzu716kFZSr1hUbZf_Tg)