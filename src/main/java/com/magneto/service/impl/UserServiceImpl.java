package com.magneto.service.impl;

import com.magneto.mapper.UserMapper;
import com.magneto.pojo.User;
import com.magneto.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int reg(String username,String password){
        int result = userMapper.reg(new User(1, username, password));
        return result;
    }

    @Override
    public int login(User user) {
        return userMapper.login(user);
    }

    @Override
    public List<User> selectAll(){
        return userMapper.selectAll();
    }

    @Override
    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }


    @Override
    // propagation 默认传播行为 isolation 默认隔离级别 readonly 事务只读
    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,readOnly = false)
    public int update(User user) {
        return userMapper.update(user);
    }


    @Override
    public int delete(int id) {
        return userMapper.delete(id);
    }

    @Override
    public User userRegisterSelect(int id) {
        return userMapper.userRegisterSelect(id);
    }

}
