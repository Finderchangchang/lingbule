package com.xiaohu.fireworkssystem.control.outputwarehouse;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchOutputWareHouseModel;
import com.xiaohu.fireworkssystem.model.view.ViewOutputWareHouseModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class OutputWareHouseListener {
    OutputWareHouseView view;
    Context context ;
    public OutputWareHouseListener(OutputWareHouseView v , Context c){
        view = v;
        context = c ;
    }

    public void Search(SearchOutputWareHouseModel model, int pageIndex){
        Utils utils = new Utils(context);
        String companyID = utils.ReadString("companyID");
        String page = ",\"PageIndex\":\""+pageIndex+"\"";
        String starttime = ",\"CreateTimeBegin\":\""+model.getCreateTimeBegin()+"\"";
        String endtime = ",\"CreateTimeEnd\":\""+model.getCreateTimeEnd()+"\"";
        String str = "'{\"PageSize\":\"15\",\"CompanyId\":\""+companyID+"\""+page+starttime+endtime+"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP,"SearchOutputWareHouse", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("result:"+result);
                ResultModel model = gson.fromJson(result, ResultModel.class);
                List<ViewOutputWareHouseModel> viewProductSaleModel = new ArrayList<ViewOutputWareHouseModel>();
                if(model.isSuccess()) {
                    TypeToken<List<ViewOutputWareHouseModel>> list = new TypeToken<List<ViewOutputWareHouseModel>>() {
                    };

                    viewProductSaleModel = gson.fromJson(model.getData(), list.getType());
                    view.SearchOutputWareHouseView("", viewProductSaleModel);
                }else{
                    view.SearchOutputWareHouseView(model.getMessage(), viewProductSaleModel);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                List<ViewOutputWareHouseModel> viewProductSaleModel = new ArrayList<ViewOutputWareHouseModel>();
                view.SearchOutputWareHouseView("网络错误", viewProductSaleModel);
            }
        });
    }



}
