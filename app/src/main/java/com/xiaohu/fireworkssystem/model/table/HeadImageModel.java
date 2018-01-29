package com.xiaohu.fireworkssystem.model.table;

import java.io.Serializable;

/**
 * //头像信息
 */
public class HeadImageModel implements Serializable {
    // 人员头像编码
    private String HeadImageID;
    // 人员头像
    private String HeadImage;
    // 创建时间
    private String CreateTime;
    // 备注
    private String Comment;

    private String HeadImageBase64;

    public String getHeadImageBase64() {
        return HeadImageBase64;
    }

    public void setHeadImageBase64(String headImageBase64) {
        HeadImageBase64 = headImageBase64;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getHeadImage() {
        return HeadImage;
    }

    public void setHeadImage(String headImage) {
        HeadImage = headImage;
    }

    public String getHeadImageID() {
        return HeadImageID;
    }

    public void setHeadImageID(String headImageID) {
        HeadImageID = headImageID;
    }
}
