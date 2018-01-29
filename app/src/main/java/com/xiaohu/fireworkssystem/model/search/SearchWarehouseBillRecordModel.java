package com.xiaohu.fireworkssystem.model.search;

import java.io.Serializable;

/**
 * 出入库单查询
 * <p>
 * Created by Administrator on 2016/11/5.
 */

public class SearchWarehouseBillRecordModel implements Serializable {
    // 所属辖区
    private String Area;
    //出入库单号
    private String RecordID;
    //企业编码
    private String CompanyID;
    //批发单位编码
    private String ParentCompanyID;
    //企业名称
    private String CompanyName;
    // 厂家名称
    private String SupplierName;
    //记录状态（入库，出库）
    private String RecordType;
    //企业类型
    private String CompanyType;
    //目标企业类型
    private String TargetCompanyType;
    //出入库完成状态（已完成，未完成)
    private String RecordState;
    //目标企业编码
    private String TargetCompanyID;
    //目标企业
    private String TargetCompanyName;
    //登记人
    private String OperatorID;
    //操作人姓名
    private String AgentName;
    //创建时间起
    private String CreateTimeBegin;
    //创建时间止
    private String CreateTimeEnd;

    public String getRecordID() {
        return RecordID;
    }

    public void setRecordID(String recordID) {
        RecordID = recordID;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getParentCompanyID() {
        return ParentCompanyID;
    }

    public void setParentCompanyID(String parentCompanyID) {
        ParentCompanyID = parentCompanyID;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getRecordType() {
        return RecordType;
    }

    public void setRecordType(String recordType) {
        RecordType = recordType;
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

    public String getRecordState() {
        return RecordState;
    }

    public void setRecordState(String recordState) {
        RecordState = recordState;
    }

    public String getTargetCompanyID() {
        return TargetCompanyID;
    }

    public void setTargetCompanyID(String targetCompanyID) {
        TargetCompanyID = targetCompanyID;
    }

    public String getTargetCompanyName() {
        return TargetCompanyName;
    }

    public void setTargetCompanyName(String targetCompanyName) {
        TargetCompanyName = targetCompanyName;
    }

    public String getOperatorID() {
        return OperatorID;
    }

    public void setOperatorID(String operatorID) {
        OperatorID = operatorID;
    }

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
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

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }
}
