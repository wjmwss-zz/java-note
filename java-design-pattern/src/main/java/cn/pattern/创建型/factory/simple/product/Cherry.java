package cn.pattern.创建型.factory.simple.product;

/**
 * 产出物：樱桃
 *
 * @Author: wjm
 * @date: 2020/6/30 22:54
 */
public class Cherry implements Food {
    @Override
    public void description() {
        System.out.println("这是樱桃");
    }
}