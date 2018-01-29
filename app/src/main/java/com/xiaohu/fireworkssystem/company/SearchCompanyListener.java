package com.xiaohu.fireworkssystem.company;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchCompanyModel;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;
import static com.xiaohu.fireworkssystem.config.MyKeys.ServerDate;

/**
 * Created by Administrator on 2016/11/2.
 */

public class SearchCompanyListener {
    private SearchCompanyView view;
    private String stringIP;
    private OkHttpUtils okHttpUtils;
    private Utils utils;

    public SearchCompanyListener(Context context, SearchCompanyView view) {
        this.view = view;
        utils=new Utils(context);
        Utils utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));

    }

    public void searchCompany(SearchCompanyModel model, int index) {
        String json = "";
        if (model != null) {
            if (!model.getCompanyName().equals("")) {
                json += "\"CompanyName\":\"" + model.getCompanyName() + "\"";
            }
            if (null != model.getParentCompanyID() & (!model.getParentCompanyID().equals(""))) {
                if (!json.equals("")) {
                    json += ",";
                }
                json += "\"ParentCompanyID\":\"" + model.getParentCompanyID() + "\"";
            }
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"CompanyType\":\"" + model.getCompanyType() + "\"";
        }
        String str = "'{\"PageIndex\":\"" + index + "\",\"PageSize\":\"20\"," + json + "}'";
        System.out.println("sssss"+str);
        okHttpUtils.requestGetByAsyn(stringIP, "SearchCompany", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {

                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);
                if (model.isSuccess()) {
                    utils.WriteString(ServerDate,model.getTime());
                    TypeToken<List<ViewCompanyModel>> list = new TypeToken<List<ViewCompanyModel>>() {
                    };
                    List<ViewCompanyModel> viewCompanyModel = new ArrayList<ViewCompanyModel>();
                    viewCompanyModel = gson.fromJson(model.getData(), list.getType());

                    view.SearchResult(viewCompanyModel);
                } else {
                    List<ViewCompanyModel> viewOrderModel = new ArrayList<ViewCompanyModel>();
                    view.SearchResult(viewOrderModel);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                List<ViewCompanyModel> viewOrderModel = new ArrayList<ViewCompanyModel>();
                view.SearchResult(viewOrderModel);
            }
        });

    }
}
