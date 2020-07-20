# 迭代器模式Iterator
# 一 概述
>迭代器就存在于常用的集合、数组中：把某一批类似的元素按某种数据结构集合起来作为一个整体来引用，比如List，Set，Map以及各种各样不同数据的表示实现；  
>当要用到每个元素的时候，需要将元素一个个的取出来，但是对不同数据类型的访问方式各有不同，于是就需要定义统一的迭代器来标准化这种遍历行为；  

# 二 使用示例
>迭代器示例：行车记录仪；
```
package cn.http.test;

/**
 * 迭代器
 *
 * @author:wjm
 * @date:2020/6/28 20:12
 */
public interface Iterator<E> {
    /**
     * 取出下一个元素
     *
     * @return
     */
    E next();

    /**
     * 是否存在下一个元素
     *
     * @return
     */
    boolean hasNext();
}
```
```
package cn.http.test;

/**
 * 行车记录仪
 *
 * @author:wjm
 * @date:2020/6/28 20:29
 */
public class DriveRecorder {
    /**
     * 所有视频
     */
    private String[] records = new String[10];

    /**
     * 行车记录仪支持记录的最大视频量
     */
    private static final int RECORD_MAX_COUNT = 10;

    /**
     * 当前视频的记录位置
     */
    private int recordIndex = -1;

    /**
     * 记录视频
     *
     * @param record
     */
    public void record(String record) {
        // 存储容量达到最大时，覆盖最旧的视频
        if (recordIndex == RECORD_MAX_COUNT - 1) {
            recordIndex = 0;
        } else {
            recordIndex++;
        }
        records[recordIndex] = record;
    }

    /**
     * 对外提供的迭代器，可以查看行车记录仪记录的视频
     *
     * @return
     */
    public Iterator<String> iterator() {
        return new DriveIteratorImpl();
    }

    private class DriveIteratorImpl implements Iterator<String> {
        // 迭代器游标，不染指原始游标。
        int recordCursor = recordIndex;
        // 迭代池中被取出的元素总数
        int loopCount = 0;

        @Override
        public boolean hasNext() {
            return loopCount < RECORD_MAX_COUNT;
        }

        @Override
        public String next() {
            // 记录即将返回的游标位置
            int i = recordCursor;
            if (recordCursor == 0) {
                recordCursor = RECORD_MAX_COUNT - 1;
            } else {
                recordCursor--;
            }
            loopCount++;
            return records[i];
        }
    }
}
```
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/28 20:30
 */
public class Test {
    public static void main(String[] args) {
        DriveRecorder driveRecorder = new DriveRecorder();

        // 记录12条视频，行车记录仪最大记录量为10条，测试迭代器的应用、新视频是否会覆盖旧视频
        for (int i = 0; i < 12; i++) {
            driveRecorder.record("视频_" + i);
        }

        /**
         * 使用行车记录仪的迭代器迭代其视频
         */
        Iterator<String> iterator = driveRecorder.iterator();
        while (iterator.hasNext()) {
            String video = iterator.next();
            System.out.println(video);
        }
    }
}
```  

# 三 总结
>对于任何的集合类，既要保证内部数据表示不暴露给外部以防搞乱内部机制，还要提供给用户遍历并访问到每个数据的权限，这就是迭代器模式，提供了所有集合对外开放的统一标准接口；

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/gxIbhqjMZKx3PwOHUMeTYw)