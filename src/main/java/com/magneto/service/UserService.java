package com.magneto.service;

import com.magneto.pojo.User;

import java.util.List;


public interface UserService {

    /**
     * 注册用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 受影响的行数
     */
    int reg(String username, String password);

    /**
     * 用户登陆
     *
     * @param user 用户登录信息 username 用户名  password 密码
     * @return 查询结果 数据库中有该用户显示1 没有该用户显示0
     */
    int login(User user);

    /**
     * 查询所有用户信息
     *
     * @return 用户信息集合
     */
    List<User> selectAll();

    /**
     * ajax异步交互检测用户名是否重复
     *
     * @param username 用户名
     * @return 如果用户名不存在返回null 否侧返回查询到的用户
     */
    int selectByUsername(String username);



    /**
     * 用户点击修改按钮后，执行查询，将用户的所有信息转发到新的user.jsp页面
     *
     * @param id 用户信息id
     * @return 用户信息
     */
    User toUpdate(int id);


    /**
     * 编辑用户
     *
     * @param user 用户信息（username：用户名、password：密码）
     * @return 返回受影响的行数
     */
    int doUpdate(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 受影响的行数
     */
    int delete(int id);

    /**
     * 添加用户信息
     * @param user
     * @return
     */
    int doAddUser(User user);


    /**
     * 根据指定的条件查询信息
     * @param key  所指定的条件（id  username  password）
     * @param value  关键字
     * @return 返回查询到的李列表
     */
    List<User> selectByKey(String key,String value);
}
