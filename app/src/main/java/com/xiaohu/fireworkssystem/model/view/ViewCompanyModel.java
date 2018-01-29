package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * 企业视图
 * Created by Administrator on 2016/9/24.
 */

public class ViewCompanyModel implements Serializable {
    //企业编码
    private String CompanyId;
    //上级企业编码
    private String ParentCompanyID;
    //上级企业名称
    private String ParentCompanyName;
    //企业名称
    private String CompanyName;
    //企业类型
    private String CompanyType;
    //企业类型名称
    private String CompanyTypeName;
    //企业状态
    private String CompanyState;
    //企业状态名称
    private String CompanyStateName;
    //企业地址
    private String CompanyAddress;
    //企业电话
    private String CompanyPhone;
    //辖区编码
    private String Area;
    //辖区名称
    private String AreaName;
    //法人
    private String Boss;
    //证件号码
    private String BossCertNumber;
    //负责人
    private String Leader;
    //负责人证件号
    private String LeaderCertNumber;
    //负责人联系方式
    private String LeaderLinkWay;
    //东经
    private String PointEast;
    //北纬
    private String PointNorth;
    //许可证件
    private String LicenseNumber;
    //有效期（至）
    private String LicenseNumberValidity;
    //营业执照号
    private String BusinessLicenseNumber;
    //营业执照有效期
    private String BusinessLicenseNumberValidity;
    //备注
    private String Comment;
    //加密key
    private String EncryptionKey;
    //创建时间
    private String CreateTime;
    //创建用户
    private String CreateUserID;
    //用户名
    private String UserName;
    //最后一次修改时间
    private String LastTime;
    private CompanyImageModel CompanyImages;



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

    public String getCompanyTypeName() {
        return CompanyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        CompanyTypeName = companyTypeName;
    }

    public String getCompanyState() {
        return CompanyState;
    }

    public void setCompanyState(String companyState) {
        CompanyState = companyState;
    }

    public String getCompanyStateName() {
        return CompanyStateName;
    }

    public void setCompanyStateName(String companyStateName) {
        CompanyStateName = companyStateName;
    }

    public String getCompanyAddress() {
        return CompanyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        CompanyAddress = companyAddress;
    }

    public String getCompanyPhone() {
        return CompanyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        CompanyPhone = companyPhone;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
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

    public String getLeaderLinkWay() {
        return LeaderLinkWay;
    }

    public void setLeaderLinkWay(String leaderLinkWay) {
        LeaderLinkWay = leaderLinkWay;
    }

    public String getPointEast() {
        return PointEast;
    }

    public void setPointEast(String pointEast) {
        PointEast = pointEast;
    }

    public String getPointNorth() {
        return PointNorth;
    }

    public void setPointNorth(String pointNorth) {
        PointNorth = pointNorth;
    }

    public String getLicenseNumber() {
        return LicenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        LicenseNumber = licenseNumber;
    }

    public String getLicenseNumberValidity() {
        return LicenseNumberValidity;
    }

    public void setLicenseNumberValidity(String licenseNumberValidity) {
        LicenseNumberValidity = licenseNumberValidity;
    }

    public String getBusinessLicenseNumber() {
        return BusinessLicenseNumber;
    }

    public void setBusinessLicenseNumber(String businessLicenseNumber) {
        BusinessLicenseNumber = businessLicenseNumber;
    }

    public String getBusinessLicenseNumberValidity() {
        return BusinessLicenseNumberValidity;
    }

    public void setBusinessLicenseNumberValidity(String businessLicenseNumberValidity) {
        BusinessLicenseNumberValidity = businessLicenseNumberValidity;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getEncryptionKey() {
        return EncryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        EncryptionKey = encryptionKey;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateUserID() {
        return CreateUserID;
    }

    public void setCreateUserID(String createUserID) {
        CreateUserID = createUserID;
    }

    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        LastTime = lastTime;
    }

    public CompanyImageModel getCompanyImages() {
        return CompanyImages;
    }

    public void setCompanyImages(CompanyImageModel companyImages) {
        CompanyImages = companyImages;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getParentCompanyName() {
        return ParentCompanyName;
    }

    public void setParentCompanyName(String parentCompanyName) {
        ParentCompanyName = parentCompanyName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
