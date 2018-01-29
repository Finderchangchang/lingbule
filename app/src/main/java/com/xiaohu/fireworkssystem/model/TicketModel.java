package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/18.
 */

public class TicketModel implements Serializable{
    //访问令牌
    private String Token;
    //账号
    private String AccountId;
    //加密后密码
    private String AccountSecret;
    //时间戳
    private String TimeStamp;
    private String image;

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public String getAccountId() {
        return AccountId;
    }

    public void setAccountId(String accountId) {
        AccountId = accountId;
    }

    public String getAccountSecret() {
        return AccountSecret;
    }

    public void setAccountSecret(String accountSecret) {
        AccountSecret = accountSecret;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
