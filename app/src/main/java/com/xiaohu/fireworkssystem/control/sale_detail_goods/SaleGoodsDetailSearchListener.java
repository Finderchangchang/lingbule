package com.xiaohu.fireworkssystem.control.sale_detail_goods;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchProductOrderModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**

 */
public class SaleGoodsDetailSearchListener {
    Context c;
    SaleGoodsDetailSearchView view;
    Utils utils;

    public SaleGoodsDetailSearchListener(SaleGoodsDetailSearchView v, Context context) {
        view = v;
        c = context;
        utils = new Utils(context);
    }

    public void search(final SearchProductOrderModel model) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        utils = new Utils(c);
        String str = "";// "'{\"OrderId\":\"XS20160912000001\"}'";
        str = "'{\"OrderId\":\"" + model.getOrderID() + "\"}'";
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "GetProductOrder", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model1 = gson.fromJson(result, ResultModel.class);
                        ViewProductSaleModel viewProductSaleModel = new ViewProductSaleModel();
                        viewProductSaleModel = gson.fromJson(model1.getData(), ViewProductSaleModel.class);
                        List<ViewSaleRecordModel> bill = new ArrayList<ViewSaleRecordModel>();
                        for (int i = 0; i < viewProductSaleModel.getProductBillOrders().length; i++) {
                            bill.add(viewProductSaleModel.getProductBillOrders()[i]);
                        }
                        view.SaleGoodsDetailSearchView("", bill,viewProductSaleModel);
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        ViewProductSaleModel viewProductSaleModel = new ViewProductSaleModel();
                        List<ViewSaleRecordModel> viewOrderModel = new ArrayList<ViewSaleRecordModel>();
                        view.SaleGoodsDetailSearchView("网络错误", viewOrderModel,viewProductSaleModel);
                    }
                }
        );
    }
}
