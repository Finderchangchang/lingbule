package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**
 * 入库单信息 zz
 * Created by Administrator on 2017/7/10.
 */

public class InputWareHouseModel implements Serializable {
    //入库单号
    private String InputWareHouseId;
    //企业编码
    private String CompanyId;
    //目标企业编码
    private String TargetCompanyId;
    //厂家编码
    private String SupplierId;
    //入库类型
    private String InputType;
    //操作人编码
    private String OperatorId;
    //经办人编码
    private String AgentId;
    //法人
    private String Boss;
    //法人证件号码
    private String BossCertNumber;
    //金额
    private float Amount;
    //产品类型数量
    private int ProduceTypeNumber;
    //记录状态
    private String RecordState;
    //创建用户
    private String CreateUser;
    //设备编码
    private String TerminalId;
    //创建时间
    private String CreateTime;
    //备注
    private String Comment;
    //入库详单
    private  WarehouseBillRecordModel[] InputWareHouseRecord;

    public String getJson() {
        String json = "'{\"CompanyId\":\"" + Utils.URLEncode(getCompanyId()) +
                "\",\"OperatorId\":\"" + Utils.URLEncode(getOperatorId()) +
                "\",\"TargetCompanyId\":\"" + Utils.URLEncode(getTargetCompanyId()) +
                "\",\"InputType\":\"" + "02" +
                "\",\"SupplierId\":\"" + Utils.URLEncode(getSupplierId()) +
                "\",\"AgentId\":\"" + Utils.URLEncode(getAgentId()) +
                "\",\"Boss\":\"" + Utils.URLEncode(getBoss()) +
                "\",\"BossCertNumber\":\"" + Utils.URLEncode(getBossCertNumber()) +
                "\",\"Amount\":" + Utils.URLEncode(getAmount()+"") +
                ",\"ProduceTypeNumber\":\"" + Utils.URLEncode(getProduceTypeNumber()+"") +
                "\",\"RecordState\":\"" + Utils.URLEncode(getRecordState()) +
                "\",\"TerminalId\":\"" + Utils.URLEncode(getTerminalId()) +
                "\",\"CreateUser\":\"" + Utils.URLEncode(getCreateUser()) +
                "\",\"CreateTime\":\"" + Utils.URLEncode(getCreateTime()) +
                "\",\"InputWareHouseRecord\":[";
        return json;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getSupplierId() {
        return SupplierId;
    }

    public void setSupplierId(String supplierId) {
        SupplierId = supplierId;
    }

    public String getInputType() {
        return InputType;
    }

    public void setInputType(String inputType) {
        InputType = inputType;
    }

    public String getInputWareHouseId() {
        return InputWareHouseId;
    }

    public void setInputWareHouseId(String inputWareHouseId) {
        InputWareHouseId = inputWareHouseId;
    }

    public WarehouseBillRecordModel[] getInputWareHouseRecord() {
        return InputWareHouseRecord;
    }

    public void setInputWareHouseRecord(WarehouseBillRecordModel[] inputWareHouseRecord) {
        InputWareHouseRecord = inputWareHouseRecord;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getTargetCompanyId() {
        return TargetCompanyId;
    }

    public void setTargetCompanyId(String targetCompanyId) {
        TargetCompanyId = targetCompanyId;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public String getAgentId() {
        return AgentId;
    }

    public void setAgentId(String agentId) {
        AgentId = agentId;
    }

    public String getBoss() {
        return Boss;
    }

    public void setBoss(String boss) {
        Boss = boss;
    }

    public String getBossCertNumber() {
        return BossCertNumber;
    }

    public void setBossCertNumber(String bossCertNumber) {
        BossCertNumber = bossCertNumber;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float amount) {
        Amount = amount;
    }

    public int getProduceTypeNumber() {
        return ProduceTypeNumber;
    }

    public void setProduceTypeNumber(int produceTypeNumber) {
        ProduceTypeNumber = produceTypeNumber;
    }

    public String getRecordState() {
        return RecordState;
    }

    public void setRecordState(String recordState) {
        RecordState = recordState;
    }


    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

}
