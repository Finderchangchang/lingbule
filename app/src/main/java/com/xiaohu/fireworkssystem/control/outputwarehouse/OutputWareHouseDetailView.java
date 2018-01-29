package com.xiaohu.fireworkssystem.control.outputwarehouse;

import com.xiaohu.fireworkssystem.model.view.ViewOutputWareHouseRecordModel;

import java.util.List;

/**
 * Created by Administrator on 2017/6/29.
 */

public interface OutputWareHouseDetailView {
    void SearchOutputWareHouseDetailView(String message, List<ViewOutputWareHouseRecordModel> list);
}
