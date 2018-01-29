package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.seal.SealAddListener;
import com.xiaohu.fireworkssystem.control.seal.SealAddView;
import com.xiaohu.fireworkssystem.model.table.ActualInventoryModel;
import com.xiaohu.fireworkssystem.model.table.BusinessBoxCodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewProductSaleModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.MyTuiDialog;
import com.xiaohu.fireworkssystem.view.MysaoDialog;
import com.xiaohu.fireworkssystem.view.scan.CaptureActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.xiaohu.fireworkssystem.R.id.seal_goods_perice;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_SaoBlueToothAddress;

/**
 * 销售退货
 * Created by Administrator on 2016/8/8.
 */
public class  SealAddActivity extends BaseActivity implements SealAddView {

    private MyTitleView seal_add_title;
    private ViewProductSaleModel viewProductSaleModel;
    private TextView sell_id_tv,goumai_name_tv,tvMoney;
    private ImageView iv_tuiscan;
    //原list
    private List<ViewSaleRecordModel> list_viewSaleRecordModel;
    //新list
    private List<ViewSaleRecordModel> list;
    //显示的list
    private List<ViewSaleRecordModel> list2;
    private ListView lv_tui;
    //listview的数据源
    private CommonAdapter<ViewSaleRecordModel> commonAdapter;
    private ViewProductSaleModel viewProductSaleModels;
    private String Mymes= "";
    private Button but_save;
    private SealAddListener listener;
    private boolean b = false;
    private MysaoDialog saoProgressDialog;
    //进度框
    private ProgressDialog mProgressDialog;

    @Override
    public void initView() {
        setContentView(R.layout.activity_seal_add);
        viewProductSaleModel = new ViewProductSaleModel();
        viewProductSaleModel = (ViewProductSaleModel) getIntent().getSerializableExtra("VIEWPRODUCTSALEMODEL");
        //分解销售单
        initDate();
        seal_add_title = (MyTitleView) findViewById(R.id.seal_add_title);
        sell_id_tv = (TextView) findViewById(R.id.sell_id_tv);
        sell_id_tv.setText(viewProductSaleModel.getProductSaleId());
        goumai_name_tv = (TextView) findViewById(R.id.goumai_name_tv);
        goumai_name_tv.setText(viewProductSaleModel.getApplyer());
        iv_tuiscan = (ImageView) findViewById(R.id.iv_tuiscan);
        tvMoney = (TextView) findViewById(R.id.tv_total_money);
        lv_tui = (ListView) findViewById(R.id.lv_tui);
        but_save = (Button) findViewById(R.id.seal_tui_save);
        listener = new SealAddListener(this,SealAddActivity.this);
    }

    private void initDate() {
        //分解销售单
        list_viewSaleRecordModel = new ArrayList<ViewSaleRecordModel>();
        list = new ArrayList<>();
        list2 = new ArrayList<>();
        List<BusinessBoxCodeModel> list_bus = new ArrayList<>();
        for (int i = 0; i < viewProductSaleModel.getProductBillOrders().length; i++) {
            list_viewSaleRecordModel.add(viewProductSaleModel.getProductBillOrders()[i]);
        }
        for (int i = 0; i <list_viewSaleRecordModel.size() ; i++){
            list_bus = new ArrayList<>();
            for (int j= 0;j<list_viewSaleRecordModel.get(i).getSaleRecordBoxCode().length;j++){

                list_bus.add(list_viewSaleRecordModel.get(i).getSaleRecordBoxCode()[j]);
            }

            for (int z = 0; z<list_bus.size();z++){
                ViewSaleRecordModel viewSaleRecordModel = new ViewSaleRecordModel();
                BusinessBoxCodeModel[] businessBoxCodeModels = new BusinessBoxCodeModel[1];
                businessBoxCodeModels[0] = list_bus.get(z);
                viewSaleRecordModel.setProductRetailPrice(list_viewSaleRecordModel.get(i).getProductRetailPrice());
                viewSaleRecordModel.setProductName(list_viewSaleRecordModel.get(i).getProductName());
                viewSaleRecordModel.setProductNumber(Math.abs(list_viewSaleRecordModel.get(i).getSaleRecordBoxCode()[0].getBusinessCount()));
                viewSaleRecordModel.setStockId(list_viewSaleRecordModel.get(i).getStockId());
                viewSaleRecordModel.setProductSaleId(list_viewSaleRecordModel.get(i).getProductSaleId());
                viewSaleRecordModel.setProductSaleRecordId(list_viewSaleRecordModel.get(i).getProductSaleRecordId());
                viewSaleRecordModel.setSaleRecordBoxCode(businessBoxCodeModels);
                list.add(viewSaleRecordModel);
                System.out.println(list.toArray().toString());
            }
        }
    }

