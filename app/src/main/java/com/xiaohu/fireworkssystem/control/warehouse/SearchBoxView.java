package com.xiaohu.fireworkssystem.control.warehouse;

import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;

import java.util.List;

/**
 * Created by Administrator on 2017/9/28.
 */

public interface SearchBoxView {
    void SearchBox(List<ViewBoxCodeStockModel> viewStockBoxModelList, String mes);
}
