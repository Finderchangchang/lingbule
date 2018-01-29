package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.model.view.ViewInputWareHouseModel;
import com.xiaohu.fireworkssystem.view.MyTitleView;

/**
 * 入库信息详情
 */
public class ImportDetailActivity extends BaseActivity  {

    //导入按钮
    private Button button_import_product;
    //入库单模型
    private ViewInputWareHouseModel model;
    //标题
    private MyTitleView title;
    //出库单编码，出库单位名称，入库单位，出库时间，备注，总金额，出库人,总量产品,操作人
    private TextView tv_id, tv_cname, tv_inwarehouse_company,tv_time, tv_connect, tv_money, tv_person,tv_amount,tv_operator;

    @Override
    public void initView() {
        setContentView(R.layout.activity_import_detail);

        button_import_product = (Button) findViewById(R.id.import_pdetail);
        title = (MyTitleView) findViewById(R.id.import_detail_title);
        tv_cname = (TextView) findViewById(R.id.aid_cname);
        tv_id = (TextView) findViewById(R.id.aid_id);
        tv_connect = (TextView) findViewById(R.id.aid_content);
        tv_money = (TextView) findViewById(R.id.aid_money);
        tv_person = (TextView) findViewById(R.id.aid_person);
        tv_time = (TextView) findViewById(R.id.aid_time);
        tv_inwarehouse_company = (TextView) findViewById(R.id.inwarehouse_company);
        tv_amount = (TextView) findViewById(R.id.tv_amount);
        tv_operator = (TextView) findViewById(R.id.tv_operator);
        model = (ViewInputWareHouseModel) getIntent().getSerializableExtra("ViewInputWareHouseModel");
        setData();
    }

    @Override
    public void initEvent() {
        button_import_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImportDetailActivity.this, ImportProductActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("InputWareHouseId",model.getInputWareHouseId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //界面赋值
    private void setData() {
        tv_id.setText(utils.isEmptyString2(model.getInputWareHouseId()));
        tv_cname.setText(utils.isEmptyString2(model.getCompanyName()));
        tv_time.setText(utils.isEmptyString2(model.getCreateTime()));
        tv_money.setText(utils.isEmptyString2(model.getAmount()));
        tv_person.setText(utils.isEmptyString2(model.getAgentName()));
        tv_connect.setText(utils.isEmptyString2(model.getComment()));
        tv_inwarehouse_company.setText(utils.isEmptyString2(model.getTargetCompanyName()));
        tv_amount.setText(utils.isEmptyString2(model.getProductSum()+"件"));
        tv_operator.setText(utils.isEmptyString2(model.getOperatorName()));
    }

}
