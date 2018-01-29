package com.xiaohu.fireworkssystem.model.table;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/10/10.
 */

public class ClientUserModel implements Serializable {
    private String UserId;
    private String LoginName ;
    private String Area ;
    private String UserName ;
    private String Password ;
    private String UserRole ;
    private String CompanyId ;
    private String DeviceCode ;
    private String AuthorizedCode ;
    private String AuthorizedType ;
    private String ExpirationTime ;
    private String UserState  ;
    private String CreateTime   ;
    private String LastTime   ;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getDeviceCode() {
        return DeviceCode;
    }

    public void setDeviceCode(String deviceCode) {
        DeviceCode = deviceCode;
    }

    public String getAuthorizedCode() {
        return AuthorizedCode;
    }

    public void setAuthorizedCode(String authorizedCode) {
        AuthorizedCode = authorizedCode;
    }

    public String getAuthorizedType() {
        return AuthorizedType;
    }

    public void setAuthorizedType(String authorizedType) {
        AuthorizedType = authorizedType;
    }

    public String getExpirationTime() {
        return ExpirationTime;
    }

    public void setExpirationTime(String expirationTime) {
        ExpirationTime = expirationTime;
    }

    public String getUserState() {
        return UserState;
    }

    public void setUserState(String userState) {
        UserState = userState;
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
}
