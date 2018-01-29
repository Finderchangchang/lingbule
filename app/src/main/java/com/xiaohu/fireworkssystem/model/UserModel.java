package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/7/19.
 * 用户信息
 */
public class UserModel implements Serializable {
    //用户编码
    private String UserID;
    //登录账户
    private String LoginName;
    //用户名称
    private String UserName;
    //密码
    private String PassWord;
    //用户角色
    private String UseerRole;

    public String getCompanyID() {
        return CompanyID;
    }

    public void setCompanyID(String companyID) {
        CompanyID = companyID;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getLastTime() {
        return LastTime;
    }

    public void setLastTime(String lastTime) {
        LastTime = lastTime;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }

    public String getUseerRole() {
        return UseerRole;
    }

    public void setUseerRole(String useerRole) {
        UseerRole = useerRole;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserState() {
        return UserState;
    }

    public void setUserState(String userState) {
        UserState = userState;
    }

    //企业编码
    private String CompanyID;
    //状态
    private String UserState;
    //创建时间
    private String CreateTime;
    //最后一次修改时间
    private String LastTime;


}
