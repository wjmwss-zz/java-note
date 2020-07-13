package cn.pattern.factory.simple_factory.producer;

import cn.pattern.factory.simple_factory.product.Food;

/**
 * 生产者：工厂
 *
 * @author:wjm
 * @date:2020/6/30 22:57
 */
public interface Factory {
    /**
     * 可以生产所有实现了Food的子类
     *
     * @return
     */
    Food create();
}