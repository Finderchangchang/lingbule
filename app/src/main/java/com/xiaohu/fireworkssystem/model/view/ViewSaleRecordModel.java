package com.xiaohu.fireworkssystem.model.view;

import com.xiaohu.fireworkssystem.model.table.BusinessBoxCodeModel;

import java.io.Serializable;
import java.util.Arrays;

/**
 * 销售产品单信息。
 */
public class ViewSaleRecordModel implements Serializable{
    //详单编码
    private String ProductSaleRecordId;
    //订单编号
    private String ProductSaleId;
    //库存编码
    private String StockId;
    //产品数量
    private int ProductNumber;
    //创建时间
    private String PrdouctSaleTime;
    //产品金额
    private double ProductSaleAmount;
    //记录状态
    private String PrdouctSaleState;
    //记录状态名称
    private String PrdouctSaleStateName;
    //产品编码
    private String ProductId;
    //企业产品进货价
    private String TradePrice;
    //企业产品零售价
    private float ProductRetailPrice;
    //企业产品缩写码
    private String ProductAbbreviation;
    //企业产品状态
    private String ProductState;
    //状态名称
    private String ProductStateName;
    //产品名称
    private String ProductName;
    //供应商编码
    private String SupplierId;
    //产品类型
    private String ProductClass;
    //产品类型内容
    private String ProductClassName;
    //产品含药量
    private double ProductDose;
    //产品包装单位（箱/件/捆等）
    private String ProductPackagingUnit;
    //
    private String ProductGuidePrice;
    //指导价格
    private int UnitProductNumber;
    //供应商名称
    private String SupplierName;
    //库房号
    private String StorageWarehouse;
    //企业名称
    private String CompanyName;
    private int Productnums;

    //销售箱码信息
    private BusinessBoxCodeModel[] SaleRecordBoxCode;

    public BusinessBoxCodeModel[] getSaleRecordBoxCode() {
        return SaleRecordBoxCode;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public int getProductnums() {
        return Productnums;
    }

    public void setProductnums(int productnums) {
        Productnums = productnums;
    }

    public void setSaleRecordBoxCode(BusinessBoxCodeModel[] saleRecordBoxCode) {
        SaleRecordBoxCode = saleRecordBoxCode;
    }

    public String getProductSaleRecordId() {
        return ProductSaleRecordId;
    }

    public void setProductSaleRecordId(String productSaleRecordId) {
        ProductSaleRecordId = productSaleRecordId;
    }

    public String getProductSaleId() {
        return ProductSaleId;
    }

    public void setProductSaleId(String productSaleId) {
        ProductSaleId = productSaleId;
    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }



    public String getPrdouctSaleTime() {
        return PrdouctSaleTime;
    }

    public void setPrdouctSaleTime(String prdouctSaleTime) {
        PrdouctSaleTime = prdouctSaleTime;
    }

    public double getProductSaleAmount() {
        return ProductSaleAmount;
    }

    public void setProductSaleAmount(double productSaleAmount) {
        ProductSaleAmount = productSaleAmount;
    }

    public String getPrdouctSaleState() {
        return PrdouctSaleState;
    }

    public void setPrdouctSaleState(String prdouctSaleState) {
        PrdouctSaleState = prdouctSaleState;
    }

    public String getPrdouctSaleStateName() {
        return PrdouctSaleStateName;
    }

    public void setPrdouctSaleStateName(String prdouctSaleStateName) {
        PrdouctSaleStateName = prdouctSaleStateName;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getTradePrice() {
        return TradePrice;
    }

    public void setTradePrice(String tradePrice) {
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

    public String getProductGuidePrice() {
        return ProductGuidePrice;
    }

    public void setProductGuidePrice(String productGuidePrice) {
        ProductGuidePrice = productGuidePrice;
    }

    public int getUnitProductNumber() {
        return UnitProductNumber;
    }

    public void setUnitProductNumber(int unitProductNumber) {
        UnitProductNumber = unitProductNumber;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getStorageWarehouse() {
        return StorageWarehouse;
    }

    public void setStorageWarehouse(String storageWarehouse) {
        StorageWarehouse = storageWarehouse;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    @Override
    public String toString() {
        return "ViewSaleRecordModel{" +
                "ProductSaleRecordId='" + ProductSaleRecordId + '\'' +
                ", ProductSaleId='" + ProductSaleId + '\'' +
                ", StockId='" + StockId + '\'' +
                ", ProductNumber=" + ProductNumber +
                ", PrdouctSaleTime='" + PrdouctSaleTime + '\'' +
                ", ProductSaleAmount=" + ProductSaleAmount +
                ", PrdouctSaleState='" + PrdouctSaleState + '\'' +
                ", PrdouctSaleStateName='" + PrdouctSaleStateName + '\'' +
                ", ProductId='" + ProductId + '\'' +
                ", TradePrice='" + TradePrice + '\'' +
                ", ProductRetailPrice=" + ProductRetailPrice +
                ", ProductAbbreviation='" + ProductAbbreviation + '\'' +
                ", ProductState='" + ProductState + '\'' +
                ", ProductStateName='" + ProductStateName + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", SupplierId='" + SupplierId + '\'' +
                ", ProductClass='" + ProductClass + '\'' +
                ", ProductClassName='" + ProductClassName + '\'' +
                ", ProductDose=" + ProductDose +
                ", ProductPackagingUnit='" + ProductPackagingUnit + '\'' +
                ", ProductGuidePrice='" + ProductGuidePrice + '\'' +
                ", UnitProductNumber=" + UnitProductNumber +
                ", SupplierName='" + SupplierName + '\'' +
                ", StorageWarehouse='" + StorageWarehouse + '\'' +
                ", CompanyName='" + CompanyName + '\'' +
                ", SaleRecordBoxCode=" + Arrays.toString(SaleRecordBoxCode) +
                '}';
    }
}
