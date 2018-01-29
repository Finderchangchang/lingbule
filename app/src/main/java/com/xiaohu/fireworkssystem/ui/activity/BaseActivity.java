package com.xiaohu.fireworkssystem.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.utils.Utils;

/**
 * Created by Administrator on 2016/7/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    //RequestQueue是一个请求队列对象，它可以缓存所有的HTTP请求，然后按照一定的算法并发地发出这些请求。RequestQueue内部的设计就是非常合适高并发的，
//    //因此我们不必为每一次HTTP请求都创建一个RequestQueue对象，这是非常浪费资源的，基本上在每一个需要和网络交互的Activity中创建一个RequestQueue对象就足够了。
    // public RequestQueue mQueue;
    Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        utils = new Utils(BaseActivity.this);

        initView();
        initEvent();
    }

    public abstract void initView();

    public abstract void initEvent();

    public Toast toast = null;

    public void ToastShort(String msg) {
        if (toast == null) {
            toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
