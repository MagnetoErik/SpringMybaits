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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/Vue.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/axios.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css"/>

    <script type="text/javascript">
        function deleteUser(id) {
            if (confirm("您确定要删除该用户吗？")) {
                location.href = "${pageContext.request.contextPath}/user/delete.action?id=" + id;
            }
        }

        function editUser(id) {
            location.href = "${pageContext.request.contextPath}/user/toUpdate.action?id=" + id;
        }

        function add() {
            location.href = "${pageContext.request.contextPath}/user/toAddUser.action"
        }


    </script>
    <style>
        td {
            text-align: center;
        }
    </style>

</head>

<body>
<div id="container">
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="collapse navbar-collapse">
                <ul class="nav navbar-nav col-md-4">
                    <li class="navbar-text">今天是<%=date%>
                    </li>

                </ul>


                <div class="nav navbar-nav navbar-brand col-md-4" style="text-align: center;">教务管理系统 v2.0</div>


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
                                <%--待处理--%>
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
        <h2 align="center">用户信息</h2>
        <hr style="border: 1px solid black; width: 100%"/>


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
                    {{user.id}}
                </td>
                <td>
                    {{user.username}}
                </td>
                <td>
                    {{user.username}}
                </td>
                <td>
                    <a href="javascript:void(0);" v-on:click="editUser()">编辑</a>
                    <a href="javascript:void(0);" v-on:click="deleteUser()">删除</a>
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
    <footer align="center">
        <i>版权所有：Magneto</i>
    </footer>
</div>
</body>
</html>
<script>
    var vm = new Vue({
        el: '#container',
        data: {
            id: '',
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
            editUser: function (event) {
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