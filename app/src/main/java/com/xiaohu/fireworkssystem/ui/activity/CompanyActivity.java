package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.ui.fragement.CompanyFragment;
import com.xiaohu.fireworkssystem.ui.fragement.EmployeeFragment;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;


public class CompanyActivity extends AppCompatActivity implements View.OnClickListener {
    //企业信息按钮
    private RadioButton radioButton_company;
    //从业人员按钮
    private RadioButton radioButton_employee;
    private Fragment allFragment = null;
    //标题
    private MyTitleView titleView;
    private Utils utils;
    public static CompanyActivity CompanyActivityContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        CompanyActivityContext = CompanyActivity.this;
        initView();
        initData();
        initListener();
    }
    private void initListener() {
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(CompanyActivity.this,SearchComponyEmployeeActivity.class);
                    startActivityForResult(intent,6);
                    radioButton_employee.setChecked(true);
            }
        });
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void initData() {

    }

    private void initView() {

        utils = new Utils(CompanyActivity.this);
        //从业人员
        utils.WriteString("EmployeeName","");
        utils.WriteString("EmployeeState","");
        utils.WriteString("EmployeeType","");
        utils.WriteString("CardNumber","");

        titleView = (MyTitleView) findViewById(R.id.company_title);
        radioButton_company = (RadioButton)findViewById(R.id.radioButton_company);
        radioButton_employee = (RadioButton)findViewById(R.id.radioButton_employee);

        allFragment = new EmployeeFragment();
        radioButton_employee.setEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_companymanager, allFragment).commit();
        radioButton_company.setOnClickListener(this);
        radioButton_employee.setOnClickListener(this);

    }
    public void onClick(View v)  {

        switch (v.getId()) {
            case R.id.radioButton_company:
                radioButton_company.setEnabled(false);
                radioButton_employee.setEnabled(true);
                radioButton_company.setSelected(true);
                allFragment = new CompanyFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_companymanager, allFragment).commit();

                break;
            case R.id.radioButton_employee:
                radioButton_company.setEnabled(true);
                radioButton_employee.setEnabled(false);
                radioButton_employee.setSelected(true);
                allFragment = new EmployeeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_companymanager, allFragment).commit();
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==6&&resultCode==6){

            utils.WriteString("EmployeeName",data.getStringExtra("EmployeeName"));
            utils.WriteString("EmployeeState",data.getStringExtra("EmployeeState"));
            utils.WriteString("EmployeeType",data.getStringExtra("EmployeeType"));
            utils.WriteString("CardNumber",data.getStringExtra("CardNumber"));
            radioButton_company.setEnabled(true);
            radioButton_employee.setEnabled(false);
            radioButton_employee.setSelected(true);
            allFragment = new EmployeeFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_companymanager, allFragment).commit();
        }
        if (resultCode==99){
            radioButton_company.setEnabled(false);
            radioButton_employee.setEnabled(true);
            radioButton_company.setSelected(true);
            allFragment = new CompanyFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_companymanager, allFragment).commit();

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        allFragment = null;
    }
}
