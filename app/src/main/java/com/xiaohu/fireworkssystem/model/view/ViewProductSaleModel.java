package com.xiaohu.fireworkssystem.model.view;

import com.xiaohu.fireworkssystem.model.table.HeadImageModel;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 销售单信息
 */
public class ViewProductSaleModel implements Serializable {
    //订单编号
    private String ProductSaleId;
    //企业编码
    private String CompanyId;
    //企业名称
    private String CompanyName;
    //所属辖区
    private String CompanyArea;
    //所属辖区内容
    private String CompanyAreaName;
    //登记人编码
    private String OperatorId;
    //登记人名称
    private String OperatorName;
    //订单状态
    private String OrderState;
    //订单状态名称
    private String OrderStateName;
    //订单金额
    private float OrderAmount;
    //是否会员
    private boolean IsMember;
    //退货金额
    private float ReturnAmount;
    //会员编码
    private String MemberId;
    //经办企业
    private String ApplyCompanyName;
    //操作人
    private String Applyer;
    //操作人证件类型
    private String ApplyerCertType;
    //操作人证件类型名称
    private String ApplyerCertTypeName;
    //操作人证件号码
    private String ApplyerCertNumber;
    //操作人民族
    private String ApplyerNation;
    //操作人民族内容
    private String ApplyerNationName;
    //头像编码
    private String ApplyerHeadImageID;
    //联系手机
    private String ApplyerMobilePhone;
    //住址
    private String ApplyerAddress;
    //购买用途
    private String Purpose;
    //购买用途
    private String PurposeName;
    //创建时间
    private String CreateTime;
    //创建用户
    private String CreateUser;
    //创建用户名
    private String UserName;
    //备注
    private String OrderComment;
    //数量
    private int ProductSum;

    //设置编码
    private String TerminalId;

    //产品详单信息
    private ViewSaleRecordModel[] ProductBillOrders;

    //头像
    private HeadImageModel ApplyerHeadImage;
    //介绍信息图片
    //private HeadImageModel IntroductionImage;

    public String getProductSaleId() {
        return ProductSaleId;
    }

    public void setProductSaleId(String productSaleId) {
        ProductSaleId = productSaleId;
    }


    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getCompanyArea() {
        return CompanyArea;
    }

    public void setCompanyArea(String companyArea) {
        CompanyArea = companyArea;
    }

    public String getCompanyAreaName() {
        return CompanyAreaName;
    }

