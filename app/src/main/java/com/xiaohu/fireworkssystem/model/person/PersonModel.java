package com.xiaohu.fireworkssystem.model.person;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;


/**
 * 旅客信息Model
 * Created by Administrator on 2015/11/11.
 */
@DatabaseTable(tableName = "PersonModel")
public class PersonModel implements Serializable {
    //表示id为主键且自动生成
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "PersonCompanyId")
    private String PersonCompanyId;//企业编码
    @DatabaseField(columnName = "PersonCardType")
    private String PersonCardType;//证件类型
    @DatabaseField(columnName = "PersonCardId")
    private String PersonCardId;//证件号码
    @DatabaseField(columnName = "PersonName")
    private String PersonName;//人员姓名
    @DatabaseField(columnName = "PersonSex")
    private String PersonSex;//性别1男2女
    @DatabaseField(columnName = "PersonNation")
    private String PersonNation;//民族
    @DatabaseField(columnName = "PersonNative")
    private String PersonNative;//籍贯
    @DatabaseField(columnName = "PersonBirthday")
    private String PersonBirthday;//出生日期
    @DatabaseField(columnName = "PersonAddress")
    private String PersonAddress;//详细地址
    @DatabaseField(columnName = "PersonPhoneNumber")
    private String PersonPhoneNumber;//联系电话
    @DatabaseField(columnName = "PersonImgBase64")
    private String PersonImgBase64;//人员的路径
    @DatabaseField(columnName = "IsUpLoad")
    private boolean IsUpLoad;//是否上传到后台

    private boolean IsExtendedService;//是否为延伸服务

    /// <summary>
    /// 创建时间。
    /// </summary>
    @DatabaseField(columnName = "PersonCreateTime")
    private String PersonCreateTime;

    public String getJson() {

        String json = "'{\"Name\":\"" + getPersonName() +
                "\",\"CardNumber\":\"" + getPersonCardId() +
                "\",\"Sex\":\"" + getPersonSex() +
                "\",\"Nation\":\"" + getPersonNation() +
                "\",\"Birthday\":\"" + getPersonBirthday() +
                "\",\"IsExtendedService\":\"" + isExtendedService() +
                "\",\"Address\":\"" + getPersonAddress() + "\"";
        return json;
    }

    public void setUpLoad(boolean upLoad) {
        IsUpLoad = upLoad;
    }

    public String getPersonImgBase64() {
        return PersonImgBase64;
    }

    public void setPersonImgBase64(String personImgBase64) {
        PersonImgBase64 = personImgBase64;
    }

    public boolean isExtendedService() {
        return IsExtendedService;
    }

    public void setExtendedService(boolean extendedService) {
        IsExtendedService = extendedService;
    }


    public String getPersonPhoneNumber() {
        return PersonPhoneNumber;
    }

    public void setPersonPhoneNumber(String personPhoneNumber) {
        PersonPhoneNumber = personPhoneNumber;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonAddress() {
        return PersonAddress;
    }

    public void setPersonAddress(String personAddress) {
        PersonAddress = personAddress;
    }

    public String getPersonBirthday() {
        return PersonBirthday;
    }

    public void setPersonBirthday(String personBirthday) {
        PersonBirthday = personBirthday;
    }

    public String getPersonCardId() {
        return PersonCardId;
    }

    public void setPersonCardId(String personCardId) {
        PersonCardId = personCardId;
    }


    public String getPersonCardType() {
        return PersonCardType;
    }

    public void setPersonCardType(String personCardType) {
        PersonCardType = personCardType;
    }

    public String getPersonCompanyId() {
        return PersonCompanyId;
    }

    public void setPersonCompanyId(String personCompanyId) {
        PersonCompanyId = personCompanyId;
    }

    public String getPersonCreateTime() {
        return PersonCreateTime;
    }

    public void setPersonCreateTime(String personCreateTime) {
        PersonCreateTime = personCreateTime;
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

    public String getPersonNative() {
        return PersonNative;
    }

    public void setPersonNative(String personNative) {
        PersonNative = personNative;
    }

    public String getPersonSex() {
        return PersonSex;
    }

    public void setPersonSex(String personSex) {
        PersonSex = personSex;
    }

    public boolean isUpLoad() {
        return IsUpLoad;
    }

    public boolean isIsUpLoad() {
        return IsUpLoad;
    }

    public void setIsUpLoad(boolean isUpLoad) {
        IsUpLoad = isUpLoad;
    }

}
