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
 * @since 2020/11/23 15:36
 */
public class SynchronizedLayoutTest {

    public static void main(String[] args) {
        Object object = new Object();
        System.out.println(ClassLayout.parseInstance(object).toPrintable());

        // 执行 synchronized 对应 {} 中的代码时，锁定 object 这个对象，不是锁定代码！（没有锁定代码这么一说）
        synchronized (object) {
            System.out.println(ClassLayout.parseInstance(object).toPrintable());
        }
    }

}
