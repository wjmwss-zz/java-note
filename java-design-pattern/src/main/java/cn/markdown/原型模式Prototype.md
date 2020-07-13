# 原型模式Prototype
# 一 概述
>新建一个标准化的word文档，这个过程其实是在实例化，称之为“零号”文件；  
>当写好了文档后，把这个文件复制给其他公司员工去填写，则这个零号文件被称之为“原型”；  
>原型模式，实际上是从原型实例复制克隆出新实例，而不是重新实例化类；

# 二 使用示例
>原型类构造、深拷贝与浅拷贝
```
package cn.http.test;

/**
 * 原型类：敌机
 *
 * @author:wjm
 * @date:2020/6/16 14:29
 */
public class EnemyPlane implements Cloneable {
    private Bullet bullet = new Bullet();

    private int x;
    private int y = 0;

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    @Override
    protected EnemyPlane clone() throws CloneNotSupportedException {
        /**
         * Java中的变量分为原始类型和引用类型；
         * 浅拷贝：只拷贝原始类型的值、引用类型的地址：
         * 比如坐标x, y的值、对象bullet的地址会被拷贝到克隆对象中，对于bullet，拷贝的所有地址都指向同一个bullet
         * 下面这句代码，调用Object的clone()，是对EnemyPlane的浅拷贝
         */
        EnemyPlane clonePlane = (EnemyPlane) super.clone();
        /**
         * 由于不可能所有Bullet都用同一个，因此需要对Bullet进行深拷贝；
         * 浅拷贝与深拷贝的区别：
         * 1、浅拷贝只复制指向某个对象的指针，而不复制对象本身，新旧对象还是共享同一块内存。
         * 2、深拷贝会另外创造一个一模一样的对象，新对象跟原对象不共享内存，修改新对象不会改到原对象。
         */
        clonePlane.setBullet(this.bullet.clone());
        return clonePlane;
    }
}

class Bullet {

}
```
>获取原型类的克隆实例
```
package cn.http.test;

/**
 * 原型模式的使用
 *
 * @author:wjm
 * @date:2020/6/16 14:30
 */
public class EnemyPlaneFactory {
    /**
     * 创建一个原型类
     */
    private final static EnemyPlane protoType = new EnemyPlane();

    /**
     * 获取原型类的拷贝类
     *
     * @return
     * @throws CloneNotSupportedException
     */
    public static EnemyPlane getInstance() throws CloneNotSupportedException {
        EnemyPlane clone = protoType.clone();
        return clone;
    }
}
```
笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/ojpzNHBHSh-71w9ynqQDpw)