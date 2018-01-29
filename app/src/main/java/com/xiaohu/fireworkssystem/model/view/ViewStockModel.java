package com.xiaohu.fireworkssystem.model.view;

import com.xiaohu.fireworkssystem.model.table.OperatorBoxCodeModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/16.
 * 库存信息表
 */
public class ViewStockModel implements Serializable {
    //库存编码
    private String StockId;
    //企业编码
    private String CompanyId;
    //企业名称
    private String CompanyName;
    //产品编码
    private String ProductId;
    //产品预警上限
    private int UpperLimit;
    //产品预警下限
    private int LowerLimit;
    //产品存放的仓库
    private String StorageWarehouse;
    //产品总数量
    private int ProductTotal;
    //创建时间
    private String CreateTime;
    //创建用户
    private String CreateUser;
    //企业产品进货价
    private float TradePrice;
    //企业产品零售价
    private float ProductRetailPrice;
    //企业产品缩写码
    private String ProductAbbreviation;
    //企业产品状态
    private String ProductState;
    //企业产品状态名
    private String ProductStateName;
    //产品名称
    private String ProductName;
    //供应商编码
    private String SupplierId;
    //产品类型
    private String ProductClass;
    // 产品类型名
    private String ProductClassName;
    // 使用对像
    private String UseClass;
    // 使用对象（名称）
    private String UseClassName;
    //产品含药量
    private double ProductDose;
    //单位
    private String ProductPackagingUnit;
    //单位名
    private String ProductPackagingUnitName;
    //单位包装数量
    private int ProductNumber;
    //指导价格
    private float ProductGuidePrice;
    //供应商名称
    private String SupplierName;
    //联系人
    private String SupplierLinker;
    //联系方式
    private String SupplierLinkway;
    //产品前缀
    private String SupplierPrefix;
    //供应商缩写
    private String SupplierAbbreviation;
    //供应商地址
    private String SupplierAddress;
    //厂家状态
    private String SupplierState;
    //厂家状态名
    private String SupplierStateName;
    //辖区
    private String Area;
    //辖区name
    private String AreaName;
    //箱码业务信息
    private OperatorBoxCodeModel[] StockBoxCode;
    //记录数量
    private int MyNumber;
    private boolean isCheck;

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

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
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

    public int getProductTotal() {
        return ProductTotal;
    }

    public void setProductTotal(int productTotal) {
        ProductTotal = productTotal;
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

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
    }

    public String getProductClassName() {
        return ProductClassName;
    }

    public void setProductClassName(String productClassName) {
        ProductClassName = productClassName;
    }

    public double getProductDose() {
        return ProductDose;
    }

    public void setProductDose(double productDose) {
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

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public float getProductGuidePrice() {
        return ProductGuidePrice;
    }

    public void setProductGuidePrice(float productGuidePrice) {
        ProductGuidePrice = productGuidePrice;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getSupplierLinker() {
        return SupplierLinker;
    }

    public void setSupplierLinker(String supplierLinker) {
        SupplierLinker = supplierLinker;
    }

    public String getSupplierLinkway() {
        return SupplierLinkway;
    }

    public void setSupplierLinkway(String supplierLinkway) {
        SupplierLinkway = supplierLinkway;
    }

    public String getSupplierPrefix() {
        return SupplierPrefix;
    }

    public void setSupplierPrefix(String supplierPrefix) {
        SupplierPrefix = supplierPrefix;
    }

    public String getSupplierAbbreviation() {
        return SupplierAbbreviation;
    }

    public void setSupplierAbbreviation(String supplierAbbreviation) {
        SupplierAbbreviation = supplierAbbreviation;
    }

    public String getSupplierAddress() {
        return SupplierAddress;
    }

    public void setSupplierAddress(String supplierAddress) {
        SupplierAddress = supplierAddress;
    }

    public String getSupplierState() {
        return SupplierState;
    }

    public void setSupplierState(String supplierState) {
        SupplierState = supplierState;
    }

    public String getSupplierStateName() {
        return SupplierStateName;
    }

    public void setSupplierStateName(String supplierStateName) {
        SupplierStateName = supplierStateName;
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

    public OperatorBoxCodeModel[] getStockBoxCode() {
        return StockBoxCode;
    }

    public void setStockBoxCode(OperatorBoxCodeModel[] stockBoxCode) {
        StockBoxCode = stockBoxCode;
    }

    public int getMyNumber() {
        return MyNumber;
    }

    public void setMyNumber(int myNumber) {
        MyNumber = myNumber;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }
}
