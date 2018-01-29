package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/12/26.
 */

public class PrisonerModel implements Serializable {
    //姓名
    private String Name;
    //证件类型（身份证号：11）
    private String IdentityType;
    //证件号码
    private String IdentityNumber;
    //民族
    private String Nation;
    //性别
    private String Sex;
    //籍贯
    private String Native;
    //出生日期
    private String Birthday;
    //企业编码
    private String CompanyId;
    //业务信息编码（销售单号）
    private String ExpansionId;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getIdentityType() {
        return IdentityType;
    }

    public void setIdentityType(String identityType) {
        IdentityType = identityType;
    }

    public String getIdentityNumber() {
        return IdentityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        IdentityNumber = identityNumber;
    }

    public String getNation() {
        return Nation;
    }

    public void setNation(String nation) {
        Nation = nation;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getNative() {
        return Native;
    }

    public void setNative(String aNative) {
        Native = aNative;
    }

    public String getBirthday() {
        return Birthday;
    }

    public void setBirthday(String birthday) {
        Birthday = birthday;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getExpansionId() {
        return ExpansionId;
    }

    public void setExpansionId(String expansionId) {
        ExpansionId = expansionId;
    }
}
