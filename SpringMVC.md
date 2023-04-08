## SpringMVC

### 回顾MVC架构

**MVC**:模型(dao,service) 视图(jsp) 控制器(Servlet)

+ Model(模型)：数据模型，提供要展示得数据，因此包含数据和行为，可以认为是领域模型或JavaBean组件(包括数据和行为)，不够现在一般都分离开来：Value Object(数据Dao)和服务层(行为Service)。也就是模型提供了模型数据查询和模型数据得状态更新等功能，包括数据和业务。
+ View(视图)：复制进行模型得展示，一般就是我们见到得用户界面，客户想看到得东西
+ Controller(控制器)：接收用户请求，委托给模型进行处理（状态改变），处理完毕后把返回的模型数据返回给视图，由视图负责展示，也就是说控制器做了调度员的工作。

最典型的MVC就是JSP+servlet+javabean的模式

<img src="C:\Users\LeeB\AppData\Roaming\Typora\typora-user-images\image-20221208162009068.png" alt="image-20221208162009068" style="zoom: 33%;" />

> Model1时代

在Web早期开发中，通常采用的都是Model1，Model1中主要分为两层，视图层和模型层

<img src="C:\Users\LeeB\AppData\Roaming\Typora\typora-user-images\image-20221208162629175.png" alt="image-20221208162629175" style="zoom:50%;" />

> Model2时代

Model2把一个项目分成三部分，包括**视图、控制、模型**

<img src="C:\Users\LeeB\AppData\Roaming\Typora\typora-user-images\image-20221208163431382.png" alt="image-20221208163431382" style="zoom:50%;" />

1、用户发请求

2、Servlet接收请求数据，并调用对应的业务逻辑方法

3、业务处理完毕，返回更新后的数据给servlet

4、servlet转向到JSP，由JSP渲染页面

5、响应给前端更新后的页面

**职责分析**

Controller：取得表单数据，调用业务逻辑，转向指定的页面

Model：业务逻辑，保存数据的状态

View：显示页面

<img src="C:\Users\LeeB\AppData\Roaming\Typora\typora-user-images\image-20221208163812580.png" alt="image-20221208163812580" style="zoom: 67%;" />

### 回顾Servlet



创建好父项目，导入需要的公共依赖

创建子项目，右键项目名称，添加框架支持

<img src="SpringMVC.assets/image-20230330092048210.png" alt="image-20230330092048210" style="zoom:67%;" />

![image-20230330092120192](SpringMVC.assets/image-20230330092120192.png)





初始一个Servlet项目需要做的一些事情，创建一个继承HttpServlet的类，可以从前端获取参数，可以实现请求/转发，同时编写jsp页面，最后配置注册



MVC框架要做哪些事情：

+ 将url映射到java类或java类的方法
+ 封装用户提交的数据
+ 处理请求 -- 调用相关的业务处理，封装响应数据
+  将响应的数据进行渲染 jsp/html等表示层数据



`常见的服务器端MVC框架`

Structs、 Spring MVC 、ASP.NET MVC 、Zend Framework、 JSF

`常见的前端MVC框架`

Vue、angularjs、react、backbone

由MVC也演化了另外一些模式，如：MVP MVVM等

==MVVM: M+V+VM==

==VM:ViewModel 双向绑定，前后端分离的核心==

`前端人员做后台可能使用 nodejs`

`后端转全栈 TNT？ 后台+前端+数据库+一些运维`

`python 也可做后端`





### 概述

基于Java实现MVC的轻量级Web框架

![image-20230330094514876](SpringMVC.assets/image-20230330094514876.png)

**sPRINGmvc特点：**

+ 轻量级，易学
+ 高效，基于请求响应的MVC框架
+ 和Spring无缝结合，可以将SpringMVC中所有要用到的bean，直接注册到Spring容器中
+ 约定优于配置
+ 功能丰富：RESTful，数据验证，格式化，本地化



### HelloSpringMVC

