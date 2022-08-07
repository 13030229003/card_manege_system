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

    /**
     * 返回表格信息。
     * @return
     */
    public Object[][] userList() {

        String sql = "select * from user";
        List<User> userList = userDAO.queryMulti(sql, User.class);

        Object[][] results = new Object[userList.size()][8];

        for (int i = 0; i < userList.size(); i++) {

            results[i][0] = userList.get(i).getName();
            results[i][1] = userList.get(i).getAccount();
            results[i][2] = userList.get(i).getTotalCredit();
            results[i][3] = userList.get(i).getAvailableCredit();
            results[i][4] = userList.get(i).getDesirableCredit();
            results[i][5] = userList.get(i).getArrearsAmount();
            results[i][6] = userList.get(i).getStorageAmount();
            results[i][7] = userList.get(i).getStatus();
        }

        return results;
    }

    /**
     * 管理员更新用户可用额度和状态。
     * @param user
     * @return
     */
    public int userUpdateByAccount(User user) {
        /**
         * 1、可用额度   availableCredit
         * 2、可取额度  desirableCredit = 可用额度availableCredit   /1.1
         * 3、总信用额 totalCredit = 可用信用availableCredit + 预存信用 storageAmount
         */
        String desirableCredit = String.valueOf((int) (Double.valueOf(user.getAvailableCredit()) / 1.1));

        Double totalCredit = Double.valueOf(user.getAvailableCredit()) + Double.valueOf(user.getStorageAmount());

        user.setDesirableCredit(desirableCredit);
        user.setTotalCredit(String.valueOf(totalCredit));

        String sql = "update user set availableCredit=?,desirableCredit=?,totalCredit=?,status=? where account=?";


        int update = userDAO.update(sql, user.getAvailableCredit(), user.getDesirableCredit(),user.getTotalCredit(), user.getStatus(), user.getAccount());

        return update;
    }



}
