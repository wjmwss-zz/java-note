package cn.pattern.factory.abstract_factory.product;

/**
 * 产出物：一级牛排
 *
 * @author:wjm
 * @date:2020/7/1 20:24
 */
public class SteakBest implements Food{
    @Override
    public void description() {
        System.out.println("这是一级牛排");
    }
}