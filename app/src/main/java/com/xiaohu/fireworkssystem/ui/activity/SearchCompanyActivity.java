package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.company.SearchCompanyListener;
import com.xiaohu.fireworkssystem.company.SearchCompanyView;
import com.xiaohu.fireworkssystem.model.search.SearchCompanyModel;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

/**
 * 选择查询企业人员信息
 * Created by Administrator on 2016/11/2.
 */

public class SearchCompanyActivity extends BaseActivity implements SearchCompanyView {
    private MyTitleView view;
    private EditText etcname;
    private PullToRefreshListView listview;
    private List<ViewCompanyModel> list;
    private CommonAdapter<ViewCompanyModel> adapter;
    private SearchCompanyListener listener;
    private int index = 1;
    private List<ViewCompanyModel> rlist;
    private SearchCompanyModel searchCompanyModel;
    private String type = "02";
    private ImageView imgSearch;

    @Override
    public void initView() {
        setContentView(R.layout.activity_search_company);
        view = (MyTitleView) findViewById(R.id.company_list_title);
        searchCompanyModel = new SearchCompanyModel();
        type = getIntent().getStringExtra("Type");
        imgSearch = (ImageView) findViewById(R.id.img_company_search);
        etcname = (EditText) findViewById(R.id.et_company_name);
        listview = (PullToRefreshListView) findViewById(R.id.lv_search_company);
        listview.setMode(PullToRefreshBase.Mode.BOTH);
        list = new ArrayList<ViewCompanyModel>();
        rlist = new ArrayList<ViewCompanyModel>();
        listener = new SearchCompanyListener(SearchCompanyActivity.this, this);
        if (getIntent().getStringExtra("CompanyID") == null) {
            searchCompanyModel.setParentCompanyID("");
        } else {
            searchCompanyModel.setParentCompanyID(getIntent().getStringExtra("CompanyID"));
        }
        searchCompanyModel.setCompanyName("");
        searchCompanyModel.setCompanyType(type);
        listener.searchCompany(searchCompanyModel, index);
    }

    @Override
    public void initEvent() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = 1;
                searchCompanyModel.setCompanyName(etcname.getText().toString().trim());
                listener.searchCompany(searchCompanyModel, index);
            }
        });
        adapter = new CommonAdapter<ViewCompanyModel>(this, list, R.layout.item_company) {
            @Override
            public void convert(ViewHolder holder, ViewCompanyModel viewCompany, int position) {
                holder.setText(R.id.item_company_name, viewCompany.getCompanyName());
                holder.setText(R.id.item_company_type, viewCompany.getCompanyTypeName());
            }
        };
        view.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("CompanyID", list.get(position - 1).getCompanyId());
                intent.putExtra("CompanyName", list.get(position - 1).getCompanyName());
                if (type.equals("02")) {
                    setResult(11, intent);
                } else {
                    setResult(12, intent);
                }
                finish();
            }
        });
        listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                index = 1;
                searchCompanyModel.setCompanyName("");
                listener.searchCompany(searchCompanyModel, index);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                index++;
                listener.searchCompany(searchCompanyModel, index);
            }
        });
    }

    @Override
    public void SearchResult(List<ViewCompanyModel> list) {
        rlist = list;
        runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (index == 1) {
                list.removeAll(list);
            }
            String nowDate = Utils.getNowDate().replace("T"," ");
            for (int i = 0; i < rlist.size(); i++) {
                ViewCompanyModel company = rlist.get(i);
                boolean isNum = Utils.compareDate(nowDate, company.getBusinessLicenseNumberValidity());
                boolean isVali = Utils.compareDate(nowDate, company.getLicenseNumberValidity());
                if (isNum || isVali) {
                    //System.out.println(company.getCompanyName()+"::"+company.getBusinessLicenseNumberValidity()+":::-----::"+company.getLicenseNumberValidity());
                } else {
                    list.add(rlist.get(i));
                    //System.out.println(company.getCompanyName()+"::"+company.getBusinessLicenseNumberValidity()+":::"+company.getLicenseNumberValidity());
                }
            }
            //list.addAll(rlist);
            listview.onRefreshComplete();
            adapter.notifyDataSetChanged();

        }
    };
}
