package com.xiaohu.fireworkssystem.model.table;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/28.
 */

public class InventoryModel implements Serializable {
    //库存编码
    private String InventoryId;
    //企业编码
    private String CompanyId;
    //操作人编码
    private String OperatorId;
    //审核结果
    private String ApproveResult;
    //审核时间
    private String ApproveTime;
    //审核用户
    private String ApproveUser;
    //盘点状态
    private String InventoryState;
    //盘点原因
    private String InventoryReason;
    //创建时间
    private String CreateTime;
    //创建人
    private String CreateUser;
    //设备编码
    private String TerminalId;
    //盘点详单信息
    private InventoryDetailModel[] InventoryDetailModel;

    public String getInventoryId() {
        return InventoryId;
    }

    public void setInventoryId(String inventoryId) {
        InventoryId = inventoryId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getOperatorId() {
        return OperatorId;
    }

    public void setOperatorId(String operatorId) {
        OperatorId = operatorId;
    }

    public String getApproveResult() {
        return ApproveResult;
    }

    public void setApproveResult(String approveResult) {
        ApproveResult = approveResult;
    }

    public String getApproveTime() {
        return ApproveTime;
    }

    public void setApproveTime(String approveTime) {
        ApproveTime = approveTime;
    }

    public String getApproveUser() {
        return ApproveUser;
    }

    public void setApproveUser(String approveUser) {
        ApproveUser = approveUser;
    }

    public String getInventoryState() {
        return InventoryState;
    }

    public void setInventoryState(String inventoryState) {
        InventoryState = inventoryState;
    }

    public String getInventoryReason() {
        return InventoryReason;
    }

    public void setInventoryReason(String inventoryReason) {
        InventoryReason = inventoryReason;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getCreateUser() {
        return CreateUser;
    }

    public void setCreateUser(String createUser) {
        CreateUser = createUser;
    }

    public String getTerminalId() {
        return TerminalId;
    }

    public void setTerminalId(String terminalId) {
        TerminalId = terminalId;
    }

    public InventoryDetailModel[] getInventoryDetailModel() {
        return InventoryDetailModel;
    }

    public void setInventoryDetailModel(InventoryDetailModel[] inventoryDetailModel) {
        InventoryDetailModel = inventoryDetailModel;
    }
}