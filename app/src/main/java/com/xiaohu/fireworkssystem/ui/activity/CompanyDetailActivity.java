package com.xiaohu.fireworkssystem.ui.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.companyimage.CompanyImageListener;
import com.xiaohu.fireworkssystem.control.companyimage.CompanyImageSearchView;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.utils.Utils;
import com.xiaohu.fireworkssystem.view.MyTitleView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static com.xiaohu.fireworkssystem.utils.Utils.isEmptyString2;

public class CompanyDetailActivity extends AppCompatActivity implements CompanyImageSearchView {
    private static final int ERROR = 1;
    private static final int SUCCESS = 2 ;
    private static final int SUCCESS2 = 3;
    private MyTitleView company_detail_title;
    private ViewCompanyModel model;
    private TextView tv_companyname,tv_companyid,tv_companytype,tv_areaname,tv_companystate,tv_companytel,tv_boss,
            tv_bossphone,tv_businesslicensenumber,tv_BusinessLicenseNumberValidity,tv_LicenseNumber,tv_licenseNumberValidity,
            tv_leader,tv_leadertel,tv_LeaderCertNumber,tv_comment;
    private ImageView iv_license,iv_businesslicense;
    private String myMes = "";
    private String string[];
    private Utils utils;
    private CompanyImageListener companyImageListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_detail);

        initView();
        initDate();
        initListener();
    }
    private void initView() {
        utils = new Utils(CompanyDetailActivity.this);
        model = (ViewCompanyModel) getIntent().getSerializableExtra("CompanyModel");
        companyImageListener = new CompanyImageListener(this,CompanyDetailActivity.this);
        final String stringheaderid =model.getCompanyId();
        companyImageListener.Search(stringheaderid);
        company_detail_title = (MyTitleView) findViewById(R.id.company_detail_title);
        tv_companyname = (TextView) findViewById(R.id.tv_companyname);
        tv_companyid = (TextView) findViewById(R.id.tv_companyid);
        tv_companytype = (TextView) findViewById(R.id.tv_companytype);
        tv_areaname = (TextView) findViewById(R.id.tv_areaname);
        tv_companystate = (TextView) findViewById(R.id.tv_companystate);
        tv_companytel = (TextView) findViewById(R.id.tv_companytel);
        tv_boss = (TextView) findViewById(R.id.tv_boss);
        tv_bossphone = (TextView) findViewById(R.id.tv_bossphone);
        tv_businesslicensenumber = (TextView) findViewById(R.id.tv_businesslicensenumber);
        tv_BusinessLicenseNumberValidity = (TextView) findViewById(R.id.tv_BusinessLicenseNumberValidity);
        tv_LicenseNumber = (TextView) findViewById(R.id.tv_LicenseNumber);
        tv_licenseNumberValidity = (TextView) findViewById(R.id.tv_licenseNumberValidity);
        tv_leader = (TextView) findViewById(R.id.tv_leader);
        tv_leadertel = (TextView) findViewById(R.id.tv_leadertel);
        tv_LeaderCertNumber = (TextView) findViewById(R.id.tv_LeaderCertNumber);
        tv_comment = (TextView) findViewById(R.id.tv_comment);
        iv_license = (ImageView) findViewById(R.id.iv_license);
        iv_businesslicense = (ImageView) findViewById(R.id.iv_businesslicense);
    }
    private void initDate() {
        model = (ViewCompanyModel) getIntent().getSerializableExtra("CompanyModel");
        tv_companyname.setText(isEmptyString2(model.getCompanyName()));
        tv_companyid.setText(isEmptyString2(model.getCompanyId()));
        tv_companytype.setText(isEmptyString2(model.getCompanyTypeName()));
        tv_areaname.setText(isEmptyString2(model.getAreaName()));
        tv_companystate.setText(isEmptyString2(model.getCompanyStateName()));
        tv_companytel.setText(isEmptyString2(model.getCompanyPhone()));
        tv_boss.setText(isEmptyString2(model.getBoss()));
        tv_bossphone.setText(isEmptyString2(model.getBossCertNumber()));
        tv_businesslicensenumber.setText(isEmptyString2(model.getBusinessLicenseNumber()));
        tv_BusinessLicenseNumberValidity.setText(isEmptyString2(model.getBusinessLicenseNumberValidity()));
        tv_LicenseNumber.setText(isEmptyString2(model.getLicenseNumber()));
        tv_licenseNumberValidity.setText(isEmptyString2(model.getLicenseNumberValidity()));
        tv_leader.setText(isEmptyString2(model.getLeader()));
        tv_leadertel.setText(isEmptyString2(model.getLeaderLinkWay()));
        tv_LeaderCertNumber.setText(isEmptyString2(model.getLeaderCertNumber()));
        tv_comment.setText(isEmptyString2(model.getComment()));
    }

    private void initListener() {
        company_detail_title.setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void SearchImage(String message, String[] str) {
        string = str;
        myMes = message;
        CompanyDetailActivity.this.runOnUiThread(run);
    }
    Runnable run = new Runnable() {
        @Override
        public void run() {
            initImageView(string[0]);
            if(string.length>1){
                initImageView2(string[1]);
            }

            if(!myMes.equals(""))
                Toast.makeText(CompanyDetailActivity.this, myMes, Toast.LENGTH_SHORT).show();
        }
    };
    private void initImageView(final String imageId ) {


        new Thread(){
            public void run() {
                //获取okHttp对象get请求,

                try {
                    OkHttpClient client = new OkHttpClient();
                    String string = utils.ReadString("IP");
                    String url = "http://"+string+"/Employee/GetHeadImage?HeadImageID="+imageId+"&imageType=Company";
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
    private void initImageView2(final String imageId ) {


        new Thread(){
            public void run() {
                //获取okHttp对象get请求,

                try {
                    OkHttpClient client = new OkHttpClient();


                    String string = utils.ReadString("IP");
                    String url = "http://"+string+"/Employee/GetHeadImage?HeadImageID="+imageId+"&imageType=Company";
                    Request request = new Request.Builder().url(url).build();
                    //获取响应体
                    ResponseBody body = client.newCall(request).execute().body();
                    //获取流
                    InputStream in = body.byteStream();
                    //转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    //使用Hanlder发送消息
                    Message msg = Message.obtain();
                    msg.what = SUCCESS2;
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
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    iv_license.setImageBitmap((Bitmap) msg.obj);
                    if(null==msg.obj){
                        iv_license.setImageResource(R.mipmap.image_loading);
                    }
                    break;
                case SUCCESS2:
                    iv_businesslicense.setImageBitmap((Bitmap) msg.obj);
                    if(null==msg.obj){
                        iv_businesslicense.setImageResource(R.mipmap.image_loading);
                    }
                    break;
                case ERROR:

                    Toast.makeText(CompanyDetailActivity.this, "请求超时", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
}
