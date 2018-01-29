package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/18.
 */

public class CardUserModel implements Serializable {
    /// <summary>
    /// 身份证号。
    /// </summary>
    private String IdentyNumber ;

    /// <summary>
    /// 姓名。
    /// </summary>
    private String PersonName ;

    /// <summary>
    /// 民族。
    /// </summary>
    private String PersonNation ;

    /// <summary>
    /// 住址。
    /// </summary>
    private String PersonAddress ;

    /// <summary>
    /// 头像照片。
    /// </summary>
    private String PersonFaceImage;

    public String getIdentyNumber() {
        return IdentyNumber;
    }

    public void setIdentyNumber(String identyNumber) {
        IdentyNumber = identyNumber;
    }

    public String getPersonName() {
        return PersonName;
    }

    public void setPersonName(String personName) {
        PersonName = personName;
    }

    public String getPersonNation() {
        return PersonNation;
    }

    public void setPersonNation(String personNation) {
        PersonNation = personNation;
    }

    public String getPersonAddress() {
        return PersonAddress;
    }

    public void setPersonAddress(String personAddress) {
        PersonAddress = personAddress;
    }

    public String getPersonFaceImage() {
        return PersonFaceImage;
    }

    public void setPersonFaceImage(String personFaceImage) {
        PersonFaceImage = personFaceImage;
    }
}
