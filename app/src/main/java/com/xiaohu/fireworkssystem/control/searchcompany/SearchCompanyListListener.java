package com.xiaohu.fireworkssystem.control.searchcompany;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.CompanyModel;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2018/1/22.
 */

public class SearchCompanyListListener  {
    private SearchCompanyListView view;
    private Utils utils;
    private Context mContext;
    private OkHttpUtils okHttpUtils;
    private String stringIP;
    public SearchCompanyListListener(SearchCompanyListView v, Context context) {
        this.view = v;
        mContext = context;
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));

    }

    public void Search(String cardid) {
        String str = "'{\"certNumber\":\"" + cardid + "\"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "GetCompanyByEmplayeeCert", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if(model.isSuccess()) {
                            TypeToken<List<CompanyModel>> list = new TypeToken<List<CompanyModel>>() {};
                            List<CompanyModel> companyModel = new ArrayList<CompanyModel>();
                            companyModel = gson.fromJson(model.getData(), list.getType());
                            List<ViewCompanyModel> companyModels = new ArrayList<ViewCompanyModel>();
                            for (int i = 0 ; i<companyModel.size(); i++){
                                ViewCompanyModel viewCompanyModel = new ViewCompanyModel();
                                viewCompanyModel.setCompanyId( companyModel.get(i).getCompanyId());
                                viewCompanyModel.setCompanyName(companyModel.get(i).getCompanyName());
                                viewCompanyModel.setCompanyType(companyModel.get(i).getCompanyType());
                                companyModels.add(viewCompanyModel);
                            }
                            view.getCompanyList("", companyModels);
                        }else{
                            List<ViewCompanyModel> viewCompanyModel = new ArrayList<ViewCompanyModel>();
                            view.getCompanyList(model.getMessage(), viewCompanyModel);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewCompanyModel> viewCompanyModel = new ArrayList<ViewCompanyModel>();
                        view.getCompanyList("网络错误", viewCompanyModel);
                    }
                }
        );
    }
}
