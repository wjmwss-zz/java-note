# 代理模式Proxy
# 一 概述
>代理，顾名思义，业务应只关注业务本身，本职工作之外的事务不应由业务本身处理，通过代理对象的加入，让代理对象帮助完成不应由业务本身处理的事情，即为代理模式；  

>代理模式与装饰器模式：两者的UML类图几乎一样，区别在于代理模式强调的是对被代理对象的控制，而不是仅限于去装饰目标对象并增强其原有的功能；

# 二 静态代理StaticProxy
## 2.1 使用示例
```
package cn.http.test;

/**
 * 人的行为
 *
 * @author:wjm
 * @date:2020/6/29 22:31
 */
public interface Events {
    /**
     * 每天要去公司签到
     */
    void sign();
}
```
```
package cn.http.test;

/**
 * 真实角色/被代理对象：人
 *
 * @author:wjm
 * @date:2020/6/29 22:31
 */
public class People implements Events {
    private String name;

    public People(String name) {
        this.name = name;
    }

    @Override
    public void sign() {
        System.out.println("公司签到成功!");
    }
}
```
```
package cn.http.test;

/**
 * 巴士代理类：可以帮助真实角色完成一些行为
 *
 * @author:wjm
 * @date:2020/6/29 22:34
 */
public class BusProxy implements Events {
    /**
     * 代理需要持有被代理对象的引用
     */
    private People people;

    /**
     * 创建代理对象同时也创建被代理对象
     */
    public BusProxy() {
        this.people = new People("小明");
    }

    /**
     * 代理对象 与 被代理对象 实现同一接口；<p>
     * 代理对象实现接口方法，先增加代理行为，再调用 被代理对象 的接口方法实现
     */
    @Override
    public void sign() {
        System.out.println("坐公交...");
        people.sign();
    }
}
```
```
package cn.http.test;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/29 22:37
 */
public class Test{
    public static void main(String[] args) {
        //直接创建代理类，由代理完成被代理对象的行为
        Events busProxy = new BusProxy();
        busProxy.sign();
    }
}
```
>以上为静态代理的示例，静态代理仅仅适用于，一个代理对象代理一个被代理对象，如示例中是人需要去公司签到，通过巴士代理了人；  

>若存在多个被代理对象，且被代理对象的行为各不相同，此时使用静态代理，“代理对象”需要实现所有”被代理对象”实现的接口，并且每增加一个被代理对象，都需要对代理对象进行修改，这违反了设计模式的开闭原则（对扩展开放，对修改关闭）--> 动态代理应运而生；

# 三 动态代理DynamicProxy
## 3.1 使用示例
>两个真实角色、两个真实角色的行为接口；
```
package cn.http.test;

/**
 * 内网
 *
 * @author:wjm
 * @date:2020/6/29 23:14
 */
public interface Intranet {
    /**
     * 连接内网
     */
    void intranetConnect(String intranetUrl);
}
```
```
package cn.http.test;

/**
 * 外网
 *
 * @author:wjm
 * @date:2020/6/29 23:14
 */
public interface Extranet {
    /**
     * 连接外网
     */
    void extranetConnect(String extranetUrl);
}
```
```
package cn.http.test;

/**
 * 真实角色/被代理对象：光猫，用于外网通讯
 *
 * @author:wjm
 * @date:2020/6/29 23:19
 */
public class Modem implements Extranet {
    @Override
    public void extranetConnect(String extranetUrl) {
        System.out.println("光猫连接外网成功，url:" + extranetUrl);
    }
}
```
```
package cn.http.test;

/**
 * 真实角色/被代理对象：交换机，用于内网通讯
 *
 * @author:wjm
 * @date:2020/6/29 23:21
 */
public class Switch implements Intranet {
    @Override
    public void intranetConnect(String intranetUrl) {
        System.out.println("交换机连接内网成功，url:" + intranetUrl);
    }
}
```
>以上的两个真实角色在发生各自的行为前，希望加入一些共性的行为，例如关键字过滤，接下来使用动态代理去代理多个真实角色，并加入关键字过滤；
```
package cn.http.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 代理类：关键字过滤<p>
 * 实现InvocationHandler接口，通过反射实现动态代理
 *
 * @author:wjm
 * @date:2020/6/29 23:25
 */
public class KeywordFilter implements InvocationHandler {

    /**
     * 被代理对象，不关注具体类型
     */
    private Object proxiedObj;

    /**
     * 初始化代理类时，注入被代理对象
     *
     * @param proxiedObj
     */
    public KeywordFilter(Object proxiedObj) {
        this.proxiedObj = proxiedObj;
    }

    /**
     * 通过反射调用被代理对象的方法
     *
     * @param proxy  需要执行的方法所在的类
     * @param method 需要执行的方法
     * @param args   需要执行的方法的参数列表
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("进行了关键字过滤...");
        //传入代理对象完成反射调用
        return method.invoke(proxiedObj, args[0]);
    }
}
```
>应用；
```
package cn.http.test;

import java.lang.reflect.Proxy;

/**
 * 应用
 *
 * @author:wjm
 * @date:2020/6/29 23:48
 */
public class Test {
    public static void main(String[] args) {
        //外网
        Extranet extranet = (Extranet) Proxy.newProxyInstance(
                //真实角色的类对象
                Modem.class.getClassLoader(),
                //真实角色实现的接口
                Modem.class.getInterfaces(),
                //new 代理类对象(new 被代理类对象)
                new KeywordFilter(new Modem())
        );
        extranet.extranetConnect("http://test1.com");

        //内网
        Intranet intranet = (Intranet) Proxy.newProxyInstance(
                Switch.class.getClassLoader(),
                Switch.class.getInterfaces(),
                new KeywordFilter(new Switch())
        );
        intranet.intranetConnect("http://test2.com");
    }
}
```
>可以看到，无论是访问外网还是内网，只需要分别生成相应的代理并调用即可，在真实角色的行为执行前，都会先执行代理行为（过滤器）；

# 四 总结
>静态代理与动态代理：  
>* 静态代理：一个代理类只能代理一个真实角色（真实角色实现其接口相应的行为），当有多个真实角色，且其代理的是相同行为的时候，静态代理的缺点就很明显了：由于java支持多实现，但总不能让一个代理类实现所有真实角色对应的接口吧；  
>* 动态代理：无论有多少个真实角色，只需要编写代理类的共同行为即可，代理类只需要实现InvocationHandler，通过反射去执行真实角色的具体方法，因为这是在运行时动态的生成代理，从而可以兼容任何接口；    

>动态代理的应用：    
>* 如Spring的面向切面AOP，只需要定义好一个切面类@Aspect，声明其切入点@Pointcut（被代理的哪些对象的哪些方法，对应上述例子中的“内网”、“外网”接口中的方法），以及被切入的代码块（即代理的行为，比如上述例子中代理类的过滤功能，可分为前置执行@Before，后置执行@After，以及异常处理@AfterThrowing等），配置好后框架会自动生成代理并切入目标执行；    
>* 如事务控制，在所有业务代码之前先切入“事务开始”，执行过后再切入“事务提交”，如果抛异常被捕获则执行“事务回滚”，如此就不必要在每个业务类中去写这些重复代码了；  

笔记整理来源：[技术文档](https://mp.weixin.qq.com/s/3wdp73HlmVb-XsmHWYHTfw)