package com.xiaohu.fireworkssystem.model.table;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Administrator on 2017/8/4.
 */

public class ActualInventoryDetailModel implements Serializable {
    //仓库编码
    private String StockId;
    //实际库存
    private int ActualProductNumber;
    //盘点状态
    private String ProductState;
    //产品编码
    private String ProductId;
    //产品名称
    private String ProductName;
    //库存数量
    private int StockProductNumber;
    //损益数量
    private int LossProductNumber;
    //盘点箱码
    private OperatorBoxCodeModel[] InventoryBoxCode;
    //
    private OperatorBoxCodeInvetoryModel[] LossBoxCode;

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    public int getActualProductNumber() {
        return ActualProductNumber;
    }

    public void setActualProductNumber(int actualProductNumber) {
        ActualProductNumber = actualProductNumber;
    }

    public String getProductState() {
        return ProductState;
    }

    public void setProductState(String productState) {
        ProductState = productState;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getStockProductNumber() {
        return StockProductNumber;
    }

    public void setStockProductNumber(int stockProductNumber) {
        StockProductNumber = stockProductNumber;
    }

    public int getLossProductNumber() {
        return LossProductNumber;
    }

    public void setLossProductNumber(int lossProductNumber) {
        LossProductNumber = lossProductNumber;
    }

    public OperatorBoxCodeModel[] getInventoryBoxCode() {
        return InventoryBoxCode;
    }

    public void setInventoryBoxCode(OperatorBoxCodeModel[] inventoryBoxCode) {
        InventoryBoxCode = inventoryBoxCode;
    }

    public OperatorBoxCodeInvetoryModel[] getLossBoxCode() {
        return LossBoxCode;
    }

    public void setLossBoxCode(OperatorBoxCodeInvetoryModel[] lossBoxCode) {
        LossBoxCode = lossBoxCode;
    }

    @Override
    public String toString() {
        return "ActualInventoryDetailModel{" +
                "StockId='" + StockId + '\'' +
                ", ActualProductNumber=" + ActualProductNumber +
                ", ProductState='" + ProductState + '\'' +
                ", ProductId='" + ProductId + '\'' +
                ", ProductName='" + ProductName + '\'' +
                ", StockProductNumber=" + StockProductNumber +
                ", LossProductNumber=" + LossProductNumber +
                ", InventoryBoxCode=" + Arrays.toString(InventoryBoxCode) +
                ", LossBoxCode=" + Arrays.toString(LossBoxCode) +
                '}';
    }
}
