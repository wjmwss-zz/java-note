# 一 概述
>Java 8 希望有自己的编程风格，并与 Java 7 区别开；它支持函数式编程，新的 JavaScript 引擎，新的日期 API，新的Stream API 等； 

>本文将介绍一些常用的新特性，如下：
>* Lambda表达式：Lambda 允许把函数作为一个方法的参数（函数作为参数传递到方法中）；
>* 方法引用：可以直接引用已有Java类或对象（实例）的方法或构造器；与Lambda结合使用，可以使语言的构造更紧凑简洁，减少冗余代码；
>* Optional 类；
>* Stream API：新添加的Stream API(java.util.stream)，把函数式编程风格引入到Java中；
>* Date Time API：加强对日期与时间的处理；
>* 默认方法：接口支持已实现的方法，用default修饰（注意区别于Java的访问控制符default，两者不是同一个东西）；

>以下特性不做详细介绍：
>* 新工具：新的编译工具，如：Nashorn引擎、jjs、类依赖分析器jdeps；
>* Nashorn、JavaScript 引擎：Java 8提供了一个新的 Nashorn、javascript 引擎，它允许我们在JVM上运行特定的javascript应用；

# 二 Lambda表达式
## 2.1 概述
>* Lambda是一个匿名函数，可以理解为是一段可以传递的代码，可以将代码像传递参数、传递数据一样进行传输；  
>* 使用Lambda，需要“函数式接口”的支持；  

>* 函数式接口:  
>   * 接口中只有一个抽象函数的接口，称为函数式接口；***（是经典的把函数抽象成接口的应用）***
>   * 使用 @FunctionalInterface 注解接口，标明该接口为函数式接口；
>   * 注：  
>       * 不使用 @FunctionalInterface 注解的接口，未必就不是函数式接口，一个接口是否是函数式接口的条件只有一个：只有一个抽象函数（Object的方法不算在内）；
>       * 但使用了 @FunctionalInterface 注解的接口就必须为函数式接口，该注解可以帮助在编译时就检查一个接口是否为函数式接口；

>* JDK中常见的函数式接口：  
```
@FunctionalInterface
public interface Runnable {
    void run();
}
```
```
@FunctionalInterface
public interface Callable<V> {
    V call() throws Exception;
}
```
>* 以下接口中虽然有两个方法，但由于hashCode()是Object类中的方法，因此该接口也是函数式接口：
```
@FunctionalInterface
public interface CustomInterface {
    void doSomething();
    // Object类中的方法
    int hashCode();  
}
```

## 2.2 语法结构
>示例：`Runnable runnable = () -> {System.out.println("Hello World");} `  
>左边括号(对应函数式接口抽象函数的参数列表) -> {抽象函数的实现}，其中 -> 为固定写法，没有实际含义；  

>* 抽象函数的实现只有一行代码，{}可省略：  
`Runnable runnable = () -> {System.out.println("Hello World");}; `   
-->  
`Runnable runnable = () -> System.out.println("Hello World");`

>* 抽象函数入参只有一个参数，且没有返回值，()可省略：  
`print((str) -> System.out.println(msg));`   
-->   
`print(str -> System.out.println(msg));`

>* 抽象函数有返回值，其实现只有一条语句，则return以及句末;可省略：  
`operation(number1, number2, (x, y) -> return x + y; );`  
-->  
`operation(number1, number2, (x, y) -> x + y );`

>* 抽象函数有两个以上参数，其实现有多行代码，此时{}不能省略：
```
operation(number1, number2, (x,y) -> {
    System.out.println(“加法与运算”);
    return (x + y);
});
```

>* 抽象函数入参的数据类型可以不用声明，JVM可以通过上下文自动推断；但要注意，有多个入参时，要么所有入参都声明数据类型，要么所有入参都不声明数据类型：
```
// 错误
operator(v1,v2,(Integer x,y) -> x - y);   
// 正确
return operator(v1,v2,(x,y) -> x - y);    
```

