package com.xiaohu.fireworkssystem.control.editcompany;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.EditCompanyModel;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2017/10/16.
 */

public class EditCompanyListener {
    EditCompanyView view;
    String stringIP = "";
    Utils utils;
public EditCompanyListener(EditCompanyView v, Context context) {
    view = v;
    utils = new Utils(context);
    stringIP = utils.ReadString("IP");
}
public void Search(EditCompanyModel editCompanyModel){
    OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
    String stringIP = utils.ReadString("IP");
    Gson gson = new Gson();
    String str = gson.toJson(editCompanyModel,EditCompanyModel.class);
    str = "'"+str+"'";
    okHttpUtils.requestGetByAsyn(stringIP, "EditCompany", str, new ReqCallBack() {
        @Override
        public void onReqSuccess(String result) {
            Gson gson = new Gson();
            ResultModel model = gson.fromJson(result, ResultModel.class);
            if (model.isSuccess()) {

                view.SearchEditCompany("", true);
            } else {
                view.SearchEditCompany(model.getMessage(), false);

            }
        }

        @Override
        public void onReqFailed(String errorMsg) {
            view.SearchEditCompany("网络错误", false);
        }
    } );
}
}
