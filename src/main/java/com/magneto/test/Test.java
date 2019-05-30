package com.magneto.test;

import com.magneto.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserService userService = applicationContext.getBean("userService", UserService.class);


        for(int i =0;i<100;i++){
            userService.reg("aa"+i,"aa"+i);
        }

    }
}
