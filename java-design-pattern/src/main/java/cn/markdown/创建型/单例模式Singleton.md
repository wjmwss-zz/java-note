# 单例模式Singleton
# 一 概述
>单例模式应用广泛，故这里只介绍常见的四种单例运用

# 二 使用示例
>单例模式一：
```
package cn.xair.manager.handler;

/**
 * 单例模式举例
 *
 * @author:wjm
 * @date:2020/5/26 15:43
 */
public class TestSingleton {
    /**
     * 单例模式（痴汉式）
     * 建议直接用痴汉式即可，饿汉式、懒汉式在现在这个内存时代不需要考虑
     */
    public static final TestSingleton testSingleton = new TestSingleton();

    /**
     * 构造方法私有化
     */
    private TestSingleton() {
    }
    
    /**
     * 提供一个公开获取对象的方法
     */
    public static TestSingleton getInstance() {
        return testSingleton;
    }
}

class Test {
    public static void main(String[] args) {
        //使用该单例模式的方法：
        TestSingleton testSingleton = TestSingleton.getInstance();
    }
}
```
>单例模式二：
```
package cn.xair.manager.handler;

import javax.annotation.PostConstruct;

/**
 * 单例模式举例
 *
 * @author:wjm
 * @date:2020/5/26 15:43
 */
public class TestSingleton {
    public static TestSingleton S = null;

    /**
     * 用@PostConstruct来构建单例模式
     */
    @PostConstruct
    public void init() {
        S = this;
    }
}

class Test {
    public static void main(String[] args) {
        //使用该单例模式的方法
        TestSingleton.S;
    }
}
```
>单例模式三：（这里只是示例，这种方式虽然是单例，但其实际使用的意义，不是专门给你做单例的，而是在需要注入第三方类时，使用[@Bean](https://my.oschina.net/bean)；或者是与@Configuration作为配置类使用）
```
package cn.xair.manager.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单例模式举例
 *
 * @author:wjm
 * @date:2020/5/26 16:41
 */
@Component
public class TestSingleton {
    private final ThirdParty thirdParty;

    /**
     * Spring的@Bean注解用于告诉方法，产生一个Bean对象，然后这个Bean对象交给Spring管理。
     * 产生这个Bean对象的方法Spring只会调用一次，随后这个Spring将会将这个Bean对象放在自己的IOC容器中。
     * 注：@Bean注解需要在被注册为bean的类的内部使用，常与@Configuration、@Component一起使用
     *
     * @return
     */
    @Bean
    public ThirdParty thirdParty() {
        return new ThirdParty();
    }
}

/**
 * 这是一个第三方类
 */
class ThirdParty {

}

/**
 * 应用
 */
class Test{
    private final ThirdParty thirdParty;

    @Autowired
    public Test(ThirdParty thirdParty) {
        this.thirdParty = thirdParty;
    }

    public static void main(String[] args){
        //使用thirdParty.....
    }
}
```
>单例模式四：
```
package cn.gue.demo.bean.euem.exception;

/**
 * 枚举类用来做单例模式
 *
 * @Author:wjm
 * @Date:2020/5/26 10:02
 */
public enum TestEnumSingleton {
    I;

    public void doSomething() {
        System.out.println("doSomething");
    }

    public static void main(String[] args) {
        TestEnumSingleton.I.doSomething();
    }

    /**
     * 单例模式的最佳方法：枚举类
     * 《Effective Java》一书中对使用枚举实现单例的方式推崇备至：使用枚举实现单例的方法虽然还没有广泛采用,但是单元素的枚举类型已经成为实现Singleton的最佳方法。
     * 枚举实现的单例可轻松地解决两个问题：
     * 1、线程安全问题。因为Java虚拟机在加载枚举类的时候，会使用ClassLoader的loadClass方法，这个方法使用了同步代码块来保证线程安全。
     * 2、避免反序列化破坏单例。因为枚举的反序列化并不通过反射实现。
     *
     * 具体可参考：https://www.cnblogs.com/happy4java/p/11206105.html
     */
}
```

# 三 类UML图
>单例模式
![单例模式] 

[单例模式]: