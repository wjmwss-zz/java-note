package cn.pattern.创建型.factory.abstracts.producer;

import cn.pattern.创建型.factory.abstracts.product.Food;

/**
 * 生产者：工厂
 *
 * @author wjm
 * @since 2020/7/1 20:18
 */
public interface Factory {
    /**
     * 生产一级食品
     *
     * @return
     */
    Food createBestFood();

    /**
     * 生产二级食品
     *
     * @return
     */
    Food createMediumFood();

    /**
     * 生产三级食品
     *
     * @return
     */
    Food createWorstFood();
}