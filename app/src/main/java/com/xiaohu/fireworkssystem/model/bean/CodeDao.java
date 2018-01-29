package com.xiaohu.fireworkssystem.model.bean;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.xiaohu.fireworkssystem.db.MyDBHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public class CodeDao {
    private Context context;
    private Dao<CodeModel, Integer> codeDao;
    private MyDBHelper helper;

    public CodeDao(Context context) {
        this.context = context;
        helper = MyDBHelper.getMyDBHelper(context);
        codeDao = helper.getDao(CodeModel.class);
    }

    //添加
    public void add(CodeModel codeModel) {
        try {

            codeDao.create(codeModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //删除
    public void delete(int id) {
        try {
            codeDao.deleteById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteall(List<CodeModel> list) {
        try {
            codeDao.delete(list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //查询所有
    public List<CodeModel> QueryAll() {
        List<CodeModel> list = null;
        try {
            list = codeDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<CodeModel> QueryByID(String name, String id) {

        QueryBuilder builder = codeDao.queryBuilder();
        List<CodeModel> list = new ArrayList<>();
        try {
            builder.where().eq("CodeName", name).and().eq("Code", id);
            list = builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //字典名称  字典字段中文名称
    public List<CodeModel> QueryByKName(String name, String value) {

        List<CodeModel> list = new ArrayList<>();
        QueryBuilder<CodeModel, Integer> qb = codeDao.queryBuilder();
        try {
            qb.where().eq("CodeName", name).and().like("Name", "%" + value + "%");
            PreparedQuery<CodeModel> pq = qb.prepare();
            list = codeDao.query(pq);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    //条件查询(查询指定字典)
    public List<CodeModel> QueryByName(String name, String value) {
        QueryBuilder builder = codeDao.queryBuilder();
        List<CodeModel> list = new ArrayList<>();
        try {
            builder.where().eq(name, value);
            list = builder.query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        QueryBuilder<CodeModel, Integer> queryBuilder = articleDaoOpe
//                .queryBuilder();
//        Where<CodeModel, Integer> where = queryBuilder.where();
//        where.and();

//        where.eq("user_id", 1);
//        where.and();
//        where.eq("name", "xxx");

//        //或者
//        articleDaoOpe.queryBuilder().//
//                where().//
//                eq("user_id", 1).and().//
//                eq("name", "xxx");
        return list;

    }
}
