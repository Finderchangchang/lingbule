package com.xiaohu.fireworkssystem.control.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.xiaohu.fireworkssystem.utils.Utils;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.ResponseBody;

/**
 * Created by Administrator on 2016/9/10.
 */
public class PersonHeaderListener {
    PersonHeaderView view;
    Context context;
    Utils utils;

    public PersonHeaderListener(PersonHeaderView v, Context c) {
        view = v;
        context = c;
    }

    public void getImage(String imgId) {
        utils = new Utils(context);
        OkHttpClient client = new OkHttpClient();
        //获取请求对象
        String url = "http://" + utils.ReadString("IP") + "/Employee/GetHeadImage?HeadImageID=" + imgId;
        Request request = new Request.Builder().url(url).build();
        //获取响应体
        ResponseBody body = null;
        try {
            body = client.newCall(request).execute().body();
            //获取流
            InputStream in = body.byteStream();
            //转化为bitmap
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            view.getImageHeader(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            view.getImageHeader(null);
        }

    }
}
