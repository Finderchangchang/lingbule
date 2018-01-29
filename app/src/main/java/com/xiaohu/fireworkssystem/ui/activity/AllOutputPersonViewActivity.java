package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.searchcompany.SearchCompanyListListener;
import com.xiaohu.fireworkssystem.control.searchcompany.SearchCompanyListView;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.zkteco.id300.IDCardReader;
import com.zkteco.id300.meta.IDCardInfo;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_BlueToothAddress;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

public class AllOutputPersonViewActivity extends AppCompatActivity implements SearchCompanyListView {

    //蓝牙读卡器部分
    private BluetoothAdapter mBluetoothAdapter;
    IDCardReader idCardReader = null;
    private MyTitleView personallout_view_title;
    private Button person_read,btn_person_view_save;
    private TextView person_name,person_cardid;
    private ProgressDialog progressDialog;
    private Utils utils;
    private int index = 0;
    private String tarcom = "";
    //进度框
    private ProgressDialog mProgressDialog;
    //公司名称
    private List<ViewCompanyModel> list_company ;
    IDCardInfo idCardInfo = new IDCardInfo();
    private SearchCompanyListListener listener;
    AllOutputPersonViewActivity.WorkThread workThread;
    private boolean boo = false;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    Toast.makeText(AllOutputPersonViewActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                    break;
                }
                case 2: {
                    idCardInfo = (IDCardInfo) msg.obj;
                    person_cardid.setText(idCardInfo.getId().toString());
                    person_name.setText(idCardInfo.getName().toString());

                    break;
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_output_person_view);

        initView();
        initListener();
    }

    private void initListener() {
        btn_person_view_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boo){
                    mProgressDialog.setTitle("请稍后");
                    mProgressDialog.setMessage("正在提交数据");
                    mProgressDialog.show();
                    OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
                    String stringIP = utils.ReadString("IP");
                    String str = "'{\"waitOutputBoxCodeId\":\""+getIntent().getStringExtra("WaitOutputBoxCodeId")+"\",\"userId\":\""+utils.ReadString("UserId")+"\"}'";
                    okHttpUtils.requestGetByAsyn(stringIP, "ConfirmOutput", str, new ReqCallBack() {
                                @Override
                                public void onReqSuccess(String result) {
                                    Gson gson = new Gson();
                                    ResultModel model = gson.fromJson(result, ResultModel.class);
                                     if(model.isSuccess()) {
                                        mHandler.sendMessage(mHandler.obtainMessage(1, "出库成功"));
                                        mProgressDialog.dismiss();
                                        finish();
                                    }else{
                                       mHandler.sendMessage(mHandler.obtainMessage(1, model.getMessage()));
                                        mProgressDialog.dismiss();
                                        finish();
                                    }
                                }
                                @Override
                                public void onReqFailed(String errorMsg) {
                                    mHandler.sendMessage(mHandler.obtainMessage(1, "网络错误"));
                                    mProgressDialog.dismiss();
                                    finish();
                                }
                            }
                    );
                }else {
                    mHandler.sendMessage(mHandler.obtainMessage(1, "请读取零售点从业人员或者法人身份证"));
                }
            }
        });
        personallout_view_title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

       person_read.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //读取身份证
               progressDialog = ProgressDialog.show(AllOutputPersonViewActivity.this, "", "正在读取身份证...", true, false);
               progressDialog.show();
               ConnectThread connectThread = new ConnectThread();
               connectThread.start();
           }
       });
    }

    private void initView() {
        tarcom = getIntent().getStringExtra("targetcompanyid");
        person_read = (Button) findViewById(R.id.person_read);
        btn_person_view_save = (Button) findViewById(R.id.btn_person_view_save);
        person_name = (TextView) findViewById(R.id.person_name);
        person_cardid = (TextView) findViewById(R.id.person_cardid);
        personallout_view_title = (MyTitleView) findViewById(R.id.personallout_view_title);
        utils = new Utils(this);
        listener  = new SearchCompanyListListener(this,AllOutputPersonViewActivity.this);
        list_company = new ArrayList<>();

        //进度条对话框
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void getCompanyList(String string, List<ViewCompanyModel> list) {
        if (list.size()>0){
            for (int i = 0 ; i < list.size();i++){
                if (list.get(i).getCompanyId().equals(tarcom)){
                    mHandler.sendMessage(mHandler.obtainMessage(2, idCardInfo));
                    boo = true;
                }
            }
            if (!boo){
                mHandler.sendMessage(mHandler.obtainMessage(1, "不是零售从业人员或者法人"));
            }
        }else {
            mHandler.sendMessage(mHandler.obtainMessage(1, "不是零售从业人员或者法人"));
        }
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
                workThread = new  AllOutputPersonViewActivity.WorkThread();
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

                String sam = idCardReader.sdtSAMID();
                System.out.println("111sam"+sam);

               /* //验证设备
                setlistener.getTerminal(sam);*/


                if (idCardReader.sdtReadCard(1, idCardInfo)) {
                    listener.Search(idCardInfo.getId().toString());
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


}
