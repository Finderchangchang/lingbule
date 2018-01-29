package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/16.
 * 销售单
 */
public class ViewProductOrderModel implements Serializable {
    //详单编码
    private String BillOrderID;
    //订单编号
    private String OrderID;
    //库存编码
    private String StockID;
    //产品数量
    private int ProductNumber;
    //创建时间
    private String OrderTime;
    //记录状态
    private String BillOrderState;
    //记录状态名称
    private String BillOrderStateName;
    //产品金额
    private String BillOrderAmount;
    //企业编码
    private String CompanyID;
    //企业名称
    private String CompanyName;
    //操作人编码
    private String OperatorID;
    //操作人名称
    private String OperatorName;
    //订单状态
    private String OrderState;
    //订单状态名称
    private String OrderStateName;
    //经办人
    private String Applyer;
    //经办人证件号码
    private String ApplyerCertNumber;
    //经办人民族
    private String ApplyerNation;
    //经办人民族内容
    private String ApplyerNationName;
    //联系手机
    private String ApplyerMobilePhone;
    //头像编码
    private String ApplyerHeadImageID;
    //住址
    private String ApplyerAddress;
    //购买用途
    private String Purpose;
    //购买用途
    private String PurposeName;
    //燃放时间
    private String UseTime;
    //燃放地点
    private String UseAddress;
    //创建用户
    private String UserID;
    //介绍信图片编码
    private String IntroductionImageID;
    //是否延伸服务
    private boolean IsExtendedService;
    //燃放人员（、号隔开)
    private String DischargePersonnel;
    //产品编码
    private String ProductID;
    //企业产品进货价
    private String TradePrice;
    //企业产品零售价
    private String ProductRetailPrice;
    //企业产品缩写码
    private String ProductAbbreviation;
    //企业产品状态
    private String ProductState;
    //企业产品状态
    private String ProductStateName;
    //产品条形码
    private String ProductBarCode;
    //产品名称
    private String ProductName;
    //供应商编码
    private String SupplierID;
    //供应商名称
    private String SupplierName;
    //产品类型
    private String ProductClass;
    //产品含药量
    private Double ProductDose;
    //产品包装单位（箱、件、捆）
    private String ProductPackagingUnit;
    //产品包装单位（箱、件、捆）
    private String ProductPackagingUnitName;
    private int ProductUnitNumber;
    //指导价格
    private String ProductGuidePrice;

    public String getBillOrderID() {
        return BillOrderID;
    }

    public void setBillOrderID(String billOrderID) {
        BillOrderID = billOrderID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getStockID() {
        return StockID;
    }

    public void setStockID(String stockID) {
        StockID = stockID;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getBillOrderState() {
        return BillOrderState;
    }

    public void setBillOrderState(String billOrderState) {
        BillOrderState = billOrderState;
    }

    public String getBillOrderStateName() {
        return BillOrderStateName;
    }

    public void setBillOrderStateName(String billOrderStateName) {
        BillOrderStateName = billOrderStateName;
    }

    public String getBillOrderAmount() {
        return BillOrderAmount;
    }

    public void setBillOrderAmount(String billOrderAmount) {
        BillOrderAmount = billOrderAmount;
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

    public String getOperatorID() {
        return OperatorID;
    }

    public void setOperatorID(String operatorID) {
        OperatorID = operatorID;
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

    public String getApplyer() {
        return Applyer;
    }

    public void setApplyer(String applyer) {
        Applyer = applyer;
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

    public String getApplyerMobilePhone() {
        return ApplyerMobilePhone;
    }

    public void setApplyerMobilePhone(String applyerMobilePhone) {
        ApplyerMobilePhone = applyerMobilePhone;
    }

    public String getApplyerHeadImageID() {
        return ApplyerHeadImageID;
    }

    public void setApplyerHeadImageID(String applyerHeadImageID) {
        ApplyerHeadImageID = applyerHeadImageID;
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

    public String getUseTime() {
        return UseTime;
    }

    public void setUseTime(String useTime) {
        UseTime = useTime;
    }

    public String getUseAddress() {
        return UseAddress;
    }

    public void setUseAddress(String useAddress) {
        UseAddress = useAddress;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getIntroductionImageID() {
        return IntroductionImageID;
    }

    public void setIntroductionImageID(String introductionImageID) {
        IntroductionImageID = introductionImageID;
    }

    public boolean isExtendedService() {
        return IsExtendedService;
    }

    public void setExtendedService(boolean extendedService) {
        IsExtendedService = extendedService;
    }

    public String getDischargePersonnel() {
        return DischargePersonnel;
    }

    public void setDischargePersonnel(String dischargePersonnel) {
        DischargePersonnel = dischargePersonnel;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getTradePrice() {
        return TradePrice;
    }

    public void setTradePrice(String tradePrice) {
        TradePrice = tradePrice;
    }

    public String getProductRetailPrice() {
        return ProductRetailPrice;
    }

    public void setProductRetailPrice(String productRetailPrice) {
        ProductRetailPrice = productRetailPrice;
    }

    public String getProductAbbreviation() {
        return ProductAbbreviation;
    }

    public void setProductAbbreviation(String productAbbreviation) {
        ProductAbbreviation = productAbbreviation;
    }

    public String getProductState() {
        return ProductState;
    }

    public void setProductState(String productState) {
        ProductState = productState;
    }

    public String getProductStateName() {
        return ProductStateName;
    }

    public void setProductStateName(String productStateName) {
        ProductStateName = productStateName;
    }

    public String getProductBarCode() {
        return ProductBarCode;
    }

    public void setProductBarCode(String productBarCode) {
        ProductBarCode = productBarCode;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
    }

    public Double getProductDose() {
        return ProductDose;
    }

    public void setProductDose(Double productDose) {
        ProductDose = productDose;
    }

    public String getProductPackagingUnit() {
        return ProductPackagingUnit;
    }

    public void setProductPackagingUnit(String productPackagingUnit) {
        ProductPackagingUnit = productPackagingUnit;
    }

    public String getProductPackagingUnitName() {
        return ProductPackagingUnitName;
    }

    public void setProductPackagingUnitName(String productPackagingUnitName) {
        ProductPackagingUnitName = productPackagingUnitName;
    }

    public int getProductUnitNumber() {
        return ProductUnitNumber;
    }

    public void setProductUnitNumber(int productUnitNumber) {
        ProductUnitNumber = productUnitNumber;
    }

    public String getProductGuidePrice() {
        return ProductGuidePrice;
    }

    public void setProductGuidePrice(String productGuidePrice) {
        ProductGuidePrice = productGuidePrice;
    }
}
