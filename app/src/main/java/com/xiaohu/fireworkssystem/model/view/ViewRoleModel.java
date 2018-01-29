package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * 用户角色
 * Created by Administrator on 2016/11/3.
 */

public class ViewRoleModel implements Serializable {
    //角色编码
    private String RoleID;
    //角色名称（零售，批发）
    private String RoleName;

    public String getRoleID() {
        return RoleID;
    }

    public void setRoleID(String roleID) {
        RoleID = roleID;
    }

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }
}
