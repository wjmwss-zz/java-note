# 状态模式State
# 一 概述
>描述状态模式前，应先回顾一下策略模式：  
>* 策略模式：提供描述A、B、C策略的接口，分别实现ABC三种策略，使用时利用多态传入不同策略的实现类即可使用策略；  
>* 状态模式：本质是和策略模式一样的，个人理解为是基于策略模式上再包装了一层功能，成为了状态模式；状态模式，即控制状态，无论任何时刻，都必须符合：***当前状态只能切换到下一个合理的状态，当前状态不允许切换到当前状态*** 的规律；

# 二 使用示例
>介绍一个实现开关状态的代码示例：
```
package cn.http.test;

/**
 * 状态接口<p>
 * 开、关
 *
 * @author:wjm
 * @date:2020/6/16 22:03
 */
public interface State {
    /**
     * 开
     *
     * @param switcher
     */
    public void on(Switcher switcher);

    /**
     * 关
     *
     * @param switcher
     */
    public void off(Switcher switcher);
}
```
```
package cn.http.test;

/**
 * 方法抽象成接口<p>
 * 实现：On !>> On; On >> Off 的状态转化
 *
 * @author:wjm
 * @date:2020/6/16 22:03
 */
public class On implements State {
    @Override
    public void on(Switcher switcher) {
        System.out.println("已打开，不允许再次打开");
        return;
    }

    @Override
    public void off(Switcher switcher) {
        switcher.setState(new Off());
        System.out.println("关闭成功");
    }
}
```
```
package cn.http.test;

/**
 * 方法抽象成接口<p>
 * 实现：Off !>> Off; Off >> On 的状态转化
 *
 * @author:wjm
 * @date:2020/6/16 22:03
 */
public class Off implements State {
    @Override
    public void on(Switcher switcher) {
        switcher.setState(new On());
        System.out.println("打开成功");
    }

    @Override
    public void off(Switcher switcher) {
        System.out.println("已关闭，不允许再次关闭");
        return;
    }
}
```
```
package cn.http.test;

/**
 * 开关
 *
 * @author:wjm
 * @date:2020/6/16 22:03
 */
public class Switcher {
    /**
     * 设置初始状态为 关
     */
    private State state = new Off();

    public State getState() {
        return state;
    }

    /**
     * 设置当前状态
     *
     * @param state
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * 开关设置为开
     */
    public void switchOn() {
        state.on(this);
    }

    /**
     * 开关设置为关
     */
    public void switchOff() {
        state.off(this);
    }
}
```
```
package cn.http.test;

/**
 * 实现台灯的开关操控<p>
 *
 * @author:wjm
 * @date:2020/6/16 22:03
 */
public class Lamp {
    /**
     * 开关
     */
    private static Switcher switcher = new Switcher();

    /**
     * 打开台灯
     */
    public void on() {
        switcher.switchOn();
    }

    /**
     * 关闭台灯
     */
    public void off() {
        switcher.switchOff();
    }
}

class Test{
    public static void main(String[] args) {
        Lamp lamp = new Lamp();
        lamp.on();
        lamp.off();
        lamp.off();
        lamp.off();
        lamp.off();
        lamp.on();
        lamp.on();
    }
}
```
# 三 总结
>状态模式的本质为策略模式，其基于策略模式上，保证了：***当前状态只能切换到下一个合理的状态，当前状态不允许切换到当前状态***；

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/oTYGAZ1b3uVo4SWdIDn98g)