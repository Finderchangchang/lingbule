
package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.AddEmployeeSearch.AddEmployeeSearchListener;
import com.xiaohu.fireworkssystem.control.AddEmployeeSearch.AddEmployeeSearchView;
import com.xiaohu.fireworkssystem.control.setting.SettingListener;
import com.xiaohu.fireworkssystem.control.setting.SettingView;
import com.xiaohu.fireworkssystem.model.EmployeeModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewCredentialEmployeeModel;
import com.xiaohu.fireworkssystem.model.view.ViewEmployeeModel;
import com.xiaohu.fireworkssystem.utils.CodeByData;
import com.xiaohu.fireworkssystem.utils.IDCardUtils;
import com.xiaohu.fireworkssystem.utils.NativePlace;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.DatePickerDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;
import com.zkteco.android.IDReader.IDPhotoHelper;
import com.zkteco.android.IDReader.WLTService;
import com.zkteco.id300.IDCardReader;
import com.zkteco.id300.meta.IDCardInfo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;
import pub.devrel.easypermissions.EasyPermissions;

import static com.xiaohu.fireworkssystem.R.id.person_read;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_BlueToothAddress;


public class AddEmployeeActivity extends TakePhotoActivity implements AddEmployeeSearchView,SettingView {

    //添加人员listener
    private AddEmployeeSearchListener listener;
    //保存，读卡
    private Button button_save, button_read;
    //标题
    private MyTitleView titleView;
    //选择弹出框
    private SpinnerDialog dialog;
    //企业人员模型
    private EmployeeModel employeeModel;
    //字典类
    private CodeDao dao;
    //常用方法类
    private Utils utils;
    //读卡头像
    private Bitmap bmHeader = null;
    //证件照
    private Bitmap cerbm = null;
    //头像
    private ImageView imageView_person , img_Credential;

    //人员姓名，卡号，地址，电话，发证编码
    private EditText person_name, person_cardid, person_address, person_tel,person_CredentialId;
    //视图人员模型
    private ViewEmployeeModel viewEmployeeModel;
    //人员类型，民族，性别，状态,发证机关，发证类型，有效期
    private TextView textView_employeeType, textView_employeeNation, textView_employeeSex, textView_employeeState,textView_IssuingAuthority,
            textView_CredentialType,et_starttime;
    //时间选择控件
    private DatePickerDialog dateDialog;
    //读卡错误
    private static final int ERROR = 1;
    private static final int CERERROR = 3;
    //读卡成功
    private static final int SUCCESS = 2;
    private static final int CERSUCCESS = 4;

    //跳页变量
    private String strAPI = "AddEmployee";
    //提示信息
    private String myMes = "";
    private  boolean boo = false;
    private ViewCredentialEmployeeModel viewCredentialEmployeeModel;
    private ProgressDialog mProgressDialog;
    private LinearLayout ll_cerdential,readiobutton_cerdential;
    private RadioButton faren_yes,faren_no;

    //蓝牙读卡器部分
    private BluetoothAdapter mBluetoothAdapter;
    IDCardReader idCardReader = null;
    private ProgressDialog progressDialog;
    //监听器
    private SettingListener setlistener;
    private boolean terminalIsTrue = false;
    AddEmployeeActivity.WorkThread workThread;
    int index = 0;
    private TakePhoto takePhoto;
    private int i = 0;


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    Toast.makeText(AddEmployeeActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        initView();
        initSetValue();
        initListener();
    }

    private void initSetValue() {
        if (!(viewEmployeeModel == null)) {
            person_name.setText(viewEmployeeModel.getEmployeeName());
            person_cardid.setText(viewEmployeeModel.getEmployeerCertNumber());
            textView_employeeNation.setText(viewEmployeeModel.getNationName());
            textView_employeeSex.setText(viewEmployeeModel.getSexName());
            person_address.setText(viewEmployeeModel.getEmployeeAddress());
            person_tel.setText(viewEmployeeModel.getEmployeeMobilePhone());
            textView_employeeType.setText(viewEmployeeModel.getEmployeeTypeName());
            textView_employeeState.setText(viewEmployeeModel.getEmployeeStateName());
            person_CredentialId.setText(viewEmployeeModel.getCredentialId());
            textView_IssuingAuthority.setText(viewEmployeeModel.getIssuingAuthorityName());
            et_starttime.setText(viewEmployeeModel.getCredentialVailtyDate());
            textView_CredentialType.setText(viewEmployeeModel.getCredentialTypeName());
            textView_employeeState.setText(viewEmployeeModel.getEmployeeStateName());
          //  (!"".equals(person_cardid.getText().toString())&&null!=person_cardid.getText()) ||!"".equals(et_starttime.getText().toString()) || !"".equals(textView_IssuingAuthority.getText().toString()
            if ("库管人员".equals(textView_employeeType.getText().toString()) || "负责人".equals(textView_employeeType.getText().toString()) || "法定代表人".equals(textView_employeeType.getText().toString())
                    ||viewEmployeeModel.getCredentialEmployeeId() != null ){
                faren_yes.setChecked(true);
                ll_cerdential.setVisibility(View.VISIBLE);
            }else {
                faren_yes.setChecked(false);
                ll_cerdential.setVisibility(View.GONE);
            }

            initImage();
        }
    }

