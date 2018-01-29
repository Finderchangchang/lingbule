package com.xiaohu.fireworkssystem.model.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * 字典表
 * Created by Administrator on 2016/7/26.
 */
@DatabaseTable(tableName = "CodeModel")
public class CodeModel implements Serializable {
    //表示id为主键且自动生成
    @DatabaseField(generatedId = true, columnName = "id")
    private int id;
    @DatabaseField(columnName = "Code")
    private String Code;//11
    @DatabaseField(columnName = "Name")
    private String Name;//女
    @DatabaseField(columnName = "State")
    private String State;
    @DatabaseField(columnName = "Parent")
    private String Parent;
    @DatabaseField(columnName = "Comment")
    private String Comment;
    @DatabaseField(columnName = "CodeName")
    private String CodeName;


    public String getParent() {
        return Parent;
    }

    public void setParent(String parent) {
        Parent = parent;
    }

    public String getComment() {
        return Comment;
    }

    public void setComment(String comment) {
        Comment = comment;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getCodeName() {
        return CodeName;
    }

    public void setCodeName(String codeName) {
        CodeName = codeName;
    }
}