+ 新建Module 添加web支持
+ 导入SpringMVC依赖
+ 配置web,xml 注册DipatcherServlet

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
<!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联一个springmvc的配置文件 ： 【servlet-name】-servlet.xml -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <!--启动级别 1-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!--/ 匹配所有的请求，不包括.jsp-->
    <!--/* 匹配所有的请求，包括.jsp-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

</web-app>
```



+ 编写SpringMVC配置文件，名称 **springmvc-servlet.xml**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        https://www.springframework.org/schema/beans/spring-beans.xsd">
    <!--添加处理映射器-->
    <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
    <!--添加处理器适配器-->
    <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
    <!--添加视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <!--后缀-->
        <property name="suffix" value=".jsp"/>  <!--将 HelloControler.java中要跳转的视图“hello”处理为 /WEB-INF/jsp/hello.jsp-->

    </bean>
    <!--Hander-->
    <bean id="/hello" class="com.lk.HelloControler"/>


</beans>
```



+ 编写要操作业务的Controller  要么实现Controller接口，要么增加注解，需要返回一个封装了数据和视图的ModelAndView

```java
public class HelloControler implements Controller {
    @Override
    public ModelAndView handleRequest(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws Exception {
        //ModelAndView 模型和视图
        ModelAndView modelAndView = new ModelAndView();

        //封装对象 放在ModelAndView中。 Model
        modelAndView.addObject("msg","HelloSpringMVC");

        //封装要跳转的视图，放在ModelAndView中
        modelAndView.setViewName("hello");  //: /WEB-INF/jsp/hello.jsp

        return modelAndView;
    }
}

```

==modelAndView.addObject("msg","HelloSpringMVC");==

控制器中已经设置了jsp页面"msg"的参数，jsp只需传入一个"msg"变量即可，访问/hello页面时直接可以访问到Controller

封装好的参数"HelloSpringMVC",jsp页面如果重新设置"msg"的参数，那么会优先得到jsp页面的参数

+ 编写jsp

![image-20230330104602178](SpringMVC.assets/image-20230330104602178.png)



+ 访问测试

![image-20230330104629284](SpringMVC.assets/image-20230330104629284.png)





==可能遇到的问题，访问出现404==

1. 查看控制台输出，看一下是不是缺少了jar包
2. 如果jar包存在，显示无法输出，就在IDEA的项目发布中，添加lib依赖

![image-20230330104827828](SpringMVC.assets/image-20230330104827828.png)

### SpringMVC执行原理（没理解待补充）

Spring的web框架围绕DispacherServlet设计。DispacherServlet的作用是将请求分发到不同的处理器。从Spring2.5开始，使用java5或者以上版本的，可以用基于注解的controller声明方式



```
DispatcherServlet --> FrameworkServlet --> HttpServletBean --> HttpServlet --> GenericServlet -->
Servlet
```

DispatcherServlet 底层还是实现了 Servlet



**整个显式流程：**

首先 注册了DispatcherServlet，这是SpringMVC的核心，请求分发，也称为前端控制器，

![image-20230330144234321](SpringMVC.assets/image-20230330144234321.png)

并且关联到 springmvc配置文件，该配置文件中注册实现了 HandlerMapping 、HandlerAdapter和ViewResolver

![image-20230330112944584](SpringMVC.assets/image-20230330112944584.png)



编写实现Controler接口的Java代码，HandlerMapping映射到HelloControler控制器，返回模型和视图。即数据和页面

![image-20230330144149947](SpringMVC.assets/image-20230330144149947.png)



### SpringMVC使用注解开发

+ 注册DispatcherServlet，匹配所有请求
+ 编写Springmvc-servlet.xml，和之前不同，这里配置注解开发

```xml
<!--自动扫描包，让指定包下的注解生效，由IOC容器统一管理-->
    <context:component-scan base-package="com.lk.controller"/>

    <!--让Spring MVC不处理静态资源  .css .js .html .mp3 .mp4-->
    <mvc:default-servlet-handler/>

    <!--
    支持mvc注解驱动
        在spring中一般采用@RequestMapping注解来完成映射关系
        要想使@RequestMapping注解生效
        必须向上下文注册DefaultAnnotationHandlerMapping
        和一个AnnotationMethodHandlerAdapter实例
        这两个实例分别在类级别和方法级别处理
        而annotation-driver配置帮助我们自动完成上述两个实例的注入
    -->
    <mvc:annotation-driven/>

    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <!--前缀-->
        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp"/>
    </bean>
```

![image-20230330152945253](SpringMVC.assets/image-20230330152945253.png)

格式基本固定，不用再专门注册 HandlerMapping， HandlerAdapter实例



+ 编写Controler

```java
@Controller
public class HelloControler {
    //localhost:8080/hello/h1  如果不要上面的@RequestMapping注解，则访问的是localhost:8080/h1
@RequestMapping("/h1")
    public String hello(Model model ){

        //封装数据
        model.addAttribute("msg","Hello,SpringMVCannotation");

        return "hello";  //会被视图解析器处理
    }
}

```

### Controler配置总结

控制器，负责提供访问应用程序的的行为，通常通过接口定义或注解定义两种方法实现。

控制器负责解析用户的请求并将其转换为一个模型

在Spring MVC中 一个控制器可以包含多个方法

在Spring MVC中，对于Controller的配置方式有很多种



#### **方式一：实现Controller接口**

```java
//只要实现了Controller 接口的类，说明这就是一个控制器了
public class ControllerTest1 implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("msg","ControllerTest1");
        modelAndView.setViewName("test");

        return modelAndView;

    }
}
```

![image-20230330172054876](SpringMVC.assets/image-20230330172054876.png)

web.xml配置文件不用改变



+ 实现Controller接口 定义控制器是较老的方法
+ 缺点是 一个控制器中只有一个方法，如果要多个方法则需要定义多个Controller。



#### 方式二：使用Controller注解

@Controller 注解类型用于声明Spring类的实例是一个控制器



```java
@Controller  //代表这个类会被Spring接管
public class ControllerTest1  {
    @RequestMapping("/t2")
   public String test1(Model model){
       model.addAttribute("msg","Anotation");

       return "test";  //: /WEB-INF/jsp/test.jsp
   }
}
```



可以写多个方法

```java
@Controller
public class ControllerTest1  {
    @RequestMapping("/t2")
   public String test1(Model model){
       model.addAttribute("msg","Anotation");

       return "test";
   }
    @RequestMapping("/t3")
    public String test2(Model model){
        model.addAttribute("msg","Anotation");

        return "test";
    }
}
```

视图可以被复用



==@RequestMapping说明==

该注解用于映射url到控制器类或一个特定的处理程序方法。可用关于**类或方法上**

![image-20230330191141624](SpringMVC.assets/image-20230330191141624.png)

用于类上时，表示类中的所有响应请求的方法都是以该地址作为父路径



+ 只在方法上注解

![image-20230330191250208](SpringMVC.assets/image-20230330191250208.png)

访问路径： http://localhost:8080/项目名/t2

+ 同时注解类与方法

![image-20230330191338157](SpringMVC.assets/image-20230330191338157.png)

访问路径：http://localhost:8080/项目名/c3/t2

一般不会在类上写注解，哪怕在方法上注解  **"/c3/t2"** ，也是可以的



### RestFul风格

RestFul就是一个资源定位及资源操作的风格。不是标准也不是协议，只是一种风格。基于这个风格设计的软件可以更简洁，更有层次，更易于实现缓存等机制。

**功能**

+ 资源：互联网所有的事物都可以被抽象为资源
+ 资源操作：使用Post、Delete、Put、Get，使用不同方法对资源进行操作
+ 分别对应添加、删除、修改、查询



**传统方式操作资源：**

通过不同的参数来实现不同的效果！方法单一，post 和 get

 http://127.0.0.1/item/queryItem.action?id=1 查询,GET

 http://127.0.0.1/item/saveItem.action 新增,POST

 http://127.0.0.1/item/updateItem.action 更新,POST

 http://127.0.0.1/item/deleteItem.action?id=1 删除,GET或POST



**使用RESTful操作资源** ：可以通过不同的请求方式来实现不同的效果！如下：请求地址一样，但是功能可以不同！

http://127.0.0.1/item/1 查询,GET

 http://127.0.0.1/item 新增,POST

 http://127.0.0.1/item 更新,PUT

 http://127.0.0.1/item/1 删除,DELETE



==测试==

1 新建一个类

```java
@Controller
public class RestFulController {
}

```

2 在Spring MVC中可以使用 @PathVariable 注解，让方法参数的值对应绑定到一个URI模板变量上

```java
@Controller
public class RestFulController {

   //映射访问路径
   @RequestMapping("/commit/{p1}/{p2}")
   public String index(@PathVariable int p1, @PathVariable int p2, Model model){
       
       int result = p1+p2;
       //Spring MVC会自动实例化一个Model对象用于向视图中传值
       model.addAttribute("msg", "结果："+result);
       //返回视图位置
       return "test";
       
  }
   
}

```

3 测试![image-20230331220918925](SpringMVC.assets/image-20230331220918925.png)







**使用路径变量的好处？**

+ 路径更加简洁
+ 获得参数更方便，框架会自动进行类型转换
+ 通过路径变量的类型可以约束访问参数，如果类型不一样，则访问不到对应的请求方法，如这里访问的是路径/commit/1/a,则路径与方法不匹配，而不会是参数转换失败

![image-20230331221300463](SpringMVC.assets/image-20230331221300463.png)



可以通过修改对应参数类型

```java
//映射访问路径
@RequestMapping("/commit/{p1}/{p2}")
public String index(@PathVariable int p1, @PathVariable String p2, Model model){

   String result = p1+p2;
   //Spring MVC会自动实例化一个Model对象用于向视图中传值
   model.addAttribute("msg", "结果："+result);
   //返回视图位置
   return "test";

}
```

![image-20230331221311711](SpringMVC.assets/image-20230331221311711.png)





**使用method属性指定请求类型**

用于约束请求的类型，可以收窄请求范围，指定请求的类型如 GET,POST,HEAD,OPTIONS,PUT,PATCH,DELETE,TRACE等



```java
//映射访问路径,必须是POST请求
@RequestMapping(value = "/hello",method = {RequestMethod.POST})
public String index2(Model model){
   model.addAttribute("msg", "hello!");
   return "test";
}

```

使用浏览器地址栏进行访问默认是Get请求，报错405



![image-20230331221537172](SpringMVC.assets/image-20230331221537172.png)

将方法POST 改为GET 就正常了



**小结**

所有的地址栏请求默认都会是HTTP GET 类型的

方法级别的注解变体有如下几个：组合注解

```
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping
```

@GetMapping 是一个组合注解，平时使用会比较多

它所扮演的是 @RequestMapping(method =RequestMethod.GET)  的一个快捷方式





### 重定向和转发

**这里不需**要视图解析器，

**转发**



![image-20230403203335044](SpringMVC.assets/image-20230403203335044.png)



```java
@Controller
public class ModelTest1 {
    @RequestMapping("/m1/t1")
    public String test(Model model){
        //转发
        model.addAttribute("msg","ModelTest1");
        return "forward:/WEB-INF/jsp/test.jsp";
    }
}
```

![image-20230403203837080](SpringMVC.assets/image-20230403203837080.png)

![image-20230403203551186](SpringMVC.assets/image-20230403203551186.png)







**重定向**

```java
@Controller
public class ModelTest1 {
    @RequestMapping("/m1/t1")
    public String test(Model model){
        //重定向
        model.addAttribute("msg","ModelTest1");
        return "redirect:/index.jsp";
    }
}
```



配置了视图解析器，就不用转发或是重定向了

```java
@Controller
public class ModelTest1 {
    @RequestMapping("/m1/t1")
    public String test(Model model){

        return "test";
    }
}
```



### 接受请求参数及数据回显



#### 处理提交数据

+ 提交的域名称和处理方法的参数名一致

```java
@Controller
public class ModelTest1 {
    @RequestMapping("/hello")
    public String test(String name){
        System.out.println(name);
        return "test";
    }
}
```

![image-20230403205833589](SpringMVC.assets/image-20230403205833589.png)



![image-20230403205824934](SpringMVC.assets/image-20230403205824934.png)

+ 提交的域名称和处理方法的参数名不一致

```java
//@RequestParam("username") :username是提交的域名的参数名
@Controller
public class ModelTest1 {
    @RequestMapping("/hello")
    public String test(@RequestParam("username") String name){
        System.out.println(name);
        return "test";
    }
}
```



![image-20230403210248906](SpringMVC.assets/image-20230403210248906.png)

![image-20230403210254465](SpringMVC.assets/image-20230403210254465.png)



+ 提交的是一个对象

要求提交的表单域和对象的属性名一致，参数使用对象即可



**实体类**

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int id;
    private String name;
    private int age;
}
```



```java
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/t1")
    public String test1(@RequestParam("username") String name , Model model){
        // 1、接收前端参数
        System.out.println("接收到的前端参数为" + name);
        //2、将返回的结果传递给前端，Model
        model.addAttribute("msg",name);
        //3、 视图跳转
        return "test";

    }

    //前端接收的是一个对象 ： id name age

    /*
    * 1. 接收前端用户传递的参数，判断参数的名字，假设名字直接在方法上，可以直接使用
    * 2. 假设传递的是一个对象User，匹配User对象中的字段名，如果名字一致就OK，否则，匹配不到
    * */
    @GetMapping("/t2")
    public String test2(User user){
        System.out.println(user);
        return "test";
    }
}
```



![image-20230403212553084](SpringMVC.assets/image-20230403212553084.png)



![image-20230403212600846](SpringMVC.assets/image-20230403212600846.png)

如果使用对象的话，前端传递的参数名和对象名必须一致，否则就是Null





#### 数据显示到前端

+ 通过ModelAndView

![image-20230403212840471](SpringMVC.assets/image-20230403212840471.png)





+ 通过Model

![image-20230403213051522](SpringMVC.assets/image-20230403213051522.png)



+ 通过ModelMap

```java
    @GetMapping("/t3")
    public String test3(String name,ModelMap map){

        //封装要显示到视图中的数据
        //相当于req.setAttribute("name",name)
        map.addAttribute("name",name);
        System.out.println(name);
        return "test";
    }
