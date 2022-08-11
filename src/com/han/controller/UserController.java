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

    public Object[][] userList() {
        return userService.userList();
    }
    public int userUpdateByAccount(User user) {
        return userService.userUpdateByAccount(user);
    }

    public Object[][] userListByLike(String like) {
        return userService.userListByLike(like);
    }

    public int userDelete(User user) {
        return userService.userDelete(user);
    }
    public int userStorageAmountCharge(User user,String money) {
        return userService.userStorageAmountCharge(user,money);
    }

    public int userTransfer(User fromUser,User toUser,String money) {

        return userService.userTransfer(fromUser,toUser,money);
    }


}
