package com.xiaohu.fireworkssystem.control.seal;

import com.xiaohu.fireworkssystem.model.table.ActualInventoryModel;
import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;

import java.util.List;

/**
 * Created by Administrator on 2016/9/2.
 */
public interface SealAddView {
    void addResult(boolean b, String mes);

    void SearchBill(String mes, List<ViewSaleRecordModel> list);

    void SearchGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel);

    void SearchAllGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel);

    void Inventory(String mes, ActualInventoryModel actualInventoryModel);

}
