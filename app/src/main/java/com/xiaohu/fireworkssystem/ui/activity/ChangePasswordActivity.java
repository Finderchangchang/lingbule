package com.xiaohu.fireworkssystem.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/*
修改密码页面
 */
public class ChangePasswordActivity extends Activity {
    //标题栏
    private MyTitleView title;
    //保存按钮
    private Button button_save;
    //原密码输入框
    private EditText editText_oldpassword;
    //新密码输入框
    private EditText editText_newpassword1, editText_newpassword2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        //初始化控件
        initView();
        //设置监听
        initListener();
    }

    private void initListener() {
        //标题栏向左箭头点击事件
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //保存密码按钮点击事件
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入值
                String oldpassword = editText_oldpassword.getText().toString().trim();
                String newpassword1 = editText_newpassword1.getText().toString().trim();
                String newpassword2 = editText_newpassword2.getText().toString().trim();
                //判断输入值是否为空，不为空判断；两次新密码是否一致，再进行请求。
                if ("".equals(oldpassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "请输入旧密码", Toast.LENGTH_SHORT).show();
                } else if ("".equals(newpassword1)) {
                    Toast.makeText(ChangePasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                } else if ("".equals(newpassword2)) {
                    Toast.makeText(ChangePasswordActivity.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                } else if (!newpassword1.equals(newpassword2)) {
                    Toast.makeText(ChangePasswordActivity.this, "新密码不一致", Toast.LENGTH_SHORT).show();
                } else {
                    Utils utils = new Utils(ChangePasswordActivity.this);
                    String userID = utils.ReadString("UserId");
                    //第一个参数userid 第二个参数原密码 第三个参数新密码
                    String str = "'{\"UserID\":\"" + userID + "\",\"OldPassword\":\"" + oldpassword + "\",\"NewPassword\":\"" + newpassword2 + "\"}'";
                    OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(KEY_TOKEN));
                    //获得ip地址
                    String stringIP = utils.ReadString("IP");
                    okHttpUtils.requestGetByAsyn(stringIP, "EditPassword", str, new ReqCallBack() {
                        @Override
                        public void onReqSuccess(String result) {
                            Gson gson = new Gson();
                            ResultModel model = gson.fromJson(result, ResultModel.class);
                            if ("true".equals(model.isSuccess() + "")) {
                                ChangePasswordActivity.this.runOnUiThread(run);
                                finish();
                            }else {
                                ChangePasswordActivity.this.runOnUiThread(run3);
                            }
                        }

                        @Override
                        public void onReqFailed(String errorMsg) {
                            ChangePasswordActivity.this.runOnUiThread(run2);
                        }
                    });
                }
            }
        });
    }

    private void initView() {
        button_save = (Button) findViewById(R.id.button_save);
        editText_oldpassword = (EditText) findViewById(R.id.editText_oldpassword);
        editText_newpassword1 = (EditText) findViewById(R.id.editText_newpassword1);
        editText_newpassword2 = (EditText) findViewById(R.id.editText_newpassword2);
        title = (MyTitleView) findViewById(R.id.change_password_title);
    }


    //新开线程提示信息
    Runnable run = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(ChangePasswordActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
        }
    };
    Runnable run2 = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(ChangePasswordActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
        }
    };
    Runnable run3 = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(ChangePasswordActivity.this, "修改未成功", Toast.LENGTH_SHORT).show();
        }
    };
}
