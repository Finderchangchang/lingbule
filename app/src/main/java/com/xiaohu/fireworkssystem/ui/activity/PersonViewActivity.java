package com.xiaohu.fireworkssystem.ui.activity;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.config.MyKeys;
import com.xiaohu.fireworkssystem.config.Sha1;
import com.xiaohu.fireworkssystem.control.seal.PersonListener;
import com.xiaohu.fireworkssystem.control.seal.PersonView;
import com.xiaohu.fireworkssystem.control.setting.SettingListener;
import com.xiaohu.fireworkssystem.control.setting.SettingView;
import com.xiaohu.fireworkssystem.http.OkHttpsUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.BlueToothModel;
import com.xiaohu.fireworkssystem.model.OCRModel;
import com.xiaohu.fireworkssystem.model.PrisonerModel;
import com.xiaohu.fireworkssystem.model.TicketModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.person.PersonModel;
import com.xiaohu.fireworkssystem.model.table.ProductSaleModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;
import com.zkteco.android.IDReader.IDPhotoHelper;
import com.zkteco.android.IDReader.WLTService;
import com.zkteco.id300.IDCardReader;
import com.zkteco.id300.meta.IDCardInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.xiaohu.fireworkssystem.R.id.person_tel;
import static com.xiaohu.fireworkssystem.R.id.person_yongtu;
import static com.xiaohu.fireworkssystem.config.MyKeys.COMPANYID;
import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_BlueToothAddress;

/**
 * 购买人信息录入
 * Created by Administrator on 2016/8/10.
 */
public class PersonViewActivity extends TakePhotoActivity implements PersonView, SettingView {

