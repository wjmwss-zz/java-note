package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：二级水果
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class FruitMedium implements Food {
    @Override
    public void description() {
        System.out.println("这是二级水果");
    }
}