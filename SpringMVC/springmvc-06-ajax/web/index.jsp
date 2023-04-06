<%--
  Created by IntelliJ IDEA.
  User: LeeB
  Date: 2023/4/6
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
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
