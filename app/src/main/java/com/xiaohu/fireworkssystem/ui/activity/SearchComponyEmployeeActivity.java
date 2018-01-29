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
import com.xiaohu.fireworkssystem.model.search.SearchEmployeeModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;

import java.util.ArrayList;
import java.util.List;

public class SearchComponyEmployeeActivity extends AppCompatActivity {
    private MyTitleView titleView;
    private EditText editText_employeeName, editText_cardNumber;
    private CodeDao dao;
    private TextView textView_employeeType, textView_employeeState;
    private Intent intent;
    private Utils utils;
    private SpinnerDialog dialog;//选择
    private SearchEmployeeModel searchEmployeeModel;
    private Button btn_companyEmployeeSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_compony_employee);

        initView();
        initListener();

    }

    private void initListener() {
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("EmployeeName", "");
                intent.putExtra("EmployeeState", "");
                intent.putExtra("EmployeeType", "");
                intent.putExtra("CardNumber", "");
                setResult(6, intent);
                finish();
            }
        });
        textView_employeeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByData(dao.QueryByName("CodeName", "Code_EmployeeType"));
            }
        });
        textView_employeeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByData2(dao.QueryByName("CodeName", "Code_EmployeeState"));
            }
        });
        btn_companyEmployeeSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("EmployeeName", editText_employeeName.getText().toString().trim());
                intent.putExtra("CardNumber", editText_cardNumber.getText().toString().trim());
                if (searchEmployeeModel.getEmployeeType() == null || searchEmployeeModel.getEmployeeType().equals("")) {
                    intent.putExtra("EmployeeType", "");
                } else {
                    intent.putExtra("EmployeeType", searchEmployeeModel.getEmployeeType());
                }
                if (searchEmployeeModel.getEmployeeState() == null || searchEmployeeModel.getEmployeeState().equals("")) {
                    intent.putExtra("EmployeeState", "");
                } else {
                    intent.putExtra("EmployeeState", searchEmployeeModel.getEmployeeState());
                }
                setResult(6, intent);
                finish();
            }
        });

    }

    private void initView() {
        dao = new CodeDao(this);
        intent = new Intent();
        utils = new Utils(SearchComponyEmployeeActivity.this);
        searchEmployeeModel = new SearchEmployeeModel();
        intent.putExtra("EmployeeName", "");
        intent.putExtra("EmployeeState", "");
        intent.putExtra("EmployeeType", "");
        intent.putExtra("CardNumber", "");
        titleView = (MyTitleView) findViewById(R.id.searchCompanyEmployee_title);
        editText_employeeName = (EditText) findViewById(R.id.et_employeeName);
        editText_cardNumber = (EditText) findViewById(R.id.et_cardNumber);
        textView_employeeType = (TextView) findViewById(R.id.textView_employeeType);
        textView_employeeState = (TextView) findViewById(R.id.textView_employeeState);
        btn_companyEmployeeSearch = (Button) findViewById(R.id.btn_companyEmployeeSearch);
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
            dialog = new SpinnerDialog(SearchComponyEmployeeActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    searchEmployeeModel.setEmployeeType(val.getDeviceAddress());
                    textView_employeeType.setText(val.getDeviceName());
                }
            });
        }
    }

    private void getCodeByData2(List<CodeModel> list) {

        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(SearchComponyEmployeeActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    searchEmployeeModel.setEmployeeState(val.getDeviceAddress());
                    textView_employeeState.setText(val.getDeviceName());
                }
            });
        }
    }


    //返回键
    public void onBackPressed() {
        intent.putExtra("EmployeeName", "");
        intent.putExtra("EmployeeState", "");
        intent.putExtra("EmployeeType", "");
        intent.putExtra("CardNumber", "");
        setResult(6, intent);
        finish();
    }


}
