package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.DatePickerDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;

/**
 * 销售单查询界面
 * Created by Administrator on 2016/8/5.
 */
public class SealSearchActivity extends BaseActivity {
    private MyTitleView title;
    private DatePickerDialog dateDialog;//时间选择控件
    private EditText etApplyer, etApplyerCard,etName;
    private TextView etStartTime, etEndTime;
    private Button btn_seal_search;
    private int resultCode = 12;
    private Intent mIntent;
    private Utils utils;

    @Override
    public void initView() {
        setContentView(R.layout.acitivity_seal_search);
        utils = new Utils(SealSearchActivity.this);
        mIntent = new Intent();
        mIntent.putExtra("etApplyer", "");
        mIntent.putExtra("etApplyerCard", "");
        mIntent.putExtra("etName", "");
        mIntent.putExtra("etStartTime", "");
        mIntent.putExtra("etEndTime", "");
        SealSearchActivity.this.setResult(0, mIntent);
        title = (MyTitleView) findViewById(R.id.seal_search_title);
        etApplyer = (EditText) findViewById(R.id.et_seal_goumai);
        etApplyerCard = (EditText) findViewById(R.id.et_seal_cardid);
        etName = (EditText) findViewById(R.id.et_seal_name);
        etStartTime = (TextView) findViewById(R.id.et_search_seal_starttime);
        etEndTime = (TextView) findViewById(R.id.et_search_seal_endtime);
        etStartTime.setText(Utils.getNowDateBefor(-7));
        etEndTime.setText(Utils.getNowDate());
        btn_seal_search = (Button) findViewById(R.id.btn_seal_search);
    }

    @Override
    public void initEvent() {
        title.setLeftOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.putExtra("etOrder", "");
                mIntent.putExtra("etApplyerCard", "");
                mIntent.putExtra("etName", "");
                mIntent.putExtra("etStartTime", "");
                mIntent.putExtra("etEndTime", "");
                finish();
            }
        });
        etStartTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.compareDate(etStartTime.getText().toString(), etEndTime.getText().toString())) {
                    etStartTime.setText(etEndTime.getText().toString());
                }
            }
        });

        etEndTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.compareDate(etEndTime.getText().toString(), Utils.getNowDate())) {
                    etEndTime.setText(Utils.getNowDate());
                }
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_seal_search:
                //查询
                if ("".equals(etApplyer.getText().toString().trim()) &&
                        "".equals(etApplyerCard.getText().toString().trim()) &&
                        "".equals(etName.getText().toString().trim()) &&
                        "".equals(etStartTime.getText().toString().trim()) &&
                        "".equals(etEndTime.getText().toString().trim())) {

                    Toast.makeText(SealSearchActivity.this, "请输入商品信息", Toast.LENGTH_SHORT).show();
                } else {
                    mIntent.putExtra("etOrder", etApplyer.getText().toString().trim() + "");
                    mIntent.putExtra("etApplyerCard", etApplyerCard.getText().toString().trim() + "");
                    mIntent.putExtra("etName", etName.getText().toString().trim() + "");
                    mIntent.putExtra("etStartTime", etStartTime.getText().toString().trim() + "");
                    mIntent.putExtra("etEndTime", etEndTime.getText().toString().trim() + "");
                    // 设置结果，并进行传送
                    SealSearchActivity.this.setResult(12, mIntent);
                    SealSearchActivity.this.finish();
                }
                break;
            case R.id.et_search_seal_starttime:
                //起始时间
                //字符串为空，默认为当前系统时间
                dateDialog = new DatePickerDialog(SealSearchActivity.this, etStartTime.getText().toString());
                dateDialog.datePickerDialog(etStartTime);//直接改变edittext的控件的值
                break;
            case R.id.et_search_seal_endtime:
                //结束时间
                //字符串为空，默认为当前系统时间
                dateDialog = new DatePickerDialog(SealSearchActivity.this, etEndTime.getText().toString());
                dateDialog.datePickerDialog(etEndTime);//直接改变edittext的控件的值
                break;
        }
    }

    public void onBackPressed() {
        mIntent.putExtra("etOrder", "");
        mIntent.putExtra("etApplyerCard", "");
        mIntent.putExtra("etName", "");
        mIntent.putExtra("etStartTime", "");
        mIntent.putExtra("etEndTime", "");
        finish();
    }
}
