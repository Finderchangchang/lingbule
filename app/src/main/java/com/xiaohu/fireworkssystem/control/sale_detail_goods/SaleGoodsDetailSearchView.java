package com.xiaohu.fireworkssystem.control.sale_detail_goods;


import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;

import java.util.List;

public interface SaleGoodsDetailSearchView {
    void SaleGoodsDetailSearchView(String message, List<ViewSaleRecordModel> list, ViewProductSaleModel viewProductSaleModel);
}