package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/9/5.
 */
public class SysCodeModel implements Serializable {
    //字典表名
    private String Name;
    //字典说明
    private String Describe;
    //是否需要更新
    private String IsUpdate;
    //创建时间
    private String CreateTime;
    //备注
    private String Comment;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getIsUpdate() {
        return IsUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        IsUpdate = isUpdate;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }
}
