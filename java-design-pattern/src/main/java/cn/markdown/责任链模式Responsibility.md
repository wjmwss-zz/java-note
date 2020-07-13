# 责任链模式Responsibility
# 一 概述
>其核心是为了处理某种连续的流程，并确保业务一定能走到相应的责任节点上并得到相应的处理；  
>例如工作流：企事业单位中为了完成某项日常任务，通常要制定一些工作流程，按步骤拆分，并组织好各个环节中的逻辑关系及走向，这样才能更高效、更规范地完成任务；

# 二 使用示例
>审批工作流：  
>审批人的抽象：不同审批人的审批权限不同，因此审批的逻辑由其实现类实现，抽象类只需抽象不同角色的责任范围：自己只懂自己怎么审批（责任），自己处理不了的递交给上层（链条）；
```
package cn.http.test;

/**
 * 抽象类：审批人员/审批权限
 *
 * @author:wjm
 * @date:2020/6/28 22:00
 */
public abstract class Approver {
    /**
     * 审批人名字
     */
    protected String name;
    /**
     * 下一个审批人/当前审批人的上级（不同角色的自己相互持有引用）
     */
    protected Approver nextApprover;

    /**
     * 创建审批人
     *
     * @param name
     */
    public Approver(String name) {
        this.name = name;
    }

    /**
     * 设置下一个审批人/设置当前审批人的领导
     *
     * @param nextApprover
     * @return
     */
    protected Approver setNextApprover(Approver nextApprover) {
        this.nextApprover = nextApprover;
        // 返回下个审批人，可以进行链式编程
        return this.nextApprover;
    }

    /**
     * 抽象审批方法：审批金额<p>
     * 金额在1000元以下的，由基层人员审批；<p>
     * 金额在1000元以上，5000元以下的，由管理人员审批；<p>
     * 金额在5000元以上的，由CEO审批；
     *
     * @param amount
     */
    public abstract void approve(int amount);
}
```
```
package cn.http.test;

/**
 * 具有审批权限的基层人员
 *
 * @author:wjm
 * @date:2020/6/28 22:10
 */
public class BasicWorker extends Approver {
    public BasicWorker(String name) {
        super(name);
    }

    @Override
    public void approve(int amount) {
        if (amount <= 1000) {
            System.out.println("【员工：" + name + "】：审批通过...");
        } else {
            System.out.println("【员工：" + name + "】：无权审批，升级处理...");
            this.nextApprover.approve(amount);
        }
    }
}
```
```
package cn.http.test;

/**
 * 具有审批权限的管理人员
 *
 * @author:wjm
 * @date:2020/6/28 22:10
 */
public class Manager extends Approver {
    public Manager(String name) {
        super(name);
    }

    @Override
    public void approve(int amount) {
        if (amount <= 5000) {
            System.out.println("【经理：" + name + "】：审批通过...");
        } else {
            System.out.println("【经理：" + name + "】：无权审批，升级处理...");
            this.nextApprover.approve(amount);
        }
    }
}
```
```
package cn.http.test;

/**
 * 具有审批权限的Ceo
 *
 * @author:wjm
 * @date:2020/6/28 22:10
 */
public class Ceo extends Approver {
    public Ceo(String name) {
        super(name);
    }

    @Override
    public void approve(int amount) {
        if (amount <= 10000) {
            System.out.println("【CEO：" + name + "】：审批通过...");
        } else {
            System.out.println("【CEO：" + name + "】：驳回申请...");
        }
    }
}
```
>至此，申请人与审批人实现了解耦，我们只需递单送给责任链即可，申请人不必再关心每个处理细节；
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/28 22:14
 */
public class Test {
    public static void main(String[] args) {
        //创建一个审批人
        Approver basicWorkerApprover = new BasicWorker("基层人员A");

        /**
         * 审批人具有的责任链:<p>
         * basicWorkerApprover.setNextApprover(new Manager("管理人员A")) : 表示基层人员的上级为管理人员（每次调用setNextApprover都会返回上级，链式调用）<p>
         * .setNextApprover(new Ceo("CeoA"))：表示管理人员的上级为Ceo
         */
        basicWorkerApprover.setNextApprover(new Manager("管理人员A")).setNextApprover(new Ceo("CeoA"));

        basicWorkerApprover.approve(1000);
        basicWorkerApprover.approve(6000);
        basicWorkerApprover.approve(10000);
        basicWorkerApprover.approve(12000);
    }
}
```
# 三 总结
>在责任链模式中，各个角色的责任划分非常明确并且被分开定义到了每个角色类中，再把他们串起来去调用；后期如果再继续添加新的角色只需要添加新角色类并加入链条即可；责任链条具有随意伸缩，灵活的可伸缩性，完美的可扩展性；

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/QitwJ4-30cn9RuaJQhIo4Q)