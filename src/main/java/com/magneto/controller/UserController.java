package com.magneto.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ModelAndView login(String username, String password, HttpSession session, HttpServletRequest request) {

        //创建一个user对象，并赋值
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        //将用户传入的信息进行查询，并返回受影响的行数
        int result = userService.login(user);
        //如果大于0，则登陆成功
        if (result > 0) {
            //登陆成功，将用户信息存入session
            session.setAttribute("user", user);
            //跳转到查询全部用户信息方法（默认第一页）
            String success = "forward:/user/selectAll.action?pageNum=1";
            return new ModelAndView(success);
        } else {
            //登陆失败，返回登陆页面
            request.setAttribute("msg", "用户名或密码错误");
            return new ModelAndView("index");
        }


    }

    /**
     * 显示用户信息列表
     *
     * @param request 请求作用域：主要用于接收分页数据
     * @param model   模型
     * @param session 保存会话信息
     * @return 返回到查询全部用户方法，将全部用户信息显示到页面
     */
    @RequestMapping("/selectAll")
    public String selectAll(HttpServletRequest request, Model model, HttpSession session) {

        //获取分页
        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
        //从session中获取user
        User user = (User) session.getAttribute("user");


        //分页插件
        PageHelper.startPage(pageNum, 14);
        //查询全部用户信息 每页显示6条记录
        List<User> userList = userService.selectAll();
        //获取List的全部分页信息
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        //用map保存数据
        Map<String, Object> map = new HashMap<>();
        map.put("pageInfo", pageInfo);
        map.put("userList", userList);
        map.put("user", user);
        model.addAllAttributes(map);

        //将所有数据发送值welcome.jsp页面
        return "welcome";
    }

    /**
     * 注册页面ajax异步请求后台，查询用户名是否重复
     */
    @RequestMapping("/checkUsernameRepeat")
    @ResponseBody
    public String checkUsernameRepeat(String username) {

        //根据用户名查询用户是否重复
        int row = userService.selectByUsername(username);

        //将返回的数据转换成json字符串
        String str = JSON.toJSONString(row);

        //返回json字符串
        return str;
    }

    @RequestMapping("/update")
    public ModelAndView update(HttpServletRequest request) {

        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");


        User user = new User(userId, username, password);
        int result = userService.update(user);

        if (result > 0) {
            String success = "forward:/user/selectAll.action?pageNum=" + pageNum;
            return new ModelAndView(success);
        } else {
            String fail = "Redirect:/user/selectAll.action?pageNum=" + pageNum;
            return new ModelAndView(fail);
        }

    }

    /**
     * 用户信息删除方法
     *
     * @param request 请求作用域
     * @return 返回到查询全部用户方法，将删除后的数据显示到页面
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ModelAndView delete(HttpServletRequest request) {
        //获取当前页码
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        //获取要删除的用户信息id
        int id = Integer.parseInt(request.getParameter("id"));
        //调用服务，将用户信息进行删除
        int result = userService.delete(id);
        //返回受影响的行数，如果大于0则删除成功
        if (result > 0) {
            //跳转到查询全部用户信息，并且显示删除后的页面（不会删除之后页码混乱）
            String success = "forward:/user/selectAll.action?pageNum=" + pageNum;
            return new ModelAndView(success);
        } else {
            //跳转到查询全部用户信息，并且显示删除前页面（不会删除之后页码混乱）
            String fail = "Redirect:/user/selectAll.action?pageNum=" + pageNum;
            return new ModelAndView(fail);
        }

    }


    /**
     * 修改前先将该用户的全部信息返回到user.jsp
     *
     * @param model   模型
     * @param request 请求作用域
     * @return 返回到指定的页面
     */
    @RequestMapping("/userRegisterSelect")
    public String userRegisterSelect(Model model, HttpServletRequest request) {
        //获取用户信息id
        int id = Integer.parseInt(request.getParameter("id"));
        //获取当前页码
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        //根据用户信息id查询用户信息
        User user = userService.userRegisterSelect(id);

        //创建一个map集合，用户存放需要转发的数据
        Map<String, Object> map = new HashMap<>();
        map.put("user", user);
        map.put("pageNum", pageNum);
        //将map存入model中
        model.addAllAttributes(map);

        //将model中的所有数据返回到user.jsp页面
        return "user";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "index";
    }

}
