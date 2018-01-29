package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/16.
 * 销售产品信息
 * 作者：zz
 */
public class ProductSaleRecordModel implements Serializable {
    //详单记录编码
    private String ProductSaleRecordId;
    //订单编号
    private String ProductSaleId;
    //产品编号
    private String ProductId;
    //库存编码
    private String StockId;
    //产品数量
    private int ProductNumber;
    //创建时间
    private String ProductSaleTime;
    //产品金额
    private float ProductSaleAmount;
    //记录状态
    private String ProductSaleState;
    //销售箱码信息
    private  OperatorBoxCodeModel[] SaleRecordBoxCode;

    /*
    *  "ProductBillOrders": [
        {
            "ProductSaleRecordId": null,
            "ProductSaleId": null,
            "StockId": "595462A10E41DE259C88B3E1",
            "ProductNumber": 2,
            "ProductSaleTime": "2016-09-10 00:00:00",
            "ProductSaleAmount": 246,
            "ProductSaleState": "01",
            "SaleRecordBoxCode": [
                {
                    "BoxCode": "A00000790",
                    "Amount": 1
                }
            ]
        }
    ],
    "ApplyerHeadImage": null
}*/
    public String getJson() {
        String json = "{\"StockId\":\"" + Utils.URLEncode(getStockId()) +
                "\",\"ProductNumber\":" + Utils.URLEncode(getProductNumber()+"") +
                ",\"ProductSaleId\":\"" + Utils.URLEncode(getProductSaleId()) +
                "\",\"ProductSaleRecordId\":\"" + Utils.URLEncode(getProductSaleRecordId()) +
                "\",\"ProductSaleState\":\"" + Utils.URLEncode(getProductSaleState()) +
                "\",\"ProductSaleTime\":\"" + Utils.getNowDate() +
                "\",\"ProductSaleAmount\":" + Utils.URLEncode(getProductSaleAmount()+"") +
                ",\"SaleRecordBoxCode\":[";


        return json;
    }

    public String getProductSaleRecordId() {
        return ProductSaleRecordId;
    }

    public void setProductSaleRecordId(String productSaleRecordId) {
        ProductSaleRecordId = productSaleRecordId;
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

    public String getProductSaleTime() {
        return ProductSaleTime;
    }

    public void setProductSaleTime(String productSaleTime) {
        ProductSaleTime = productSaleTime;
    }

    public float getProductSaleAmount() {
        return ProductSaleAmount;
    }

    public void setProductSaleAmount(float productSaleAmount) {
        ProductSaleAmount = productSaleAmount;
    }

    public String getProductSaleState() {
        return ProductSaleState;
    }

    public void setProductSaleState(String productSaleState) {
        ProductSaleState = productSaleState;
    }

    public OperatorBoxCodeModel[] getSaleRecordBoxCode() {
        return SaleRecordBoxCode;
    }

    public void setSaleRecordBoxCode(OperatorBoxCodeModel[] saleRecordBoxCode) {
        SaleRecordBoxCode = saleRecordBoxCode;
    }

    public String getProductSaleId() {
        return ProductSaleId;
    }

    public void setProductSaleId(String productSaleId) {
        ProductSaleId = productSaleId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }
}
