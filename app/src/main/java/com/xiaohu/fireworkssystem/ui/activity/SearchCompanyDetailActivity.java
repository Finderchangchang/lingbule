package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.model.BlueToothModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.search.SearchCompanyModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;

import java.util.ArrayList;
import java.util.List;

public class SearchCompanyDetailActivity extends AppCompatActivity {

    private MyTitleView titleView;
    private EditText editText_companyName, editText_boss,editText_leader;
    private CodeDao dao;
    private TextView textView_companyType;
    private Intent intent;
    private Utils utils;
    private SpinnerDialog dialog;//选择
    private SearchCompanyModel searchCompanyModel;
    private Button btn_companySearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_company_detail);

        initView();
        initListener();
    }

    private void initListener() {
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CompanyName", "");
                intent.putExtra("Boss", "");
                intent.putExtra("CompanyType", "");
                intent.putExtra("Leader", "");
                setResult(5, intent);
                finish();
            }
        });

        textView_companyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByData(dao.QueryByName("CodeName", "Code_CompanyType"));
            }
        });

        btn_companySearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("CompanyName", editText_companyName.getText().toString().trim());
                intent.putExtra("Boss", editText_boss.getText().toString().trim());
                intent.putExtra("Leader", editText_leader.getText().toString().trim());
                if (searchCompanyModel.getCompanyType() == null || searchCompanyModel.getCompanyType().equals("")) {
                    intent.putExtra("CompanyType", "");
                } else {
                    intent.putExtra("CompanyType", searchCompanyModel.getCompanyType());
                }

                setResult(5, intent);
                finish();
            }
        });
    }

    private void initView() {
        titleView = (MyTitleView) findViewById(R.id.searchCompany_title);
        textView_companyType = (TextView) findViewById(R.id.textView_companytype);
        dao = new CodeDao(this);
        intent = new Intent();
        utils = new Utils(SearchCompanyDetailActivity.this);
        searchCompanyModel = new SearchCompanyModel();
        intent.putExtra("CompanyName", "");
        intent.putExtra("Boss", "");
        intent.putExtra("CompanyType", "");
        intent.putExtra("Leader", "");
        editText_companyName = (EditText) findViewById(R.id.et_companyName);
        editText_boss = (EditText) findViewById(R.id.et_boss);
        editText_leader = (EditText) findViewById(R.id.et_leader);
        btn_companySearch = (Button) findViewById(R.id.btn_companySearch);
    }

    private void getCodeByData(List<CodeModel> list) {

        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(SearchCompanyDetailActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    searchCompanyModel.setCompanyType(val.getDeviceAddress());
                    textView_companyType.setText(val.getDeviceName());
                }
            });
        }
    }
    //返回键
    public void onBackPressed() {
        intent.putExtra("CompanyName", "");
        intent.putExtra("Boss", "");
        intent.putExtra("CompanyType", "");
        intent.putExtra("Leader", "");
        setResult(6, intent);
        finish();
    }
}
