package com.xiaohu.fireworkssystem.http;

import android.util.Log;

import com.xiaohu.fireworkssystem.utils.SSLSocketClient;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/4.
 */
public class OkHttpsUtils {
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("application/json; charset=UTF-8");
    // String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJDb21wYW55SUQiOiIxMzAxMzMwMDAyIiwiQXV0aG9yaXplZFR5cGUiOiJBUFAiLCJFeHBpcmF0aW9uVGltZSI6IjIwMTctMDktMjggMDA6MDA6MDAifQ.h-30WeTD2W9dVKjNxUq2sgnouDo77UY6qZH13hsQjAM";
    String token = "";

    public OkHttpsUtils(String t) {
        token = t;
    }

    public <T> Call requestGetByAsyn(String port, String method, String json, final ReqCallBack callBack) {
        String requestUrl = String.format("https://%s%s", port, method);
        RequestBody body = RequestBody.create(MEDIA_TYPE_MARKDOWN, json);
        Request request = new Request.Builder().url(requestUrl).post(body).build();
        Log.e("Msg"+requestUrl,"");
        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
        OkHttpClient okHttpClient = httpBuilder.readTimeout(60, TimeUnit.SECONDS).writeTimeout(60,TimeUnit.SECONDS).connectTimeout(60,TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                .build();
        //OkHttpClient okHttpClient = new OkHttpClient();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onReqFailed("网络错误"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String str = response.body().string();
                    callBack.onReqSuccess(str);
                } else {
                    callBack.onReqFailed("网络错误"+response.message());
                }
            }
        });
        return call;
    }

}

