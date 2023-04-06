<%--
  Created by IntelliJ IDEA.
  User: LeeB
  Date: 2023/4/6
  Time: 15:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>修改书籍</title>
  <%--BootStrap 美化界面--%>
  <link href="https://cdn.staticfile.org/twitter-bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<body>
<div class="container">
  <div class="row clearfix">
    <div class="col-md-12 column">
      <div class="page-header">
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
</body>
</html>
