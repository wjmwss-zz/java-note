IoC Service Provider 为被注入对象提供被依赖对象也有如下几种方式：  
构造方法注入、stter方法注入、接口注入。

① 构造器注入

构造器注入，顾名思义就是被注入的对象通过在其构造方法中声明依赖对象的参数列表，让外部知道它需要哪些依赖对象。

YoungMan(BeautifulGirl beautifulGirl) {
    this.beautifulGirl = beautifulGirl;
}
构造器注入方式比较直观，对象构造完毕后就可以直接使用，这就好比你出生你家里就给你指定了你媳妇。

② setter 方法注入

对于 JavaBean 对象而言，我们一般都是通过 getter 和 setter 方法来访问和设置对象的属性。所以，当前对象只需要为其所依赖的对象提供相对应的 setter 方法，就可以通过该方法将相应的依赖对象设置到被注入对象中。如下：

public class YoungMan {

    private BeautifulGirl beautifulGirl;

    public void setBeautifulGirl(BeautifulGirl beautifulGirl) {
        this.beautifulGirl = beautifulGirl;
    }

}
相比于构造器注入，setter 方式注入会显得比较宽松灵活些，它可以在任何时候进行注入（当然是在使用依赖对象之前），这就好比你可以先把自己想要的妹子想好了，然后再跟婚介公司打招呼，你可以要林志玲款式的，赵丽颖款式的，甚至凤姐哪款的，随意性较强。

③ 接口方式注入

接口方式注入显得比较霸道，因为它需要被依赖的对象实现不必要的接口，带有侵入性。一般都不推荐这种方式，感兴趣推荐阅读：

[依赖注入的三种实现形式](https://wiki.jikexueyuan.com/project/spring-ioc/iocordi-1.html#6e5dfcd838f3a79e9129641785cf736f)

这里补充一个知识点：classPath
在编译打包后的项目中，根目录是META-INF和WEB-INF。这个时候，我们可以看到classes这个文件夹,
它就是我们要找的classpath。
在第1个例子里，classpath:entry/dev/spring-mvc. xml中，classpath就 是指WEB- INF/classes/这个
目录的路径。需要声明的一点是，使用classpath: 这种前缀，就只能代表一个文件。
在第2个例子里，classpath*: **/mapper/mapping/*Mapper.xml，使用classpath*:这种前缀，则可以
代表多个匹配的文件; **/mapper/mapping/ *Mapper . xml，双星号**表示在任意目录下，也就是说在
WEB- INF/classes/下任意层的目录，只要符合后面的文件路径，都会被作为资源文件找到。