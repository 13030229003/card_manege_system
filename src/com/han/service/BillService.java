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


    /**
     * 根据订单id还款（只有类型位 1 和 3 的订单才能还款），将订单状态设置为0，   1表示为未还款。
     *          1、然后生成还款订单，类型为 2。 1表示消费订单，3表示取现订单。
     * @param id
     * @return
     */
   public int billRepayment(String id,String repayMoney,User user,String textMoney) {



           String billSql01 = "update bill set status=0 where id=?";
           billDAO.update(billSql01,id);

           String billID = "P" + DateUtil.getNowTime2();
           String addBillSql = "insert into bill(id,userName,account,amount,type) values(?,?,?,?,?)";
           int billInsertIndex = billDAO.update(addBillSql,billID,user.getName(),user.getAccount(), repayMoney, "2");

           String userSql01 = "update user set arrearsAmount=arrearsAmount-?,availableCredit=availableCredit+? where account=?";
           billDAO.update(userSql01,repayMoney,Math.ceil(Double.valueOf(repayMoney)/1.1),user.getAccount());

           user = userService.userSelectByAccount(user);

           String sql = "update user set totalCredit=?+?,desirableCredit=? where account=?";
           billDAO.update(sql,Double.valueOf(user.getAvailableCredit()),Double.valueOf(user.getStorageAmount()),
                   Math.floor(Double.valueOf(user.getAvailableCredit())/1.1)
                   ,user.getAccount());

           return 1;
   }

    /**
     * 根据用户帐号查找需要还款的账单。除去还款的账单。
     * @param account
     * @return
     */
    public Object[][] billRepaymentListByAccount(String account) {

        String sql = "select * from bill where account=? and type!=2 and status=1";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class, account);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }


    /**
     * 查找所有账单，管理员查找
     * @return
     */
    public Object[][] billList() {

        String sql = "select * from bill";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }

    /**
     * 根据账单号查找，管理员查找
     * @param id
     * @return
     */
    public Object[][] billListByID(String id) {

        String sql = "select * from bill where id like concat('%',?,'%')";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class,id);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }

    /**
     * 查找需要还款的账单，管理员查找
     * @return
     */
    public Object[][] arrearsBillList() {

        String sql = "select * from bill where type!=2 and status=1";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }
    /**
     * 根据账单号查找 需要还款的账单，管理员查找
     * @param id
     * @return
     */
    public Object[][]arrearsBillListByID(String id) {

        String sql = "select * from bill where id like concat('%',?,'%') and type!=2 and status=1";
        List<Bill> billList = billDAO.queryMulti(sql, Bill.class,id);
        Object[][] objectList = getObjectList(billList);
        return objectList;

    }


    /**
     * 根据用户帐号查找账单，用户查找
     * @param account
     * @return
     */
   public Object[][] billListByAccount(String account) {

       String sql = "select * from bill where account=?";
       List<Bill> billList = billDAO.queryMulti(sql, Bill.class, account);
       Object[][] objectList = getObjectList(billList);
       return objectList;

   }

    /**
     * 用户查找账单
     * @param account
     * @param id
     * @return
     */
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

        Double billAmount = Double.valueOf(bill.getAmount()); // 消费账单额度

        Double availableCredit = Double.valueOf(user.getAvailableCredit()); // 用户可用余额

        Double userStorageAmount = Double.valueOf(user.getStorageAmount()); // 用户预存余额
        Double userDesirable = Double.valueOf(user.getDesirableCredit()); // 用户可取现余额 = 可用额度 *  1.1


        String id = "P" + DateUtil.getNowTime2();
        bill.setId(id);

        // 预存金额大于 0
        if (userStorageAmount > 0) {


            // 预存金额 大于 账单金额
            if (userStorageAmount >= billAmount) {

                String userSql = "update user set storageAmount=storageAmount-? where account=?";
                int update01 = billDAO.update(userSql, billAmount, user.getAccount());

                String billSql = "insert into bill(id,userName,account,amount,type,status) values(?,?,?,?,?,?)";
                int update02 = billDAO.update(billSql, id, user.getName(), user.getAccount(), billAmount, bill.getType(), 0);

                user = userService.userSelectByAccount(user);

                String sql = "update user set totalCredit=?+? where account=?";
                int update = billDAO.update(sql, user.getAvailableCredit(), user.getStorageAmount(), user.getAccount());

                return bill;

                // 预存金额 + 可用额度 大于 账单额度
            } else if (userStorageAmount + availableCredit >= billAmount) {
                id = "SP" + DateUtil.getNowTime2();
                String billSql = "insert into bill(id,userName,account,amount,type,status) values(?,?,?,?,?,?)";
                int update01 = billDAO.update(billSql, id, user.getName(), user.getAccount(), userStorageAmount, bill.getType(), 0);
                id = "P" + DateUtil.getNowTime2();
                int update02 = billDAO.update(billSql, id, user.getName(), user.getAccount(), (int)((billAmount - userStorageAmount)*1.1), bill.getType(), 1);

                String userSql01 = "update user set storageAmount=0,availableCredit=?-?,desirableCredit=? where account=?";
                int update03 = billDAO.update(userSql01, availableCredit, (billAmount - userStorageAmount),
                        (int) Math.ceil((availableCredit - (int) (billAmount - userStorageAmount)) / 1.1),
                        user.getAccount());

                user = userService.userSelectByAccount(user);

                String sql = "update user set totalCredit=?+?,arrearsAmount=arrearsAmount+? where account=?";
                int update = billDAO.update(sql, user.getAvailableCredit(), user.getStorageAmount(), (int)((billAmount - userStorageAmount)*1.1),user.getAccount());

                return bill;
            } else {
                return null;
            }

        } else {
            if (availableCredit >= billAmount) {

                String billSql = "insert into bill(id,userName,account,amount,type,status) values(?,?,?,?,?,?)";
                int update01 = billDAO.update(billSql, id, user.getName(), user.getAccount(), (int)(billAmount*1.1),bill.getType(), 1);

                String userSql = "update user set availableCredit=availableCredit-? where account=?";
                int update02 = billDAO.update(userSql, billAmount, user.getAccount());

                user = userService.userSelectByAccount(user);
                String useSql02 = "update user set desirableCredit=?,totalCredit=?,arrearsAmount=arrearsAmount+? where account=?";
                int update = billDAO.update(useSql02, Math.ceil(Double.valueOf(user.getAvailableCredit()) / 1.1),
                        Double.valueOf(user.getAvailableCredit()) + Double.valueOf(user.getStorageAmount()),(int)(billAmount*1.1),user.getAccount());

                return bill;


            } else {
                return null;
            }
        }
    }

    /**
     * 返回 1：表示生成订单成功；   0 ： 生成失败。余额不足
     * @param bill
     * @return
     */
    public Bill billInsert02(Bill bill) {

        // 获取用户的可用余额，足够生成账单就生成，不够就提示不够。
        User user = new User();
        user.setAccount(bill.getAccount());
        user = userService.userSelectByAccount(user);

        Double billAmount = Double.valueOf(bill.getAmount()); // 消费账单额度

        Double availableCredit = Double.valueOf(user.getAvailableCredit()); // 用户可用余额

        Double userStorageAmount = Double.valueOf(user.getStorageAmount()); // 用户预存余额
        Double userDesirable = Double.valueOf(user.getDesirableCredit()); // 用户可取现余额 = 可用额度 *  1.1


        String id = "P" + DateUtil.getNowTime2();
        bill.setId(id);

        // 预存金额大于 0
        if (userStorageAmount > 0) {


            // 预存金额 大于 账单金额
            if (userStorageAmount >= billAmount) {

                String userSql = "update user set storageAmount=storageAmount-? where account=?";
                int update01 = billDAO.update(userSql, billAmount, user.getAccount());

                String billSql = "insert into bill(id,userName,account,amount,type,status) values(?,?,?,?,?,?)";
                int update02 = billDAO.update(billSql, id, user.getName(), user.getAccount(), billAmount, bill.getType(), 0);

                user = userService.userSelectByAccount(user);

                String sql = "update user set totalCredit=?+? where account=?";
                int update = billDAO.update(sql, user.getAvailableCredit(), user.getStorageAmount(), user.getAccount());

                return bill;

                // 预存金额 + 可用额度 大于 账单额度
            } else if (userStorageAmount + userDesirable >= billAmount) {
                id = "SP" + DateUtil.getNowTime2();
                String billSql = "insert into bill(id,userName,account,amount,type,status) values(?,?,?,?,?,?)";
                int update01 = billDAO.update(billSql, id, user.getName(), user.getAccount(), userStorageAmount, bill.getType(), 0);
                id = "P" + DateUtil.getNowTime2();
                int update02 = billDAO.update(billSql, id, user.getName(), user.getAccount(), (int)((billAmount - userStorageAmount)*1.1), bill.getType(), 1);

                String userSql01 = "update user set storageAmount=0,availableCredit=?-?,desirableCredit=? where account=?";
                int update03 = billDAO.update(userSql01, availableCredit, (billAmount - userStorageAmount),
                        (int) Math.ceil((availableCredit - (int) (billAmount - userStorageAmount)) / 1.1),
                        user.getAccount());

                user = userService.userSelectByAccount(user);

                String sql = "update user set totalCredit=?+?,arrearsAmount=arrearsAmount+? where account=?";
                int update = billDAO.update(sql, user.getAvailableCredit(), user.getStorageAmount(), (int)((billAmount - userStorageAmount)*1.1),user.getAccount());

                return bill;
            } else {
                return null;
            }

        } else {
            if (userDesirable >= billAmount) {

                String billSql = "insert into bill(id,userName,account,amount,type,status) values(?,?,?,?,?,?)";
                int update01 = billDAO.update(billSql, id, user.getName(), user.getAccount(), (int)(billAmount*1.1),bill.getType(), 1);

                String userSql = "update user set availableCredit=availableCredit-? where account=?";
                int update02 = billDAO.update(userSql, billAmount, user.getAccount());

                user = userService.userSelectByAccount(user);
                String useSql02 = "update user set desirableCredit=?,totalCredit=?,arrearsAmount=arrearsAmount+? where account=?";
                int update = billDAO.update(useSql02, Math.ceil(Double.valueOf(user.getAvailableCredit()) / 1.1),
                        Double.valueOf(user.getAvailableCredit()) + Double.valueOf(user.getStorageAmount()),(int)(billAmount*1.1),user.getAccount());

                return bill;

            } else {
                return null;
            }
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
