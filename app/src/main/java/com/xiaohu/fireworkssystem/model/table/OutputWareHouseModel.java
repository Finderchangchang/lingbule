package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**
 * 出库单信息 zz
 * Created by Administrator on 2017/7/10.
 */

public class OutputWareHouseModel implements Serializable {
    //出入库单号
    private String OutputWareHouseId;
    //企业编码
    private String CompanyId;
    //目标企业编码
    private String TargetCompanyId;
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
    private String CreateUserId;
    //设备编码
    private String TerminalId;
    //创建时间
    private String CreateTime;
    //备注
    private String Comment;
    //出库详单
    private  WarehouseBillRecordModel[] OutputWareHouseRecord;

    public String getJson() {
        String json = "'{\"CompanyId\":\"" + Utils.URLEncode(getCompanyId()) +
                "\",\"OperatorId\":\"" + Utils.URLEncode(getOperatorId()) +
                "\",\"TargetCompanyId\":\"" + Utils.URLEncode(getTargetCompanyId()) +
                "\",\"AgentId\":\"" + Utils.URLEncode(getAgentId()) +
                "\",\"Boss\":\"" + Utils.URLEncode(getBoss()) +
                "\",\"BossCertNumber\":\"" + Utils.URLEncode(getBossCertNumber()) +
                "\",\"Amount\":" + Utils.URLEncode(getAmount()+"") +
                ",\"ProduceTypeNumber\":\"" + Utils.URLEncode(getProduceTypeNumber()+"") +
                "\",\"RecordState\":\"" + Utils.URLEncode(getRecordState()) +
                "\",\"CreateUserId\":\"" + Utils.URLEncode(getCreateUserId()) +
                "\",\"TerminalId\":\"" + Utils.URLEncode(getTerminalId()) +
                "\",\"CreateTime\":\"" + Utils.URLEncode(getCreateTime()) +
                "\",\"OutputWareHouseRecord\":[";
        return json;
    }



    public String getOutputWareHouseId() {
        return OutputWareHouseId;
    }

    public void setOutputWareHouseId(String outputWareHouseId) {
        OutputWareHouseId = outputWareHouseId;
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

    public String getCreateUserId() {
        return CreateUserId;
    }

    public void setCreateUserId(String createUserId) {
        CreateUserId = createUserId;
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

    public WarehouseBillRecordModel[] getOutputWareHouseRecord() {
        return OutputWareHouseRecord;
    }

    public void setOutputWareHouseRecord(WarehouseBillRecordModel[] outputWareHouseRecord) {
        OutputWareHouseRecord = outputWareHouseRecord;
    }
}
