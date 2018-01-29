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

public class MyOutWarehouseDialog extends Dialog {
    private Button btnDelete, btnSave;
    private EditText etNum, txPrice;
    private TextView txName, txTotalNum, tvNumName,et_startgoods_num;
    private Context myContext;

    public MyOutWarehouseDialog(Context context, String name, String p, String num, String total, String numNAME) {
        super(context, R.style.Base_Theme_AppCompat_Light_Dialog);
        myContext=context;
        setMyDialog();
        txName.setText(name);
        txPrice.setText(p);
        etNum.setText(num);
        myContext = context;
        txPrice.setSelection(txPrice.getText().length());
        txTotalNum.setText(total);
        tvNumName.setText(numNAME);
        et_startgoods_num.setText(num);
    }

    private void setMyDialog() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.myout_alertdialog, null);
        btnDelete = (Button) v.findViewById(R.id.btn_delete);
        btnSave = (Button) v.findViewById(R.id.btn_save);
        etNum = (EditText) v.findViewById(R.id.et_goods_num);
        txName = (TextView) v.findViewById(R.id.goods_name);
        txPrice = (EditText) v.findViewById(R.id.goods_price);
        txTotalNum = (TextView) v.findViewById(R.id.et_goods_total_num);
        tvNumName = (TextView) v.findViewById(R.id.tv_dialog_num_name);
        et_startgoods_num = (TextView) v.findViewById(R.id.et_startgoods_num);
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

    public String getGoodsNum() {
        return etNum.getText().toString().trim();
    }
    public void setPRICEENABLE(){
        txPrice.setEnabled(false);
    }

    public void setDeleteListener(View.OnClickListener listener) {
        btnDelete.setOnClickListener(listener);
    }

    public void setSave(View.OnClickListener listener) {
        btnSave.setOnClickListener(listener);
    }
}
