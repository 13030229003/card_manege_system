package com.han.pojo;

/**
 * @PACKAGE_NAME: com.han.pojo
 * @Author XSH
 * @Date 2022-08-08 16:19
 * @Version 1.0
 * 描述： 账单类
 **/
public class Bill {

    /**
     * 账单号
     */
    private String id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 账号
     */
    private String account;

    /**
     * 金额
     */
    private String amount;

    /**
     * 账单类型
     */
    private String type;

    /**
     * 状态
     */
    private String status;

    public Bill() {
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", account='" + account + '\'' +
                ", amount='" + amount + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