```



| Model        | 精简                                                         |
| ------------ | ------------------------------------------------------------ |
| ModelMap     | 继承了LinkedMap，除了实现自身的一些方法，同样继承LinkedMap的方法和特性 |
| ModelAndView | 可以在储存数据的同时，可进行设置返回的逻辑视图，进行控制展示层的跳转 |



#### 乱码问题



先写一个表单做简单测试

```jsp
<form action="/e/t1" method="post">
  <input type="text" name="name">
  <input type="submit">
</form>
```



```java
@Controller
public class EncodingController {
    @PostMapping("/e/t1")
    public String test1(String name, Model model){

        model.addAttribute("msg",name); //获取表单提交的值

        return "test"; //跳转到test页面显示输入的值
    }
}
```



访问表单 通过表单提交数据显示在test.jsp 中

![image-20230403220021557](SpringMVC.assets/image-20230403220021557.png)

结果乱码（英文和数字不会乱码）

![image-20230403220031218](SpringMVC.assets/image-20230403220031218.png)







通过设置过滤器 还是乱码

```java
public class EncodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding("utf-8");
        servletResponse.setCharacterEncoding("utf-8");

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
```

之后在web.xml中配置注册过滤器



==将Post改为Get方法，通过注册过滤器的方式，不会出现乱码问题==

![image-20230403221202871](SpringMVC.assets/image-20230403221202871.png)

![image-20230403221237908](SpringMVC.assets/image-20230403221237908.png)

访问

![image-20230403221246593](SpringMVC.assets/image-20230403221246593.png)



==通过SpringMVC注册Filter==

```xml
    <!--配置SpringMVC的乱码过滤-->
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
```



![image-20230403222855147](SpringMVC.assets/image-20230403222855147.png)





### JSON



前后端分离： 后端部署后端，提供接口，提供数据	； 前端独立部署，负责渲染后端的数据



**JSON** ：是一种轻量级的数据交互格式

+ 采用完全独立于编程语言的文本格式来存储和表示数据

+ 层次结构简洁清晰，有效提升网络传输效率





JavaScript中，一切都是对象，任何JS支持的类型都可以通过JSON来表示：如字符串、数字、对象、数组等



**JSON键值对**是用来保存JS对象的一种方式，和JS对象的写法大同小异，



```json
{"name": "lk"}
{"age": "24"}
{"sex": "男"}
```



==JSON和JavaScript的区别==

+ JSON是JavaScript对象的字符串表示法，它使用文本表示一个JS对象信息，本质是一个字符串

```json
var obj = {a: 'hello' , b : 'world'}; //这是一个对象，键名也可以使用引号包裹
var json = '{"a": "hello", "b": "world"} //这是一个JSON字符串，本质是一个字符串
```



+ JSON和JavaScript对象互转

  + 要实现从JSON字符串转换为JavaScript对象，使用JSON.parse（）方法

    ```
    var obj = JSON.parse('{"a": "Hello","b" :"world"}');
    //结果是 '{"a": "Hello","b": "world"}'
    ```

    

  + 从JavaScript对象转换为JSON字符，使用JSON.stringify( ) 方法

    ```
    var json = JSON.stringify('{"a": "Hello","b": "world"}');
    //结果是 {"a": "Hello","b" :"world"}
    ```

    

    

  ```javascript
  //编写一个JavaScript对象
          var User= {
              name : "lk",
              age : 3,
              sex : "男"
          };
          //将JS对象转换为JSON
          var json = JSON.stringify(User);
  
          console.log(json)
  
          console.log("============")
          //将JSON对象转换为 JavaScript对象
          var obj =JSON.parse(json);
          console.log(obj);
  ```

    


![image-20230404163615261](SpringMVC.assets/image-20230404163615261.png)

#### JackSon

目前比较好的JSON解析工具

当然还有阿里的fastjson等



+ 第一步 需要导入依赖

```xml
<!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.9.8</version>
</dependency>

