package com.xiaohu.fireworkssystem.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;

/**
 * Created by Administrator on 2016/8/16.
 */

public class MyTuiDialog extends Dialog {
    private Button btnDelete, btnSave;
    private EditText txPrice,et_goods_num;
    private TextView txName,et_goods_total_num,tv_dialogname;
    private Context myContext;

    public MyTuiDialog(Context context, String name, String p, String numNAME,String goodsnum,String goods_total_num,String numnames) {
        super(context, R.style.Base_Theme_AppCompat_Light_Dialog);
        myContext=context;
        setMyDialog();
        txName.setText(name);
        txPrice.setText(p);
        et_goods_num.setText(goodsnum);
        myContext = context;
        txPrice.setSelection(txPrice.getText().length());
        et_goods_total_num.setText(goods_total_num);
        tv_dialogname.setText(numnames);
        //etNum.requestFocus();  //这是关键当前控件获得焦点
    }

    private void setMyDialog() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.my_alertdialog, null);
        btnDelete = (Button) v.findViewById(R.id.btn_delete);
        btnSave = (Button) v.findViewById(R.id.btn_save);
        txName = (TextView) v.findViewById(R.id.goods_name);
        txPrice = (EditText) v.findViewById(R.id.goods_price);
        et_goods_num = (EditText) v.findViewById(R.id.et_goods_num);
        et_goods_total_num= (TextView) v.findViewById(R.id.et_goods_total_num);
        tv_dialogname = (TextView) v.findViewById(R.id.tv_dialog_num_name);
        super.setContentView(v);
    }

    public void setBtnLeftText(String text) {
        btnDelete.setText(text);
    }

    public void setBtnRightText(String text) {
        btnSave.setText(text);
    }

    public String getGoodsPrice() {
        return txPrice.getText().toString().trim();
    }

    public void setPRICEENABLE(){
        txPrice.setEnabled(false);
    }

    public String getGoodsNum() {
        return et_goods_num.getText().toString().trim();
    }
    public void setDeleteListener(View.OnClickListener listener) {
        btnDelete.setOnClickListener(listener);
    }

    public void setSave(View.OnClickListener listener) {
        btnSave.setOnClickListener(listener);
    }
}
