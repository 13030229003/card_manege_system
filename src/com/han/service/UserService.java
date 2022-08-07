package com.han.service;

import com.han.dao.UserDAO;
import com.han.pojo.User;

import java.util.List;

/**
 * @PACKAGE_NAME: com.han.service
 * @Author XSH
 * @Date 2022-08-06 17:47
 * @Version 1.0
 * 描述：
 **/
public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User login(User user) {

        String sql = "select * from user where account=? and password=? and identity=?";
        User querySingle = userDAO.querySingle(sql, User.class, user.getAccount(), user.getPassword(),user.getIdentity());
        return querySingle;
    }

    public User userSelectByAccount(User user) {
        String sql = "select * from user where account=?";
        User user1 = userDAO.querySingle(sql, User.class, user.getAccount());
        return user1;
    }

    public int userInsert(User user) {
        String sql = "insert into user(name,account,password) values(?,?,?)";
        int update = userDAO.update(sql, user.getName(), user.getAccount(), user.getPassword());
        return update;
    }



}
