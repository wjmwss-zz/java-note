package cn.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cas 原理
 * 具体参考印象笔记《Notes About Work》/《并发编程》/《CAS》
 *
 * @author wjm
 * @since 2020/11/22 18:36
 */
public class CasTest {

    public static void main(String[] args) {
        /**
         * 在并发中，如果需要对一个值如 Integer、int 进行递增，需要进行 synchronized 加锁，在锁内进行递增，否则无法保持数据的一致性；
         * 而 Atomic 是一个原子操作类，他可以在不使用 synchronized 情况下并发时保证数据的一致性；
         * 原子类相比于普通的锁，粒度更细、效率更高(除了高度竞争的情况下)；
         *
         * 常见的原子类：
         * 基本类型原子类
         * Atomic*:
         * AtomicInteger
         * AtomicLong
         * AtomicBoolean
         *
         * 数组类型原子类
         * Atomic*Array:
         * AtomicIntegerArray
         * AtomicLongArray
         * AtomicReferenceArray
         *
         * 应用类型原子类
         * Atomic*Reference:
         * AtomicReference
         * AtomicStampedReference
         * AtomicMarkAbleReference
         *
         * 升级类型原子类
         * Atomic*FieldUpdater:
         * AtomicIntegerFieldUpdater
         * AtomicLongFieldUpdater
         * AtomicReferenceFieldUpdater
         *
         * 累加器
         * *Adder:
         * LongAdder
         * DoubleAdder
         *
         * 累加器
         * *Accumulator:
         * LongAccumulator
         * DoubleAccumulator
         */
        AtomicInteger i = new AtomicInteger();

        /**
         * increment 递增，表示递增后再返回，初始化时为1，则下面输出为2；
         * 点进去跟踪源码，发现 #compareAndSwapInt(xxx) 这个方法，就是 CAS 的操作（缩写）;
         *
         * 什么是 cas:
         * cas 是 compare and swap 的缩写，即：比较交换；还有一种说法叫 compare and exchange
         * cas 可以在没有锁的状态，保证多个线程对一个值的更新
         * cas是一种基于锁的操作，而且是乐观锁；
         * 在 java 中锁分为乐观锁和悲观锁；
         * 悲观锁是将资源锁住，等一个之前获得锁的线程释放锁之后，下一个线程才可以访问；
         * 乐观锁采取了一种宽泛的态度，通过某种方式不加锁来处理资源，比如通过给记录加version来获取数据，性能较悲观锁有很大的提高；
         *
         * 正常情况下，多个线程对一个值的更新，使用 synchronized 的方式，也就是悲观锁，来保证数据一致性；cas 是怎么做的呢：
         * cas 操作包含三个操作数 —— 内存位置（V）、预期原值（A）和新值(B)；
         * 如果内存地址里面的值和 A 的值是一样的，那么就将内存里面的值更新成 B；
         * cas 是通过无限循环来获取数据的（看源码可知道为 do{}while() 的循环），
         * 如果在第一轮循环中，a 线程获取地址里面的值被 b 线程修改了，那么 a 线程需要自旋，直到获取到锁才能执行；
         * 这里有画图，具体参考印象笔记《Notes About Work》/《并发编程》/《CAS》
         *
         * 这里就要涉及到自旋锁的概念了：
         * 多线程中，对共享资源进行访问，为了防止并发引起的相关问题，通常都是引入锁的机制来处理并发问题。
         * 获取到资源的线程A对这个资源加锁，其他线程比如B要访问这个资源首先要获得锁，而此时A持有这个资源的锁，只有等待线程A逻辑执行完，释放锁，这个时候B才能获取到资源的锁进而获取到该资源。
         * 这个过程中，A一直持有着资源的锁，那么没有获取到锁的其他线程比如B怎么办？通常就会有两种方式：
         * 1. 一种是没有获得锁的进程就直接进入阻塞（BLOCKING），这种就是互斥锁
         * 2. 另外一种就是没有获得锁的进程，不进入阻塞，而是一直循环着，看是否能够等到A释放了资源的锁。
         * 上述的两种方式，在学术上的定义：
         * 自旋锁（spin lock）是一种非阻塞锁，也就是说，如果某线程需要获取锁，但该锁已经被其他线程占用时，该线程不会被挂起，而是在不断的消耗CPU的时间，不停的试图获取锁。
         * 互斥锁（mutex lock）是一种阻塞锁，当某线程无法获取锁时，该线程会被直接挂起，该线程不再消耗CPU时间，当其他线程释放锁后，操作系统会激活那个被挂起的线程，让其投入运行。
         * 在《linux内核设计与实现》经常提到两种态，一种是内核态，一种是用户态，对于自旋锁来说，自旋锁使线程处于用户态，而互斥锁需要重新分配，进入到内核态。用户态比较轻，内核态比较重。
         * 用户态和内核态这个也是 linux 中必备的知识基础，借鉴这个，可以进行很多程序设计语言API上的优化，就比如说 java io 的部分，操作io的时候，先是要从用户态，进入内核态，再用内核态去操作输入输出设备的抽象，通过减少用户态到内核态的转换，达到新io的优化；
         * 具体参考印象笔记《Notes About Work》/《并发编程》/《java自旋锁》
         *
         * 回到 #compareAndSwapInt(xxx) 方法：
         * public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
         * 其中 native 的意思是表示 c 或 c++ 写的代码
         *
         * cas 的概念，在 jdk 中大量的被运用
         *
         */
        System.out.println(i.incrementAndGet());
    }

}
