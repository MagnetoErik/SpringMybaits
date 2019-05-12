<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<c:if test="${user==null}">
    <%
        session.invalidate();
        response.sendRedirect("../index.jsp");
    %>
</c:if>
<html>

<head>
    <title>Title</title>
    <meta charset="utf-8" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <script type="text/javascript">
        function userDelete(id, pageNum) {
            if(confirm("您确定要删除该用户吗？")) {
                location.href = "${pageContext.request.contextPath}/user/delete.action?id=" + id + "&pageNum=" + pageNum;
            }
        }

        function userRegister(id, pageNum) {
            location.href = "${pageContext.request.contextPath}/user/userRegisterSelect.action?id=" + id + "&pageNum=" + pageNum;
        }

        function topPage() {
            location.href = "${pageContext.request.contextPath}/user/selectAll.action?pageNum=1";

        }

        function lastPage() {
            location.href = "${pageContext.request.contextPath}/user/selectAll.action?pageNum=" + ${pageInfo.pageNum - 1};

        }

        function nextPage() {
            location.href = "${pageContext.request.contextPath}/user/selectAll.action?pageNum=" + ${pageInfo.pageNum + 1};
        }

        function finalPage() {
            location.href = "${pageContext.request.contextPath}/user/selectAll.action?pageNum=" + ${pageInfo.pages + 1};
        }

        function exit() {
            if(confirm("您确定要退出吗？")) {
                window.location.href = "${pageContext.request.contextPath}/user/logout.action";
            }
        }
    </script>

</head>

<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">网上书店 v1.0</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">购物车</a>
                </li>
                <li>
                    <a href="#">消息中心</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">${user.username} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#">Action</a>
                        </li>
                        <li>
                            <a href="#">Another action</a>
                        </li>
                        <li>
                            <a href="#">Something else here</a>
                        </li>
                        <li role="separator" class="divider"></li>
                        <li>
                            <a href="javascript:void(0)" onclick="exit()">退出系统</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

<ul class="nav nav-stacked col-md-2">
    <li>
        <a href="#">用户列表</a>
    </li>
    <li>
        <a href="#">SVN</a>
    </li>
    <li>
        <a href="#">iOS</a>
    </li>
    <li>
        <a href="#">VB.Net</a>
    </li>
    <li>
        <a href="#">Java</a>
    </li>
    <li>
        <a href="#">PHP</a>
    </li>
</ul>

<div class="col-md-10">
    <table align="center" class="table">
        <tr>
            <td align="center">编号</td>
            <td align="center">用户名</td>
            <td align="center">密码</td>
            <td align="center">操作</td>
        </tr>
        <c:forEach items="${userList}" var="users">
            <tr>
                <td align="center">
                        ${users.id}
                </td>
                <td align="center">
                        ${users.username}
                </td>
                <td align="center">
                        ${users.password}
                </td>
                <td align="center">
                    <a href="javascript:void(0);" onclick="userRegister(${users.id},${pageInfo.pageNum})">编辑</a>
                    <a href="javascript:void(0);" onclick="userDelete(${users.id},${pageInfo.pageNum})">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <div align="center">
        <input type="button" class="btn" onclick="topPage()" value="首页" />
        <input type="button" class="btn" onclick="lastPage()" value="上一页" /> ${pageInfo.pageNum}/${pageInfo.pages}
        <input type="button" class="btn" onclick="nextPage()" value="下一页" />
        <input type="button" class="btn" onclick="finalPage()" value="尾页" />

    </div>
</div>
</body>

</html>