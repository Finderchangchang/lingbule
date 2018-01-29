package com.xiaohu.fireworkssystem.control.seal_manager;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchProductOrderModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**

 */
public class SealManagerListener {
    SealManagerView view;

    public SealManagerListener(SealManagerView v){view = v;}


    public void Search(SearchProductOrderModel model, int pageIndex,Context context){
        //销售单号 OrderID 证件号码ApplyercertNumber 创建时间 creattimebegin 结束时间creattimeend
        String strOrderID = "";
        String strApplyerCertNumber = "";
        String strName ="";
        String strCreateTimeBegin ="";
        String strCreateTimeEnd = "";
        if (!("".equals(model.getOrderID()))){
            strOrderID = ",\"OrderID\":\""+model.getOrderID()+"\"";
        }
        if (!("".equals(model.getApplyerCertNumber()))){
            strApplyerCertNumber = ",\"ApplyerCertNumber\":\""+model.getApplyerCertNumber()+"\"";
        }
        if (!("".equals(model.getAppler()))){
            strName = ",\"Applyer\":\""+model.getAppler()+"\"";
        }
        if (!("".equals(model.getCreateTimeBegin()))){
            strCreateTimeBegin = ",\"CreateTimeBegin\":\""+model.getCreateTimeBegin()+"\"";
        }
        if (!("".equals(model.getCreateTimeEnd()))){
            strCreateTimeEnd = ",\"CreateTimeEnd\":\""+model.getCreateTimeEnd()+"\"";
        }
        Utils utils = new Utils(context);
        String companyID = utils.ReadString("companyID");
        String page = ",\"PageIndex\":\""+pageIndex+"\"";
        String str = "'{\"PageSize\":\"20\",\"CompanyId\":\""+companyID+"\""+strOrderID+strApplyerCertNumber+strName+strCreateTimeBegin+strCreateTimeEnd+page+"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP,"SearchProductOrder", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("result:"+result);
                ResultModel model = gson.fromJson(result, ResultModel.class);
                List<ViewProductSaleModel> viewProductSaleModel = new ArrayList<ViewProductSaleModel>();
                if(model.isSuccess()) {
                    TypeToken<List<ViewProductSaleModel>> list = new TypeToken<List<ViewProductSaleModel>>() {
                    };

                    viewProductSaleModel = gson.fromJson(model.getData(), list.getType());
                    view.SealManagerView("", viewProductSaleModel);
                }else{
                    view.SealManagerView(model.getMessage(), viewProductSaleModel);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                List<ViewProductSaleModel> viewProductSaleModel = new ArrayList<ViewProductSaleModel>();
                view.SealManagerView("网络错误", viewProductSaleModel);
            }
        });
    }
}
