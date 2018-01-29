package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * 销售信息详情
 * Created by Administrator on 2016/8/4.
 */
public class SaleDetailActivity extends BaseActivity {
    private Button goGoodsList;
    private ViewProductSaleModel model;
    private TextView textView_OrderID, textView_OrderTime, textView_appleyer,
            textView_nation, textView_ApplyerCertNumber, textView_ApplyerMobilePhone,
            textView_ApplyerAddress, textView_PurposeName, textView_UseTime, textView_OrderAmount,
            textView_OperatorName, textView_ReturnAmount, ranfang;
    private ImageView person_header;
    private String orderidstr, companynamestr, applyer, operatorname, orderamount, ordertime;
    private ImageView title;
    private CodeDao dao;
    private static final int ERROR = 1;
    private static final int SUCCESS = 2;
    private Utils utils;

    @Override
    public void initView() {
        setContentView(R.layout.activity_seal_detail);
        dao = new CodeDao(this);
        utils = new Utils(SaleDetailActivity.this);
        goGoodsList = (Button) findViewById(R.id.btn_go_goods_list);
        model = (ViewProductSaleModel) getIntent().getSerializableExtra("ORDERMODEL");
        textView_OrderID = (TextView) findViewById(R.id.textView_OrderID);
        textView_OrderTime = (TextView) findViewById(R.id.textView_OrderTime);
        textView_appleyer = (TextView) findViewById(R.id.textView_appleyer);
        textView_nation = (TextView) findViewById(R.id.textView_nation);
        textView_ApplyerCertNumber = (TextView) findViewById(R.id.textView_ApplyerCertNumber);
        textView_ApplyerMobilePhone = (TextView) findViewById(R.id.textView_ApplyerMobilePhone);
        textView_ApplyerAddress = (TextView) findViewById(R.id.textView_ApplyerAddress);
        textView_PurposeName = (TextView) findViewById(R.id.textView_PurposeName);
        textView_UseTime = (TextView) findViewById(R.id.textView_UseTime);
        textView_OrderAmount = (TextView) findViewById(R.id.textView_OrderAmount);
        textView_OperatorName = (TextView) findViewById(R.id.textView_OperatorName);
        textView_ReturnAmount = (TextView) findViewById(R.id.textView_ReturnAmount);
        //textView_CreatTime = (TextView) findViewById(R.id.textView_CreatTime);
        person_header = (ImageView) findViewById(R.id.seal_detail_header);
        title = (ImageView) findViewById(R.id.img_left);
        ranfang = (TextView) findViewById(R.id.textView_ranfang);
        setTextViewVlaue();
    }

    private void setTextViewVlaue() {
        textView_OrderID.setText(model.getProductSaleId());
        String time = model.getCreateTime();
        String times = model.getCreateTime();
        textView_OrderTime.setText(time.substring(5, 7) + "月" + times.substring(8, 10) + "日 " + model.getCreateTime().substring(11, 13) + "时");
        textView_appleyer.setText("购买人：" + model.getApplyer());
        //textView_CreatTime.setText("销售时间："+model.getCreateTime());
        List<CodeModel> list = dao.QueryByID("Code_Nation", model.getApplyerNation());
        if (list.size() > 0) {
            textView_nation.setText("民族：" + list.get(0).getName());
        }

        ranfang.setText("销售状态：" +Utils.isEmptyString2(model.getOrderStateName()));

        textView_ApplyerCertNumber.setText("证件号码：" + model.getApplyerCertNumber());
        if(model.getApplyerMobilePhone().equals("")){
            textView_ApplyerMobilePhone.setText("联系方式：" + model.getApplyerMobilePhone()+"无");
        }else {
            textView_ApplyerMobilePhone.setText("联系方式：" + model.getApplyerMobilePhone());

        }
        textView_ApplyerAddress.setText("通讯地址：" + model.getApplyerAddress());
        if (model.getPurpose() != null) {
            List<CodeModel> list2 = dao.QueryByID("Code_Purpose", model.getPurpose());
            if (list2.size() > 0) {
                textView_PurposeName.setText("购买用途：" + list2.get(0).getName());
            }
        }
        if (null == model.getCompanyName()) {
            textView_UseTime.setText("销售单位：无");
        } else {
            textView_UseTime.setText("销售单位：" + model.getCompanyName());
        }

        textView_OrderAmount.setText("¥" + model.getOrderAmount());
        //System.out.println(model.getOperatorName());
        if (null == model.getOperatorName()) {
            textView_OperatorName.setText("无");
        } else {
            textView_OperatorName.setText(model.getOperatorName());
        }
        textView_ReturnAmount.setText("¥" + model.getReturnAmount());
        orderidstr = model.getProductSaleId();
        companynamestr = model.getCompanyName();
        applyer = model.getApplyer();
        operatorname = model.getOperatorName();
        orderamount =( model.getOrderAmount() - model.getReturnAmount())+"";
        ordertime = model.getCreateTime();
        final String stringheaderid = model.getProductSaleId();
        new Thread() {
            public void run() {
                //获取okHttp对象get请求,

                try {
                    OkHttpClient client = new OkHttpClient();
                    //获取请求对象HeadImageID=13010000010058
                    String string = utils.ReadString("IP");
                    String url = getResources().getString(R.string.http)+"://" + string + "/Employee/GetHeadImage?HeadImageID=ApplyHead-" + stringheaderid;
                    Request request = new Request.Builder().url(url).build();
                    //获取响应体
                    ResponseBody body = client.newCall(request).execute().body();
                    //获取流
                    InputStream in = body.byteStream();
                    //转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    //使用Hanlder发送消息
                    Message msg = Message.obtain();
                    msg.what = SUCCESS;
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                    //失败
                    Message msg = Message.obtain();
                    msg.what = ERROR;
                    handler.sendMessage(msg);
                }
            }
        }.start();
    }

    @Override
    public void initEvent() {
        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            //产品订单详情
            case R.id.btn_go_goods_list:
                Intent intent = new Intent();
                intent.putExtra("OrderID", orderidstr);
                intent.putExtra("OrderTime", ordertime);
                intent.putExtra("CompanyName", companynamestr);
                intent.putExtra("OperatorName", operatorname);
                intent.putExtra("Applyer", applyer);
                intent.putExtra("OrderAmount", orderamount);
                intent.putExtra("OrderTui", model.getReturnAmount() + "");
                intent.setClass(SaleDetailActivity.this, SealDetailGoodsListActivity.class);
                startActivity(intent);
                break;
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SUCCESS:
                    person_header.setImageBitmap((Bitmap) msg.obj);
                    if(null==msg.obj){
                        person_header.setImageResource(R.mipmap.person_header);
                    }
                    break;
                case ERROR:
                    Toast.makeText(SaleDetailActivity.this, "请求超时,请检查网络", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
            }
        }
    };
}