    private void initImage() {
        final String stringheaderid = viewEmployeeModel.getEmployeeId();
        new Thread() {
            public void run() {
                //获取okHttp对象get请求,
                try {
                    OkHttpClient client = new OkHttpClient();
                    //获取请求对象HeadImageID=13010000010058
                    String string = utils.ReadString("IP");
                    String url = getResources().getString(R.string.http)+"://" + string + "/Employee/GetHeadImage?HeadImageID=" + stringheaderid;
                    Request request = new Request.Builder().url(url).build();
                    //获取响应体
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


        final String stringcerid = viewEmployeeModel.getCredentialEmployeeId();
        if (null!=stringcerid  &&  !"".equals(stringcerid)){
        new Thread() {
            public void run() {
                //获取okHttp对象get请求,
                try {
                    OkHttpClient client = new OkHttpClient();
                    //获取请求对象HeadImageID=13010000010058
                    String string = utils.ReadString("IP");
                    String url = "http://" + string + "/Employee/GetCredentialEmployeeCredImage?CredImageID=" + stringcerid;
                    Request request = new Request.Builder().url(url).build();
                    //获取响应体
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

    private void initListener() {
        faren_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ll_cerdential.setVisibility(View.GONE);

            }
        });
        faren_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ll_cerdential.setVisibility(View.VISIBLE);
            }
        });
        img_Credential.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                 i = 2 ;

                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromCapture(imageUri);

            }
        });
        et_starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DatePickerDialog(AddEmployeeActivity.this, et_starttime.getText().toString());
                dateDialog.datePickerDialog(et_starttime);//直接改变edittext的控件的值
            }
        });
        et_starttime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Utils.compareDate(et_starttime.getText().toString(), et_starttime.getText().toString())) {
                    et_starttime.setText(et_starttime.getText().toString());
                }
            }
        });
        button_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取身份证
                progressDialog = ProgressDialog.show(AddEmployeeActivity.this, "", "正在读取身份证...", true, false);
                progressDialog.show();
                ConnectThread connectThread = new ConnectThread();
                connectThread.start();
            }
        });
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //image1.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.drawable.A).getConstantState()));
                if (imageView_person.getDrawable().getCurrent().getConstantState().equals(getResources().getDrawable(R.mipmap.touxiang).getConstantState())) {
                    Toast.makeText(AddEmployeeActivity.this, "请拍取照片", Toast.LENGTH_SHORT).show();
                } else if ("".equals(person_name.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请输入姓名", Toast.LENGTH_SHORT).show();
                } else if ("".equals(person_cardid.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请输入身份证号", Toast.LENGTH_SHORT).show();
                } else if (!IDCardUtils.IDCardValidate(person_cardid.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请检查身份证号", Toast.LENGTH_SHORT).show();
                } else if ("".equals(textView_employeeNation.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请输入民族", Toast.LENGTH_SHORT).show();
                } else if ("".equals(textView_employeeSex.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                } else if ("".equals(person_address.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请输入地址", Toast.LENGTH_SHORT).show();
                } else if ("".equals(person_tel.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请输入联系电话", Toast.LENGTH_SHORT).show();
                } else if (person_tel.getText().toString().length() != 11) {
                    Toast.makeText(AddEmployeeActivity.this, "请检查联系电话", Toast.LENGTH_SHORT).show();
                } else if ("请选择".equals(textView_employeeType.getText().toString())) {
                    Toast.makeText(AddEmployeeActivity.this, "请选择员工类型", Toast.LENGTH_SHORT).show();
                }else if ("法定代表人".equals(textView_employeeType.getText().toString()) && "离职".equals(textView_employeeState.getText().toString())){
                    Toast.makeText(AddEmployeeActivity.this, "法定代表人不可离职", Toast.LENGTH_SHORT).show();
                }else if ("负责人".equals(textView_employeeType.getText().toString()) && "离职".equals(textView_employeeState.getText().toString())){
                    Toast.makeText(AddEmployeeActivity.this, "负责人不可离职", Toast.LENGTH_SHORT).show();
                }
                else {

                    if (faren_yes.isChecked()){
                        if ("".equals(textView_IssuingAuthority.getText().toString())){
                            Toast.makeText(AddEmployeeActivity.this, "请选择发证机关", Toast.LENGTH_SHORT).show();
                        }else if ("".equals(textView_CredentialType.getText().toString())) {
                            Toast.makeText(AddEmployeeActivity.this, "请选择职业类型", Toast.LENGTH_SHORT).show();
                        }else if ("".equals(et_starttime.getText().toString())) {
                            Toast.makeText(AddEmployeeActivity.this, "请选择有效期", Toast.LENGTH_SHORT).show();
                        }else if ("".equals(person_CredentialId.getText().toString())) {
                            Toast.makeText(AddEmployeeActivity.this, "请填写发证编码", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            LayoutInflater layoutInflater = LayoutInflater.from(AddEmployeeActivity.this); // 创建视图容器并设置上下文
                            final View view = layoutInflater.inflate(R.layout.dialog_credential,null); // 获取list_item布局文件的视图
                            TextView textView_credentialId = (TextView) view.findViewById(R.id.textView_credentialId);
                            textView_credentialId.setText(person_CredentialId.getText().toString().trim());
                            TextView textView_starttime = (TextView) view.findViewById(R.id.textView_starttime);
                            textView_starttime.setText(et_starttime.getText().toString().trim());
                            TextView textView_issuingAuthority = (TextView) view.findViewById(R.id.textView_issuingAuthority);
                            textView_issuingAuthority.setText(textView_IssuingAuthority.getText().toString().trim());
                            TextView textView_credentialType = (TextView) view.findViewById(R.id.textView_credentialType);
                            textView_credentialType.setText(textView_CredentialType.getText().toString().trim());
                            new AlertDialog.Builder(AddEmployeeActivity.this).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    mProgressDialog.setTitle("请稍后");
                                    mProgressDialog.setMessage("正在提交数据");
                                    mProgressDialog.show();
                                    EmployeeListener();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();

                        }
                    }else {
                        mProgressDialog.setTitle("请稍后");
                        mProgressDialog.setMessage("正在提交数据");
                        mProgressDialog.show();
                        EmployeeListener();
                    }

                }
            }
        });

        textView_employeeType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeByData.getCodeByData(dao.QueryByName("CodeName", "Code_EmployeeType"), AddEmployeeActivity.this, dialog, textView_employeeType);
            }
        });
        textView_employeeNation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeByData.getCodeByData(dao.QueryByName("CodeName", "Code_Nation"), AddEmployeeActivity.this, dialog, textView_employeeNation);
            }
        });
        textView_employeeSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeByData.getCodeByData(dao.QueryByName("CodeName", "Code_Sex"), AddEmployeeActivity.this, dialog, textView_employeeSex);
            }
        });
        textView_employeeState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeByData.getCodeByData(dao.QueryByName("CodeName", "Code_EmployeeState"), AddEmployeeActivity.this, dialog, textView_employeeState);
            }
        });
        textView_IssuingAuthority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeByData.getCodeByData(dao.QueryByName("CodeName", "Code_Department"), AddEmployeeActivity.this, dialog, textView_IssuingAuthority);
            }
        });
        textView_CredentialType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CodeByData.getCodeByData(dao.QueryByName("CodeName", "Code_CretEmployeeType"), AddEmployeeActivity.this, dialog, textView_CredentialType);
            }
        });
        imageView_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                i = 0 ;

                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromCapture(imageUri);

            }
        });
     /*   if (viewEmployeeModel == null) {
            imageView_person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                    if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                    Uri imageUri = Uri.fromFile(file);
                     i = 0 ;
                    takePhoto.onPickFromCapture(imageUri);

                }
            });

        } else {
            imageView_person.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(AddEmployeeActivity.this, "头像不可修改", Toast.LENGTH_SHORT).show();
                }
            });
        }*/

        textView_employeeType.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if ("库管人员".equals(textView_employeeType.getText().toString()) || "负责人".equals(textView_employeeType.getText().toString()) || "法定代表人".equals(textView_employeeType.getText().toString())){
                    faren_yes.setChecked(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        person_cardid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                person_CredentialId.setFocusable(true);
                person_CredentialId.setFocusableInTouchMode(true);
            }
        });
        person_CredentialId.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    if (person_cardid.getText().toString().trim().length()>17){
                        person_CredentialId.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                            }

                            @Override
                            public void onTextChanged(CharSequence s, int start, int before, int count) {

                            }

                            @Override
                            public void afterTextChanged(Editable s) {
                                listener.SearchCredential(person_CredentialId.getText().toString().trim() );
                            }
                        });
                    }else {
                        Toast.makeText(AddEmployeeActivity.this, "请先添加身份证号码", Toast.LENGTH_SHORT).show();
                        person_CredentialId.setFocusable(false);
                        person_CredentialId.setFocusableInTouchMode(false);
                    }
                }
            }
        });

    }

    @Override
    public void getCodeResult(boolean isTrue, String mes) {

    }

    @Override
    public void getUserInfo(boolean isTrue, String mes) {

    }

    @Override
    public void getSamCode(boolean isTrue, String mes, String sam) {
        terminalIsTrue = isTrue;
        runOnUiThread(terminalRun);
    }

    Runnable terminalRun = new Runnable() {
        @Override
        public void run() {
            if (!terminalIsTrue) {
                Toast.makeText(AddEmployeeActivity.this, "设备信息未识别", Toast.LENGTH_SHORT).show();
                AddEmployeeActivity.this.finish();
            }else {


            }
        }
    };

    private class ConnectThread extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
            try {
                String ad = utils.ReadString(KEY_BlueToothAddress);
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(ad);

                connect(device);
            } catch (Exception e) {
                // TODO: handle exception
                mHandler.sendMessage(mHandler.obtainMessage(1, "连接失败"));
                progressDialog.dismiss();
            }
        }
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (index < 10) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!ReadCardInfo()) {
                            index++;
                            if (index == 9) {
                                progressDialog.dismiss();
                                idCardReader.closeDevice();
                                idCardReader = null;
                                mHandler.sendMessage(mHandler.obtainMessage(1, "读卡失败"));
                            } else {
                                //Toast.makeText(AddEmployeeActivity.this,"请放卡...",Toast.LENGTH_SHORT);
                            }
                        } else {
                            index = 11;
                            mHandler.sendMessage(mHandler.obtainMessage(1, "读卡成功"));
                        }
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void connect(BluetoothDevice device) {
        try {
            idCardReader = new IDCardReader();
            idCardReader.setDevice(device.getAddress());
            int ret = 0;
            if (IDCardReader.ERROR_SUCC == (ret = idCardReader.openDevice())) {
                //progressDialog.dismiss();
                //Toast.makeText(AddEmployeeActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                // ReadCardInfo();
                index = 0;
                workThread = new  AddEmployeeActivity.WorkThread();
                workThread.start();// 线程启动
            } else {
                idCardReader = null;
                progressDialog.dismiss();
                mHandler.sendMessage(mHandler.obtainMessage(1, "连接失败，请重启蓝牙身份证阅读器"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            idCardReader = null;
            progressDialog.dismiss();
            mHandler.sendMessage(mHandler.obtainMessage(1, "连接失败"));
        }
    }

    private boolean ReadCardInfo() {
        if (idCardReader != null) {
            if (!idCardReader.sdtFindCard() || !idCardReader.sdtSelectCard()) {

                return false;
            } else {
                final com.zkteco.id300.meta.IDCardInfo idCardInfo = new IDCardInfo();
                String sam = idCardReader.sdtSAMID();
                System.out.println("111sam"+sam);

                //验证设备
                setlistener.getTerminal(sam);


                if (idCardReader.sdtReadCard(1, idCardInfo)) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

                    textView_employeeNation.setText(idCardInfo.getNation());
                    person_name.setText(idCardInfo.getName());
                    person_cardid.setText(idCardInfo.getId());
                    person_address.setText(idCardInfo.getAddress());
                    textView_employeeSex.setText(idCardInfo.getSex());
                    employeeModel.setEmployeeCertType("11");
                    employeeModel.setEmployeeNation((queryCode("Code_Nation", idCardInfo.getNation())));


                    if (idCardInfo.getPhoto() != null) {
                        byte[] buf = new byte[WLTService.imgLength];
                        if (1 == WLTService.wlt2Bmp(idCardInfo.getPhoto(), buf)) {
                            Bitmap bitmap = IDPhotoHelper.Bgr2Bitmap(buf);
                            if (null != bitmap) {
                                bmHeader = bitmap;
                                imageView_person.setImageBitmap(bmHeader);
                            }
                        }
                    }
                    progressDialog.dismiss();
                    idCardReader.closeDevice();
                    idCardReader = null;
                    return true;
                } else {
                    return false;
                    //playSound(9, 0);
                }
            }
        } else {
            return false;
        }

    }



    private void initView() {

        takePhoto = getTakePhoto();
        listener = new AddEmployeeSearchListener(this, AddEmployeeActivity.this);
        setlistener = new SettingListener(AddEmployeeActivity.this,this);
        utils = new Utils(AddEmployeeActivity.this);
        viewEmployeeModel = (ViewEmployeeModel) getIntent().getSerializableExtra("Employee");
        employeeModel = new EmployeeModel();
        dao = new CodeDao(this);
        person_name = (EditText) findViewById(R.id.person_name);
        person_cardid = (EditText) findViewById(R.id.person_cardid);
        textView_employeeNation = (TextView) findViewById(R.id.textView_employeeNation);
        textView_employeeSex = (TextView) findViewById(R.id.textView_employeeSex);
        person_address = (EditText) findViewById(R.id.person_address);
        person_tel = (EditText) findViewById(R.id.person_tel);
        button_read = (Button) findViewById(person_read);
        titleView = (MyTitleView) findViewById(R.id.add_employee_title);
        button_save = (Button) findViewById(R.id.btn_person_view_save);
        textView_employeeType = (TextView) findViewById(R.id.textView_employeeType);
        textView_employeeState = (TextView) findViewById(R.id.textView_employeeState);
        imageView_person = (ImageView) findViewById(R.id.img_person);
        img_Credential = (ImageView) findViewById(R.id.img_Credential);
        person_CredentialId = (EditText) findViewById(R.id.person_CredentialId);
        textView_IssuingAuthority = (TextView) findViewById(R.id.textView_IssuingAuthority);
        textView_CredentialType = (TextView) findViewById(R.id.textView_CredentialType);
        et_starttime = (TextView) findViewById(R.id.et_starttime);
        et_starttime.setText(Utils.getNowDate());
        ll_cerdential = (LinearLayout) findViewById(R.id.ll_cerdential);
        readiobutton_cerdential = (LinearLayout) findViewById(R.id.readiobutton_cerdential);
        faren_yes = (RadioButton) findViewById(R.id.faren_yes);
        faren_no = (RadioButton) findViewById(R.id.faren_no);
        viewCredentialEmployeeModel = new ViewCredentialEmployeeModel();

        //进度条对话框
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        if (viewEmployeeModel!=null&& ("07".equals(viewEmployeeModel.getEmployeeType())||"08".equals(viewEmployeeModel.getEmployeeType()))){
            person_name.setEnabled(false);
            button_read.setEnabled(false);
            person_cardid.setEnabled(false);
            textView_employeeType.setEnabled(false);
        }
    }


    //向后台传送模型
    public void EmployeeListener() {
        if (viewEmployeeModel != null) {
            strAPI = "EditEmployee";
            employeeModel.setEmployeeId(viewEmployeeModel.getEmployeeId());
            if (null!=viewEmployeeModel.getCredentialId()){
                employeeModel.setCredentialEmployeeId(viewEmployeeModel.getCredentialEmployeeId());
            }else{
                employeeModel.setCredentialEmployeeId("");
            }


        } else {
            employeeModel.setEmployeeId("");
            employeeModel.setCredentialEmployeeId("");
            strAPI = "AddEmployee";
        }
        employeeModel.setCompanyId(utils.ReadString("companyID"));
        employeeModel.setEmployeeType(queryCode("Code_EmployeeType", textView_employeeType.getText().toString()));
        employeeModel.setEmployeeSex(queryCode("Code_Sex", textView_employeeSex.getText().toString()));
        employeeModel.setEmployeeName(person_name.getText().toString());
        employeeModel.setEmployeeMobilePhone(person_tel.getText().toString());
        employeeModel.setEmployeerCertNumber(person_cardid.getText().toString().trim());
        employeeModel.setEmployeeNation(queryCode("Code_Nation", textView_employeeNation.getText().toString()));
        employeeModel.setEmployeeAddress(NativePlace.getNativePlace(Integer.parseInt(person_cardid.getText().toString().trim().substring(0, 6))));
        employeeModel.setEmployeeAddress(person_address.getText().toString());
        employeeModel.setEmployeeState(queryCode("Code_EmployeeState", textView_employeeState.getText().toString()));
        employeeModel.setCredentialType(queryCode("Code_CretEmployeeType", textView_CredentialType.getText().toString()));
        employeeModel.setIssuingAuthority(queryCode("Code_Department", textView_IssuingAuthority.getText().toString()));
        employeeModel.setCredentialId(person_CredentialId.getText().toString().trim());
        employeeModel.setCredentialVailtyDate(et_starttime.getText().toString().trim());

        employeeModel.setCreateTime(Utils.getNowDate());
        /*if (bm != null) {
            employeeModel.setEmployeeHeadImageBase64(Utils.encodeBitmap(bm));
        }*/
        if (bmHeader != null) {
            employeeModel.setEmployeeHeadImageBase64(Utils.encodeBitmap(bmHeader));
        }
        if (cerbm != null) {
            employeeModel.setCredentialImageBase64(Utils.encodeBitmap(cerbm));
        }
        listener.Search(employeeModel, strAPI);
    }

    private String queryCode(String CodeName, String string) {
        List<CodeModel> list = dao.QueryByKName(CodeName, string);
        if (list.size() > 0) {
            return list.get(0).getCode();
        }
        return "";
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
                    bmHeader = (Bitmap) msg.obj;
                    break;
                case ERROR:
                    Toast.makeText(AddEmployeeActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    imageView_person.setImageResource(R.mipmap.person_header);
                    break;
                case CERSUCCESS:
                    img_Credential.setImageBitmap((Bitmap) msg.obj);
                    if (msg.obj == null) {
                        img_Credential.setImageResource(R.mipmap.touxiang);
                    }
                    cerbm = (Bitmap) msg.obj;
                    break;
                case CERERROR:
                    Toast.makeText(AddEmployeeActivity.this, "请求超时", Toast.LENGTH_SHORT).show();
                    img_Credential.setImageResource(R.mipmap.touxiang);
                    break;
            }
        }
    };



    @Override
    public void SearchAddEmployee(String message , boolean b) {
        mProgressDialog.dismiss();
        myMes = message;
        boo = b;
        AddEmployeeActivity.this.runOnUiThread(run);
    }

    @Override
    public void SearchEmployeecreId(ViewCredentialEmployeeModel model, String mes) {
          if (null!=model){
              viewCredentialEmployeeModel = model;
              AddEmployeeActivity.this.runOnUiThread(rn);
          }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (!myMes.equals("")) {

                Toast.makeText(AddEmployeeActivity.this, myMes, Toast.LENGTH_SHORT).show();
                if (boo){
                    finish();
                }
            }
        }
    };
    Runnable rn = new Runnable() {
        @Override
        public void run() {
            if (viewCredentialEmployeeModel.getCredentialEmployeerCertNumber() == person_cardid.getText().toString().trim()){
                textView_IssuingAuthority.setText(viewCredentialEmployeeModel.getDepartmentName());
                textView_CredentialType.setText(viewCredentialEmployeeModel.getCretEmployeeTypeName());
                et_starttime.setText(viewCredentialEmployeeModel.getCredentialData());
            }else {
                Toast.makeText(AddEmployeeActivity.this, "资格证号码与身份信息不符", Toast.LENGTH_SHORT).show();
                person_CredentialId.setText("");
            }

        }
    };


    //拍照设置
    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){
        takePhoto.onEnableCompress(null,false);

        int maxSize= Integer.parseInt("512000");
        int width= Integer.parseInt("800");
        int height= Integer.parseInt("800");
        boolean showProgressBar=false;
        boolean enableRawFile = true;
        CompressConfig config;
        config=new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width>=height? width:height)
                .enableReserveRaw(enableRawFile)
                .create();

        takePhoto.onEnableCompress(config,showProgressBar);


    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (i == 0){
            showImg(result.getImages());
        }else {
            showImg2(result.getImages());

        }

    }

    private void showImg(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getCompressPath());

            Glide.with(this).load(file).into(imageView_person);
            bmHeader = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }
    private void showImg2(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getCompressPath());
            Glide.with(this).load(file).into(img_Credential);
            cerbm = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}

