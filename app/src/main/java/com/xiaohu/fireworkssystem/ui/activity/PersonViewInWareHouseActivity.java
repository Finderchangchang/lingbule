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
import com.xiaohu.fireworkssystem.model.table.InputWareHouseModel;
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
 * Created by Administrator on 2017/7/11.
 */
public class PersonViewInWareHouseActivity extends BaseActivity implements PersonView , SettingView,SearchCompanyListView {

    private TextView person_name,person_cardid,person_users_operate,person_out,tv_incompanyname;
    private MyTitleView personout_view_title;
    private InputWareHouseModel inputWareHouseModel;
    //提示信息
    private String mes = "";
    private SearchCompanyListListener listener;
    //公司名称
    private List<ViewCompanyModel> list_company ;

    //蓝牙读卡器部分
    private BluetoothAdapter mBluetoothAdapter;
    IDCardReader idCardReader = null;
    private ProgressDialog progressDialog;
    //监听器
    private SettingListener setlistener;
    private boolean terminalIsTrue = false;
    PersonViewInWareHouseActivity.WorkThread workThread;
    int index = 0;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1: {
                    Toast.makeText(PersonViewInWareHouseActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
                }

            }
        }
    };

    @Override
    public void initView() {
        setContentView(R.layout.activity_person_view_in_warehouse);

        inputWareHouseModel = new InputWareHouseModel();
        inputWareHouseModel = (InputWareHouseModel) getIntent().getSerializableExtra("InputWareHouseModel");
        personout_view_title = (MyTitleView) findViewById(R.id.personin_view_title);
        person_name = (TextView) findViewById(R.id.person_name);
        person_cardid = (TextView) findViewById(R.id.person_cardid);
        person_users_operate = (TextView) findViewById(R.id.person_out_operate);
        person_out = (TextView) findViewById(R.id.person_out);
        tv_incompanyname = (TextView) findViewById(R.id.tv_incompanyname);
        list_company = new ArrayList<>();
        listener = new SearchCompanyListListener(this, PersonViewInWareHouseActivity.this);
        setlistener = new SettingListener(PersonViewInWareHouseActivity.this ,this);
    }

    @Override
    public void initEvent() {

        if (null != inputWareHouseModel) {
            person_name.setText(inputWareHouseModel.getBoss());
            person_cardid.setText(inputWareHouseModel.getBossCertNumber());
        }
        //公司
        tv_incompanyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (0<list_company.size()){
                    SpinnerCompanyDialog dialog = new SpinnerCompanyDialog(PersonViewInWareHouseActivity.this);
                    dialog.setListView(list_company);
                    dialog.show();
                    dialog.setOnItemClick(new SpinnerCompanyDialog.OnItemClick() {
                        @Override
                        public void onClick(int position, ViewCompanyModel viewCompanyModel) {
                            tv_incompanyname.setText(viewCompanyModel.getCompanyName());
                            inputWareHouseModel.setCompanyId(viewCompanyModel.getCompanyId());
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
                if (null == inputWareHouseModel.getCompanyId() || "".equals(inputWareHouseModel.getCompanyId())){
                    ToastShort("请选择企业名称");
                }else {
                    Intent intent = new Intent(PersonViewInWareHouseActivity.this, EmployeeActivity.class);
                    intent.putExtra("EmployeesIDs", "");
                    intent.putExtra("Operate", true);
                    intent.putExtra("companyid", inputWareHouseModel.getCompanyId());
                    startActivityForResult(intent, 31);

                }
            }
        });

        //出库人员
        person_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonViewInWareHouseActivity.this, EmployeeActivity.class);
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
                bundle.putSerializable("InputWareHouseModel", inputWareHouseModel);
                intent.putExtras(bundle);
                setResult(20,intent);
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.person_read:
                //读取身份证
                progressDialog = ProgressDialog.show(PersonViewInWareHouseActivity.this, "", "正在读取身份证...", true, false);
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
                    bundle.putSerializable("InputWareHouseModel", inputWareHouseModel);
                    intent.putExtras(bundle);
                    setResult(20, intent);
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
                PersonViewInWareHouseActivity.this.finish();
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
                workThread = new  PersonViewInWareHouseActivity.WorkThread();
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

                    InputWareHouseModel inputWareHouseModel = new InputWareHouseModel();
                    inputWareHouseModel.setBoss(idCardInfo.getName());
                    inputWareHouseModel.setBossCertNumber(idCardInfo.getId());
                    setPersonNation(inputWareHouseModel);



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



    private void setPersonNation(InputWareHouseModel inputWareHouseModel) {

           /* listener.CheckPerson(model);*/
        /*SearchCompanyModel searchCompanyModel = new SearchCompanyModel();
        searchCompanyModel.setBossCertNumber(inputWareHouseModel.getBossCertNumber());*/
        person_name.setText(inputWareHouseModel.getBoss());
        person_cardid.setText(inputWareHouseModel.getBossCertNumber());
        listener.Search(inputWareHouseModel.getBossCertNumber());



    }

    private boolean Yan() {
        String Mymes = "";
        if (person_name.getText().toString().trim().equals("")) {
            Mymes = "请填写购买人信息   ！";
            Toast.makeText(PersonViewInWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }else if (inputWareHouseModel.getCompanyId() == null || inputWareHouseModel.getCompanyId().equals("")) {
            Mymes = "请选择企业!";
            Toast.makeText(PersonViewInWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        } else if (inputWareHouseModel.getOperatorId() == null || inputWareHouseModel.getOperatorId().equals("")) {
            Mymes = "请选择入库人员!";
            Toast.makeText(PersonViewInWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }else if (inputWareHouseModel.getAgentId() == null || inputWareHouseModel.getAgentId().equals("")) {
            Mymes = "请选择操作人员!";
            Toast.makeText(PersonViewInWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }
        return true;
    }

    private void getData() {
        if (inputWareHouseModel == null) {
            inputWareHouseModel = new InputWareHouseModel();
        }
        inputWareHouseModel.setBoss(person_name.getText().toString());
        inputWareHouseModel.setBossCertNumber(person_cardid.getText().toString());
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 31 && resultCode == 3) {
            inputWareHouseModel.setAgentId(data.getStringExtra("IDS") == null ? "" : data.getStringExtra("IDS"));
            person_users_operate.setText(data.getStringExtra("NAMES") == null ? "" : data.getStringExtra("NAMES"));
        }
        if (requestCode == 32 && resultCode == 3) {
            inputWareHouseModel.setOperatorId(data.getStringExtra("IDS") == null ? "" : data.getStringExtra("IDS"));
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
                Toast.makeText(PersonViewInWareHouseActivity.this, mes, Toast.LENGTH_SHORT).show();
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
            PersonViewInWareHouseActivity.this.runOnUiThread(rn);
        }else {
            PersonViewInWareHouseActivity.this.runOnUiThread(runn);
            list_company = lists;

        }
    }

/*    @Override
    public void ResultList(String string, List<ViewCompanyModel> list) {
        List<ViewCompanyModel> lists = new ArrayList<>();
        for (int i = 0 ; i<list.size();i++){
            if ("03".equals(list.get(i).getCompanyType())){
                lists.add(list.get(i));
            }
        }
        if (lists.size()<1){
            PersonViewInWareHouseActivity.this.runOnUiThread(rn);
        }else {
            PersonViewInWareHouseActivity.this.runOnUiThread(runn);
            list_company = lists;

        }

    }*/

    Runnable rn = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(PersonViewInWareHouseActivity.this,"不是零售单位法人身份证",Toast.LENGTH_SHORT).show();
            person_name.setText("");
            person_cardid.setText("");
        }
    };
    Runnable runn = new Runnable() {
        @Override
        public void run() {
            if (null!=list_company.get(0).getCompanyName()){
                tv_incompanyname.setText(list_company.get(0).getCompanyName());
                inputWareHouseModel.setCompanyId(list_company.get(0).getCompanyId());
            }

        }
    };
}
