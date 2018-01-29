package com.xiaohu.fireworkssystem.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.stock.StockSearchListener;
import com.xiaohu.fireworkssystem.control.stock.StockSearchView;
import com.xiaohu.fireworkssystem.model.search.SearchStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewStockModel;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.R.id.et_product_name;

//产品列表页面   胡海珍/张泽
public class GoodsListActivity extends Activity implements StockSearchView {
    //带刷新listview
    private PullToRefreshListView listView_goods_list;
    //listview数据源
    private CommonAdapter<ViewStockModel> adapter;
    //商品名称
    private EditText etProduct;
    //扫描
    private ImageView imgSearch;
    //列表显示库存集合
    private List<ViewStockModel> list;
    //选择产品集合
    private List<ViewStockModel> searchList;
    //产品列表Listener
    private StockSearchListener listener;
    //标题
    private MyTitleView title;
    //查询库存模型
    private SearchStockModel searchStockModel;
    //查询结果库存集合
    private List<ViewStockModel> addList;
    //已选产品列表
    private List<ViewStockModel> sealList;
    //页数
    private  int num = 1;
    //提示信息
    private String myMes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);
        listener = new StockSearchListener(this, GoodsListActivity.this);
        list = new ArrayList<>();
        addList = new ArrayList<>();
        sealList = new ArrayList<>();
        searchList = new ArrayList<>();
        sealList = (List<ViewStockModel>) getIntent().getSerializableExtra("ProductList");
        searchStockModel = new SearchStockModel();
       /* searchStockModel.setCompanyID("");*/
        searchStockModel.setProductName("");
        num=1;
        listener.Search(null, num);
        initView();
    }


    private void initDate() {
        adapter = new CommonAdapter<ViewStockModel>(GoodsListActivity.this, list, R.layout.lv_goods_seal_item2) {
            @Override
            public void convert(final ViewHolder holder, final ViewStockModel goodsModel, final int position) {
                holder.setText(R.id.seal_item_gname, goodsModel.getProductName());
                holder.setText(R.id.seal_goods_perice, goodsModel.getProductRetailPrice() + "/个");

               /* holder.setText(R.id.seal_goods_num, goodsModel.getProductTotal() + "");*/
                holder.setText(R.id.stock_goods_num, goodsModel.getProductTotal() + "个");

            }
        };
        listView_goods_list.setAdapter(adapter);
        //查询
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchStockModel = new SearchStockModel();
            /*    searchStockModel.setProductBarCode("");*/
                searchStockModel.setProductClass("");
                searchStockModel.setProductName(etProduct.getText().toString().trim());
                num=1;
                listener.Search(searchStockModel, num);
            }
        });
        etProduct.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchStockModel = new SearchStockModel();
                /*searchStockModel.setProductBarCode("");*/
                searchStockModel.setProductClass("");
                searchStockModel.setProductName(etProduct.getText().toString().trim());
                num=1;
                listener.Search(searchStockModel, 1);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(7, null);
                finish();
            }
        });
        //刷新
        listView_goods_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                num = 1;
                listener.Search(null, num);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                num++;
                listener.Search(searchStockModel, num);
            }
        });
    }

    private void initView() {
        listView_goods_list = (PullToRefreshListView) findViewById(R.id.listView_goods_list);
        listView_goods_list.setMode(PullToRefreshBase.Mode.BOTH);
        etProduct = (EditText) findViewById(et_product_name);
        imgSearch = (ImageView) findViewById(R.id.img_product_search);
        title = (MyTitleView) findViewById(R.id.goods_list_title);
        initDate();
    }

    //查询结果返回后刷新界面
    @Override
    public void SearchStock(String message, List<ViewStockModel> list) {

        myMes = message;
        addList = list;
        GoodsListActivity.this.runOnUiThread(run);
    }

    Runnable run =
            new Runnable() {
                @Override
                public void run() {
                    if (!myMes.equals(""))
                        Toast.makeText(GoodsListActivity.this, myMes, Toast.LENGTH_SHORT).show();
                    listView_goods_list.onRefreshComplete();
                    //如果是第一页，就清空所有数据
                    if (num == 1) {
                        list.removeAll(list);
                    }
                    //如果返回的库存产品的数量不为0则显示
                    for (int i = 0; i < addList.size(); i++) {
                        if (addList.get(i).getProductTotal() != 0) {
                            list.add(addList.get(i));
                        }
                    }
                 /*   //遍历返回的产品信息，看选择的产品是否库存还有，有就加显示选择，没有自动删除
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setMyNumber(list.get(i).getProductTotal());
                        //返回的库存产品的信息，默认选择一个
                        list.get(i).setProductTotal(1);
                        list.get(i).setCheck(false);
                        //遍历已经选择的产品信息
                        for (int j = 0; j < sealList.size(); j++) {
                            if (list.get(i).getProductID().equals(sealList.get(j).getProductID())) {
                                list.get(i).setCheck(true);
                                list.get(i).setProductTotal(sealList.get(j).getProductTotal());
                                list.get(i).setProductRetailPrice(sealList.get(j).getProductRetailPrice());
                                searchList.add(list.get(i));
                                break;
                            }
                        }
                    }*/
                    adapter.notifyDataSetChanged();
                }
            };


}
