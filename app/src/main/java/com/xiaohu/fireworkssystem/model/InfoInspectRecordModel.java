package com.xiaohu.fireworkssystem.model;

/**
 * 作者：zz on 2016/7/3 16:31
 */
public class InfoInspectRecordModel {
    private String InspectID;//检查记录编码
    private String CompanyID;//企业编码
    private String InspectType;//检查部门
    private String InspectionItems;//检查项目
    private String InspectPerson;//检查人
    private String InspectionState;//检查状态
    private String InspectionResult;//检查结果
    private String CreateTime;//创建时间
    private String CreateUserID;//创建用户

    @Override
    public String toString() {
        return "InfoInspectRecordModel{" +
                "CompanyID='" + CompanyID + '\'' +
                ", InspectID='" + InspectID + '\'' +
                ", InspectType='" + InspectType + '\'' +
                ", InspectionItems='" + InspectionItems + '\'' +
                ", InspectPerson='" + InspectPerson + '\'' +
                ", InspectionState='" + InspectionState + '\'' +
                ", InspectionResult='" + InspectionResult + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", CreateUserID='" + CreateUserID + '\'' +
                '}';
    }


    public String getInspectType() {
        return InspectType;
    }

    public void setInspectType(String inspectType) {
        InspectType = inspectType;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateUserID() {
        return CreateUserID;
    }

    public void setCreateUserID(String createUserID) {
        CreateUserID = createUserID;
    }

    public String getInspectID() {
        return InspectID;
    }

    public void setInspectID(String inspectID) {
        InspectID = inspectID;
    }

    public String getInspectionItems() {
        return InspectionItems;
    }

    public void setInspectionItems(String inspectionItems) {
        InspectionItems = inspectionItems;
    }

    public String getInspectionResult() {
        return InspectionResult;
    }

    public void setInspectionResult(String inspectionResult) {
        InspectionResult = inspectionResult;
    }

    public String getInspectionState() {
        return InspectionState;
    }

    public void setInspectionState(String inspectionState) {
        InspectionState = inspectionState;
    }

    public String getInspectPerson() {
        return InspectPerson;
    }

    public void setInspectPerson(String inspectPerson) {
        InspectPerson = inspectPerson;
    }


}
