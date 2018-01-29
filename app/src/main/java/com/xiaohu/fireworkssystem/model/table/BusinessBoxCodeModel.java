package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**
 箱码业务信息（采购、销售、库存、盘点等）

 */

public class BusinessBoxCodeModel implements Serializable{
    //业务编码
    private String BusinessId;
    //企业编码
    private String CompanyId;
    //业务单编码
    private String BusinessOrder;
    //箱码
    private String BoxCode;
    //业务类型
    private String BusinessType;
    //业务数量
    private int BusinessCount;
    //业务状态
    private String BusinessStatus;
    //创建时间
    private String CreateTime;
    //创建用户
    private String CreateUser;
    // 备注
    private String Comment;
    //
    private int ProductNumber;

    public String getJson() {
        String json = "{\"BoxCode\":\"" + Utils.URLEncode(getBoxCode()) +
                "\",\"Amount\":" + getProductNumber()+
                "}]}";


        return json;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public String getBusinessId() {
        return BusinessId;
    }

    public void setBusinessId(String businessId) {
        BusinessId = businessId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getBusinessOrder() {
        return BusinessOrder;
    }

    public void setBusinessOrder(String businessOrder) {
        BusinessOrder = businessOrder;
    }

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String boxCode) {
        BoxCode = boxCode;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public int getBusinessCount() {
        return BusinessCount;
    }

    public void setBusinessCount(int businessCount) {
        BusinessCount = businessCount;
    }

    public String getBusinessStatus() {
        return BusinessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        BusinessStatus = businessStatus;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    @Override
    public String toString() {
        return "BusinessBoxCodeModel{" +
                "BusinessId='" + BusinessId + '\'' +
                ", CompanyId='" + CompanyId + '\'' +
                ", BusinessOrder='" + BusinessOrder + '\'' +
                ", BoxCode='" + BoxCode + '\'' +
                ", BusinessType='" + BusinessType + '\'' +
                ", BusinessCount=" + BusinessCount +
                ", BusinessStatus='" + BusinessStatus + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", CreateUser='" + CreateUser + '\'' +
                ", Comment='" + Comment + '\'' +
                ", ProductNumber=" + ProductNumber +
                '}';
    }
}
