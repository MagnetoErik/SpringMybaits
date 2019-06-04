<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>

<head>
    <title>用户注册</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <script type="text/javascript">
        $(document).ready(function () {


            $("#add").click(function () {
                var username = $("#username").val();
                if (username == null && username == '') {
                    alert('请输入用户名！');
                    return;
                }
                var password = $("#password").val();
                if (password == null && password == '') {
                    alert('请输入密码!');
                    return;
                }
                $("#addFrom").submit();


            });
        });
    </script>
</head>

<body>
<div class="container">
    <h2 class="col-sm-offset-1">用户添加</h2>
    <hr style="border: 1px solid black; width: 100%"/>
    <br/>
    <form action="${pageContext.request.contextPath}/doAddUser" method="post" id="addFrom" class="form-horizontal">
        <div style="margin: 0px auto">
            <div class="form-group">
                <label class="col-sm-2 control-label">用户名:</label>
                <div class="col-sm-3">
                    <input type="text" class="form-control" id="username" name="username"/>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">密码:</label>
                <div class="col-sm-3">
                    <input type="password" class="form-control" id="password" name="password"/>
                </div>
            </div>
            <div class="form-group">
                <input class="btn col-sm-offset-3" type="button" value="提交" id="add"/>
                <input class="btn" type="reset" value="重置"/>
            </div>
        </div>
    </form>
    <hr style="border: 1px solid black; width: 100%"/>
    <span id="content" style="color: red"></span>
</div>

</body>

</html>
