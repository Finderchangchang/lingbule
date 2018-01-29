package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.seal.SealAddListener;
import com.xiaohu.fireworkssystem.control.seal.SealAddView;
import com.xiaohu.fireworkssystem.model.table.ActualInventoryModel;
import com.xiaohu.fireworkssystem.model.table.HeadImageModel;
import com.xiaohu.fireworkssystem.model.table.ProductSaleModel;
import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.printer.Device;
import com.xiaohu.fireworkssystem.printer.PrintService;
import com.xiaohu.fireworkssystem.printer.PrinterClass;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.LLDialog;
import com.xiaohu.fireworkssystem.view.MyDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.MysaoDialog;
import com.xiaohu.fireworkssystem.view.scan.CaptureActivity;
import com.xiaohu.fireworkssystem.view.scan.CaptureAllActivity;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_BlueToothAddress;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_SaoBlueToothAddress;
import static com.xiaohu.fireworkssystem.config.MyKeys.ServerDate;
import static com.xiaohu.fireworkssystem.ui.activity.SealDetailGoodsListActivity.pl;

/**
 * 销售登记
 * fragment改为activity
 * zz
 * 2017/5/23
 */
public class SalesRegistrationActivity extends AppCompatActivity implements SealAddView {

    private LLDialog llDialog;
    //显示销售商品列表
    private ListView lv;
    //listview的数据源
    private CommonAdapter<ViewBoxCodeStockModel> commonAdapter;
    //给数据源的集合
    private List<ViewBoxCodeStockModel> list;
    private List<ViewBoxCodeStockModel> scannlist;
    //跳转到商品列表页面按钮，扫描条形码
    private ImageView imgAdd, imgScan , imgallScan;
    //录入人员，保存，打印
    private Button btnPerson, btnSave;
    //总金额，购买人员姓名
    private TextView tvMoney, tvPersonNAME,tv_5,tv_4;
    //标题
    private MyTitleView titleView;
    //销售单模型
    private ProductSaleModel orderModel;
    //访问后台
    private SealAddListener listener;
    //保存后台返回的信息，显示到页面
    private String Mymes = "";
    //暂时燃放人员的信息
    private String employee = "";
    //工具类
    private Utils utils;
    //后台提示语
    private String mes = "";

