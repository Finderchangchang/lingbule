package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.ui.fragement.ImportListFragment;
import com.xiaohu.fireworkssystem.ui.fragement.StockFragment;
import com.xiaohu.fireworkssystem.ui.fragement.StockOutFragment;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

public class StockManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private RadioButton radioButton_stock;
    private RadioButton radioButton_stockru;
    private RadioButton radioButton_stockout;
    private Fragment allFragment = null;
    private MyTitleView titleView;
    private Utils utils;
    private int TAG = 1;
    public static StockManagerActivity StockManagerContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_manager);

        StockManagerContext = StockManagerActivity.this;
        initView();
        initData();
        initListener();
    }
    private void initListener() {
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TAG == 1){
                    Intent intent = new Intent();
                    intent.setClass(StockManagerActivity.this,SearchStorageActivity.class);
                    startActivityForResult(intent,123);

                }else if (TAG == 2){
                    Intent intent = new Intent();
                    intent.setClass(StockManagerActivity.this,ImportSearchActivity.class);
                    startActivityForResult(intent,2);
                }
                else if (TAG == 3){
                    Intent intent = new Intent();
                    intent.setClass(StockManagerActivity.this,ImportSearchActivity.class);
                    startActivityForResult(intent,3);
                }

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

        utils = new Utils(StockManagerActivity.this);
        //库存查询
        utils.WriteString("ProductName","");
        utils.WriteString("ProductClass","");
        utils.WriteString("ProductBarCode","");
        utils.WriteString("UseClass","");

        titleView = (MyTitleView) findViewById(R.id.company_title);
        radioButton_stock = (RadioButton)findViewById(R.id.radioButton_stock);
        radioButton_stockru = (RadioButton)findViewById(R.id.radioButton_stockin);
        radioButton_stockout = (RadioButton) findViewById(R.id.radioButton_stockout);

        allFragment = new StockFragment();
        radioButton_stock.setEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_company, allFragment).commit();
        radioButton_stock.setOnClickListener(this);
        radioButton_stockru.setOnClickListener(this);
        radioButton_stockout.setOnClickListener(this);
    }
    public void onClick(View v)  {

        switch (v.getId()) {
            case R.id.radioButton_stock:
                radioButton_stock.setEnabled(false);
                radioButton_stockru.setEnabled(true);
                radioButton_stockout.setEnabled(true);
                TAG = 1;
                radioButton_stock.setSelected(true);
                allFragment = new StockFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_company, allFragment).commit();
                break;

            case R.id.radioButton_stockin:
                utils.WriteString("InSTARTTime","");
                utils.WriteString("InENDTime","");
                radioButton_stock.setEnabled(true);
                radioButton_stockru.setEnabled(false);
                radioButton_stockout.setEnabled(true);
                TAG = 2;
                radioButton_stockru.setSelected(true);
                allFragment = new ImportListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_company, allFragment).commit();
                break;

            case R.id.radioButton_stockout:
                utils.WriteString("STARTTime","");
                utils.WriteString("ENDTime","");
                radioButton_stock.setEnabled(true);
                radioButton_stockru.setEnabled(true);
                radioButton_stockout.setEnabled(false);
                TAG = 3;
                radioButton_stockout.setSelected(true);
                allFragment = new StockOutFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fl_company, allFragment).commit();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==123&&resultCode==123){

            utils.WriteString("ProductName",data.getStringExtra("ProductName"));
            utils.WriteString("ProductClass",data.getStringExtra("ProductClass"));
            utils.WriteString("ProductBarCode",data.getStringExtra("ProductBarCode"));
            utils.WriteString("UseClass",data.getStringExtra("UseClass"));
            TAG = 1;
            radioButton_stock.setSelected(true);
            allFragment = new StockFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_company, allFragment).commit();

        }
        if (requestCode==2&&resultCode==11){
            allFragment = new ImportListFragment();
            utils.WriteString("InSTARTTime",data.getStringExtra("STARTTime"));
            utils.WriteString("InENDTime",data.getStringExtra("ENDTime"));
            TAG = 2;
            radioButton_stock.setSelected(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_company, allFragment).commit();

        }
        if (requestCode==3&&resultCode==11){
            allFragment = new StockOutFragment();
            utils.WriteString("STARTTime",data.getStringExtra("STARTTime"));
            utils.WriteString("ENDTime",data.getStringExtra("ENDTime"));
            TAG = 3;
            radioButton_stock.setSelected(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fl_company, allFragment).commit();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        allFragment = null;
    }
}
