package com.xiaohu.fireworkssystem.model.search;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/29.
 */

public class SearchInputWareHouseModel implements Serializable{
    //所属辖区
    private  String Area;
    //出入库单号
    private  String InputWareHouseId;
    //企业编码
    private  String CompanyId;
    //企业名称
    private  String CompanyName;
    //记录状态
    private  String RecordType;
    //企业类型
    private  String CompanyType;
    //目标企业类型
    private  String TargetCompanyType;
    //出入库完成状态
    private  String RecordState;
    //目标企业编码
    private  String TargetCompanyId;
    //目标企业
    private  String TargetCompanyName;
    //登记人
    private  String OperatorName;
    //登记人编码
    private  String OperatorId;
    //操作人姓名
    private  String AgentName;
    //创建时间(起)
    private  String CreateTimeBegin;
    // 创建时间(止)
    private  String CreateTimeEnd;

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getInputWareHouseId() {
        return InputWareHouseId;
    }

    public void setInputWareHouseId(String inputWareHouseId) {
        InputWareHouseId = inputWareHouseId;
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

    public String getTargetCompanyId() {
        return TargetCompanyId;
    }

    public void setTargetCompanyId(String targetCompanyId) {
        TargetCompanyId = targetCompanyId;
    }

    public String getTargetCompanyName() {
        return TargetCompanyName;
    }

    public void setTargetCompanyName(String targetCompanyName) {
        TargetCompanyName = targetCompanyName;
    }

    public String getOperatorName() {
        return OperatorName;
    }

    public void setOperatorName(String operatorName) {
        OperatorName = operatorName;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
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
}
