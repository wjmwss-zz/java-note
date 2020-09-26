# IoC 之 Spring 统一资源加载策略
1、
地址前缀：classpath:	
示例：classpath:top/mcwebsite/resource/bean.xml	
从类路径中加载资源，classpath:和classpath:/是等价的，都是相对于类的路径。资源文件可以在标准的文件系统中，也可以在JAR和ZIP的类包中；

2、
地址前缀：file:	
示例：file:/config/bean,xml	
使用UrlResource从文件系统目录中装载资源，可采用绝对或相对路径；

3、
地址前缀：http://	
示例：http://www.mcwebsite.top/bean.xml	
使用UrlResource从Web服务器中装载资源；

4、
地址前缀：ftp://	
示例：ftp://www.mcwebsite.top/bean.xml	
使用UrlResource从ftp服务器中装载资源；

5、spring的加载资源
首先，通过 ProtocolResolver 来加载资源（自定义接口）；

其次，以 / 开头，返回 ClassPathContextResource 类型的资源； 
return new ClassPathContextResource(path, getClassLoader());

再次，以 classpath: 开头，返回 ClassPathResource 类型的资源；
return new ClassPathResource(location.substring(CLASSPATH_URL_PREFIX.length()), getClassLoader());

然后，根据是否为文件 URL ，是则返回 FileUrlResource 类型的资源，否则返回 UrlResource 类型的资源；（这一步报错，就到了最后一步）
return (ResourceUtils.isFileURL(url) ? new FileUrlResource(url) : new UrlResource(url));

最后，返回 ClassPathContextResource 类型的资源；
return new ClassPathContextResource(path, getClassLoader());


"D:/Users/chenming673/Documents/spark.txt" : ClassPathResource 类型；
"/Users/chenming673/Documents/spark.txt" : ClassPathContextResource 类型；
"file:/Users/chenming673/Documents/spark.txt" : UrlResource 类型；
"http://www.baidu.com" : UrlResource 类型；


针对第一个，可以看到spring是没有很好地处理的，在spring处理第一个的时候，最终是抛了异常，使用了上述过程中“最后”的一种方式来创建，因此最终被创建成了ClassPathContextResource的资源，但是我们更希望他被处理成FileSystemResource的类型：org.springframework.core.io.FileSystemResourceLoader，这个类继承 DefaultResourceLoader ，且覆写了 #getResourceByPath(String) 方法，使之从文件系统加载资源并以 FileSystemResource 类型返回，这样我们就可以得到想要的资源类型；


ProtocolResolver自定义接口：
在 Spring 中你会发现该接口并没有实现类，它需要用户自定义，自定义的 Resolver 如何加入 Spring 体系呢？调用 DefaultResourceLoader#addProtocolResolver(ProtocolResolver) 方法即可。代码如下：
```
/**
 * ProtocolResolver 集合
 */
private final Set<ProtocolResolver> protocolResolvers = new LinkedHashSet<>(4);

public void addProtocolResolver(ProtocolResolver resolver) {
	Assert.notNull(resolver, "ProtocolResolver must not be null");
	this.protocolResolvers.add(resolver);
}
```


5、Resource 为 Spring 框架所有资源的抽象和访问接口，AbstractResource抽象类是他的默认实现，因此如果我们想要实现自定义的 Resource ，记住不要实现 Resource 接口，而应该继承 AbstractResource 抽象类，然后根据当前的具体资源特性覆盖相应的方法即可；

6、ResourceLoader 为资源统一加载接口，DefaultResourceLoader类是他的默认实现，其加载最为核心的方法是DefaultResourceLoader的#getResource(String location)方法，他最开始是通过 ProtocolResolver 来加载资源，ProtocolResolver 是用户自定义协议资源解决策略，在自定 ResourceLoader 的时候我们除了可以继承DefaultResourceLoader外还可以实现 ProtocolResolver 接口来实现自定资源加载协议。



7、
```
ant风格通配符：
? 匹配任何单字符
* 匹配0或者任意数量的字符
** 匹配0或者更多的目录

如：
/project/*.a 匹配项目根路径下所有在project路径下的.a文件

/project/p?ttern 匹配项目根路径下/project/pattern 和/app/pXttern,但是不包括/app/pttern
/**/example 匹配项目根路径下/project/example, /project/foow/example,和/example

/projet//dir/file.* 匹配项目根路径下/project/di/file.jsp, /projectfoow/dirfile.html,/projectfoow/arldir/file.pdf

**/*.jsp 匹配项目根路径下任何的.jsp文件
需要注意的是:
最长匹配原则(has more characters)：URL请求/project/dir/file.jsp，现在存在两个路径匹配模式/**/*.jsp和/project/ir/*.jsp，那么会根据模式/project/dir/* .jsp来匹配

Spring中支持Ant风格的路径匹配：PathMatchingResourcePatternResolver
```

8、ClassRelativeResourceLoader 扩展的功能是，可以根据给定的class 所在包或者所在包的子包下加载资源。


9、ResourceLoader 的 Resource getResource(String location) 方法，每次只能根据 location 返回一个 Resource 。当需要加载多个资源时，我们除了多次调用 #getResource(String location) 方法外，别无他法。org.springframework.core.io.support.ResourcePatternResolver 是 ResourceLoader 的扩展，它支持根据指定的资源路径匹配模式每次返回多个 Resource 实例，其定义如下：
```
public interface ResourcePatternResolver extends ResourceLoader {

	String CLASSPATH_ALL_URL_PREFIX = "classpath*:";

	Resource[] getResources(String locationPattern) throws IOException;

}
```


10、determineRootDir(String location) 方法，主要是用于确定根路径。

11、至此 Spring 整个资源记载过程已经分析完毕。下面简要总结下：

Spring 提供了 Resource 和 ResourceLoader 来统一抽象整个资源及其定位。使得资源与资源的定位有了一个更加清晰的界限，并且提供了合适的 Default 类，使得自定义实现更加方便和清晰。

AbstractResource 为 Resource 的默认抽象实现，它对 Resource 接口做了一个统一的实现，子类继承该类后只需要覆盖相应的方法即可，同时对于自定义的 Resource 我们也是继承该类。

DefaultResourceLoader 同样也是 ResourceLoader 的默认实现，在自定 ResourceLoader 的时候我们除了可以继承该类外还可以实现 ProtocolResolver 接口来实现自定资源加载协议。

DefaultResourceLoader 每次只能返回单一的资源，所以 Spring 针对这个提供了另外一个接口 ResourcePatternResolver ，该接口提供了根据指定的 locationPattern 返回多个资源的策略。其子类 PathMatchingResourcePatternResolver 是一个集大成者的 ResourceLoader ，因为它既实现了 Resource getResource(String location) 方法，也实现了 Resource[] getResources(String locationPattern) 方法。

另外，如果胖友认真的看了本文的包结构，我们可以发现，Resource 和 ResourceLoader 核心是在，spring-core 项目中。

如果想要调试本小节的相关内容，可以直接使用 Resource 和 ResourceLoader 相关的 API ，进行操作调试。