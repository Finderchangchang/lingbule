package com.xiaohu.fireworkssystem.model.view;

import com.xiaohu.fireworkssystem.model.table.BusinessBoxCodeModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ViewInputWareHouseRecordModel implements Serializable {
    // 入库详单编码
    private String WareHouseRecordId;
    // 入库单号
    private String WareHouseId;
    // 库存编码
    private String StockId;
    // 总件数
    private int ProductNumber;
    // 总箱数量
    private int UnitProducntNumber;
    // 产品金额
    private Float BillRecordAmount;
    // 商品编码
    private String ProductId;
    // 库存预警上限
    private int UpperLimit;
    // 库存预警下限
    private int LowerLimit;
    // 产品存放的仓库
    private String StorageWarehouse;
    // 产品总数量
    private int StockProductNumber;
    // 企业产品进货价
    private float TradePrice;
    // 企业产品零售价
    private float ProductRetailPrice;
    // 企业产品缩写码
    private String ProductAbbreviation;
    // 企业产品状态
    private String ProductState;
    // 企业产品状态Name
    private String ProductStateName;
    // 产品名称
    private String ProductName;
    // 产品类型Name
    private String ProductClassName;
    // 供应商编码
    private String SupplierId;
    // 产品含药量
    private float ProductDose;
    // 厂家名称
    private String SupplierName;
    // 产品单位
    private String ProductPackagingUnitName;
    // 规格
    private String UnitProductNumber;
    // 企业编码
    private String CompanyId;
    // 目标单位
    private String TragetCompanyId;
    // 登记人
    private String OperatorId;
    // 操作人
    private String AgentId;
    // 金额
    private float Amount;
    // 创建时间
    private String CreateTime;
    // 备注
    private String Comment;
    // 企业名称
    private String CompanyName;
    // 目标企业名称
    private String TargetCompanyName;
    // 登记人
    private String OperatorName;
    // 操作人
    private String AgentName;
    // 记录状态名称
    private String RecordStateName;
    // 用户名称
    private String UserName;
    // 企业类型
    private String CompanyType;
    // 目标企业类型
    private String TargetCompanyType;
    // 所属辖区
    private String Area;
    // 所属辖区名称
    private String AreaName;
    // 产品类型
    private String ProductClass;
    // 使用对象
    private String UseClass;
    // 使用对象（名称）
    private String UseClassName;
    //入库产品业务箱码信息
    private BusinessBoxCodeModel[] InputProductBoxCode;

    public BusinessBoxCodeModel[] getInputProductBoxCode() {
        return InputProductBoxCode;
    }

    public void setInputProductBoxCode(BusinessBoxCodeModel[] inputProductBoxCode) {
        InputProductBoxCode = inputProductBoxCode;
    }

    public String getWareHouseRecordId() {
        return WareHouseRecordId;
    }

    public void setWareHouseRecordId(String wareHouseRecordId) {
        WareHouseRecordId = wareHouseRecordId;
    }

    public String getWareHouseId() {
        return WareHouseId;
    }

    public void setWareHouseId(String wareHouseId) {
        WareHouseId = wareHouseId;
    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public int getUnitProducntNumber() {
        return UnitProducntNumber;
    }

    public void setUnitProducntNumber(int unitProducntNumber) {
        UnitProducntNumber = unitProducntNumber;
    }

    public String getUseClass() {
        return UseClass;
    }

    public void setUseClass(String useClass) {
        UseClass = useClass;
    }

    public String getUseClassName() {
        return UseClassName;
    }

    public void setUseClassName(String useClassName) {
        UseClassName = useClassName;
    }

    public Float getBillRecordAmount() {
        return BillRecordAmount;
    }

    public void setBillRecordAmount(Float billRecordAmount) {
        BillRecordAmount = billRecordAmount;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getUpperLimit() {
        return UpperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        UpperLimit = upperLimit;
    }

    public int getLowerLimit() {
        return LowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        LowerLimit = lowerLimit;
    }

    public String getStorageWarehouse() {
        return StorageWarehouse;
    }

    public void setStorageWarehouse(String storageWarehouse) {
        StorageWarehouse = storageWarehouse;
    }

    public int getStockProductNumber() {
        return StockProductNumber;
    }

    public void setStockProductNumber(int stockProductNumber) {
        StockProductNumber = stockProductNumber;
    }

    public float getTradePrice() {
        return TradePrice;
    }

    public void setTradePrice(float tradePrice) {
        TradePrice = tradePrice;
    }

    public float getProductRetailPrice() {
        return ProductRetailPrice;
    }

    public void setProductRetailPrice(float productRetailPrice) {
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

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getProductClassName() {
        return ProductClassName;
    }

    public void setProductClassName(String productClassName) {
        ProductClassName = productClassName;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public float getProductDose() {
        return ProductDose;
    }

    public void setProductDose(float productDose) {
        ProductDose = productDose;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getProductPackagingUnitName() {
        return ProductPackagingUnitName;
    }

    public void setProductPackagingUnitName(String productPackagingUnitName) {
        ProductPackagingUnitName = productPackagingUnitName;
    }

    public String getUnitProductNumber() {
        return UnitProductNumber;
    }

    public void setUnitProductNumber(String unitProductNumber) {
        UnitProductNumber = unitProductNumber;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getTragetCompanyId() {
        return TragetCompanyId;
    }

    public void setTragetCompanyId(String tragetCompanyId) {
        TragetCompanyId = tragetCompanyId;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getTargetCompanyName() {
        return TargetCompanyName;
    }

    public void setTargetCompanyName(String targetCompanyName) {
        TargetCompanyName = targetCompanyName;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public String getRecordStateName() {
        return RecordStateName;
    }

    public void setRecordStateName(String recordStateName) {
        RecordStateName = recordStateName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public void setCompanyType(String companyType) {
        CompanyType = companyType;
    }

    public String getTargetCompanyType() {
        return TargetCompanyType;
    }

    public void setTargetCompanyType(String targetCompanyType) {
        TargetCompanyType = targetCompanyType;
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

    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
    }
}
