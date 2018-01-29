package com.xiaohu.fireworkssystem.ui.activity;

import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.inputwarehouse.InputWareHouseDetailListener;
import com.xiaohu.fireworkssystem.control.inputwarehouse.InputWareHouseDetailView;
import com.xiaohu.fireworkssystem.model.search.SearchWarehouseBillRecordModel;
import com.xiaohu.fireworkssystem.model.view.ViewInputWareHouseRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 入库信息产品列表
 */
public class ImportProductActivity extends BaseActivity implements InputWareHouseDetailView {


    private ListView lv;
    //入库信息模型
    private ViewInputWareHouseRecordModel viewModel;
    //入库信息查询结果
    private List<ViewInputWareHouseRecordModel> listGoods;
    private List<ViewInputWareHouseRecordModel> addList;
    //入库信息数据数据源
    private CommonAdapter<ViewInputWareHouseRecordModel> adapter;
    //标题
    private MyTitleView title;
    private String strInputWareHouseId = "";
    private InputWareHouseDetailListener listener;
    private SearchWarehouseBillRecordModel model;
    private String mes = "";
    private Utils utils;

    @Override
    public void initView() {
        setContentView(R.layout.activity_import_product);
        title = (MyTitleView) findViewById(R.id.import_detail_title);
        listGoods = new ArrayList<ViewInputWareHouseRecordModel>();
        addList = new ArrayList<ViewInputWareHouseRecordModel>();
        utils = new Utils(this);
        lv = (ListView) findViewById(R.id.aip_lv);
        strInputWareHouseId = getIntent().getStringExtra("InputWareHouseId");
        listener = new InputWareHouseDetailListener(this,ImportProductActivity.this);
        model = new SearchWarehouseBillRecordModel();
        model.setRecordID(strInputWareHouseId);
        listener.Search(model);
    }

    @Override
    public void initEvent() {

        adapter = new CommonAdapter<ViewInputWareHouseRecordModel>(ImportProductActivity.this, listGoods, R.layout.item_import_product) {
            @Override
            public void convert(ViewHolder holder, final ViewInputWareHouseRecordModel goodsModel, final int position) {
                holder.setText(R.id.textView_productname, goodsModel.getProductName());
                holder.setText(R.id.textView_UnitProducntNumber, "整箱数量(箱)："+goodsModel.getUnitProducntNumber());
                holder.setText(R.id.textView_producttype, "产品类型："+utils.isEmptyString2(goodsModel.getProductClassName()));
                holder.setText(R.id.textView_useclass, "使用对象："+utils.isEmptyString2(goodsModel.getUseClassName()));
                holder.setText(R.id.textView_ProductNumber, "产品数量(个)："+goodsModel.getProductNumber());
                holder.setText(R.id.textView_SupplierName, "厂家名称："+utils.isEmptyString2(goodsModel.getSupplierName()));
            }
        };
        lv.setAdapter(adapter);
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    @Override
    public void SearchInputWareHouseDetailView(String message, List<ViewInputWareHouseRecordModel> list) {
        mes = message;
        addList = list;
        ImportProductActivity.this.runOnUiThread(run);
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            listGoods.addAll(addList);
            adapter.notifyDataSetChanged();
            if (!mes.equals(""))
                Toast.makeText(ImportProductActivity.this, mes, Toast.LENGTH_SHORT).show();
        }
    };
}
