package com.xiaohu.fireworkssystem.model.table;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.Serializable;

/**
 * 注册模型
 * Created by Administrator on 2016/11/2.
 */

public class RegisterModel implements Serializable {
    //企业编码
    private String CompanyId;
    //企业名称
    private String CompanyName;
    //企业类型
    private String CompanyType;
    //登录名
    private String LoginName;
    //用户名
    private String UserName;
    //密码
    private String Password;
    //用户类型
    private String UserRole;
    //用户角色名称
    private String UserRoleName;
    //描述符
    private String DeviceCode;
    //授权码
    private String AuthorizedCode;
    //授权类型
    private String AuthorizedType;
    //设备编码
     private String TerminalCode;
    //创建时间
    private String CreateTime;
    //法人
    private String Boss;
    //法人
    private String BossCertNumber;
    //住址
    private String BossAddress;
    //民族名称
    private String BossNationName;
    //民族
    private String BossNation;
    //
    private String UserId;
    //法人电话
    private String BossPhone;
    //true普通手机 false一体机
    private boolean isCVR;

    public String getJson() {
        String json = "'{";
        if ((!"".equals(getCompanyId())) && null != getCompanyId()) {
            json += "\"CompanyId\":\"" + getCompanyId() + "\"";
        }
        if ((!"".equals(getCompanyName())) && null != getCompanyName()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"CompanyName\":\"" + getCompanyName() + "\"";
        }
        if ((!"".equals(getLoginName())) && null != getLoginName()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"LoginName\":\"" + getLoginName() + "\"";
        }
        if ((!"".equals(getUserName())) && null != getUserName()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"UserName\":\"" + getUserName() + "\"";
        }
        if ((!"".equals(getPassword())) && null != getPassword()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"Password\":\"" + getPassword() + "\"";
        }
        if ((!"".equals(getUserRole())) && null != getUserRole()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"UserRole\":\"" + getUserRole() + "\"";
        }
        if ((!"".equals(getDeviceCode())) &&null != getDeviceCode()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"DeviceCode\":\"" + getDeviceCode() + "\"";
        }
        if ((!"".equals(getTerminalCode())) && null != getTerminalCode()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"TerminalCode\":\"" + getTerminalCode() + "\"";
        }
        if ((!"".equals(getAuthorizedCode())) && null != getAuthorizedCode()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"AuthorizedCode\":\"" + getAuthorizedCode() + "\"";
        }
        if ((!"".equals(getAuthorizedType())) && null != getAuthorizedType()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"AuthorizedType\":\"" + getAuthorizedType() + "\"";
        }

        if ((!"".equals(getCreateTime())) && null != getCreateTime()) {
            if (!json.equals("")) {
                json += ",";
            }
            json += "\"CreateTime\":\"" + Utils.getNOWTime() + "\"";
        }

        json += ",";
        json += "\"isCVR\":" + isCVR() + "";
        return json + "}'";
    }

    public boolean isCVR() {
        return isCVR;
    }

    public void setCVR(boolean CVR) {
        isCVR = CVR;
    }

    public String getTerminalCode() {
        return TerminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        TerminalCode = terminalCode;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCompanyType() {
        return CompanyType;
    }

    public void setCompanyType(String companyType) {
        CompanyType = companyType;
    }

    public String getBossNation() {
        return BossNation;
    }

    public void setBossNation(String bossNation) {
        BossNation = bossNation;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getLoginName() {
        return LoginName;
    }

    public void setLoginName(String loginName) {
        LoginName = loginName;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserRoleName() {
        return UserRoleName;
    }

    public void setUserRoleName(String userRoleName) {
        UserRoleName = userRoleName;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
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

//    public String getTerminalId() {
//        return TerminalId;
//    }
//
//    public void setTerminalId(String terminalId) {
//        TerminalId = terminalId;
//    }


    public String getBossPhone() {
        return BossPhone;
    }

    public void setBossPhone(String bossPhone) {
        BossPhone = bossPhone;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getBoss() {
        return Boss;
    }

    public void setBoss(String boss) {
        Boss = boss;
    }

    public String getBossCertNumber() {
        return BossCertNumber;
    }

    public void setBossCertNumber(String bossCertNumber) {
        BossCertNumber = bossCertNumber;
    }

    public String getBossAddress() {
        return BossAddress;
    }

    public void setBossAddress(String bossAddress) {
        BossAddress = bossAddress;
    }

    public String getBossNationName() {
        return BossNationName;
    }

    public void setBossNationName(String bossNationName) {
        BossNationName = bossNationName;
    }
}
