package cn.pattern.factory.simple_factory.product;

/**
 * 产出物：苹果
 *
 * @author:wjm
 * @date:2020/6/30 22:54
 */
public class Apple implements Food {
    @Override
    public void description() {
        System.out.println("这是苹果");
    }
}