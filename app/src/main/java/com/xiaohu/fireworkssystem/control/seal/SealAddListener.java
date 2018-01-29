package com.xiaohu.fireworkssystem.control.seal;

import android.content.Context;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.table.ActualInventoryModel;
import com.xiaohu.fireworkssystem.model.table.InputWareHouseModel;
import com.xiaohu.fireworkssystem.model.table.InventoryModel;
import com.xiaohu.fireworkssystem.model.table.OperatorBoxCodeModel;
import com.xiaohu.fireworkssystem.model.table.OutputWareHouseModel;
import com.xiaohu.fireworkssystem.model.table.ProductBillOrderModel;
import com.xiaohu.fireworkssystem.model.table.ProductSaleModel;
import com.xiaohu.fireworkssystem.model.table.ProductSaleRecordModel;
import com.xiaohu.fireworkssystem.model.table.WarehouseBillRecordModel;
import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.model.view.ViewStockModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.ServerDate;

/**
 * Created by Administrator on 2016/9/2.
 */
public class SealAddListener {
    SealAddView view;
    List<ProductBillOrderModel> billList;
    List<ProductSaleRecordModel> productSaleRecordModelList;
    String json = "";
    String stringIP = "";
    String stringCompanyID = "";
    Utils utils;
    Gson gson = new Gson();

    public SealAddListener(SealAddView v , Context context) {
        this.view = v;

        this.utils = new Utils(context);
        stringIP = utils.ReadString("IP");
        stringCompanyID = utils.ReadString("companyID");
    }

