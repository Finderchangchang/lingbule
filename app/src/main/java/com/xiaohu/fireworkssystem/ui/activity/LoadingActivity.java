package com.xiaohu.fireworkssystem.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.utils.CopyData_File;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by Administrator on 2016/12/28.
 */

public class LoadingActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks{

    @Override
    public void initView() {
        setContentView(R.layout.activity_loading);
        setBrightnessMode(this,0);
        String[] perms = {Manifest.permission.CAMERA, Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.INTERNET,Manifest.permission.WRITE_SETTINGS,Manifest.permission.WRITE_CONTACTS,Manifest.permission.CHANGE_CONFIGURATION,
        Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.BLUETOOTH_ADMIN,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_NETWORK_STATE
                ,Manifest.permission.ACCESS_WIFI_STATE,Manifest.permission.SYSTEM_ALERT_WINDOW
                ,Manifest.permission.BLUETOOTH,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.INSTALL_SHORTCUT};
        if (EasyPermissions.hasPermissions(this, perms)) {
            Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        } else {
            if (utils.ReadString("hasPermissions").equals("")){
                EasyPermissions.requestPermissions(this, "程序需要以下权限",
                        0, perms);
            }else {

            }
        }
        if (Build.VERSION.SDK_INT < 23) {

        }else {
            if (utils.ReadString("hasPermissions").equals("")){
                EasyPermissions.requestPermissions(this, "程序需要以下权限",
                        0, perms);
            }else {
                Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

        }



        if (utils.ReadString("IP").equals("")) {
            utils.WriteString("IP", getResources().getString(R.string.ip));
        }

        CopyData_File cf = new CopyData_File(LoadingActivity.this);
        cf.DoCopy();

    }

    @Override
    public void initEvent() {

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        utils.WriteString("hasPermissions","permissions");
        Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    private void setBrightnessMode(Context context, int mode) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.System.canWrite(context)) {
                    Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
                } else {
                    Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                    intent.setData(Uri.parse("com.xiaohu.fireworkssystem:" + context.getPackageName()));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            } else {
                Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS_MODE, mode);
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
