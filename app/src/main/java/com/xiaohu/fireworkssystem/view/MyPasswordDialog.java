package com.xiaohu.fireworkssystem.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaohu.fireworkssystem.R;

/**
 * Created by Administrator on 2016/11/8.
 */

public class MyPasswordDialog extends Dialog {
    private Button btnexit, btnSave;
    private EditText etNum;
    private Context myContext;
    public MyPasswordDialog(Context context) {
        super(context, R.style.Base_Theme_AppCompat_Light_Dialog);
        myContext=context;
        setCancelable(false);
        initView();
    }
    private void initView(){
        View v = LayoutInflater.from(getContext()).inflate(R.layout.my_password_dialog, null);
        etNum= (EditText) v.findViewById(R.id.et_pw_num);
        btnexit= (Button) v.findViewById(R.id.btn_exit);
        btnSave= (Button) v.findViewById(R.id.btn_save);
        super.setContentView(v);
    }
    public String getPSW(){
        return etNum.getText().toString().trim();
    }
    public void setSave(View.OnClickListener listener) {
        btnSave.setOnClickListener(listener);
    }
    public void setExit(View.OnClickListener listener){
        btnexit.setOnClickListener(listener);
    }
}
