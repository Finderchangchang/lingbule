package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoActivity;
import com.jph.takephoto.compress.CompressConfig;
import com.jph.takephoto.model.TImage;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.model.TakePhotoOptions;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.linregister.LinRegisterListener;
import com.xiaohu.fireworkssystem.control.linregister.LinRegisterView;
import com.xiaohu.fireworkssystem.model.BlueToothModel;
import com.xiaohu.fireworkssystem.model.CompanyModel;
import com.xiaohu.fireworkssystem.model.bean.CodeDao;
import com.xiaohu.fireworkssystem.model.bean.CodeModel;
import com.xiaohu.fireworkssystem.model.table.ClientUserModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;
import com.xiaohu.fireworkssystem.view.SpinnerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TemporaryActivity extends TakePhotoActivity implements LinRegisterView {

    private MyTitleView myTitleView;
    private Button bt_register;
    private CompanyModel companyModel;
    //头像
    private Bitmap bm = null;
    private ImageView img_base64Image;
    private TakePhoto takePhoto;
    private LinRegisterListener listener;
    //进度框
    private ProgressDialog mProgressDialog;
    private String strloginname = "";
    private String mes = "", strGUID= "";
    private Utils utils;
    private EditText edit_password,edit_password2,edit_id,edit_companyname,edit_xuke,edit_phone,edit_address,edit_boss,edit_idcard;
    private TextView tv_Area,tv_DeviceCode;
    private CodeDao dao;
    private SpinnerDialog dialog;//选择
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temporary);

        initView();
        initListener();
    }

    private void initListener() {
        //辖区
        tv_Area.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCodeByData(dao.QueryByName("CodeName", "Code_Area"));
            }
        });
        myTitleView.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=bm){
                    companyModel.setBase64LicenseImage(Utils.encodeBitmap(bm));

                }
                if("".equals(edit_password.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_password2.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
                }else if (!edit_password.getText().toString().equals(edit_password2.getText().toString())){
                    Toast.makeText(TemporaryActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_id.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入注册编码",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_companyname.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入企业名称",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_boss.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入法人名称",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_idcard.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入法人证件号码",Toast.LENGTH_SHORT).show();
                }else if("请选择".equals(tv_Area.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请选择辖区",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_xuke.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入许可证",Toast.LENGTH_SHORT).show();
                }/*else if(null==bm){
                    Toast.makeText(TemporaryActivity.this,"请拍摄许可证图片",Toast.LENGTH_SHORT).show();
                }*/else if("".equals(edit_phone.getText().toString().trim())){
                    Toast.makeText(TemporaryActivity.this,"请输入联系电话",Toast.LENGTH_SHORT).show();
                }else {
                    companyModel.setCreateUserId("APP");
                    companyModel.setDeviceCode(tv_DeviceCode.getText().toString());
                    companyModel.setTerminalCode(edit_id.getText().toString().trim());
                    companyModel.setCompanyName(edit_companyname.getText().toString().trim());
                    companyModel.setBoss(edit_boss.getText().toString().trim());
                    companyModel.setBossCertNumber(edit_idcard.getText().toString().trim());
                    companyModel.setLicenseNumber(edit_xuke.getText().toString().trim());
                    companyModel.setCompanyPhone(edit_phone.getText().toString().trim());
                    companyModel.setCompanyAddress(edit_address.getText().toString().trim());
                    companyModel.setClientUserPassWord(edit_password.getText().toString().trim());
                    companyModel.setLicenseNumberValidity("2018-03-10");
                    companyModel.setBusinessLicenseNumberValidity("2018-03-10");

                    companyModel.setBusinessLicenseNumber(strGUID);
                    companyModel.setCVR(true);
                    companyModel.setCompanyType("05");
                    mProgressDialog.setTitle("请稍后");
                    mProgressDialog.setMessage("正在提交数据");
                    mProgressDialog.show();
                    listener.Search(companyModel);
                }

            }
        });
        img_base64Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromCapture(imageUri);
            }
        });
    }

    private void initView() {
        myTitleView = (MyTitleView) findViewById(R.id.temporary_title);
        bt_register = (Button) findViewById(R.id.bt_register);
        companyModel = new CompanyModel();
        img_base64Image = (ImageView) findViewById(R.id.img_base64Image);
        listener = new LinRegisterListener(this,TemporaryActivity.this);
        takePhoto = getTakePhoto();
        utils = new Utils(TemporaryActivity.this);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_password2 = (EditText) findViewById(R.id.edit_password2);
        edit_id = (EditText) findViewById(R.id.edit_id);
        edit_companyname = (EditText) findViewById(R.id.edit_companyname);
        tv_Area = (TextView) findViewById(R.id.tv_Area);
        tv_DeviceCode = (TextView) findViewById(R.id.tv_DeviceCode);
        edit_xuke = (EditText) findViewById(R.id.edit_xuke);
        edit_phone = (EditText) findViewById(R.id.edit_phone);
        edit_address = (EditText) findViewById(R.id.edit_address);
        edit_boss = (EditText) findViewById(R.id.edit_boss);
        edit_idcard = (EditText) findViewById(R.id.edit_idcard);
        dao = new CodeDao(this);
        //进度条对话框
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

        //设备描述符
        strGUID = java.util.UUID.randomUUID().toString();
        strGUID = strGUID.replaceAll("-","");
        strGUID = strGUID.substring(0,20);
            tv_DeviceCode.setText(Utils.getMYImei(((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                    .getDeviceId()));

    }
    //拍照设置
    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){
        takePhoto.onEnableCompress(null,false);
        //102400
        int maxSize= Integer.parseInt("512000");
        int width= Integer.parseInt("800");
        int height= Integer.parseInt("800");
        boolean showProgressBar=false;
        boolean enableRawFile = true;
        CompressConfig config;
        config=new CompressConfig.Builder()
                .setMaxSize(maxSize)
                .setMaxPixel(width>=height? width:height)
                .enableReserveRaw(enableRawFile)
                .create();

        takePhoto.onEnableCompress(config,showProgressBar);


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

            Glide.with(this).load(file).into(img_base64Image);
            bm = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }

    @Override
    public void LinSearchView(String message, ClientUserModel clientUserModel) {
        mProgressDialog.dismiss();
        mes = message;
        if (null!=clientUserModel){
            if (null!=clientUserModel.getLoginName()&&!"".equals(clientUserModel.getLoginName().toString())){
                strloginname = clientUserModel.getLoginName().toString();
            }
        }
        TemporaryActivity.this.runOnUiThread(run);

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {

            if (!mes.equals("")){
                Toast.makeText(TemporaryActivity.this, mes, Toast.LENGTH_SHORT).show();
            }else {
                if (!"".equals(strloginname)){
                  /*  loginmin.finish(); */
                    utils.WriteString("username",strloginname);
                    Intent intent = new Intent();
                    intent.setClass(TemporaryActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    };
    private void getCodeByData(final List<CodeModel> list) {

        List<BlueToothModel> listBlue = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            CodeModel model = list.get(i);
            BlueToothModel blueToothModel = new BlueToothModel();
            blueToothModel.setDeviceAddress(model.getCode());
            blueToothModel.setDeviceName(model.getName());
            listBlue.add(blueToothModel);
        }
        if (listBlue.size() > 0) {
            dialog = new SpinnerDialog(TemporaryActivity.this);
            dialog.setListView(listBlue);
            dialog.show();
            dialog.setOnItemClick(new SpinnerDialog.OnItemClick() {
                @Override
                public void onClick(int position, BlueToothModel val) {
                    companyModel.setArea(val.getDeviceAddress());
                    tv_Area.setText(val.getDeviceName());


                }
            });
        }
    }
}
