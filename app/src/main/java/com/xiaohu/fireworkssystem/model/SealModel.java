package com.xiaohu.fireworkssystem.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/4.
 * 销售模型
 */
public class SealModel implements Serializable {
    private int id;
    private String sealId;
    private String price;
    private String personName;
    private String createTime;
    private String kaipiaoren;

    List<GoodsModel> list = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSealId() {
        return sealId;
    }

    public void setSealId(String sealId) {
        this.sealId = sealId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getKaipiaoren() {
        return kaipiaoren;
    }

    public void setKaipiaoren(String kaipiaoren) {
        this.kaipiaoren = kaipiaoren;
    }
}
