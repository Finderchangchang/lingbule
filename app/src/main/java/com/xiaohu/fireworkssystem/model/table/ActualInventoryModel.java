package com.xiaohu.fireworkssystem.model.table;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/4.
 */

public class ActualInventoryModel implements Serializable {
    //企业编码
    private String CompanyId;
    //操作人编码
    private String OperatorId;
    //盘点状态
    private String InventoryState;
    //盘点原因
    private String InventoryReason;
    //创建时间
    private String CreateTime;
    //盘点产品
    private ActualInventoryDetailModel[] InventoryDetail;

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

    public ActualInventoryDetailModel[] getInventoryDetail() {
        return InventoryDetail;
    }

    public void setInventoryDetail(ActualInventoryDetailModel[] inventoryDetail) {
        InventoryDetail = inventoryDetail;
    }
}
