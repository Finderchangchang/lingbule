package com.xiaohu.fireworkssystem.utils;

import android.content.Context;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.model.BlueToothModel;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：zz on 2016/7/3 16:31
 * 通过字典获得dialog的item选项
 */
public class CodeByData {
    public static void getCodeByData(List<CodeModel> list , Context context , SpinnerDialog dialog , final  TextView view ) {
        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(context);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    view.setText(val.getDeviceName());
                }
            });
        }
    }
}
