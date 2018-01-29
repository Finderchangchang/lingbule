package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.model.BlueToothModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.search.SearchStockModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 查询库存信息
 * Created by Administrator on 2016/8/4.
 */
public class SearchStorageActivity extends BaseActivity {
    //产品名称，二维码
    private EditText etName, etScanId;
    //标题
    private MyTitleView title;
    //产品类型
    private TextView etType ,etUseClass;
    //字典操作类
    private CodeDao dao;
    private SpinnerDialog dialog;//选择
    //查询模型
    private SearchStockModel stockModel;
    private Intent intent;
    private Utils utils;

    @Override
    public void initView() {
        dao = new CodeDao(this);
        intent = new Intent();
        utils = new Utils(SearchStorageActivity.this);
        intent.putExtra("ProductName", "");
        intent.putExtra("ProductClass", "");
        intent.putExtra("ProductBarCode", "");
        intent.putExtra("UseClass", "");

        stockModel = new SearchStockModel();
        setContentView(R.layout.activity_search_storage);
        title = (MyTitleView) findViewById(R.id.search_storage_title);
        //产品名称
        etName = (EditText) findViewById(R.id.et_search_storage_name);
        //产品类型
        etType = (TextView) findViewById(R.id.et_search_storage_type);
        //条形码
        etScanId = (EditText) findViewById(R.id.et_search_storage_scanid);
        //使用对象
        etUseClass = (TextView) findViewById(R.id.et_search_storage_use);
    }

    @Override
    public void initEvent() {
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("ProductName", "");
                intent.putExtra("ProductClass", "");
                intent.putExtra("ProductBarCode", "");
                intent.putExtra("UseClass", "");
                setResult(123, intent);
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.et_search_storage_use:
                //选择商品类型
                getCodeByData2(dao.QueryByName("CodeName", "Code_UseClass"));

                break;
            case R.id.et_search_storage_type:
                //选择商品类型
                getCodeByData(dao.QueryByName("CodeName", "Code_ProductClass"));

                break;
            case R.id.btn_storage_search:
                //查询
                //返回条件，结果页面按条件查询
                //startActivityForResult();

                intent.putExtra("ProductBarCode", etScanId.getText().toString().trim());
                intent.putExtra("ProductName", etName.getText().toString().trim());
                if ("请选择".equals(etType.getText().toString().trim())) {
                    intent.putExtra("ProductClass", "");
                } else {
                    intent.putExtra("ProductClass", stockModel.getProductClass());
                }
                if ("请选择".equals(etUseClass.getText().toString().trim())) {
                    intent.putExtra("UseClass", "");
                } else {
                    intent.putExtra("UseClass", stockModel.getUseClass());
                }
                setResult(123, intent);
                finish();
                break;
        }
    }

    private void getCodeByData(List<CodeModel> list) {
        System.out.println(list);
        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(SearchStorageActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    stockModel.setProductClass(val.getDeviceAddress());
                    etType.setText(val.getDeviceName());
                }
            });
        }
    }
    private void getCodeByData2(List<CodeModel> list) {
        System.out.println(list);
        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(SearchStorageActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    stockModel.setUseClass(val.getDeviceAddress());
                    etUseClass.setText(val.getDeviceName());
                }
            });
        }
    }

    public void onBackPressed() {
        intent.putExtra("ProductName", "");
        intent.putExtra("ProductClass", "");
        intent.putExtra("ProductBarCode", "");
        intent.putExtra("UseClass", "");
        setResult(123, intent);
        finish();
    }
}
