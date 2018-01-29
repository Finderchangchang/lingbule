package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * // 入库视图模型
 * Created by Administrator on 2017/6/29.
 */

public class ViewOutputWareHouseModel implements Serializable {
    //出库单号
    private String OutputWareHouseId;
    //企业编码
    private String CompanyId;
    //目标企业编码
    private String TragetCompanyId;
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
    //用户编码
    private String CreateUser;
    //备注
    private String Comment;
    //产品总数量
    private int ProductSum;
    //企业名称
    private String CompanyName;
    //目标企业名称
    private String TargetCompanyName;
    //操作人姓名
    private String OperatorName;
    //登记人姓名
    private String AgentName;
    //记录状态
    private String RecordStateName;
    //用户姓名
    private String UserName;
    //产品类型数量
    private String ProduceTypeNumber;
    //企业类型
    private String CompanyType;
    //目标企业类型
    private String TargetCompanyType;
    ///// 辖区
    private String Area;
    //辖区名称
    private String AreaName;
    //入库产口信息
   private ViewOutputWareHouseRecordModel[] OutputWareHouseRecord;

    public String getOutputWareHouseId() {
        return OutputWareHouseId;
    }

    public void setOutputWareHouseId(String outputWareHouseId) {
        OutputWareHouseId = outputWareHouseId;
    }

    public int getProductSum() {
        return ProductSum;
    }

    public void setProductSum(int productSum) {
        ProductSum = productSum;
    }

    public ViewOutputWareHouseRecordModel[] getOutputWareHouseRecord() {
        return OutputWareHouseRecord;
    }

    public void setOutputWareHouseRecord(ViewOutputWareHouseRecordModel[] outputWareHouseRecord) {
        OutputWareHouseRecord = outputWareHouseRecord;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getTragetCompanyId() {
        return TragetCompanyId;
    }

    public void setTragetCompanyId(String tragetCompanyId) {
        TragetCompanyId = tragetCompanyId;
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

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }


    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
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

    public String getAgentName() {
        return AgentName;
    }

    public void setAgentName(String agentName) {
        AgentName = agentName;
    }

    public String getRecordStateName() {
        return RecordStateName;
    }

    public void setRecordStateName(String recordStateName) {
        RecordStateName = recordStateName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getProduceTypeNumber() {
        return ProduceTypeNumber;
    }

    public void setProduceTypeNumber(String produceTypeNumber) {
        ProduceTypeNumber = produceTypeNumber;
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


}
