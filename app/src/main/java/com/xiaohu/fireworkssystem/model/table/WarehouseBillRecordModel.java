package com.xiaohu.fireworkssystem.model.table;


import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;
import java.util.List;
/**
 * 进出库产品信息
 * Created by Administrator on 2016/11/5.
 */

public class WarehouseBillRecordModel implements Serializable {
    //详细记录编码
    private String BillRecordId;
    //出入库单号
    private String RecordId;
    //库存编码
    private String StockId;
    //产品数量
    private String ProductNumber;
    //产品金额
    private float BillRecordAmount;
    //产品编码
    private String ProductId;
    // 出入库箱码信息。
    private List<String> WareHouseRecordBoxCode;

    public String getJson() {
        String json = "{\"StockId\":\"" + Utils.URLEncode(getStockId()) +
                "\",\"ProductNumber\":" + Utils.URLEncode(getProductNumber()) +
                ",\"BillRecordAmount\":" + Utils.URLEncode(getBillRecordAmount()+"") +
                ",\"ProductId\":\"" + Utils.URLEncode(getProductId()) +
                "\",\"WareHouseRecordBoxCode\":[\""+getWareHouseRecordBoxCode().get(0)+"\"]} ";


        return json;
    }

    public String getBillRecordId() {
        return BillRecordId;
    }

    public void setBillRecordId(String billRecordId) {
        BillRecordId = billRecordId;
    }

    public String getRecordId() {
        return RecordId;
    }

    public void setRecordId(String recordId) {
        RecordId = recordId;
    }

    public String getStockId() {
        return StockId;
    }

    public void setStockId(String stockId) {
        StockId = stockId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public List<String> getWareHouseRecordBoxCode() {
        return WareHouseRecordBoxCode;
    }

    public void setWareHouseRecordBoxCode(List<String> wareHouseRecordBoxCode) {
        WareHouseRecordBoxCode = wareHouseRecordBoxCode;
    }

    public String getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(String productNumber) {
        ProductNumber = productNumber;
    }

    public float getBillRecordAmount() {
        return BillRecordAmount;
    }

    public void setBillRecordAmount(float billRecordAmount) {
        BillRecordAmount = billRecordAmount;
    }
}
