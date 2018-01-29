package com.xiaohu.fireworkssystem.ui.activity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.xiaohu.fireworkssystem.control.companyimage.CompanyImageSearchView;
import com.xiaohu.fireworkssystem.control.editcompany.EditCompanyListener;
import com.xiaohu.fireworkssystem.control.editcompany.EditCompanyView;
import com.xiaohu.fireworkssystem.model.EditCompanyModel;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.DatePickerDialog;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;


public class EditCompanyActivity extends TakePhotoActivity implements EditCompanyView,CompanyImageSearchView {

    private MyTitleView editcompany_title;
    private Button bt_save;
    private TextView tv_LicenseNumberValidity,tv_BusinessLicenseNumberValidity;
    private EditText edit_CompanyName,edit_LicenseNumber,edit_BusinessLicenseNumber,
            edit_CompanyAddress,edit_CompanyPhone,edit_LeaderLinkWay,edit_PointEast,edit_PointNorth;
    private ImageView img_base64BusinessLicenseImages,img_Base64LicenseImage;
    //时间选择控件
    private DatePickerDialog dateDialog;
    //图片保存路径
    private StringBuffer path = new StringBuffer();
    //头像
    private Bitmap busbm = null;
    private Bitmap leabm = null;
    private ViewCompanyModel viewCompanyModel;
    private EditCompanyModel companyModel;
    private EditCompanyListener listener;
    private String Mes = "";
    private boolean boo = false;
    //进度框
    private ProgressDialog mProgressDialog;
    private FileInputStream is = null;
    private FileInputStream is2 = null;
    private TakePhoto takePhoto;
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_company);

        initView();
        initValue();
        initListener();
    }

    private void initValue() {


        tv_LicenseNumberValidity.setText(Utils.isEmptyString2(viewCompanyModel.getLicenseNumberValidity()));
        tv_BusinessLicenseNumberValidity.setText(Utils.isEmptyString2(viewCompanyModel.getBusinessLicenseNumberValidity()));
        edit_CompanyName.setText(Utils.isEmptyString2(viewCompanyModel.getCompanyName()));
        edit_LicenseNumber.setText(Utils.isEmptyString2(viewCompanyModel.getLicenseNumber()));
        edit_BusinessLicenseNumber.setText(Utils.isEmptyString2(viewCompanyModel.getBusinessLicenseNumber()));
        edit_CompanyAddress.setText(Utils.isEmptyString2(viewCompanyModel.getCompanyAddress()));
        edit_CompanyPhone.setText(Utils.isEmptyString2(viewCompanyModel.getCompanyPhone()));
        edit_LeaderLinkWay.setText(Utils.isEmptyString2(viewCompanyModel.getLeaderLinkWay()));
        edit_PointNorth.setText(Utils.isEmptyString2(viewCompanyModel.getPointNorth()));
        edit_PointEast.setText(Utils.isEmptyString2(viewCompanyModel.getPointEast()));

        try {
            is = EditCompanyActivity.this.openFileInput("businesslicense");
            if (is!=null){
                leabm = BitmapFactory.decodeStream(is);
                img_Base64LicenseImage.setImageBitmap(leabm);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            is2 = EditCompanyActivity.this.openFileInput("license");
            if (is2!=null){
                busbm = BitmapFactory.decodeStream(is2);
                img_base64BusinessLicenseImages.setImageBitmap(busbm);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initListener() {


        img_base64BusinessLicenseImages.setOnClickListener(new View.OnClickListener() {
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
        img_Base64LicenseImage.setOnClickListener(new View.OnClickListener() {
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
        editcompany_title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_LicenseNumberValidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DatePickerDialog(EditCompanyActivity.this, tv_LicenseNumberValidity.getText().toString());
                dateDialog.datePickerDialog(tv_LicenseNumberValidity);//直接改变edittext的控件的值
            }
        });

        tv_BusinessLicenseNumberValidity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog = new DatePickerDialog(EditCompanyActivity.this, tv_BusinessLicenseNumberValidity.getText().toString());
                dateDialog.datePickerDialog(tv_BusinessLicenseNumberValidity);//直接改变edittext的控件的值
            }
        });
        bt_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //企业id
                companyModel.setCompanyId(viewCompanyModel.getCompanyId());
                if ("".equals(edit_CompanyName.getText().toString())){
                    Toast.makeText(EditCompanyActivity.this,"请输入企业名称",Toast.LENGTH_SHORT).show();
                }else if ("".equals(edit_LeaderLinkWay.getText().toString())){
                    Toast.makeText(EditCompanyActivity.this,"请输入负责人联系方式",Toast.LENGTH_SHORT).show();
                }else if ("".equals(edit_LicenseNumber.getText().toString())){
                    Toast.makeText(EditCompanyActivity.this,"请输入许可证号",Toast.LENGTH_SHORT).show();
                }else if ("".equals(edit_BusinessLicenseNumber.getText().toString())){
                    Toast.makeText(EditCompanyActivity.this,"请输入营业执照",Toast.LENGTH_SHORT).show();
                }else if ("".equals(edit_CompanyAddress.getText().toString())){
                    Toast.makeText(EditCompanyActivity.this,"请输入企业地址",Toast.LENGTH_SHORT).show();
                }else if ("".equals(edit_CompanyPhone.getText().toString())){
                    Toast.makeText(EditCompanyActivity.this,"请输入企业电话",Toast.LENGTH_SHORT).show();
                }else {
                    //企业名称
                    companyModel.setCompanyName(edit_CompanyName.getText().toString().trim());
                    //负责人联系方式
                    companyModel.setLeaderLinkWay(edit_LeaderLinkWay.getText().toString().trim());
                    //许可证号
                    companyModel.setLicenseNumber(edit_LicenseNumber.getText().toString().trim());
                    //许可证有效期
                    companyModel.setLicenseNumberValidity(tv_LicenseNumberValidity.getText().toString().trim());
                    //营业执照
                    companyModel.setBusinessLicenseNumber(edit_BusinessLicenseNumber.getText().toString().trim());
                    //营业执照有效期
                    companyModel.setBusinessLicenseNumberValidity(tv_BusinessLicenseNumberValidity.getText().toString().trim());
                    //企业地址
                    companyModel.setCompanyAddress(edit_CompanyAddress.getText().toString().trim());
                    //企业电话
                    companyModel.setCompanyPhone(edit_CompanyPhone.getText().toString().trim());

                    //营业执照图片
                    if (busbm!=null){
                        companyModel.setBase64BusinessLicenseImage(Utils.encodeBitmap(busbm));
                    }
                    //许可证图片
                    if (leabm!=null){
                        companyModel.setBase64LicenseImage(Utils.encodeBitmap(leabm));
                    }
                    mProgressDialog.setTitle("请稍后");
                    mProgressDialog.setMessage("正在提交数据");
                    mProgressDialog.show();
                    listener.Search(companyModel);
                }

            }
        });
    }

    private void initView() {
        takePhoto = getTakePhoto();
        companyModel = new EditCompanyModel();
        listener = new EditCompanyListener(EditCompanyActivity.this,this);
        viewCompanyModel = (ViewCompanyModel) getIntent().getSerializableExtra("ViewCompanyModel");
        editcompany_title = (MyTitleView) findViewById(R.id.editcompany_title);
        bt_save = (Button) findViewById(R.id.bt_save);
        img_base64BusinessLicenseImages = (ImageView) findViewById(R.id.img_base64BusinessLicenseImages);
        img_Base64LicenseImage = (ImageView) findViewById(R.id.img_Base64LicenseImage);
        edit_CompanyName = (EditText) findViewById(R.id.edit_CompanyName);
        edit_LicenseNumber = (EditText) findViewById(R.id.edit_LicenseNumber);
        edit_CompanyAddress = (EditText) findViewById(R.id.edit_CompanyAddress);
        edit_CompanyPhone = (EditText) findViewById(R.id.edit_CompanyPhone);
        edit_BusinessLicenseNumber = (EditText) findViewById(R.id.edit_BusinessLicenseNumber);
        tv_BusinessLicenseNumberValidity = (TextView) findViewById(R.id.tv_BusinessLicenseNumberValidity);
        tv_LicenseNumberValidity = (TextView) findViewById(R.id.tv_LicenseNumberValidity);
        edit_LeaderLinkWay = (EditText) findViewById(R.id.edit_LeaderLinkWay);
        edit_PointEast = (EditText) findViewById(R.id.edit_PointEast);
        edit_PointNorth = (EditText) findViewById(R.id.edit_PointNorth);


        //进度条对话框
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIcon(android.R.drawable.ic_dialog_info);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);

    }




    @Override
    public void SearchEditCompany(String message, Boolean boo) {
        Mes = message;
        this.boo = boo;

        EditCompanyActivity.this.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            mProgressDialog.dismiss();

            if (boo){
                if ("".equals(Mes)){
                    Toast.makeText(EditCompanyActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    setResult(99);
                    finish();
                }else {
                    Toast.makeText(EditCompanyActivity.this,Mes,Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(EditCompanyActivity.this,Mes,Toast.LENGTH_SHORT).show();
            }
        }

    };

    @Override
    public void SearchImage(String message, String[] str) {

    }


    //拍照设置
    private void configTakePhotoOption(TakePhoto takePhoto){
        TakePhotoOptions.Builder builder=new TakePhotoOptions.Builder();
        builder.setCorrectImage(true);
        takePhoto.setTakePhotoOptions(builder.create());

    }
    private void configCompress(TakePhoto takePhoto){
        takePhoto.onEnableCompress(null,false);

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
        }else {
            showImg2(result.getImages());
        }

    }

    private void showImg(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getCompressPath());

            Glide.with(this).load(file).into(img_base64BusinessLicenseImages);
            busbm = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }
    private void showImg2(ArrayList<TImage> images) {
        if (images.size() % 2 == 1) {
            TImage tImage = images.get(images.size() - 1);
            File file = new File((tImage).getCompressPath());

            Glide.with(this).load(file).into(img_Base64LicenseImage);
            leabm = BitmapFactory.decodeFile(tImage.getCompressPath());

        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
