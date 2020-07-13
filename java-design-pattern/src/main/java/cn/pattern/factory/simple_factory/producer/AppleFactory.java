package cn.pattern.factory.simple_factory.producer;

import cn.pattern.factory.simple_factory.product.Apple;
import cn.pattern.factory.simple_factory.product.Food;

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