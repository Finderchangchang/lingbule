package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ViewWaitOutputBoxCodeModel implements Serializable {
    private String WaitOutputBoxCodeId;
    private String CompanyId;
    private String TargetCompanyId;
    private String OperatorId;
    private String AgentId;
    private String WaitOutputState;
    private String CreateTime;
    private String OutputTime;
    private String Comment;
    private String CompanyName;
    private String TargetCompanyName;
    private String OperatorName;
    private String AgentName;
    private String WaitOutputStateName;
    private List<ViewWaitOutputBoxCodeRecordModel> WaitOutputBoxCodeRecord;

    public String getWaitOutputBoxCodeId() {
        return WaitOutputBoxCodeId;
    }

    public void setWaitOutputBoxCodeId(String waitOutputBoxCodeId) {
        WaitOutputBoxCodeId = waitOutputBoxCodeId;
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

    public String getWaitOutputState() {
        return WaitOutputState;
    }

    public void setWaitOutputState(String waitOutputState) {
        WaitOutputState = waitOutputState;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getOutputTime() {
        return OutputTime;
    }

    public void setOutputTime(String outputTime) {
        OutputTime = outputTime;
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

    public String getWaitOutputStateName() {
        return WaitOutputStateName;
    }

    public void setWaitOutputStateName(String waitOutputStateName) {
        WaitOutputStateName = waitOutputStateName;
    }

    public List<ViewWaitOutputBoxCodeRecordModel> getWaitOutputBoxCodeRecord() {
        return WaitOutputBoxCodeRecord;
    }

    public void setWaitOutputBoxCodeRecord(List<ViewWaitOutputBoxCodeRecordModel> waitOutputBoxCodeRecord) {
        WaitOutputBoxCodeRecord = waitOutputBoxCodeRecord;
    }
}
