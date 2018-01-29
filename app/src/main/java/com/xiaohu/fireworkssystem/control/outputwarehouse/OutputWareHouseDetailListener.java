package com.xiaohu.fireworkssystem.control.outputwarehouse;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchWarehouseBillRecordModel;
import com.xiaohu.fireworkssystem.model.view.ViewOutputWareHouseModel;
import com.xiaohu.fireworkssystem.model.view.ViewOutputWareHouseRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class OutputWareHouseDetailListener {
    OutputWareHouseDetailView view;
    Context context ;
    public OutputWareHouseDetailListener(OutputWareHouseDetailView v , Context c){
        view = v;
        context = c ;
    }
    public void Search(SearchWarehouseBillRecordModel model){
        Utils utils = new Utils(context);
       // String companyID = utils.ReadString("companyID");
        String recordID = "\"OutputWareHouseId\":\""+model.getRecordID()+"\"";
        String str = "'{"+recordID+"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP,"GetOutputWareHouse", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("result:"+result);
                ResultModel model = gson.fromJson(result, ResultModel.class);
                List<ViewOutputWareHouseRecordModel> viewInputWareHouseRecordModelList = new ArrayList<ViewOutputWareHouseRecordModel>();
                if(model.isSuccess()) {
                    ViewOutputWareHouseModel viewInputWareHouseModel = gson.fromJson(model.getData(),ViewOutputWareHouseModel.class);
                    for (int i = 0; i < viewInputWareHouseModel.getOutputWareHouseRecord().length; i++) {
                        viewInputWareHouseRecordModelList.add(viewInputWareHouseModel.getOutputWareHouseRecord()[i]);
                    }
                    view.SearchOutputWareHouseDetailView("", viewInputWareHouseRecordModelList);
                }else{
                    view.SearchOutputWareHouseDetailView(model.getMessage(), viewInputWareHouseRecordModelList);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                List<ViewOutputWareHouseRecordModel> viewProductSaleModel = new ArrayList<ViewOutputWareHouseRecordModel>();
                view.SearchOutputWareHouseDetailView("网络错误", viewProductSaleModel);
            }
        });
    }


}
