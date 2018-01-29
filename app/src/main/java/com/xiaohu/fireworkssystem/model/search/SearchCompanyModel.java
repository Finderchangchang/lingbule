package com.xiaohu.fireworkssystem.model.search;

import java.io.Serializable;

/**
 * 查询企业
 */

public class SearchCompanyModel implements Serializable {
    //企业编码
    private String CompanyID;
    //直营企业编码
    private String ParentCompanyID;
    //企业名称
    private String CompanyName;
    //企业类型
    private String CompanyType;
    //企业状态
    private String CompanyState;
    //所属辖区
    private String Area;
    //法人
    private String Boss;
    //法人证件号码
    private String BossCertNumber;
    //负责人
    private String Leader;
    //负责人证件号码
    private String LeaderCertNumber;
    //许可证号
    private String LicenseNumber;
    //营业执照号
    private String BusinessLicenseNumber;
    //创建用户
    private String CreateUserID;
    //创建时间起
    private String CreateTimeBegin;
    //创建时间（至）
    private String CreateTimeEnd;

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getParentCompanyID() {
        return ParentCompanyID;
    }

    public void setParentCompanyID(String parentCompanyID) {
        ParentCompanyID = parentCompanyID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public void setCompanyType(String companyType) {
        CompanyType = companyType;
    }

    public String getCompanyState() {
        return CompanyState;
    }

    public void setCompanyState(String companyState) {
        CompanyState = companyState;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getBoss() {
        return Boss;
    }

    public void setBoss(String boss) {
        Boss = boss;
    }

    public String getBossCertNumber() {
        return BossCertNumber;
    }

    public void setBossCertNumber(String bossCertNumber) {
        BossCertNumber = bossCertNumber;
    }

    public String getLeader() {
        return Leader;
    }

    public void setLeader(String leader) {
        Leader = leader;
    }

    public String getLeaderCertNumber() {
        return LeaderCertNumber;
    }

    public void setLeaderCertNumber(String leaderCertNumber) {
        LeaderCertNumber = leaderCertNumber;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public String getBusinessLicenseNumber() {
        return BusinessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        BusinessLicenseNumber = businessLicenseNumber;
    }

    public String getCreateUserID() {
        return CreateUserID;
    }

    public void setCreateUserID(String createUserID) {
        CreateUserID = createUserID;
    }

    public String getCreateTimeBegin() {
        return CreateTimeBegin;
    }

    public void setCreateTimeBegin(String createTimeBegin) {
        CreateTimeBegin = createTimeBegin;
    }

    public String getCreateTimeEnd() {
        return CreateTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        CreateTimeEnd = createTimeEnd;
    }
}
