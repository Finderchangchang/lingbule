package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.seal_manager.SealManagerListener;
import com.xiaohu.fireworkssystem.control.seal_manager.SealManagerView;
import com.xiaohu.fireworkssystem.model.search.SearchProductOrderModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 销售信息查询
 * 2017/5/23
 * zz
 * 原fragment改为activity
 */

public class SalesManageActivity extends AppCompatActivity implements SealManagerView {

    private PullToRefreshListView listView_salesManagement;
    private List<ViewProductSaleModel> list;
    private List<ViewProductSaleModel> addList;
    private MyTitleView title;
    private CommonAdapter<ViewProductSaleModel> adapter;
    private SealManagerListener sealManagerListener;
    private SearchProductOrderModel productOrderModel;
    private Utils utils;
    private String myMes = "";
    private int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_manage);

        num = 1;
        initView();
        initEvent();
    }

    private void initView() {
        sealManagerListener = new SealManagerListener(this);
        list = new ArrayList<ViewProductSaleModel>();
        //请求数据
        productOrderModel = new SearchProductOrderModel();
        productOrderModel.setOrderID("");
        productOrderModel.setApplyerCertNumber("");
        productOrderModel.setAppler("");
        productOrderModel.setCreateTimeBegin("");
        productOrderModel.setCreateTimeEnd("");
        sealManagerListener.Search(productOrderModel, 1,SalesManageActivity.this);
        listView_salesManagement = (PullToRefreshListView)findViewById(R.id.seal_lv);
        listView_salesManagement.setMode(PullToRefreshBase.Mode.BOTH);
        title = (MyTitleView)findViewById(R.id.seal_manager_title);
        utils = new Utils(SalesManageActivity.this);
    }

    public void initEvent() {
        title.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SalesManageActivity.this, SealSearchActivity.class);
                startActivityForResult(intent, 12);
            }
        });
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new CommonAdapter<ViewProductSaleModel>(SalesManageActivity.this, list, R.layout.lv_seal_item) {
            @Override
            public void convert(ViewHolder holder, ViewProductSaleModel model, final int position) {
                holder.setText(R.id.seal_id, model.getProductSaleId());
                holder.setText(R.id.seal_kai_name, model.getOperatorName());

                String time = model.getCreateTime();
                String times = model.getCreateTime();
                String resulttime = time.substring(5, 7) + "月" + times.substring(8, 10) + "日 " + model.getCreateTime().substring(11, 13) + "时";
                holder.setText(R.id.seal_createtime, resulttime);
                holder.setText(R.id.seal_person_name, model.getApplyer());
                holder.setText(R.id.seal_price, "¥:" + model.getOrderAmount());
               /* holder.setOnClickListener(R.id.btn_seal_item_tui, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(SalesManageActivity.this, SealAddActivity.class);
                        intent.putExtra("ISADD", 1);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("TuiHuo", list.get(position));
                        intent.putExtras(bundle);
                        SalesManageActivity.this.startActivity(intent);
                    }
                });
                if (model.getOrderState().equals("02")) {
                    holder.setVisible(R.id.tv_seal_item_state, false);
                    holder.setVisible(R.id.btn_seal_item_tui, false);
                    holder.setOnClickListener(R.id.btn_seal_item_tui, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(SalesManageActivity.this, SealAddActivity.class);
                            intent.putExtra("ISADD", 1);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("TuiHuo", list.get(position));
                            intent.putExtras(bundle);
                            SalesManageActivity.this.startActivity(intent);
                        }
                    });
                } else {
                    holder.setVisible(R.id.tv_seal_item_state, true);
                    holder.setVisible(R.id.btn_seal_item_tui, false);
                    holder.setText(R.id.tv_seal_item_state, model.getOrderStateName());
                }*/
            }
        };
        listView_salesManagement.setAdapter(adapter);
        TextView emptyView = new TextView(SalesManageActivity.this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有符合条件的查询，请重新查询");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)listView_salesManagement.getParent()).addView(emptyView);
        listView_salesManagement.setEmptyView(emptyView);

        listView_salesManagement.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SalesManageActivity.this, SaleDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ORDERMODEL", list.get(position - 1));
                intent.putExtras(bundle);
                SalesManageActivity.this.startActivity(intent);
            }
        });
        listView_salesManagement.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                num=1;
                list.removeAll(list);
                sealManagerListener.Search(productOrderModel, 1,SalesManageActivity.this);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                num++;
                sealManagerListener.Search(productOrderModel, num,SalesManageActivity.this);
            }
        });
    }


    //实现的接口
    @Override
    public void SealManagerView(String message, List<ViewProductSaleModel> list) {
        myMes = message;
        addList = list;
        SalesManageActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            list.addAll(addList);
            listView_salesManagement.onRefreshComplete();
            adapter.notifyDataSetChanged();
            if (!myMes.equals(""))
                Toast.makeText(SalesManageActivity.this, myMes, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == 12) {
            String etOrder = data.getStringExtra("etOrder");
            String etApplyerCard = data.getStringExtra("etApplyerCard");
            String etName = data.getStringExtra("etName");
            String etStartTime = data.getStringExtra("etStartTime");
            String etEndTime = data.getStringExtra("etEndTime");
            productOrderModel.setOrderID(etOrder);
            productOrderModel.setApplyerCertNumber(etApplyerCard);
            productOrderModel.setAppler(etName);
            productOrderModel.setCreateTimeBegin(etStartTime);
            productOrderModel.setCreateTimeEnd(etEndTime + " " + "23:59:59");
            list.removeAll(list);
            num=1;
            sealManagerListener.Search(productOrderModel, 1,SalesManageActivity.this);
        }
    }
}
