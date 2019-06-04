package com.magneto.controller;

import com.alibaba.fastjson.JSON;
import com.magneto.pojo.User;
import com.magneto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 返回受影响的行数
     */
    @RequestMapping("/reg")
    public String reg(String username, String password) {
        //调用服务注册信息
        int result = userService.reg(username, password);
        //如果返回结果大于0，表示注册成功
        if (result > 0) {
            //返回登录界面index.jsp
            return "index";
        } else {
            //返回注册页面reg.jsp
            return "reg";
        }
    }

    /**
     * 用户登陆
     *
     * @param username 用户名
     * @param password 密码
     * @param session  session作用于，用于将用户信息保存在session中
     * @return 返回视图（selectAll） 查询全部用户信息
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(HttpSession session, User user) {

        //将用户传入的信息进行查询，并返回受影响的行数
        int result = userService.login(user);
        //如果大于0，则登陆成功
        if (result > 0) {
            //登陆成功，将用户信息存入session
            session.setAttribute("user", user);
            //跳转到查询全部用户信息方法（默认第一页）
            return "success";
        } else {
            //登陆失败，返回登陆页面
            return "error";
        }


    }

    /**
     * 查询全部用户信息
     *
     * @param   用于存放全部用户列表
     * @return 将模型返回/WEB-INF/jsp/userList.jsp页面
     */
    @RequestMapping("/selectAll")
    @ResponseBody
    public String selectAll() {

        //查询全部用户信息
        List<User> userList = userService.selectAll();

        //将所有数据发送值userList.jsp页面
        return JSON.toJSONString(userList);
    }

    /**
     * 注册页面ajax异步请求后台，查询用户名是否重复
     */
    @RequestMapping("/checkUsernameRepeat")
    @ResponseBody
    public String checkUsernameRepeat(String username) {

        //根据用户名查询用户是否重复
        int row = userService.selectByUsername(username);

        //返回json字符串
        return JSON.toJSONString(row);
    }

    /**
     * 用户修改方法
     *
     * @param user 需要修改的用户信息
     * @return 返回到selectAll 用于向前端页面显示数据
     */
    @RequestMapping("/doUpdate")
    public String doUpdate(User user) {

        //修改用户信息，返回受影响的行数
        userService.doUpdate(user);

        //返回到selectAll 用于向前端页面显示数据
        return "redirect:/selectAll";

    }



    /**
     * 删除用户信息
     *
     * @param id 前端页面指定的id
     * @return 返回到selectAll 用于向前端页面显示数据
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String delete(String id) {

        //调用服务，将用户信息进行删除
        userService.delete(Integer.parseInt(id));

        //返回到selectAll 用于向前端页面显示数据
        return "redirect:/selectAll";

    }


    /**
     * 用户点击修改，执行此查询方法，将查询到的数据转发到/WEB-INF/jsp/editUser.jsp
     *
     * @param model 模型   用户存放查询到的user对象
     * @param id    用户指定的需要修改的id
     * @return 将model发送到/WEB-INF/jsp/editUser.jsp
     */
    @RequestMapping("/toUpdate")
    public String toUpdate(Model model, String id) {

        System.out.println(id);
        //根据用户信息id查询用户信息
        User user = userService.toUpdate(Integer.parseInt(id));

        model.addAttribute("user", user);

        //将model中的所有数据返回到user.jsp页面
        return "jsp/editUser";
    }

    /**
     * 前端页面访问添加用户信息jsp
     *
     * @return 返回/WEB-INF/jsp/addUser.jsp页面
     */
    @RequestMapping("/toAddUser")
    public String toAddUser() {
        return "jsp/addUser";
    }


    @RequestMapping("/selectByKey")
    @ResponseBody
    public String select(String key, String value) {
        System.out.println(key);
        List<User> userList = userService.selectByKey(key, value);
        return JSON.toJSONString(userList);

    }


    /**
     * 添加用户信息
     *
     * @param user 用户信息
     * @return 返回到selectAll 用于向前端页面显示数据
     */
    @RequestMapping("/doAddUser")
    public String doAddUser(User user) {
        userService.doAddUser(user);
        return "redirect:/selectAll";
    }



    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

}
