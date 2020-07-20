# 解释器模式Interpreter
# 一 概述
>解释，其功能是对“语言”的拆解，例如将A语言解释成B语言，让B语言也能理解A语言；  
>例如Java是人类可以理解的语言，当Java程序写好后要先进行编译生成字节码（class文件），然后对此文件解释成机器码，最终机器才可以理解并执行；  

# 二 使用示例
>先回顾一下[组合模式](https://my.oschina.net/u/4530399/blog/4325854)，组合模式的本质，是抽象出共同的根属性（根节点），将功能抽象成由根节点产生不同的子节点，子节点又可以继续产生子节点，直到最终的“叶子”（二叉树结构），然后通过不同节点相互组合产生结果（例如文件夹功能），他强调的是节点与节点之间是像二叉树一样的结构（强调结构型）；  
>而解释器模式，其结构类似于组合模式，他通过抽象出功能最原子的属性，然后通过该原子属性不断向外伸展更多的原子属性，而不同的原子属性组合起来也可以成为一个非原子属性（组合使用），他的结构也是像二叉树一样，但强调的并非是二叉树的结构，而是原子属性的使用、原子属性与原子属性组合成非原子属性的使用（强调行为型）；  

>在解释器模式中，对语言原子性的拆解至关重要，任意一段话、一个功能、或者一段逻辑等等，必定可以分解出最原子的构成；    

>看一段需求：现有一段游戏外挂脚本，需要将用xx语言写的脚本通过Java实现出同样的效果；
```
        /**
         * BEGIN             // 脚本开始
         * AUTO_SEARCH       // 自动寻找怪物
         * MOVE 500,600      // 鼠标移动到怪物位置(500, 600)
         * BEGIN LOOP 5      // 开始循环5次
         * SKILL             // 放技能
         * LOOP END          // 循环体结束
         * END               // 脚本结束
         */
```

>实现核心思想：运用解释器模式，该脚本的最原子属性可以抽象为指令，人物移动、放技能等这些操作则作为最原子属性的分支：移动指令、放技能指令等；同时我们需要实现单击鼠标右键放技能，这是把单击右键指令与放技能指令组合起来的一个指令（非原子属性）；

>可以作为原子属性的示例代码；
```
package cn.http.test;

/**
 * 解释器<p>
 * 功能：解释不同的指令/执行不同的指令
 *
 * @author:wjm
 * @date:2020/7/6 22:58
 */
public interface Interpreter {
    /**
     * 解释指令
     */
    void interpreter();
}
```
```
package cn.http.test;

/**
 * 原子属性：自动寻找怪物指令解释器
 *
 * @author:wjm
 * @date:2020/7/6 22:58
 */
public class SearchInterpreter implements Interpreter {

    @Override
    public void interpreter() {
        System.out.println("执行自动寻找怪物指令");
    }
}
```
```
package cn.http.test;

/**
 * 原子属性：移动指令解释器
 *
 * @author:wjm
 * @date:2020/7/6 22:58
 */
public class MoveInterpreter implements Interpreter {
    private int x;
    private int y;

    public MoveInterpreter(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void interpreter() {
        System.out.println("执行移动指令：移动人物到位置【" + x + "," + y + "】");
    }
}
```
```
package cn.http.test;

/**
 * 原子属性：放技能指令解释器
 *
 * @author:wjm
 * @date:2020/7/6 22:58
 */
public class SkillInterpreter implements Interpreter {
    /**
     * 技能名字
     */
    private String name;

    public SkillInterpreter(String name) {
        this.name = name;
    }

    @Override
    public void interpreter() {
        System.out.println("执行放技能指令：发动技能【" + name + "】");
    }
}
```
```
package cn.http.test;

/**
 * 原子属性：躲避指令
 *
 * @author:wjm
 * @date:2020/7/6 22:58
 */
public class AvoidInterpreter implements Interpreter {

    @Override
    public void interpreter() {
        System.out.println("执行躲避指令：躲避成功！");
    }
}
```
```
package cn.http.test;

/**
 * 原子属性：按下鼠标左键指令解释器
 *
 * @author:wjm
 * @date:2020/7/6 22:58
 */
public class LeftMouseDownInterpreter implements Interpreter {

    @Override
    public void interpreter() {
        System.out.println("执行【按下鼠标左键】指令");
    }
}
```
```
package cn.http.test;

/**
 * 原子属性：抬起鼠标左键指令解释器
 *
 * @author:wjm
 * @date:2020/7/6 22:58
 */
public class LeftMouseUpInterpreter implements Interpreter {

    @Override
    public void interpreter() {
        System.out.println("执行【抬起鼠标左键】指令");
    }
}
```
>可以作为非原子属性的示例代码；
```
package cn.http.test;

/**
 * 非原子属性：单击鼠标左键指令解释器（包含了按下左键指令，任务移动指令、抬起左键指令）
 *
 * @author:wjm
 * @date:2020/7/6 23:26
 */
public class LeftMouseClickInterpreter implements Interpreter {

    private Interpreter leftMouseDownInterpreter;
    private Interpreter leftMouseUpInterpreter;
    private Interpreter moveInterpreter;

    /**
     * 单击鼠标左键默认为移动人物，其执行的指令顺序是规定的、固化的，因此只需让用户传入要移动的坐标即可，其余的指令无需与外界产生关联
     *
     * @param x
     * @param y
     */
    public LeftMouseClickInterpreter(int x, int y) {
        this.leftMouseDownInterpreter = new LeftMouseDownInterpreter();
        this.leftMouseUpInterpreter = new LeftMouseUpInterpreter();
        this.moveInterpreter = new MoveInterpreter(x, y);
    }

    /**
     * 单击鼠标左键：<p>
     * 执行按下鼠标左键指令；<p>
     * 执行人物移动指令；<p>
     * 执行抬起鼠标左键指令；
     */
    @Override
    public void interpreter() {
        leftMouseDownInterpreter.interpreter();
        moveInterpreter.interpreter();
        leftMouseUpInterpreter.interpreter();
    }
}
```
```
package cn.http.test;

/**
 * 非原子属性：单击鼠标右键指令解释器（包含了单击右键指令，放技能指令）
 *
 * @author:wjm
 * @date:2020/7/6 23:26
 */
public class RightMouseClickInterpreter implements Interpreter {

    private Interpreter skillInterpreter;

    /**
     * 单击鼠标右键默认为放技能，其执行的指令顺序是规定的、固化的，因此只需让用户传入要技能名字即可，其余的指令无需与外界产生关联
     */
    public RightMouseClickInterpreter(String name) {
        this.skillInterpreter = new SkillInterpreter(name);
    }

    /**
     * 单击鼠标右键：<p>
     * 执行放技能指令；
     */
    @Override
    public void interpreter() {
        skillInterpreter.interpreter();
    }
}
```
```
package cn.http.test;

/**
 * 非原子属性：循环执行指令 指令解释器
 *
 * @author:wjm
 * @date:2020/7/6 23:26
 */
public class LoopInterpreter implements Interpreter {

    /**
     * 循环次数
     */
    private int loopCount;

    /**
     * 循环体内要执行的指令
     */
    private Interpreter interpreter;

    public LoopInterpreter(int loopCount, Interpreter interpreter) {
        this.loopCount = loopCount;
        this.interpreter = interpreter;
    }

    /**
     * 循环执行所需指令
     */
    @Override
    public void interpreter() {
        for (int i = 1; i <= loopCount; i++) {
            interpreter.interpreter();
        }
    }
}
```
```
package cn.http.test;

import java.util.List;

/**
 * 非原子属性：按顺序执行指令集 指令解释器
 *
 * @author:wjm
 * @date:2020/7/6 23:26
 */
public class SequenceInterpreter implements Interpreter {

    private List<Interpreter> interpreters;

    public SequenceInterpreter(List<Interpreter> interpreters) {
        this.interpreters = interpreters;
    }

    /**
     * 使用List的Stream API遍历，存进List的表达式按顺序执行
     */
    @Override
    public void interpreter() {
        interpreters.forEach(expression -> expression.interpreter());
    }
}
```
>具体应用；
```
package cn.http.test;

import java.util.Arrays;

/**
 * 解释器模式应用
 *
 * @author:wjm
 * @date:2020/7/7 00:02
 */
public class Test {
    public static void main(String[] args) {
        /**
         * 需求：用Java语言作为编译器，把不同功能的脚本解释成可以在Java上运行的语言；
         */

        //这是一段游戏外挂脚本
        /**
         * BEGIN             // 脚本开始
         * AUTO_SEARCH       // 自动寻找怪物
         * MOVE 500,600      // 鼠标移动到怪物位置(500, 600)
         * BEGIN LOOP 5      // 开始循环5次
         * SKILL             // 放技能
         * LOOP END          // 循环体结束
         * END               // 脚本结束
         */

        /**
         * 通过定义解析游戏语义树来解析游戏外挂脚本：<p>
         * 通过 按顺序执行指令集的指令解释器 接收 要执行的指令集，按顺序执行；<p>
         */
        Interpreter sequenceInterpreter = new SequenceInterpreter(
                Arrays.asList(
                        //执行寻找怪物指令
                        new SearchInterpreter(),
                        //执行单击鼠标左键指令（完成了按下鼠标左键、移动人物位置、抬起鼠标左键）
                        new LeftMouseClickInterpreter(200, 300),
                        //执行循环指令（循环的指令为单击右键进行放技能指令，次数为5次）
                        new LoopInterpreter(5, new RightMouseClickInterpreter("火焰箭"))
                ));
        sequenceInterpreter.interpreter();
    }
}
```

# 三 总结
>解释器模式中，最终使用解释器，其本质也是组合模式：需要定义一个按顺序执行指令集的指令解释器SequenceInterpreter，然后按执行顺序组合不同的指令成为指令集（语义树），让指令集解释器按顺序执行指令集；  

>同样，语义树也可以单独抽象出来，比如专门解析游戏外挂脚本的语义树、专门解析刷用户量脚本的语令树等等；后期若有更多类型脚本的解释需求，运用解释器模式可以达到只需优雅植入语义树即可，（符合设计模式的开闭原则）；  

>解释器模式讲究高度抽象：对语法解析、指令/表达式抽象化，关系结构化，使得让解释工作变得即插即用；解释器模式不但提高了代码的易读性、易用性、可维护性，更重要的是对未来语言变化的可扩展性；  

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/f9N62Uj8Uf-dsybKcGGg6Q)