package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.EmployeeModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewEmployeeModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

public class EmployeeDetailActivity extends AppCompatActivity {
    private TextView textView_EmployeeName, textView_EmployeeCardNumber, textView_EmployeeSex, textView_EmployeeNation, textView_EmployeeAddress,
            textView_EmployeeMobilePhone, textView_EmployeeState, textView_CreateTime, textView_EmployeeType,
            textView_CredentialId,textView_CredentialType,textView_IssuingAuthority,textView_CredentialVailtyDate;
    private ViewEmployeeModel viewEmployeeModel;
    private MyTitleView titleView;
    private Button button_leave, button_change;
    private EmployeeModel employeeModel;
    private Utils utils;
    private ImageView imageView_person,imageView_credential;
    private static final int ERROR = 1;
    private static final int SUCCESS = 2;
    private static final int CERERROR = 3;
    private static final int CERSUCCESS = 4;
    //进度框
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_detail);
        initView();
        initValue();
        initListener();
    }

    private void initListener() {
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("法定代表人".equals(textView_EmployeeType.getText().toString())){
                    Toast.makeText(EmployeeDetailActivity.this, "法定代表人不可离职", Toast.LENGTH_SHORT).show();
                }else if ("负责人".equals(textView_EmployeeType.getText().toString())){
                    Toast.makeText(EmployeeDetailActivity.this, "负责人不可离职", Toast.LENGTH_SHORT).show();
                }
                else {
                    dialog();
                }

            }
        });
        button_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeeDetailActivity.this, AddEmployeeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("Employee", viewEmployeeModel);
                intent.putExtras(bundle);
                EmployeeDetailActivity.this.startActivity(intent);
                finish();
            }
        });
    }
    private String queryCode(String CodeName, String string) {
        //字典类
        CodeDao dao = new CodeDao(this);
        List<CodeModel> list = dao.QueryByKName(CodeName, string);
        if (list.size() > 0) {
            return list.get(0).getCode();
        }
        return "";
    }
    private void initValue() {
        viewEmployeeModel = (ViewEmployeeModel) getIntent().getSerializableExtra("Employee");
        System.out.print(viewEmployeeModel.toString());
        textView_EmployeeName.setText(utils.isEmptyString2(viewEmployeeModel.getEmployeeName()));
        textView_EmployeeCardNumber.setText(utils.isEmptyString2(viewEmployeeModel.getEmployeerCertNumber()));
        textView_EmployeeType.setText(utils.isEmptyString2(viewEmployeeModel.getEmployeeTypeName()));
        textView_EmployeeSex.setText(utils.isEmptyString2(viewEmployeeModel.getSexName()));
        textView_EmployeeNation.setText(utils.isEmptyString2(viewEmployeeModel.getNationName()));
        textView_EmployeeAddress.setText(utils.isEmptyString2(viewEmployeeModel.getEmployeeAddress()));
        textView_EmployeeMobilePhone.setText(utils.isEmptyString2(viewEmployeeModel.getEmployeeMobilePhone()));
        textView_EmployeeState.setText(utils.isEmptyString2(viewEmployeeModel.getEmployeeStateName()));
        textView_CreateTime.setText(utils.isEmptyString2(viewEmployeeModel.getCreateTime()));
        textView_CredentialId.setText(utils.isEmptyString2(viewEmployeeModel.getCredentialId()));
        textView_CredentialType .setText(utils.isEmptyString2(viewEmployeeModel.getCredentialTypeName()));
        textView_IssuingAuthority .setText(utils.isEmptyString2(viewEmployeeModel.getIssuingAuthorityName()));
        textView_CredentialVailtyDate.setText(utils.isEmptyString2(viewEmployeeModel.getCredentialVailtyDate()));
        if (viewEmployeeModel.getEmployeeState().equals("02")) {
            button_leave.setVisibility(View.GONE);
        }
        final String stringheaderid = viewEmployeeModel.getEmployeeId();
        final  String stringcerid = viewEmployeeModel.getCredentialEmployeeId();
        new Thread() {
            public void run() {
                //获取okHttp对象get请求,
                try {
                    OkHttpClient client = new OkHttpClient();
                    //获取请求对象HeadImageID=13010000010058
                    String string = utils.ReadString("IP");
                    String url = getResources().getString(R.string.http)+"://" + string + "/Employee/GetHeadImage?HeadImageID=" + stringheaderid;
                    //证件照
                    // String url = "http://" + string + "/Employee/GetCredentialEmployeeCredImage?CredImageID=" + stringheaderid;
                    Request request = new Request.Builder().url(url).build();
                    //获取响0应体
                    ResponseBody body = client.newCall(request).execute().body();
                    //获取流
                    InputStream in = body.byteStream();
                    //转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    //使用Hanlder发送消息
                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    //失败
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
            }
        }.start();

        if (null!=stringcerid  &&  !"".equals(stringcerid)){
            new Thread() {
                public void run() {
                    //获取okHttp对象get请求,
                    try {
                        OkHttpClient client = new OkHttpClient();
                        String string = utils.ReadString("IP");
                        String url = getResources().getString(R.string.http)+"://" + string + "/Employee/GetCredentialEmployeeCredImage?CredImageID=" + stringcerid;
                        //证件照
                        Request request = new Request.Builder().url(url).build();
                        //获取响0应体
                        ResponseBody body = client.newCall(request).execute().body();
                        //获取流
                        InputStream in = body.byteStream();
                        //转化为bitmap
                        Bitmap bitmap = BitmapFactory.decodeStream(in);
                        //使用Hanlder发送消息
                        Message msg = Message.obtain();
                        msg.what = CERSUCCESS;
                        msg.obj = bitmap;
                        handler.sendMessage(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                        //失败
                        Message msg = Message.obtain();
                        msg.what = CERERROR;
                        handler.sendMessage(msg);
                    }
                }
            }.start();
        }

    }

    private void initView() {
        utils = new Utils(EmployeeDetailActivity.this);
        button_leave = (Button) findViewById(R.id.button_leave);
        button_change = (Button) findViewById(R.id.button_change);
        titleView = (MyTitleView) findViewById(R.id.computer_employee_detali_title);
        textView_EmployeeName = (TextView) findViewById(R.id.textView_EmployeeName);
        textView_EmployeeType = (TextView) findViewById(R.id.textView_EmployeeType);
        textView_EmployeeCardNumber = (TextView) findViewById(R.id.textView_EmployeeCardNumber);
        textView_EmployeeSex = (TextView) findViewById(R.id.textView_EmployeeSex);
        textView_EmployeeNation = (TextView) findViewById(R.id.textView_EmployeeNation);
        textView_EmployeeAddress = (TextView) findViewById(R.id.textView_EmployeeAddress);
        textView_EmployeeMobilePhone = (TextView) findViewById(R.id.textView_EmployeeMobilePhone);
        textView_EmployeeState = (TextView) findViewById(R.id.textView_EmployeeState);
        textView_CreateTime = (TextView) findViewById(R.id.textView_CreateTime);

        textView_CredentialId = (TextView) findViewById(R.id.textView_CredentialId);
        textView_CredentialType = (TextView) findViewById(R.id.textView_CredentialType);
        textView_IssuingAuthority = (TextView) findViewById(R.id.textView_IssuingAuthority);
        textView_CredentialVailtyDate = (TextView) findViewById(R.id.textView_CredentialVailtyDate);
        imageView_person = (ImageView) findViewById(R.id.imageView_employee);
        imageView_credential = (ImageView) findViewById(R.id.imageView_credential);
    }

    protected void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EmployeeDetailActivity.this);
        builder.setMessage("确认离职吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mProgressDialog = new ProgressDialog(EmployeeDetailActivity.this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
                saveModel();
                EmployeeDetailActivity.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    public void saveModel() {
        employeeModel = new EmployeeModel();
        //员工ID
        employeeModel.setEmployeeId(viewEmployeeModel.getEmployeeId());
        //公司ID
        employeeModel.setCompanyId(viewEmployeeModel.getCompanyId());
        //员工类型
        employeeModel.setEmployeeType(viewEmployeeModel.getEmployeeType());
        //员工性别
        employeeModel.setEmployeeSex(viewEmployeeModel.getEmployeeSex());
        //员工姓名
        employeeModel.setEmployeeName(viewEmployeeModel.getEmployeeName());
        //手机号
        employeeModel.setEmployeeMobilePhone(viewEmployeeModel.getEmployeeMobilePhone());
        //身份证号
        employeeModel.setEmployeerCertNumber(viewEmployeeModel.getEmployeerCertNumber());
        //民族
        employeeModel.setEmployeeNation(viewEmployeeModel.getEmployeeNation());
        //地址
        employeeModel.setEmployeeAddress(viewEmployeeModel.getEmployeeAddress());
        //状态
        employeeModel.setEmployeeState("02");
        //发证编码
        employeeModel.setCredentialId(viewEmployeeModel.getCredentialId());
        //发证类型
        employeeModel.setCredentialType(viewEmployeeModel.getCredentialType());
        //发证机关
        employeeModel.setIssuingAuthority(viewEmployeeModel.getIssuingAuthority());
        //有效期
        employeeModel.setCredentialVailtyDate(viewEmployeeModel.getCredentialVailtyDate());
        EmployeeListener();
    }

    public void EmployeeListener() {
        String strEmployeeName = "", strEmployeerCertNumber = "", strEmployeeID = "", strEmployeeState = "";
       /* if (!("".equals(employeeModel.getEmployeeName()))) {
            strEmployeeName = "\"EmployeeName\":\"" + employeeModel.getEmployeeName() + "\"";
        }
        if (!("".equals(employeeModel.getEmployeerCertNumber()))) {
            strEmployeerCertNumber = "\"EmployeerCertNumber\":\"" + employeeModel.getEmployeerCertNumber() + "\"";
        }*/
        if (!("".equals(employeeModel.getEmployeeState()))) {
            strEmployeeState = "\"EmployeeState\":\"" + "02" + "\"";
        }
        if (!("".equals(employeeModel.getEmployeeId()))) {
            strEmployeeID = "\"EmployeeID\":\"" + employeeModel.getEmployeeId() + "\"";
        }
        String str = "'{" + strEmployeeState + "," + strEmployeeID + "}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
        String stringIP = utils.ReadString("IP");
        okHttpUtils.requestGetByAsyn(stringIP, "EditEmployee", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onReqFailed(String errorMsg) {
                System.out.println(errorMsg);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    imageView_person.setImageBitmap((Bitmap) msg.obj);
                    if (msg.obj == null) {
                        imageView_person.setImageResource(R.mipmap.person_header);
                    }
                    break;
                case ERROR:
                    imageView_person.setImageResource(R.mipmap.person_header);
                    Toast.makeText(EmployeeDetailActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    break;
                case CERSUCCESS:
                    imageView_credential.setImageBitmap((Bitmap) msg.obj);
                    if (msg.obj == null) {
                        imageView_credential.setImageResource(R.mipmap.person_header);
                    }
                    break;
                case CERERROR:
                    imageView_credential.setImageResource(R.mipmap.person_header);
                    Toast.makeText(EmployeeDetailActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };
}
