package com.han.controller;

import com.han.pojo.Bill;
import com.han.pojo.User;
import com.han.service.BillService;
import sun.security.util.DerEncoder;

/**
 * @PACKAGE_NAME: com.han.controller
 * @Author XSH
 * @Date 2022-08-08 17:03
 * @Version 1.0
 * 描述：
 **/
public class BillController {

    private BillService billService = new BillService();

    public Bill billInsert(Bill bill) {
        return billService.billInsert(bill);
    }

    public Bill billInsert02(Bill bill) {
        return billService.billInsert02(bill);
    }

    public Object[][] billListByAccount(String account) {
        return billService.billListByAccount(account);
    }

    public Object[][] billListByAccountAndID(String account,String id) {
        return billService.billListByAccountAndID(account,id);
    }

    public Object[][] billList() {
        return billService.billList();
    }
    public Object[][] billListByID(String id) {
        return billService.billListByID(id);
    }

    public Object[][] billRepaymentListByAccount(String account) {
        return billService.billRepaymentListByAccount(account);
    }
    public int billRepayment(String id, String repayMoney,User user,String textMoney) {
        return billService.billRepayment(id,repayMoney,user,textMoney);
    }

    public Object[][] arrearsBillList() {
        return billService.arrearsBillList();
    }

    public Object[][] arrearsBillListByID(String id) {
        return billService.arrearsBillListByID(id);
    }

}
