package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.model.BlueToothModel;
import com.xiaohu.fireworkssystem.model.CompanyModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.DatePickerDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;
import com.zkteco.id300.IDCardReader;
import com.zkteco.id300.meta.IDCardInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_BlueToothAddress;


public class LinRegisterActivity extends AppCompatActivity {
    public static LinRegisterActivity linthis;
    private MyTitleView titleView;
    private Button bt_next,bt_read;
    private TextView tv_Area,tv_Boss,tv_BossCertNumber,tv_CompanyType;
    private EditText  edit_CompanyName,edit_BossCredentialId,edit_LicenseNumber,edit_BusinessLicenseNumber,
            edit_CompanyAddress,edit_CompanyPhone;
    private CodeDao dao;
    private SpinnerDialog dialog;//选择
    private CompanyModel companyModel;
    private Utils utils;
    //时间
    private TextView tv_LicenseNumberValidity, tv_BusinessLicenseNumberValidity,tv_bluetooth_name;
    //时间选择控件
    private DatePickerDialog dateDialog;
    private RelativeLayout rl_bluetooth;
    private SpinnerDialog Contentdialog;//选择
    private List<BlueToothModel> listBlue;
    private ProgressDialog progressDialog;
    private BluetoothAdapter mBluetoothAdapter;
    IDCardReader idCardReader = null;
    LinRegisterActivity.WorkThread workThread;
    int index = 0;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    Toast.makeText(LinRegisterActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lin_register);

