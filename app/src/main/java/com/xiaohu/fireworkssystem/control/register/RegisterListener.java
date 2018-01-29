package com.xiaohu.fireworkssystem.control.register;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.table.RegisterModel;
import com.xiaohu.fireworkssystem.model.view.ViewRoleModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2016/11/2.
 */

public class RegisterListener {
    private RegisterView view;
    private String stringIP;
    private OkHttpUtils okHttpUtils;

    public RegisterListener(Context context, RegisterView view) {
        this.view = view;
        Utils utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
    }

    //获取用户状态  启用，停用
    public void getRoles(final String string) {
        okHttpUtils.requestGetByAsyn(stringIP, "GetRoles", "", new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);
                if (model.isSuccess()) {
                    TypeToken<List<ViewRoleModel>> list = new TypeToken<List<ViewRoleModel>>() {
                    };
                    List<ViewRoleModel> viewOrderModel = new ArrayList<ViewRoleModel>();
                    viewOrderModel = gson.fromJson(model.getData(), list.getType());
                    for (int i = 0; i < viewOrderModel.size(); i++) {
                        if (viewOrderModel.get(i).getRoleName().contains(string)) {
                            view.RolesResult(viewOrderModel.get(i).getRoleID());
                            break;
                        }
                    }
                } else {
                    view.RolesResult("");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.RolesResult("");
            }
        });
    }

    //注册
    public void Register(RegisterModel model) {
        String json = model.getJson();
        System.out.println("ss" + json);
        okHttpUtils.requestGetByAsyn(stringIP, "Register", json, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                System.out.println("ss" + result);
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);
                RegisterModel registerModel = gson.fromJson(model.getData(), RegisterModel.class);
                if (model.isSuccess()) {
                    view.onResultRegister(model.isSuccess(), model.getMessage(), registerModel.getAuthorizedCode());
                } else {
                    view.onResultRegister(model.isSuccess(), model.getMessage(), "");
                }

            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.onResultRegister(false, "注册失败，请检查网络地址！", "");
            }
        });
    }

}
