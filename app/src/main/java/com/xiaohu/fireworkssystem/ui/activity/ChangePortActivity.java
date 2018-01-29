package com.xiaohu.fireworkssystem.ui.activity;

import android.content.ClipboardManager;
import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiaohu.fireworkssystem.config.MyKeys.SAM_CODE;
import static com.xiaohu.fireworkssystem.config.MyKeys.SAM_CODE_Result;

/*
修改端口页面
 */
public class ChangePortActivity extends BaseActivity   {
    //ip地址输入框
    private EditText etAddress;
    //标题栏
    private MyTitleView title;
    private TextView tvImei, tvVersion, tvSam, tvphoneImei, tvSAMresult;
    private String imei = "";
    //private DeviceListener listener;
    //应该为false
    private boolean samIsTrue = false;

    //初始化控件
    @Override
    public void initView() {
        setContentView(R.layout.activity_change_port);
        //ip地址
        etAddress = (EditText) findViewById(R.id.editText_IP);

        //标题
        title = (MyTitleView) findViewById(R.id.change_port_title);
        //描述符
        tvImei = (TextView) findViewById(R.id.port_imei);
        //版本号
        tvVersion = (TextView) findViewById(R.id.tv_version);
        //SAM码
        tvSam = (TextView) findViewById(R.id.tv_sam);
        tvSAMresult = (TextView) findViewById(R.id.change_port_sam);
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();
        //imei码
        tvphoneImei = (TextView) findViewById(R.id.phone_imei);
        tvphoneImei.setText(imei);
        //listener = new DeviceListener(this, ChangePortActivity.this);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(etAddress, InputMethodManager.SHOW_FORCED);
        String sam = utils.ReadString(SAM_CODE);
        tvSam.setText(sam);
        String shouquan = "";
        shouquan=Utils.getMYImei(((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId());
            tvImei.setText(shouquan);

    }

    @Override
    public void initEvent() {
        //标题栏向左尽头点击事件
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //读取ip地址
        etAddress.setText(utils.ReadString("IP"));
        if (etAddress.getText().toString().trim().equals("")) {
            etAddress.setText(getResources().getString(R.string.ip));
            utils.WriteString("IP",getResources().getString(R.string.ip));
        }
        tvVersion.setText(utils.getVersion());


        tvSam.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cmb.setText(tvSam.getText().toString());
                ToastShort("已复制到剪切板");
                return false;
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_change_port_save:
                String str = etAddress.getText().toString().trim();
                Pattern p= Pattern.compile("[a-zA-Z]");
                Matcher m=p.matcher(str);
                if (str.equals("")) {
                    ToastShort("请输入网络地址");
                } else if (!str.contains(".")) {
                    ToastShort("请检查网络地址");
                } else if (str.contains("：")) {
                    ToastShort("请检查网络地址");
                } else if(m.matches()){
                    ToastShort("请检查网络地址");
                }else {
                    if (!utils.ReadString("IP").equals(str)) {
                        utils.WriteString(SAM_CODE_Result, "false");
                        utils.WriteString("IP", str);
//                        String sam = utils.ReadString(SAM_CODE);
//                        if (!sam.equals("")) {
//                            listener.getTerminal(sam);
//                        }
                    }
                    finish();
                }
                break;
            case R.id.btn_clear:
                //etKey.setText("");
                etAddress.setText("");
                break;

        }
    }

    //创建终端授权码   (后台授权码)
    private boolean createTerminalLincesse(String terminalCode) {
        if (terminalCode.equals("") || terminalCode.length() != 14) {
            //终端授权码长度应为14位!
            ToastShort("终端授权码长度应为14位");
            return false;
        } else {
            //还原终端描述符对应值
            if (terminalCode.equals(Utils.getTerminalNumber(Utils.getMYImei(imei)))) {
                return true;
            } else {
                return false;
            }
        }
    }







}
