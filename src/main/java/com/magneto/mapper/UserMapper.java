package com.magneto.mapper;

import com.magneto.pojo.User;

import java.util.List;

public interface UserMapper {

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 返回受影响的行数
     */
    int reg(User user);

    /**
     * 用户登陆
     *
     * @param user 用户登录信息 username 用户名  password 密码
     * @return
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
    User selectByUsername(String username);

    /**
     * 编辑用户
     *
     * @param user 用户信息（username：用户名、password：密码）
     * @return 返回受影响的行数
     */
    int update(User user);

    /**
     * 删除用户
     *
     * @param id 用户id
     * @return 受影响的行数
     */
    int delete(int id);

    /**
     * 用户点击修改按钮后，执行查询，将用户的所有信息转发到新的user.jsp页面
     *
     * @param id
     * @return
     */
    User userRegisterSelect(int id);
}
