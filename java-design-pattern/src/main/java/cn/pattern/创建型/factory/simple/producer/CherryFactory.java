package cn.pattern.创建型.factory.simple.producer;

import cn.pattern.创建型.factory.simple.product.Cherry;
import cn.pattern.创建型.factory.simple.product.Food;

/**
 * 生产者：樱桃
 *
 * @author wjm
 * @since 2020/6/30 22:58
 */
public class CherryFactory implements Factory {
    @Override
    public Food create() {
        return new Cherry();
    }
}