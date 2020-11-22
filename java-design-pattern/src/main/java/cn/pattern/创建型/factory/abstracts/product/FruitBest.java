package cn.pattern.创建型.factory.abstracts.product;

/**
 * 产出物：一级水果
 *
 * @author wjm
 * @since 2020/7/1 20:24
 */
public class FruitBest implements Food {
    @Override
    public void description() {
        System.out.println("这是一级水果");
    }
}