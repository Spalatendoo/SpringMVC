<%--
  Created by IntelliJ IDEA.
  User: LeeB
  Date: 2023/4/5
  Time: 11:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <%--BootStrap 美化界面--%>
    <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
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
</body>
</html>