    //手机号，地址，证件号码
    private EditText etPhone, etAddress, person_yanzheng;
    private StringBuffer path = new StringBuffer();
    //头像
    private ImageView imgPerson;
    private Bitmap bmHeader = null;
    //订单模型
    private ProductSaleModel orderModel;
    //标题
    private MyTitleView title;
    //字典操作类
    private CodeDao dao;
    //法人选项视图
    private LinearLayout layout_boss;
    //用途，姓名，名字，民族，证件号码，，操作人
    private TextView tvyongtu, etAName, etNation, etCardCode, tvOperate, textView_line;
    //选择弹出框
    private SpinnerDialog Contentdialog;//选择
    private PersonListener listener;
    private Button person_read, button_code, person_ocr;
    private PrisonerModel prisonerModel;
    private LinearLayout linearLayout_code;
    //提示信息
    private String mes = "";
    static String filepath = "";
    private RadioButton faren_yes, faren_no;
    private ProgressDialog progressDialog;
    private List<BlueToothModel> listBlue;
    private BluetoothAdapter mBluetoothAdapter;
    IDCardReader idCardReader = null;
    PersonViewActivity.WorkThread workThread;
    int index = 0;
    //监听器
    private SettingListener setlistener;
    private boolean terminalIsTrue = false;
    private String strGUID = "", strType = "";
    private TakePhoto takePhoto;
    private Utils utils;
    private Bitmap bm;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what > 0 && msg.what < 61) {
                button_code.setText(msg.what + "秒");
                button_code.setEnabled(false);
            } else if (msg.what == 0) {
                //在handler里可以更改UI组件
                button_code.setText("获取验证码");
                timer.cancel();
                timer = null;
                button_code.setEnabled(true);
            }
            if (msg.what == 200) {
                Toast.makeText(PersonViewActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();
            }
       /*     switch (msg.what) {
                case 1: {
                    Toast.makeText(PersonViewActivity.this, (String) msg.obj, Toast.LENGTH_SHORT).show();


            }*/
        }
    };
    private Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_view);

        initView();
        initEvent();
    }

    public void initView() {
        orderModel = (ProductSaleModel) getIntent().getSerializableExtra("PersonView");
        setlistener = new SettingListener(PersonViewActivity.this, this);
        listener = new PersonListener(PersonViewActivity.this, this);
        dao = new CodeDao(this);
        textView_line = (TextView) findViewById(R.id.textView_line);
        linearLayout_code = (LinearLayout) findViewById(R.id.linearLayout_code);

        button_code = (Button) findViewById(R.id.button_code);
        person_yanzheng = (EditText) findViewById(R.id.person_yanzheng);
        title = (MyTitleView) findViewById(R.id.person_view_title);
        imgPerson = (ImageView) findViewById(R.id.img_person);
        etAName = (TextView) findViewById(R.id.person_name);
        etCardCode = (TextView) findViewById(R.id.person_cardid);
        etNation = (TextView) findViewById(R.id.person_nation);
        etPhone = (EditText) findViewById(person_tel);
        etAddress = (EditText) findViewById(R.id.person_address);
        tvyongtu = (TextView) findViewById(person_yongtu);
        tvOperate = (TextView) findViewById(R.id.person_users_operate);
        faren_yes = (RadioButton) findViewById(R.id.faren_yes);
        faren_no = (RadioButton) findViewById(R.id.faren_no);
        layout_boss = (LinearLayout) findViewById(R.id.layout_boss);
        person_read = (Button) findViewById(R.id.person_read);
        person_ocr = (Button) findViewById(R.id.person_ocr);
        takePhoto = getTakePhoto();
        utils = new Utils(this);
        orderModel.setMember(false);





        /*法人销售
        if ("03".equals(utils.ReadString("CompanyType"))){
            layout_boss.setVisibility(View.GONE);
        }*/
    }


    public void initEvent() {
        tvyongtu.setText(utils.ReadString("Purpose"));
        orderModel.setPurpose(utils.ReadString("Purpose_code"));
        tvOperate.setText(utils.ReadString("Applyer"));
        orderModel.setOperatorId(utils.ReadString("Applyer_code"));
        if (orderModel != null) {

            String bmStr = getIntent().getStringExtra("HeaderImage");
            etAName.setText(orderModel.getApplyer());
            etCardCode.setText(orderModel.getApplyerCertNumber());
            etPhone.setText(orderModel.getApplyerMobilePhone());
            etAddress.setText(orderModel.getApplyerAddress());

            if (bmStr != null && (!bmStr.equals(""))) {
                bmHeader = Utils.getBitmapByte(bmStr);
                imgPerson.setImageBitmap(bmHeader);
            }
            if (orderModel.getApplyerNation() != null) {
                etNation.setText(GetCodeNmae("Code_Nation", orderModel.getApplyerNation()));
            }
            /*if (orderModel.getPurpose() != null) {
                tvyongtu.setText(GetCodeNmae("Code_Purpose", orderModel.getPurpose()));
            }*/
        } else {
            orderModel = new ProductSaleModel();

        }


        //操作人员
        tvOperate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonViewActivity.this, EmployeeActivity.class);
                intent.putExtra("EmployeesIDs", "");
                intent.putExtra("Operate", true);
                intent.putExtra("companyid", utils.ReadString("companyID"));
                startActivityForResult(intent, 31);
            }
        });
        tvyongtu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CodeModel> list = dao.QueryByName("CodeName", "Code_Purpose");
                getCodeByData(list);
                //选择用途
            }
        });

        title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onActivityResult(6, 6, null);
                finish();
            }
        });

        faren_no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etAName.setText(utils.ReadString("COMPANYBOSS"));
                etAddress.setText(utils.ReadString("COMPANYBOSSADDRESS"));
                etCardCode.setText(utils.ReadString("COMPANYCERTNUMBER"));
                etNation.setText(utils.ReadString("COMPANYBOSSNATIONNAME"));
                orderModel.setApplyerNation(utils.ReadString("COMPANYBOSSNATION"));
                etPhone.setText(utils.ReadString("COMPANYBOSSPHONE"));
                orderModel.setOrderState("04");
                textView_line.setVisibility(View.GONE);
                linearLayout_code.setVisibility(View.GONE);
                person_read.setEnabled(false);
            }
        });
        faren_yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                etAName.setText("");
                etAddress.setText("");
                etCardCode.setText("");
                etNation.setHint("汉族");
                etPhone.setText("");
                imgPerson.setImageResource(R.mipmap.person_header);
                orderModel.setApplyerNation("01");
                orderModel.setOrderState("02");
                person_read.setEnabled(true);


            }
        });

        button_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button_code.setEnabled(false);
                // 定义计时器
                timer = new Timer();
                strGUID = java.util.UUID.randomUUID().toString();
                strGUID = strGUID.replaceAll("-", "");
                StringBuffer sb = new StringBuffer();
                if (strGUID != null) {
                    for (int i = 0; i < strGUID.length(); i++) {
                        char c = strGUID.charAt(i);
                        if (Character.isUpperCase(c)) {
                            sb.append(Character.toLowerCase(c));
                        } else if (Character.isLowerCase(c)) {
                            sb.append(Character.toUpperCase(c));
                        }
                    }
                }
                strGUID = sb.toString();
                if (utils.isMobileNO(etPhone.getText().toString().trim())) {
                    if (!"".equals(prisonerModel.getName())) {
                        button_code.setEnabled(false);
                        listener.SendVerificationCode(etPhone.getText().toString().trim(), strGUID, prisonerModel.getName());
                        // 定义计划任务，根据参数的不同可以完成以下种类的工作：在固定时间执行某任务，在固定时间开始重复执行某任务，重复时间间隔可控，在延迟多久后执行某任务，在延迟多久后重复执行某任务，重复时间间隔可控
                        timer.schedule(new TimerTask() {
                            int i = 60;

                            // TimerTask 是个抽象类,实现的是Runable类
                            @Override
                            public void run() {


                                //定义一个消息传过去
                                Message msg = new Message();
                                msg.what = i--;
                                mHandler.sendMessage(msg);

                            }

                        }, 1000, 1000);
                    } else {
                        Toast.makeText(PersonViewActivity.this, "请读取身份证信息", Toast.LENGTH_SHORT).show();
                        button_code.setEnabled(true);
                    }
                } else {
                    Toast.makeText(PersonViewActivity.this, "请输入正确手机号码", Toast.LENGTH_SHORT).show();
                    button_code.setEnabled(true);
                }
            }
        });


    }

    //根据字典编码获取字典名称
    private String GetCodeNmae(String cname, String code) {
        CodeDao dao = new CodeDao(this);
        List<CodeModel> list = dao.QueryByID(cname, code);
        if (list.size() > 0) {
            return list.get(0).getName();
        }
        return "";
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.person_ocr:
                String is = utils.ReadString("isopen");
                if (utils.ReadString("isopen").equals("isopen") && android.os.Build.VERSION.SDK_INT <= 23) {
                    File file = new File(Environment.getExternalStorageDirectory(), "/temp/" + System.currentTimeMillis() + ".jpg");
                    if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                    Uri imageUri = Uri.fromFile(file);
                    configCompress(takePhoto);
                    configTakePhotoOption(takePhoto);
                    takePhoto.onPickFromCapture(imageUri);
                } else {
                    startActivityForResult(new Intent(this, CameraPersonActivity.class), 1);
                }
                break;
            case R.id.person_read:
                strType = "";
                linearLayout_code.setVisibility(View.GONE);
                textView_line.setVisibility(View.GONE);
                cz();
                orderModel.setOrderState("02");
                //读取身份证
                progressDialog = ProgressDialog.show(PersonViewActivity.this, "", "正在读取身份证...", true, false);
                progressDialog.show();
                ConnectThread connectThread = new ConnectThread();
                connectThread.start();

                break;
            case R.id.btn_person_view_save:
                if (linearLayout_code.getVisibility() == View.VISIBLE) {
                    listener.CheckVerificationCode(strGUID, person_yanzheng.getText().toString().trim());
                } else {
                    //保存
                    if (Yan()) {
                        getData();
                        utils.WriteString("Purpose_code",orderModel.getPurpose());
                        utils.WriteString("Purpose",tvyongtu.getText().toString().trim());
                        utils.WriteString("Applyer_code",orderModel.getOperatorId());
                        utils.WriteString("Applyer",tvOperate.getText().toString().trim());
                        //返回主界面
                        Intent intent = new Intent();
                        Bundle bundle = new Bundle();

                        bundle.putSerializable("PersonModel", orderModel);
                        if (bmHeader != null) {
                            intent.putExtra("HeaderImage", Utils.encodeBitmap(bmHeader));
                        } else {
                            intent.putExtra("HeaderImage", "");
                        }
                        intent.putExtras(bundle);
                        intent.putExtra("OperatorName", tvOperate.getText());
                        setResult(6, intent);
                        finish();
                    } else {
                        //ToastShort("请填写购买人信息和操作人信息！");
                    }
                }
                break;
        }
    }


    private void cz() {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.person_header);
