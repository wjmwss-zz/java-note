package cn.pattern.创建型.factory.abstracts.producer;

import cn.pattern.创建型.factory.abstracts.product.Food;
import cn.pattern.创建型.factory.abstracts.product.SteakBest;
import cn.pattern.创建型.factory.abstracts.product.SteakMedium;
import cn.pattern.创建型.factory.abstracts.product.SteakWorst;

/**
 * 生产者：不同级别的牛排
 *
 * @Author: wjm
 * @date: 2020/7/1 20:18
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