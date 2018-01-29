package com.xiaohu.fireworkssystem.control.alloutput;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.view.ViewWaitOutputBoxCodeModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2018/1/25.
 */

public class AllOutputDetailListener  {
    private Utils utils;
    private Gson gson;
    private AllOutputDetailView view;
    private OkHttpUtils okHttpUtils;
    private String stringIP;
    private Context mContext;

    public AllOutputDetailListener(AllOutputDetailView v, Context context){
        view = v;
        mContext = context;
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils=new OkHttpUtils(utils.ReadString(KEY_TOKEN));
    }


    public void GetWaitViewAllOutput(String waitOutputCompanyId ){
        String str= "'{\"waitOutputBoxCodeId\":\"" + waitOutputCompanyId + "\"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "GetWaitOutputBoxCodeRecord",str , new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if (model.isSuccess()) {
                           ViewWaitOutputBoxCodeModel viewStockModel = gson.fromJson(model.getData(),ViewWaitOutputBoxCodeModel.class );
                            view.Search("", viewStockModel);

                        }else {
                            ViewWaitOutputBoxCodeModel viewStockModel = new ViewWaitOutputBoxCodeModel();
                            view.Search("", viewStockModel);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        ViewWaitOutputBoxCodeModel viewStockModel = new ViewWaitOutputBoxCodeModel();
                        view.Search("网络错误", viewStockModel);

                    }
                }
        );
    }
}

