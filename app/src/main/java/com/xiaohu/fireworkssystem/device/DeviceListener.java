package com.xiaohu.fireworkssystem.device;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.utils.Utils;

/**
 * Created by Administrator on 2016/11/23.
 */

public class DeviceListener {
    DeviceView view;
    private OkHttpUtils okHttpUtils;
    private String stringIP;

    public DeviceListener(DeviceView v, Context context) {
        view = v;
        Utils utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
    }

    //获取设备信息
    public void getTerminal(String sam) {
        String samYuan = sam;
        sam = sam.substring(0, 4) + samYuan.substring(6, 24);
        System.out.println(sam + ";;;;sam");
        okHttpUtils.requestGetByAsyn(stringIP, "GetTerminal", "'{\"TerminalCode\":\"" + sam + "\"}'", new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                System.out.println("searchwarehouse:" + result);
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);
                view.device(model.isSuccess());
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.device(false);
            }
        });
    }
}
