package com.xiaohu.fireworkssystem.ui.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.CheckAPKView;
import com.xiaohu.fireworkssystem.control.setting.SettingListener;
import com.xiaohu.fireworkssystem.control.setting.SettingView;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.table.RegisterModel;
import com.xiaohu.fireworkssystem.utils.CircularJumpLoadingAnim;
import com.xiaohu.fireworkssystem.utils.ConnectionDetector;
import com.xiaohu.fireworkssystem.utils.SearchNewApkListener;
import com.xiaohu.fireworkssystem.utils.UpdateManager;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.LLDialog;
import com.xiaohu.fireworkssystem.view.MyPasswordDialog;

import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_IMEI_MI;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;
import static com.xiaohu.fireworkssystem.config.MyKeys.ServerDate;
import static com.xiaohu.fireworkssystem.config.MyKeys.TISHI;

/*
登录页面
 */
public class LoginActivity extends Activity implements SettingView, CheckAPKView {
    //登录按钮
    private Button bt_login;
    //登录动画
    private CircularJumpLoadingAnim circularJumpLoadingAnim;
    //用户名，密码输入框
    private EditText editText_password, editText_name;
    //设置ip按钮
    private ImageView imageView_settingIP;
    //是否保存密码按钮
    private CheckBox checkBox;
    //工具类
    private Utils utils = new Utils(LoginActivity.this);
    //监听器
    private SettingListener listener;
    //提示信息
    private String mes = "";
    //标签
    private int TAG = 2;
    //联网监听
    ConnectionDetector cd;
    //是否联网
    Boolean isInternetPresent = false;
    //登录
    private ImageView ivBottom;
    //是否登录成功s
    private boolean isResult = false;
    private SearchNewApkListener searchNewApkListener;
    //更新提示
    private UpdateManager mUpdateManager;
    private String Version = "";
    public static LoginActivity loginmin;
    //登录返回的企业信息
    private RegisterModel userModel;
    private Button button_linregister;
    private LLDialog llDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginmin = this;

        initView();
        initListener();

