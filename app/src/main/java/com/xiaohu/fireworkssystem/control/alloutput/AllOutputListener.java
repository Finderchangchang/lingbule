package com.xiaohu.fireworkssystem.control.alloutput;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.view.ViewWaitOutputBoxCodeModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * Created by Administrator on 2018/1/24.
 */

public class AllOutputListener {
    private Utils utils;
    private Gson gson;
    private AllOutputView view;
    private OkHttpUtils okHttpUtils;
    private String stringIP;
    private Context mContext;

    public AllOutputListener(AllOutputView v, Context context){
        view = v;
        mContext = context;
        utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils=new OkHttpUtils(utils.ReadString(KEY_TOKEN));
    }


    public void GetWaitViewOutput(String waitOutputCompanyId , int num){
        String str= "'{\"companyId\":\"" + waitOutputCompanyId + "\",\"watiOutputStatus\":\"02\"}'";
        okHttpUtils.requestGetByAsyn(stringIP, "GetWaitOutputBoxCode",str , new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if (model.isSuccess()) {
                            TypeToken<List<ViewWaitOutputBoxCodeModel>> list = new TypeToken<List<ViewWaitOutputBoxCodeModel>>() {
                            };
                            List<ViewWaitOutputBoxCodeModel> viewStockModel = new ArrayList<ViewWaitOutputBoxCodeModel>();
                            viewStockModel = gson.fromJson(model.getData(), list.getType());
                            for (int i = 0 ; i<viewStockModel.size();i++){
                                if (!viewStockModel.get(i).getWaitOutputState().equals("03") ){
                                    viewStockModel.remove(i);
                                    i=i-1;
                                }
                            }
                            view.Search("", viewStockModel);

                        }else {
                            List<ViewWaitOutputBoxCodeModel> viewStockModel = new ArrayList<ViewWaitOutputBoxCodeModel>();
                            view.Search("", viewStockModel);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewWaitOutputBoxCodeModel> viewStockModel = new ArrayList<ViewWaitOutputBoxCodeModel>();
                        view.Search("网络错误", viewStockModel);

                    }
                }
        );
    }
}
