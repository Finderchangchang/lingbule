package com.xiaohu.fireworkssystem.utils;


import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xiaohu.fireworkssystem.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.xiaohu.fireworkssystem.ui.activity.LoginActivity.loginmin;


public class UpdateManager {

    private Context mContext;
    private Utils utils;
    private String stringVersion;
    //提示语
    private String updateMsg = "有最新的软件包，请您下载使用";

    //返回的安装包url
    //private String apkUrl = "http://192.168.1.113:6058/APK/1.0.apk";
    private String apkUrl ="";
    private Dialog noticeDialog;

    private Dialog downloadDialog;
    /* 下载包安装路径 */
    private static final String savePath = "/sdcard/烟花爆竹信息采集追溯管理系统/";

    private static final String saveFileName = savePath + "UpdateRelease.apk";

    /* 进度条与通知ui刷新的handler和msg常量 */
    private ProgressBar mProgress;
    private TextView textView_count;

    private static final int DOWN_UPDATE = 1;

    private static final int DOWN_OVER = 2;

    private int progress;

    private Thread downLoadThread;

    private boolean interceptFlag = false;

    private Handler mHandler = new Handler(){
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWN_UPDATE:
                    mProgress.setProgress(progress);
                    break;
                case DOWN_OVER:

                    installApk();
                    loginmin.finish();
                    System.exit(0);
                    break;
                default:
                    break;
            }
        };
    };

    public UpdateManager(Context context , String sting) {

        this.mContext = context;
        this.stringVersion = sting;
    }

    //外部接口让主Activity调用
    public void checkUpdateInfo(){
        showNoticeDialog();
    }


    private void showNoticeDialog(){
        utils = new Utils(mContext);
        String stringIP =  utils.ReadString("IP");
        apkUrl =  mContext.getResources().getString(R.string.http)+"://"+stringIP+"/APK/"+mContext.getResources().getString(R.string.updateaddress)+"/"+stringVersion+".apk";
        Builder builder = new Builder(mContext);
        builder.setTitle("软件版本更新");
        builder.setMessage(updateMsg);
        builder.setCancelable(false);
        builder.setPositiveButton("下载", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                showDownloadDialog();

             /*   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    DownloadUtils downloadUtils = new DownloadUtils(mContext);
                    downloadUtils.downloadAPK(apkUrl,"UpdateRelease.apk");
                }else {
                    dialog.dismiss();
                    showDownloadDialog();
                }*/

            }
        });
        builder.setNegativeButton("以后再说", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                loginmin.finish();
                System.exit(0);
            }
        });
        noticeDialog = builder.create();
        noticeDialog.show();
    }

    private void showDownloadDialog(){
        Builder builder = new Builder(mContext);
        builder.setTitle("软件版本更新");
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.progress, null);
        mProgress = (ProgressBar)v.findViewById(R.id.progress);
        builder.setView(v);
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                interceptFlag = true;
                loginmin.finish();
                System.exit(0);
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
        downloadApk();
    }

    private Runnable mdownApkRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                URL url = new URL(apkUrl);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();
                conn.connect();
                int length = conn.getContentLength();
                InputStream is = conn.getInputStream();
                File file = new File(savePath);
                if(!file.exists()){
                    file.mkdir();
                }
                String apkFile = saveFileName;
                File ApkFile = new File(apkFile);
                FileOutputStream fos = new FileOutputStream(ApkFile);
                int count = 0;
                byte buf[] = new byte[1024];
                do{
                    int numread = is.read(buf);
                    count += numread;
                    progress =(int)(((float)count / length) * 100);
                    //更新进度
                    mHandler.sendEmptyMessage(DOWN_UPDATE);
                    if(numread <= 0){
                        //下载完成通知安装
                        mHandler.sendEmptyMessage(DOWN_OVER);
                        break;
                    }
                    fos.write(buf,0,numread);
                }while(!interceptFlag);//点击取消就停止下载.

                fos.close();
                is.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    };

    /**
     * 下载apk
     */

    private void downloadApk(){
        downLoadThread = new Thread(mdownApkRunnable);
        downLoadThread.start();
    }


    /**
     * 安装apk
     */
    private void installApk(){
      /*  File apkfile = new File(saveFileName);
        if (!apkfile.exists()) {
            return;
        }
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);*/
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, "com.xiaohu.fireworkssystem.fileprovider", new File(saveFileName));
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(saveFileName)),
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);

    }




}
