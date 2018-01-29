package com.xiaohu.fireworkssystem.control.register;

/**
 * Created by Administrator on 2016/11/2.
 */

public interface RegisterView {
    void onResultRegister(boolean isTrue, String mes, String shouQuan);

    void RolesResult(String rolesid);
}
