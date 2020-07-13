# 命令模式Command
# 一 概述
>在介绍命令模式前，先回顾一下策略模式：  
>在策略模式中，定义好策略后，策略需要绑定其策略的使用者，用户需通过策略使用者来调用策略的功能；在这种模式中，策略与策略的使用者是强绑定的，当策略使用者的需求变得复杂后，需要不断的修改原有策略来满足复杂的需求，这不符合设计模式开闭原则；

>命令模式：在发令者与执行者（策略使用者）中加入命令模块（不同的策略接口与不同的策略使用者接口绑定），而达到将策略与策略使用者***彻底解耦***的目的；

# 二 使用示例
>定义命令的执行者（设备、电器）；
```
package cn.http.test;

/**
 * 定义标准的电器，具有通电与断电的功能
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public interface Electric {
    /**
     * 通电
     */
    void powerOn();

    /**
     * 断电
     */
    void powerOff();
}
```
```
package cn.http.test;

/**
 * 定义设备接口
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public interface Device extends Electric{
    /**
     * 频道+
     */
    void channelUp();

    /**
     * 频道-
     */
    void channelDown();

    /**
     * 音量+
     */
    void volumeUp();

    /**
     * 音量-
     */
    void volumeDown();
}
```
```
package cn.http.test;

/**
 * 设备：电视机
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public class Tv implements Device {

    @Override
    public void channelUp() {
        System.out.println("电视机频道+");
    }

    @Override
    public void channelDown() {
        System.out.println("电视机频道-");
    }

    @Override
    public void volumeUp() {
        System.out.println("电视机音量+");
    }

    @Override
    public void volumeDown() {
        System.out.println("电视机音量-");
    }

    @Override
    public void powerOn() {
        System.out.println("电视机通电");
    }

    @Override
    public void powerOff() {
        System.out.println("电视机断电");
    }

}
```
```
package cn.http.test;

/**
 * 设备：收音机
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public class Radio implements Device {

    @Override
    public void channelUp() {
        System.out.println("收音机频道+");
    }

    @Override
    public void channelDown() {
        System.out.println("收音机频道-");
    }

    @Override
    public void volumeUp() {
        System.out.println("收音机音量+");
    }

    @Override
    public void volumeDown() {
        System.out.println("收音机音量-");
    }

    @Override
    public void powerOn() {
        System.out.println("收音机通电");
    }

    @Override
    public void powerOff() {
        System.out.println("收音机断电");
    }

}
```

>定义命令模块；
```
package cn.http.test;

/**
 * 定义命令模块，把设备的所有功能抽象为执行命令、反执行命令
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public interface Command {
    /**
     * 执行命令
     */
    void exec();

    /**
     * 反执行命令
     */
    void unExec();
}
```
```
package cn.http.test;

/**
 * 实现通电、断电的命令
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public class ElectricCommand implements Command {
    /**
     * 命令模块绑定要使用的设备，从而将执行者（具体设备）与发令者（使用设备功能的一方）彻底解耦
     */
    private Device device;

    public ElectricCommand(Device device) {
        this.device = device;
    }

    @Override
    public void exec() {
        device.powerOn();
    }

    @Override
    public void unExec() {
        device.powerOff();
    }
}
```
```
package cn.http.test;

/**
 * 实现调频道的命令
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public class ChannelCommand implements Command {
    /**
     * 命令模块绑定要使用的设备，从而将执行者（具体设备）与发令者（使用设备功能的一方）彻底解耦
     */
    private Device device;

    public ChannelCommand(Device device) {
        this.device = device;
    }

    @Override
    public void exec() {
        device.channelUp();
    }

    @Override
    public void unExec() {
        device.channelDown();
    }
}
```
```
package cn.http.test;

/**
 * 实现调音量的命令
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public class VolumeCommand implements Command {
    /**
     * 命令模块绑定要使用的设备，从而将执行者（具体设备）与发令者（使用设备功能的一方）彻底解耦
     */
    private Device device;

    public VolumeCommand(Device device) {
        this.device = device;
    }

    @Override
    public void exec() {
        device.volumeUp();
    }

    @Override
    public void unExec() {
        device.volumeDown();
    }
}
```

>定义发令者；
```
package cn.http.test;

/**
 * 命令的使用者
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public class CommandController {
    Command electricCommand;
    Command channelCommand;
    Command volumeCommand;

    /*绑定命令*/

    public void bindElectricCommand(Command electricCommand) {
        this.electricCommand = electricCommand;
    }

    public void bindChannelCommand(Command channelCommand) {
        this.channelCommand = channelCommand;
    }

    public void bindVolumeCommand(Command volumeCommand) {
        this.volumeCommand = volumeCommand;
    }

    /*发送命令*/

    public void sendPowerOnCommand() {
        System.out.println("发送了通电的命令");
        electricCommand.exec();
    }

    public void sendPowerOffCommand() {
        System.out.println("发送了断电的命令");
        electricCommand.exec();
    }

    public void sendChannelUpCommand() {
        System.out.println("发送了频道+的命令");
        channelCommand.exec();
    }

    public void sendChannelDownCommand() {
        System.out.println("发送了频道-的命令");
        channelCommand.exec();
    }

    public void sendVolumeUpCommand() {
        System.out.println("发送了音量+的命令");
        volumeCommand.exec();
    }

    public void sendVolumeDownCommand() {
        System.out.println("发送了音量-的命令");
        volumeCommand.exec();
    }
}
```

>应用；
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/7/5 15:20
 */
public class Test {
    public static void main(String[] args) {
        Device tv = new Tv();
        Device radio = new Radio();
        CommandController commandController = new CommandController();

        //电视机的命令使用
        commandController.bindChannelCommand(new ChannelCommand(tv));
        commandController.bindElectricCommand(new ElectricCommand(tv));
        commandController.bindVolumeCommand(new VolumeCommand(tv));
        commandController.sendPowerOnCommand();
        commandController.sendPowerOffCommand();
        commandController.sendChannelUpCommand();
        commandController.sendChannelDownCommand();
        commandController.sendVolumeUpCommand();
        commandController.sendVolumeDownCommand();

        System.out.println();

        //收音机命令的使用
        commandController.bindChannelCommand(new ChannelCommand(radio));
        commandController.bindElectricCommand(new ElectricCommand(radio));
        commandController.bindVolumeCommand(new VolumeCommand(radio));
        commandController.sendPowerOnCommand();
        commandController.sendPowerOffCommand();
        commandController.sendChannelUpCommand();
        commandController.sendChannelDownCommand();
        commandController.sendVolumeUpCommand();
        commandController.sendVolumeDownCommand();
    }
}
```

# 三 总结
>通过命令模式的使用，使得原本耦合的系统（策略模式中策略与策略使用者的强绑定）变得松散、自由，可以随意组合；  
>发令者与执行者彻底解耦，这实现了对各模块的自由扩展，松散的系统得以成就繁多模块解耦的最终目的；

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/kMZUigTcYgvS5Y4yFVQQ8g)