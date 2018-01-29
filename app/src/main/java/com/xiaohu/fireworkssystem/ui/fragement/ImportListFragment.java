package com.xiaohu.fireworkssystem.ui.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import com.xiaohu.fireworkssystem.control.inputwarehouse.InputWareHouseListener;
import com.xiaohu.fireworkssystem.control.inputwarehouse.InputWareHouseView;
import com.xiaohu.fireworkssystem.model.search.SearchInputWareHouseModel;
import com.xiaohu.fireworkssystem.model.view.ViewInputWareHouseModel;
import com.xiaohu.fireworkssystem.ui.activity.ImportDetailActivity;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.ui.activity.StockManagerActivity.StockManagerContext;

/**
 * Created by Administrator on 2017/6/29.
 */

public class ImportListFragment extends Fragment implements InputWareHouseView {
    private View view;
    //刷新Listview
    private PullToRefreshListView listView;
    //listview数据源
    private CommonAdapter<ViewInputWareHouseModel> adapter;
    //入库信息显示集合
    private List<ViewInputWareHouseModel> list;
    private List<ViewInputWareHouseModel> addList;
    //导入listener
    private InputWareHouseListener listener;
    //页数
    private int page = 1;
    private Utils utils;
    //查询模型
    private SearchInputWareHouseModel searchModel;
    String mes = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_import_list, container, false);

        initView();
        initListener();
        return view;
    }

    private void initView() {
        list = new ArrayList<ViewInputWareHouseModel>();
        addList= new ArrayList<ViewInputWareHouseModel>();
        utils = new Utils(StockManagerContext);
        searchModel = new SearchInputWareHouseModel();

        /*searchModel.setCreateTimeBegin(Utils.getNowDateBefor(-40));
        searchModel.setCreateTimeEnd(Utils.getNowDate());*/

        String starttime = utils.ReadString("InSTARTTime");
        String endtime = utils.ReadString("InENDTime");
        if (null!= starttime&&!"".equals(starttime)){
            searchModel.setCreateTimeBegin(starttime);
        }else {
            searchModel.setCreateTimeBegin(Utils.getNowDateBefor(-40));
        }
        if (null!= endtime&&!"".equals(endtime)){
            searchModel.setCreateTimeEnd(endtime+" 23:59:59");
        }else {
            searchModel.setCreateTimeEnd(Utils.getNowDate()+" 23:59:59");
        }

        listView = (PullToRefreshListView) view.findViewById(R.id.import_lv);
        listView.setMode(PullToRefreshBase.Mode.BOTH);
        listener = new InputWareHouseListener(this,StockManagerContext);
        listener.Search(searchModel, 1);
        page=1;
    }

    private void initListener() {
        adapter = new CommonAdapter<ViewInputWareHouseModel>(StockManagerContext, list, R.layout.item_import) {
            @Override
            public void convert(ViewHolder holder, ViewInputWareHouseModel model, int position) {
                holder.setText(R.id.ii_tvfahuo, model.getCompanyName());
                holder.setText(R.id.ii_chukuren, model.getOperatorName());
                holder.setText(R.id.ii_totalmoney, model.getAmount().substring(0, model.getAmount().indexOf(".") + 2) + "元");
                holder.setText(R.id.ii_time, model.getCreateTime().substring(2, 10));
                holder.setText(R.id.ii_inwarehousetype, model.getInputTypeName());
            }
        };
        listView.setAdapter(adapter);
        TextView emptyView = new TextView(StockManagerContext);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有符合条件的查询，请重新查询");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)listView.getParent()).addView(emptyView);
        listView.setEmptyView(emptyView);

        //点击跳转到详情页面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StockManagerContext, ImportDetailActivity.class);
                intent.putExtra("ViewInputWareHouseModel", list.get(position - 1));
                startActivityForResult(intent, 122);
            }
        });
        //刷新
        listView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                page = 1;
                list.removeAll(list);
                listener.Search(searchModel, page);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                page++;
                listener.Search(searchModel, page);
            }
        });
    }
    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 13 && resultCode == 11) {
        *//*    searchModel.setCreateTimeBegin(data.getStringExtra("STARTTime"));
            searchModel.setCreateTimeEnd(data.getStringExtra("ENDTime"));*//*
            page = 1;
            list.removeAll(list);
            listener.Search(searchModel, page);
        }
        if (requestCode == 122 && resultCode == 122) {
            page = 1;
            listener.Search(searchModel, page);
        }
    }*/

    @Override
    public void SearchInputWareHouseView(String message, List<ViewInputWareHouseModel> list) {
        mes = message;
        addList = list;
        StockManagerContext.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {

            if (!mes.equals("")){
                Toast.makeText(StockManagerContext, mes, Toast.LENGTH_SHORT).show();
            }else {
                list.addAll(addList);
                listView.onRefreshComplete();
                adapter.notifyDataSetChanged();
            }


        }
    };
}
