package com.xiaohu.fireworkssystem.model;

import com.xiaohu.fireworkssystem.model.table.HeadImageModel;

import java.io.Serializable;

/**
 * 作者：zz on 2016/7/3 16:31
 */
public class EmployeeModel implements Serializable {
    //人员编码
    private String EmployeeId;
    //企业编码
    private String CompanyId;
    //人员姓名
    private String EmployeeName;
    //员工类型
    private String EmployeeType;
    //员工性别
    private String EmployeeSex;
    //证件类型
    private String EmployeeCertType;
    //证件号码
    private String EmployeerCertNumber;
    //民族
    private String EmployeeNation;
    //出生日期
    private String EmployeeBirthday;
    //籍贯
    private String EmployeeNative;
    //手机号
    private String EmployeeMobilePhone;
    //住址
    private String EmployeeAddress;
    //员工状态
    private String EmployeeState;
    //创建时间
    private String CreateTime;
    //最后一次修改时间
    private String LastTime;
    //扩展信息
    private String ExtendedInformation;
    //备注
    private String Comment;
    //发证人员编码
    private String CredentialEmployeeId;
    //发证编码
    private String CredentialId;
    //发证类型
    private String CredentialType;
    //发证有效期
    private String CredentialVailtyDate;
    //发证机关
    private String IssuingAuthority;
    //接收头像图片
    private byte[] HeadImageByte;
    //上岗资格证图片
    private byte[] CredentialImageByte;


    // 接收头像（base64 字符串）
    private String EmployeeHeadImageBase64;
    private HeadImageModel HeadImageModel;
    private String CredentialImageBase64;
    private HeadImageModel CredentialImage;

    public String getCredentialEmployeeId() {
        return CredentialEmployeeId;
    }

    public void setCredentialEmployeeId(String credentialEmployeeId) {
        CredentialEmployeeId = credentialEmployeeId;
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

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
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

    public String getEmployeeBirthday() {
        return EmployeeBirthday;
    }

    public void setEmployeeBirthday(String employeeBirthday) {
        EmployeeBirthday = employeeBirthday;
    }

    public String getEmployeeNative() {
        return EmployeeNative;
    }

    public void setEmployeeNative(String employeeNative) {
        EmployeeNative = employeeNative;
    }

    public String getEmployeeMobilePhone() {
        return EmployeeMobilePhone;
    }

    public void setEmployeeMobilePhone(String employeeMobilePhone) {
        EmployeeMobilePhone = employeeMobilePhone;
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

    public String getExtendedInformation() {
        return ExtendedInformation;
    }

    public void setExtendedInformation(String extendedInformation) {
        ExtendedInformation = extendedInformation;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getEmployeeHeadImageBase64() {
        return EmployeeHeadImageBase64;
    }

    public void setEmployeeHeadImageBase64(String employeeHeadImageBase64) {
        EmployeeHeadImageBase64 = employeeHeadImageBase64;
    }

    public byte[] getHeadImageByte() {
        return HeadImageByte;
    }

    public void setHeadImageByte(byte[] headImageByte) {
        HeadImageByte = headImageByte;
    }

    public byte[] getCredentialImageByte() {
        return CredentialImageByte;
    }

    public void setCredentialImageByte(byte[] credentialImageByte) {
        CredentialImageByte = credentialImageByte;
    }

    public String getCredentialImageBase64() {
        return CredentialImageBase64;
    }

    public void setCredentialImageBase64(String credentialImageBase64) {
        CredentialImageBase64 = credentialImageBase64;
    }

    public com.xiaohu.fireworkssystem.model.table.HeadImageModel getCredentialImage() {
        return CredentialImage;
    }

    public void setCredentialImage(com.xiaohu.fireworkssystem.model.table.HeadImageModel credentialImage) {
        CredentialImage = credentialImage;
    }

    public com.xiaohu.fireworkssystem.model.table.HeadImageModel getHeadImageModel() {
        return HeadImageModel;
    }

    public void setHeadImageModel(com.xiaohu.fireworkssystem.model.table.HeadImageModel headImageModel) {
        HeadImageModel = headImageModel;
    }
}
