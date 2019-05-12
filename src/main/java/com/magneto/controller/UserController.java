package com.magneto.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.magneto.pojo.User;
import com.magneto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/reg")
    public String reg(String username, String password) {
        int result = userService.reg(username, password);
        if (result > 0) {
            return "index";
        } else {
            return "reg";
        }
    }

    @RequestMapping("/login")
    public ModelAndView login(String username, String password,HttpSession session) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);


        String success = "forward:/user/selectAll.action?pageNum=1";
        ModelAndView successMav = new ModelAndView(success);
        ModelAndView failMav = new ModelAndView("index");

        int result = userService.login(user);
        if (result > 0) {
            session.setAttribute("user",user);
            return successMav;
        }
        else{
            return failMav;
        }


    }

    @RequestMapping("/selectAll")
    public String selectAll(HttpServletRequest request, Model model,HttpSession session){

        Integer pageNum = Integer.parseInt(request.getParameter("pageNum"));
        User user = (User)session.getAttribute("user");

        PageHelper.startPage(pageNum, 6);
        List<User> userList = userService.selectAll();
        PageInfo<User> pageInfo = new PageInfo<>(userList);

        Map<String,Object> map = new HashMap<>();
        map.put("pageInfo",pageInfo);
        map.put("userList",userList);
        map.put("user",user);
        model.addAllAttributes(map);

        return "welcome";
    }

    @RequestMapping(value = "/selectByUsername", method = RequestMethod.POST)
    public void selectByUsername(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;UTF-8");
        String username = request.getParameter("username");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);
        User user = userService.selectByUsername(username);
        PrintWriter out = response.getWriter();
        if (user != null) {
            //如果不为空，返回true表示数据已经存在，需要重新输入用户名
            out.print(1);
        } else {
            out.print(0);
        }

    }

    @RequestMapping("/update")
    public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;UTF-8");

        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = new User(userId, username, password);


        ApplicationContext applicationContext = (ApplicationContext) request.getSession().getAttribute("applicationContext");
        UserService userService = (UserService) request.getSession().getAttribute("userService");

        int result = userService.update(user);
        PrintWriter out = response.getWriter();
        if (result > 0) {
            out.println("<script>alert('修改成功');</script>");
            request.getRequestDispatcher("/user?method=selectAll&pageNum=" + pageNum).forward(request, response);
        } else {
            out.println("<script>alert('修改失败');history.back();");
        }
    }

    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    public ModelAndView delete(HttpServletRequest request){
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        int id = Integer.parseInt(request.getParameter("id"));
        int result = userService.delete(id);
        if (result > 0) {
            String success = "forward:/user/selectAll.action?pageNum="+pageNum;
            ModelAndView successMav = new ModelAndView(success);
            return successMav;
        } else {
            String fail = "Redirect:/user/selectAll.action?pageNum="+pageNum;
            ModelAndView failMav = new ModelAndView(fail);
            return failMav;
        }

    }


    @RequestMapping("/userRegisterSelect")
    public void userRegisterSelect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;UTF-8");
        int id = Integer.parseInt(request.getParameter("id"));
        int pageNum = Integer.parseInt(request.getParameter("pageNum"));
        ApplicationContext applicationContext = (ApplicationContext) request.getSession().getAttribute("applicationContext");
        UserService userService = (UserService) request.getSession().getAttribute("userService");
        User user = userService.userRegisterSelect(id);
        request.setAttribute("user", user);
        request.setAttribute("pageNum", pageNum);
        request.getRequestDispatcher("user.jsp").forward(request, response);
    }

}
