package com.xiaohu.fireworkssystem.control.searchcompany;

import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;

import java.util.List;

/**
 * Created by Administrator on 2018/1/22.
 */

public interface SearchCompanyListView {
    void getCompanyList(String string , List<ViewCompanyModel> list );
}
