# 备忘录模式Memo
# 一 概述
>备忘录是一个抽象的概念，他应代表着记录了任何关于对象的行为、状态、时间线回滚、时间线前进等等的一切操作，如：回滚，倒退，载入历史，数据库恢复、游戏存盘载入、操作系统快照恢复、打开备份文档、手机恢复出厂设置等；   


# 二 使用示例
>使用备忘录模式，示例一个文档编辑器的时间线行为：保存撤销操作；
```
package cn.http.test;

/**
 * 实体类：一个文档
 *
 * @author:wjm
 * @date:2020/6/28 21:07
 */
public class Doc {
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String body;

    /**
     * 新建文档，需要给文档命名
     *
     * @param title
     */
    public Doc(String title) {
        this.title = title;
        this.body = "";
    }

    /**
     * 创建文档的历史记录数据
     *
     * @param
     */
    public DocHistory createHistory() {
        return new DocHistory(body);
    }

    /**
     * 恢复历史文档
     *
     * @param docHistory 历史文档
     */
    public void restoreHistory(DocHistory docHistory) {
        this.body = docHistory.getBody();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
```
```
package cn.http.test;

/**
 * 实体类：记录Doc的历史数据
 *
 * @author:wjm
 * @date:2020/6/28 21:09
 */
public class DocHistory {
    /**
     * Doc的历史数据
     */
    private String historyBody;

    /**
     * 创建历史数据
     *
     * @param body
     */
    public DocHistory(String body) {
        this.historyBody = body;
    }

    public String getBody() {
        return historyBody;
    }
}
```
```
package cn.http.test;

import java.util.ArrayList;
import java.util.List;

/**
 * 编辑器
 *
 * @author:wjm
 * @date:2020/6/28 21:13
 */
public class Editor {
    private Doc doc;
    /**
     * 历史文档记录列表
     */
    private List<DocHistory> historyRecords;
    /**
     * 当前记录到历史记录的位置
     */
    private int historyPosition = -1;

    /**
     * 创建编辑器，可以编辑文档
     *
     * @param doc
     */
    public Editor(Doc doc) {
        System.out.println("<<<打开文档【" + doc.getTitle() + "】");
        this.doc = doc;
        historyRecords = new ArrayList<>();
        //保存/备份
        save();
    }

    /**
     * 编辑文档内容
     *
     * @param txt
     */
    public void append(String txt) {
        System.out.println("<<<插入...");
        doc.setBody(doc.getBody() + txt + "\n");
        save();
    }

    /**
     * 删除文档内容
     */
    public void delete() {
        System.out.println("<<<删除...");
        doc.setBody("");
        save();
    }

    /**
     * 展示当前文档内容
     */
    public void show() {
        System.out.println(doc.getBody());
    }

    /**
     * 撤销操作（控制对象的时间线行为）
     */
    public void undo() {
        System.out.println(">>>撤销操作");
        //最初一步，不能再撤销
        if (historyPosition == 0) {
            System.out.println("已到起点...");
            return;
        }
        //历史记录位置回滚
        historyPosition--;
        //获取上一次操作的历史文档，进行恢复
        DocHistory docHistory = historyRecords.get(historyPosition);
        doc.restoreHistory(docHistory);
    }

    /**
     * 保存/备份当前文档
     */
    private void save() {
        historyRecords.add(doc.createHistory());
        historyPosition++;
    }
}
```
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/28 21:28
 */
public class Test {
    public static void main(String[] args) {
        Editor editor = new Editor(new Doc("《透过代码看世界》"));
        editor.append("第一章 世界与代码");
        editor.append("正文2000字……");
        editor.append("第二章 代码与世界");
        editor.append("正文3000字……");
        editor.show();
        editor.delete();
        editor.show();
        editor.undo();
        editor.show();
    }
}
```

# 三 总结
>备忘录可以控制对象实现时间线行为，但任何模式都有其优缺点，例如如果历史状态内容过大，会导致内存消耗严重，本文代码示例的list也是属于在内存中的，因此一定要依场景灵活运用，切不可生搬硬套；

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/LKAlxvnBqidddwmxRCPQWg)