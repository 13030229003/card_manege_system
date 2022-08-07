package com.han.pojo;

/**
 * @PACKAGE_NAME: com.han.pojo
 * @Author XSH
 * @Date 2022-08-06 15:18
 * @Version 1.0
 * 描述：
 **/
public class User {

    private String id;

    private String name;

    private String account;

    private String password;

    private String identity;

    /**
     * 总信用额 = 可用信用 + 预存信用
     */
    private String totalCredit;

    /**
     * 可用信用默认为5k
     */
    private String availableCredit;

    /**
     * 可取信用额 = 可用信用额 除以 1.1
     */
    private String desirableCredit;

    /**
     * 欠款金额
     */
    private String arrearsAmount;

    /**
     * 预存金额
     */
    private String storageAmount;

    /**
     * 注册时间
     */
    private String registerTime;


    private String status;

    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }

    public String getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(String availableCredit) {
        this.availableCredit = availableCredit;
    }

    public String getDesirableCredit() {
        return desirableCredit;
    }

    public void setDesirableCredit(String desirableCredit) {
        this.desirableCredit = desirableCredit;
    }

    public String getArrearsAmount() {
        return arrearsAmount;
    }

    public void setArrearsAmount(String arrearsAmount) {
        this.arrearsAmount = arrearsAmount;
    }

    public String getStorageAmount() {
        return storageAmount;
    }

    public void setStorageAmount(String storageAmount) {
        this.storageAmount = storageAmount;
    }

    public String getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(String registerTime) {
        this.registerTime = registerTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", identity='" + identity + '\'' +
                ", totalCredit='" + totalCredit + '\'' +
                ", availableCredit='" + availableCredit + '\'' +
                ", desirableCredit='" + desirableCredit + '\'' +
                ", arrearsAmount='" + arrearsAmount + '\'' +
                ", storageAmount='" + storageAmount + '\'' +
                ", registerTime='" + registerTime + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User(String account, String password, String identity) {
        this.account = account;
        this.password = password;
        this.identity = identity;
    }

    public User() {
    }
}