        //字典查询
        CodeDao dao = new CodeDao(LoginActivity.this);
        List<CodeModel> list = dao.QueryAll();
        if (list.size() == 0) {
            //字典查询
            String[] codes = getResources().getStringArray(R.array.RegCodeNameList);
            for (String s : codes) {
                CodeModel model = new CodeModel();
                model.setCodeName(s);
                dao.delete(model);
            }
            listener.getRegCode();
        }
    }

    //判断版本更新
    private void initNewApk() {
        searchNewApkListener = new SearchNewApkListener(LoginActivity.this, this);
        utils = new Utils(LoginActivity.this);
        //现版本号

        Version = searchNewApkListener.getVersion();
        if (Version.equals("获取版本号失败")) {
            Toast.makeText(LoginActivity.this, Version, Toast.LENGTH_SHORT).show();
        } else {
            searchNewApkListener.checkApkUrl();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initNewApk();
    }

    public void checkVersion(String version) {
        if (version.equals("")) {
        } else {
            if (!Version.equals(version)) {
                //这里来检测版本是否需要更新
                Version = version;
                runOnUiThread(update);
            }
        }
    }

    Runnable update = new Runnable() {
        @Override
        public void run() {
            mUpdateManager = new UpdateManager(LoginActivity.this, Version);
            mUpdateManager.checkUpdateInfo();
        }
    };

    private void initView() {
        llDialog = new LLDialog(this);
        //用户信息
        userModel = new RegisterModel();
        //联网监听
        cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent = cd.isConnectingToInternet(); // true or false
        //监听器
        listener = new SettingListener(this, LoginActivity.this);
        //选择是否保存账号
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        //图片空间
        imageView_settingIP = (ImageView) findViewById(R.id.imageView_settingIP);
        //用户名输入框
        editText_name = (EditText) findViewById(R.id.editText_name);
        //将用户登录名添加到输入框
        editText_name.setText(utils.ReadString("username"));
        ivBottom = (ImageView) findViewById(R.id.iv_bottom);
        if ("".equals(editText_name.getText())) {
            checkBox.setChecked(false);
        } else {
            checkBox.setChecked(true);
        }
        //密码输入框
        editText_password = (EditText) findViewById(R.id.editText_password);
        //将用户登录密码添加到输入框
        editText_password.setText(utils.ReadString("password"));
        //开始按钮
        bt_login = (Button) findViewById(R.id.bt_login);
        //动画控件
        circularJumpLoadingAnim = (CircularJumpLoadingAnim) findViewById(R.id.circularjump);
        button_linregister = (Button) findViewById(R.id.button_linregister);
    }

    private void initListener() {
        // check for Internet status
        if (isInternetPresent) {
            initNewApk();
        } else {
            showAlertDialog(LoginActivity.this, "网络连接失败",
                    "请检查网络是否正常", false);
        }
        //设置ip点击事件
        imageView_settingIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ChangePortActivity.class);
                LoginActivity.this.startActivityForResult(intent, TAG);
            }
        });

        //登录按钮点击事件
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           /*     circularJumpLoadingAnim.setVisibility(View.VISIBLE);
                circularJumpLoadingAnim.startAnim();
                utils.WriteString("username", editText_name.getText().toString().trim());
                utils.WriteString("password", editText_password.getText().toString().trim());
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,Main2Activity.class);
                startActivity(intent);
                finish();*/

                bt_login.setEnabled(false);
                circularJumpLoadingAnim.setVisibility(View.VISIBLE);
                circularJumpLoadingAnim.startAnim();
                LOGo();

            }
        });
        button_linregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this, LinRegisterActivity.class), 11);
            }
        });

        ivBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new HideClick().start();
                if (HideClick.sIsAlive == 4) {
                    final MyPasswordDialog dialog = new MyPasswordDialog(LoginActivity.this);
                    HideClick.sIsAlive = 0;
                    dialog.show();
                    dialog.setExit(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setSave(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // if (dialog.getPSW().equals(Utils.GetNOWTIMEPASSWORD())) {
                            if ("hbgd".equals(dialog.getPSW())) {
                                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                                startActivity(intent);
                                dialog.dismiss();
                            } else {
                                Toast.makeText(LoginActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 11 && resultCode == 12) {
            if (data != null) {
                String user_name = data.getStringExtra("user_name");
                utils.WriteString("username", user_name);
                editText_name.setText(user_name);
                editText_name.setSelection(user_name.length());
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void getCodeResult(boolean isTrue, String mes) {
        this.mes = mes;
        this.isResult = isTrue;
        if (mes.equals("注册下载成功")) {
            loadCodes();
        }
        LoginActivity.this.runOnUiThread(run);
    }

    @Override
    public void getUserInfo(boolean isTrue, String mes) {
        this.mes = mes;
        this.isResult = isTrue;
        runOnUiThread(userRun);
    }

    @Override
    public void getSamCode(boolean isTrue, String mes, String sam) {

    }


    Runnable userRun = new Runnable() {
        @Override
        public void run() {

            if (isResult) {
                utils.WriteString(TISHI, "0");
                utils.WriteString("CompanyType", userModel.getCompanyType());
                if ("02".equals(userModel.getCompanyType())) {
                    startActivity(new Intent().setClass(LoginActivity.this, Main2Activity.class));
                    finish();
                } else if ("03".equals(userModel.getCompanyType()) || "05".equals(userModel.getCompanyType())) {
                    startActivity(new Intent().setClass(LoginActivity.this, MainActivity.class));
                    finish();
                }
            } else {
                bt_login.setEnabled(true);
                CodeDao dao = new CodeDao(LoginActivity.this);
                dao.deleteall(dao.QueryAll());
                Toast.makeText(LoginActivity.this, mes, Toast.LENGTH_SHORT).show();
                mes = "";
            }
            circularJumpLoadingAnim.setVisibility(View.INVISIBLE);

        }
    };

    //加载字典项
    void loadCodes() {
        CodeDao dao = new CodeDao(LoginActivity.this);
        List<CodeModel> list = dao.QueryAll();
        if (list.size() > 1) {
            //字典查询
            String[] codes = getResources().getStringArray(R.array.CodeNameList);
            for (String s : codes) {
                CodeModel model = new CodeModel();
                model.setCodeName(s);
                dao.delete(model);
            }
            listener.getCode();
        }
    }

    static class HideClick extends Thread {
        public static volatile int sIsAlive = 0;

        @Override
        public void run() {
            sIsAlive++;
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (sIsAlive > 0) {
                sIsAlive = 0;
            }
            super.run();
        }
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (!isResult) {
                CodeDao dao = new CodeDao(LoginActivity.this);
                dao.deleteall(dao.QueryAll());
                bt_login.setEnabled(true);
                circularJumpLoadingAnim.stopAnim();
                circularJumpLoadingAnim.setVisibility(View.INVISIBLE);
                Toast.makeText(LoginActivity.this, mes, Toast.LENGTH_SHORT).show();
                mes = "";
            } else {
                if (!"".equals(editText_name.getText().toString().trim()) && !"".equals(editText_password.getText().toString().trim())) {
                    listener.getUserInfo(editText_name.getText().toString().trim(), editText_password.getText().toString().trim());
                }

            }
        }
    };
    Runnable rnn = new Runnable() {
        @Override
        public void run() {
            circularJumpLoadingAnim.stopAnim();
            circularJumpLoadingAnim.setVisibility(View.INVISIBLE);
            if (!mes.equals("")) {
                Toast.makeText(LoginActivity.this, mes, Toast.LENGTH_SHORT).show();
            }
            mes = "";
            bt_login.setEnabled(true);
        }
    };

    private void LOGo() {
        //开始loading动画
        bt_login.setEnabled(false);
        String stringIP = utils.ReadString("IP");
        if ("".equals(stringIP)) {
            Toast.makeText(LoginActivity.this, "请设置网络", Toast.LENGTH_LONG).show();
            //请求未成功隐藏loading动画
            circularJumpLoadingAnim.stopAnim();
            circularJumpLoadingAnim.setVisibility(View.INVISIBLE);
            bt_login.setEnabled(true);
        }
        if ("".equals(editText_name.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_LONG).show();
            circularJumpLoadingAnim.stopAnim();
            circularJumpLoadingAnim.setVisibility(View.INVISIBLE);
            bt_login.setEnabled(true);
        } else if ("".equals(editText_password.getText().toString().trim())) {
            Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_LONG).show();
            circularJumpLoadingAnim.stopAnim();
            circularJumpLoadingAnim.setVisibility(View.INVISIBLE);
            bt_login.setEnabled(true);
        } else {
            //登录请求
            OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
            String password = editText_password.getText().toString();
            String name = editText_name.getText().toString();

            String shouquan = "";
            shouquan = Utils.getMYImei(((TelephonyManager) getSystemService(TELEPHONY_SERVICE)).getDeviceId());
            String str = "'{\"LoginName\":\"" + name + "\",\"PassWord\":\"" + password + "\",\"TerminalCode\":\"" + "CF-49-49-8C-95" + "\",\"DeviceCode\":\"" + shouquan + "\"}'";
            okHttpUtils.requestGetByAsyn(stringIP, "AppClientLogin", str, new ReqCallBack() {
                @Override
                public void onReqSuccess(String result) {
                    System.out.println("请求：" + result);
                    Gson gson = new Gson();
                    ResultModel model = gson.fromJson(result, ResultModel.class);
                    if ("true".equals(model.isSuccess() + "")) {
                        userModel = gson.fromJson(model.getData(), RegisterModel.class);
                        //String userID = userModel.getUserID();
                        utils.WriteString(ServerDate, model.getTime());
                        Utils utils = new Utils(LoginActivity.this);
                        //保存userID，公司ID
                        utils.WriteString("UserId", userModel.getUserId());
                        utils.WriteString(COMPANYID, userModel.getCompanyId());
                        utils.WriteString("COMPANYBOSS", userModel.getBoss());
                        utils.WriteString("COMPANYCERTNUMBER", userModel.getBossCertNumber());
                        utils.WriteString("COMPANYBOSSADDRESS", userModel.getBossAddress());
                        utils.WriteString("COMPANYBOSSNATIONNAME", userModel.getBossNationName());
                        utils.WriteString("COMPANYBOSSNATION", userModel.getBossNation());
                        utils.WriteString("COMPANYBOSSPHONE", userModel.getBossPhone());

                        utils.WriteString("COMPANYROLE", userModel.getUserRole());
                        utils.WriteString(KEY_IMEI_MI, userModel.getDeviceCode());
                        utils.WriteString(KEY_TOKEN, userModel.getAuthorizedCode());
                        //存储登录名，密码
                        if (checkBox.isChecked()) {
                            utils.WriteString("username", editText_name.getText().toString().trim());
                            utils.WriteString("password", editText_password.getText().toString().trim());
                        } else {
                            utils.WriteString("username", "");
                            utils.WriteString("password", "");
                        }
                        //字典查询
//                        CodeDao dao = new CodeDao(LoginActivity.this);
//                        List<CodeModel> list = dao.QueryAll();
//                        if (list.size() <= 0) {
//                            //字典查询
//                            dao.deleteall(list);
//                            listener.getCode();
//                        } else {
                        listener.getUserInfo(editText_name.getText().toString().trim(), editText_password.getText().toString().trim());
                        //listener.RefushCode();
//                        }

                    } else {
                        mes = model.getMessage();
                        LoginActivity.this.runOnUiThread(rnn);
                    }
                }

                @Override
                public void onReqFailed(String errorMsg) {
                    mes = "网络错误";
                    LoginActivity.this.runOnUiThread(rnn);
                }
            });
        }
    }


    //网络连接dialog
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        // Setting Dialog Title
        alertDialog.setTitle(title);
        // Setting Dialog Message
        alertDialog.setMessage(message);
        // Setting alert dialog icon
        alertDialog.setIcon(R.mipmap.center_wifi);
        // Setting OK Button
        alertDialog.setButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        // Showing Alert Message
        alertDialog.show();
    }

    /**
     * 点击返回弹出消息框，确定后完全退出系统
     *
     * @param keyCode key的编码
     * @param event   key的时间
     * @return
     * @author cc 2016年4月8日 14:45:14
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            llDialog.setMiddleMessage("确认要退出系统？");
            llDialog.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//点击确定按钮
                    llDialog.cancel();
                    LoginActivity.this.finish();
                    System.exit(0);//完全退出系统
                }
            });
            llDialog.setOnNegativeListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    llDialog.cancel();

                }
            });//点击取消按钮
            llDialog.show();
        }
        return false;
    }

}