    protected static final String TAG = "";
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    Handler mhandler = null;
    Handler handler = null;
    //标记状态
    private boolean allboo = false;
    private boolean b = false;
    private String string_applyer = "", string_OperatorName = "";
    //蓝牙列表
    public static List<Device> deviceList = new ArrayList<Device>();
    //蓝牙扫码dialog
    private MysaoDialog saoProgressDialog;
    //进度框
    private ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_registration);

        initView();
        initEvent();
    }

    private void initView() {
        orderModel = new ProductSaleModel();
        lv = (ListView) findViewById(R.id.order_list_lv);
        imgAdd = (ImageView) findViewById(R.id.seal_add_img_goods);
        btnPerson = (Button) findViewById(R.id.btn_seal_personread);
        tvMoney = (TextView) findViewById(R.id.seal_add_total_money);
        imgScan = (ImageView) findViewById(R.id.img_goods_scan);
        imgallScan = (ImageView) findViewById(R.id.img_allgoods_scan);
        btnSave = (Button) findViewById(R.id.btn_seal_add_save);
        tvPersonNAME = (TextView) findViewById(R.id.goumai_name_tv);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        llDialog = new LLDialog(SalesRegistrationActivity.this);
        utils = new Utils(SalesRegistrationActivity.this);
        titleView = (MyTitleView) findViewById(R.id.sealsregistration_title);
        printer();
        scannlist=new ArrayList<>();
        list = new ArrayList<>();
        listener = new SealAddListener(this,SalesRegistrationActivity.this);
    }



    public void initEvent() {
        //保存
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=orderModel.getOperatorId() && !"".equals(orderModel.getOperatorId().toString().trim())){
                    btnSave.setEnabled(false);
                    if (Yan()) {
                        //验证数据
                        orderModel.setCreateTime(utils.ReadString(ServerDate));
                        orderModel.setCompanyId(utils.ReadString("companyID"));

                 /*   if(null!=orderModel.getApplyerHeadImage()){
                        HeadImageModel model=orderModel.getApplyerHeadImage();
                        if(model.getHeadImage()!=null&&(!model.getHeadImage().equals(""))) {
                            Bitmap b2 = Utils.getimage(300, model.getHeadImage());
                            model.setHeadImageBase64(Utils.bitmapToBase64(b2));
                            model.setHeadImage("");
                            orderModel.setApplyerHeadImage(model);
                        }
                    }*/
                        //商品清单
                        product_list();

                    }else{
                        btnSave.setEnabled(true);
                    }
                }else {
                    Toast.makeText(SalesRegistrationActivity.this,"请登记购买人信息",Toast.LENGTH_SHORT).show();
                }

            }
        });

        commonAdapter = new CommonAdapter<ViewBoxCodeStockModel>(SalesRegistrationActivity.this, list, R.layout.lv_goods_seal_item) {
            @Override
            public void convert(final ViewHolder holder, final ViewBoxCodeStockModel goodsModel, final int position) {
                holder.setText(R.id.seal_item_gname, goodsModel.getProductName());
                holder.setText(R.id.seal_goods_perice, goodsModel.getProductRetailPrice() + "/个");
                holder.setText(R.id.seal_goods_num, goodsModel.getMyNumber() + "个");
                holder.setText(R.id.stock_goods_num, goodsModel.getBusinessCount()+ "个");
                holder.setOnClickListener(R.id.goods_item_editer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MyDialog dialog = new MyDialog(SalesRegistrationActivity.this, goodsModel.getProductName(), goodsModel.getProductRetailPrice() + "",goodsModel.getMyNumber()+"",goodsModel.getBusinessCount() + "" + "", "库存数量：");
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
                                    Toast.makeText(SalesRegistrationActivity.this, "数量不能为空！", Toast.LENGTH_SHORT).show();
                                    Mymes = "";
                                } else {
                                    if ( Integer.parseInt(dialog.getGoodsNum()) > goodsModel.getBusinessCount()) {
                                        Toast.makeText(SalesRegistrationActivity.this, "大于库存数量！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else if (0 == Integer.parseInt(dialog.getGoodsNum()) ) {
                                        Toast.makeText(SalesRegistrationActivity.this, "请输入购买数量！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    } else if (dialog.getGoodsPrice().trim().equals("")) {
                                        Toast.makeText(SalesRegistrationActivity.this, "请输入单价！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else if(dialog.getGoodsPrice().indexOf(".")!=dialog.getGoodsPrice().lastIndexOf(".")) {
                                        Toast.makeText(SalesRegistrationActivity.this, "请检查单价输入格式！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else {
                                        try {
                                            list.get(position).setMyNumber(Integer.parseInt(dialog.getGoodsNum()));
                                            list.get(position).setProductRetailPrice(Float.parseFloat(dialog.getGoodsPrice()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        holder.setText(R.id.seal_goods_num, dialog.getGoodsNum());
                                        holder.setText(R.id.seal_goods_perice, dialog.getGoodsPrice() + "/件");
                                        commonAdapter.notifyDataSetChanged();
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
        lv.setAdapter(commonAdapter);


        titleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //录入人员信息
        btnPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到人员录入页面，并接受返回值
                Intent intent1 = new Intent(SalesRegistrationActivity.this, PersonViewActivity.class);
                Bundle bundle = new Bundle();
                ProductSaleModel productSaleModel = orderModel;
                // o.setProductBillOrders(null);
                bundle.putSerializable("PersonView", productSaleModel);
                if (productSaleModel.getApplyerHeadImage() != null) {
                    intent1.putExtra("HeaderImage", productSaleModel.getApplyerHeadImage().getHeadImage());
                }
                intent1.putExtra("Employee", employee);
                intent1.putExtras(bundle);
                startActivityForResult(intent1, 6);
            }
        });
        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //跳转到选择商品界面
                Intent intent = new Intent(SalesRegistrationActivity.this, GoodsListActivity.class);
                //传已经存在的
                Bundle bundle = new Bundle();
                bundle.putSerializable("ProductList", (Serializable) list);
                intent.putExtras(bundle);
                startActivityForResult(intent, 7);

            }
        });
        tv_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = true;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    PackageManager pm = SalesRegistrationActivity.this.getPackageManager();
                    boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                            || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                            || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                            || Camera.getNumberOfCameras() > 0;
                    if(hasACamera) {
                        Intent intent = new Intent(SalesRegistrationActivity.this, CaptureAllActivity.class);
                        startActivityForResult(intent, 15);
                    }else{
                        Toast.makeText(SalesRegistrationActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
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
                    PackageManager pm = SalesRegistrationActivity.this.getPackageManager();
                    boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                            || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                            || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                            || Camera.getNumberOfCameras() > 0;
                    if(hasACamera) {
                        Intent intent = new Intent(SalesRegistrationActivity.this, CaptureActivity.class);
                        startActivityForResult(intent, 14);
                    }else{
                        Toast.makeText(SalesRegistrationActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //扫描
        imgScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = false;
            if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                showDialog();
            }else {
                PackageManager pm = SalesRegistrationActivity.this.getPackageManager();
                boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                        || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                        || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                        || Camera.getNumberOfCameras() > 0;
                if(hasACamera) {
                    Intent intent = new Intent(SalesRegistrationActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 14);
                }else{
                    Toast.makeText(SalesRegistrationActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                }
            }
            }
        });

        imgallScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = true;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                PackageManager pm = SalesRegistrationActivity.this.getPackageManager();
                boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                        || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                        || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                        || Camera.getNumberOfCameras() > 0;
                if(hasACamera) {
                    Intent intent = new Intent(SalesRegistrationActivity.this, CaptureAllActivity.class);
                    startActivityForResult(intent, 15);
                }else{
                    Toast.makeText(SalesRegistrationActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                }
                }
            }
        });
    }

    //蓝牙扫码枪扫码
    private void showDialog(){
        saoProgressDialog = new MysaoDialog(SalesRegistrationActivity.this);
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
                            listener.SearchAllGoodsByBarCode(sid,utils.ReadString(COMPANYID));
                        }else {
                            listener.SearchGoodsByBarCode(sid,utils.ReadString(COMPANYID));
                        }

                    }
                    return true;
                }
