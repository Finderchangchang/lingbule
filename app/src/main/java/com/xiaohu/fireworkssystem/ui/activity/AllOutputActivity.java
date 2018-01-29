package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.alloutput.AllOutputListener;
import com.xiaohu.fireworkssystem.control.alloutput.AllOutputView;
import com.xiaohu.fireworkssystem.model.view.ViewWaitOutputBoxCodeModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;


public class AllOutputActivity extends AppCompatActivity implements AllOutputView{

    private RadioButton radioButton_out,radioButton_in;
    private PullToRefreshListView lv_allout;
    private AllOutputListener listener;
    private String mes = "";
    private int num = 1;
    private Utils utils;
    private List<ViewWaitOutputBoxCodeModel> list;
    private List<ViewWaitOutputBoxCodeModel> addList;
    private CommonAdapter<ViewWaitOutputBoxCodeModel> adapter;
    private MyTitleView titleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_output);

        initView();
        initListener();
    }

    private void initView() {
        titleView = (MyTitleView) findViewById(R.id.allout_title);
        utils = new Utils(this);
        radioButton_in = (RadioButton) findViewById(R.id.radioButton_in);
        radioButton_out = (RadioButton) findViewById(R.id.radioButton_out);
        lv_allout = (PullToRefreshListView) findViewById(R.id.alloutput_lv);
        list = new ArrayList<>();
        addList = new ArrayList<>();
        listener = new AllOutputListener(this,AllOutputActivity.this);
        listener.GetWaitViewOutput(utils.ReadString(COMPANYID),1);

        adapter = new CommonAdapter<ViewWaitOutputBoxCodeModel>(AllOutputActivity.this, list, R.layout.item_alloutput) {
            @Override
            public void convert(ViewHolder holder, ViewWaitOutputBoxCodeModel viewEmployeeModel, int position) {
                holder.setText(R.id.textView_tagrcompany, viewEmployeeModel.getTargetCompanyName());
                holder.setText(R.id.textView_caozuoren, viewEmployeeModel.getAgentName());
                holder.setText(R.id.textView_dengjiren, viewEmployeeModel.getOperatorName());
                holder.setText(R.id.textView_time, viewEmployeeModel.getCreateTime());
            }
        };
        lv_allout.setAdapter(adapter);
        lv_allout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("WaitOutputBoxCodeId",list.get(position-1).getWaitOutputBoxCodeId());
                intent.putExtra("WaitTargetCompanyName",list.get(position-1).getTargetCompanyName());
                intent.setClass(AllOutputActivity.this,AllOutoutDetailActivity.class);
                startActivity(intent);
            }
        });
        TextView emptyView = new TextView(AllOutputActivity.this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有符合条件的查询，请重新查询");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)lv_allout.getParent()).addView(emptyView);
        lv_allout.setEmptyView(emptyView);
    }
    private void initListener() {
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        radioButton_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AllOutputActivity.this,InWareHouseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        radioButton_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AllOutputActivity.this,OutWareHouseActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public void Search(String string, List<ViewWaitOutputBoxCodeModel> viewWaitOutputBoxCodeModelList) {
        mes = string;
        addList.removeAll(addList);
        addList.addAll(viewWaitOutputBoxCodeModelList);
        AllOutputActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            list.addAll(addList);
            lv_allout.onRefreshComplete();
            adapter.notifyDataSetChanged();
            if (!mes.equals(""))
                Toast.makeText(AllOutputActivity.this, mes, Toast.LENGTH_SHORT).show();
        }
    };
}
