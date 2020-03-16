package com.suki.remindyourself.service;

import com.suki.remindyourself.dao.UserDao;
import com.suki.remindyourself.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * UserService 具体的实现类
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        User user = userDao.getUserByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public boolean checkUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    @Override
    public boolean checkEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public int saveUser(String username, String password, String email) {
        int res = userDao.saveUser(username, password, email);
        return res;
    }
}
