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