//        iv_photo.setImageResource(R.drawable.nophoto);
        imgPerson.setImageBitmap(bmp);
        etAName.setText("");
        etNation.setText("");
        etAddress.setText("");
        etCardCode.setText("");
    }


    private void getCodeByData(List<CodeModel> list) {
        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            Contentdialog = new SpinnerDialog(this);
            Contentdialog.setListView(listBlue);
            Contentdialog.show();
            Contentdialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    orderModel.setPurpose(val.getDeviceAddress());
                    tvyongtu.setText(val.getDeviceName());
                }
            });
        }
    }

    private boolean Yan() {
        String Mymes = "";
        if (etAName.getText().toString().trim().equals("")) {
            Mymes = "请填写购买人信息   ！";
            Toast.makeText(PersonViewActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            return false;
        } else if ((orderModel.getPurpose() == null || orderModel.getPurpose().equals(""))) {
            Mymes = "请选择购买用途！";
            Toast.makeText(PersonViewActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            return false;
        } else if (orderModel.getOperatorId() == null || orderModel.getOperatorId().equals("")) {
            Mymes = "请选择操作人员!";
            Toast.makeText(PersonViewActivity.this, Mymes, Toast.LENGTH_SHORT).show();
            Mymes = "";
            return false;
        }
        return true;
    }

    private void getData() {
        if (orderModel == null) {
            orderModel = new ProductSaleModel();
        }
        orderModel.setApplyer(etAName.getText().toString());
        orderModel.setApplyerCertNumber(etCardCode.getText().toString());
        orderModel.setApplyerAddress(etAddress.getText().toString());
        orderModel.setApplyerMobilePhone(etPhone.getText().toString());

    }

    OCRModel model;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (requestCode == 31 && resultCode == 3) {
            orderModel.setOperatorId(data.getStringExtra("IDS") == null ? "" : data.getStringExtra("IDS"));
            tvOperate.setText(data.getStringExtra("NAMES") == null ? "" : data.getStringExtra("NAMES"));
        }
        if (resultCode == 66) {
            if (data.getStringExtra("data") != null) {

                String url = data.getStringExtra("data");
                model = null;//先清空
               /* Bitmap bmp = compressImage(uu.rotaingImageView(90, compressImage(uu.getimage(100, url))));
                getModule(CarManageModule::class.java, this).ocr_sfz(bmp)*/
                //身份证图片
                load_ocr(utils.getimage(100, url));
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    void load_ocr(Bitmap bmp) {
        progressDialog = ProgressDialog.show(PersonViewActivity.this, "", "正在读取身份证...", true, false);
        progressDialog.show();
        bmp = utils.compressImage(bmp, 100);
        bmp = utils.rotaingImageView(90, bmp);
        //时间戳
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        // String strtimes = formatter.format(curDate);

        TicketModel ticketModel = new TicketModel();
        ticketModel.setImage(utils.encodeBitmap(bmp));
               /* ticketModel.setAccountId("lwj123");
                ticketModel.setAccountSecret("6578E24A9F797B02748BB2F57260B5E5DCC7FCB8");
                ticketModel.setTimeStamp(strtimes);
                ticketModel.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFbnRlcnByaXNlSWQiOiJDMDIxMzA2MDIwMDA1IiwiVXNlcklkIjoibHdqMTIzIiwiRXhwVGltZSI6IjIwMTgwMTIwMDkzMDUwIn0.hqjmxdI6zTJ_w7J3KJmTyj_KnqQQh2nawMc8y2EdkCY");
                */
        Gson gson = new Gson();
        String str_json = gson.toJson(ticketModel, TicketModel.class);
        //String str = "'" + str_json + "'";
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        String TimeStamp = df.format(new Date());// new Date()为
        String AccountSecret = Sha1.encode(Utils.md5("123456").toUpperCase() + "_" + TimeStamp).toUpperCase();
        OkHttpsUtils okHttpUtils = new OkHttpsUtils(utils.ReadString(MyKeys.KEY_TOKEN));
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJFbnRlcnByaXNlSWQiOiJDMDIxMzA2MDIwMDA1IiwiVXNlcklkIjoibHdqMTIzIiwiRXhwVGltZSI6IjIwMTgwMTIyMTUyMjUzIn0.NohQbQA1siyisWbNQjbFl5zL8t85Roe4TZ2DKO9o34A";
        //System.out.println("证照识别+" + str);
        okHttpUtils.requestGetByAsyn("jx.hbgdfw.com/Api/", "Other/CardRecognition?Token=" + token + "&AccountId=lwj123&AccountSecret=" + AccountSecret + "&TimeStamp=" + TimeStamp, str_json, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                System.out.println("result:" + result);
                model = gson.fromJson(result, OCRModel.class);
                if (model.isSuccess()) {
                    runOnUiThread(run_ocr);
                } else {
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                progressDialog.dismiss();
            }
        });
    }

    private void setPersonNation(PersonModel model) {
        if (null == orderModel) {
            orderModel = new ProductSaleModel();
        }
        List<CodeModel> list = dao.QueryByKName("Code_Nation", model.getPersonNation());
        System.out.println("------------" + model.getId());

        etAName.setText(model.getPersonName());
        etCardCode.setText(model.getPersonCardId());
        etAddress.setText(model.getPersonAddress());
        prisonerModel = new PrisonerModel();

        if (list.size() > 0) {
            etNation.setText(list.get(0).getName());
            //orderModel.setApplyerNation(list.get(0).getCode());
        }

        orderModel.setApplyerNation(model.getPersonNation());
        if (!"".equals(model.getPersonCardId())) {
            prisonerModel.setNation(model.getPersonNation());
            prisonerModel.setIdentityNumber(model.getPersonCardId() + "");
            prisonerModel.setName(model.getPersonName());
            prisonerModel.setIdentityType("11");
            prisonerModel.setCompanyId(utils.ReadString(COMPANYID));
           /* if (model.getPersonSex().equals("男")){
                personModel.setSex("1");
            }else {
                personModel.setSex("2");
            }*/

            listener.CheckPerson(prisonerModel);

        }


    }


    @Override
    public void CheckResult(boolean isTrue, String mes, String type) {
        if (mes != null) {
            if (!mes.equals(""))
                this.mes = mes;
            runOnUiThread(run);
        }
        if (type != null) {
            if (!type.equals(""))
                this.strType = type;
            runOnUiThread(run);
        }
    }

    //发送验证码
    @Override
    public void SendVerificationCode(boolean boo, String mes) {

    }

    //校验验证码
    @Override
    public void CheckVerificationCode(boolean boo, String mes) {
        if (boo) {
            //保存
            if (Yan()) {
                getData();
                //返回主界面
                Intent intent = new Intent();
                Bundle bundle = new Bundle();

                bundle.putSerializable("PersonModel", orderModel);
                if (bmHeader != null) {
                    intent.putExtra("HeaderImage", Utils.encodeBitmap(bmHeader));
                } else {
                    intent.putExtra("HeaderImage", "");
                }
                intent.putExtras(bundle);
                intent.putExtra("OperatorName", tvOperate.getText().toString().toString());
                setResult(6, intent);
                finish();
            } else {
                //ToastShort("请填写购买人信息和操作人信息！");
            }
        } else {
            this.mes = mes;
            runOnUiThread(runcode);
        }
    }

    Runnable run_ocr = new Runnable() {
        @Override
        public void run() {
            if (model != null) {
                final OCRModel.OCR cardUserModel = model.getData();
                AlertDialog.Builder builder = new AlertDialog.Builder(PersonViewActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialog = inflater.inflate(R.layout.dialog_ocr, (ViewGroup) findViewById(R.id.dialog_ocr));
                final EditText name_et = (EditText) dialog.findViewById(R.id.name_et);
                final EditText id_card_et = (EditText) dialog.findViewById(R.id.id_card_et);
                final EditText address_et = (EditText) dialog.findViewById(R.id.address_et);
                name_et.setText(cardUserModel.getPersonName());
                id_card_et.setText(cardUserModel.getIdentyNumber());
                address_et.setText(cardUserModel.getPersonAddress());
                builder.setTitle("信息确认");
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PersonModel personModel = new PersonModel();
                        personModel.setPersonAddress(address_et.getText().toString().trim());
                        //personModel.setPersonBirthday(idCardInfo.getBirth().replace("年", "-").replace("月", "-").replace("日", ""));
                        //personModel.setPersonNation(queryCode("Code_Nation", idCardInfo.getNation()));
                        personModel.setPersonName(name_et.getText().toString().trim());
                        personModel.setPersonNation(cardUserModel.getPersonNation() + "族");
                        //personModel.setPersonSex(idCardInfo.getSex());
                        personModel.setPersonCardId(id_card_et.getText().toString().trim());
                        bmHeader = base64ToBitmap(cardUserModel.getPersonFaceImage());
                        imgPerson.setImageBitmap(bmHeader);
                        setPersonNation(personModel);
                        //model = null;
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                progressDialog.dismiss();
                builder.setView(dialog);
                builder.show();
            } else {
                progressDialog.dismiss();
            }
        }
    };

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (!mes.equals("")) {
                imgPerson.setImageResource(R.mipmap.person_header);
                etAName.setText("");
                etAddress.setText("");
                etNation.setText("");
                etCardCode.setText("");
                bmHeader = null;
                Toast.makeText(PersonViewActivity.this, mes, Toast.LENGTH_SHORT).show();
            }
            if ("RefusedNation".equals(strType)) {
                imgPerson.setImageResource(R.mipmap.person_header);
                etAName.setText("");
                etAddress.setText("");
                etNation.setText("");
                etCardCode.setText("");
                bmHeader = null;
                Toast.makeText(PersonViewActivity.this, "禁止销售", Toast.LENGTH_SHORT).show();
            }
            if ("RefusedRegion".equals(strType)) {
                imgPerson.setImageResource(R.mipmap.person_header);
                etAName.setText("");
                etAddress.setText("");
                etNation.setText("");
                etCardCode.setText("");
                bmHeader = null;
                Toast.makeText(PersonViewActivity.this, "禁止销售", Toast.LENGTH_SHORT).show();
            }
            if ("LimitRegion".equals(strType)) {
                linearLayout_code.setVisibility(View.VISIBLE);
                textView_line.setVisibility(View.VISIBLE);
            }

        }
    };
    Runnable runcode = new Runnable() {
        @Override
        public void run() {
            if (!mes.equals("")) {
                Toast.makeText(PersonViewActivity.this, "请输入正确验证码", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void getCodeResult(boolean isTrue, String mes) {

    }

    @Override
    public void getUserInfo(boolean isTrue, String mes) {

    }

    @Override
    public void getSamCode(boolean isTrue, String mes, String sam) {
        this.mes = mes;
        terminalIsTrue = isTrue;
        runOnUiThread(terminalRun);
    }

    Runnable terminalRun = new Runnable() {
        @Override
        public void run() {
            if (!terminalIsTrue) {
                Toast.makeText(PersonViewActivity.this, "设备信息未识别", Toast.LENGTH_SHORT).show();
                PersonViewActivity.this.finish();
            } else {


            }
        }
    };

    private class ConnectThread extends Thread {
        @Override
        public void run() {
            super.run();
            Looper.prepare();
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
            try {
                String ad = utils.ReadString(KEY_BlueToothAddress);
                BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(ad);

                connect(device);
            } catch (Exception e) {
                // TODO: handle exception
                mHandler.sendMessage(mHandler.obtainMessage(200, "连接失败"));
                progressDialog.dismiss();
            }
        }
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (index < 10) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        if (!ReadCardInfo()) {
                            index++;
                            if (index == 9) {
                                progressDialog.dismiss();
                                idCardReader.closeDevice();
                                idCardReader = null;
                                mHandler.sendMessage(mHandler.obtainMessage(200, "读卡失败"));
                            } else {
                                //Toast.makeText(AddEmployeeActivity.this,"请放卡...",Toast.LENGTH_SHORT);
                            }
                        } else {
                            index = 11;
                            mHandler.sendMessage(mHandler.obtainMessage(200, "读卡成功"));
                        }
                    }
                });

                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void connect(BluetoothDevice device) {
        try {
            idCardReader = new IDCardReader();
            idCardReader.setDevice(device.getAddress());
            int ret = 0;
            if (IDCardReader.ERROR_SUCC == (ret = idCardReader.openDevice())) {
                //progressDialog.dismiss();
                //Toast.makeText(AddEmployeeActivity.this, "连接成功", Toast.LENGTH_SHORT).show();
                // ReadCardInfo();
                index = 0;
                workThread = new PersonViewActivity.WorkThread();
                workThread.start();// 线程启动
            } else {
                idCardReader = null;
                progressDialog.dismiss();
                mHandler.sendMessage(mHandler.obtainMessage(200, "连接失败，请重启蓝牙身份证阅读器"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            idCardReader = null;
            progressDialog.dismiss();
            mHandler.sendMessage(mHandler.obtainMessage(200, "连接失败"));
        }
    }

    private boolean ReadCardInfo() {
        if (idCardReader != null) {
            if (!idCardReader.sdtFindCard() || !idCardReader.sdtSelectCard()) {

                return false;
            } else {
                final IDCardInfo idCardInfo = new IDCardInfo();
                String sam = idCardReader.sdtSAMID();
                System.out.println("111sam" + sam);

                //验证设备
                setlistener.getTerminal(sam);


                if (idCardReader.sdtReadCard(1, idCardInfo)) {
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式

                    PersonModel personModel = new PersonModel();
                    personModel.setPersonAddress(idCardInfo.getAddress());
                    personModel.setPersonBirthday(idCardInfo.getBirth().replace("年", "-").replace("月", "-").replace("日", ""));
                    personModel.setPersonNation(queryCode("Code_Nation", idCardInfo.getNation()));
                    personModel.setPersonName(idCardInfo.getName());
                    personModel.setPersonSex(idCardInfo.getSex());
                    personModel.setPersonCardId(idCardInfo.getId());
                    etNation.setText(idCardInfo.getNation());
                    setPersonNation(personModel);


                    if (idCardInfo.getPhoto() != null) {
                        byte[] buf = new byte[WLTService.imgLength];
                        if (1 == WLTService.wlt2Bmp(idCardInfo.getPhoto(), buf)) {
                            Bitmap bitmap = IDPhotoHelper.Bgr2Bitmap(buf);
                            if (null != bitmap) {
                                bmHeader = bitmap;
                                imgPerson.setImageBitmap(bmHeader);
                            }
                        }
                    }
                    progressDialog.dismiss();
                    idCardReader.closeDevice();
                    idCardReader = null;
                    return true;
                } else {
                    return false;
                    //playSound(9, 0);
                }
            }
        } else {
            return false;
        }

    }

    private String queryCode(String CodeName, String string) {
        List<CodeModel> list = dao.QueryByKName(CodeName, string);

        if (list.size() > 0) {
            return list.get(0).getCode();
        }
        return "";

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer = null;
    }

    //拍照设置
    private void configTakePhotoOption(TakePhoto takePhoto) {
        TakePhotoOptions.Builder builder = new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }

    private void configCompress(TakePhoto takePhoto) {
        takePhoto.onEnableCompress(null, false);
        //102400
        int maxSize = Integer.parseInt("512000");
        int width = Integer.parseInt("800");
        int height = Integer.parseInt("800");
        boolean showProgressBar = false;
        boolean enableRawFile = true;
        CompressConfig config;
        config = new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width >= height ? width : height)
                .enableReserveRaw(enableRawFile)
                .create();

        takePhoto.onEnableCompress(config, showProgressBar);


    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        showImg(result.getImages());
    }

    private void showImg(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getOriginalPath());

           /* Glide.with(this).load(file).into(img_base64Image);*/
            bm = BitmapFactory.decodeFile(tImage.getCompressPath());
            load_ocr(bm);

        }

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Toast.makeText(PersonViewActivity.this, "识别成功", Toast.LENGTH_SHORT).show();
        }
    };

}
