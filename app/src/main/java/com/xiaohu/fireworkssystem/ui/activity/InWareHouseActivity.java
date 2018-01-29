package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.seal.SealAddListener;
import com.xiaohu.fireworkssystem.control.seal.SealAddView;
import com.xiaohu.fireworkssystem.control.warehouse.SearchBoxListener;
import com.xiaohu.fireworkssystem.control.warehouse.SearchBoxView;
import com.xiaohu.fireworkssystem.model.table.ActualInventoryModel;
import com.xiaohu.fireworkssystem.model.table.InputWareHouseModel;
import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyOutWarehouseDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.MysaoDialog;
import com.xiaohu.fireworkssystem.view.scan.CaptureActivity;
import com.xiaohu.fireworkssystem.view.scan.CaptureAllActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_SaoBlueToothAddress;
import static com.xiaohu.fireworkssystem.config.MyKeys.ServerDate;

public class InWareHouseActivity extends AppCompatActivity implements SealAddView,SearchBoxView {

    //出入库选择按钮
    private RadioButton radioButton_out,radioButton_allout;
    //法人登记，保存按钮
    private Button btn_personread,btn_save;
    //退货入库模型
    private InputWareHouseModel inputWareHouseModel;
    //法人姓名，总价
    private TextView boss_name_tv,tvMoney,tv_5,tv_4;
    //扫码按钮
    private ImageView iv_inscan,iv_allinscan;
    //工具类
    private Utils utils;
    //给数据源的集合
    private List<ViewBoxCodeStockModel> list;
    private List<ViewBoxCodeStockModel> scannlist;
    //listview的数据源
    private CommonAdapter<ViewBoxCodeStockModel> commonAdapter;
    //显示销售商品列表
    private ListView lv_inwarehouse;
    //访问后台
    private SealAddListener listener;
    //后台提示语
    private String Mymes = "";
    //标题
    private MyTitleView titleView;
    //蓝牙扫码dialog
    private MysaoDialog saoProgressDialog;
    //标记状态
    private boolean allboo = false;
    //进度框
    private ProgressDialog mProgressDialog;
    //总价
    private double prices = 0.0;
    private SearchBoxListener searchBoxListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_ware_house);
        initView();
        initListener();
    }

    private void initListener() {
        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //保存
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_save.setEnabled(false);
                if (Yan()) {
                    //验证数据
                    inputWareHouseModel.setCreateTime(utils.ReadString(ServerDate));
                    inputWareHouseModel.setTargetCompanyId(utils.ReadString("companyID"));

                    //商品清单
                    product_list();
                }else{
                    btn_save.setEnabled(true);
                }
            }
        });
        radioButton_allout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InWareHouseActivity.this,AllOutputActivity.class);
                startActivity(intent);
                finish();
            }
        });
        radioButton_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InWareHouseActivity.this,OutWareHouseActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_personread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(InWareHouseActivity.this, PersonViewInWareHouseActivity.class);
                //跳转到人员录入页面，并接受返回值
                Bundle bundle = new Bundle();
                InputWareHouseModel model = inputWareHouseModel;
                bundle.putSerializable("InputWareHouseModel", model);
                intent.putExtras(bundle);
                startActivityForResult(intent,20);
            }
        });
        tv_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = true;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    if (null != inputWareHouseModel.getCompanyId() && !"".equals(inputWareHouseModel.getCompanyId())) {
                        PackageManager pm = InWareHouseActivity.this.getPackageManager();
                        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                                || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                                || Camera.getNumberOfCameras() > 0;
                        if (hasACamera) {

                            if (list.size()<300){
                                Intent intent = new Intent(InWareHouseActivity.this, CaptureAllActivity.class);
                                startActivityForResult(intent, 15);
                            }else {
                                Toast.makeText(InWareHouseActivity.this,"每单最多添加300箱，请分多单登记",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(InWareHouseActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(InWareHouseActivity.this, "请先读取身份信息", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        tv_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = false;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    if (null != inputWareHouseModel.getCompanyId() && !"".equals(inputWareHouseModel.getCompanyId())) {
                        PackageManager pm = InWareHouseActivity.this.getPackageManager();
                        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                                || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                                || Camera.getNumberOfCameras() > 0;
                        if (hasACamera) {
                            if (list.size()<300){
                                Intent intent = new Intent(InWareHouseActivity.this, CaptureActivity.class);
                                startActivityForResult(intent, 14);
                            }else {
                                Toast.makeText(InWareHouseActivity.this,"每单最多添加300箱，请分多单登记",Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(InWareHouseActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(InWareHouseActivity.this, "请先读取身份信息", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //扫描
        iv_inscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = false;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    if (null != inputWareHouseModel.getCompanyId() && !"".equals(inputWareHouseModel.getCompanyId())) {
                        PackageManager pm = InWareHouseActivity.this.getPackageManager();
                        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                                || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                                || Camera.getNumberOfCameras() > 0;
                        if (hasACamera) {
                            if (list.size()<300){
                                Intent intent = new Intent(InWareHouseActivity.this, CaptureActivity.class);
                                startActivityForResult(intent, 14);
                            }else {
                                Toast.makeText(InWareHouseActivity.this,"每单最多添加300箱，请分多单登记",Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(InWareHouseActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(InWareHouseActivity.this, "请先读取身份信息", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        iv_allinscan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = true;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    if (null != inputWareHouseModel.getCompanyId() && !"".equals(inputWareHouseModel.getCompanyId())) {
                        PackageManager pm = InWareHouseActivity.this.getPackageManager();
                        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                                || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                                || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                                || Camera.getNumberOfCameras() > 0;
                        if (hasACamera) {

                            if (list.size()<300){
                                Intent intent = new Intent(InWareHouseActivity.this, CaptureAllActivity.class);
                                startActivityForResult(intent, 15);
                            }else {
                                Toast.makeText(InWareHouseActivity.this,"每单最多添加300箱，请分多单登记",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(InWareHouseActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(InWareHouseActivity.this, "请先读取身份信息", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        commonAdapter = new CommonAdapter<ViewBoxCodeStockModel>(InWareHouseActivity.this, list, R.layout.lv_goods_seal_item) {
            @Override
            public void convert(final ViewHolder holder, final ViewBoxCodeStockModel goodsModel, final int position) {
                holder.setText(R.id.seal_item_gname, goodsModel.getProductName());
                holder.setText(R.id.seal_goods_perice, Float.toString(goodsModel.getProductRetailPrice()*goodsModel.getProductNumber()));
                holder.setText(R.id.seal_goods_num, goodsModel.getBoxCode());
                holder.setText(R.id.stock_goods_num, "1箱");
                if (goodsModel.getBusinessCount() != goodsModel.getProductNumber()){
                    holder.setTextColorRes(R.id.stock_goods_num,R.color.title);
                    holder.setText(R.id.stock_goods_num, "不足");
                }else {
                    holder.setTextColorRes(R.id.stock_goods_num,R.color.line_gray);

                }
                holder.setOnClickListener(R.id.goods_item_editer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MyOutWarehouseDialog dialog = new MyOutWarehouseDialog(InWareHouseActivity.this, goodsModel.getProductName(), Float.toString(goodsModel.getProductRetailPrice()*goodsModel.getProductNumber()) + "",goodsModel.getBoxCode() + "" ,goodsModel.getProductTotal()/goodsModel.getProductNumber()+ "", "产品剩余(箱)：：");
                        dialog.setBtnLeftText("删除");
                        dialog.setBtnRightText("保存");
                        dialog.setDeleteListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除
                                list.remove(list.get(position));
                                dialog.dismiss();
                                jiSuanMoney();
                                commonAdapter.notifyDataSetChanged();
                            }
                        });
                        dialog.setSave(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //取消
                                if (dialog.getGoodsNum().equals("")) {
                                    Toast.makeText(InWareHouseActivity.this, "箱码不能为空！", Toast.LENGTH_SHORT).show();
                                    Mymes = "";
                                }  else if (9!=dialog.getGoodsNum().toString().length()){
                                    Toast.makeText(InWareHouseActivity.this, "请输入正确箱码！", Toast.LENGTH_SHORT).show();
                                    Mymes = "";
                                }else {
                                    String startCode = list.get(position).getBoxCode();
                                    String endCode = dialog.getGoodsNum();
                                    startCode = startCode.substring(1,9);
                                    endCode = endCode.substring(1,9);
                                    int startCodes = Integer.parseInt(startCode);
                                    int endCodes = Integer.parseInt(endCode);
                                    String stringyear = list.get(position).getBoxCode().substring(0,1);
                                    if ( startCodes > endCodes) {
                                        Toast.makeText(InWareHouseActivity.this, "小于起始箱码！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }
                                    if (dialog.getGoodsPrice().trim().equals("")) {
                                        Toast.makeText(InWareHouseActivity.this, "请输入单价！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else if(dialog.getGoodsPrice().indexOf(".")!=dialog.getGoodsPrice().lastIndexOf(".")) {
                                        Toast.makeText(InWareHouseActivity.this, "请检查单价输入格式！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else {
                                        try {
                                            Float price = Float.parseFloat(dialog.getGoodsPrice())/list.get(0).getProductNumber();
                                            if (endCodes-startCodes>0&&((list.size()+(endCodes-startCodes-1))<=300)){
                                                String s = "00000000"+(startCodes+1);
                                                s = s.substring(s.length()-8,s.length());
                                                String e = "00000000"+endCodes;
                                                e = e.substring(e.length()-8,e.length());
                                                prices = price;
                                                searchBoxListener.Searchbox(stringyear+s,stringyear+e,inputWareHouseModel.getCompanyId());
                                              /*  for (int i = startCodes+1;i<=endCodes;i++){
                                                    ViewBoxCodeStockModel m = new ViewBoxCodeStockModel();
                                                    *//*m.setProductNumber(list.get(position).getProductNumber());
                                                    m.setBusinessCount(list.get(position).getBusinessCount());
                                                    m.setCompanyId(list.get(position).getCompanyId());
                                                    m.setProductTotal(list.get(position).getProductTotal());
                                                    m.setProductRetailPrice(price);
                                                    m.setProductName(list.get(position).getProductName());
                                                    m.setProductId(list.get(position).getProductId());
                                                    m.setStockId(list.get(position).getStockId());
                                                    m.setProductClassName(list.get(position).getProductClassName());
                                                    m.setCompanyName(list.get(position).getCompanyName());
                                                    m.setProductPackagingUnit(list.get(position).getProductPackagingUnit());
                                                    m.setSupplierName(list.get(position).getSupplierName());*//*

                                                    String s = "00000000"+i;
                                                    s = s.substring(s.length()-8,s.length());
                                                    m.setBoxCode(stringyear+s);
                                                    prices = price;
                                                    //list.add(m);
                                                    listener.SearchGoodsByBarCode(stringyear+s,inputWareHouseModel.getCompanyId());
                                                }*/
                                                // commonAdapter.notifyDataSetChanged();
                                            }else if (endCodes==startCodes){

                                            }else {
                                                Toast.makeText(InWareHouseActivity.this,"每单最多添加300箱",Toast.LENGTH_LONG).show();
                                            }


                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        holder.setText(R.id.seal_goods_perice, dialog.getGoodsPrice());
                                        commonAdapter.notifyDataSetChanged();
                                        list.get(position).setProductRetailPrice(Float.parseFloat(dialog.getGoodsPrice())/list.get(position).getProductNumber());
                                        jiSuanMoney();
                                        dialog.dismiss();
                                    }
                                }
                            }
                        });
                        dialog.show();
                    }
                });
            }
        };
        List<ViewBoxCodeStockModel> list_fan = new ArrayList<>();
        lv_inwarehouse.setAdapter(commonAdapter);
    }

    private void initView() {
        radioButton_allout = (RadioButton) findViewById(R.id.radioButton_allout);
        radioButton_out = (RadioButton) findViewById(R.id.radioButton_out);
        btn_personread = (Button) findViewById(R.id.btn_seal_personread);
        boss_name_tv = (TextView) findViewById(R.id.inbossname_tv);
        tvMoney = (TextView) findViewById(R.id.add_inwarehouse_money);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        iv_inscan = (ImageView) findViewById(R.id.iv_inscan);
        btn_save = (Button) findViewById(R.id.btn_inwarehouse_save);
        lv_inwarehouse = (ListView) findViewById(R.id.lv_inwarehouse);
        iv_allinscan = (ImageView) findViewById(R.id.iv_allinscan);
        titleView = (MyTitleView) findViewById(R.id.in_title);
        inputWareHouseModel = new InputWareHouseModel();
        utils = new Utils(InWareHouseActivity.this);
        listener = new SealAddListener(this,InWareHouseActivity.this);
        scannlist=new ArrayList<>();
        list = new ArrayList<>();
        searchBoxListener = new SearchBoxListener(this,this);
    }

    //蓝牙扫码枪扫码
    private void showDialog(){
        saoProgressDialog = new MysaoDialog(InWareHouseActivity.this);
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
                if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    editText.requestFocus();
                    saoProgressDialog.dismiss();
                    String sid = editText.getText().toString().trim();
                    sid = sid.substring(sid.length()-9,sid.length());
                    if (!sid.equals("")) {
                        if (allboo){
                            listener.SearchAllGoodsByBarCode(sid,inputWareHouseModel.getCompanyId());
                        }else {
                            listener.SearchGoodsByBarCode(sid,inputWareHouseModel.getCompanyId());
                        }

                    }
                    return true;
                }
//让mPasswordEdit获取输入焦点
                return false;
            }
        });
    }
    //将人员录入页面的数据保存到总模型里
    private void setModelValue( InputWareHouseModel model) {
        //法人
        inputWareHouseModel.setBoss(model.getBoss());
        //法人身份证
        inputWareHouseModel.setBossCertNumber(model.getBossCertNumber());
        //经办人
        inputWareHouseModel.setAgentId(model.getAgentId());
        //操作人（出库人）
        inputWareHouseModel.setOperatorId(model.getOperatorId());
        //公司id
        inputWareHouseModel.setCompanyId(model.getCompanyId());
        inputWareHouseModel.setTargetCompanyId(utils.ReadString("companyID"));
        boss_name_tv.setText(inputWareHouseModel.getBoss());
    }

    //验证数据是否录入完成
    private boolean Yan() {
        if (inputWareHouseModel == null) {
            Mymes = "请填写人员信息！";
            Toast.makeText(InWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        } else if (inputWareHouseModel.getBoss() == null || inputWareHouseModel.getBoss().equals("")) {
            Mymes = "请填写人员信息！";
            Toast.makeText(InWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }  else if (list.size() == 0) {
            Mymes = "请选择商品";
            Toast.makeText(InWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }
        return true;
    }

    //商品清单
    private void product_list(){
        List<ViewBoxCodeStockModel> lists = new ArrayList<>();
        for (int position = 0; position<list.size();position++){
            ViewBoxCodeStockModel m = new ViewBoxCodeStockModel();

            m.setProductName(list.get(position).getProductName());
            m.setProductId(list.get(position).getProductId());
            m.setMyNumber(list.get(position).getMyNumber());
            lists.add(m);
        }
        //循环，将相同数据叠加
        for (int i=0 ; i<lists.size() ; i++){
            for (int j=lists.size()-1 ; j>i ; j--){
                if (lists.get(i).getProductId().equals(lists.get(j).getProductId())){
                    lists.get(i).setMyNumber(lists.get(i).getMyNumber()+1);
                    lists.remove(j);
                }
            }
        }

        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();

            listem.put("ProductName", lists.get(i).getProductName());
            listem.put("ProductId",lists.get(i).getProductId());
            listem.put("ProductNumber",lists.get(i).getMyNumber()+"箱");
            listems.add(listem);
        }

        SimpleAdapter simplead = new SimpleAdapter(InWareHouseActivity.this, listems,
                R.layout.item_certificate, new String[] { "ProductName","ProductId","ProductNumber"},
                new int[] {R.id.textView22,R.id.textView23,R.id.textView24});

        LayoutInflater layoutInflater = LayoutInflater.from(InWareHouseActivity.this); // 创建视图容器并设置上下文
        final View view = layoutInflater.inflate(R.layout.dialog_outlist,null); // 获取list_item布局文件的视图
        ListView listView = (ListView) view.findViewById(R.id.lv_dialog);
        listView.setAdapter(simplead);
        new AlertDialog.Builder(InWareHouseActivity.this).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //进度条对话框
                mProgressDialog = new ProgressDialog(InWareHouseActivity.this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setTitle("请稍后");
                mProgressDialog.setMessage("正在提交数据");
                mProgressDialog.show();
                listener.addInWarehouse(inputWareHouseModel, list );
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btn_save.setEnabled(true);
            }
        }).show();
    }
    //计算总钱数
    private String jiSuanMoney() {
        double money = 0.0;
        if(list==null){
            list=new ArrayList<>();
        }
        for (int i = 0; i < list.size(); i++) {
            ViewBoxCodeStockModel model = list.get(i);
            if (0!=model.getProductNumber()){
                money = money + model.getProductNumber() * model.getProductRetailPrice();
            }

        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        tvMoney.setText(p);
        inputWareHouseModel.setAmount(Float.parseFloat(p));
        return p;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (20==resultCode){

            if (null != data.getSerializableExtra("InputWareHouseModel")){
                InputWareHouseModel inputWareHouseModel = (InputWareHouseModel) data.getSerializableExtra("InputWareHouseModel");
                boss_name_tv.setText(inputWareHouseModel.getBoss());
                setModelValue(inputWareHouseModel);
            }
        }

        //扫描条形码返回的条形码
        if (resultCode == 14) {
            String sid = data.getStringExtra("BarScan");
            if (!sid.equals("")) {
                listener.SearchGoodsByBarCode(sid,inputWareHouseModel.getCompanyId());
            }
        }
        //扫描条形码返回的条形码
        if (resultCode == 15) {
            String sid = data.getStringExtra("BarScan");
            if (!sid.equals("")) {

                listener.SearchAllGoodsByBarCode(sid,inputWareHouseModel.getCompanyId());
            }
        }
    }

    @Override
    public void addResult(boolean b, String mes) {
        mProgressDialog.dismiss();
        this.Mymes = mes;
        InWareHouseActivity.this.runOnUiThread(run);
    }

    @Override
    public void SearchBill(String mes, List<ViewSaleRecordModel> list) {

    }

    //通过条形码查询返回的商品信息
    @Override
    public void SearchGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel) {
        Mymes = mes;
        if (viewBoxCodeStockModel.getProductId() != null) {
            boolean isYou = true;
            viewBoxCodeStockModel.setMyNumber(1);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getBoxCode().equals(viewBoxCodeStockModel.getBoxCode())) {
                    isYou = false;
                    break;
                }
            }
            if (isYou) {
                if(scannlist==null){

                    scannlist=new ArrayList<>();
                }
                if (viewBoxCodeStockModel.getBusinessCount()==viewBoxCodeStockModel.getProductNumber()){
                    scannlist.add(viewBoxCodeStockModel);
                } else {
                    Mymes = "此箱产品不足一箱";
                    scannlist.add(viewBoxCodeStockModel);
                }
            } else {
                Mymes = "产品已存在列表中！";
                scannlist.add(viewBoxCodeStockModel);
            }
            InWareHouseActivity.this.runOnUiThread(rn);
        } else {
            InWareHouseActivity.this.runOnUiThread(rnn);
        }
    }

    @Override
    public void SearchAllGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel) {
        Mymes = mes;
        if (viewBoxCodeStockModel.getProductId() != null) {
            boolean isYou = true;
            viewBoxCodeStockModel.setMyNumber(1);
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getBoxCode().equals(viewBoxCodeStockModel.getBoxCode())) {
                    isYou = false;
                    break;
                }
            }
            if (isYou) {
                if(scannlist==null){
                    scannlist=new ArrayList<>();
                }
                if (viewBoxCodeStockModel.getBusinessCount()==viewBoxCodeStockModel.getProductNumber()){
                    scannlist.add(viewBoxCodeStockModel);
                } else {
                    scannlist.add(viewBoxCodeStockModel);
                    Mymes = "此箱产品不足一箱";
                }
            } else {
                scannlist.add(viewBoxCodeStockModel);
                Mymes = "产品已存在列表中！";
            }
            InWareHouseActivity.this.runOnUiThread(rrn);
        } else {
            InWareHouseActivity.this.runOnUiThread(rnn);
        }
    }

    @Override
    public void Inventory(String mes, ActualInventoryModel inventoryModelList) {

    }

    Runnable rrn = new Runnable() {
        @Override
        public void run() {
            if(scannlist!=null) {
                list.addAll(scannlist);
                scannlist=null;
            }
            commonAdapter.notifyDataSetChanged();
            jiSuanMoney();

            if (!Mymes.equals("")) {
                Toast.makeText(InWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {

                    if (list.size()<300){
                        Intent intent = new Intent(InWareHouseActivity.this, CaptureAllActivity.class);
                        startActivityForResult(intent, 15);
                    }else {
                        Toast.makeText(InWareHouseActivity.this,"每单最多添加300箱，请分多单登记",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    };
    Runnable rnn = new Runnable() {
        @Override
        public void run() {
            btn_save.setEnabled(true);
            if (!Mymes.equals("")) {
                Toast.makeText(InWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
            }
        }
    };
    Runnable rn = new Runnable() {
        @Override
        public void run() {
            if(scannlist!=null) {
                list.addAll(scannlist);
                scannlist=null;
            }
            //产品排序
            Collections.sort(list, new Comparator<ViewBoxCodeStockModel>(){

                /*
                 * int compare(Student o1, Student o2) 返回一个基本类型的整型，
                 * 返回负数表示：o1 小于o2，
                 * 返回0 表示：o1和o2相等，
                 * 返回正数表示：o1大于o2。
                 */
                public int compare(ViewBoxCodeStockModel o1, ViewBoxCodeStockModel o2) {

                    //按照箱码大小排列顺序
                    if(Integer.parseInt(o1.getBoxCode().substring(1,9)) > Integer.parseInt(o2.getBoxCode().substring(1,9))){
                        return 1;
                    }
                    if(Integer.parseInt(o1.getBoxCode().substring(1,9)) == Integer.parseInt(o2.getBoxCode().substring(1,9))){
                        return 0;
                    }
                    return -1;
                }
            });

            commonAdapter.notifyDataSetChanged();
            jiSuanMoney();
            if (!Mymes.equals("")) {
                Toast.makeText(InWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
            }
        }
    };

    Runnable run = new Runnable() {
        @Override
        public void run() {
            btn_save.setEnabled(true);

            Toast.makeText(InWareHouseActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            if ("入库成功".equals(Mymes)){
                list.clear();
                tvMoney.setText("0.000");
            }
            Mymes = "";

            commonAdapter.notifyDataSetChanged();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mProgressDialog = null;
        commonAdapter = null;
        list = null;
        listener = null;
        lv_inwarehouse = null;
    }

    @Override
    public void SearchBox(List<ViewBoxCodeStockModel> viewStockBoxModelList, String mes) {
        for (int j = 0 ; j<viewStockBoxModelList.size();j++){
            viewStockBoxModelList.get(j).setProductRetailPrice((float) prices);
            Mymes = mes;
            if (viewStockBoxModelList.get(j).getProductId() != null) {
                boolean isYou = true;
                viewStockBoxModelList.get(j).setMyNumber(1);
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getBoxCode().equals(viewStockBoxModelList.get(j).getBoxCode())) {
                        isYou = false;
                        break;
                    }
                }
                //没有相同产品

                if (isYou) {
                    if(scannlist==null){
                        scannlist=new ArrayList<>();
                    }
                    if (0<viewStockBoxModelList.get(j).getBusinessCount()){
                        viewStockBoxModelList.get(j).setProductRetailPrice((float) prices);
                        if (viewStockBoxModelList.get(j).getBusinessCount()==viewStockBoxModelList.get(j).getProductNumber()){

                            scannlist.add(viewStockBoxModelList.get(j));

                        }
                        else {
                            scannlist.add(viewStockBoxModelList.get(j));
                            Mymes = "此箱产品不足一箱";
                        }
                    }else {
                        scannlist.add(viewStockBoxModelList.get(j));
                        Mymes = "此箱产品已销售完";
                    }


                }
                //有相同产品
                else {

                    Mymes = "产品已存在列表中！";

                }

            } else {
                InWareHouseActivity.this.runOnUiThread(rnn);
            }
        }
        InWareHouseActivity.this.runOnUiThread(rn);
    }
}
