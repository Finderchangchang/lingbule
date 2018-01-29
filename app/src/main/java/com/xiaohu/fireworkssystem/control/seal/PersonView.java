package com.xiaohu.fireworkssystem.control.seal;

/**
 * Created by Administrator on 2016/9/14.
 */
public interface PersonView {
    //核对身份证
    void CheckResult(boolean isTrue, String mes ,String type);
    //发送验证码
    void SendVerificationCode(boolean boo,String mes);
    //校验验证码
    void CheckVerificationCode(boolean boo,String mes);
}
