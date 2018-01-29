package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.LLDialog;

import static com.xiaohu.fireworkssystem.config.MyKeys.BusinessLicenseNumberValidity;
import static com.xiaohu.fireworkssystem.config.MyKeys.LicenseNumberValidity;
import static com.xiaohu.fireworkssystem.config.MyKeys.ServerDate;
import static com.xiaohu.fireworkssystem.config.MyKeys.TISHI;

public class Main2Activity extends AppCompatActivity  {
    private Utils utils;
    private LLDialog llDialog;
    public static Main2Activity mIntail2s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        mIntail2s = this;
        utils = new Utils(Main2Activity.this);
        llDialog = new LLDialog(this);
        GetDaoQi();
    }

    private void GetDaoQi() {
        if(utils.ReadString(TISHI).equals("0")) {
            String sertime = utils.ReadString(ServerDate);
            long xuday = utils.getDayCha(utils.ReadString(LicenseNumberValidity), sertime);
            long yingday = utils.getDayCha(utils.ReadString(BusinessLicenseNumberValidity), sertime);
            String mes = "";
            if (xuday <= 5) {
                mes = "许可证有效期还有" + xuday + "天到期;";
            }
            if (yingday <= 5) {
                if (!mes.equals("")) {
                    mes += "\n";
                }
                mes += "营业执照还有" + yingday + "天到期;";
            }
            if (!mes.equals("")) {
                llDialog = new LLDialog(Main2Activity.this);
                llDialog.setMiddleMessage(mes);
                llDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击确定按钮
                        llDialog.cancel();
                    }
                });
                utils.WriteString(TISHI, "1");
                llDialog.show();
            }
        }
    }


    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.image_employee:
                intent.setClass(Main2Activity.this, CompanyActivity.class);
                startActivity(intent);
                break;
            case R.id.image_ruku:
                intent.setClass(Main2Activity.this, AllOutputActivity.class);
                startActivity(intent);
                break;
            case R.id.image_saleadd:
                intent.setClass(Main2Activity.this, SalesRegistrationActivity.class);
               /* intent.putExtra("num", "0");*/
                startActivity(intent);
                break;
            case R.id.image_stock:
                intent.setClass(Main2Activity.this, StockManagerActivity.class);
                intent.putExtra("num", "1");
                startActivity(intent);
                break;
            case R.id.image_sale:
                intent.setClass(Main2Activity.this, SalesManageActivity.class);
                /*intent.putExtra("num", "2");*/
                startActivity(intent);
                break;
            case R.id.image_setting:
                intent.setClass(Main2Activity.this, SettingActivity.class);
                /*intent.putExtra("num", "3");*/
                startActivity(intent);
                break;
        }
    }

    /**
     * 点击返回弹出消息框，确定后完全退出系统
     *
     * @param keyCode key的编码
     * @param event   key的时间
     * @return
     * @author cc 2016年4月8日 14:45:14
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            llDialog.setMiddleMessage("确认要退出系统？");
            llDialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//点击确定按钮
                    llDialog.cancel();
                    Main2Activity.this.finish();
                    System.exit(0);//完全退出系统
                }
            });
            llDialog.setOnNegativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llDialog.cancel();

                }
            });//点击取消按钮
            llDialog.show();
        }
        return false;
    }
}
