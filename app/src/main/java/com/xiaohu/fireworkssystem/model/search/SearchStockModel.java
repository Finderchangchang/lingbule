package com.xiaohu.fireworkssystem.model.search;

import java.io.Serializable;

/**
 * 库存查询
 * Created by Administrator on 2016/9/3.
 */
public class SearchStockModel implements Serializable {
    //企业编码
    private String CompanyId;
    //产品编码
    private String ProductId;
    //企业名称
    private String CompanyName;
    //企业类型名称
    private String CompanyTypeName;
    //企业类型
    private String CompanyType;
    //所属辖区
    private String Area;
    //产品简称
    private String ProductAbbreviation;
    //产品名称
    private String ProductName;
    //供应商名称
    private String SupplierName;
    //产品类型
    private String ProductClass;
    //使用对象
    private String UseClass;
    //箱码
    private String BoxCode;
    //创建起始时间
    private String CreateTimeBegin;
    //创建结束时间
    private String CreateTimeEnd;
    //
    private String StockId;

    public String getUseClass() {
        return UseClass;
    }

    public void setUseClass(String useClass) {
        UseClass = useClass;
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

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getProductAbbreviation() {
        return ProductAbbreviation;
    }

    public void setProductAbbreviation(String productAbbreviation) {
        ProductAbbreviation = productAbbreviation;
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

    public String getProductClass() {
        return ProductClass;
    }

    public void setProductClass(String productClass) {
        ProductClass = productClass;
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

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getCompanyTypeName() {
        return CompanyTypeName;
    }

    public void setCompanyTypeName(String companyTypeName) {
        CompanyTypeName = companyTypeName;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public void setCompanyType(String companyType) {
        CompanyType = companyType;
    }

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String boxCode) {
        BoxCode = boxCode;
    }
}

