# Spring源码: 加载配置文件

## Demo
```
public static void main(String[] args) {
    XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("beans.xml"));
    User user = factory.getBean(User.class);
    System.out.println("user = " + user);
}
```
>学习xml配置文件是如何加载到内存中并解析的；

## Spring的文件读取功能
>文件读取在 Spring 中很常见，也算是一个比较基本的功能，而且 Spring 提供的文件加载方式，不仅仅在 Spring 框架中可以使用，我们在项目中有其他文件加载需求也可以使用。

>Spring 中使用 Resource 接口来封装底层资源，Resource 接口本身实现自 InputStreamSource 接口：