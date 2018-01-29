package com.xiaohu.fireworkssystem.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.Employee.EmployeeSearchView;
import com.xiaohu.fireworkssystem.control.Employee.EmpolyeeSearchListener;
import com.xiaohu.fireworkssystem.model.search.SearchEmployeeModel;
import com.xiaohu.fireworkssystem.model.view.ViewEmployeeModel;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.R.id.editText_name;

/*人员信息*/
public class EmployeeActivity extends Activity implements EmployeeSearchView {
    //下拉列表
    private PullToRefreshListView listView_employee;
    //适配器
    private CommonAdapter<ViewEmployeeModel> adapter;
    //模型列表
    private List<ViewEmployeeModel> list;
    //数据查询
    private EmpolyeeSearchListener listener;
    //数据列表
    private List<ViewEmployeeModel> returnlist;
    private List<ViewEmployeeModel> addList;
    //标题栏
    private MyTitleView title;
    //保存按钮
    private Button btnSave;
    //放大镜搜索
    private ImageView imgSearch;
    //输入从业人员姓名
    private EditText etName;
    private String names = "";
    private String ids = "";
    private String companyid = "";
    private boolean isOperate = false;
    private  int num = 1;
    private String myMes = "";
    private SearchEmployeeModel searchEmployeeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);
        isOperate = getIntent().getBooleanExtra("Operate", false);
        //初始化控件
        num = 1;
        initView();
        //添加数据
        initDate();
    }

    private void initDate() {
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchEmployeeModel.setEmployeeName(etName.getText().toString());
                listener.Search(searchEmployeeModel, num);
            }
        });
        listener.Search(searchEmployeeModel, num);
        adapter = new CommonAdapter<ViewEmployeeModel>(EmployeeActivity.this, list, R.layout.item_employee) {
            @Override
            public void convert(final ViewHolder holder, final ViewEmployeeModel model, final int position) {
                // String idd = "Applyer_XS20160905000005";
//                //idd=viewEmployeeModel.getEmployeeHeadImage()
//                String url = "http://192.168.1.113:6058/Employee/GetHeadImage?HeadImageID=" + idd;
//                holder.setGlideImage(R.id.item_e_img_header, url);
                holder.setText(R.id.textView_goods_name, model.getEmployeeName());
                holder.setText(R.id.textView_goods_unit, model.getEmployeeId() + "");
               holder.setText(R.id.textView_goods_number, model.getSexName());
                if (model.isCheck()) {
                    holder.setImageResource(R.id.imageView_box, R.mipmap.check_box_ture);
                } else {
                    holder.setImageResource(R.id.imageView_box, R.mipmap.check_box_false);
                }

                holder.setOnClickListener(R.id.ll_employee_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (addList == null) {
                            addList = new ArrayList<>();
                        }
                        if (model.isCheck()) {
                            list.get(position).setCheck(false);
                            holder.setImageResource(R.id.imageView_box, R.mipmap.check_box_false);
                            addList.remove(model);
                        } else {
                            if (isOperate) {
                                addList.removeAll(addList);
                            }
                            list.get(position).setCheck(true);
                            holder.setImageResource(R.id.imageView_box, R.mipmap.check_box_ture);
                            addList.add(model);
                            if (isOperate) {
                                Intent intent = new Intent();
                                intent.putExtra("IDS", model.getEmployeeId());
                                intent.putExtra("NAMES", model.getEmployeeName());
                                setResult(3, intent);
                                finish();
                            }
                        }
                    }
                });
            }
        };
        listView_employee.setAdapter(adapter);
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ids = "";
                names = "";
                Intent intent = new Intent();
                for (int i = 0; i < addList.size(); i++) {
                    if (names.equals("")) {
                        ids = addList.get(i).getEmployeeId();
                        names = addList.get(i).getEmployeeName();
                    } else {
                        ids = ids + "," + addList.get(i).getEmployeeId();
                        names = names + "," + addList.get(i).getEmployeeName();
                    }
                }
                intent.putExtra("IDS", ids);
                intent.putExtra("NAMES", names);
                setResult(3, intent);
                finish();
            }
        });
        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchEmployeeModel.setEmployeeName(etName.getText().toString());
                listener.Search(searchEmployeeModel, num);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ids = "";
                names = "";
                for (int i = 0; i < addList.size(); i++) {
                    if (names.equals("")) {
                        ids = addList.get(i).getEmployeeId();
                        names = addList.get(i).getEmployeeName();
                    } else {
                        ids = ids + "," + addList.get(i).getEmployeeId();
                        names = names + "," + addList.get(i).getEmployeeName();
                    }
                }
                intent.putExtra("IDS", ids);
                intent.putExtra("NAMES", names);
                setResult(3, intent);
                finish();
            }
        });
        listView_employee.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                num = 1;
                searchEmployeeModel.setEmployeeName(etName.getText().toString());
                listener.Search(searchEmployeeModel, num);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                num++;
                searchEmployeeModel.setEmployeeName(etName.getText().toString());
                listener.Search(searchEmployeeModel, num);
            }
        });
    }

    private void initView() {
        listener = new EmpolyeeSearchListener(this, EmployeeActivity.this);
        etName = (EditText) findViewById(editText_name);
        imgSearch = (ImageView) findViewById(R.id.img_search_employee);
        list = new ArrayList<>();
        returnlist = new ArrayList<>();
        addList = new ArrayList<>();
        ids = getIntent().getStringExtra("EmployeesIDs");
        companyid = getIntent().getStringExtra("companyid");
        searchEmployeeModel = new SearchEmployeeModel();
        if (null==companyid||"".equals(companyid)){

        }else {
            searchEmployeeModel.setCompanyID(companyid);
        }
        listView_employee = (PullToRefreshListView) findViewById(R.id.listView_employee);
        listView_employee.setMode(PullToRefreshBase.Mode.BOTH);
        title = (MyTitleView) findViewById(R.id.employee_list_title);
        btnSave = (Button) findViewById(R.id.btn_employee_save);

    }

    @Override
    public void EmployeeSearchView(String message, List<ViewEmployeeModel> list) {
        myMes = message;
        if (!isOperate) {
            String[] strids = ids.split(",");
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setCheck(false);
                for (int j = 0; j < strids.length; j++) {
                    if (strids[j].equals(list.get(i).getEmployeeId())) {
                        list.get(i).setCheck(true);
                        addList.add(list.get(i));
                        break;
                    }
                }
            }
        }
        returnlist = list;
        EmployeeActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (num == 1) {
                list.removeAll(list);
            }
            listView_employee.onRefreshComplete();
            for (int i = 0; i < returnlist.size(); i++) {
                if (returnlist.get(i).getEmployeeState().equals("01")) {
                    list.add(returnlist.get(i));
                }
            }
            if (!myMes.equals(""))
                Toast.makeText(EmployeeActivity.this, myMes, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
        }
    };
}
