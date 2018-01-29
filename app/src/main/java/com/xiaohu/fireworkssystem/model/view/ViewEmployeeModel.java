package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/16.
 * 从业人员信息表
 */
public class ViewEmployeeModel implements Serializable {
    //编码
    private String EmployeeId;
    //公司编码
    private String CompanyId;
    // 公司名
    private String CompanyName;
    // 辖区
    private String Area;
    // 辖区名
    private String AreaName;
    //员工类型
    private String EmployeeType;
    //员工性别
    private String EmployeeSex;
    //人员姓名
    private String EmployeeName;
    //手机号
    private String EmployeeMobilePhone;
    //证件类型
    private String EmployeeCertType;
    //证件号码
    private String EmployeerCertNumber;
    //民族
    private String EmployeeNation;
    //住址
    private String EmployeeAddress;
    //员工状态
    private String EmployeeState;
    //创建时间
    private String CreateTime;
    //最后一次修改时间
    private String LastTime;
    //备注
    private String Comment;
    //性别名称
    private String SexName;
    //员工状态名称
    private String EmployeeStateName;
    //是否选中
    private boolean isCheck;
    //民族名称
    private String EmployeeTypeName;
    private String CertTypeName;
    //员工类型名称
    private String NationName;
    //发证人员信息
    private String CredentialEmployeeId;
    //发证编码
    private String CredentialId;
    //发证类型
    private String CredentialType;
    //发证类型（名称）
    private String CredentialTypeName;
    //发证有效期
    private String CredentialVailtyDate;
    //发证机关
    private String IssuingAuthority;
    //发证机关name
    private String IssuingAuthorityName;

    public String getIssuingAuthorityName() {
        return IssuingAuthorityName;
    }

    public void setIssuingAuthorityName(String issuingAuthorityName) {
        IssuingAuthorityName = issuingAuthorityName;
    }

    public String getCredentialId() {
        return CredentialId;
    }

    public void setCredentialId(String credentialId) {
        CredentialId = credentialId;
    }

    public String getCredentialType() {
        return CredentialType;
    }

    public void setCredentialType(String credentialType) {
        CredentialType = credentialType;
    }

    public String getCredentialTypeName() {
        return CredentialTypeName;
    }

    public void setCredentialTypeName(String credentialTypeName) {
        CredentialTypeName = credentialTypeName;
    }

    public String getCredentialVailtyDate() {
        return CredentialVailtyDate;
    }

    public void setCredentialVailtyDate(String credentialVailtyDate) {
        CredentialVailtyDate = credentialVailtyDate;
    }

    public String getIssuingAuthority() {
        return IssuingAuthority;
    }

    public void setIssuingAuthority(String issuingAuthority) {
        IssuingAuthority = issuingAuthority;
    }

    public String getCredentialEmployeeId() {
        return CredentialEmployeeId;
    }

    public void setCredentialEmployeeId(String credentialEmployeeId) {
        CredentialEmployeeId = credentialEmployeeId;
    }

    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
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

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }

    public String getEmployeeType() {
        return EmployeeType;
    }

    public void setEmployeeType(String employeeType) {
        EmployeeType = employeeType;
    }

    public String getEmployeeSex() {
        return EmployeeSex;
    }

    public void setEmployeeSex(String employeeSex) {
        EmployeeSex = employeeSex;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getEmployeeMobilePhone() {
        return EmployeeMobilePhone;
    }

    public void setEmployeeMobilePhone(String employeeMobilePhone) {
        EmployeeMobilePhone = employeeMobilePhone;
    }

    public String getEmployeeCertType() {
        return EmployeeCertType;
    }

    public void setEmployeeCertType(String employeeCertType) {
        EmployeeCertType = employeeCertType;
    }

    public String getEmployeerCertNumber() {
        return EmployeerCertNumber;
    }

    public void setEmployeerCertNumber(String employeerCertNumber) {
        EmployeerCertNumber = employeerCertNumber;
    }

    public String getEmployeeNation() {
        return EmployeeNation;
    }

    public void setEmployeeNation(String employeeNation) {
        EmployeeNation = employeeNation;
    }

    public String getEmployeeAddress() {
        return EmployeeAddress;
    }

    public void setEmployeeAddress(String employeeAddress) {
        EmployeeAddress = employeeAddress;
    }

    public String getEmployeeState() {
        return EmployeeState;
    }

    public void setEmployeeState(String employeeState) {
        EmployeeState = employeeState;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        LastTime = lastTime;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getSexName() {
        return SexName;
    }

    public void setSexName(String sexName) {
        SexName = sexName;
    }

    public String getEmployeeStateName() {
        return EmployeeStateName;
    }

    public void setEmployeeStateName(String employeeStateName) {
        EmployeeStateName = employeeStateName;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getEmployeeTypeName() {
        return EmployeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        EmployeeTypeName = employeeTypeName;
    }

    public String getCertTypeName() {
        return CertTypeName;
    }

    public void setCertTypeName(String certTypeName) {
        CertTypeName = certTypeName;
    }

    public String getNationName() {
        return NationName;
    }

    public void setNationName(String nationName) {
        NationName = nationName;
    }
}
