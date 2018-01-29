package com.xiaohu.fireworkssystem.control.stock;

import com.xiaohu.fireworkssystem.model.view.ViewStockModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/5.
 */
public interface StockSearchView {
    void SearchStock(String message, List<ViewStockModel> list);
}