    @Override
    public void initEvent() {
        seal_add_title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        iv_tuiscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    PackageManager pm = SealAddActivity.this.getPackageManager();
                    boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                            || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                            || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                            || Camera.getNumberOfCameras() > 0;
                    if (hasACamera) {
                        Intent intent = new Intent(SealAddActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, 14);
                    } else {
                        Toast.makeText(SealAddActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        but_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list2.size()>0){
                    but_save.setEnabled(false);
                    System.out.println(list2.get(0).getProductNumber());
                    //进度条对话框
                    mProgressDialog = new ProgressDialog(SealAddActivity.this);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setTitle("请稍后");
                    mProgressDialog.setMessage("正在提交数据");
                    mProgressDialog.show();

                    listener.TuiSave(list2);
                }else {
                    ToastShort("请添加退货商品");
                }

            }
        });

        commonAdapter = new CommonAdapter<ViewSaleRecordModel>(SealAddActivity.this, list2, R.layout.lv_goods_tuihuo_item) {
            @Override
            public void convert(final ViewHolder holder, final ViewSaleRecordModel goodsModel, final int position) {
                holder.setText(R.id.seal_item_gname, goodsModel.getProductName());
                holder.setText(R.id.stock_goods_num, goodsModel.getProductRetailPrice() + "/件");
                holder.setText(R.id.seal_goods_num, goodsModel.getProductNumber()+"/件");
                holder.setText(R.id.seal_goods_perice , goodsModel.getSaleRecordBoxCode()[0].getBoxCode());
                holder.setOnClickListener(R.id.goods_item_editer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MyTuiDialog dialog = new MyTuiDialog(SealAddActivity.this, goodsModel.getProductName(), goodsModel.getProductRetailPrice() + "", "库存数量：",goodsModel.getProductNumber()+"",goodsModel.getProductnums()+"","最多退货数量：");
                        dialog.setBtnLeftText("删除");
                        dialog.setBtnRightText("保存");
                        dialog.setDeleteListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除
                                list2.remove(position);
                                dialog.dismiss();
                                jiSuanMoney();
                                commonAdapter.notifyDataSetChanged ();
                            }
                        });
                     dialog.setSave(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //保存
                                Pattern p = Pattern.compile("[0-9]*");
                                Matcher m = p.matcher(dialog.getGoodsNum());

                                if (dialog.getGoodsPrice().trim().equals("")) {
                                        Toast.makeText(SealAddActivity.this, "请输入单价！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else if(dialog.getGoodsPrice().indexOf(".")!=dialog.getGoodsPrice().lastIndexOf(".")) {
                                        Toast.makeText(SealAddActivity.this, "请检查单价输入格式！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else if (!m.matches()){
                                    Toast.makeText(SealAddActivity.this, "请检查退货数量", Toast.LENGTH_SHORT).show();
                                    Mymes = "";
                                } else {
                                        try {
                                            list2.get(position).setProductNumber(Integer.parseInt(dialog.getGoodsNum()));
                                            list2.get(position).setProductRetailPrice(Float.parseFloat(dialog.getGoodsPrice()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        holder.setText(seal_goods_perice, dialog.getGoodsPrice() + "/件");
                                        commonAdapter.notifyDataSetChanged();
                                        jiSuanMoney();
                                        dialog.dismiss();
                                    }

                            }
                        });
                        dialog.show();
                    }
                });
            }
        };
        lv_tui.setAdapter(commonAdapter);
    }


    @Override
    public void addResult(boolean b, String mes) {

        mProgressDialog.dismiss();
        Mymes = mes;
        this.b = b;
        SealAddActivity.this.runOnUiThread(rnn);
    }
    Runnable rnn = new Runnable() {
        @Override
        public void run() {
            but_save.setEnabled(true);
            if (!Mymes.equals("")) {
                Toast.makeText(SealAddActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
            }
            if (b){
                list2.clear();
                commonAdapter.notifyDataSetChanged();
                tvMoney.setText("0.000");
            }
        }
    };
    @Override
    public void SearchBill(String mes, List<ViewSaleRecordModel> list) {

    }

    @Override
    public void SearchGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel) {

    }

    @Override
    public void SearchAllGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel) {

    }

    @Override
    public void Inventory(String mes, ActualInventoryModel inventoryModelList) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //扫描条形码返回的条形码
        if (resultCode == 14) {
            String sid = data.getStringExtra("BarScan");
            boolean bs = false;
            if (!sid.equals("")) {

            for (int i = 0;i<list.size();i++){
                if (sid.equals(list.get(i).getSaleRecordBoxCode()[0].getBoxCode())){
                   if (list2.size()==0){
                       list.get(i).setProductnums(list.get(i).getProductNumber());
                       list2.add(list.get(i));
                       System.out.println( list.get(i).toString());

                       bs = true;
                       Mymes = "查询成功";

                   }else {
                       for (int j = 0 ; j <list2.size();j++){
                           if ( sid.equals(list2.get(j).getSaleRecordBoxCode()[0].getBoxCode())){
                               bs = true;
                               Mymes = "商品已添加";
                           }else {
                               list2.add(list.get(i));
                               bs = true;
                               Mymes = "查询成功";
                           }
                       }

                   }
                }
            }
                commonAdapter.notifyDataSetChanged();
            if (bs){
                SealAddActivity.this.runOnUiThread(rnn);
            }else {
                Mymes = "商品不在销售单里";
                SealAddActivity.this.runOnUiThread(rnn);
            }

            }
            jiSuanMoney();
        }
    }
    //蓝牙扫码枪扫码
    private void showDialog(){

        saoProgressDialog = new MysaoDialog(SealAddActivity.this);
        saoProgressDialog.setBtnLeftText("取消");
        saoProgressDialog.setDeleteListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saoProgressDialog.dismiss();
            }
        });
        saoProgressDialog.show();

        final EditText editText = (EditText) saoProgressDialog.findViewById(R.id.eet_goods_num);
        editText.setFocusable(true);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean bs = false;
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    editText.requestFocus();
                    saoProgressDialog.dismiss();
                    String sid = editText.getText().toString().trim();
                    sid = sid.substring(sid.length()-9,sid.length());
                    if (!sid.equals("")) {

                        for (int i = 0;i<list.size();i++){
                            if (sid.equals(list.get(i).getSaleRecordBoxCode()[0].getBoxCode())){
                                if (list2.size()==0){
                                    list.get(i).setProductnums(list.get(i).getProductNumber());
                                    list2.add(list.get(i));
                                    System.out.println( list.get(i).toString());

                                    bs = true;
                                    Mymes = "查询成功";

                                }else {
                                    for (int j = 0 ; j <list2.size();j++){
                                        if ( sid.equals(list2.get(j).getSaleRecordBoxCode()[0].getBoxCode())){
                                            bs = true;
                                            Mymes = "商品已添加";
                                        }else {
                                            list2.add(list.get(i));
                                            bs = true;
                                            Mymes = "查询成功";
                                        }
                                    }

                                }
                            }
                        }
                        commonAdapter.notifyDataSetChanged();
                        if (bs){
                            SealAddActivity.this.runOnUiThread(rnn);
                        }else {
                            Mymes = "商品不在销售单里";
                            SealAddActivity.this.runOnUiThread(rnn);
                        }


                    }
                    jiSuanMoney();
                    return true;
                }
//让mPasswordEdit获取输入焦点
                jiSuanMoney();
                return false;
            }
        });
    }
    //计算总钱数
    private String jiSuanMoney() {
        double money = 0.0;
        if(list2==null){
            list2=new ArrayList<>();
        }
        for (int i = 0; i < list2.size(); i++) {
            ViewSaleRecordModel model = list2.get(i);
            money = money + model.getProductNumber() * model.getProductRetailPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        tvMoney.setText(p);
        return p;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;

    }
}
