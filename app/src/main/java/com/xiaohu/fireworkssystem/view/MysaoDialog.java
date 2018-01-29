package com.xiaohu.fireworkssystem.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.xiaohu.fireworkssystem.R;

/**
 * Created by Administrator on 2017/8/15.
 */

public class MysaoDialog extends Dialog {
    private Button btnDelete;
    private EditText etNum;
    private Context myContext;

    public MysaoDialog(Context context) {
        super(context, R.style.Base_Theme_AppCompat_Light_Dialog);
        myContext=context;
        setMyDialog();
        myContext = context;
        etNum.requestFocus();  //这是关键当前控件获得焦点
    }

    private void setMyDialog() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.my_saoalertdialog, null);
        btnDelete = (Button) v.findViewById(R.id.btn_delete);
        etNum = (EditText) v.findViewById(R.id.eet_goods_num);
        super.setContentView(v);
    }

    public void setBtnLeftText(String text) {
        btnDelete.setText(text);
    }


    public void setDeleteListener(View.OnClickListener listener) {
        btnDelete.setOnClickListener(listener);
    }
}
