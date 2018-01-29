package com.xiaohu.fireworkssystem.control.company;

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
import static com.xiaohu.fireworkssystem.utils.Utils.URLEncode;

/**
 * 企业信息
 * Created by Administrator on 2016/9/23.
 */
public class CompanyListener {
    private CompanySearchView view;
    private OkHttpUtils okHttpUtils;
    private String stringIP;
    private Utils utils;

    public CompanyListener(CompanySearchView v, Context context) {
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils=new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        view = v;
    }

    //企业详细信息
    public void GetCompany(String cid) {
        okHttpUtils.requestGetByAsyn(stringIP, "GetCompany", "'{\"CompanyId\":\"" + cid + "\"}'", new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if (model.isSuccess()) {
                            ViewCompanyModel companyModel = gson.fromJson(model.getData(), ViewCompanyModel.class);

                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        System.out.println("3e" + errorMsg);

                    }
                }
        );
    }

    public void Search(SearchCompanyModel model, int pageIndex) {
        String companyname = ",\"CompanyName\":\""+ URLEncode(model.getCompanyName())+"\"";
        String companytype = ",\"CompanyType\":\""+ URLEncode(model.getCompanyType())+"\"";
        String bossid = ",\"BossCertNumber\":\""+ URLEncode(model.getBossCertNumber())+"\"";
        String boss = ",\"Boss\":\""+ URLEncode(model.getBoss())+"\"";
        String leader = ",\"Leader\":\""+ URLEncode(model.getLeader())+"\"";
        String str = "'{\"CompanyId\":\"" + URLEncode(model.getCompanyID()) + "\",\"PageIndex\":\"" + pageIndex + "\",\"PageSize\":\"15\""+
                companyname+companytype+boss+bossid+leader+"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "SearchCompany", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if(model.isSuccess()) {
                            TypeToken<List<ViewCompanyModel>> list = new TypeToken<List<ViewCompanyModel>>() {
                            };
                            List<ViewCompanyModel> viewCompanyModel = new ArrayList<ViewCompanyModel>();
                            viewCompanyModel = gson.fromJson(model.getData(), list.getType());
                            view.ResultList("", viewCompanyModel);
                        }else{
                            List<ViewCompanyModel> viewCompanyModel = new ArrayList<ViewCompanyModel>();
                            view.ResultList(model.getMessage(), viewCompanyModel);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        view.ResultList("网络错误",null);
                    }
                }
        );
    }
}
