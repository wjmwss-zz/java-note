# 适配器模式Adapt
# 一 概述
>例子：墙上的插座为三孔接口，但是很多家电都是双孔标准，这个时候就需要一个适配器，让双孔的家电通过适配器后也可以接入三孔接口的插座；

>两种适配器  
>* 类适配器  
>* 对象适配器

# 二 使用示例
>对象适配器
```
package cn.http.test;

/**
 * 三孔插座
 *
 * @author:wjm
 * @date:2020/6/16 17:10
 */
public interface TriplePin {
    /**
     * 参数分别为火线live，零线null，地线earth
     *
     * @param l
     * @param n
     * @param e
     */
    void electrify(int l, int n, int e);
}
```
```
package cn.http.test;

/**
 * 双孔插座
 *
 * @author:wjm
 * @date:2020/6/16 17:10
 */
public interface DualPin {
    /**
     * 参数分别为火线live，零线null
     *
     * @param l
     * @param n
     */
    void electrify(int l, int n);
}
```
```
package cn.http.test;

/**
 * 使用双孔插座的电视
 *
 * @author:wjm
 * @date:2020/6/16 17:10
 */
public class TV implements DualPin {
    /**
     * 电视实现的是双孔插座标准
     *
     * @param l
     * @param n
     */
    @Override
    public void electrify(int l, int n) {
        System.out.println("火线通电：" + l);
        System.out.println("零线通电：" + n);
    }
}
```
```
package cn.http.test;

/**
 * 对象适配器
 *
 * @author:wjm
 * @date:2020/6/16 17:10
 */
public class TVObjectAdapter implements TriplePin {

    private DualPin dualPinDevice;

    /**
     * 创建对象适配器时，需要注入实现了双孔插座的类（不仅仅是电视，其他实现了DualPin的双孔家电都可以注入）
     */
    public TVObjectAdapter(DualPin dualPinDevice) {
        this.dualPinDevice = dualPinDevice;
    }

    /**
     * 实际上调用了被适配设备的双插通电，地线e被丢弃了
     *
     * @param l
     * @param n
     * @param e
     */
    @Override
    public void electrify(int l, int n, int e) {
        dualPinDevice.electrify(l, n);
    }
}
```
>类适配器
```
package cn.http.test;

/**
 * 类适配器
 *
 * @author:wjm
 * @date:2020/6/16 17:10
 */
public class TVClassAdapter extends TV implements TriplePin {
    /**
     * 类适配器无需注入其他对象（不需对象组合），通过继承TV来直接调用
     *
     * @param l
     * @param n
     * @param e
     */
    @Override
    public void electrify(int l, int n, int e) {
        super.electrify(l, n);
    }
}
```
>具体应用
```
package cn.http.test;

/**
 * 具体应用
 *
 * @author:wjm
 * @date:2020/6/16 17:12
 */
public class Test {
    public static void main(String[] args) {
        //参数分别为火线live，零线null，地线earth，其中地线只是初始化，实际上并没有用到，已经通过适配器转换
        int l = 1;
        int n = 2;
        int e = 0;

        //对象适配器，需要注入具体实现类才能使用
        TVObjectAdapter TVObjectAdapter = new TVObjectAdapter(new TV());
        TVObjectAdapter.electrify(l, n, e);

        //类适配器，是专门为某一个类做的适配器，使用时无需注入具体实现类，使用起来更方便，但是别的双孔家电就没法使用了，多态特性在类适配器失效
        TVClassAdapter TVClassAdapter = new TVClassAdapter();
        TVClassAdapter.electrify(l, n, e);
    }
}
```
# 三 总结
>主要关注两种适配器：  
>* 对象适配器：对象适配器在使用时，需要注入实现了双孔插座的类，因此对象适配器是可以使用多态的，只要是双孔插座的类，都可以在通过注入后使用；
>* 类适配器：类适配器是专门为某一个类进行适配的适配器，使用时直接调用即可，但也就摒弃了多态；
>* 两种方式各有优劣，根据不同场景按需使用；

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/NBF5IvkboC8wt_DhYaHhvA)