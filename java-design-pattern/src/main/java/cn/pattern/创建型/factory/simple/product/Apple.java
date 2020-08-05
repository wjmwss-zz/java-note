package cn.pattern.创建型.factory.simple.product;

/**
 * 产出物：苹果
 *
 * @Author: wjm
 * @date: 2020/6/30 22:54
 */
public class Apple implements Food {
    @Override
    public void description() {
        System.out.println("这是苹果");
    }
}