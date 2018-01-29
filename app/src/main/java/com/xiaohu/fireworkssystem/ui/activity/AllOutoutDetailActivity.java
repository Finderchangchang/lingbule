package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.alloutput.AllOutputDetailListener;
import com.xiaohu.fireworkssystem.control.alloutput.AllOutputDetailView;
import com.xiaohu.fireworkssystem.model.view.ViewWaitOutputBoxCodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewWaitOutputBoxCodeRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;


public class AllOutoutDetailActivity extends AppCompatActivity implements AllOutputDetailView{

    private AllOutputDetailListener listener;
    private ViewWaitOutputBoxCodeModel viewWaitOutputBoxCodeModel;
    private String mes = "";
    private PullToRefreshListView alloutputdetail_lv;
    private Utils utils;
    private List<ViewWaitOutputBoxCodeRecordModel> list;
    private List<ViewWaitOutputBoxCodeRecordModel> addList;
    private CommonAdapter<ViewWaitOutputBoxCodeRecordModel> adapter;
    private MyTitleView titleView;
    private Button button_output;
    private TextView textView_comname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_outout_detail);

        initView();
        initListener();
    }

    private void initListener() {
        button_output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if (list.size()>0){
                    intent.putExtra("targetcompanyid",list.get(0).getTargetCompanyId());
                    intent.putExtra("WaitOutputBoxCodeId",getIntent().getStringExtra("WaitOutputBoxCodeId"));
                    intent.setClass(AllOutoutDetailActivity.this,AllOutputPersonViewActivity.class);
                    startActivity(intent);
                }

            }
        });
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new CommonAdapter<ViewWaitOutputBoxCodeRecordModel>(AllOutoutDetailActivity.this, list, R.layout.item_alloutputdetail) {
            @Override
            public void convert(ViewHolder holder, ViewWaitOutputBoxCodeRecordModel viewEmployeeModel, int position) {
                holder.setText(R.id.textView_tagrcompany, viewEmployeeModel.getProductName());
                holder.setText(R.id.textView_caozuoren, viewEmployeeModel.getProductId());
                holder.setText(R.id.textView_time, viewEmployeeModel.getProductNumber()+"");
                String s = viewEmployeeModel.getBoxCodeExtend();
                s = s.replaceAll(",","\r\n");
                holder.setText(R.id.textView_qujian, s);
            }
        };
        alloutputdetail_lv.setAdapter(adapter);
    }

    private void initView() {
        utils = new Utils(this);
        list = new ArrayList<>();
        addList = new ArrayList<>();
        button_output  = (Button) findViewById(R.id.button_output);
        titleView = (MyTitleView) findViewById(R.id.alloutdetail_title);
        alloutputdetail_lv = (PullToRefreshListView) findViewById(R.id.alloutputdetail_lv);
        viewWaitOutputBoxCodeModel = new ViewWaitOutputBoxCodeModel();
        textView_comname = (TextView) findViewById(R.id.textView_comname);
        textView_comname.setText(getIntent().getStringExtra("WaitTargetCompanyName"));
        listener = new AllOutputDetailListener(this,AllOutoutDetailActivity.this);
        if (getIntent().getStringExtra("WaitOutputBoxCodeId")!=null){
            listener.GetWaitViewAllOutput(getIntent().getStringExtra("WaitOutputBoxCodeId"));
        }

    }

    @Override
    public void Search(String string, ViewWaitOutputBoxCodeModel viewWaitOutputBoxCodeModel) {
        mes = string;
        List<ViewWaitOutputBoxCodeRecordModel> lists = new ArrayList<>();
        lists = viewWaitOutputBoxCodeModel.getWaitOutputBoxCodeRecord();
        addList.removeAll(addList);
        addList.addAll(lists);
        AllOutoutDetailActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            list.addAll(addList);
            alloutputdetail_lv.onRefreshComplete();
            adapter.notifyDataSetChanged();
            if (!mes.equals(""))
                Toast.makeText(AllOutoutDetailActivity.this, mes, Toast.LENGTH_SHORT).show();
        }
    };
}
