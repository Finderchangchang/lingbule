package com.xiaohu.fireworkssystem.control.companyimage;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * 作者：zz on 2016/7/3 16:31
 */
public class CompanyImageListener {
    CompanyImageSearchView view;
    String stringIP="";
    Context context;
    OkHttpUtils okHttpUtils;
    public CompanyImageListener(CompanyImageSearchView v, Context c) {
        view = v;
        context = c;
        Utils utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));

    }
    public void Search(String companyId) {
        Utils utils = new Utils(context);
        String str = "'{\"CompanyId\":\""+utils.URLEncode(companyId)+"\"}'";
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "GetCompanyImage", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println(result);
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if(model.isSuccess()) {
                            String s = model.getData();
                            s= s.replace("\"","");
                            s= s.replace("[","");
                            s= s.replace("]","");
                           String[] str = s.split(",");
                            /*str[0].replace("\"","");
                            str[0].replace("[","");
                            str[1].replace("\"","");
                            str[1].replace("]","");*/
                            view.SearchImage("",str);
                        }else{

                        }
                    }
                    @Override
                    public void onReqFailed(String errorMsg) {

                    }
                }
        );
    }
}
