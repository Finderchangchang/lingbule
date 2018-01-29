package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.control.setting.SettingListener;
import com.xiaohu.fireworkssystem.control.setting.SettingView;
import com.xiaohu.fireworkssystem.model.BlueToothModel;
import com.xiaohu.fireworkssystem.utils.DataCleanManager;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_PrintBlueToothAddress;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_PrintBlueToothName;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_SaoBlueToothAddress;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_SaoBlueToothName;

/*
 * 设置页面
 * fragment改成activity
 * zz
 * 2017/5/23
 */
public class SettingActivity extends AppCompatActivity implements SettingView {


    private RelativeLayout rl, rlBluetooth, rl_clear, rl_sao, rl_printbluetooth, camera_rl;
    private SettingListener listener;
    private String message = "下载失败";
    private SpinnerDialog Contentdialog;//选择
    private List<BlueToothModel> listBlue;
    private Utils utils;
    private View layout_changepassword, layout_port;
    private ProgressDialog progressDialog;
    private boolean isResult = false;
    private TextView textView_clear, tvBluetooth, tv_sao_name, tv_printbluetooth_name, camera_state_tv;
    private MyTitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        initView();
        initListener();
    }

    private void initView() {
        utils = new Utils(SettingActivity.this);
        titleView = (MyTitleView) findViewById(R.id.setting_title);
        layout_changepassword = findViewById(R.id.layout_changepassword);
        camera_rl = (RelativeLayout) findViewById(R.id.camera_rl);
        layout_port = findViewById(R.id.layout_port);
        textView_clear = (TextView) findViewById(R.id.textView_clear);
        rl_clear = (RelativeLayout) findViewById(R.id.rl_clear);
        rl = (RelativeLayout) findViewById(R.id.rl_refush_code);
        rlBluetooth = (RelativeLayout) findViewById(R.id.rl_bluetooth);
        tvBluetooth = (TextView) findViewById(R.id.tv_bluetooth_name);
        rl_printbluetooth = (RelativeLayout) findViewById(R.id.rl_printbluetooth);
        rl_sao = (RelativeLayout) findViewById(R.id.rl_sao);
        tv_sao_name = (TextView) findViewById(R.id.tv_sao_name);
        tv_sao_name.setText(utils.ReadString(KEY_SaoBlueToothName));
        tv_printbluetooth_name = (TextView) findViewById(R.id.tv_printbluetooth_name);
        tv_printbluetooth_name.setText(utils.ReadString(KEY_PrintBlueToothName));
        camera_state_tv = (TextView) findViewById(R.id.camera_state_tv);
        try {
            textView_clear.setText(DataCleanManager.getTotalCacheSize(SettingActivity.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
        listener = new SettingListener(this, SettingActivity.this);
        utils = new Utils(SettingActivity.this);
    }

    private void initListener() {

        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //蓝牙
        rlBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contentdialog = new SpinnerDialog(SettingActivity.this);
                getBluetooth();
            }
        });
        tvBluetooth.setText(utils.ReadString(MyKeys.KEY_BlueToothName));
        //蓝牙打印机
        rl_printbluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contentdialog = new SpinnerDialog(SettingActivity.this);
                getPrintBluetooth();
            }
        });
        tv_printbluetooth_name.setText(utils.ReadString(KEY_PrintBlueToothName));

        //更新字典
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = ProgressDialog.show(SettingActivity.this, "", "正在更新字典...", true, false);
                progressDialog.show();
                listener.RefushCode();
            }
        });
        if (utils.ReadString("isopen").equals("isopen")) {
            camera_state_tv.setText("打开");
        } else {
            camera_state_tv.setText("关闭");
        }
        //扫码设置
        rl_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contentdialog = new SpinnerDialog(SettingActivity.this);
                listBlue = new ArrayList<BlueToothModel>();
                listBlue.add(new BlueToothModel("扫码枪", "blue"));
                listBlue.add(new BlueToothModel("本机", "camera"));
                Contentdialog.setListView(listBlue);
                Contentdialog.show();
                Contentdialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                    @Override
                    public void onClick(int position, BlueToothModel val) {
                        tv_sao_name.setText(val.getDeviceName());
                        utils.WriteString(KEY_SaoBlueToothName, listBlue.get(position).getDeviceName());
                        utils.WriteString(KEY_SaoBlueToothAddress, listBlue.get(position).getDeviceAddress());
                    }
                });
            }
        });
        camera_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (utils.ReadString("isopen").equals("isopen")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("系统拍照功能将“关闭”，请确保拍照方向");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            utils.WriteString("isopen", "");
                            camera_state_tv.setText("关闭");
                        }
                    });
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                    builder.setTitle("提示");
                    builder.setMessage("系统拍照功能将“打开”，请确保拍照方向");
                    builder.setNegativeButton("取消", null);
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            utils.WriteString("isopen", "isopen");
                            camera_state_tv.setText("打开");
                        }
                    });
                    builder.show();
                }
            }
        });

        //清除缓存
        rl_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataCleanManager.clearAllCache(SettingActivity.this);
                Toast.makeText(SettingActivity.this, "清除缓存成功", Toast.LENGTH_SHORT).show();
                textView_clear.setText("0 K");

            }
        });

        //修改密码
        layout_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChangePasswordActivity.class));
            }
        });

        //修改端口
        layout_port.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, ChangePortActivity.class));
            }
        });
    }

    @Override
    public void getCodeResult(boolean isTrue, String mes) {
        progressDialog.cancel();
        message = mes;
        SettingActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(SettingActivity.this, message, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void getUserInfo(boolean isTrue, String mes) {

    }

    @Override
    public void getSamCode(boolean isTrue, String mes, String sam) {

    }

    /**
     * 获得蓝牙的相关信息
     */
    private void getBluetooth() {
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(SettingActivity.this, "不支持蓝牙设备！", Toast.LENGTH_SHORT).show();
        } else {
            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
            if (pairedDevices.size() == 0) {
                Toast.makeText(SettingActivity.this, "没有配对蓝牙设备！", Toast.LENGTH_SHORT).show();
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
                        tvBluetooth.setText(val.getDeviceName());
                        utils.WriteString(MyKeys.KEY_BlueToothName, listBlue.get(position).getDeviceName());
                        utils.WriteString(MyKeys.KEY_BlueToothAddress, listBlue.get(position).getDeviceAddress());
                    }
                });
            }
        } //name:ID300-0064--address:00:13:04:84:00:64
    }

    /**
     * 获得蓝牙打印机的相关信息
     */
    private void getPrintBluetooth() {
        BluetoothAdapter mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBtAdapter == null) {
            Toast.makeText(SettingActivity.this, "不支持蓝牙设备！", Toast.LENGTH_SHORT).show();
        } else {
            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
            if (pairedDevices.size() == 0) {
                Toast.makeText(SettingActivity.this, "没有配对蓝牙设备！", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS));
            }
            listBlue = new ArrayList<BlueToothModel>();
            for (BluetoothDevice device : pairedDevices) {
                listBlue.add(new BlueToothModel(device.getName(), device.getAddress()));
            }
            if (listBlue.size() > 0) {
                Contentdialog.setListView(listBlue);
                Contentdialog.show();
                Contentdialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                    @Override
                    public void onClick(int position, BlueToothModel val) {
                        tv_printbluetooth_name.setText(val.getDeviceName());
                        utils.WriteString(KEY_PrintBlueToothName, listBlue.get(position).getDeviceName());
                        utils.WriteString(KEY_PrintBlueToothAddress, listBlue.get(position).getDeviceAddress());
                    }
                });
            }
        }
    }
}
