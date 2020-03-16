package com.suki.remindyourself.service;

import com.suki.remindyourself.po.User;

/**
 * UserService 接口
 */
public interface UserService {

    User checkUser(String username, String password);

    boolean checkUsername(String username);

    boolean checkEmail(String email);

    int saveUser(String username, String password, String email);
}