        initView();
        initListener();
        initValue();
    }

    private void initValue() {
        tv_LicenseNumberValidity.setText(Utils.getNowDate());
        tv_BusinessLicenseNumberValidity.setText(Utils.getNowDate());
    }


    private void initView() {
        linthis = this;
        titleView = (MyTitleView) findViewById(R.id.linregister_title);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_read = (Button) findViewById(R.id.bt_read);
        rl_bluetooth = (RelativeLayout) findViewById(R.id.rl_bluetooth);
        tv_bluetooth_name = (TextView) findViewById(R.id.tv_bluetooth_name);
        tv_CompanyType = (TextView) findViewById(R.id.tv_CompanyType);

        edit_CompanyName = (EditText) findViewById(R.id.edit_CompanyName);
        tv_Area = (TextView) findViewById(R.id.tv_Area);
        tv_LicenseNumberValidity = (TextView) findViewById(R.id.tv_LicenseNumberValidity);
        tv_BusinessLicenseNumberValidity = (TextView) findViewById(R.id.tv_BusinessLicenseNumberValidity);
        tv_Boss = (TextView) findViewById(R.id.tv_Boss);
        tv_BossCertNumber = (TextView) findViewById(R.id.tv_BossCertNumber);
        edit_BossCredentialId = (EditText) findViewById(R.id.edit_BossCredentialId);
        edit_LicenseNumber = (EditText) findViewById(R.id.edit_LicenseNumber);
        edit_BusinessLicenseNumber = (EditText) findViewById(R.id.edit_BusinessLicenseNumber);
        edit_CompanyAddress = (EditText) findViewById(R.id.edit_CompanyAddress);
        edit_CompanyPhone = (EditText) findViewById(R.id.edit_CompanyPhone);
        dao = new CodeDao(this);
        companyModel = new CompanyModel();
        utils = new Utils(this);
    }
    private void initListener() {
        tv_CompanyType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByData2(dao.QueryByName("CodeName", "Code_CompanyType"));
            }
        });
        bt_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_Boss.setText("");
                tv_BossCertNumber.setText("");
                //读取身份证
                progressDialog = ProgressDialog.show(LinRegisterActivity.this, "", "正在读取身份证...", true, false);
                progressDialog.show();
                ConnectThread connectThread = new ConnectThread();
                connectThread.start();
            }
        });
        rl_bluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contentdialog = new SpinnerDialog(LinRegisterActivity.this);
                getBluetooth();
            }
        });
        tv_LicenseNumberValidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DatePickerDialog(LinRegisterActivity.this, tv_LicenseNumberValidity.getText().toString());
                dateDialog.datePickerDialog(tv_LicenseNumberValidity);//直接改变edittext的控件的值
            }
        });

        tv_BusinessLicenseNumberValidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DatePickerDialog(LinRegisterActivity.this, tv_BusinessLicenseNumberValidity.getText().toString());
                dateDialog.datePickerDialog(tv_BusinessLicenseNumberValidity);//直接改变edittext的控件的值
            }
        });


        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //辖区
        tv_Area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByData(dao.QueryByName("CodeName", "Code_Area"));
            }
        });
        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(edit_CompanyName.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请输入企业名称",Toast.LENGTH_SHORT).show();
                }else if(null==companyModel.getCompanyType()){
                    Toast.makeText(LinRegisterActivity.this,"请选择企业类型",Toast.LENGTH_SHORT).show();
                }else if(null==companyModel.getArea()){
                    Toast.makeText(LinRegisterActivity.this,"请选择辖区",Toast.LENGTH_SHORT).show();
                }else if("".equals(tv_Boss.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请读取法人",Toast.LENGTH_SHORT).show();
                }else if("".equals(tv_BossCertNumber.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请读取法人证件号",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_BossCredentialId.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请输入法人上岗证号",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_LicenseNumber.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请输入许可证号",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_BusinessLicenseNumber.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请输入营业执照",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_CompanyAddress.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请输入企业地址",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_CompanyPhone.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请输入企业电话",Toast.LENGTH_SHORT).show();
                }else if(!utils.isMobileNO(edit_CompanyPhone.getText().toString().trim())){
                    Toast.makeText(LinRegisterActivity.this,"请输入正确企业电话",Toast.LENGTH_SHORT).show();
                }else {
                    companyModel.setLeader(tv_Boss.getText().toString().trim());
                    companyModel.setLeaderCertNumber(tv_BossCertNumber.getText().toString().trim());
                    companyModel.setLeaderCredentialId(edit_BossCredentialId.getText().toString().trim());
                    companyModel.setCompanyName(edit_CompanyName.getText().toString().trim());
                    companyModel.setBoss(tv_Boss.getText().toString().trim());
                    companyModel.setBossCertNumber(tv_BossCertNumber.getText().toString().trim());
                    companyModel.setBossCredentialId(edit_BossCredentialId.getText().toString().trim());
                    companyModel.setLicenseNumber(edit_LicenseNumber.getText().toString().trim());
                    companyModel.setLicenseNumberValidity(tv_LicenseNumberValidity.getText().toString().trim());
                    companyModel.setBusinessLicenseNumber(edit_BusinessLicenseNumber.getText().toString().trim());
                    companyModel.setBusinessLicenseNumberValidity(tv_BusinessLicenseNumberValidity.getText().toString().trim());
                    companyModel.setCompanyAddress(edit_CompanyAddress.getText().toString().trim());
                    companyModel.setCompanyPhone(edit_CompanyPhone.getText().toString().trim());
                    companyModel.setCVR(true);



                    Intent intent = new Intent();

                  /*  if (null!=cerbm){
                        intent.putExtra("License", Utils.encodeBitmap(cerbm));

                    }else {
                        intent.putExtra("License", "");
                    }
                    if (null!=busbm){
                        intent.putExtra("BusinessLicense", Utils.encodeBitmap(busbm));

                    }else {
                        intent.putExtra("BusinessLicense", "");
                    }*/

                    intent.putExtra("CompanyModel", companyModel);
                    intent.setClass(LinRegisterActivity.this,PasswordActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void getCodeByData(final List<CodeModel> list) {

        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(LinRegisterActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    companyModel.setArea(val.getDeviceAddress());
                    tv_Area.setText(val.getDeviceName());


                }
            });
        }
    }
    private void getCodeByData2(final List<CodeModel> list) {

        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(LinRegisterActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    companyModel.setCompanyType(val.getDeviceAddress());
                    tv_CompanyType.setText(val.getDeviceName());


                }
            });
        }
    }


    /**
     * 获得蓝牙的相关信息
     */
    private void getBluetooth() {
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(LinRegisterActivity.this, "不支持蓝牙设备！", Toast.LENGTH_SHORT).show();
        } else {
            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
            if (pairedDevices.size() == 0) {
                Toast.makeText(LinRegisterActivity.this, "没有配对蓝牙设备！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            }
            // blue_device_scale = new String[pairedDevices.size()];
            listBlue = new ArrayList<BlueToothModel>();
            //listAddress = new ArrayList<String>();
            for (BluetoothDevice device : pairedDevices) {
//                BlueToothModel blue = new BlueToothModel();
//                blue.setDeviceName(device.getName());
//                blue.setDeviceAddress(device.getAddress());
                listBlue.add(new BlueToothModel(device.getName(), device.getAddress()));
                // listAddress.add(device.getAddress());
                // blue_device_scale[count++] = device.getName() + "\n" + device.getAddress();
            }
            //蓝牙设置选择
            // MainActivity.mInstance.ToastShort("list size:"+listblue.size());
            if (listBlue.size() > 0) {
                Contentdialog.setListView(listBlue);
                Contentdialog.show();
                Contentdialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                    @Override
                    public void onClick(int position, BlueToothModel val) {
                        tv_bluetooth_name.setText(val.getDeviceName());
                        utils.WriteString(MyKeys.KEY_BlueToothName, listBlue.get(position).getDeviceName());
                        utils.WriteString(KEY_BlueToothAddress, listBlue.get(position).getDeviceAddress());
                    }
                });
            }
        } //name:ID300-0064--address:00:13:04:84:00:64
    }
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
                workThread = new LinRegisterActivity.WorkThread();
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
                final IDCardInfo idCardInfo = new IDCardInfo();
                String sam = idCardReader.sdtSAMID();
                System.out.println("111sam"+sam);

            /*    //验证设备
                setlistener.getTerminal(sam);*/


                if (idCardReader.sdtReadCard(1, idCardInfo)) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

                    tv_Boss.setText(idCardInfo.getName());
                    tv_BossCertNumber.setText(idCardInfo.getId());
                    companyModel.setTerminalCode(sam);

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
    private String queryCode(String CodeName,String string) {
        List<CodeModel> list = dao.QueryByKName(CodeName,string);

        if (list.size() > 0) {
            return list.get(0).getCode() ;
        }
        return "";

    }


}
