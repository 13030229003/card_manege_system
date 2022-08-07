package com.han.controller;

import com.han.pojo.User;
import com.han.service.UserService;

/**
 * @PACKAGE_NAME: com.han.controller
 * @Author XSH
 * @Date 2022-08-06 17:54
 * @Version 1.0
 * 描述：
 **/
public class UserController {

    private UserService userService = new UserService();

    public User login(User user) {
        return userService.login(user);
    }

    public User userSelectByAccount(User user) {
        return userService.userSelectByAccount(user);
    }

    public int userInsert(User user) {
        return userService.userInsert(user);
    }


}
