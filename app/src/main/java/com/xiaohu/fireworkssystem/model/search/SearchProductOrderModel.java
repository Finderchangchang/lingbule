package com.xiaohu.fireworkssystem.model.search;

import java.io.Serializable;

/**
 * 销售单查询
 * Created by Administrator on 2016/9/3.
 */
public class SearchProductOrderModel implements Serializable {
    //销售单号
    private String OrderID;
    //批发企业编码
    private String ParentCompanyID;
    //零售点编码
    private String CompanyID;
    //企业名称
    private String CompanyName;
    //客户名称
    private String Appler;
    //客户证件号码
    private String ApplyerCertNumber;
    //订单状态
    private String OrderState;
    //销售员
    private String OperatorName;
    //是否延伸服务
    private boolean IsExtendedService;
    //创建时间（起始）
    private String CreateTimeBegin;

    public String getAppler() {
        return Appler;
    }

    public void setAppler(String appler) {
        Appler = appler;
    }

    public String getApplyerCertNumber() {
        return ApplyerCertNumber;
    }

    public void setApplyerCertNumber(String applyerCertNumber) {
        ApplyerCertNumber = applyerCertNumber;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public boolean isExtendedService() {
        return IsExtendedService;
    }

    public void setExtendedService(boolean extendedService) {
        IsExtendedService = extendedService;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String orderState) {
        OrderState = orderState;
    }

    public String getParentCompanyID() {
        return ParentCompanyID;
    }

    public void setParentCompanyID(String parentCompanyID) {
        ParentCompanyID = parentCompanyID;
    }

    //创建时间（至）
    private String CreateTimeEnd;


}
