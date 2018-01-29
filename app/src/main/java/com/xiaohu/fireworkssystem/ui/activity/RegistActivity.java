package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.register.RegisterListener;
import com.xiaohu.fireworkssystem.control.register.RegisterView;
import com.xiaohu.fireworkssystem.control.setting.SettingView;
import com.xiaohu.fireworkssystem.model.table.RegisterModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_IMEI_MI;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;
import static com.xiaohu.fireworkssystem.config.MyKeys.UserName;

/**
 * 注册设备信息界面
 */
public class RegistActivity extends BaseActivity implements RegisterView, SettingView {
    //用户名，密码，IP地址，登录名，确认密码
    private EditText et_username, et_password, etip, etloginname, et_password2;
    //企业名称，授权码，描述符，零售单位
    private TextView tv_companyname, tv_shouquan, tv_miaoshufu, tv_lingshou;
    //注册
    private Button btn_reg;
    //标题
    private MyTitleView titleView;
    //注册模型
    private RegisterModel registerModel;
    private RegisterListener listener;
    //imei码
    private String imei = "";
    //提示信息
    private String mes = "";
    private String samstr = "";
    private String JMIMEI = "";

    public void initView() {
        setContentView(R.layout.activity_regist);
        registerModel = new RegisterModel();
        listener = new RegisterListener(RegistActivity.this, this);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        tv_companyname = (TextView) findViewById(R.id.tv_companyname);
        tv_shouquan = (TextView) findViewById(R.id.tv_shouquan);
        titleView = (MyTitleView) findViewById(R.id.register_title);
        tv_miaoshufu = (TextView) findViewById(R.id.tv_miaoshufu);
        etloginname = (EditText) findViewById(R.id.et_login_name);
        tv_lingshou = (TextView) findViewById(R.id.tv_lingshou);
        tv_shouquan = (TextView) findViewById(R.id.tv_shouquan);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        etip = (EditText) findViewById(R.id.et_ip);
        if (!utils.ReadString("IP").equals("")) {
            etip.setText(utils.ReadString("IP"));
        }
        imei = ((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                .getDeviceId();
        tv_miaoshufu.setText(Utils.getMYImei(imei));
        btn_reg = (Button) findViewById(R.id.btn_regist);
    }

    @Override
    public void initEvent() {
        tv_lingshou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ip = etip.getText().toString().trim();
                if (ip.equals("")) {
                    ToastShort("请输入网络地址");
                } else if (ip.contains("：")) {
                    ToastShort("请检查输入内容符号");
                } else {

                        Utils utils = new Utils(RegistActivity.this);
                        //保存userID，公司ID
                        utils.WriteString("IP", etip.getText().toString().trim());
                        Intent intent = new Intent(RegistActivity.this, SearchCompanyActivity.class);
                        intent.putExtra("Type", "03");
                        startActivityForResult(intent, 11);
                }
            }
        });

        tv_companyname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ip = etip.getText().toString().trim();
                if (ip.equals("")) {
                    ToastShort("请输入网络地址");
                } else if ((!ip.contains(":")) || (!ip.contains("."))) {
                    ToastShort("请检查输入内容符号");
                } else {
                    Utils utils = new Utils(RegistActivity.this);
                    //保存userID，公司ID
                    utils.WriteString("IP", etip.getText().toString().trim());
                    Intent intent = new Intent(RegistActivity.this, SearchCompanyActivity.class);
                    intent.putExtra("Type", "02");
                    startActivityForResult(intent, 11);
                }
            }
        });
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //注册
                btn_reg.setEnabled(false);
                if (yan()) {
                    getData();
                } else {
                    btn_reg.setEnabled(true);
                }
            }
        });
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getData() {
        //授权码
        registerModel.setAuthorizedCode("");
        registerModel.setAuthorizedType("APP");
        registerModel.setTerminalCode("");
        registerModel.setCreateTime(Utils.getNOWTime());
        registerModel.setDeviceCode(tv_miaoshufu.getText().toString().trim());
        registerModel.setLoginName(etloginname.getText().toString().trim());
        registerModel.setPassword(et_password.getText().toString().trim());
        registerModel.setUserName(et_username.getText().toString().trim());
        registerModel.setCVR(true);

    }

    private boolean yan() {
        if (null==registerModel.getCompanyId()){
            ToastShort("请选择单位名称！");
            return false;
        }
        else if (et_username.getText().toString().trim().equals("")) {
            ToastShort("请填写用户名称！");
            return false;
        } else if (etloginname.getText().toString().trim().equals("")) {
            ToastShort("请填写登录名称");
            return false;
        } else if (et_password.getText().toString().trim().equals("")) {
            ToastShort("请填写密码！");
            return false;
        } else if (et_password2.getText().toString().trim().equals("")) {
            ToastShort("请填写确认密码！");
            return false;
        } else if (!et_password2.getText().toString().trim().equals(et_password.getText().toString().trim())) {
            ToastShort("两次密码输入不一致,请重新输入！");
            return false;
        }
        //获取用户状态  启用，停用
        if ("零售单位".equals( tv_lingshou.getText())){
            listener.getRoles("批发");
        }else {
            listener.getRoles("零售");
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == 11) {

                tv_lingshou.setText("零售单位");
                registerModel.setCompanyId(data.getStringExtra("CompanyID"));
                tv_companyname.setText(data.getStringExtra("CompanyName"));
                registerModel.setCompanyName(data.getStringExtra("CompanyName"));
                registerModel.setCompanyType("02");
        }
        if (requestCode == 11 && resultCode == 12) {
            if (null!=data.getStringExtra("CompanyID")){
                tv_companyname.setText("批发单位");
                registerModel.setCompanyId(data.getStringExtra("CompanyID"));
                tv_lingshou.setText(data.getStringExtra("CompanyName"));
                registerModel.setCompanyName(data.getStringExtra("CompanyName"));
                registerModel.setCompanyType("03");
            }

        }
    }

    //注册返回结果
    @Override
    public void onResultRegister(boolean isTrue, String mes, String shouQuan) {
        if (isTrue) {
            this.mes = "注册成功，请等待确认！";
            JMIMEI = shouQuan;
            //保存相应信息
            utils.WriteString(KEY_TOKEN, JMIMEI);//授权码
            utils.WriteString(UserName, registerModel.getLoginName());//登录名
            utils.WriteString(KEY_IMEI_MI, registerModel.getDeviceCode());
            finish();
        } else {
            this.mes = mes;
        }
        runOnUiThread(runn);
    }

    Runnable runn = new Runnable() {
        @Override
        public void run() {
            btn_reg.setEnabled(true);
            tv_shouquan.setText(JMIMEI);
            ToastShort(mes);
        }
    };

    //返回用户状态
    @Override
    public void RolesResult(String rolesid) {
        registerModel.setUserRole(rolesid);
        runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (registerModel.getUserRole().equals("")) {
                ToastShort("注册失败");
                btn_reg.setEnabled(true);
            } else {
                listener.Register(registerModel);
            }
        }
    };

    @Override
    public void getCodeResult(boolean isTrue, String mes) {

    }

    @Override
    public void getUserInfo(boolean isTrue, String mes) {

    }

    @Override
    public void getSamCode(boolean isTrue, String mes , String sam) {

    }


}
