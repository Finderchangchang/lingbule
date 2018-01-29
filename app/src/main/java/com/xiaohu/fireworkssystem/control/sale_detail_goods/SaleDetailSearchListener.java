package com.xiaohu.fireworkssystem.control.sale_detail_goods;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchProductOrderModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**

 */
public class SaleDetailSearchListener {
    Context context;
    SaleDetailSearchView view;
    Utils utils;

    public SaleDetailSearchListener(SaleDetailSearchView v, Context context) {
        view = v;
        context = context;
        utils = new Utils(context);
    }

    public void search(SearchProductOrderModel model) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        String str = "'{\"OrderId\":\"\"}'";
        Utils utils = new Utils(context);
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "GetProductOrder", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if (model.isSuccess()) {
                            TypeToken<List<ViewProductSaleModel>> list = new TypeToken<List<ViewProductSaleModel>>() {
                            };
                            ViewProductSaleModel viewProductOrderModel = new ViewProductSaleModel();
                            viewProductOrderModel = gson.fromJson(model.getData(), ViewProductSaleModel.class);
                            // view.SaleDetailSearchView("查询成功", viewProductOrderModel);
                            List<ViewSaleRecordModel> billlist = new ArrayList<ViewSaleRecordModel>();


                            TypeToken<List<ViewSaleRecordModel>> listee = new TypeToken<List<ViewSaleRecordModel>>() {
                            };
                            //billlist=gson.fromJson(viewProductOrderModel.getProductBillOrders(),listee.getType());
                        } else {
                            Toast.makeText(context, "SSS", Toast.LENGTH_LONG).show();
                        }

                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewProductSaleModel> viewProductOrderModel = new ArrayList<ViewProductSaleModel>();
                        view.SaleDetailSearchView("网络错误", viewProductOrderModel);
                    }
                }
        );
    }
}
