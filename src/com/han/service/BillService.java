package com.han.service;

import com.han.dao.BillDAO;
import com.han.dao.UserDAO;
import com.han.pojo.Bill;
import com.han.pojo.User;
import com.han.utils.DateUtil;

import java.util.List;

/**
 * @PACKAGE_NAME: com.han.service
 * @Author XSH
 * @Date 2022-08-08 16:55
 * @Version 1.0
 * 描述：
 **/
public class BillService {

    private BillDAO billDAO = new BillDAO();

   private UserService userService = new UserService();

    public Object[][] billList() {

        String sql = "select * from bill";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }

    public Object[][] billListByID(String id) {

        String sql = "select * from bill where id like concat('%',?,'%')";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class,id);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }


   public Object[][] billListByAccount(String account) {

       String sql = "select * from bill where account=?";
       List<Bill> billList = billDAO.queryMulti(sql, Bill.class, account);
       Object[][] objectList = getObjectList(billList);
       return objectList;

   }
    public Object[][] billListByAccountAndID(String account,String id) {

        String sql = "select * from bill where account=? and id like concat('%',?,'%')";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class, account,id);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }

    /**
     * 返回 1：表示生成订单成功；   0 ： 生成失败。余额不足
     * @param bill
     * @return
     */
    public Bill billInsert(Bill bill) {

        // 获取用户的可用余额，足够生成账单就生成，不够就提示不够。
        User user = new User();
        user.setAccount(bill.getAccount());
        user = userService.userSelectByAccount(user);

        Double userDesirableCredit = Double.valueOf(user.getDesirableCredit());   // 可取信用
        Double userStorageAmount = Double.valueOf(user.getStorageAmount());   // 预存信用额
        Double billAmount = Double.valueOf(bill.getAmount());

        String id = "P" + DateUtil.getNowTime2();
        bill.setId(id);

        if (userDesirableCredit > billAmount) {  // 可用余额足够

            /**
             * 1、生成账单
             * 2、减少自己的可用余额
             */

            String sqlInsert = "insert into bill(id,userName,account,amount,type) values(?,?,?,?,?)";
            String sqlDec = "update user set desirableCredit=desirableCredit-? where account=?";

            int billInsert = billDAO.update(sqlInsert, bill.getId(), bill.getUserName(),bill.getAccount(), String.valueOf((int)(billAmount * 1.1)), bill.getType());
            int desirableDec = billDAO.update(sqlDec, bill.getAmount(), bill.getAccount());

            if (billInsert == 1 && desirableDec == 1) {
                bill.setAmount(String.valueOf((int)(billAmount * 1.1)));
                return bill;
            } else {
                return null;
            }

        } else if ((userDesirableCredit+userStorageAmount) > billAmount) { // 可用余额加上预存余额才够


            System.out.println("不够的在用户预存余额中扣除！！！");

            String value = String.valueOf(billAmount - userDesirableCredit);
            /**
             * 1、生成账单
             * 2、减少自己的可用余额为零， 将剩下的在预存余额里面扣除。
             */
            String sqlInsert = "insert into bill(id,userName,account,amount,type) values(?,?,?,?,?)";
            String sqlDec = "update user set desirableCredit=0,storageAmount=storageAmount-? where account=?";

            int billInsert = billDAO.update(sqlInsert, bill.getId(), bill.getUserName(),bill.getAccount() , String.valueOf((int)(billAmount * 1.1)), bill.getType());
            int desirableDec = billDAO.update(sqlDec, value, bill.getAccount());

            if (billInsert == 1 && desirableDec == 1) {
                bill.setAmount(String.valueOf((int)(billAmount * 1.1)));
                return bill;
            } else {
                return null;
            }

        } else {
            /**
             * 可用额度 + 预存余额  <  账单消费      无法消费。
             */
            System.out.println("用户可用余额不够，无法消费！！！");
            return null;
        }
    }

    /**
     * 根据查询结果返回Object的二维数组，用于表格的显示。
     * @param billList
     * @return
     */
    private Object[][] getObjectList(List<Bill> billList) {
        Object[][] results = new Object[billList.size()][6];

        for (int i = 0; i < billList.size(); i++) {

            results[i][0] = billList.get(i).getId();
            results[i][1] = billList.get(i).getUserName();
            results[i][2] = billList.get(i).getAccount();
            results[i][3] = billList.get(i).getAmount();
            results[i][4] = billList.get(i).getType();
            results[i][5] = billList.get(i).getStatus();
        }

        return results;
    }

}
