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
import com.xiaohu.fireworkssystem.model.CompanyModel;
import com.xiaohu.fireworkssystem.model.table.ClientUserModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.io.File;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_IMEI_MI;
import static com.xiaohu.fireworkssystem.ui.activity.LinRegisterActivity.linthis;
import static com.xiaohu.fireworkssystem.ui.activity.LoginActivity.loginmin;


public class PasswordActivity extends TakePhotoActivity implements LinRegisterView {

    private TextView tv_DeviceCode;
    private Utils utils;
    private CompanyModel companyModel;
    private EditText edit_password,edit_passwords;
    private Button bt_regist;
    private LinRegisterListener listener;
    private String mes = "";
    //图片保存路径
    private StringBuffer path = new StringBuffer();
    //头像
    private Bitmap bm = null;
    private Bitmap cerbm = null;
    private Bitmap busbm = null;
    private ImageView img_base64BossCredentialImage,img_base64LeaderCredentialImage,img_base64BusinessLicenseImages;
    //进度框
    private ProgressDialog mProgressDialog;
    private String strloginname = "";
    private MyTitleView password_title;
    private TakePhoto takePhoto;
    private int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        initView();
        initValue();
        initListener();
    }

    private void initListener() {
        password_title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_base64BossCredentialImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                i = 0 ;

                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromCapture(imageUri);
            }
        });
        img_base64LeaderCredentialImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                i = 1 ;
                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromCapture(imageUri);
            }
        });
        img_base64BusinessLicenseImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
                if (!file.getParentFile().exists())file.getParentFile().mkdirs();
                Uri imageUri = Uri.fromFile(file);
                i = 2 ;
                configCompress(takePhoto);
                configTakePhotoOption(takePhoto);
                takePhoto.onPickFromCapture(imageUri);
            }
        });
        bt_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null!=bm){
                    companyModel.setBase64BossCredentialImage(Utils.encodeBitmap(bm));

                }
                if (null!=cerbm){
                    companyModel.setBase64LicenseImage(Utils.encodeBitmap(cerbm));
                }
                if (null!=busbm){
                    companyModel.setBase64BusinessLicenseImage(Utils.encodeBitmap(busbm));
                }
               if("".equals(edit_password.getText().toString().trim())){
                    Toast.makeText(PasswordActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                }else if("".equals(edit_passwords.getText().toString().trim())){
                    Toast.makeText(PasswordActivity.this,"请输入确认密码",Toast.LENGTH_SHORT).show();
                }else if (!edit_password.getText().toString().equals(edit_passwords.getText().toString())){
                    Toast.makeText(PasswordActivity.this,"密码不一致",Toast.LENGTH_SHORT).show();
                }else {
                    companyModel.setDeviceCode(tv_DeviceCode.getText().toString());
                    companyModel.setClientUserPassWord(edit_password.getText().toString().trim());
                    companyModel.setCreateUserId("APP");

                   mProgressDialog.setTitle("请稍后");
                   mProgressDialog.setMessage("正在提交数据");
                   mProgressDialog.show();
                   listener.Search(companyModel);



               }
            }
        });
    }

    private void initView() {
        utils = new Utils(this);
        takePhoto = getTakePhoto();
        password_title = (MyTitleView) findViewById(R.id.password_title);
        companyModel = (CompanyModel) getIntent().getSerializableExtra("CompanyModel");
        tv_DeviceCode = (TextView) findViewById(R.id.tv_DeviceCode);
        edit_password = (EditText) findViewById(R.id.edit_password);
        edit_passwords = (EditText) findViewById(R.id.edit_passwords);
        bt_regist = (Button) findViewById(R.id.bt_regist);
        img_base64BossCredentialImage = (ImageView) findViewById(R.id.img_base64BossCredentialImage);
        img_base64LeaderCredentialImage = (ImageView) findViewById(R.id.img_base64LeaderCredentialImage);
        img_base64BusinessLicenseImages = (ImageView) findViewById(R.id.img_base64BusinessLicenseImages);
        listener = new LinRegisterListener(this,PasswordActivity.this);
        //进度条对话框
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

    }
    private void initValue() {
        //设备描述符
        String shouquan = utils.ReadString(KEY_IMEI_MI);
        if (shouquan.equals("")) {
            tv_DeviceCode.setText(Utils.getMYImei(((TelephonyManager) getSystemService(TELEPHONY_SERVICE))
                    .getDeviceId()));
        } else {
            tv_DeviceCode.setText(shouquan);
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


        PasswordActivity.this.runOnUiThread(run);

    }

    Runnable run = new Runnable() {
        @Override
        public void run() {

            if (!mes.equals("")){
                Toast.makeText(PasswordActivity.this, mes, Toast.LENGTH_SHORT).show();
            }else {
                if (!"".equals(strloginname)){
                    utils.WriteString("username",strloginname);
                    loginmin.finish();
                    linthis.finish();
                    Intent intent = new Intent();
                    intent.setClass(PasswordActivity.this,LoginActivity.class);
                    startActivity(intent);
                    finish();

                }


            }


        }
    };




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
        if (i == 0){
            showImg(result.getImages());
        }else if (i == 1){
            showImg2(result.getImages());
        }else if (i == 2){
            showImg3(result.getImages());
        }

    }

    private void showImg(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getOriginalPath());

            Glide.with(this).load(file).into(img_base64BossCredentialImage);
            bm = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }
    private void showImg2(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getCompressPath());

            Glide.with(this).load(file).into(img_base64LeaderCredentialImage);
            cerbm = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }
    private void showImg3(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getCompressPath());

            Glide.with(this).load(file).into(img_base64BusinessLicenseImages);
            busbm = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
