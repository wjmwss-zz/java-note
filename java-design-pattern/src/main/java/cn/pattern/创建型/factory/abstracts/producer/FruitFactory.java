package cn.pattern.创建型.factory.abstracts.producer;

import cn.pattern.创建型.factory.abstracts.product.Food;
import cn.pattern.创建型.factory.abstracts.product.FruitBest;
import cn.pattern.创建型.factory.abstracts.product.FruitMedium;
import cn.pattern.创建型.factory.abstracts.product.FruitWorst;

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