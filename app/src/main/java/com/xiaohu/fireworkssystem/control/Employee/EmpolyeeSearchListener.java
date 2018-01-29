package com.xiaohu.fireworkssystem.control.Employee;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchEmployeeModel;
import com.xiaohu.fireworkssystem.model.view.ViewEmployeeModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class EmpolyeeSearchListener {
    EmployeeSearchView view;
    Context context;

    public EmpolyeeSearchListener(EmployeeSearchView v, Context context) {
        view = v;
        this.context = context;
    }


    public void Search(SearchEmployeeModel model, int index) {
        Utils utils = new Utils(context);
        String companyID = Utils.URLEncode(model.getCompanyID());
        String name = Utils.URLEncode(model.getEmployeeName());
        String str = "'{\"PageIndex\":\"" + index + "\",\"PageSize\":\"20\",\"CompanyId\":\"" + companyID + "\",\"EmployeeName\":\"" + name + "\"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));

        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "SearchEmployee", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                System.out.println();
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);
                TypeToken<List<ViewEmployeeModel>> list = new TypeToken<List<ViewEmployeeModel>>() {
                };
                List<ViewEmployeeModel> viewOrderModel = new ArrayList<ViewEmployeeModel>();
                viewOrderModel = gson.fromJson(model.getData(), list.getType());
                view.EmployeeSearchView("", viewOrderModel);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                List<ViewEmployeeModel> viewOrderModel = new ArrayList<ViewEmployeeModel>();
                view.EmployeeSearchView("网络错误", viewOrderModel);
            }
        });
    }
}
