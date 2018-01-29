package com.xiaohu.fireworkssystem.ui.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.xiaohu.fireworkssystem.control.stock.StockSearchListener;
import com.xiaohu.fireworkssystem.control.stock.StockSearchView;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.search.SearchStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewStockModel;
import com.xiaohu.fireworkssystem.ui.activity.GoodsDetailActivity;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.ui.activity.StockManagerActivity.StockManagerContext;

/**
 * 库存查询
 */
public class StockFragment extends Fragment implements StockSearchView {
    //下拉列表
    private PullToRefreshListView listView_stock;
    //适配器
    private CommonAdapter<ViewStockModel> adapter;
    //数组
    private List<ViewStockModel> list;
    private SearchStockModel stockModel;
    private int isVisible = 0;//隐藏   1：显示
    private List<ViewStockModel> addList;
    private int number = 0;
    private StockSearchListener listener;
    private View view;
    private String myMes = "";
    private  int num = 1;
    private Utils utils;
/*    private Button button_inventory;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        utils = new Utils(StockManagerContext);
        listener = new StockSearchListener(this, StockManagerContext);

        list = new ArrayList<ViewStockModel>();

        num=1;
        //请求数据
        stockModel = new SearchStockModel();
        stockModel.setProductClass(utils.URLEncode(utils.ReadString("ProductClass")));
        stockModel.setProductName(utils.URLEncode(utils.ReadString("ProductName")));
        stockModel.setProductId(utils.URLEncode(utils.ReadString("ProductBarCode")));
        stockModel.setUseClass(utils.URLEncode(utils.ReadString("UseClass")));
        listener.Search(stockModel, 1);
        listView_stock.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                list.removeAll(list);
                num=1;
                listener.Search(stockModel, 1);
                listView_stock.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listView_stock.onRefreshComplete();
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                num++;
                listener.Search(stockModel, num);
            }
        });
        initEvent();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_stock, container, false);
        listView_stock = (PullToRefreshListView) view.findViewById(R.id.storage_lv);
        listView_stock.setMode(PullToRefreshBase.Mode.BOTH);
        return view;
    }



    public void initEvent() {
        adapter = new CommonAdapter<ViewStockModel>(StockManagerContext, list, R.layout.lv_goods_item) {
            @Override
            public void convert(ViewHolder holder, final ViewStockModel model, final int position) {
                holder.setText(R.id.it_goods_id, utils.isEmptyString2(model.getUseClassName()));
                holder.setText(R.id.it_goods_name, utils.isEmptyString2(model.getProductName()));
                holder.setText(R.id.it_goods_price, "¥" + model.getProductRetailPrice() + "");
                CodeDao dao = new CodeDao(StockManagerContext);
                List<CodeModel> codes = dao.QueryByID("Code_ProductClass", utils.isEmptyString2(model.getProductClass()));
                if (codes.size() > 0) {
                    holder.setText(R.id.it_goods_type, utils.isEmptyString2(codes.get(0).getName()));
                }
                holder.setText(R.id.it_goods_barcode, utils.isEmptyString2(model.getProductId() + ""));
                int z = model.getProductTotal()%model.getProductNumber();
                if (0==z){
                    holder.setText(R.id.it_goods_num, model.getProductTotal()/model.getProductNumber()+"箱");
                }else {
                    holder.setText(R.id.it_goods_num, model.getProductTotal()/model.getProductNumber()+"箱"+z+"个");
                }

            }
        };
        listView_stock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StockManagerContext, GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("PRODUCT", list.get(position - 1));
                intent.putExtras(bundle);
                StockManagerContext.startActivity(intent);
            }
        });
        listView_stock.setAdapter(adapter);
        TextView emptyView = new TextView(StockManagerContext);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有符合条件的查询，请重新查询");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)listView_stock.getParent()).addView(emptyView);
        listView_stock.setEmptyView(emptyView);


    }


    @Override
    public void SearchStock(String message, List<ViewStockModel> list) {
        myMes = message;
        addList = list;
        StockManagerContext.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            list.addAll(addList);
            listView_stock.onRefreshComplete();
            adapter.notifyDataSetChanged();
            if (!myMes.equals(""))
                Toast.makeText(StockManagerContext, myMes, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    /*    if (requestCode == 123 && requestCode == 123) {
            String etName = data.getStringExtra("NAME");
            String etScanId = data.getStringExtra("SCANID");

            String etType = data.getStringExtra("TYPE");
            stockModel.setProductName(etName);
            stockModel.setProductClass(etType);
            stockModel.setProductBarCode(etScanId);
            list.removeAll(list);
            num=1;
            listener.Search(stockModel, 1);
        }*/
        //当没有符合查询条件是，提醒用户。
       /* TextView emptyView = new TextView(StockManagerContext);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有符合条件的查询，请重新查询");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)listView_stock.getParent()).addView(emptyView);
        listView_stock.setEmptyView(emptyView);*/


    }


}
