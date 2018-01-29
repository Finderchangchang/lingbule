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
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.adapter.CommonAdapter;
import com.xiaohu.fireworkssystem.adapter.ViewHolder;
import com.xiaohu.fireworkssystem.control.seal.SealAddListener;
import com.xiaohu.fireworkssystem.control.seal.SealAddView;
import com.xiaohu.fireworkssystem.model.table.ActualInventoryDetailModel;
import com.xiaohu.fireworkssystem.model.table.ActualInventoryModel;
import com.xiaohu.fireworkssystem.model.table.InventoryDetailModel;
import com.xiaohu.fireworkssystem.model.table.InventoryModel;
import com.xiaohu.fireworkssystem.model.table.OperatorBoxCodeModel;
import com.xiaohu.fireworkssystem.model.view.ViewBoxCodeStockModel;
import com.xiaohu.fireworkssystem.model.view.ViewSaleRecordModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyInventoryDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.MysaoDialog;
import com.xiaohu.fireworkssystem.view.scan.CaptureActivity;
import com.xiaohu.fireworkssystem.view.scan.CaptureAllActivity;
import com.xiaohu.fireworkssystem.view.scan.MyInventoryResDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_SaoBlueToothAddress;
import static com.xiaohu.fireworkssystem.config.MyKeys.ServerDate;

public class InventoryActivity extends AppCompatActivity implements  SealAddView  {

