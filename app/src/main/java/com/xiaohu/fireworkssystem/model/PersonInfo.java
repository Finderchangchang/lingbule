package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 */

public class PersonInfo implements Serializable {
    //姓名
    private String Name;
    //证件号码
    private String CardNumber;
    //性别
    private String Sex;
    //籍贯
    private String Nation;
    //出生日期
    private String Birthday;
    //住址
    private String Address;
    //用户编码
    private String UserId;
    //企业编码
    private String CompanyId;
    //延伸服务
    private boolean IsExtendedService;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        Nation = nation;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public boolean isExtendedService() {
        return IsExtendedService;
    }

    public void setExtendedService(boolean extendedService) {
        IsExtendedService = extendedService;
    }
}
