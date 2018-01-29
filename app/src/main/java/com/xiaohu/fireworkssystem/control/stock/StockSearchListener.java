package com.xiaohu.fireworkssystem.control.stock;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewStockModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2016/9/5.
 */
public class StockSearchListener {
    StockSearchView view;
    String stringIP="";
    Utils utils;
    public StockSearchListener(StockSearchView v, Context context) {
        view = v;
         utils = new Utils(context);
        stringIP = utils.ReadString("IP");
    }

    public void Search(SearchStockModel model , int pageIndex) {
        //产品简称ProductAbbreviation 名称ProductName 类型ProductClass 条形码ProductBarCode
        String strProductId = "";
        String strProductName = "";
        String strProductClass = "";
        String strUseClass = "";
        if(model!=null) {

            if (!("".equals(model.getProductId()))) {
                strProductId = ",\"ProductId\":\"" + Utils.URLEncode(model.getProductId()) + "\"";
            }
            if (!("".equals(model.getProductName()))) {
                strProductName = ",\"ProductName\":\"" + Utils.URLEncode(model.getProductName()) + "\"";
            }
            if (!("".equals(model.getProductClass()))) {
                strProductClass = ",\"ProductClass\":\"" + Utils.URLEncode(model.getProductClass()) + "\"";
            }
            if (!("".equals(model.getUseClass()))) {
                strUseClass = ",\"UseClass\":\"" + Utils.URLEncode(model.getUseClass()) + "\"";
            }
        }
        String page = ",\"PageIndex\":\""+pageIndex+"\"";
        String pagesize = ",\"PageSize\":\"20\"";
        String company = utils.ReadString("companyID");
        Gson gson = new Gson();
        String json = gson.toJson(model);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        String str = "'{\"CompanyId\":\""+company+"\""+strProductId+strProductName+strProductClass+strUseClass+page+pagesize+"}'";
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "SearchStock", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if(model.isSuccess()) {
                            System.out.println("stock+"+model.getData());
                            TypeToken<List<ViewStockModel>> list = new TypeToken<List<ViewStockModel>>() {
                            };
                            List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                            viewStockModel = gson.fromJson(model.getData(), list.getType());
                            view.SearchStock("", viewStockModel);
                        }else{
                            List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                            view.SearchStock(model.getMessage(), viewStockModel);
                        }
                    }
                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                        view.SearchStock("网络错误", viewStockModel);
                    }
                }
        );
    }
}