    private MyTitleView inventory_title;
    //操作人，盘点人
    private Button btn_operator;
    //工具类
    private Utils utils;
    //操作人，盘点原因
    private TextView tv_operator,tv_inventoryreason;
    //盘点单模型
    private InventoryModel inventoryModel ;
    private ActualInventoryModel actualInventoryModel;
    private ImageView img_goods_scan,img_allgoods_scan;
    //访问后台
    private SealAddListener listener;
    //listview的数据源
    private CommonAdapter<ViewBoxCodeStockModel> commonAdapter;
    //给数据源的集合
    private List<ViewBoxCodeStockModel> list;
    private List<ViewBoxCodeStockModel> scannlist;
    //显示盘点商品列表
    private ListView listView_inventory;
    private String Mymes="";
    private ViewBoxCodeStockModel viewBoxCodeStockModel;
    private Button btn_inventory_save;
    private boolean b = false;
    private ActualInventoryModel actualInventoryModelcreat;
    private MysaoDialog saoProgressDialog;
    private boolean allboo = false;
    //进度框
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);

        initView();
        initListener();
    }
    private void initView() {
        inventory_title = (MyTitleView) findViewById(R.id.inventory_title);
        btn_operator = (Button) findViewById(R.id.btn_operator);
        tv_operator = (TextView) findViewById(R.id.tv_operator);
        tv_inventoryreason = (TextView) findViewById(R.id.tv_inventoryreason);
        img_allgoods_scan = (ImageView) findViewById(R.id.img_allgoods_scan);
        img_goods_scan = (ImageView) findViewById(R.id.img_goods_scan);
        btn_inventory_save = (Button) findViewById(R.id.btn_inventory_save);
        utils = new Utils(InventoryActivity.this);
        viewBoxCodeStockModel = new ViewBoxCodeStockModel();
        inventoryModel = new InventoryModel();
        listener = new SealAddListener(this,InventoryActivity.this);
        listView_inventory = (ListView) findViewById(R.id.lv_inventory);
        list = new ArrayList<>();
        scannlist = new ArrayList<>();


    }

    private void initListener() {
        commonAdapter = new CommonAdapter<ViewBoxCodeStockModel>(InventoryActivity.this, list, R.layout.lv_goods_seal_item) {
            @Override
            public void convert(final ViewHolder holder, final ViewBoxCodeStockModel goodsModel, final int position) {
                holder.setText(R.id.seal_item_gname, goodsModel.getProductName());
                holder.setText(R.id.seal_goods_perice, goodsModel.getBoxCode());
                holder.setText(R.id.seal_goods_num, goodsModel.getMyNumber() + "");
                holder.setText(R.id.stock_goods_num, goodsModel.getBusinessCount()+ "");
                holder.setOnClickListener(R.id.goods_item_editer, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final MyInventoryDialog dialog = new MyInventoryDialog(InventoryActivity.this, goodsModel.getProductName(), goodsModel.getBoxCode() + "",goodsModel.getBusinessCount()+"",goodsModel.getBusinessCount()+ "", "库存数量：");
                        dialog.setBtnLeftText("删除");
                        dialog.setBtnRightText("保存");
                        dialog.setDeleteListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //删除
                                list.remove(list.get(position));
                                dialog.dismiss();
                                commonAdapter.notifyDataSetChanged();
                            }
                        });
                        dialog.setSave(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //取消
                                if (dialog.getGoodsNum().equals("")) {
                                    Toast.makeText(InventoryActivity.this, "数量不能为空！", Toast.LENGTH_SHORT).show();
                                    Mymes = "";
                                } else {
                                     if (0 == Integer.parseInt(dialog.getGoodsNum()) ) {
                                        Toast.makeText(InventoryActivity.this, "请输入数量！", Toast.LENGTH_SHORT).show();
                                        Mymes = "";
                                    }else if (Integer.parseInt(dialog.getGoodsNum()) >list.get(position).getProductNumber()){
                                         Toast.makeText(InventoryActivity.this, "数量大于单位数量！", Toast.LENGTH_SHORT).show();
                                         Mymes = "";
                                     }else {
                                        try {
                                            list.get(position).setMyNumber(Integer.parseInt(dialog.getGoodsNum()));
                                        }catch (Exception e){
                                            e.printStackTrace();
                                        }
                                        holder.setText(R.id.seal_goods_num, dialog.getGoodsNum());
                                        commonAdapter.notifyDataSetChanged();
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
        listView_inventory.setAdapter(commonAdapter);

        img_goods_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = false;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                PackageManager pm = InventoryActivity.this.getPackageManager();
                boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                        || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                        || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                        || Camera.getNumberOfCameras() > 0;
                if(hasACamera) {
                    Intent intent = new Intent(InventoryActivity.this, CaptureActivity.class);
                    startActivityForResult(intent, 14);
                }else{
                    Toast.makeText(InventoryActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                }
                }
            }
        });

        img_allgoods_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                allboo = true;
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    PackageManager pm = InventoryActivity.this.getPackageManager();
                    boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)
                            || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)
                            || Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD
                            || Camera.getNumberOfCameras() > 0;
                    if (hasACamera) {
                        Intent intent = new Intent(InventoryActivity.this, CaptureAllActivity.class);
                        startActivityForResult(intent, 15);
                    } else {
                        Toast.makeText(InventoryActivity.this, "请检查相机功能是否正常!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        inventory_title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_operator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InventoryActivity.this, EmployeeActivity.class);
                intent.putExtra("EmployeesIDs", "");
                intent.putExtra("Operate", true);
                intent.putExtra("companyid",utils.ReadString("companyID"));
                startActivityForResult(intent, 31);
            }
        });

        tv_inventoryreason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyInventoryResDialog dialog = new MyInventoryResDialog(InventoryActivity.this);
                dialog.show();
                dialog.setExit(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setSave(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv_inventoryreason.setText(dialog.getPSW());
                        dialog.dismiss();
                    }
                });
            }
        });

        btn_inventory_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  List<InventoryDetailModel> inventoryDetailModelList = new ArrayList<InventoryDetailModel>();
                InventoryDetailModel inventoryDetailModel = new InventoryDetailModel();*/

                List<ActualInventoryDetailModel> actualInventoryModelList = new ArrayList<ActualInventoryDetailModel>();


                if (null==inventoryModel.getOperatorId()  || "".equals(inventoryModel.getOperatorId())){
                    Toast.makeText(InventoryActivity.this,"请选择操作人",Toast.LENGTH_SHORT).show();
                }else if (list.size()>0){
                    btn_inventory_save.setEnabled(false);
                    for (int i= 0; i <list.size();i++){
                        ActualInventoryDetailModel actualInventoryDetailModel = new ActualInventoryDetailModel();
                        OperatorBoxCodeModel[] operatorBoxCodeModel = new OperatorBoxCodeModel[1];
                        OperatorBoxCodeModel o = new OperatorBoxCodeModel();
                        actualInventoryDetailModel.setStockId(list.get(i).getStockId());
                        actualInventoryDetailModel.setActualProductNumber(list.get(i).getMyNumber());
                        actualInventoryDetailModel.setStockProductNumber(list.get(i).getBusinessCount());
                        actualInventoryDetailModel.setProductId(list.get(i).getProductId());
                        o.setAmount(list.get(i).getMyNumber());
                        o.setBoxCode(list.get(i).getBoxCode());
                        operatorBoxCodeModel[0] = o;
                        actualInventoryDetailModel.setInventoryBoxCode(operatorBoxCodeModel);
                        actualInventoryModelList.add(actualInventoryDetailModel);
                        actualInventoryDetailModel = null;
                        operatorBoxCodeModel = null;
                        o = null;
                    }
                    for (int i = 0 ; i< actualInventoryModelList.size(); i++){

                        for (int j = actualInventoryModelList.size()-1; j>i ;j--){
                            if (actualInventoryModelList.get(i).getProductId().equals(actualInventoryModelList.get(j).getProductId())){
                                actualInventoryModelList.get(i).setActualProductNumber(actualInventoryModelList.get(i).getActualProductNumber()+actualInventoryModelList.get(j).getActualProductNumber());
                               actualInventoryModelList.get(i).setStockProductNumber(actualInventoryModelList.get(i).getStockProductNumber()+actualInventoryModelList.get(j).getStockProductNumber());
                                OperatorBoxCodeModel[] a = actualInventoryModelList.get(i).getInventoryBoxCode();
                                OperatorBoxCodeModel[] b = actualInventoryModelList.get(j).getInventoryBoxCode();


                                OperatorBoxCodeModel[] c = new OperatorBoxCodeModel[a.length+b.length];
                                System.arraycopy(a, 0, c, 0, a.length);
                                System.arraycopy(b, 0, c, a.length, b.length);

                                actualInventoryModelList.get(i).setInventoryBoxCode(c);
                                actualInventoryModelList.remove(j);
                            }
                        }
                    }
                     actualInventoryModelcreat = new ActualInventoryModel();
                    int size = actualInventoryModelList.size();
                    ActualInventoryDetailModel[] arr = (ActualInventoryDetailModel[])actualInventoryModelList.toArray(new ActualInventoryDetailModel[size]);//使用了第二种接口，返回值和参数均为结果
                    actualInventoryModelcreat.setInventoryDetail(arr);
                    actualInventoryModelcreat.setCompanyId(utils.ReadString(COMPANYID));
                    actualInventoryModelcreat.setCreateTime(utils.ReadString(ServerDate));
                    actualInventoryModelcreat.setInventoryState("01");
                    actualInventoryModelcreat.setOperatorId(inventoryModel.getOperatorId());
                    actualInventoryModelcreat.setInventoryReason(tv_inventoryreason.getText().toString().trim());


                    //进度条对话框
                    mProgressDialog = new ProgressDialog(InventoryActivity.this);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.setTitle("请稍后");
                    mProgressDialog.setMessage("正在提交数据");
                    mProgressDialog.show();
                    listener.creatinventory(actualInventoryModelcreat);
                   /* //箱码模型转化成盘点单详情
                    for (int i= 0; i <list.size();i++){
                        OperatorBoxCodeModel[] operatorBoxCodeModel = new OperatorBoxCodeModel[1];
                        OperatorBoxCodeModel o = new OperatorBoxCodeModel();
                        inventoryDetailModel.setStockId(list.get(i).getStockId());
                        inventoryDetailModel.setProductNumber(list.get(i).getMyNumber());
                        inventoryDetailModel.setProductId(list.get(i).getProductId());
                        o.setAmount(list.get(i).getMyNumber());
                        o.setBoxCode(list.get(i).getBoxCode());
                        operatorBoxCodeModel[0] = o;
                        inventoryDetailModel.setBoxs(operatorBoxCodeModel);
                        inventoryDetailModelList.add(inventoryDetailModel);
                    }
                    for (int i = 0 ; i<inventoryDetailModelList.size();i++){
                        inventoryDetailModelList.get(i).setProductId(null);
                    }
                    for (int i = 0 ; i< inventoryDetailModelList.size(); i++){

                        for (int j = inventoryDetailModelList.size()-1; j>i ;j--){
                            if (inventoryDetailModelList.get(i).getProductId().equals(inventoryDetailModelList.get(j).getProductId())){
                                inventoryDetailModelList.get(i).setProductNumber(inventoryDetailModelList.get(i).getProductNumber()+inventoryDetailModelList.get(j).getProductNumber());
                              OperatorBoxCodeModel[] a = inventoryDetailModelList.get(i).getBoxs();
                                OperatorBoxCodeModel[] b = inventoryDetailModelList.get(j).getBoxs();


                                OperatorBoxCodeModel[] c = new OperatorBoxCodeModel[a.length+b.length];
                                System.arraycopy(a, 0, c, 0, a.length);
                                System.arraycopy(b, 0, c, a.length, b.length);

                                inventoryDetailModelList.get(i).setBoxs(c);
                                inventoryDetailModelList.remove(j);
                            }
                        }
                    }


                    int size = inventoryDetailModelList.size();
                    InventoryDetailModel[] arr = (InventoryDetailModel[])inventoryDetailModelList.toArray(new InventoryDetailModel[size]);//使用了第二种接口，返回值和参数均为结果
                    inventoryModel.setInventoryDetailModel(arr);
                    inventoryModel.setCompanyId(utils.ReadString(COMPANYID));
                    inventoryModel.setCreateUser(utils.ReadString("COMPANYROLE"));
                    inventoryModel.setCreateTime(utils.ReadString(ServerDate));
                    inventoryModel.setInventoryState("01");
                    inventoryModel.setInventoryReason(tv_inventoryreason.getText().toString().trim());
                    listener.inventory(inventoryModel);*/
                }else {
                    Toast.makeText(InventoryActivity.this,"请添加盘点产品",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    //蓝牙扫码枪扫码
    private void showDialog(){
        saoProgressDialog = new MysaoDialog(InventoryActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 31 && resultCode == 3) {
            inventoryModel.setOperatorId(data.getStringExtra("IDS") == null ? "" : data.getStringExtra("IDS"));
            tv_operator.setText(data.getStringExtra("NAMES") == null ? "" : data.getStringExtra("NAMES"));
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
    public void addResult(boolean b, String mes) {
        mProgressDialog.dismiss();
        Mymes = mes;
        this.b = b;
        if (b){
            InventoryActivity.this.runOnUiThread(runn);
        }else {
            InventoryActivity.this.runOnUiThread(rnn);
        }
    }

    @Override
    public void SearchBill(String mes, List<ViewSaleRecordModel> list) {

    }

    @Override
    public void SearchGoods(String mes, ViewBoxCodeStockModel viewBoxCodeStockModel) {
        Mymes = mes;
        if (viewBoxCodeStockModel.getProductId() != null) {
            boolean isYou = true;
            viewBoxCodeStockModel.setMyNumber(viewBoxCodeStockModel.getBusinessCount());
            System.out.println("viewBoxCodeStockModel.getMyNumber()"+viewBoxCodeStockModel.getMyNumber());
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
            InventoryActivity.this.runOnUiThread(rn);
        } else {
            InventoryActivity.this.runOnUiThread(rnn);
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

                    scannlist.add(viewBoxCodeStockModel);

            } else {
                Mymes = "产品已存在列表中！";
            }
            InventoryActivity.this.runOnUiThread(rrn);
        } else {
            InventoryActivity.this.runOnUiThread(rnn);

        }
    }


    @Override
    public void Inventory(String mes, ActualInventoryModel actualInventoryModels) {
        Mymes = mes;
        if (actualInventoryModels == null){
            InventoryActivity.this.runOnUiThread(rnn);
        }else {
            actualInventoryModel = new ActualInventoryModel();
            actualInventoryModel = actualInventoryModels;
            Gson gson = new Gson();
            System.out.println("sss+"+gson.toJson(actualInventoryModels));
            InventoryActivity.this.runOnUiThread(run);

        }

    }

    Runnable rrn = new Runnable() {
        @Override
        public void run() {
            if(scannlist!=null) {
                list.addAll(scannlist);
                scannlist=null;
            }
            commonAdapter.notifyDataSetChanged();

            if (!Mymes.equals("")) {
                Toast.makeText(InventoryActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
                if ("blue".equals(utils.ReadString(KEY_SaoBlueToothAddress))){
                    showDialog();
                }else {
                    Intent intent = new Intent(InventoryActivity.this, CaptureAllActivity.class);
                    startActivityForResult(intent, 15);
                }
            }
        }
    };
    Runnable runn = new Runnable() {
        @Override
        public void run() {
            btn_inventory_save.setEnabled(true);
            list.clear();
            commonAdapter.notifyDataSetChanged();
            actualInventoryModel = null;
            if (!Mymes.equals("")) {
                Toast.makeText(InventoryActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
            }
        }
    };
    Runnable rnn = new Runnable() {
        @Override
        public void run() {
            btn_inventory_save.setEnabled(true);
            if (!Mymes.equals("")) {
                Toast.makeText(InventoryActivity.this, Mymes, Toast.LENGTH_SHORT).show();
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
            if (!Mymes.equals("")) {
                Toast.makeText(InventoryActivity.this, Mymes, Toast.LENGTH_SHORT).show();
                Mymes = "";
            }
        }
    };
    Runnable run = new Runnable() {
        @Override
        public void run() {
            btn_inventory_save.setEnabled(true);

            final List<ActualInventoryDetailModel> acinventoryDetailModel= new ArrayList<>();
            for (int i = 0; i < actualInventoryModel.getInventoryDetail().length; i++) {
                acinventoryDetailModel.add(actualInventoryModel.getInventoryDetail()[i]);
            }
            List<Map<String, Object>> listems = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < acinventoryDetailModel.size(); i++) {
                Map<String, Object> listem = new HashMap<String, Object>();
                listem.put("ProductName", acinventoryDetailModel.get(i).getProductName());
                listem.put("Productkucun","原库存："+actualInventoryModelcreat.getInventoryDetail()[i].getStockProductNumber()+"件");
                listem.put("Productshiji","新库存："+acinventoryDetailModel.get(i).getActualProductNumber()+"件");
                listems.add(listem);
            }

            SimpleAdapter simplead = new SimpleAdapter(InventoryActivity.this, listems,
                    R.layout.item_certificate, new String[] { "ProductName","Productkucun","Productshiji"},
                    new int[] {R.id.textView22,R.id.textView23,R.id.textView24});

            LayoutInflater layoutInflater = LayoutInflater.from(InventoryActivity.this); // 创建视图容器并设置上下文
            final View view = layoutInflater.inflate(R.layout.dialog_outlist,null); // 获取list_item布局文件的视图
            ListView listView = (ListView) view.findViewById(R.id.lv_dialog);
            listView.setAdapter(simplead);
            new AlertDialog.Builder(InventoryActivity.this).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    InventoryModel inventoryModel2 = new InventoryModel();
                    inventoryModel2.setCompanyId(utils.ReadString(COMPANYID));
                    inventoryModel2.setCreateUser(utils.ReadString("COMPANYROLE"));
                    inventoryModel2.setCreateTime(utils.ReadString(ServerDate));
                    inventoryModel2.setInventoryState("01");
                    inventoryModel2.setOperatorId(actualInventoryModel.getOperatorId());

                    inventoryModel2.setInventoryReason(tv_inventoryreason.getText().toString().trim());

                    InventoryDetailModel[] l = new InventoryDetailModel[actualInventoryModel.getInventoryDetail().length];

                    for (int i = 0; i < actualInventoryModel.getInventoryDetail().length ; i++){
                        InventoryDetailModel in = new InventoryDetailModel();
                        in.setStockId(acinventoryDetailModel.get(i).getStockId());
                        in.setProductNumber(acinventoryDetailModel.get(i).getActualProductNumber());
                        in.setLossState(acinventoryDetailModel.get(i).getProductState());
                        in.setBoxs(acinventoryDetailModel.get(i).getLossBoxCode());
                        l[i] = in;
                        in = null;
                    }
                    inventoryModel2.setInventoryDetailModel(l);
                    listener.inventory(inventoryModel2);

                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    mProgressDialog.dismiss();
                }
            }).show();
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewBoxCodeStockModel = null;
        inventoryModel = null;
        listener = null;
        list = null;
        scannlist = null;
        actualInventoryModelcreat = null;
        mProgressDialog = null;
    }
}
