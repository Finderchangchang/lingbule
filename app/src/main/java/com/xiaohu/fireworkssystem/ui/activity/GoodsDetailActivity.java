package com.xiaohu.fireworkssystem.ui.activity;

import android.view.View;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewStockModel;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.List;

/**
 * Created by Administrator on 2016/8/11.
 */
public class GoodsDetailActivity extends BaseActivity {
    //标题
    private MyTitleView title;
    //产品编码，二维码，名称，厂家，类型，含药量，数量，单价，创建时间
    private TextView tvPid, tvBarCode, tvName, tvSupplier, tvType, tvDose, tvNumber, tvPrice, tvCreateTime,tvGoodsState,tyUseClass;
    //视图库存模型
    private ViewStockModel model;
    //字典操作类
    private CodeDao dao;

    @Override
    public void initView() {
        //加载界面
        setContentView(R.layout.activity_goods_detail);
        dao = new CodeDao(this);
        model = (ViewStockModel) getIntent().getSerializableExtra("PRODUCT");
        title = (MyTitleView) findViewById(R.id.seal_goods_detail_title);
        tvPid = (TextView) findViewById(R.id.goods_detail_pid);
        tvBarCode = (TextView) findViewById(R.id.goods_detail_pbarcode);
        tvName = (TextView) findViewById(R.id.goods_detail_name);
        tvSupplier = (TextView) findViewById(R.id.goods_detail_supplier);
        tvType = (TextView) findViewById(R.id.goods_detail_type);
        tvDose = (TextView) findViewById(R.id.goods_detail_dose);
        tvNumber = (TextView) findViewById(R.id.goods_detail_num);
        tvPrice = (TextView) findViewById(R.id.goods_detail_price);
        tvCreateTime = (TextView) findViewById(R.id.goods_detial_createtime);
        tvGoodsState = (TextView) findViewById(R.id.tv_goods_state);
        tyUseClass = (TextView) findViewById(R.id.goods_detail_use);
        setTextViewVlaue();
    }

    //界面赋值
    public void setTextViewVlaue() {
        tvPid.setText(utils.isEmptyString2(model.getProductStateName()));
        tvBarCode.setText(utils.isEmptyString2(model.getProductId()));
        tvName.setText(utils.isEmptyString2(model.getProductName()));
        tvSupplier.setText(utils.isEmptyString2(model.getSupplierName()));
        List<CodeModel> list = dao.QueryByID("Code_ProductClass", model.getProductClass());
        if (list.size() > 0) {
            tvType.setText(list.get(0).getName());
        }
        tvDose.setText(model.getProductDose() + "g");
        int z = model.getProductTotal()%model.getProductNumber();
        if (0==z){
            tvNumber.setText(model.getProductTotal()/model.getProductNumber() + "箱");
        }else {
            tvNumber.setText(model.getProductTotal()/model.getProductNumber() + "箱"+z+"件");
        }

        tvPrice.setText("¥" + model.getProductRetailPrice());
        tvCreateTime.setText(utils.isEmptyString2(model.getCreateTime()));
        tvGoodsState.setText(utils.isEmptyString2(model.getProductNumber()+""));
        tyUseClass.setText(utils.isEmptyString2(model.getUseClassName()));
    }

    @Override
    public void initEvent() {
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
