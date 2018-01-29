package com.xiaohu.fireworkssystem.model.table;

import java.io.Serializable;

/**
 * 出入库单
 * Created by Administrator on 2016/11/5.
 */

public class WarehouseRecordModel implements Serializable {
    //出入库单号
    private String RecoredID;
    //直营公司编码
    private String ParentCompanyID;
    //公司编码
    private String CompanyID;
    //目标企业编码
    private String TargetCompanyID;
    //登记人编码
    private String OperatorID;
    //操作人编码
    private String AgentID;
    //金额
    private String Amount;
    //创建时间
    private String CreateTime;
    //记录状态
    private String RecordState;
    //记录类型
    private String RecordType;
    //创建用户
    private String UserID;
    //备注
    private String Comment;
    //出库单号
    private String OutRecordID;
    //产品类型数量
    private String ProduceTypeNumber;
    private WarehouseBillRecordModel[] warehouseBillRecordModels;

    public String getRecoredID() {
        return RecoredID;
    }

    public void setRecoredID(String recoredID) {
        RecoredID = recoredID;
    }

    public String getParentCompanyID() {
        return ParentCompanyID;
    }

    public void setParentCompanyID(String parentCompanyID) {
        ParentCompanyID = parentCompanyID;
    }

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getTargetCompanyID() {
        return TargetCompanyID;
    }

    public void setTargetCompanyID(String targetCompanyID) {
        TargetCompanyID = targetCompanyID;
    }

    public String getOperatorID() {
        return OperatorID;
    }

    public void setOperatorID(String operatorID) {
        OperatorID = operatorID;
    }

    public String getAgentID() {
        return AgentID;
    }

    public void setAgentID(String agentID) {
        AgentID = agentID;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getRecordState() {
        return RecordState;
    }

    public void setRecordState(String recordState) {
        RecordState = recordState;
    }

    public String getRecordType() {
        return RecordType;
    }

    public void setRecordType(String recordType) {
        RecordType = recordType;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getOutRecordID() {
        return OutRecordID;
    }

    public void setOutRecordID(String outRecordID) {
        OutRecordID = outRecordID;
    }

    public String getProduceTypeNumber() {
        return ProduceTypeNumber;
    }

    public void setProduceTypeNumber(String produceTypeNumber) {
        ProduceTypeNumber = produceTypeNumber;
    }

    public WarehouseBillRecordModel[] getWarehouseBillRecordModels() {
        return warehouseBillRecordModels;
    }

    public void setWarehouseBillRecordModels(WarehouseBillRecordModel[] warehouseBillRecordModels) {
        this.warehouseBillRecordModels = warehouseBillRecordModels;
    }
}
