<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
    String date = format.format(new Date());
%>
<html>

<head>
    <title>Title</title>
    <meta charset="utf-8"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-3.3.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>
    <script type="text/javascript">
        function userDelete(id, pageNum) {
            if (confirm("您确定要删除该用户吗？")) {
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
            if (confirm("您确定要退出吗？")) {
                window.location.href = "${pageContext.request.contextPath}/user/logout.action";
            }
        }
    </script>
    <style>
        tr>td{
            align : center;
        }
    </style>

</head>

<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav col-md-4">
                <li class="navbar-text">今天是<%=date%>
                </li>

            </ul>


            <div class="nav navbar-nav navbar-brand col-md-4" style="text-align: center;">教务管理系统 v1.0</div>


            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="#">课表查询</a>
                </li>
                <li>
                    <a href="#">消息中心</a>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true"
                       aria-expanded="false">欢迎${user.username} <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li>
                            <a href="#">用户信息</a>
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
        <a href="#">课程编排</a>
    </li>
    <li>
        <a href="#">学生管理</a>
    </li>
</ul>


<div class="col-md-10" id="main">
    <div class="form-inline" style="float: right">
        <div class="form-group">
            <label>查找条件：</label>
            <select class="form-control" v-model="key">
                <option value="id">id</option>
                <option value="username">username</option>
                <option value="password">password</option>
            </select>
        </div>
        <div class="form-group">
            <label>关键字</label>：
            <input class="form-control" v-model="value">
        </div>

        <input class="btn btn-default" id="select" name="select" value="查询" v-on:click="submitForm()"/>
        <input class="btn btn-default" value="添加" onclick="add()"/>
    </div>
    <table align="center" class="table" id="content">
        <tr>
            <td>编号</td>
            <td>用户名</td>
            <td>密码</td>
            <td>操作</td>
        </tr>
        <tbody>
        <tr v-for="(user,index) in userList">
            <td>
                {{user.id}}<%--我用了热部署--%>
            </td>
            <td>
                {{user.username}}
            </td>
            <td>
                {{user.username}}
            </td>
            <td align="center">
                <a href="javascript:void(0);" onclick="editUser(${users.id})">编辑</a>
                <a href="javascript:void(0);" onclick="deleteUser(${users.id})">删除</a>
            </td>
        </tr>
        </tbody>
    </table>
    <div align="center">
        <input type="button" class="btn" onclick="topPage()" value="首页"/>
        <input type="button" class="btn" onclick="lastPage()" value="上一页"/> ${pageInfo.pageNum}/${pageInfo.pages}
        <input type="button" class="btn" onclick="nextPage()" value="下一页"/>
        <input type="button" class="btn" onclick="finalPage()" value="尾页"/>

    </div>
</div>
<footer>
    <span>版权所有：Magneto</span>
</footer>
</body>

</html>
<script>
    var vm = new Vue({
        el: '#container',
        data: {
            id:'',
            key: '',
            value: '',
            userList: [],
        },
        methods: {
            submitForm: function (event) {
                $.ajax({
                    type: "post",
                    url: "/user/selectByKey.action",
                    data: {
                        key: this.key,
                        value: this.value
                    },
                    success: function (res) {
                        vm.userList = JSON.parse(res);
                    }
                });
            },
            editUser:function (event) {
                location.href = "${pageContext.request.contextPath}/user/toUpdate.action?id=" + this.id;
            }
        },

        created() {
            $.ajax({
                type: "post",
                url: "/user/selectAll.action",
                success: function (res) {
                    vm.userList = JSON.parse(res);
                }
            });
        }
    });
</script>