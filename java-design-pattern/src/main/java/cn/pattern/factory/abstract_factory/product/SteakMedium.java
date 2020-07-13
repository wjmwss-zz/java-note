package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：二级牛排
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class SteakMedium implements Food{
    @Override
    public void description() {
        System.out.println("这是二级牛排");
    }
}