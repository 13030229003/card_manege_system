package com.han.service;

import com.han.dao.UserDAO;
import com.han.pojo.User;
import com.han.utils.DateUtil;

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


    /**
     * 用户转账
     * @param fromUser  发起转账的对象
     * @param toUser  转账对象
     * @param money  转账金额
     * @return
     */
    public int userTransfer(User fromUser,User toUser,String money) {
        toUser = userGetByAccount(toUser.getAccount());

        /**fromUser
         * 1、减少自己的可用额度：  可用额度 - 转账金额
         * 2、可取信用额度：  （可用额度 - 转账金额） / 1.1 向上取整
         * 3、总信用额度： 总信用额 - 转账金额`
         */
        String fromUserSql = "update user set availableCredit=availableCredit-?,desirableCredit=?,totalCredit=totalCredit-? where account=?";
        double ceil = Math.floor((Double.valueOf(fromUser.getAvailableCredit()) - Double.valueOf(money)) / 1.1);
        int updateFromUser = userDAO.update(fromUserSql, money, String.valueOf(ceil), money, fromUser.getAccount());

        /**toUser
         * 1、增加自己的可用额度：  可用额度 + 转账金额
         * 2、可取信用额度：  （可用额度 + 转账金额） / 1.1 向上取整
         * 3、总信用额度： 总信用额 + 转账金额
         */

        String toUserSql = "update user set availableCredit=availableCredit+?,desirableCredit=?,totalCredit=totalCredit+? where account=?";
        ceil = Math.floor((Double.valueOf(toUser.getAvailableCredit()) + Double.valueOf(money)) / 1.1);
        int updateToUser = userDAO.update(toUserSql, money, String.valueOf(ceil), money, toUser.getAccount());
        if (updateFromUser > 0 && updateToUser > 0) {
            return 1;
        }
        else {
            return 0;
        }

    }


    public int userStorageAmountCharge(User user,String money) {

        String sql = "update user set storageAmount=storageAmount+?,totalCredit=totalCredit+? where account=?";
        int update = userDAO.update(sql, money,money, user.getAccount());
        return update;
    }

    public User userGetByAccount(String account) {

        String sql = "select * from user where account=?";
        User user = userDAO.querySingle(sql, User.class, account);
        return user;

    }

    public User login(User user) {

        String sql = "select * from user where account=? and password=? and identity=? and status!=2";
        User querySingle = userDAO.querySingle(sql, User.class, user.getAccount(), user.getPassword(),user.getIdentity());
        return querySingle;
    }

    public User userSelectByAccount(User user) {
        String sql = "select * from user where account=?";
        User user1 = userDAO.querySingle(sql, User.class, user.getAccount());
        return user1;
    }

    public int userInsert(User user) {

        String totalCredit = "5000";

        String availableCredit = "5000";

        int desirable = (int)(5000.00/1.1);

        String desirableCredit = String.valueOf(desirable);

        String nowTime = DateUtil.getNowTime();

        String sql = "insert into user(name,account,password,totalCredit,availableCredit,desirableCredit,registerTime) values(?,?,?,?,?,?,?)";
        int update = userDAO.update(sql, user.getName(), user.getAccount(), user.getPassword(),totalCredit,availableCredit,desirableCredit,nowTime);
        return update;
    }

    /**
     * 删除用户，设置用户状态为  ： 2
     * @param user
     * @return
     */
    public int userDelete(User user) {

        String sql = "update user set status=2 where account=? ";
        int update = userDAO.update(sql, user.getAccount());
        return update;

    }

    /**
     * 根据查询结果返回Object的二维数组，用于表格的显示。
     * @param userList
     * @return
     */
    private Object[][] getObjectList(List<User> userList) {
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
     * 返回表格信息，模糊查询。
     * @return
     */
    public Object[][] userListByLike(String like) {

        String sql = "select * from user where account like concat('%',?,'%') or name like concat('%',?,'%') and status!=2";
        List<User> userList = userDAO.queryMulti(sql, User.class,like,like);

        if (userList.size() < 1) {
            return null;
        }

        return getObjectList(userList);
    }

    /**
     * 返回表格信息。
     * @return
     */
    public Object[][] userList() {

        String sql = "select * from user where status!=2 and identity=0";
        List<User> userList = userDAO.queryMulti(sql, User.class);

        if (userList.size() < 1) {
            return null;
        }

        return getObjectList(userList);
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
