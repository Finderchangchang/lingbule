package com.xiaohu.fireworkssystem.ui.fragement;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.company.CompanyListener;
import com.xiaohu.fireworkssystem.control.company.CompanySearchView;
import com.xiaohu.fireworkssystem.control.companyimage.CompanyImageListener;
import com.xiaohu.fireworkssystem.control.companyimage.CompanyImageSearchView;
import com.xiaohu.fireworkssystem.model.search.SearchCompanyModel;
import com.xiaohu.fireworkssystem.model.view.ViewCompanyModel;
import com.xiaohu.fireworkssystem.ui.activity.EditCompanyActivity;
import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

import static com.xiaohu.fireworkssystem.ui.activity.CompanyActivity.CompanyActivityContext;
import static com.xiaohu.fireworkssystem.utils.Utils.isEmptyString2;


public class CompanyFragment extends Fragment  implements CompanySearchView ,CompanyImageSearchView{
    private View view;
    private List<ViewCompanyModel> resultList;
    private CompanyListener listener;
    private SearchCompanyModel searchCompanyModel;
    private Utils utils;
    private static final int ERROR = 1;
    private static final int SUCCESS = 2 ;
    private static final int SUCCESS2 = 3;
    private ViewCompanyModel model;
    private TextView tv_companyname,tv_companyid,tv_companytype,tv_areaname,tv_companystate,tv_companytel,tv_boss,
            tv_bossphone,tv_businesslicensenumber,tv_BusinessLicenseNumberValidity,tv_LicenseNumber,tv_licenseNumberValidity,
            tv_leader,tv_leadertel,tv_LeaderCertNumber,tv_comment;
    private ImageView iv_license,iv_businesslicense;
    private String myMes = "";
    private String string[];
    private CompanyImageListener companyImageListener;
    private Button bt_editcompany;
    private Bitmap bit1 ,bit2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_company, container, false);


        initView();

        return view;
    }

    private void initData() {
        model = resultList.get(0);
        final String stringheaderid =model.getCompanyId();
        companyImageListener = new CompanyImageListener(this,CompanyActivityContext);
        companyImageListener.Search(stringheaderid);
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
        bt_editcompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("ViewCompanyModel",model);

                if (bit1!=null){
                    //传许可证
                    Bitmap picture = bit1;
                    try {
                        FileOutputStream fos = null;
                        File file = null;
                        file = CompanyActivityContext.getFileStreamPath("license");
                        fos = new FileOutputStream(file);
// save picture to local file
                        picture.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if (bit2!=null){
                    //传营业执照
                    Bitmap picture2 = bit2;
                    try {
                        FileOutputStream fos = null;
                        File file = null;
                        file = CompanyActivityContext.getFileStreamPath("businesslicense");
                        fos = new FileOutputStream(file);
// save picture to local file
                        picture2.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                        fos.flush();
                        fos.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



                intent.setClass(CompanyActivityContext, EditCompanyActivity.class);
                startActivityForResult(intent,99);
            }
        });
    }

    private void initView() {
        utils = new Utils(CompanyActivityContext);
        resultList = new ArrayList<>();
        listener = new CompanyListener(this, CompanyActivityContext);
        searchCompanyModel = new SearchCompanyModel();
        searchCompanyModel.setCompanyID(utils.ReadString("companyID"));
        listener.Search(searchCompanyModel, 1);

        bt_editcompany = (Button) view.findViewById(R.id.bt_editcompany);
        tv_companyname = (TextView) view.findViewById(R.id.tv_companyname);
        tv_companyid = (TextView) view.findViewById(R.id.tv_companyid);
        tv_companytype = (TextView) view.findViewById(R.id.tv_companytype);
        tv_areaname = (TextView) view.findViewById(R.id.tv_areaname);
        tv_companystate = (TextView) view.findViewById(R.id.tv_companystate);
        tv_companytel = (TextView) view.findViewById(R.id.tv_companytel);
        tv_boss = (TextView) view.findViewById(R.id.tv_boss);
        tv_bossphone = (TextView) view.findViewById(R.id.tv_bossphone);
        tv_businesslicensenumber = (TextView) view.findViewById(R.id.tv_businesslicensenumber);
        tv_BusinessLicenseNumberValidity = (TextView) view.findViewById(R.id.tv_BusinessLicenseNumberValidity);
        tv_LicenseNumber = (TextView) view.findViewById(R.id.tv_LicenseNumber);
        tv_licenseNumberValidity = (TextView) view.findViewById(R.id.tv_licenseNumberValidity);
        tv_leader = (TextView) view.findViewById(R.id.tv_leader);
        tv_leadertel = (TextView) view.findViewById(R.id.tv_leadertel);
        tv_LeaderCertNumber = (TextView) view.findViewById(R.id.tv_LeaderCertNumber);
        tv_comment = (TextView) view.findViewById(R.id.tv_comment);
        iv_license = (ImageView) view.findViewById(R.id.iv_license);
        iv_businesslicense = (ImageView) view.findViewById(R.id.iv_businesslicense);
    }

    @Override
    public void Result(ViewCompanyModel model) {

    }

    @Override
    public void ResultList(String string ,List<ViewCompanyModel> list) {
        resultList = list;
        CompanyActivityContext.runOnUiThread(run);
    }

    Runnable run = new Runnable() {
        @Override
        public void run() {
            if (resultList != null) {
                initData();
            } else {
                Toast.makeText(CompanyActivityContext,"网络错误！",Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void SearchImage(String message, String[] str) {
        string = str;
        myMes = message;
        CompanyActivityContext.runOnUiThread(runn);
    }
    Runnable runn = new Runnable() {
        @Override
        public void run() {
            initImageView(string[0]);
            if(string.length>1){
                initImageView2(string[1]);
            }

            if(!myMes.equals(""))
                Toast.makeText(CompanyActivityContext, myMes, Toast.LENGTH_SHORT).show();
        }
    };
    private void initImageView(final String imageId ) {


        new Thread(){
            public void run() {
                //获取okHttp对象get请求,

                try {
                    OkHttpClient client = new OkHttpClient();
                    String string = utils.ReadString("IP");
                    String url = getResources().getString(R.string.http)+"://"+string+"/Employee/GetHeadImage?HeadImageID="+imageId+"&imageType=Company";
                    Request request = new Request.Builder().url(url).build();
                    //获取响应体
                    ResponseBody body = client.newCall(request).execute().body();
                    //获取流
                    InputStream in = body.byteStream();
                    //转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    bit2 = bitmap;
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
                    String url = getResources().getString(R.string.http)+"://"+string+"/Employee/GetHeadImage?HeadImageID="+imageId+"&imageType=Company";
                    Request request = new Request.Builder().url(url).build();
                    //获取响应体
                    ResponseBody body = client.newCall(request).execute().body();
                    //获取流
                    InputStream in = body.byteStream();
                    //转化为bitmap
                    Bitmap bitmap = BitmapFactory.decodeStream(in);
                    bit1 = bitmap;
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

                    Toast.makeText(CompanyActivityContext, "请求超时", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };

}
