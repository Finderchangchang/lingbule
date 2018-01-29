package com.xiaohu.fireworkssystem.control.seal;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.PrisonerModel;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2016/9/14.
 */
public class PersonListener {
    private PersonView view;
    private String stringIP = "";
    private Utils utils;
    private Gson gson;

    public PersonListener(PersonView view, Context context) {
        this.view = view;
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        gson = new Gson();
    }

    public void CheckPerson(PrisonerModel prisonerModel) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        /*String str = "'{\"CompanyId\":\"" +utils.ReadString(COMPANYID) +
                "\",\"UserId\":\"" + utils.ReadString(UserID) +
                "\",\"Name\":\"" + personInfo.getName() +
                "\",\"CardNumber\":\"" + personInfo.getCardNumber() +
                "\",\"Nation\":\"" + personInfo.getNation() +
                "\",\"Sex\":\"" + personInfo.getSex() +
                "\",\"Address\":\"" + personInfo.getAddress() +
                "\"}'";*/
        String str = gson.toJson(prisonerModel);
        str = "'"+str+"'";
        System.out.println("alarmcheck:" + str);
        okHttpUtils.requestGetByAsyn(stringIP, "AlarmCheck", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println("alarmcheck:" + result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                     /*   ResultModel m = gson.fromJson(model.getData(), ResultModel.class);*/
                        /*utils.WriteString(ServerDate,m.getTime());*/
                        if (model.isSuccess()) {
                            view.CheckResult(true, model.getMessage(),model.getData());
                        } else {
                            view.CheckResult(false, model.getMessage(),model.getData());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        System.out.println("3e" + errorMsg);
                        view.CheckResult(false, "网络错误","");
                    }
                }
        );

    }
    public void SendVerificationCode(String phonenumber, String GUID , String Reciver ) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));

        String str = "'{\"MobileNumber\":\""+utils.URLEncode(phonenumber)+"\",\"CompanyId\":\""+utils.ReadString(COMPANYID)+"\",\"VerificationId\":\""+utils.URLEncode(GUID)+"\",\"Reciver\":\""+Reciver+"\"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "SendVerificationCode", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                ResultModel model = gson.fromJson(result, ResultModel.class);
/*                ResultModel m = gson.fromJson(model.getData(), ResultModel.class);*/
                if (model.isSuccess()){
                    view.SendVerificationCode(true,model.getMessage());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.SendVerificationCode(false,errorMsg);
            }
        });
    }
    public void CheckVerificationCode(String GUID, String VerificationCode) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        String str = "'{\"VerificationCode\":\""+utils.URLEncode(VerificationCode)+"\",\"VerificationId\":\""+utils.URLEncode(GUID)+"\"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "CheckVerificationCode", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                ResultModel model = gson.fromJson(result, ResultModel.class);
/*                ResultModel m = gson.fromJson(model.getData(), ResultModel.class);*/
                if (model.isSuccess()){
                    view.CheckVerificationCode(true,model.getMessage());
                }else {
                    view.CheckVerificationCode(false,model.getMessage());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.CheckVerificationCode(false,errorMsg);
            }
        });
    }
}
