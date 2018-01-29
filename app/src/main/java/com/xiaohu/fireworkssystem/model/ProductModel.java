package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;

/**
 * 产品信息表
 * 作者：zz on 2016/7/3 16:31
 */
public class ProductModel implements Serializable {
    //产品编码
    private String ProductID;
    //产品条形码
    private String ProductBarCode;
    //产品名称
    private String ProductName;
    //产品供应商编码
    private String SupplierID;
    //产品类型
    private String ProductClass;
    //产品含药量
    private double ProductDose;
    //产品包装单位（箱/件/捆等）
    private String ProductPackagingUnit;
    //单位数量
    private int ProductNumber;
    //指导价格
    private double ProductGuidePrice;
    //创建时间
    private String ProductCreateTime;
    //最后一次修改时间
    private String ProductLastTime;
    //备注
    private String ProductComment;

    @Override
    public String toString() {
        return "ProductModel{" +
                "ProductBarCode='" + ProductBarCode + '\'' +
                ", ProductID='" + ProductID + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", SupplierID='" + SupplierID + '\'' +
                ", ProductClass='" + ProductClass + '\'' +
                ", ProductDose=" + ProductDose +
                ", ProductPackagingUnit='" + ProductPackagingUnit + '\'' +
                ", ProductNumber=" + ProductNumber +
                ", ProductGuidePrice=" + ProductGuidePrice +
                ", ProductCreateTime='" + ProductCreateTime + '\'' +
                ", ProductLastTime='" + ProductLastTime + '\'' +
                ", ProductComment='" + ProductComment + '\'' +
                '}';
    }


    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
    }


    public String getProductBarCode() {
        return ProductBarCode;
    }

    public void setProductBarCode(String productBarCode) {
        ProductBarCode = productBarCode;
    }

    public String getProductComment() {
        return ProductComment;
    }

    public void setProductComment(String productComment) {
        ProductComment = productComment;
    }

    public String getProductCreateTime() {
        return ProductCreateTime;
    }

    public void setProductCreateTime(String productCreateTime) {
        ProductCreateTime = productCreateTime;
    }

    public double getProductDose() {
        return ProductDose;
    }

    public void setProductDose(double productDose) {
        ProductDose = productDose;
    }

    public double getProductGuidePrice() {
        return ProductGuidePrice;
    }

    public void setProductGuidePrice(double productGuidePrice) {
        ProductGuidePrice = productGuidePrice;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductLastTime() {
        return ProductLastTime;
    }

    public void setProductLastTime(String productLastTime) {
        ProductLastTime = productLastTime;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public String getProductPackagingUnit() {
        return ProductPackagingUnit;
    }

    public void setProductPackagingUnit(String productPackagingUnit) {
        ProductPackagingUnit = productPackagingUnit;
    }

    public String getSupplierID() {
        return SupplierID;
    }

    public void setSupplierID(String supplierID) {
        SupplierID = supplierID;
    }


}
