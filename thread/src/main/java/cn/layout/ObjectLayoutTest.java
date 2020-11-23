package cn.layout;

import org.openjdk.jol.info.ClassLayout;

/**
 * Object o = new Object()
 * 1、内存分布结构；
 * 2、上述代码在内存中占了多少个字节；
 * <p>
 * 具体参考印象笔记《Notes About Work》/《并发编程》/《对象布局分析》
 *
 * @author wjm
 * @since 2020/11/23 10:02
 */
public class ObjectLayoutTest {

    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());
    }

}
