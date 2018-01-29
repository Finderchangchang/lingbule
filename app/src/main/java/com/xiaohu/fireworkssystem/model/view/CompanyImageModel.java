package com.xiaohu.fireworkssystem.model.view;

import java.io.Serializable;

/**
 * 企业证件图片
 * Created by Administrator on 2016/9/24.
 */

public class CompanyImageModel implements Serializable {
    //图片编码
    private String ImageId;
    //企业编码
    private String CompanyId;
    //图片类型
    private String ImageType;
    //图片
    private byte[] Image;
    //创建时间
    private String CreateTime;
    //备注
    private String Comment;

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getCompanyId() {
        return CompanyId;
    }

    public void setCompanyId(String companyId) {
        CompanyId = companyId;
    }

    public String getImageType() {
        return ImageType;
    }

    public void setImageType(String imageType) {
        ImageType = imageType;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
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
