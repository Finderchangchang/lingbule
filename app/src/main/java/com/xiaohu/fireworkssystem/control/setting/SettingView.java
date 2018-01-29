package com.xiaohu.fireworkssystem.control.setting;

/**
 * Created by Administrator on 2016/9/5.
 */
public interface SettingView {
    void getCodeResult(boolean isTrue, String mes);
    void getUserInfo(boolean isTrue, String mes);
    void getSamCode(boolean isTrue,String mes,String sam);
}