//让mPasswordEdit获取输入焦点
                return false;
            }
        });
    }


    //验证数据是否录入完成
    private boolean Yan() {
        boolean boo = true;
        for (int i = 0; i<list.size(); i++){
            if ("成人".equals(list.get(i).getUseClassName())){
                boo = false;
            }
        }
        if (boo == false){
            if (orderModel == null) {
                Mymes = "请填写人员信息！";
                Toast.makeText(SalesRegistrationActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
                return false;
            } else if (orderModel.getApplyer() == null || orderModel.getApplyer().equals("")) {
                Mymes = "请填写人员信息！";
                Toast.makeText(SalesRegistrationActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
                return false;
            }
        }
        if (list.size() == 0) {
            Mymes = "请选择商品";
            Toast.makeText(SalesRegistrationActivity.this, Mymes, Toast.LENGTH_SHORT).show();
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
                   lists.get(i).setMyNumber(lists.get(i).getMyNumber()+lists.get(j).getMyNumber());
                    lists.remove(j);
                }
            }
        }

        List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < lists.size(); i++) {
            Map<String, Object> listem = new HashMap<String, Object>();

            listem.put("ProductName", lists.get(i).getProductName());
            listem.put("ProductId",lists.get(i).getProductId());
            listem.put("ProductNumber",lists.get(i).getMyNumber()+"件");
            listems.add(listem);
        }

        SimpleAdapter simplead = new SimpleAdapter(SalesRegistrationActivity.this, listems,
                R.layout.item_certificate, new String[] { "ProductName","ProductId","ProductNumber"},
                new int[] {R.id.textView22,R.id.textView23,R.id.textView24});

        LayoutInflater layoutInflater = LayoutInflater.from(SalesRegistrationActivity.this); // 创建视图容器并设置上下文
        final View view = layoutInflater.inflate(R.layout.dialog_outlist,null); // 获取list_item布局文件的视图
        ListView listView = (ListView) view.findViewById(R.id.lv_dialog);
        listView.setAdapter(simplead);
        new AlertDialog.Builder(SalesRegistrationActivity.this).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //进度条对话框
                mProgressDialog = new ProgressDialog(SalesRegistrationActivity.this);
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
                mProgressDialog.setIndeterminate(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setTitle("请稍后");
                mProgressDialog.setMessage("正在提交数据");
                mProgressDialog.show();


                listener.add(orderModel, list );
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                btnSave.setEnabled(true);
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
            money = money + model.getMyNumber() * model.getProductRetailPrice();
        }
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String p = decimalFormat.format(money);//format 返回的是字符串
        tvMoney.setText(p);
        orderModel.setOrderAmount(Float.parseFloat(p));
        return p;
    }


    //将人员录入页面的数据保存到总模型里
    private void setModelValue(ProductSaleModel model) {
        //公司id
        orderModel.setCompanyId(utils.ReadString("companyID"));
        //操作人
        orderModel.setApplyer(model.getApplyer());
        //民族
        orderModel.setApplyerNation(model.getApplyerNation());
        //住址
        orderModel.setApplyerAddress(model.getApplyerAddress());
        //证件类型
        orderModel.setApplyerCertType("11");
        //身份证号
        orderModel.setApplyerCertNumber(model.getApplyerCertNumber());
        //手机
        orderModel.setApplyerMobilePhone(model.getApplyerMobilePhone());
        //购买用途
        orderModel.setPurpose(model.getPurpose());
        //销售状态
        orderModel.setOrderState(model.getOrderState());
        //登记人编码
        orderModel.setOperatorId(model.getOperatorId());
        tvPersonNAME.setText(model.getApplyer());
        string_applyer = model.getApplyer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //返回的购买人员的信息
        if (requestCode == 6 && resultCode == 6) {
            if (data != null) {
                setModelValue((ProductSaleModel) data.getSerializableExtra("PersonModel"));
                String header = data.getStringExtra("HeaderImage");
                string_OperatorName = data.getStringExtra("OperatorName");
                employee = data.getStringExtra("Employee");
                if (null != header) {
                    HeadImageModel headImageModel = new HeadImageModel();
                    Bitmap bmHeader = Utils.getBitmapByte(header);
                    headImageModel.setHeadImage(header);
                    headImageModel.setHeadImageBase64(utils.bitmapToBase64(bmHeader));
                    orderModel.setApplyerHeadImage(headImageModel);
                }
            }
        }
        //商品列表返回的数据
        if (requestCode == 7 && resultCode == 7) {
           if (data != null) {
                list.removeAll(list);
                List<ViewBoxCodeStockModel> my = (List<ViewBoxCodeStockModel>) data.getSerializableExtra("ListProduct");
                list.addAll(my);
                commonAdapter.notifyDataSetChanged();
                jiSuanMoney();
            }
        }
        //扫描条形码返回的条形码
        if (resultCode == 14) {
            String sid = data.getStringExtra("BarScan");
            if (!sid.equals("")) {

                listener.SearchGoodsByBarCode(sid,utils.ReadString(COMPANYID));
            }
        }
        //扫描条形码返回的条形码
        if (resultCode == 15) {
            String sid = data.getStringExtra("BarScan");
            if (!sid.equals("")) {

                listener.SearchAllGoodsByBarCode(sid,utils.ReadString(COMPANYID));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mProgressDialog = null;
        orderModel = null;
        llDialog = null;
        commonAdapter = null;
        scannlist = null;
        list = null;
        listener = null;
    }

    //添加返回的结果
    @Override
    public void addResult(boolean b, String mes) {
        mProgressDialog.dismiss();
        this.b = b;
        this.Mymes = mes;
        SalesRegistrationActivity.this.runOnUiThread(run);
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
                if (0<viewBoxCodeStockModel.getBusinessCount()){
                    scannlist.add(viewBoxCodeStockModel);
                }else {
                    Mymes = "此箱产品已销售完";
                }
            } else {
                Mymes = "产品已存在列表中！";
            }
            SalesRegistrationActivity.this.runOnUiThread(rn);
        } else {
            SalesRegistrationActivity.this.runOnUiThread(rnn);
        }
    }

    @Override
    public void SearchAllGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel) {
        Mymes = mes;
        if (viewBoxCodeStockModel.getProductId() != null) {
            boolean isYou = true;
            viewBoxCodeStockModel.setMyNumber(viewBoxCodeStockModel.getBusinessCount());
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
                if (0<viewBoxCodeStockModel.getBusinessCount()){
                    scannlist.add(viewBoxCodeStockModel);

                }else {
                    Mymes = "此箱产品已销售完";
                }
            } else {
                Mymes = "产品已存在列表中！";
            }
            SalesRegistrationActivity.this.runOnUiThread(rrn);
        } else {
            SalesRegistrationActivity.this.runOnUiThread(rnn);

        }
    }

    @Override
    public void Inventory(String mes, ActualInventoryModel inventoryModelList) {

    }


    Runnable rnn = new Runnable() {
        @Override
        public void run() {
            btnSave.setEnabled(true);
            if (!Mymes.equals("")) {
                Toast.makeText(SalesRegistrationActivity.this, Mymes, Toast.LENGTH_SHORT).show();
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
            commonAdapter.notifyDataSetChanged();
            jiSuanMoney();
            if (!Mymes.equals("")) {
                Toast.makeText(SalesRegistrationActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
            }
        }
    };
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
                Toast.makeText(SalesRegistrationActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    Intent intent = new Intent(SalesRegistrationActivity.this, CaptureAllActivity.class);
                    startActivityForResult(intent, 15);
                }

            }
        }
    };
    Runnable run = new Runnable() {
        @Override
        public void run() {
            btnSave.setEnabled(true);
            if (b) {

           /*     llDialog.setMiddleMessage("是否打印小票？");
                llDialog.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {//点击确定按
                        llDialog.cancel();
                        mes += "\r\n";
                        mes += "\r\n";
                        mes += utils.ReadString(CompanyName) + "\r\n";
                        Mymes = Mymes.replace("\"", "");
                        mes += "销售单号：" + Mymes + "\r\n";
                        mes += "购买人：" + string_applyer + "  销售人：" + string_OperatorName + "\r\n";
                        mes += "日期：" + Utils.getNOWTime() + "\n";
                        mes += "商品名称          总价   数量  \r\n";
                        mes += "================================\r\n";
                        for (int i = 0; i < list.size(); i++) {
                            mes += list.get(i).getProductName() + "\r\n";
                            mes += list.get(i).getBoxCode() + "        " + list.get(i).getProductRetailPrice()*list.get(i).getMyNumber() + "   " + list.get(i).getMyNumber() + "件\r\n";
                        }
                        mes += "================================" +
                                "\r\n";
                        mes += "总金额：" + tvMoney.getText().toString() + "\r\n";
                        mes += "------------谢谢惠顾！----------" + "\r\n";
                        mes += "请安全、正确、文明燃放烟花爆竹！" + "\r\n";
                        mes += "\r\n";
                        mes += "\r\n";
                        pl = PrinterClassFactory.create(0, SalesRegistrationActivity.this, mhandler, handler);
                        ConnectPrinter();

                        list.removeAll(list);
                        orderModel = new ProductSaleModel();
                        commonAdapter.notifyDataSetChanged();
                        tvPersonNAME.setText("请录入人员信息");
                        tvMoney.setText("0.00");
                        Mymes = "";

                    }
                });
                llDialog.setOnNegativeListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        llDialog.cancel();
                        list.removeAll(list);
                        orderModel = new ProductSaleModel();
                        commonAdapter.notifyDataSetChanged();
                        tvPersonNAME.setText("请录入人员信息");
                        tvMoney.setText("0.00");

                    }
                });//点击取消按钮
                llDialog.show();*/
                Toast.makeText(SalesRegistrationActivity.this, "销售成功", Toast.LENGTH_SHORT).show();
                list.removeAll(list);
                orderModel = new ProductSaleModel();
                commonAdapter.notifyDataSetChanged();
                tvPersonNAME.setText("请录入人员信息");
                tvMoney.setText("0.00");
            } else {
                Toast.makeText(SalesRegistrationActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
            }

        }
    };


    //连接打印机设备
    private void ConnectPrinter() {
        if (utils.ReadString(KEY_BlueToothAddress).equals("")) {
            Toast.makeText(SalesRegistrationActivity.this, "蓝牙打印机未连接", Toast.LENGTH_SHORT).show();
            mes = "";
        } else {
            Toast.makeText(SalesRegistrationActivity.this, "正在连接蓝牙打印机", Toast.LENGTH_SHORT).show();
            if (deviceList != null) {
                deviceList.clear();
            }
            if (!pl.IsOpen()) {
                pl.open(SalesRegistrationActivity.this);
            }
            pl.scan();
            deviceList = pl.getDeviceList();
            for (int i = 0; i < deviceList.size(); i++) {
                String address = utils.ReadString(KEY_BlueToothAddress);
                if (deviceList.get(i).deviceAddress.equals(address)) {
                    pl.connect(address);
                    break;
                }
            }
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
                                Toast.makeText(SalesRegistrationActivity.this, "正在连接", Toast.LENGTH_SHORT).show();
                                break;
                            case PrinterClass.STATE_LISTEN:
                            case PrinterClass.STATE_NONE:
                                break;
                            case PrinterClass.SUCCESS_CONNECT:
                                pl.write(new byte[]{0x1b, 0x2b});// 检测打印机型号
                                Toast.makeText(SalesRegistrationActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                                PrinterMes();
                                break;
                            case PrinterClass.FAILED_CONNECT:
                                Toast.makeText(SalesRegistrationActivity.this, "连接失败", Toast.LENGTH_SHORT).show();
                                mes = "";
                                break;
                            case PrinterClass.LOSE_CONNECT:
                           /*     Toast.makeText(SalesRegistrationActivity.this,"丢失连接",Toast.LENGTH_SHORT).show();*/

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
        /*pl = PrinterClassFactory.create(0, SalesRegistrationActivity.this, mhandler, handler);*/
    }

    //打印内容
    private void PrinterMes() {
        if (pl != null) {
            if (pl.getState() == PrinterClass.STATE_CONNECTED) {
                pl.printText(mes + "\r\n\r\n\r\n");
            } else {
                Toast.makeText(SalesRegistrationActivity.this, "打印机连接失败!", Toast.LENGTH_SHORT).show();
                mes = "";
            }
        } else {
            Toast.makeText(SalesRegistrationActivity.this, "打印机连接失败!", Toast.LENGTH_SHORT).show();
            mes = "";
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                pl.disconnect();
                mes = "";
            }
        }).start();
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
}
