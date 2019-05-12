package com.magneto.test;

import com.github.pagehelper.PageHelper;
import com.magneto.pojo.User;
import com.magneto.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);

        PageHelper.startPage(1,2);

        User user = new User();
        user.setUsername("aa");
        user.setPassword("aa");
        int result = userService.login(user);

        System.out.println(result);
    }
}
