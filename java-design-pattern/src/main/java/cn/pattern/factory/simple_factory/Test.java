package cn.pattern.factory.simple_factory;

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