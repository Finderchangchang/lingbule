package com.xiaohu.fireworkssystem.control.CompanyEmployee;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchEmployeeModel;
import com.xiaohu.fireworkssystem.model.view.ViewEmployeeModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * 作者：zz on 2016/7/3 16:31
 */
public class CompanyEmployeeSearchListener {
    CompanyEmployeeSearchView view;
    String stringIP = "";
    Utils utils;

    public CompanyEmployeeSearchListener(CompanyEmployeeSearchView v, Context context) {
        view = v;
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
    }

    public void Search(SearchEmployeeModel model, int pageIndex) {
        String strEmployeeName = "";
        String strEmployeerCertNumber = "";
        String strEmployeeType = "";
        String strEmployeeState = "";
        String strCompanyId = ",\"CompanyId\":\"" + utils.ReadString("companyID") + "\"";
        if (model != null) {

            if (!("".equals(model.getEmployeeName()))) {
                strEmployeeName = ",\"EmployeeName\":\"" + model.getEmployeeName() + "\"";
            }
            if (!("".equals(model.getEmployeerCertNumber()))) {
                strEmployeerCertNumber = ",\"EmployeerCertNumber\":\"" + model.getEmployeerCertNumber() + "\"";
            }
            if (!("".equals(model.getEmployeeType()))) {
                strEmployeeType = ",\"EmployeeType\":\"" + model.getEmployeeType() + "\"";
            }
            if (!("".equals(model.getEmployeeState()))) {
                strEmployeeState = ",\"EmployeeState\":\"" + model.getEmployeeState() + "\"";
            }
        }
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        String str = "'{\"PageIndex\":\"" + pageIndex + "\",\"PageSize\":\"20\"" + strEmployeeName + strEmployeerCertNumber + strEmployeeState + strEmployeeType + strCompanyId + "}'";
        System.out.println("search:" + str);
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "SearchEmployee", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println("1===" + result);
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if (model.isSuccess()) {
                            TypeToken<List<ViewEmployeeModel>> list = new TypeToken<List<ViewEmployeeModel>>() {
                            };
                            List<ViewEmployeeModel> viewStockModel = new ArrayList<ViewEmployeeModel>();
                            viewStockModel = gson.fromJson(model.getData(), list.getType());
                            view.SearchCompanyEmployee("", viewStockModel);
                        } else {
                            List<ViewEmployeeModel> viewStockModel = new ArrayList<ViewEmployeeModel>();
                            view.SearchCompanyEmployee(model.getMessage(), viewStockModel);

                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewEmployeeModel> viewStockModel = new ArrayList<ViewEmployeeModel>();
                        view.SearchCompanyEmployee("网络错误", viewStockModel);
                    }
                }
        );
    }
}