    public void SearchGoodsByBarCode(final String code,String companyId) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));

        String str = "'{\"CompanyId\":\"" + companyId + "\",\"BoxCode\":\"" + code + "\"}'";
        System.out.println("result:" + str);

        okHttpUtils.requestGetByAsyn(stringIP, "GetBoxStock", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println("result:" + result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);

                        if (model.isSuccess()) {
                            ViewBoxCodeStockModel boxCodeStockModel = new ViewBoxCodeStockModel();
                            boxCodeStockModel = gson.fromJson(model.getData(),ViewBoxCodeStockModel.class);
                            if (null != boxCodeStockModel) {
                                view.SearchGoods("",boxCodeStockModel);
                            } else {
                                view.SearchGoods("未搜索到该产品！",boxCodeStockModel);
                            }
                        } else {
                            ViewBoxCodeStockModel boxCodeStockModel = new ViewBoxCodeStockModel();
                            view.SearchGoods(model.getMessage(),boxCodeStockModel);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        ViewStockModel viewStockModel = new ViewStockModel();
                        ViewBoxCodeStockModel boxCodeStockModel = new ViewBoxCodeStockModel();
                        view.SearchGoods("网络错误",boxCodeStockModel);
                    }
                }
        );
    }
    //整箱扫描
    public void SearchAllGoodsByBarCode(final String code,String companyId) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));

        String str = "'{\"CompanyId\":\"" + companyId + "\",\"BoxCode\":\"" + code + "\"}'";
        System.out.println("result:" + str);

        okHttpUtils.requestGetByAsyn(stringIP, "GetBoxStock", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println("result:" + result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);

                        if (model.isSuccess()) {
                            ViewBoxCodeStockModel boxCodeStockModel = new ViewBoxCodeStockModel();
                            boxCodeStockModel = gson.fromJson(model.getData(),ViewBoxCodeStockModel.class);
                            if (null != boxCodeStockModel) {
                                view.SearchAllGoods("查询成功",boxCodeStockModel);
                            } else {
                                view.SearchAllGoods("未搜索到该产品！",boxCodeStockModel);
                            }
                        } else {
                            ViewBoxCodeStockModel boxCodeStockModel = new ViewBoxCodeStockModel();
                            view.SearchAllGoods(model.getMessage(),boxCodeStockModel);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        ViewStockModel viewStockModel = new ViewStockModel();
                        ViewBoxCodeStockModel boxCodeStockModel = new ViewBoxCodeStockModel();
                        view.SearchAllGoods("网络错误",boxCodeStockModel);
                    }
                }
        );
    }
    //保存
    public void add(ProductSaleModel model,  List<ViewBoxCodeStockModel> viewBoxCodeStockModels) {
        //将ViewStockModel转换为ProductBillOrderModel
        model.setCreateTime(utils.ReadString(ServerDate));

     /*   Gson gson = new Gson();
       String jsons = gson.toJson(model);
        String q = jsons;*/
        model.setCreateUser(utils.ReadString("COMPANYROLE"));
        json = model.getJson();
        List<ProductSaleRecordModel> productSaleRecordModels = new ArrayList<>();
        List<OperatorBoxCodeModel> operatorBoxCodeModels = new ArrayList<>();
         for (int i = 0; i<viewBoxCodeStockModels.size(); i++){
             ProductSaleRecordModel productSaleRecordModel = new ProductSaleRecordModel();
             productSaleRecordModel.setProductSaleAmount(viewBoxCodeStockModels.get(i).getMyNumber()*viewBoxCodeStockModels.get(i).getProductRetailPrice());
             productSaleRecordModel.setProductNumber(viewBoxCodeStockModels.get(i).getMyNumber());
             productSaleRecordModel.setProductId(viewBoxCodeStockModels.get(i).getProductId());
             productSaleRecordModel.setStockId(viewBoxCodeStockModels.get(i).getStockId());
             productSaleRecordModels.add(productSaleRecordModel);

             OperatorBoxCodeModel operatorBoxCodeModel = new OperatorBoxCodeModel();
             operatorBoxCodeModel.setBoxCode(viewBoxCodeStockModels.get(i).getBoxCode());
             operatorBoxCodeModel.setAmount(viewBoxCodeStockModels.get(i).getMyNumber());
             operatorBoxCodeModels.add(operatorBoxCodeModel);
        }
        getChangeModel(productSaleRecordModels,operatorBoxCodeModels);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));

        //json = json.replace("/", "%2F").replace("=", "%3D").replace("+", " ");
        System.out.println(json);
        okHttpUtils.requestGetByAsyn(stringIP, "AddProductOrder", json, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println(result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        //String orderid = result.substring(24,40);
                        if (model.isSuccess()) {
                            if (model.getData() == null) {
                                view.addResult(false, model.getMessage());
                            } else {
                                view.addResult(true, model.getData().replace("\"\"", ""));
                            }

                        } else {
                            view.addResult(false, model.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                        System.out.println("3e" + errorMsg);
                        view.addResult(false, "网络错误");
                    }
                }
        );

    }


    //出库保存
    public void addOutWarehouse(OutputWareHouseModel model, List<ViewBoxCodeStockModel> viewBoxCodeStockModels) {
        //将ViewStockModel转换为ProductBillOrderModel
        model.setCreateTime(utils.ReadString(ServerDate));
        model.setProduceTypeNumber(1);
        model.setRecordState("01");
       // model.setCreateUserId("13010300010002");
        //model.setTerminalId("5860BAC1F64BF24090C535B0");
      //  model.setCreateTime(Utils.getNowDate());
        model.setComment("无");



     /*   Gson gson = new Gson();
       String jsons = gson.toJson(model);
        String q = jsons;*/
        json = model.getJson();
        List<WarehouseBillRecordModel> warehouseBillRecordModels = new ArrayList<>();
        List<String> list_wareHouseRecordBoxCode = new ArrayList<>();
        for (int i = 0; i<viewBoxCodeStockModels.size(); i++){
            WarehouseBillRecordModel warehouseBillRecordModel = new WarehouseBillRecordModel();

            warehouseBillRecordModel.setProductId(viewBoxCodeStockModels.get(i).getProductId());
            warehouseBillRecordModel.setProductNumber(viewBoxCodeStockModels.get(i).getProductNumber()+"");
            warehouseBillRecordModel.setBillRecordAmount(viewBoxCodeStockModels.get(i).getProductRetailPrice()*viewBoxCodeStockModels.get(i).getProductNumber());
            warehouseBillRecordModel.setStockId(viewBoxCodeStockModels.get(i).getStockId());

            warehouseBillRecordModels.add(warehouseBillRecordModel);

            String boxcode = "";
            boxcode = viewBoxCodeStockModels.get(i).getBoxCode();
            list_wareHouseRecordBoxCode.add(boxcode);
        }
        getChangeModel3(warehouseBillRecordModels,list_wareHouseRecordBoxCode);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));

        //json = json.replace("/", "%2F").replace("=", "%3D").replace("+", " ");
        System.out.println(json);
        okHttpUtils.requestGetByAsyn(stringIP, "AddOutputWareHouse", json, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println(result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        //String orderid = result.substring(24,40);
                        if (model.isSuccess()) {
                            if (model.getData() == null) {
                                view.addResult(true, "出库成功");

                            } else {
                                view.addResult(false, model.getMessage());
                            }

                        } else {
                            view.addResult(false, model.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                        System.out.println("3e" + errorMsg);
                        view.addResult(false, "网络错误");
                    }
                }
        );

    }

    //退货入库保存
    public void addInWarehouse(InputWareHouseModel model, List<ViewBoxCodeStockModel> viewBoxCodeStockModels) {
        //将ViewStockModel转换为ProductBillOrderModel
        model.setCreateTime(utils.ReadString(ServerDate));


        model.setProduceTypeNumber(1);
        model.setRecordState("01");
       // model.setCreateUser("13010300010002");
        //+model.setTerminalId("5860BAC1F64BF24090C535B0");
       // model.setCreateTime("2017-07-13 09:37:20");
        model.setComment("无");



     /*   Gson gson = new Gson();
       String jsons = gson.toJson(model);
        String q = jsons;*/
        json = model.getJson();
        List<WarehouseBillRecordModel> warehouseBillRecordModels = new ArrayList<>();
        List<String> list_wareHouseRecordBoxCode = new ArrayList<>();
        for (int i = 0; i<viewBoxCodeStockModels.size(); i++){
            WarehouseBillRecordModel warehouseBillRecordModel = new WarehouseBillRecordModel();

            warehouseBillRecordModel.setProductId(viewBoxCodeStockModels.get(i).getProductId());
            warehouseBillRecordModel.setProductNumber(viewBoxCodeStockModels.get(i).getProductNumber()+"");
            warehouseBillRecordModel.setBillRecordAmount(viewBoxCodeStockModels.get(i).getProductRetailPrice()*viewBoxCodeStockModels.get(i).getProductNumber());
            warehouseBillRecordModel.setStockId(viewBoxCodeStockModels.get(i).getStockId());

            warehouseBillRecordModels.add(warehouseBillRecordModel);
            list_wareHouseRecordBoxCode.add(viewBoxCodeStockModels.get(i).getBoxCode());
        }
        getChangeModel3(warehouseBillRecordModels,list_wareHouseRecordBoxCode);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));

        //json = json.replace("/", "%2F").replace("=", "%3D").replace("+", " ");
        System.out.println(json);
        okHttpUtils.requestGetByAsyn(stringIP, "AddInputWareHouse", json, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println(result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        //String orderid = result.substring(24,40);
                        if (model.isSuccess()) {
                            if (model.getData() == null) {
                                view.addResult(true, "入库成功");

                            } else {
                                view.addResult(false, model.getMessage());
                            }

                        } else {
                            view.addResult(false, model.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                        System.out.println("3e" + errorMsg);
                        view.addResult(false, "网络错误");
                    }
                }
        );

    }

    //销售退货
    public void TuiSave(List<ViewSaleRecordModel> viewSaleRecordModelList) {
        json = "'[";
        getChangeModel2(viewSaleRecordModelList);
        System.out.println("销售退货" +json );
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        okHttpUtils.requestGetByAsyn(stringIP, "ReturnedGoods", json, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        System.out.println("333333333333333333333333" + result);
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        System.out.println("data" + model.getData());
//                        TypeToken<List<ViewStockModel>> list = new TypeToken<List<ViewStockModel>>() {
//                        };
//                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
//                        viewStockModel = gson.fromJson(model.getData(), list.getType());
                        if (model.isSuccess()) {
                            view.addResult(true, "退货成功");
                        } else {
                            view.addResult(false, model.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {

                        view.addResult(false, "网络错误");
                    }
                }
        );

    }
    //creat盘点
    public void creatinventory(ActualInventoryModel actualInventoryModel ){
        json = "\'"+gson.toJson(actualInventoryModel)+"\'";
        System.out.println("creat盘点传给后台json" + json);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        okHttpUtils.requestGetByAsyn(stringIP, "CreateInventory", json, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                ResultModel model = gson.fromJson(result, ResultModel.class);
                ActualInventoryModel actualInventoryModel = new ActualInventoryModel();
                actualInventoryModel = gson.fromJson(model.getData(), ActualInventoryModel.class);
                    view.Inventory(model.getMessage(),actualInventoryModel);

            }

            @Override
            public void onReqFailed(String errorMsg) {
                ActualInventoryModel actualInventoryModel = new ActualInventoryModel();
                view.Inventory("网络错误",actualInventoryModel);
            }
        });
    }
    //add盘点
    public void inventory(InventoryModel inventoryModel ){

        json = "\'"+gson.toJson(inventoryModel)+"\'";
        System.out.println("盘点传给后台json" + json);
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        okHttpUtils.requestGetByAsyn(stringIP, "AddInventory", json, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                ResultModel model = gson.fromJson(result, ResultModel.class);
                if (model.isSuccess()) {
                    view.addResult(true, "盘点成功");
                } else {
                    view.addResult(false, model.getMessage());
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
               view.addResult(false,"网络错误");
            }
        });

    }


    //查询商品列表
    public void search(String oid) {
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        String str = "'{\"OrderId\":\"" + oid + "\"}'";

        okHttpUtils.requestGetByAsyn(stringIP, "GetProductOrder", str, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        System.out.println("result:" + result);
                        ResultModel model1 = gson.fromJson(result, ResultModel.class);
                        if (model1.isSuccess()) {


                            ViewProductSaleModel viewProductSaleModel = new ViewProductSaleModel();
                            viewProductSaleModel = gson.fromJson(model1.getData(), ViewProductSaleModel.class);
                            List<ViewSaleRecordModel> productOrderModel = new ArrayList<ViewSaleRecordModel>();
                            for (int i = 0; i < viewProductSaleModel.getProductBillOrders().length; i++) {
                                productOrderModel.add(viewProductSaleModel.getProductBillOrders()[i]);
                            }
                            view.SearchBill("查询成功", productOrderModel);
                        } else {
                            List<ViewSaleRecordModel> viewOrderModel = new ArrayList<ViewSaleRecordModel>();
                            view.SearchBill(model1.getMessage(), viewOrderModel);
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewSaleRecordModel> viewOrderModel = new ArrayList<ViewSaleRecordModel>();
                        view.SearchBill("网络错误", viewOrderModel);
                    }
                }
        );
    }

    //退货
    public void TuiHuo(String id) {
        String json = "'{\"OrderID\":\"" + id + "\",\"OrderState\":\"03\"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        okHttpUtils.requestGetByAsyn(stringIP, "CancelProductOrder", json, new ReqCallBack() {
                    @Override
                    public void onReqSuccess(String result) {
                        Gson gson = new Gson();
                        ResultModel model = gson.fromJson(result, ResultModel.class);
                        if (model.isSuccess()) {
//                        TypeToken<List<ViewStockModel>> list = new TypeToken<List<ViewStockModel>>() {
//                        };
//                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
//                        viewStockModel = gson.fromJson(model.getData(), list.getType());
                            view.addResult(true, "退货成功");
                        } else {
                            view.addResult(false, model.getMessage());
                        }
                    }

                    @Override
                    public void onReqFailed(String errorMsg) {
                        List<ViewStockModel> viewStockModel = new ArrayList<ViewStockModel>();
                        System.out.println("3e" + errorMsg);
                        view.addResult(false, "网络错误");
                    }
                }
        );
    }

    //销售退货转换模型
    private void getChangeModel2(List<ViewSaleRecordModel> list) {

        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                json += ",";
            }
            ProductSaleRecordModel bill = new ProductSaleRecordModel();
            ViewSaleRecordModel viewSaleRecordModel = list.get(i);
            viewSaleRecordModel.getSaleRecordBoxCode()[0].setProductNumber(list.get(i).getProductNumber());

            bill.setProductSaleState("02");
            //bill.setProductSaleRecordId(viewSaleRecordModel.getProductSaleRecordId());赋值会覆盖
            bill.setProductSaleRecordId("");
            bill.setStockId(viewSaleRecordModel.getStockId());
            bill.setProductSaleId(viewSaleRecordModel.getProductSaleId());
            bill.setProductSaleAmount(viewSaleRecordModel.getProductNumber() * viewSaleRecordModel.getProductRetailPrice());
            json = json + bill.getJson()+viewSaleRecordModel.getSaleRecordBoxCode()[0].getJson();
        }
        json = json + "]'";

    }

    //转换模型
    private void getChangeModel(List<ProductSaleRecordModel> list ,List<OperatorBoxCodeModel> codelist ) {
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                json += ",";
            }
            ProductSaleRecordModel bill = list.get(i);
            OperatorBoxCodeModel operatorBoxCodeModel  = codelist.get(i);;
            bill.setProductSaleState("01");
            json = json + bill.getJson()+operatorBoxCodeModel.getJson();
        }
        json = json + "]}'";
    }
    //转换模型
    private void getChangeModel3(List<WarehouseBillRecordModel> list ,List<String> codelist ) {
        for (int i = 0; i < list.size(); i++) {
            if (i > 0) {
                json += ",";
            }
            List<String> list1  = new ArrayList<>();
            list1.add(codelist.get(i));
            list.get(i).setWareHouseRecordBoxCode(list1);
            WarehouseBillRecordModel bill = list.get(i);

            json = json + bill.getJson();
        }
        json = json + "]}'";
    }

}
