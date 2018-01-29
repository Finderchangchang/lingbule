package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;

/**企业修改模型
 * Created by Administrator on 2017/10/16.
 */

public class EditCompanyModel implements Serializable{

    //企业编码
    private String CompanyId;
    //企业名称
    private String CompanyName;
    //企业地址
    private String CompanyAddress;
    //企业电话
    private String CompanyPhone;
    //负责人联系方式
    private String LeaderLinkWay;
    //东经
    private float PointEast;
    //北纬
    private float PointNorth;
    //许可证图片
    private String Base64LicenseImage;
    //许可证号
    private String LicenseNumber;
    //有效期（至）
    private String LicenseNumberValidity;
    //营业执照图片
    private String Base64BusinessLicenseImage;
    //营业执照号
    private String BusinessLicenseNumber;
    //营业执照有效期
    private String BusinessLicenseNumberValidity;
    //备注
    private String Comment;

    public String getBase64BusinessLicenseImage() {
        return Base64BusinessLicenseImage;
    }

    public void setBase64BusinessLicenseImage(String base64BusinessLicenseImage) {
        Base64BusinessLicenseImage = base64BusinessLicenseImage;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public String getLeaderLinkWay() {
        return LeaderLinkWay;
    }

    public void setLeaderLinkWay(String leaderLinkWay) {
        LeaderLinkWay = leaderLinkWay;
    }

    public float getPointEast() {
        return PointEast;
    }

    public void setPointEast(float pointEast) {
        PointEast = pointEast;
    }

    public float getPointNorth() {
        return PointNorth;
    }

    public void setPointNorth(float pointNorth) {
        PointNorth = pointNorth;
    }

    public String getBase64LicenseImage() {
        return Base64LicenseImage;
    }

    public void setBase64LicenseImage(String base64LicenseImage) {
        Base64LicenseImage = base64LicenseImage;
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
}
