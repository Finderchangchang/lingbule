package com.xiaohu.fireworkssystem.control.linregister;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.CompanyModel;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.table.ClientUserModel;
import com.xiaohu.fireworkssystem.utils.Utils;

/**
 * Created by Administrator on 2017/10/10.
 */

public class LinRegisterListener {
    LinRegisterView view;
    Context context;

    public LinRegisterListener(LinRegisterView v, Context context) {
        view = v;
        this.context = context;
    }
    public void Search(CompanyModel model) {
        Utils utils = new Utils(context);
        Gson gson = new Gson();
        String str = gson.toJson(model,CompanyModel.class);
        str = "'"+str+"'";
        System.out.println("注册json"+str);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));

        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "ClientAddCompany", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                System.out.println();
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);
               ClientUserModel clientUserModel = gson.fromJson(model.getData(),ClientUserModel.class);
                if (model.isSuccess()){
                    view.LinSearchView("", clientUserModel);
                } else {
                    view.LinSearchView(model.getMessage(), clientUserModel);
                }
                }


            @Override
            public void onReqFailed(String errorMsg) {
                ClientUserModel companyModel = new ClientUserModel();
                view.LinSearchView("网络错误", companyModel);
            }
        });
    }

}
