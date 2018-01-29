package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.DatePickerDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;

/**
 * 入库信息查询条件
 * Created by Administrator on 2016/11/6.
 */

public class ImportSearchActivity extends BaseActivity {
    //起始时间
    private TextView etEndTime, etStartTime;
    //标题
    private MyTitleView title;
    //查询
    private Button btnSearch;
    private String start = "", end = "";
    //时间选择控件
    private DatePickerDialog dateDialog;

    @Override
    public void initView() {
        setContentView(R.layout.activity_import_search);
        title = (MyTitleView) findViewById(R.id.import_search_title);
        etStartTime = (TextView) findViewById(R.id.et_search_import_starttime);
        etEndTime = (TextView) findViewById(R.id.et_search_import_endtime);
        btnSearch = (Button) findViewById(R.id.btn_import_search);
        start = getIntent().getStringExtra("STARTTime");
        end = getIntent().getStringExtra("ENDTime");
        if (start != null && (!start.equals(""))) {
            etStartTime.setText(start);
        } else {
            etStartTime.setText(Utils.getNowDateBefor(-30));
        }
        if (end != null && (!end.equals(""))) {
            etEndTime.setText(end);
        } else {
            etEndTime.setText(Utils.getNowDate());
        }
    }

    @Override
    public void initEvent() {
        etStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DatePickerDialog(ImportSearchActivity.this, etStartTime.getText().toString());
                dateDialog.datePickerDialog(etStartTime);//直接改变edittext的控件的值
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
        etEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DatePickerDialog(ImportSearchActivity.this, etEndTime.getText().toString());
                dateDialog.datePickerDialog(etEndTime);//直接改变edittext的控件的值

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
        //查询
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("STARTTime", etStartTime.getText().toString());
                intent.putExtra("ENDTime", etEndTime.getText().toString());
                setResult(11, intent);
                finish();
            }
        });
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(13, null);
                finish();
            }
        });
    }

}
