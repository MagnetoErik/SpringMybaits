<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>用户登录</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#login").click(function () {
                var username = $("#username").val();
                if (username == null && username == '') {
                    alert('请输入用户名!');
                    return;
                }
                var password = $("#password").val();
                if (password == null && password == '') {
                    alert('请输入密码!');
                    return;
                }
                $("#loginForm").submit();

            })
        });
    </script>
</head>
<body>
<div class="container">
    <h2 class="col-sm-offset-1">用户登录</h2>
    <hr style="border: 1px solid black; width: 100%"/>
    <form action="${pageContext.request.contextPath}/user/login.action" method="post" id="loginForm" class="form-horizontal">
        <div class="form-group">
            <label class="col-sm-2 control-label">用户名:</label>
            <div class="col-sm-3">
                <input type="text" class="form-control" id="username" name="username"/>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-2 control-label">密码:</label>
            <div class="col-sm-3">
                <input type="password" class="form-control" id="password" name="password" value="${user.password}"/>
                <a href="../reg.jsp">还没有账号？点击注册</a>
            </div>
        </div>
        <div class="form-group">
            <input type="button" class="btn col-sm-offset-3" value="登录" id="login"/>
            <input type="reset" class="btn" value="重置"/>
        </div>
    </form>
    <hr style="border: 1px solid black; width: 100%"/>
    <span style="color: red">${msg}</span>
</div>
</body>
</html>
