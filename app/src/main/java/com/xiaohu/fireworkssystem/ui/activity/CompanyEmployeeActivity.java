package com.xiaohu.fireworkssystem.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.CompanyEmployee.CompanyEmployeeSearchListener;
import com.xiaohu.fireworkssystem.control.CompanyEmployee.CompanyEmployeeSearchView;
import com.xiaohu.fireworkssystem.model.search.SearchEmployeeModel;
import com.xiaohu.fireworkssystem.model.view.ViewEmployeeModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;

public class CompanyEmployeeActivity extends Activity implements CompanyEmployeeSearchView {
    private MyTitleView titleView;
    private PullToRefreshListView listView_computerEmployee;
    private ImageButton button_add;
    private Intent intent;
    private List<ViewEmployeeModel> list;
    private List<ViewEmployeeModel> addList;
    private String myMes = "";
    private  int num = 1;
    private CompanyEmployeeSearchListener listener;
    private SearchEmployeeModel searchEmployeeModel;
    private CommonAdapter<ViewEmployeeModel> adapter;
    private static final int ERROR = 1;
    private static final int SUCCESS = 2;
    private ImageView imageView_person;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_computer_employee);
        initView();
        num=1;
        initListener();
    }

    private void initListener() {
        titleView.setRightOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setClass(CompanyEmployeeActivity.this, SearchComponyEmployeeActivity.class);
                startActivityForResult(intent, 6);
            }
        });
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView_computerEmployee.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.removeAll(list);
                num=1;
                listener.Search(searchEmployeeModel, 1);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                num++;
                listener.Search(searchEmployeeModel, num);
            }
        });
        listView_computerEmployee.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(CompanyEmployeeActivity.this, EmployeeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Employee", list.get(position - 1));
                intent.putExtras(bundle);
                CompanyEmployeeActivity.this.startActivity(intent);
            }
        });
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyEmployeeActivity.this, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        utils = new Utils(CompanyEmployeeActivity.this);
        titleView = (MyTitleView) findViewById(R.id.computer_employee_title);
        imageView_person = (ImageView) findViewById(R.id.imageView_header);
        button_add = (ImageButton) findViewById(R.id.imageView_addEmployee);
        list = new ArrayList<ViewEmployeeModel>();
        addList = new ArrayList<ViewEmployeeModel>();
        listener = new CompanyEmployeeSearchListener(this, CompanyEmployeeActivity.this);
        searchEmployeeModel = new SearchEmployeeModel();
        searchEmployeeModel.setEmployeeName("");
        searchEmployeeModel.setEmployeerCertNumber("");
        searchEmployeeModel.setEmployeeState("");
        searchEmployeeModel.setEmployeeType("");
        searchEmployeeModel.setCompanyID(utils.ReadString(COMPANYID));
        listener.Search(searchEmployeeModel, 1);
        listView_computerEmployee = (PullToRefreshListView) findViewById(R.id.lv_computerEmployee);
        listView_computerEmployee.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new CommonAdapter<ViewEmployeeModel>(this, list, R.layout.item_company_employee) {
            @Override
            public void convert(ViewHolder holder, ViewEmployeeModel viewEmployeeModel, int position) {
                String strIP = utils.ReadString("IP");
                holder.setGlideImage(R.id.imageView_header, "http://" + strIP + "/Employee/GetHeadImage?HeadImageID=" + viewEmployeeModel.getEmployeeId());
                holder.setText(R.id.textView_name, viewEmployeeModel.getEmployeeName());
                holder.setText(R.id.textView_type, viewEmployeeModel.getEmployeeTypeName());
                holder.setText(R.id.textView_cardNumber, viewEmployeeModel.getEmployeerCertNumber());
                holder.setText(R.id.textView_state, viewEmployeeModel.getEmployeeStateName());
            }
        };
        listView_computerEmployee.setAdapter(adapter);
    }

    @Override
    public void SearchCompanyEmployee(String message, List<ViewEmployeeModel> list) {
        myMes = message;
        addList.removeAll(addList);
        addList.addAll(list);
        CompanyEmployeeActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            list.addAll(addList);
            listView_computerEmployee.onRefreshComplete();
            adapter.notifyDataSetChanged();
            if (!myMes.equals(""))
                Toast.makeText(CompanyEmployeeActivity.this, myMes, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 6 & requestCode == 6) {
            String strEmployeeName = data.getStringExtra("EmployeeName");
            String strCardNumber = data.getStringExtra("CardNumber");
            String strEmployeeType = data.getStringExtra("EmployeeType");
            String strEmployeeState = data.getStringExtra("EmployeeState");
            System.out.println("==type:" + strEmployeeType + ":::--" + strEmployeeState);
            searchEmployeeModel.setEmployeeName(strEmployeeName);
            searchEmployeeModel.setEmployeerCertNumber(strCardNumber);
            searchEmployeeModel.setEmployeeType(strEmployeeType);
            searchEmployeeModel.setEmployeeState(strEmployeeState);
            searchEmployeeModel.setCompanyID(utils.ReadString(COMPANYID));
            list.removeAll(list);
            listener.Search(searchEmployeeModel, 1);
        } else {
            searchEmployeeModel.setEmployeeName("");
            searchEmployeeModel.setEmployeerCertNumber("");
            searchEmployeeModel.setEmployeeState("");
            searchEmployeeModel.setEmployeeType("");
            searchEmployeeModel.setCompanyID(utils.ReadString(COMPANYID));
            list.removeAll(list);
            listener.Search(searchEmployeeModel, 1);
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    imageView_person.setImageBitmap((Bitmap) msg.obj);
                    break;
                case ERROR:
                    Toast.makeText(CompanyEmployeeActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
