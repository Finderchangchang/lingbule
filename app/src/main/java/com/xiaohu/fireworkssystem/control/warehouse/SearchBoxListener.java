package com.xiaohu.fireworkssystem.control.warehouse;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2017/9/28.
 */

public class SearchBoxListener {
    SearchBoxView view;
    String stringIP = "";
    Utils utils;
    public SearchBoxListener(SearchBoxView view, Context context){
        this.view = view;
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
    }
    public void Searchbox(String startBoxCode, String endBoxCode, String companyId) {
        String str = "'{\"StartBoxCode\":\""+startBoxCode+"\",\"EndBoxCode\":\""+endBoxCode+"\",\"CompanyId\":\""+companyId+"\"}'";
        System.out.println("Searchbox:"+str);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        okHttpUtils.requestGetByAsyn(stringIP, "GetBoxStocks", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        System.out.println("result:"+result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        List<ViewBoxCodeStockModel> viewProductSaleModel = new ArrayList<ViewBoxCodeStockModel>();
                        if(model.isSuccess()) {
                            TypeToken<List<ViewBoxCodeStockModel>> list = new TypeToken<List<ViewBoxCodeStockModel>>() {
                            };

                            viewProductSaleModel = gson.fromJson(model.getData(), list.getType());

                            view.SearchBox(viewProductSaleModel,"");
                            System.out.println(""+viewProductSaleModel.toArray().toString());
                        }else{
                            view.SearchBox(viewProductSaleModel ,model.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewBoxCodeStockModel> viewProductSaleModel = new ArrayList<ViewBoxCodeStockModel>();
                        view.SearchBox(viewProductSaleModel,"网络错误");
                    }
                }
        );
    }

}