```



+ 第二步 配置web.xml

```
DispatcherServlet 和 Filter
```



+ 正常方式传入web页面一个User对象参数

```java
@Controller
public class UserController {
    @RequestMapping("/j1")
    @ResponseBody  //加了这个注解，不会走视图解析器，会直接返回一个字符串
    public String json1(){
        //创建一个对象
        User user = new User("lk",23,"男");
        

        return user.toString();
    }
}
```

![image-20230404165759892](SpringMVC.assets/image-20230404165759892.png)



+ 使用 jackson传参

```java
@Controller
public class UserController {
    @RequestMapping("/j1")
    @ResponseBody  //加了这个注解，不会走视图解析器，会直接返回一个字符串
    public String json1() throws JsonProcessingException {

        //jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        //创建一个对象
        User user = new User("lk",23,"男");
        String str = mapper.writeValueAsString(user);

        
        return str;
    }
}
```

![image-20230404170030318](SpringMVC.assets/image-20230404170030318.png)

+ 解决乱码问题1（一般不会这么做）

![image-20230404170253828](SpringMVC.assets/image-20230404170253828.png)

![image-20230404170258988](SpringMVC.assets/image-20230404170258988.png)

+ 解决乱码问题2（乱码统一解决）

使用jackson 配置 springmvc-servlet.xml 处理乱码的代码

```xml
 <!--处理乱码问题-->
    <mvc:annotation-driven>
        <mvc:message-converters register-defaults="true">
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <constructor-arg value="UTF-8"/>
            </bean>

            <bean class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">
                <property name="objectMapper">
                    <bean class="org.springframework.http.converter.json.Jackson2ObjectMapperFactoryBean">
                        <property name="failOnEmptyBeans" value="false"/>
                    </bean>
                </property>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
```

![image-20230404170953079](SpringMVC.assets/image-20230404170953079.png)





==@RestController和@Controller==

@RestController 默认不走视图解析器，返回字符串

@Controller 和 @ResponseBody 搭配使用 可以和 @RestController 一样效果



+ jackson返回一个数组测试

```java
    @RequestMapping("/j2")
    public String json2() throws JsonProcessingException {

        //jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        List<User> list = new ArrayList<User>();
        //创建一个对象
        User user1 = new User("lk",23,"男");
        User user2 = new User("lk",23,"男");
        User user3 = new User("lk",23,"男");
        User user4 = new User("lk",23,"男");

        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        String str = mapper.writeValueAsString(list);
        return str;
    }
```

![image-20230404172003606](SpringMVC.assets/image-20230404172003606.png)

+ 输出时间

```java
    @RequestMapping("/j3")
    public String json3() throws JsonProcessingException {

        //jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
       // 不使用时间戳方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);

        //自定义日期的格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mapper.setDateFormat(sdf);

        Date date = new Date();

        String str = mapper.writeValueAsString(date);
        return str;
    }
```

![image-20230404172653663](SpringMVC.assets/image-20230404172653663.png)



==将 “ 不使用时间戳 ”写成一个工具类==

![image-20230404173146624](SpringMVC.assets/image-20230404173146624.png)

```java
public class JsonUtils {

