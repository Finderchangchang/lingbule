package com.xiaohu.fireworkssystem.control.AddEmployeeSearch;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.EmployeeModel;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.view.ViewCredentialEmployeeModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * 作者：zz on 2016/7/3 16:31
 * 添加从业人员
 */
public class AddEmployeeSearchListener {
    AddEmployeeSearchView view;
    Utils utils;
    Context context;
    String stringIP;
    public AddEmployeeSearchListener(AddEmployeeSearchView v, Context c) {

        view = v;
        context = c;

    }

    public void Search(EmployeeModel model, String strAPI) {
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        String strCompanyID = ",\"CompanyId\":\"" + utils.ReadString(COMPANYID) + "\"";
        String strEmployeeID = ",\"EmployeeId\":\"" + model.getEmployeeId() + "\"";
        String strEmployeeCertType = ",\"EmployeeCertType\":\"" + "11" + "\"";
        String strCreateTime = ",\"CreateTime\":\"" + Utils.getNowDate() + "\"";
        String strLastTime = ",\"LastTime\":\"" + Utils.getNowDate() + "\"";
        String strEmployeeAddress = ",\"EmployeeAddress\":\"" + model.getEmployeeAddress() + "\"";
        String strEmployeeState = "\"EmployeeState\":\"" + model.getEmployeeState() + "\"";
        String strEmployeeType = ",\"EmployeeType\":\"" + model.getEmployeeType() + "\"";
        String strEmployeeName = ",\"EmployeeName\":\"" + model.getEmployeeName() + "\"";
        String strEmployeeNation = ",\"EmployeeNation\":\"" + model.getEmployeeNation() + "\"";
        String strbir = model.getEmployeerCertNumber().substring(6,10)+"-"+model.getEmployeerCertNumber().substring(10,12)+"-"+model.getEmployeerCertNumber().substring(12,14);
        String strEmployeeBirthday = ",\"EmployeeBirthday\":\"" + strbir + "\"";
        String strCreateUser = ",\"CreateUser\":\"" +utils.ReadString("COMPANYROLE") + "\"";
        String strEmployeeSex = ",\"EmployeeSex\":\"" + model.getEmployeeSex() + "\"";
        String strEmployeePostalAddress = ",\"EmployeePostalAddress\":\"" + model.getEmployeeAddress() + "\"";
        String strEmployeerCertNumber = ",\"EmployeerCertNumber\":\"" + model.getEmployeerCertNumber() + "\"";
        String strEmployeeMobilePhone = ",\"EmployeeMobilePhone\":\"" + model.getEmployeeMobilePhone() + "\"";
        String strCredentialId = ",\"CredentialId\":\"" + model.getCredentialId() + "\"";
        String strCredentialEmployeeId = ",\"CredentialEmployeeId\":\"" + model.getCredentialEmployeeId() + "\"";
        String strCredentialType = ",\"CredentialType\":\"" + model.getCredentialType() + "\"";
        String strCredentialVailtyDate = ",\"CredentialVailtyDate\":\"" + model.getCredentialVailtyDate() + "\"";
        String strIssuingAuthority = ",\"IssuingAuthority\":\"" + model.getIssuingAuthority() + "\"";
        String strEmployeeHeadImageBase64 = ",\"EmployeeHeadImageBase64\":\"" + model.getEmployeeHeadImageBase64() + "\"";
        String strCredentialImageBase64 = ",\"CredentialImageBase64\":\"" + model.getCredentialImageBase64() + "\"";
        String str = "'{" + strEmployeeState + strEmployeeType + strEmployeeID + strEmployeeName + strEmployeeSex +
                strEmployeeNation + strEmployeerCertNumber + strEmployeeMobilePhone + strCompanyID + strEmployeeCertType + strEmployeeAddress +
                strCreateTime +strCreateUser+ strLastTime +strCredentialEmployeeId+strCredentialId+strCredentialType+strCredentialVailtyDate+strIssuingAuthority+ strEmployeeHeadImageBase64 +strCredentialImageBase64+ "}'";

        System.out.println("AddEmployee+"+str);


        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        okHttpUtils.requestGetByAsyn(stringIP, "AddEmployee", str, new ReqCallBack() {
            Gson gson = new Gson();
            @Override
            public void onReqSuccess(String result) {
                ResultModel model = gson.fromJson(result, ResultModel.class);
                if (model.isSuccess()) {
                    view.SearchAddEmployee("添加成功" , true);
                } else {
                    view.SearchAddEmployee(model.getMessage() ,false);
                    System.out.println("AddEmployee+"+model.getMessage());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.SearchAddEmployee("网络错误",false);
            }
        });
    }

    public void SearchCredential(String id){
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));

        okHttpUtils.requestGetByAsyn(stringIP, "GetCredentialEmployee", "'{\"CredentialId\":\"" + id + "\"}'", new ReqCallBack() {
            Gson gson = new Gson();
            @Override
            public void onReqSuccess(String result) {
                ResultModel model = gson.fromJson(result, ResultModel.class);
                if (model.isSuccess()) {
                    ViewCredentialEmployeeModel viewCredentialEmployeeModel = new ViewCredentialEmployeeModel();
                    viewCredentialEmployeeModel = gson.fromJson(model.getData(),ViewCredentialEmployeeModel.class);
                    view.SearchEmployeecreId(viewCredentialEmployeeModel , "");
                } else {
                    view.SearchEmployeecreId(null , "");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.SearchEmployeecreId(null , "");
            }
        });
    }
}
