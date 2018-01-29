package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.seal.PersonView;
import com.xiaohu.fireworkssystem.control.searchcompany.SearchCompanyListListener;
import com.xiaohu.fireworkssystem.control.searchcompany.SearchCompanyListView;
import com.xiaohu.fireworkssystem.control.setting.SettingListener;
import com.xiaohu.fireworkssystem.control.setting.SettingView;
import com.xiaohu.fireworkssystem.model.table.OutputWareHouseModel;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerCompanyDialog;
import com.zkteco.id300.IDCardReader;
import com.zkteco.id300.meta.IDCardInfo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_BlueToothAddress;

/**
 * 购买人信息录入
 * Created by Administrator on 2016/8/10.
 */
public class PersonViewOutWareHouseActivity extends BaseActivity implements PersonView ,SettingView , SearchCompanyListView{

    private TextView person_name,person_cardid,person_users_operate,person_out,tv_companyname;
    private MyTitleView personout_view_title;
    private OutputWareHouseModel outputWareHouseModel;
    //公司名称
    private List<ViewCompanyModel> list_company ;
    //提示信息
    private String mes = "";
    private ProgressDialog mProgressDialog;
    private SearchCompanyListListener listener;


    //蓝牙读卡器部分
    private BluetoothAdapter mBluetoothAdapter;
    IDCardReader idCardReader = null;
    private ProgressDialog progressDialog;
    //监听器
    private SettingListener setlistener;
    private boolean terminalIsTrue = false;
    PersonViewOutWareHouseActivity.WorkThread workThread;
    int index = 0;


    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    Toast.makeText(PersonViewOutWareHouseActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }

            }
        }
    };
    @Override
    public void initView() {
        setContentView(R.layout.activity_person_view_out_warehouse);

        setlistener = new SettingListener(PersonViewOutWareHouseActivity.this , this);
        outputWareHouseModel = new OutputWareHouseModel();
        outputWareHouseModel = (OutputWareHouseModel) getIntent().getSerializableExtra("OutputWareHouseModel");
        personout_view_title = (MyTitleView) findViewById(R.id.personout_view_title);
        person_name = (TextView) findViewById(R.id.person_name);
        person_cardid = (TextView) findViewById(R.id.person_cardid);
        person_users_operate = (TextView) findViewById(R.id.person_out_operate);
        person_out = (TextView) findViewById(R.id.person_out);
        tv_companyname = (TextView) findViewById(R.id.tv_companyname);
        listener = new SearchCompanyListListener(this, PersonViewOutWareHouseActivity.this);
        list_company = new ArrayList<>();
        //进度条对话框
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
    }

    @Override
    public void initEvent() {

        if (null != outputWareHouseModel) {
            person_name.setText(outputWareHouseModel.getBoss());
            person_cardid.setText(outputWareHouseModel.getBossCertNumber());
        }

            //企业名称
            tv_companyname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (0<list_company.size()){
                        SpinnerCompanyDialog dialog = new SpinnerCompanyDialog(PersonViewOutWareHouseActivity.this);
                        dialog.setListView(list_company);
                        dialog.show();
                        dialog.setOnItemClick(new SpinnerCompanyDialog.OnItemClick() {
                            @Override
                            public void onClick(int position, ViewCompanyModel viewCompanyModel) {
                                tv_companyname.setText(viewCompanyModel.getCompanyName());
                                outputWareHouseModel.setTargetCompanyId(viewCompanyModel.getCompanyId());
                            }
                        });
                    }else {
                        ToastShort("请读取法人身份证或者无网络");
                    }

                }
            });
            //操作人员
            person_users_operate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null == outputWareHouseModel.getTargetCompanyId() || "".equals(outputWareHouseModel.getTargetCompanyId())){
                        ToastShort("请选择企业名称");
                    }else {
                        Intent intent = new Intent(PersonViewOutWareHouseActivity.this, EmployeeActivity.class);
                        intent.putExtra("EmployeesIDs", "");
                        intent.putExtra("Operate", true);
                        intent.putExtra("companyid", outputWareHouseModel.getTargetCompanyId());
                        startActivityForResult(intent, 31);

                    }

                }
            });

            //出库人员
            person_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(PersonViewOutWareHouseActivity.this, EmployeeActivity.class);
                    intent.putExtra("companyid", utils.ReadString("companyID"));
                    intent.putExtra("EmployeesIDs", "");
                    intent.putExtra("Operate", true);
                    startActivityForResult(intent, 32);
                }
            });

            personout_view_title.setLeftOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("OutputWareHouseModel", outputWareHouseModel);
                    intent.putExtras(bundle);
                    setResult(10,intent);
                    finish();
                }
            });
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.person_read:
                //读取身份证
                progressDialog = ProgressDialog.show(PersonViewOutWareHouseActivity.this, "", "正在读取身份证...", true, false);
                progressDialog.show();
                ConnectThread connectThread = new ConnectThread();
                connectThread.start();
                break;
            case R.id.btn_person_view_save:
                //保存
                if (Yan()) {
                    getData();
                    //返回主界面
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("OutputWareHouseModel", outputWareHouseModel);
                    intent.putExtras(bundle);
                    setResult(10, intent);
                    finish();
                } else {
                    //ToastShort("请填写购买人信息和操作人信息！");
                }
                break;

        }
    }

    @Override
    public void getCodeResult(boolean isTrue, String mes) {

    }

    @Override
    public void getUserInfo(boolean isTrue, String mes) {

    }

    @Override
    public void getSamCode(boolean isTrue, String mes, String sam) {
        this.mes = mes;
        terminalIsTrue = isTrue;
        runOnUiThread(terminalRun);
    }

    Runnable terminalRun = new Runnable() {
        @Override
        public void run() {
            if (!terminalIsTrue) {
                ToastShort("设备信息未识别！");
                PersonViewOutWareHouseActivity.this.finish();
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
                workThread = new  PersonViewOutWareHouseActivity.WorkThread();
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

                //验证设备
                setlistener.getTerminal(sam);


                if (idCardReader.sdtReadCard(1, idCardInfo)) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

                    OutputWareHouseModel outputWareHouseModel = new OutputWareHouseModel();
                    outputWareHouseModel.setBoss(idCardInfo.getName());
                    outputWareHouseModel.setBossCertNumber(idCardInfo.getId());
                    setPersonNation(outputWareHouseModel);



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




    private void setPersonNation(OutputWareHouseModel outputWareHouseModel) {

        /*SearchCompanyModel searchCompanyModel = new SearchCompanyModel();
        searchCompanyModel.setBossCertNumber(outputWareHouseModel.getBossCertNumber());*/
        person_name.setText(outputWareHouseModel.getBoss());
        person_cardid.setText(outputWareHouseModel.getBossCertNumber());
        listener.Search(outputWareHouseModel.getBossCertNumber());

    }






    private boolean Yan() {
        String Mymes = "";
        if (person_name.getText().toString().trim().equals("")) {
            Mymes = "请填写购买人信息   ！";
            Toast.makeText(PersonViewOutWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            return false;
        } else if (outputWareHouseModel.getTargetCompanyId() == null || outputWareHouseModel.getTargetCompanyId().equals("")) {
            Mymes = "请选择企业!";
            Toast.makeText(PersonViewOutWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        } else if (outputWareHouseModel.getOperatorId() == null || outputWareHouseModel.getOperatorId().equals("")) {
            Mymes = "请选择出库人员!";
            Toast.makeText(PersonViewOutWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }else if (outputWareHouseModel.getAgentId() == null || outputWareHouseModel.getAgentId().equals("")) {
            Mymes = "请选择操作人员!";
            Toast.makeText(PersonViewOutWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }
        return true;
    }

    private void getData() {
        if (outputWareHouseModel == null) {
            outputWareHouseModel = new OutputWareHouseModel();
        }
        outputWareHouseModel.setBoss(person_name.getText().toString());
        outputWareHouseModel.setBossCertNumber(person_cardid.getText().toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 31 && resultCode == 3) {
            outputWareHouseModel.setAgentId(data.getStringExtra("IDS") == null ? "" : data.getStringExtra("IDS"));
            person_users_operate.setText(data.getStringExtra("NAMES") == null ? "" : data.getStringExtra("NAMES"));
        }
        if (requestCode == 32 && resultCode == 3) {
            outputWareHouseModel.setOperatorId(data.getStringExtra("IDS") == null ? "" : data.getStringExtra("IDS"));
            person_out.setText(data.getStringExtra("NAMES") == null ? "" : data.getStringExtra("NAMES"));
        }
    }




    @Override
    public void CheckResult(boolean isTrue, String mes,String type) {
        if (mes != null) {
            if (!mes.equals(""))
                this.mes = mes;
            runOnUiThread(run);
        }
    }

    @Override
    public void SendVerificationCode(boolean boo,String mes) {

    }

    @Override
    public void CheckVerificationCode(boolean boo,String mes) {

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (!mes.equals("")) {
                person_name.setText("");
                person_cardid.setText("");
                Toast.makeText(PersonViewOutWareHouseActivity.this, mes, Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    public void getCompanyList(String string, List<ViewCompanyModel> list) {
        List<ViewCompanyModel> lists = new ArrayList<>();
        for (int i = 0 ; i<list.size();i++){
            if ("03".equals(list.get(i).getCompanyType())||"05".equals(list.get(i).getCompanyType())){
                lists.add(list.get(i));
            }
        }
        if (lists.size()<1){
            PersonViewOutWareHouseActivity.this.runOnUiThread(rn);
        }else {
            PersonViewOutWareHouseActivity.this.runOnUiThread(runn);
            list_company = lists;

        }
    }

    /*@Override
    public void Result(ViewCompanyModel model) {

    }

    @Override
    public void ResultList(String string, List<ViewCompanyModel> list) {
        List<ViewCompanyModel> lists = new ArrayList<>();
        for (int i = 0 ; i<list.size();i++){
            if ("03".equals(list.get(i).getCompanyType())){
                lists.add(list.get(i));
            }
        }
        if (lists.size()<1){
            PersonViewOutWareHouseActivity.this.runOnUiThread(rn);
        }else {
            PersonViewOutWareHouseActivity.this.runOnUiThread(runn);
            list_company = lists;

        }

    }
*/
    Runnable rn = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(PersonViewOutWareHouseActivity.this,"不是零售单位法人身份证",Toast.LENGTH_SHORT).show();
            person_name.setText("");
            person_cardid.setText("");
        }
    };
    Runnable runn = new Runnable() {
        @Override
        public void run() {
            if (null!=list_company.get(0).getCompanyName()) {
                tv_companyname.setText(list_company.get(0).getCompanyName());
                outputWareHouseModel.setTargetCompanyId(list_company.get(0).getCompanyId());
            }
        }
    };
}
