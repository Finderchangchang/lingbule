package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**
 * 销售单信息
 * Created by Administrator on 2016/9/3.
 */
public class ProductSaleModel implements Serializable {
    //订单编号
    private String ProductSaleId;
    // 销售类型
    private String ProductSaleType;
    //企业编码
    private String CompanyId;
    //登记人编码
    private String OperatorId;
    //订单状态
    private String OrderState;
    // 企业名称
    private String CompanyName;
    // 操作人
    private String Applyer;
    //证件类型
    private String ApplyerCertType;
    //证件号码
    private String ApplyerCertNumber;
    //民族
    private String ApplyerNation;
    // 头像编码
    private String ApplyerHeadImageID;
    // 联系手机
    private String ApplyerMobilePhone;
    // 住址
    private String ApplyerAddress;
    //是否会员
    private boolean IsMember;
    //会员编码
    private String MemberID;
    //订单金额
    private float OrderAmount;
    //退货金额
    private float ReturnAmount;
    // 购买用途
    private String Purpose;
    // 创建时间
    private String CreateTime;
    // 创建用户
    private String CreateUser;
    // 设备编码
    private String TerminalId;
    // 备注
    private String OrderComment;
    // 销售详单信息
    private ProductSaleRecordModel[] ProductBillOrders;
    // 头像
    private HeadImageModel ApplyerHeadImage;

    public String getJson() {
        String json = "'{\"CompanyId\":\"" + Utils.URLEncode(getCompanyId()) +
                "\",\"OperatorId\":\"" + Utils.URLEncode(getOperatorId()) +
                "\",\"OrderState\":\"" + Utils.URLEncode(getOrderState()) +
                "\",\"Applyer\":\"" + Utils.URLEncode(getApplyer()) +
                "\",\"ApplyerCertType\":\"11" +
                "\",\"ApplyerCertNumber\":\"" + Utils.URLEncode(getApplyerCertNumber()) +
                "\",\"ApplyerNation\":\"" + Utils.URLEncode(getApplyerNation()) +
                "\",\"ApplyerMobilePhone\":\"" + Utils.URLEncode(getApplyerMobilePhone()) +
                "\",\"ApplyerAddress\":\"" + Utils.URLEncode(getApplyerAddress()) +
                "\",\"Purpose\":\"" + Utils.URLEncode(getPurpose()) +
                "\",\"CreateTime\":\"" + Utils.getNowDate()+
                "\",\"CreateUser\":\"" +Utils.URLEncode(getCreateUser())  +
                "\",\"OrderAmount\":" + Utils.URLEncode(getOrderAmount()+"") +
                ",\"ApplyerHeadImage\":{\"HeadImageBase64\":\"" + getApplyerHeadImage().getHeadImageBase64() +
                "\",\"CreateTime\":\""+Utils.getNowDate()+"\"}" +

//                ",\"IntroductionImage\":{\"HeadImageBase64\":\"" +getIntroductionImage().getHeadImage() +
//                "\",\"CreateTime\":\"" + Utils.getNowDate() + "\"}" +
//                "\",\"ApplyerHeadImage\":null"+
                //              ",\"IntroductionImage\":null"+
               /* getSImage() +*/
                ",\"ProductBillOrders\":[";
        return json;
    }


/*    public String getSImage() {
        if (getApplyerHeadImage() == null) {
            return ",\"IntroductionImage\":null";
        } else if (getApplyerHeadImage().getHeadImage().equals("")) {
            return ",\"IntroductionImage\":null";
        } else {
            return ",\"IntroductionImage\":{\"HeadImageBase64\":\"" + getIntroductionImage().getHeadImage() +
                    "\",\"CreateTime\":\"" + Utils.getNOWTime() + "\"}";
        }
    }*/

    public String getProductSaleId() {
        return ProductSaleId;
    }

    public void setProductSaleId(String productSaleId) {
        ProductSaleId = productSaleId;
    }

    public String getProductSaleType() {
        return ProductSaleType;
    }

    public void setProductSaleType(String productSaleType) {
        ProductSaleType = productSaleType;
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

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String orderState) {
        OrderState = orderState;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public boolean isMember() {
        return IsMember;
    }

    public void setMember(boolean member) {
        IsMember = member;
    }

    public String getMemberID() {
        return MemberID;
    }

    public void setMemberID(String memberID) {
        MemberID = memberID;
    }

    public float getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(float orderAmount) {
        OrderAmount = orderAmount;
    }

    public float getReturnAmount() {
        return ReturnAmount;
    }

    public void setReturnAmount(float returnAmount) {
        ReturnAmount = returnAmount;
    }

    public String getPurpose() {
        return Purpose;
    }

    public void setPurpose(String purpose) {
        Purpose = purpose;
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

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public String getOrderComment() {
        return OrderComment;
    }

    public void setOrderComment(String orderComment) {
        OrderComment = orderComment;
    }

    public ProductSaleRecordModel[] getProductBillOrders() {
        return ProductBillOrders;
    }

    public void setProductBillOrders(ProductSaleRecordModel[] productBillOrders) {
        ProductBillOrders = productBillOrders;
    }

    public HeadImageModel getApplyerHeadImage() {
        return ApplyerHeadImage;
    }

    public void setApplyerHeadImage(HeadImageModel applyerHeadImage) {
        ApplyerHeadImage = applyerHeadImage;
    }
}
