package com.xiaohu.fireworkssystem.control.inputwarehouse;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchWarehouseBillRecordModel;
import com.xiaohu.fireworkssystem.model.view.ViewInputWareHouseModel;
import com.xiaohu.fireworkssystem.model.view.ViewInputWareHouseRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class InputWareHouseDetailListener {
    InputWareHouseDetailView view;
    Context context ;
    public InputWareHouseDetailListener(InputWareHouseDetailView v , Context c){
        view = v;
        context = c ;
    }
    public void Search(SearchWarehouseBillRecordModel model){
        Utils utils = new Utils(context);
        String recordID = "\"InputWareHouseId\":\""+model.getRecordID()+"\"";
       // String companyID = utils.ReadString("companyID");
      //  String str = "'{\"CompanyId\":\""+companyID+"\","+recordID+"}'";
        String str = "'{"+recordID+"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP,"GetInputWareHouse", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("result:"+result);
                ResultModel model = gson.fromJson(result, ResultModel.class);
                List<ViewInputWareHouseRecordModel> viewInputWareHouseRecordModelList = new ArrayList<ViewInputWareHouseRecordModel>();
                if(model.isSuccess()) {
                    ViewInputWareHouseModel viewInputWareHouseModel = gson.fromJson(model.getData(),ViewInputWareHouseModel.class);
                    for (int i = 0; i < viewInputWareHouseModel.getInputWareHouseRecord().length; i++) {
                        viewInputWareHouseRecordModelList.add(viewInputWareHouseModel.getInputWareHouseRecord()[i]);
                    }
                    view.SearchInputWareHouseDetailView("", viewInputWareHouseRecordModelList);
                }else{
                    view.SearchInputWareHouseDetailView(model.getMessage(), viewInputWareHouseRecordModelList);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                List<ViewInputWareHouseRecordModel> viewProductSaleModel = new ArrayList<ViewInputWareHouseRecordModel>();
                view.SearchInputWareHouseDetailView("网络错误", viewProductSaleModel);
            }
        });
    }


}
