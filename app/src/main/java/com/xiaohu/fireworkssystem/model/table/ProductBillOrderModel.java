package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/16.
 * 产品详细记录表
 */
public class ProductBillOrderModel implements Serializable {
    //详单记录编码
    private String BillOrderID;
    //订单编号
    private String OrderID;
    //库存编码
    private String StockID;
    //产品数量
    private int ProductNumber;
    //产品金额
    private String BillRecordAmount;
    //创建时间
    private String OrderTime;
    //产品金额
    private String BillOrderAmount;
    //记录状态
    private String BillOrderState;

    public String getJson() {
        String json = "{\"StockID\":\"" + Utils.URLEncode(getStockID()) +
                "\",\"OrderID\":\"" + Utils.URLEncode(getOrderID() + "") +
                "\",\"ProductNumber\":\"" + Utils.URLEncode(getProductNumber() + "") +
                "\",\"BillOrderState\":\"" + Utils.URLEncode(getBillOrderState()) +
                "\",\"OrderTime\":\"" + Utils.getNOWTime() +
                "\",\"BillOrderAmount\":\"" + Utils.URLEncode(getBillOrderAmount()) +
                "\"}";


        return json;
    }

    public String getBillOrderAmount() {
        return BillOrderAmount;
    }

    public void setBillOrderAmount(String billOrderAmount) {
        BillOrderAmount = billOrderAmount;
    }

    public String getBillOrderState() {
        return BillOrderState;
    }

    public void setBillOrderState(String billOrderState) {
        BillOrderState = billOrderState;
    }

    public String getBillRecordAmount() {
        return BillRecordAmount;
    }

    public void setBillRecordAmount(String billRecordAmount) {
        BillRecordAmount = billRecordAmount;
    }

    public String getBillOrderID() {
        return BillOrderID;
    }

    public void setBillOrderID(String billOrderID) {
        BillOrderID = billOrderID;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getStockID() {
        return StockID;
    }

    public void setStockID(String stockID) {
        StockID = stockID;
    }

    public int getProductNumber() {
        return ProductNumber;
    }

    public void setProductNumber(int productNumber) {
        ProductNumber = productNumber;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }
}