### 2.2.1 使用示例1
```
package cn.hello;

/**
 * 计算器
 *
 * @author:wjm
 * @date:2020/6/18 22:17
 */
@FunctionalInterface
public interface Calculator<Integer> {
    /**
     * 运算
     *
     * @param number1
     * @param number2
     * @return
     */
    Integer operation(Integer number1, Integer number2);
}
```
```
package cn.hello;

/**
 * Lambda示例1
 *
 * @author:wjm
 * @date:2020/6/18 22:21
 */
public class Lambda1 {
    /**
     * 提供一个包装了计算器运算功能的函数，供Lambda实现
     *
     * @param number1
     * @param number2
     * @param calculator
     * @return
     */
    public Integer operation(Integer number1, Integer number2, Calculator<Integer> calculator) {
        return calculator.operation(number1, number2);
    }

    /**
     * Lambda表达式实现加法
     *
     * @param number1
     * @param number2
     * @return
     */
    public Integer add(Integer number1, Integer number2) {
        //
        /**
         * 左边括号对应的是Calculator函数式接口抽象函数的入参，右边x+y是抽象函数的实现；<p>
         * operation的第三个入参，可以理解为其实就是传入了一个实现了Calculator抽象函数的对象，从而使得方法能够被实现；
         */
        return operation(number1, number2, (x, y) -> x + y);
    }

    public Integer subtract(Integer number1, Integer number2) {
        return operation(number1, number2, (x, y) -> x - y);
    }

    public Integer multiply(Integer number1, Integer number2) {
        return operation(number1, number2, (x, y) -> x * y);
    }

    public Integer divide(Integer number1, Integer number2) {
        return operation(number1, number2, (x, y) -> x / y);
    }

    public static void main(String[] args) {
        Lambda1 lambda1 = new Lambda1();
        Integer number1 = 10;
        Integer number2 = 5;
        System.out.println(lambda1.add(number1, number2));
        System.out.println(lambda1.subtract(number1, number2));
        System.out.println(lambda1.multiply(number1, number2));
        System.out.println(lambda1.divide(number1, number2));
    }
}
```
>总结：operation的第三个入参，可以理解为其实就是传入了一个实现了Calculator抽象函数的对象，从而使得方法能够被实现；

## 2.3 Java8内置的函数式接口
>* 四大核心函数式接口：
>   * Consumer<T>    : 消费型接口（无返回值，有去无回）void accept(T t);
>   * Supplier<T>    : 供给型接口 T get();
>   * Function<T, R> : 函数型接口 R apply(T t);
>   * Predicate<T>   : 断言型接口 boolean test(T t);
>* 对应的增强型：
>   * BiConsumer<T>    : 消费型接口（无返回值，有去无回）void accept(T t, U u);
>   * BiFunction<T, R> : 函数型接口 R apply(T t, U u);
>   * BiPredicate<T>   : 断言型接口 boolean test(T t, U u);


### 2.3.1 使用示例2
```
package cn.http.test;

import lombok.Data;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Lambda表达式：使用JDK提供的四大核心函数式接口
 *
 * @author:wjm
 * @date:2020/6/19 15:44
 */
public class Lambda2 {
    /**
     * 打印user信息
     */
    public void print(User user, Consumer<User> userConsumer) {
        userConsumer.accept(user);
    }

    /**
     * 返回一个user
     */
    public User getUser(Supplier<User> userSupplier) {
        return userSupplier.get();
    }

    /**
     * 转换一个user
     */
    public User transformUser(User user, Function<User, User> function) {
        return function.apply(user);
    }

    /**
     * 检验User是否合法
     */
    public boolean checkUser(User user, Predicate<User> predicate) {
        return predicate.test(user);
    }

    public static void main(String[] args) {

        User userObj = new User();
        userObj.setUsername("小明1");
        userObj.setAge(22);

        // 测试Consumer
        Lambda2 lambda2 = new Lambda2();
        lambda2.print(userObj, user -> System.out.println(user));

        // 测试Supplier
        final User user1 = lambda2.getUser(() -> {
            User user = new User();
            user.setUsername("小明2");
            user.setAge(22);
            return user;
        });
        System.out.println(user1);

        // 将小明1的年龄改为25
        final User user2 = lambda2.transformUser(userObj, (user) -> {
            user.setAge(25);
            return user;
        });
        System.out.println(user2);

        // 判断User是否是小明1
        final boolean checkUser = lambda2.checkUser(userObj, (user -> user.getUsername().equals("小明1")));
        System.out.println(checkUser);
    }
}

/**
 * 一个pojo
 */
@Data
class User {
    private String username;
    private int age;
}
```

