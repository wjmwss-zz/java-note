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
         * 先在创建工厂的时候，确定食品的类型，再通过工厂创建xx级别的食品，当然，也可以反过来；<p>
         * 抽象工厂利用了两两组合的特性，从而可以产生更多类型的对象，
         * 对比简单工厂，一个类就要对应一个工厂，不同的对象；
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