package com.xiaohu.fireworkssystem.control.inputwarehouse;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.search.SearchInputWareHouseModel;
import com.xiaohu.fireworkssystem.model.view.ViewInputWareHouseModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public class InputWareHouseListener {
    InputWareHouseView view;
    Context context ;
    public InputWareHouseListener(InputWareHouseView v , Context c){
        view = v;
        context = c ;
    }

    public void Search(SearchInputWareHouseModel model, int pageIndex){
        Utils utils = new Utils(context);
        String companyId = utils.ReadString("companyID");
        String page = ",\"PageIndex\":\""+pageIndex+"\"";
        String starttime = ",\"CreateTimeBegin\":\""+model.getCreateTimeBegin()+"\"";
        String endtime = ",\"CreateTimeEnd\":\""+model.getCreateTimeEnd()+"\"";
        String str = "'{\"PageSize\":\"20\",\"TargetCompanyId\":\""+companyId+"\""+page+starttime+endtime+"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP,"SearchInputWareHouse", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("result:"+result);
                ResultModel model = gson.fromJson(result, ResultModel.class);
                List<ViewInputWareHouseModel> viewProductSaleModel = new ArrayList<ViewInputWareHouseModel>();
                if(model.isSuccess()) {
                    TypeToken<List<ViewInputWareHouseModel>> list = new TypeToken<List<ViewInputWareHouseModel>>() {
                    };

                    viewProductSaleModel = gson.fromJson(model.getData(), list.getType());
                    view.SearchInputWareHouseView("", viewProductSaleModel);
                }else{
                    view.SearchInputWareHouseView(model.getMessage(), viewProductSaleModel);
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                List<ViewInputWareHouseModel> viewProductSaleModel = new ArrayList<ViewInputWareHouseModel>();
                view.SearchInputWareHouseView("网络错误", viewProductSaleModel);
            }
        });
    }



}