    public static  String getJson(Object object,String dateFormat){
        ObjectMapper mapper = new ObjectMapper();

        //不使用时间戳方式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //自定义日期格式
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        mapper.setDateFormat(sdf);

        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```





Controller代码

```java
    @RequestMapping("/j3")
    public String json3() throws JsonProcessingException {

        Date date = new Date();
        return JsonUtils.getJson(date,"yyyy-MM-dd HH:mm:ss");
    }
```



工具类中实现代码复用

![image-20230404173649693](SpringMVC.assets/image-20230404173649693.png)

![image-20230404173658189](SpringMVC.assets/image-20230404173658189.png)

格式也不用再写了



#### fastjson

```xml
<!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.60</version>
</dependency>

```

fastJSON 三个主要的类 ：

+ JSONObject 代表json对象

  + JSONObject实现了Map接口，底层操作应该是由Map实现

  + JSONObject对应json对象，通过各种形式的et()方法可以获取json对象中的数据，也可利用诸如size(),isEmpty()等方法获取”键：值“对的个数和判断是否为空

​    

+ JSONArray代表json对象数组
  + 内部有List接口中的方法来完成操作的
+ JSON代表JSONObject和JSONArray的转化
  + JSON类源码分析与使用
  + 这些方法主要是实现json对象  ，对象数组，javabean对象，json字符串之间的相互转化





### SSM整合：Mybatis层

#### 环境搭建

**创建表**

```mysql
CREATE TABLE `books` (
  `bookID` int NOT NULL AUTO_INCREMENT COMMENT '书id',
  `bookName` varchar(100) NOT NULL COMMENT '书名',
  `bookCounts` int NOT NULL COMMENT '数量',
  `detail` varchar(200) NOT NULL COMMENT '描述',
  KEY `bookID` (`bookID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4

INSERT INTO `books`(`bookID`,`bookName`,`bookCounts`,`detail`)VALUES
(1,'Java',1,'Java带我飞'),
(2,'Mysql',23,'Mysql好好学'),
(3,'Linux',13,'这个也得学');
```



**导入项目依赖**

```xml
    <!--依赖：junit ， 数据库驱动 ， 连接池， servlet , jsp ,mybatis , mybatis-spring,spring-->
    <dependencies>
        <!--Junit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.12</version>
        </dependency>
        <!--数据库驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.47</version>
        </dependency>
        <!-- 数据库连接池 c3p0-->
        <dependency>
            <groupId>com.mchange</groupId>
            <artifactId>c3p0</artifactId>
            <version>0.9.5.2</version>
        </dependency>

        <!--Servlet - JSP -->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>servlet-api</artifactId>
            <version>2.5</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet.jsp</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>

        <!--Mybatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.2</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.2</version>
        </dependency>

        <!--Spring-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.1.9.RELEASE</version>
        </dependency>

    </dependencies>
```



**静态资源导出问题**

```xml
<!--静态资源导出问题-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
```



#### **建立基本项目框架**

com.lk.pojo

com.lk.dao

com.lk.service

**resources**:==applicationContext.xml==

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

</beans>

```

​						==**mybatis-config.xml**==

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

</configuration>

```

![image-20230404200702598](SpringMVC.assets/image-20230404200702598.png)



#### Mybatis层编写

1、数据库配置文件 **database.properties**

```properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/ssmbuild?useSSL=false&useUnicode=true
jdbc.username=root
jdbc.password=root
```

2、编写数据库对应的实体类

```xml
 <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.16.10</version>
            <scope>provided</scope>
        </dependency>
```

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Books {
    private int bookID;
    private String bookName;
    private  int bookCount;
    private String detail;
}
```

3、dao编写接口操作

```java
public interface BookMapper {

    //增加一本书
    int addBook(Books books);

    //删除一本书
    int deleteBookByID(int id);
    //查询一本书
    Books queryBookByID(int id);

    //更新一本书
    int updateBook(Books books);
    //查询全部书
    List<Books> queryAllBook();
}
```

4、编写接口对应的Mapper.xml文件

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.lk.dao.BookMapper">

    <insert id="addBook" parameterType="Books">
        insert into ssmbuild.books (bookName, bookCounts, detail)
        values (#{bookName},#{bookCounts},#{detail});
    </insert>

    <delete id="deleteBookByID" parameterType="int">
        delete from ssmbuild.books where bookID = #{bookId};
    </delete>

    <update id="updateBook" parameterType="Books">
        update ssmbuild.books
        set bookName=#{bookName},bookCounts=#{bookCounts},detail=#{detail}
        where bookID = #{bookId};
    </update>

    <select id="queryBookByID" parameterType="Books">
        select * from ssmbuild.books where bookID = #{bookId}
    </select>

    <select id="queryAllBook" parameterType="Books">
        select * from ssmbuild.books
    </select>

</mapper>
```



记得在mybatis-config.xml中注册该Mapper



4、编写Service层的接口和实现类

```java
public interface BookService {
    //增加一本书
    int addBook(Books books);

    //删除一本书
    int deleteBookByID(int id);
    //查询一本书
    Books queryBookByID(int id);

    //更新一本书
    int updateBook(Books books);
    //查询全部书
    List<Books> queryAllBook();
}
```



```java
public class BookServiceImpl implements BookService{
    //Service调dao层 ，组合dao
    private BookMapper bookMapper;

    public void setBookMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    public int addBook(Books books) {
        return bookMapper.addBook(books);
    }

    public int deleteBookByID(int id) {
        return bookMapper.deleteBookByID(id);
    }

    public Books queryBookByID(int id) {
        return bookMapper.queryBookByID(id);
    }

    public int updateBook(Books books) {
        return bookMapper.updateBook(books);
    }

    public List<Books> queryAllBook() {
        return bookMapper.queryAllBook();
    }
}
```



### SSM整合：Spring层

+ 配置Spring整合MyBatis，

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:contex="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--1 关联数据库配置文件-->
    <contex:property-placeholder location="classpath:database.properties"/>
    <!--2 连接池-->
    <!--dbcp 半自动化操作
     c3p0 自动化操作(自动化的加载配置文件，可以自动设置到对象中)
     druid
      hikari-->
    <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
        <property name="driverClass" value="${jdbc.driver}"/>
        <property name="jdbcUrl" value="${jdbc.url}"/>
        <property name="user" value="${jdbc.username}"/>
        <property name="password" value="${jdbc.password}"/>

        <!-- c3p0连接池的私有属性-->
        <property name="maxPoolSize" value="30"/>
        <property name="minPoolSize" value="10"/>
        <!--关闭连接后不自动commit-->
        <property name="autoCommitOnClose" value="false"/>
        <!--获取连接超时时间-->
        <property name="checkoutTimeout" value="10000"/>
        <!--当前连接失败重试次数-->
        <property name="acquireRetryAttempts" value="2"/>
        

    </bean>

    <!--3 SQLSessionFactory-->

    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--绑定mybatis的配置文件-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
    </bean>

    <!--4 配置Dao接口扫描包，动态的实现了Dao接口可以注入到Spring容器中-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--注入 sqlSessionFactory-->
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <!--要扫描的dao包-->
        <property name="basePackage" value="com.lk.dao"/>
    </bean>
</beans>
```



+ Spring整合service层

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:contex="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <!--扫描service下的包-->
    <contex:component-scan base-package="com.lk.service"/>

    <!--2 将我们的所有业务类，注入到Spring，可以通过配置或者注解实现-->
    <bean id="BookServiceImpl" class="com.lk.service.BookServiceImpl">
        <property name="bookMapper" ref="bookMapper"/>
    </bean>

    <!--3 声明式事务配置-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <!--注入数据源-->
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!--4 aop事务支持-->
</beans>
```



### SSM整合：SpringMVC层

1、增加web支持

![image-20230404223350592](SpringMVC.assets/image-20230404223350592.png)

2、配置web.xml



```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">

    <!--DispatchServlet-->
    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <!--关联一个springmvc的配置文件 ： 【servlet-name】-servlet.xml -->
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:spring-mvc.xml</param-value>
        </init-param>
        <!--启动级别 1-->
        <load-on-startup>1</load-on-startup>
    </servlet>

    <!--/ 匹配所有的请求，不包括.jsp-->
    <!--/* 匹配所有的请求，包括.jsp-->
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>

    <!--乱码过滤-->
    <filter>
        <filter-name>encodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encodingFilter</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>

    <!--session-->
    <session-config>
        <session-timeout>15</session-timeout>
    </session-config>
</web-app>
```



3、 spring-mvc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/mvc
        http://www.springframework.org/schema/mvc/spring-mvc.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

    <!--1 注解驱动-->
    <mvc:annotation-driven/>
    <!--2 静态资源过滤-->
    <mvc:default-servlet-handler/>
    <!--3 扫描包-->
    <context:component-scan base-package="com.lk.controller"/>
    <!--4 视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```



4、Spring 配置整合文件，applicationContex.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd">

    <import resource="Spring-dao.xml"/>
    <import resource="spring-mvc.xml"/>
    <import resource="Spring-service.xml"/>
</beans>
```



剩下的就是编写Controller了

### 书籍查询功能

**Controller编写**

```java
@Controller
@RequestMapping("book")
public class BookController {
    //controller 调 service层
    @Autowired
    @Qualifier("BookServiceImpl")
    private BookService bookService;
//    private BookService bookService = new BookServiceImpl();

    //查询全部的书籍，并且返回到一个书籍展示页面
    @RequestMapping("/allBook")
    public String list(Model model){
        List<Books> books = bookService.queryAllBook();
        model.addAttribute("books",books);
        return "allBook";
    }
}
```



**首页界面优化**

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>首页</title>
    <style>
      a{
        text-decoration: none;
        color: black;
        font-size: 18px;
      }
      h3{
        width: 180px;
        height: 38px;
        margin: 100px auto;
        text-align: center;
        line-height: 38px;
        background: deepskyblue;
        border-radius: 5px;
      }
    </style>
  </head>
  <body>
  <h3>

    <a href="${pageContext.request.contextPath}/book/allBook">进入书籍页面</a>
  </h3>
  </body>
</html>
```

![image-20230405104614840](SpringMVC.assets/image-20230405104614840.png)



**查询所有书籍界面优化**

```jsp
<html>
<head>
    <title>书籍展示</title>

    <%--BootStrap 美化界面--%>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>书籍列表 ———— 显示所有书籍</small>
                </h1>
            </div>
        </div>
    </div>

    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-hover table-striped">
                <thead>
                    <tr>
                        <th>书籍编号</th>
                        <th>书籍名称</th>
                        <th>书籍数量</th>
                        <th>书籍说明</th>
                    </tr>
                </thead>
                <%--书籍从数据库中查询出来，从books遍历出来 foreach--%>
                <tbody>
                    <c:forEach var="book" items="${books}">
                        <tr>
                            <td>${book.bookID}</td>
                            <td>${book.bookName}</td>
                            <td>${book.bookCount}</td>
                            <td>${book.detail}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>

</div>

</body>
</html>
```

![image-20230405104539471](SpringMVC.assets/image-20230405104539471.png)



==过程中遇到的报错问题==

+ 解决“至少有一个JAR被扫描用于TLD但尚未包含TLD”

  + 1、打开Tomcat配置文件，找到conf文件夹下的catalina.properties

  + 2、编辑配置文件，找到在108行，修改如下

    ![image-20230405104820014](SpringMVC.assets/image-20230405104820014.png)

  + 保存退出编辑，重启服务

+ 解决“一个或多个筛选器启动失败”

  + 这个错误由于最开始创建项目时包没有导入引起

    ![image-20230405105030554](SpringMVC.assets/image-20230405105030554.png)

​	

+ bean报错

  ![image-20230405105347155](SpringMVC.assets/image-20230405105347155.png)

  + 排错思路

    + 查看这个bean是否注入成功![image-20230405105453976](SpringMVC.assets/image-20230405105453976.png)

    + Junit单元测试，看代码结果是否能跑出来![image-20230405105528048](SpringMVC.assets/image-20230405105528048.png)

    + 问题：一定不在底层，是Spring出了问题————手动注入![image-20230405105840457](SpringMVC.assets/image-20230405105840457.png)

    + 报错空指针异常![image-20230405105936892](SpringMVC.assets/image-20230405105936892.png)

      ![image-20230405105959555](SpringMVC.assets/image-20230405105959555.png)

    + SpringMVC ，整合的时候没有调用到我们的service层的bean：

      1、applicationContext.xml 没有注入bean

      2、web.xml中，也绑定过配置文件。~ 发现问题，配置的是sprinmvc.xml ，没有绑定其他spring配置，所以空指针

​					![image-20230405110104862](SpringMVC.assets/image-20230405110104862.png)  



### 添加书籍功能



Controller编写

```java
//跳转到增加书籍页面
    @RequestMapping("/toAddBook")
    public String toADDPaper() {
        return "addBook";
    }


    //添加书籍请求
    @RequestMapping("/addBook")
    public String addBokk(Books books) {
        System.out.println("addBook=>" + books);
        bookService.addBook(books);
        return "redirect:/book/allBook"; //重定向到我们的@RequestMapping("/allBook") 请求
    }
```



页面

```jsp
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>新增书籍</small>
                </h1>
            </div>
        </div>

        <form action="${pageContext.request.contextPath}/book/addBook" method="post">
            <div class="for-group">
                <label for="bkname">书籍名称</label>
                <input type="text" name="bookName" class="form-control" id="bkname" required>
            </div>
            <div class="for-group">
                <label for="bkcount">书籍数量</label>
                <input type="text" name="bookCounts" class="form-control" id="bkcount" required>
            </div>
            <div class="for-group">
                <label for="bkretail">书籍描述</label>
                <input type="text" name="detail" class="form-control" id="bkretail" required>
            </div>
            <div class="form-group">
                <input type="submit"  class="form-control" value="添加">
            </div>

        </form>

    </div>



</div>
```

### 修改删除书籍

Controller编写

```java
 //跳转到修改页面
    @RequestMapping("/toUpdate")
    public String toUpdatePaper(int id,Model model){
        Books books = bookService.queryBookByID(id);
        model.addAttribute("QBook",books);
        return "updateBook";

    }

    //修改书籍
    @RequestMapping("/updateBook")
    public String updateBook(Books books){
        System.out.println("updateBook=>"+ books);
        bookService.updateBook(books);
        return "redirect:/book/allBook";
    }

    //删除书籍
    @RequestMapping("/deleteBook/{bookID}")
    public String deleteBook(@PathVariable("bookID") int id){
        bookService.deleteBookByID(id);
        return "redirect:/book/allBook";

    }
```

修改需要配置aop事务

**Spring-service.xml**

```xml
<!--4 aop事务支持-->
<!--结合AOP实现事务的注入-->
<!--配置事务通知-->
<tx:advice id="txAdvice" transaction-manager="transactionManager">
    <!--给哪些方法配置事务-->
    <!--配置事务的传播特性 new-->
    <tx:attributes>
        <tx:method name="*" propagation="REQUIRED"/>
    </tx:attributes>
</tx:advice>

<!--配置事务切入-->
<aop:config>
    <aop:pointcut id="txPointCut" expression="execution(* com.lk.dao.*.*(..))"/>
    <aop:advisor advice-ref="txAdvice" pointcut-ref="txPointCut"/>
</aop:config>
```





修改页面

```jsp
        <h1>
          <small>修改书籍</small>
        </h1>
      </div>
    </div>

    <form action="${pageContext.request.contextPath}/book/updateBook" method="post">
      <%--提交了修改的SQL请求，但是修改失败，初次考虑，是事务问题，配置完毕事务，依旧失败--%>
      <%--前端传递隐藏域--%>
        <input type="hidden" name="bookID" value="${QBook.bookID}">
      <div class="for-group">
        <label for="bkname">书籍名称</label>
        <input type="text" name="bookName" class="form-control" id="bkname" value="${QBook.bookName}" required>
      </div>
      <div class="for-group">
        <label for="bkcount">书籍数量</label>
        <input type="text" name="bookCounts" class="form-control" id="bkcount" value="${QBook.bookCounts}" required>
      </div>
      <div class="for-group">
        <label for="bkretail">书籍描述</label>
        <input type="text" name="detail" class="form-control" id="bkretail" value="${QBook.detail}" required>
      </div>
      <div class="for-group">
        <input type="submit"  class="form-control" value="修改">
      </div>

    </form>

  </div>
</div>
```



### 新增搜索功能

dao - service -controller 从下自上添加业务

![image-20230406165738307](SpringMVC.assets/image-20230406165738307.png)

![image-20230406165825484](SpringMVC.assets/image-20230406165825484.png)



```java
//查询书籍
@RequestMapping("/queryBook")
public String queryBook(String queryBookName,Model model){
    Books books = bookService.queryBookByName(queryBookName);

    System.err.println("books=>"+books);

    List<Books> list = new ArrayList<Books>();
    list.add(books);
    if (books==null){
        list = bookService.queryAllBook();
        model.addAttribute("error","未查到");
    }
    model.addAttribute("list",list);

    return "allBook";
}
```



```jsp
<div class="col-md-4 column">
    <%--查询书籍--%>
    <form action="${pageContext.request.contextPath}/book/queryBook" method="post" style="float: right" class="form-inline">
        <span style="color: crimson;font-weight: bold">${error}</span>
        <input type="text" name="queryBookName" class="form-control" placeholder="请输入要查询的书籍名称">
        <input type="submit" value="查询" class="btn btn-primary">
    </form>
</div>
```





### Ajax

**Asynchronous JavaScript and XML**

异步无刷新请求

+ 传统的网页想要更新内容或者提交一个表单，都需要重新加载整个网页。
+ 使用ajax技术的网页，通过在后台服务器进行少量的数据交换，就可以实现异步局部更新



#### jQuery.ajax

不需要纯原生实现Ajax，直接使用jQuery提供的。

Ajax的核心是XMLHttpRequest对象（XHR）。XHR为向服务器发送请求和解析服务器响应提供了接口，能够以异步方式从服务器获取新数据

通过jQuery Ajax方法，能够使用HTTP Get和HTTP Post从远程服务器上请求文本、HTML、XML或JSON，也可以把外部数据直接载入网页的被选元素中

jQuery 本质就是XMLHttpRequest，对其进行了封装，方便调用



```
jQuery.ajax(...)
      部分参数：
            url：请求地址
            type：请求方式，GET、POST（1.9.0之后用method）
        headers：请求头
            data：要发送的数据
    contentType：即将发送信息至服务器的内容编码类型(默认: "application/x-www-form-urlencoded; charset=UTF-8")
          async：是否异步
        timeout：设置请求超时时间（毫秒）
      beforeSend：发送请求前执行的函数(全局)
        complete：完成之后执行的回调函数(全局)
        success：成功之后执行的回调函数(全局)
          error：失败之后执行的回调函数(全局)
        accepts：通过请求头发送给服务器，告诉服务器当前客户端可接受的数据类型
        dataType：将服务器端返回的数据转换成指定类型
          "xml": 将服务器端返回的内容转换成xml格式
          "text": 将服务器端返回的内容转换成普通文本格式
          "html": 将服务器端返回的内容转换成普通文本格式，在插入DOM中时，如果包含JavaScript标签，则会尝试去执行。
        "script": 尝试将返回值当作JavaScript去执行，然后再将服务器端返回的内容转换成普通文本格式
          "json": 将服务器端返回的内容转换成相应的JavaScript对象
        "jsonp": JSONP 格式使用 JSONP 形式调用函数时，如 "myurl?callback=?" jQuery 将自动替换 ? 为正确的函数名，以执行回调函数

```





==简单测试==

+ 配置web.xml 和 Springmvc的配置文件（applicationContext.xml）

  记得配置静态资源过滤和注解驱动

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
      <servlet>
          <servlet-name>springmvc</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:applicationContext.xml</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping>
          <servlet-name>springmvc</servlet-name>
          <url-pattern>/</url-pattern>
      </servlet-mapping>
  
      <filter>
          <filter-name>encoding</filter-name>
          <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
          <init-param>
              <param-name>encoding</param-name>
              <param-value>utf-8</param-value>
          </init-param>
      </filter>
      <filter-mapping>
          <filter-name>encoding</filter-name>
          <url-pattern>/*</url-pattern>
      </filter-mapping>
  </web-app>
  ```

  

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       https://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd
       http://www.springframework.org/schema/mvc
       http://www.springframework.org/schema/mvc/spring-mvc.xsd">


    <context:component-scan base-package="com.lk.controller"/>
    <!--静态资源过滤-->
    <mvc:default-servlet-handler/>
    <mvc:annotation-driven/>


    <!--视图解析器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">

        <property name="prefix" value="/WEB-INF/jsp/" />
        <property name="suffix" value=".jsp"/>
    </bean>
</beans>
```



+ 编写AjaxController

```java
@Controller
public class AjaxController {
    @RequestMapping("/t1")
    public String test(){
        return "hello";
    }

    @RequestMapping("/a1")
    public void a1(String name, HttpServletResponse response) throws IOException {
        System.out.println("a1:param = >"+name);
        if ("lk".equals(name)){
            response.getWriter().print("true");
        }else {
            response.getWriter().print("false");
        }
    }
}
```



+ 导入Jquery  可以使用在线的CDN或者下载导入

  ![image-20230406195925570](SpringMVC.assets/image-20230406195925570.png)

+ index.jsp测试

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="${pageContext.request.contextPath}/statics/js/jquery-3.4.1.js"></script>
    <%--js是一个很随便的语言--%>
    <script>
      function a(){
        $.post({
            url:"${pageContext.request.contextPath}/a1",
            data:{"name":$("#username").val()},
            success:function (data,status){
              console.log("data=" + data);
              console.log("status=" + status)
            }
                })

      }

    </script>
  </head>
  <body>

  <%--失去焦点的时候，发起一个请求到后台--%>
  用户名:<input type="text" id="username" onblur="a()">
  </body>
</html>
```

![image-20230406193636146](SpringMVC.assets/image-20230406193636146.png)



#### SpringMVC实现

实体类User

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private int age;
    private String sex;
}
```

Controller获取一个i集合对象 

```java
@RequestMapping("/a2")
public List<User> a2(){
    List<User> users = new ArrayList<>();

    //添加数据
    users.add(new User("lk",21,"男"));
    users.add(new User("yzy",20,"女"));
    users.add(new User("hxf",23,"男"));

    return users;
}
```



前端页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<input type="button" id="btn" value="获取数据"/>
<table width="80%" align="center">
  <tr>
    <td>姓名</td>
    <td>年龄</td>
    <td>性别</td>
  </tr>
  <tbody id="content">
  </tbody>
</table>

<script src="${pageContext.request.contextPath}/statics/js/jquery-3.4.1.js"></script>
<script>

  $(function () {
    $("#btn").click(function () {
      $.post("${pageContext.request.contextPath}/a2",function (data) {
        console.log(data)
        var html="";
        for (var i = 0; i <data.length ; i++) {
          html+= "<tr>" +
                  "<td>" + data[i].name + "</td>" +
                  "<td>" + data[i].age + "</td>" +
                  "<td>" + data[i].sex + "</td>" +
                  "</tr>"
        }
        $("#content").html(html);
      });
    })
  })
</script>


</body>
</html>
```



![image-20230406201903252](SpringMVC.assets/image-20230406201903252.png)





#### Ajax验证用户名

```java
@RequestMapping("/a3")
public String a3(String name , String pwd){
    String msg = "";
    if (name != null){
        // admin 这些数据应该再数据库中查
        if ("admin".equals(name)){
            msg = "ok";
        }else {
            msg = "用户名有误";
        }
    }
    if (pwd != null){
        // admin 这些数据应该再数据库中查
        if ("123456".equals(pwd)){
            msg = "ok";
        }else {
            msg = "密码有误";
        }
    }
    return msg;
}
```



```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
  <script src="${pageContext.request.contextPath}/statics/js/jquery-3.4.1.js"></script>
  <script>
    function a1(){
      $.post({
        url:"${pageContext.request.contextPath}/a3",
        data:{"name":$("#name").val()},
        success:function (data){
          if (data.toString()==='ok'){
            $("#userInfo").css("color","green");
          }else {
            $("#userInfo").css("color","red");
          }
          $("#userInfo").html(data);
          console.log(data);
        }
      })
    }
    function a2(){
      $.post({
        url:"${pageContext.request.contextPath}/a3",
        data:{"pwd":$("#pwd").val()},
        success:function (data){
          if (data.toString()==='ok'){
            $("#pwdInfo").css("color","green");
          }else {
            $("#pwdInfo").css("color","red");
          }
          $("#pwdInfo").html(data);


          console.log(data);
        }
      })
    }
  </script>

</head>
<body>

<p>
  用户名: <input type="text" id="name" onblur="a1()">
  <span id="userInfo"></span>
</p>
<p>
  密码: <input type="text" id="pwd" onblur="a2()">
  <span id="pwdInfo"></span>
</p>

</body>
</html>
```



![image-20230406205009379](SpringMVC.assets/image-20230406205009379.png)





### SpringMVC拦截器

**过滤器与拦截器的区别 ：** 

SpringMVC拦截器类似于Servlet开发中的过滤器Filter，用于对处理器进行预处理和后处理。拦截器是AOP思想的具体应用



**过滤器**

+ 在Servlet中，任何java web工程都可以使用
+ 在url-pattern中配置了/*后，可以对所有要访问的资源进行拦截

![image-20230406205703123](SpringMVC.assets/image-20230406205703123.png)

**拦截器**

+ 拦截器是SpringMVC框架自己的，只有使用了SpringMVC框架的工程才能使用
+ 拦截器只会拦截访问的控制器方法，如果访问的是jsp/html/css/image/js是不会进行拦截的



#### Test

拦截器只需实现HandlerInterceptor，该接口方法不必须重写，一般只重写 preHandle( )，后面两个方法postHandle(),afterCompletion() 可以当作拦截日志

![image-20230406211944630](SpringMVC.assets/image-20230406211944630.png)

![image-20230406211951108](SpringMVC.assets/image-20230406211951108.png)

Controller类 执行前只有preHandle可以拦截生效





编写拦截类

```java
public class MyIntercepter implements HandlerInterceptor {

    //return true ;  执行下一个拦截器，放行
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("======================处理前==================");
        return true;
    }

    //后面两个方法可以写拦截日志
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("======================处理后==================");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("======================清理==================");
    }
}
```

 若将 preHandle( )方法 return false，即执行该拦截器，（注意这个拦截器是在Controller执行前运行生效的），不放行，那么就能实现成功拦截的效果。

![image-20230406212246959](SpringMVC.assets/image-20230406212246959.png)





==注意在SpringMVC配置文件下配置拦截器==

```xml
    <mvc:interceptors>
        <mvc:interceptor>
            <!-- /** :包括这个请求下面的所有请求-->
            <mvc:mapping path="/**"/>
            <bean class="com.lk.config.MyIntercepter"/>
        </mvc:interceptor>
    </mvc:interceptors>
```



#### 登录判断验证

+ 编写Controller

```java
@Controller
public class LoginController {
    @RequestMapping("/main")
    public String main(){
        return "main";
    }
    @RequestMapping("/goLogin")
    public String login(){
        return "login";
    }
    @RequestMapping("/login")
    public String login(HttpSession session,String username, String password){
//把用户的信息存在session中
        session.setAttribute("userLoginInfo",username);
        return "main";
    }
}
```

+ 首页 页面

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>首页</h1>
</body>
</html>
```

+ 登录 页面

```xml
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%-- 在web-inf下的所有页面或者资源，只能通过controller , 或者servlet进行访问--%>
<h1>登陆页面</h1>

<form action="${pageContext.request.contextPath}/login" method="post">
  用户名: <input type="text" name="username"/>
  密码： <input type="text" name="password"/>
  <input type="submit" value="提交">
</form>
</body>
</html>
```





![image-20230406220549512](SpringMVC.assets/image-20230406220549512.png)

两个页面都能跳转成功，业务上问题就是：我登陆后才能进入首页，这里不登陆也能访问首页，是有问题的，想要的效果是 ：没登陆时点击首页 是无法进入的





写一个Login拦截器的类

```java
public class LoginIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        //放行的判断：判断什么情况下没有登录

        //登录页面也会放行
        if (request.getRequestURI().contains("login")){
            return true;
        }
        //说明我在提交登录
        if (request.getRequestURI().contains("goLogin")){
            return true;
        }
        //第一次登录，也是没有Session的
        if (session.getAttribute("userLoginInfo")!=null){
            return true;
        }


        //判断什么情况下没登录

        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request,response);

        return false;
    }
}
```



首页 页面增加注销功能

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>首页</h1>
<span>${username}</span>

<p>
    <a href="${pageContext.request.contextPath}/user/goOut">注销</a>
</p>
</body>
</html>
```

