package com.xiaohu.fireworkssystem.control.company;


import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/23.
 */
public interface CompanySearchView {
    void Result(ViewCompanyModel model);
    void ResultList(String string,List<ViewCompanyModel> list);
}
