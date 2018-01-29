package com.xiaohu.fireworkssystem.model.table;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/28.
 */

public class InventoryDetailModel implements Serializable {
    //详细编码
    private String DetailId;
    //盘点编码
    private String InventoryId;
    //库存产品编码
    private String StockId;
    //数量
    private int ProductNumber;
    //损益状态
    private String LossState;
    //箱码
    private OperatorBoxCodeInvetoryModel[] Boxs;
    //产品编码
    private String ProductId;

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getDetailId() {
        return DetailId;
    }

    public void setDetailId(String detailId) {
        DetailId = detailId;
    }

    public String getInventoryId() {
        return InventoryId;
    }

    public void setInventoryId(String inventoryId) {
        InventoryId = inventoryId;
    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public String getLossState() {
        return LossState;
    }

    public void setLossState(String lossState) {
        LossState = lossState;
    }

    public OperatorBoxCodeInvetoryModel[] getBoxs() {
        return Boxs;
    }

    public void setBoxs(OperatorBoxCodeInvetoryModel[] boxs) {
        Boxs = boxs;
    }
}