笔记整理来源：[技术文档1](https://www.cnblogs.com/wuhenzhidu/p/lambda.html)

# 三 方法引用
## 3.1 概述
>当Lambda表达式左边的参数与右边抽象函数实现的参数一致时，就可以通过方法引用的语法来令到代码更整洁，其本质是：当Lambda表达式仅仅是调用一些已经存在的方法，除了调用动作外，没有其他任何多余的动作，此时则可以使用方法引用；

## 3.2 语法结构
类型 | 语法 | 	Lambda表达式 
:-: | :-: | :-:
静态方法引用 |	类名::staticMethod |	(args) -> 类名.staticMethod(args)
实例方法引用 |	instance::instanceMethod |	(args) -> instance.instanceMethod(args)
对象方法引用 |	类名::instanceMethod |	(instance,args) -> 类名.instanceMethod(args)
构建方法引用 |	类名::new |	(args) -> new 类名(args)

### 3.2.1 示例代码
>静态方法引用；
```
package cn.http.test;

import java.util.Arrays;
import java.util.List;

/**
 * Lambda表达式：静态方法引用
 *
 * @author:wjm
 * @date:2020/6/21 16:13
 */
public class Lambda3 {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 5, 8, 2, 9);
        //其对应的Lambda表达式
        list.sort((x, y) -> Integer.compare(x, y));
        //静态方法引用
        list.sort(Integer::compare);
        System.out.println(list);
    }
}
```
>实例方法引用；
```
package cn.http.test;

import lombok.Getter;
import java.util.function.Supplier;

/**
 * Lambda表达式：实例方法引用
 *
 * @author:wjm
 * @date:2020/6/21 16:13
 */
public class Lambda3 {
    public static void main(String[] args) {
        User user = new User("小明");
        //其对应的Lambda表达式
        Supplier<String> supplier1 = () -> user.getName();
        //实例方法引用
        Supplier<String> supplier2 = user::getName;
        System.out.println(supplier1.get());
        System.out.println(supplier2.get());
    }
}

@Getter
class User {
    private String name;

    public User(String name) {
        this.name = name;
    }
}
```
>对象方法引用：若Lambda参数列表中的第一个参数是实例方法的参数调用者，而第二个参数是实例方法的参数时，可以使用对象方法引用；
```
package cn.http.test;

import java.util.function.BiPredicate;

/**
 * Lambda表达式：对象方法引用
 *
 * @author:wjm
 * @date:2020/6/21 16:13
 */
public class Lambda3 {
    public static void main(String[] args) {
        /**
         * 其对应的Lambda表达式
         * x作为抽象函数实现方法入参的第一个参数，也作为抽象函数具体实现中的参数调用者
         * y作为抽象函数实现方法入参的第二个参数，也作为抽象函数具体实现里参数调用者调用时使用的参数（这个参数可以是多个，args保持一致即可）
         */
        BiPredicate<String, String> biPredicate1 = (x, y) -> x.equals(y);
        //对象方法引用
        BiPredicate<String, String> biPredicate2 = String::equals;

        boolean test = biPredicate2.test("xy", "xx");
        System.out.println(test);
    }
}
```
>构造方法引用：调用的构造器的参数列表要与函数式接口中抽象方法的参数列表保持一致。
```
package cn.http.test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Lambda表达式：构造方法引用
 *
 * @author:wjm
 * @date:2020/6/21 16:13
 */
public class Lambda3 {
    public static void main(String[] args) {
        //其对应的Lambda表达式
        Supplier<List<User>> userSupplier1 = () -> new ArrayList<>();
        //构造方法引用
        Supplier<List<User>> userSupplier2 = ArrayList::new;
    }
}

class User {

}
```

笔记整理来源：[技术文档2](https://www.cnblogs.com/wuhenzhidu/p/10727065.html)

# 四 Optional类
## 4.1 概述
>Optional是一个容器对象，它可能包含空值，也可能包含非空值；  
>例：当属性value被设置时，isPesent()方法将返回true，并且get()方法将返回Optional的值，如果容器为空，则抛出NoSuchElementException异常；  
>该类支持泛型，即其属性value可以是任何对象的实例；

## 4.2 Optional API
函数 | 备注 
:-: | :-:  
private Optional() | 无参构造，构造一个空Optional 
private Optional(T value) | 根据传入的非空value构建Optional 
public static<T> Optional<T> empty() | 返回一个空的Optional，该实例的value为空 
public static <T> Optional<T> of(T value) | 根据传入的非空value构建Optional，与Optional(T value)方法作用相同 
public static <T> Optional<T> ofNullable(T value) | 与of(T value)方法不同的是，ofNullable(T value)允许你传入一个空的value，当传入的是空值时其创建一个空Optional，当传入的value非空时，与of()作用相同 
public T get() | 返回Optional的值，如果容器为空，则抛出NoSuchElementException异常 
public boolean isPresent() | 判断当家Optional是否已设置了值 
public void ifPresent(Consumer<? super T> consumer) | 判断当家Optional是否已设置了值，如果有值，则调用Consumer函数式接口进行处理 
public Optional<T> filter(Predicate<? super T> predicate) | 如果设置了值，且满足Predicate的判断条件，则返回该Optional，否则返回一个空的Optional 
public<U> Optional<U> map(Function<? super T, ? extends U> mapper) | 如果Optional设置了value，则调用Function对值进行处理，并返回包含处理后值的Optional，否则返回空Optional 
public<U> Optional<U> flatMap(Function<? super T, Optional<U>> mapper) | 与map()方法类型，不同的是它的mapper结果已经是一个Optional，不需要再对结果进行包装 
public T orElse(T other) | 如果Optional值不为空，则返回该值，否则返回other　 
public T orElseGet(Supplier<? extends T> other) | 如果Optional值不为空，则返回该值，否则根据other另外生成一个 
public <X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier) throws X | 如果Optional值不为空，则返回该值，否则通过supplier抛出一个异常 

笔记整理来源：[技术文档3](https://www.cnblogs.com/wuhenzhidu/p/10765655.html)

# 五 Stream API
## 5.1 概述
>Stream 是Java8中处理集合的关键抽象概念，它可以对集合进行非常复杂的查找、过滤、筛选等操作，基于这特性，可以快速并优雅的实现对数据的处理；

## 5.2 Stream操作步骤
>* 创建Stream：从一个数据源，如集合/数组中获取流；  
>* 中间操作：一个操作的中间链，对数据源的数据进行操作；    
>* 终止操作：一个终止操作，执行中间操作链，并产生结果。要注意的是，对流的操作完成后需要进行终止操作（或用JAVA7的try-with-resources）；  

### 5.2.1 示例代码
>* 创建流式：  
>   * list.Stream();
>* 中间操作：  
>   * 筛选与切片：  
>       * filter：过滤器，按自定义的boolean条件进行过滤流数据，接收的参数为：Predicate<? super T> predicate；
>       * limit：截取器，截取流数据的前n位，从1开始，返回被截取的流数据；
>       * skip(n)：跳过n位元素，从1开始，返回剩余的流数据（与limit互补）；   
>       * distinct：去重，通过流生成元素的hashCode()和equals()去除重复元素；  
>   * 映射：
>       * map：转换器，将元素转换成其他形式或提取信息，接收的参数为：Function< ? super T, ? extends R > mapper，该函数会被应用到每个元素上，并将其映射成一个新的元素； 
>       * flatMap：接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流；    
>   * 排序：  
>       * sorted()：自然排序(Comparable)；  
>       * sorted(Comparator< ? super T > comparator)：定制排序，按照自定义的规则排序；  
>* 终止操作（***会关闭流***）：  
>   * 遍历：
>       * foreach；
>   * 查找与匹配：  
>       * allMatch：检查是否匹配所有元素；
>       * anyMatch：检查是否至少匹配一个元素；
>       * noneMatch：检查是否没有匹配所有元素；
>       * findFirst：返回第一个元素；
>       * findAny：返回当前流中的任意元素；
>       * count：返回流中元素的总个数；
>       * max：返回流中最大值；
>       * min：返回流中最小值；
>   * 归约：  
>       * reduce：将流中元素反复结合起来，得到一个值；
>   * 收集：  
>       * collect：将流转换为其他形式，接收一个Collector接口实现，用于Stream进行汇总；
>       * count：计算总数；

```
package cn.http.test;

import cn.xag.bean.UserPo;
import java.util.*;
import java.util.stream.Stream;

/**
 * Stream API
 *
 * @author:wjm
 * @date:2020/6/22 10:44
 */
public class Test {
    public static void main(String[] args) {

        List<UserPo> userPoList = new ArrayList<>();
        userPoList.add(new UserPo("小明1", 18, "广州", "woman"));
        userPoList.add(new UserPo("小明2", 20, "广州", "man"));
        userPoList.add(new UserPo("小明3", 24, "广州", "man"));
        userPoList.add(new UserPo("小明4", 18, "广州", "woman"));
        userPoList.add(new UserPo("小明5", 18, "惠州", "man"));
        userPoList.add(new UserPo("小明6", 20, "惠州", "man"));
        userPoList.add(new UserPo("小明7", 18, "佛山", "man"));
        userPoList.add(new UserPo("小明8", 20, "佛山", "woman"));
        userPoList.add(new UserPo("小明9", 24, "佛山", "man"));

        /**************************************中间操作：筛选与切片**************************************/

        /**
         * filter：过滤器，按自定义的boolean条件进行过滤流数据，接收的参数为：Predicate<? super T> predicate；<p>
         * 经过userPoList.stream()后，filter里面的元素为userPoList中的userPo；<p>
         * 找到年龄大于18岁的user并输出；
         */
        userPoList.stream().filter(userPo -> userPo.getAge() > 18).forEach((userPo) -> System.out.println(userPo));
        userPoList.stream().filter(userPo -> userPo.getAge() > 18).forEach(System.out::println);

        /**
         * limit：截取器，截取流数据的前n位，从1开始，返回被截取的流数据；<p>
         * 找到地址为广州的user，并截取前2位user进行输出；
         */
        userPoList.stream().filter((userPo) -> userPo.getAddress().equals("广州")).limit(2).forEach(System.out::println);

        /**
         * skip：跳过n位元素，从1开始，返回剩余的流数据（与limit互补）；<p>
         * 找到地址为广州的user，并跳过第一位，输出剩余的user；
         */
        userPoList.stream().filter((userPo) -> userPo.getAddress().equals("广州")).skip(1).forEach(System.out::println);

        /**
         * distinct：去重；<p>
         * 注：参考的技术文档  中对于distinct的用法是错误的，文档中的去重是对实体类的去重，但就算实体类里面的所有属性都是同一个值，其实体类对象依然不是同一个；<p>
         * 这里直接用了字符串数组来测试distinct；<p>
         * 输出：AA BB CC
         */
        List<String> testList1 = Arrays.asList("AA", "BB", "CC", "BB", "CC", "AA", "AA");
        testList1.stream().distinct().forEach(System.out::println);

        /**************************************中间操作：映射**************************************/

        /**
         * map：转换器，将元素转换成其他形式或提取信息，接收的参数为：Function< ? super T, ? extends R > mapper，该函数会被应用到每个元素上，并将其映射成一个新的元素；
         */
        userPoList.stream().map(userPo -> userPo.getName()).distinct().forEach(System.out::println);

        /**
         * 有一个字符列表，需要获取到每一个字符；
         */
        List<String> testList2 = Arrays.asList("aaa", "bbb", "ccc", "ddd", "ddd");
        Stream<Stream<Character>> streamStream = testList2.stream().map((str) -> {
            List<Character> characterList = new ArrayList<>();
            for (Character character : str.toCharArray()) {
                characterList.add(character);
            }
            return characterList.stream();
        });
        //这是一个流中流，需要对流中的每个流进行打印；
        // streamStream.forEach(System.out::println); 这样打印出来的是 流中流 里的 流
        streamStream.forEach(sm -> sm.forEach(System.out::print));
        //flatMap：如果希望返回的是一个流，而不是含有多个流的流，则可以使用flatMap；
        final Stream<Character> characterStream = testList2.stream().flatMap(Test::getCharacterByString);//Test::getCharacterByString是对上述map例子的封装
        characterStream.forEach(System.out::print);
        //map在接收到流后，通过抽象函数的实现，将Stream放入到一个Stream中，最终返回了一个包含了多个Stream的Stream；
        //flatMap在接收到Stream后，将接收到的Stream中的每个元素取出来放入一个Stream中，最终返回了一个包含多个元素的Stream；

        /**************************************中间操作：排序**************************************/

        /**
         * sort()：自然排序，将数字从小到大排列
         */
        List<Integer> testList3 = Arrays.asList(1, 2, 3, 11, 2, 4);
        testList3.stream().sorted().forEach(System.out::println);

        /**
         * sorted(Comparator< ? super T > comparator)：定制排序，按照自定义的规则排序；<p>
         * 将UserPo按年龄从小到大排序，若年龄相同，则再按姓名排序（String:按首字母排序）；
         */
        Stream<UserPo> sorted = userPoList.stream().sorted((userPo1, userPo2) -> {
            if (!userPo1.getAge().equals(userPo2.getAge())) {
                return userPo1.getAge().compareTo(userPo2.getAge());
            }
            return userPo1.getName().compareTo(userPo2.getName());
        });
        sorted.forEach(System.out::println);

        /**************************************终止操作：查找与匹配**************************************/

        /**
         * allMatch：检查是否匹配所有元素；接收的参数为：Predicate<? super T> predicate，返回boolean<p>
         * 流数据是否都为成年人
         */
        boolean isAdult = userPoList.stream().allMatch(userPo -> userPo.getAge() >= 18);

        /**
         * max、min：求最大最小值，接收的参数为：Comparator<? super T> comparator，返回Optional，需要通过get取值
         */
        final Optional<UserPo> maxAge = userPoList.stream().max((userPo1, userPo2) -> userPo1.getAge().compareTo(userPo2.getAge()));
        System.out.println("年龄最大的人：" + maxAge.get().getAge());
        final Optional<UserPo> minAge = userPoList.stream().min(Comparator.comparing(UserPo::getAge));
        System.out.println("年龄最小的人：" + minAge.get().getAge());

        /**
         * 上述的foreach()遍历、count()计算总数，都属于终止操作
         */
    }

    public static Stream<Character> getCharacterByString(String str) {
        List<Character> characterList = new ArrayList<>();
        for (Character character : str.toCharArray()) {
            characterList.add(character);
        }
        return characterList.stream();
    }
}

@Getter
@Setter
class UserPo implements Serializable {

    private static final long serialVersionUID = 1L;
    private String name;
    private Integer age;
    private String address;
    private String mailbox;

    public UserPo(String name, Integer age, String address, String mailbox) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.mailbox = mailbox;
    }
}
```
>总结：  
>* 1、list.stream()后，里面操作的元素实际上就是list里面的单个元素；
>* 2、一个流只要执行了终止操作，就无法再次执行中间操作，要执行中间操作必须先创建流式；

源码地址：[我的GitHub](https://github.com/wjmwss/java-program)   
