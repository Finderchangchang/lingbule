package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/11.
 */

public class ViewBoxCodeStockModel implements Serializable {
    //仓库id
    private String StockId;
    private int ProductTotal;
    //商品名称
    private String ProductName;
    //生产厂家
    private String SupplierName;
    //零售
    private float ProductRetailPrice;
    //产品类型名
    private String ProductClassName;
    //使用对象
    private String UseClass;
    //使用对象名称
    private String UseClassName;
    private String ProductPackagingUnit;
    private String CompanyName;
    //箱码
    private String BoxCode;
    //剩余数量
    private int BusinessCount;
    //产品id
    private String ProductId;
    private int ProductNumber;
    private String CompanyId;
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

    public int getProductTotal() {
        return ProductTotal;
    }

    public void setProductTotal(int productTotal) {
        ProductTotal = productTotal;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public float getProductRetailPrice() {
        return ProductRetailPrice;
    }

    public void setProductRetailPrice(float productRetailPrice) {
        ProductRetailPrice = productRetailPrice;
    }

    public String getProductClassName() {
        return ProductClassName;
    }

    public void setProductClassName(String productClassName) {
        ProductClassName = productClassName;
    }

    public String getProductPackagingUnit() {
        return ProductPackagingUnit;
    }

    public void setProductPackagingUnit(String productPackagingUnit) {
        ProductPackagingUnit = productPackagingUnit;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String boxCode) {
        BoxCode = boxCode;
    }

    public int getBusinessCount() {
        return BusinessCount;
    }

    public void setBusinessCount(int businessCount) {
        BusinessCount = businessCount;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
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
