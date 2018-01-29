package com.xiaohu.fireworkssystem.model.person;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.xiaohu.fireworkssystem.db.MyDBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/5.
 */

public class PersonDao {
    private Context context;
    private Dao<PersonModel, Integer> pdao;
    private MyDBHelper helper;

    public PersonDao(Context context) {
        this.context = context;
        helper = MyDBHelper.getMyDBHelper(context);
        pdao = helper.getDao(PersonModel.class);
    }

    //添加
    public void add(PersonModel personModel) {
        try {
            pdao.create(personModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询所有
    public List<PersonModel> QueryAll() {
        QueryBuilder builder = pdao.queryBuilder();
        List<PersonModel> list = null;
        try {
            list = builder.query();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<PersonModel> QueryByPersonName(String name) {
        QueryBuilder builder = pdao.queryBuilder();
        List<PersonModel> list = new ArrayList<>();
        try {
            builder.orderBy("PersonCreateTime", true);
            builder.where().like("PersonName", "%" + name + "%");
            builder.limit(30);
            list = builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<PersonModel> QueryByID(String cid) {
        QueryBuilder builder = pdao.queryBuilder();
        List<PersonModel> list = new ArrayList<>();
        try {
            builder.where().eq("PersonCardId", cid);
            list = builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //删除
    public void delete(int id) {
        try {
            pdao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