    public void setCompanyAreaName(String companyAreaName) {
        CompanyAreaName = companyAreaName;
    }


    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String orderState) {
        OrderState = orderState;
    }

    public String getOrderStateName() {
        return OrderStateName;
    }

    public void setOrderStateName(String orderStateName) {
        OrderStateName = orderStateName;
    }

    public float getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        OrderAmount = orderAmount;
    }

    public boolean isMember() {
        return IsMember;
    }

    public void setMember(boolean member) {
        IsMember = member;
    }

    public float getReturnAmount() {
        return ReturnAmount;
    }

    public void setReturnAmount(float returnAmount) {
        ReturnAmount = returnAmount;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public String getMemberId() {
        return MemberId;
    }

    public void setMemberId(String memberId) {
        MemberId = memberId;
    }

    public String getApplyCompanyName() {
        return ApplyCompanyName;
    }

    public void setApplyCompanyName(String applyCompanyName) {
        ApplyCompanyName = applyCompanyName;
    }

    public String getApplyer() {
        return Applyer;
    }

    public void setApplyer(String applyer) {
        Applyer = applyer;
    }

    public String getApplyerCertType() {
        return ApplyerCertType;
    }

    public void setApplyerCertType(String applyerCertType) {
        ApplyerCertType = applyerCertType;
    }

    public String getApplyerCertTypeName() {
        return ApplyerCertTypeName;
    }

    public void setApplyerCertTypeName(String applyerCertTypeName) {
        ApplyerCertTypeName = applyerCertTypeName;
    }

    public String getApplyerCertNumber() {
        return ApplyerCertNumber;
    }

    public void setApplyerCertNumber(String applyerCertNumber) {
        ApplyerCertNumber = applyerCertNumber;
    }

    public String getApplyerNation() {
        return ApplyerNation;
    }

    public void setApplyerNation(String applyerNation) {
        ApplyerNation = applyerNation;
    }

    public String getApplyerNationName() {
        return ApplyerNationName;
    }

    public void setApplyerNationName(String applyerNationName) {
        ApplyerNationName = applyerNationName;
    }

    public String getApplyerHeadImageID() {
        return ApplyerHeadImageID;
    }

    public void setApplyerHeadImageID(String applyerHeadImageID) {
        ApplyerHeadImageID = applyerHeadImageID;
    }

    public String getApplyerMobilePhone() {
        return ApplyerMobilePhone;
    }

    public void setApplyerMobilePhone(String applyerMobilePhone) {
        ApplyerMobilePhone = applyerMobilePhone;
    }

    public String getApplyerAddress() {
        return ApplyerAddress;
    }

    public void setApplyerAddress(String applyerAddress) {
        ApplyerAddress = applyerAddress;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
    }

    public String getPurposeName() {
        return PurposeName;
    }

    public void setPurposeName(String purposeName) {
        PurposeName = purposeName;
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

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getOrderComment() {
        return OrderComment;
    }

    public void setOrderComment(String orderComment) {
        OrderComment = orderComment;
    }

    public int getProductSum() {
        return ProductSum;
    }

    public void setProductSum(int productSum) {
        ProductSum = productSum;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public ViewSaleRecordModel[] getProductBillOrders() {
        return ProductBillOrders;
    }

    public void setProductBillOrders(ViewSaleRecordModel[] productBillOrders) {
        ProductBillOrders = productBillOrders;
    }

    public HeadImageModel getApplyerHeadImage() {
        return ApplyerHeadImage;
    }

    public void setApplyerHeadImage(HeadImageModel applyerHeadImage) {
        ApplyerHeadImage = applyerHeadImage;
    }

    @Override
    public String toString() {
        return "ViewProductSaleModel{" +
                "ProductSaleId='" + ProductSaleId + '\'' +
                ", CompanyId='" + CompanyId + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", CompanyArea='" + CompanyArea + '\'' +
                ", CompanyAreaName='" + CompanyAreaName + '\'' +
                ", OperatorId='" + OperatorId + '\'' +
                ", OperatorName='" + OperatorName + '\'' +
                ", OrderState='" + OrderState + '\'' +
                ", OrderStateName='" + OrderStateName + '\'' +
                ", OrderAmount=" + OrderAmount +
                ", IsMember=" + IsMember +
                ", ReturnAmount=" + ReturnAmount +
                ", MemberId='" + MemberId + '\'' +
                ", ApplyCompanyName='" + ApplyCompanyName + '\'' +
                ", Applyer='" + Applyer + '\'' +
                ", ApplyerCertType='" + ApplyerCertType + '\'' +
                ", ApplyerCertTypeName='" + ApplyerCertTypeName + '\'' +
                ", ApplyerCertNumber='" + ApplyerCertNumber + '\'' +
                ", ApplyerNation='" + ApplyerNation + '\'' +
                ", ApplyerNationName='" + ApplyerNationName + '\'' +
                ", ApplyerHeadImageID='" + ApplyerHeadImageID + '\'' +
                ", ApplyerMobilePhone='" + ApplyerMobilePhone + '\'' +
                ", ApplyerAddress='" + ApplyerAddress + '\'' +
                ", Purpose='" + Purpose + '\'' +
                ", PurposeName='" + PurposeName + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", CreateUser='" + CreateUser + '\'' +
                ", UserName='" + UserName + '\'' +
                ", OrderComment='" + OrderComment + '\'' +
                ", ProductSum=" + ProductSum +
                ", TerminalId='" + TerminalId + '\'' +
                ", ProductBillOrders=" + Arrays.toString(ProductBillOrders) +
                ", ApplyerHeadImage=" + ApplyerHeadImage +
                '}';
    }
}