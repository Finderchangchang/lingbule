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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
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
import com.xiaohu.fireworkssystem.ui.activity.AddEmployeeActivity;
import com.xiaohu.fireworkssystem.ui.activity.EmployeeDetailActivity;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.ui.activity.CompanyActivity.CompanyActivityContext;


public class EmployeeFragment extends Fragment implements CompanyEmployeeSearchView {

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
    private Utils utils;
    private View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_employee, container, false);

        initView();
        num=1;
        initListener();
        return view;
    }


    private void initListener() {

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
                Intent intent = new Intent(CompanyActivityContext, EmployeeDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Employee", list.get(position - 1));
                intent.putExtras(bundle);
                CompanyActivityContext.startActivity(intent);
            }
        });
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyActivityContext, AddEmployeeActivity.class);
                startActivity(intent);
            }
        });
    }


    private void initView() {
        utils = new Utils(CompanyActivityContext);
        button_add = (ImageButton) view.findViewById(R.id.imageView_addEmployee);
        list = new ArrayList<ViewEmployeeModel>();
        addList = new ArrayList<ViewEmployeeModel>();
        listener = new CompanyEmployeeSearchListener(this, CompanyActivityContext);

        searchEmployeeModel = new SearchEmployeeModel();
        searchEmployeeModel.setEmployeeName(utils.URLEncode(utils.ReadString("EmployeeName")));
        searchEmployeeModel.setEmployeerCertNumber(utils.URLEncode(utils.ReadString("CardNumber")));
        searchEmployeeModel.setEmployeeState(utils.URLEncode(utils.ReadString("EmployeeState")));
        searchEmployeeModel.setEmployeeType(utils.URLEncode(utils.ReadString("EmployeeType")));
        searchEmployeeModel.setCompanyID(utils.ReadString(COMPANYID));
        listener.Search(searchEmployeeModel, 1);

        listView_computerEmployee = (PullToRefreshListView) view.findViewById(R.id.lv_fragmentEmployee);
        listView_computerEmployee.setMode(PullToRefreshBase.Mode.BOTH);
        adapter = new CommonAdapter<ViewEmployeeModel>(CompanyActivityContext, list, R.layout.item_company_employee) {
            @Override
            public void convert(ViewHolder holder, ViewEmployeeModel viewEmployeeModel, int position) {
                String strIP = utils.ReadString("IP");
                holder.setGlideImage(R.id.imageView_header, getResources().getString(R.string.http)+"://" + strIP + "/Employee/GetHeadImage?HeadImageID=" + viewEmployeeModel.getEmployeeId());
                holder.setText(R.id.textView_name, viewEmployeeModel.getEmployeeName());
                holder.setText(R.id.textView_type, viewEmployeeModel.getEmployeeTypeName());
                holder.setText(R.id.textView_cardNumber, viewEmployeeModel.getEmployeerCertNumber());
                holder.setText(R.id.textView_state, viewEmployeeModel.getEmployeeStateName());
            }
        };
        listView_computerEmployee.setAdapter(adapter);
        TextView emptyView = new TextView(CompanyActivityContext);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有符合条件的查询，请重新查询");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)listView_computerEmployee.getParent()).addView(emptyView);
        listView_computerEmployee.setEmptyView(emptyView);
    }

    @Override
    public void SearchCompanyEmployee(String message, List<ViewEmployeeModel> list) {
        myMes = message;
        addList.removeAll(addList);
        addList.addAll(list);
        CompanyActivityContext.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            list.addAll(addList);
            listView_computerEmployee.onRefreshComplete();
            adapter.notifyDataSetChanged();
            if (!myMes.equals(""))
                Toast.makeText(CompanyActivityContext, myMes, Toast.LENGTH_SHORT).show();
        }
    };

}
