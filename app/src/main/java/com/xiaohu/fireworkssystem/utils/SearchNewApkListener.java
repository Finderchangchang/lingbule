package com.xiaohu.fireworkssystem.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.google.gson.Gson;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.control.CheckAPKView;
import com.xiaohu.fireworkssystem.http.OkHttpUtils;
import com.xiaohu.fireworkssystem.http.ReqCallBack;
import com.xiaohu.fireworkssystem.model.ResultModel;
import com.xiaohu.fireworkssystem.model.view.APKVersionsModel;

import static com.xiaohu.fireworkssystem.config.MyKeys.KEY_TOKEN;

/**
 * 作者：zz on 2016/7/3 16:31
 */
public class SearchNewApkListener {
    private Context mContext;
    private Utils utils;
    private String versionStr;
    CheckAPKView view;

    public SearchNewApkListener(Context context, CheckAPKView v) {
        view = v;
        this.mContext = context;
    }

    //外部接口让主Activity调用
    public void checkApkUrl() {
        utils = new Utils(mContext);
        //ip地址
        String stringIP = utils.ReadString("IP");
        //版本号

        String str = "'{\"versions\":\"" + getVersion() + "\",\"file\":\""+mContext.getResources().getString(R.string.updateaddress)+"\"}'";
        OkHttpUtils okHttpUtils = new OkHttpUtils(utils.ReadString(utils.ReadString(KEY_TOKEN)));
        okHttpUtils.requestGetByAsyn(stringIP, "GetAPKGetAPKVersions", str, new ReqCallBack() {
            @Override
            public void onReqSuccess(String result) {
                Gson gson = new Gson();
                ResultModel model = gson.fromJson(result, ResultModel.class);

                if (model.isSuccess()) {
                    APKVersionsModel apkVersionsModel = gson.fromJson(model.getData(), APKVersionsModel.class);
                    versionStr = apkVersionsModel.getVersions();
                       view.checkVersion(versionStr);
                } else {
                    view.checkVersion("");
                }
            }

            @Override
            public void onReqFailed(String errorMsg) {
                view.checkVersion("");
            }
        });
    }


    /**
     * 获取版本号
     *
     * @return 当前应用的版本号
     */
    public String getVersion() {
        try {
            PackageManager manager = mContext.getPackageManager();
            PackageInfo info = manager.getPackageInfo(mContext.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return "获取版本号失败";
        }
    }
}
