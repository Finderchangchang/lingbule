package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**操作箱码模型。
 * Created by Administrator on 2017/7/7.
 */

public class OperatorBoxCodeInvetoryModel implements Serializable {
    //损益状态
    private String LossState;
    //操作箱码
    private String BoxCode;
    //数量
    private int Amount;

    public String getLossState() {
        return LossState;
    }

    public void setLossState(String lossState) {
        LossState = lossState;
    }

    public String getBoxCode() {
        return BoxCode;
    }

    public void setBoxCode(String boxCode) {
        BoxCode = boxCode;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
    public String getJson() {
        String json = "{\"BoxCode\":\"" + Utils.URLEncode(getBoxCode()) +
                "\",\"LossState\":\"" + Utils.URLEncode(getLossState())+
                "\",\"Amount\":" + Utils.URLEncode(getAmount()+"")+
                "}]}";


        return json;
    }

    @Override
    public String toString() {
        return "OperatorBoxCodeInvetoryModel{" +
                "LossState='" + LossState + '\'' +
                ", BoxCode='" + BoxCode + '\'' +
                ", Amount=" + Amount +
                '}';
    }
}
