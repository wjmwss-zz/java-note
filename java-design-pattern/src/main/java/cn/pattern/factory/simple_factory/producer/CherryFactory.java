package cn.pattern.factory.simple_factory.producer;

import cn.pattern.factory.simple_factory.product.Cherry;
import cn.pattern.factory.simple_factory.product.Food;

/**
 * 生产者：樱桃
 *
 * @author:wjm
 * @date:2020/6/30 22:58
 */
public class CherryFactory implements Factory {
    @Override
    public Food create() {
        return new Cherry();
    }
}