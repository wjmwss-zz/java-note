package cn.pattern.创建型.factory.simple.producer;

import cn.pattern.创建型.factory.simple.product.Apple;
import cn.pattern.创建型.factory.simple.product.Food;

/**
 * 生产者：苹果
 *
 * @author:wjm
 * @date:2020/6/30 22:58
 */
public class AppleFactory implements Factory {
    @Override
    public Food create() {
        return new Apple();
    }
}