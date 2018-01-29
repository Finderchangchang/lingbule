package com.xiaohu.fireworkssystem.model.search;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/3.
 */
public class SearchEmployeeModel implements Serializable {
    //人员编号
    private String EmployeeID;
    //证件号码
    private String EmployeerCertNumber;
    //姓名
    private String EmployeeName;
    //性别
    private String EmployeeSex;
    //人员状态
    private String EmployeeState;
    //人员类型
    private String EmployeeType;
    //所属辖区
    private String Area;
    //企业名称
    private String CompanyName;
    //企业id
    private String CompanyID;
    //批发单位
    private String ParentCompanyID;
    //登记时间(起)
    private String CreateTimeBegin;
    //登记时间(止)
    private String CreateTimeEnd;

    public String getEmployeeSex() {
        return EmployeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        EmployeeSex = employeeSex;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
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

    public String getEmployeeID() {
        return EmployeeID;
    }

    public void setEmployeeID(String employeeID) {
        EmployeeID = employeeID;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeerCertNumber() {
        return EmployeerCertNumber;
    }

    public void setEmployeerCertNumber(String employeerCertNumber) {
        EmployeerCertNumber = employeerCertNumber;
    }

    public String getEmployeeState() {
        return EmployeeState;
    }

    public void setEmployeeState(String employeeState) {
        EmployeeState = employeeState;
    }

    public String getEmployeeType() {
        return EmployeeType;
    }

    public void setEmployeeType(String employeeType) {
        EmployeeType = employeeType;
    }

    public String getParentCompanyID() {
        return ParentCompanyID;
    }

    public void setParentCompanyID(String parentCompanyID) {
        ParentCompanyID = parentCompanyID;
    }
}
