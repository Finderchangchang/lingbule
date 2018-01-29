package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.sale_detail_goods.SaleGoodsDetailSearchListener;
import com.xiaohu.fireworkssystem.control.sale_detail_goods.SaleGoodsDetailSearchView;
import com.xiaohu.fireworkssystem.model.search.SearchProductOrderModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.printer.Device;
import com.xiaohu.fireworkssystem.printer.PrintService;
import com.xiaohu.fireworkssystem.printer.PrinterClass;
import com.xiaohu.fireworkssystem.printer.PrinterClassFactory;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.util.ArrayList;
import java.util.List;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_PrintBlueToothAddress;

/**
 * 销售信息产品列表
 * Created by Administrator on 2016/8/18.
 */
public class SealDetailGoodsListActivity extends BaseActivity implements SaleGoodsDetailSearchView {
    private ListView lv;
    private List<ViewSaleRecordModel> listGoods;
    private CommonAdapter<ViewSaleRecordModel> adapter;
    //标题
    private MyTitleView title;
    //订单编码，企业名称，购买人，操作人，总金额，销售时间，退货金额
    private String string_OrderID, string_CompanyName, string_applyer, string_operatorname, string_OrderAmount, string_OrderTime, strtui;
    private SaleGoodsDetailSearchListener listener;
    private SearchProductOrderModel searchProductOrderModel;
    private ViewProductSaleModel viewProductSaleModel;
    //打印
    private Button button_print,button_tuihuo;
    // 打印机操作类
    public static PrinterClass pl = null;
    //提示信息
    String mes = "", myMes = "";
    protected static final String TAG = "";
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    Handler mhandler = null;
    Handler handler = null;
    public static List<Device> deviceList = new ArrayList<Device>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_seal_detail_goods_list);
        //初始化打印机
        printer();
        title = (MyTitleView) findViewById(R.id.seal_detail_goods_lsit_title);
        listGoods = new ArrayList<ViewSaleRecordModel>();
        button_print = (Button) findViewById(R.id.button_print);
        button_tuihuo = (Button) findViewById(R.id.button_tuihuo);
        //取得订单号
        Intent intent = this.getIntent();
        string_OrderID = intent.getStringExtra("OrderID");
        string_CompanyName = intent.getStringExtra("CompanyName");
        string_applyer = intent.getStringExtra("Applyer");
        string_operatorname = intent.getStringExtra("OperatorName");
        string_OrderAmount = intent.getStringExtra("OrderAmount");
        string_OrderTime = intent.getStringExtra("OrderTime");
        strtui = intent.getStringExtra("OrderTui");

        lv = (ListView) findViewById(R.id.seal_goods_list_lv);
        listener = new SaleGoodsDetailSearchListener(this, SealDetailGoodsListActivity.this);
        searchProductOrderModel = new SearchProductOrderModel();
        //没有数据时提示用户
        TextView emptyView = new TextView(SealDetailGoodsListActivity.this);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        emptyView.setText("没有信息");
        emptyView.setGravity(Gravity.CENTER);
        emptyView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) lv.getParent()).addView(emptyView);
        lv.setEmptyView(emptyView);
    }

    @Override
    public void initEvent() {
        //退货
        button_tuihuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=viewProductSaleModel){
                    Intent intent = new Intent(SealDetailGoodsListActivity.this, SealAddActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("VIEWPRODUCTSALEMODEL", viewProductSaleModel);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else {
                    ToastShort("没有销售产品");
                }

            }
        });
        //打印
        button_print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!button_print.getText().toString().equals("正在连接")) {
                    button_print.setText("正在连接");
                    ConnectPrinter();
                }
            }
        });
        searchProductOrderModel.setOrderID(string_OrderID);
        //加载销售产品列表
        listener.search(searchProductOrderModel);
        adapter = new CommonAdapter<ViewSaleRecordModel>(SealDetailGoodsListActivity.this, listGoods, R.layout.lv_goods_seal_item3) {
            @Override
            public void convert(ViewHolder holder, final ViewSaleRecordModel goodsModel, final int position) {
                holder.setText(R.id.seal_item_gname, goodsModel.getProductName()+" / "+goodsModel.getProductClassName());
                holder.setText(R.id.seal_goods_perice, goodsModel.getProductSaleAmount()+"");
                holder.setText(R.id.seal_goods_num, goodsModel.getProductNumber() + "个");
                holder.setText(R.id.stock_goods_num, goodsModel.getPrdouctSaleStateName());
            }
        };
        lv.setAdapter(adapter);
        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //处理查询结果
    @Override
    public void SaleGoodsDetailSearchView(String message, List<ViewSaleRecordModel> list , ViewProductSaleModel model) {
        this.listGoods.addAll(list);
        myMes = message;
        viewProductSaleModel = model;
        SealDetailGoodsListActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            adapter.notifyDataSetChanged();
            if (!myMes.equals(""))
                Toast.makeText(SealDetailGoodsListActivity.this, myMes, Toast.LENGTH_SHORT).show();
        }
    };


    //连接打印机设备
    private void ConnectPrinter() {
        if (utils.ReadString(KEY_PrintBlueToothAddress).equals("")) {
            Toast.makeText(SealDetailGoodsListActivity.this, "蓝牙打印机未连接", Toast.LENGTH_SHORT).show();
            button_print.setText("打印");
        } else {
            Toast.makeText(SealDetailGoodsListActivity.this, "正在连接蓝牙打印机", Toast.LENGTH_SHORT).show();
            if (!pl.IsOpen()) {
                pl.open(SealDetailGoodsListActivity.this);
            }
            pl.scan();
            String address = utils.ReadString(KEY_PrintBlueToothAddress);
            pl.connect(address);
        }
    }

    //连接打印
    private void printer() {
        mhandler = new Handler() {
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MESSAGE_READ:
                        byte[] readBuf = (byte[]) msg.obj;
                        Log.i(TAG, "readBuf:" + readBuf[0]);
                        if (readBuf[0] == 0x13) {
                            PrintService.isFUll = true;
                        } else if (readBuf[0] == 0x11) {
                            PrintService.isFUll = false;
                        } else {
                            String readMessage = new String(readBuf, 0, msg.arg1);
                            if (readMessage.contains("800"))// 80mm paper
                            {
                                PrintService.imageWidth = 72;
//                                Toast.makeText(getApplicationContext(), "80mm",
//                                        Toast.LENGTH_SHORT).show();
                            } else if (readMessage.contains("580"))// 58mm paper
                            {
                                PrintService.imageWidth = 48;
//                                Toast.makeText(getApplicationContext(), "58mm",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                    case MESSAGE_STATE_CHANGE:// 蓝牙连接状
                        switch (msg.arg1) {
                            case PrinterClass.STATE_CONNECTED:// 已经连接
                                break;
                            case PrinterClass.STATE_CONNECTING:// 正在连接
                                button_print.setText("正在连接");
                                break;
                            case PrinterClass.STATE_LISTEN:
                            case PrinterClass.STATE_NONE:
                                break;
                            case PrinterClass.SUCCESS_CONNECT:
                                pl.write(new byte[]{0x1b, 0x2b});// 检测打印机型号
                                button_print.setText("连接成功");
                                PrinterMes();
                                break;
                            case PrinterClass.FAILED_CONNECT:
                                button_print.setText("连接失败");
                                mes = "";
                                break;
                            case PrinterClass.LOSE_CONNECT:
                                button_print.setText("丢失连接");
                                mes = "";
                        }
                        break;
                    case MESSAGE_WRITE:

                        break;
                }
                super.handleMessage(msg);
            }
        };

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0:
                        break;
                    case 1:// 扫描完毕
                        Device d = (Device) msg.obj;
                        if (d != null) {
                            if (deviceList == null) {
                                deviceList = new ArrayList<Device>();
                            }
                            if (!checkData(deviceList, d)) {
                                deviceList.add(d);
                            }
                        }
                        break;
                    case 2:// 停止扫描
                        break;
                }
            }
        };
        pl = PrinterClassFactory.create(0, SealDetailGoodsListActivity.this, mhandler, handler);
    }

    //打印内容
    private void PrinterMes() {
        if (pl != null) {
            if (pl.getState() == PrinterClass.STATE_CONNECTED) {
                //String ss = button_print.getText().toString();
                // if (button_print.getText().toString().equals("连接成功")) {
                getPrinterMes();
                pl.printText(mes + "\r\n\r\n\r\n");
                mes = "";
                finish();
                //}
            } else {
                Toast.makeText(SealDetailGoodsListActivity.this, "打印机连接失败!", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SealDetailGoodsListActivity.this, "打印机连接失败!", Toast.LENGTH_SHORT).show();
        }
    }

    //是否有打印设备
    private boolean checkData(List<Device> list, Device d) {
        for (Device device : list) {
            if (device.deviceAddress.equals(d.deviceAddress)) {
                return true;
            }
        }
        return false;
    }

    //拼接打印信息
    private void getPrinterMes() {
        String s = "";
        mes = "";
        mes += "\r\n";
        mes += "\r\n";
        mes += string_CompanyName + "\n";
        mes += "销售单号：" + string_OrderID + "\n";
        mes += "购买人：" + string_applyer + "  销售人：" + string_operatorname + "\r\n";
        mes += "销售日期：" + string_OrderTime + "\n";
        mes += "商品名称          总价   数量  \r\n";
        mes += "================================\r\n";
        for (int i = 0; i < listGoods.size(); i++) {
            if ("退货".equals(listGoods.get(i).getPrdouctSaleStateName())){
                s = "-";
            }
            mes += listGoods.get(i).getProductName() + "\r\n";
            mes += listGoods.get(i).getProductId() + "         " +s+listGoods.get(i).getProductRetailPrice()*listGoods.get(i).getProductNumber() + "  " + listGoods.get(i).getProductNumber() + "件" + "\r\n";
            s = "";
        }
        mes += "================================" + "\r\n";
        mes += "总金额：" + string_OrderAmount + "\r\n" ;
        mes += "------------谢谢惠顾！----------" + "\r\n";
        mes += "请安全、正确、文明燃放烟花爆竹！" + "\r\n";
        mes += "\r\n";
        mes += "\r\n";
    }

    @Override
    protected void onStop() {
        super.onStop();
        pl.disconnect();
    }
}
