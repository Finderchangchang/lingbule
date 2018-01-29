package com.xiaohu.fireworkssystem.ui.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.github.florent37.camerafragment.widgets.RecordButton;
import com.xiaohu.fireworkssystem.R;
import com.xiaohu.fireworkssystem.ocrutils.CameraSurfaceView;


public class CameraPersonActivity extends AppCompatActivity implements CameraSurfaceView.onScan{
    private static int REQUEST_CAMERA_PERMISSIONS = 931 ;
    private static String FRAGMENT_TAG = "camera";
    private ImageView iv;
    private RecordButton record_button;
    private CameraSurfaceView main_cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera_person);


        record_button  = (RecordButton) findViewById(R.id.record_button);
        main_cv = (CameraSurfaceView) findViewById(R.id.main_cv);
        iv = (ImageView) findViewById(R.id.iv);
        record_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record_button.setEnabled(false);
                main_cv.takePicture(CameraPersonActivity.this);
            }
        });

        onAddCameraClicked();
    }

    private void onAddCameraClicked() {
    }

    @Override
    public void get(String url) {
        //返回人员头像
        if (url != null) {
            Intent myintent = new Intent();
            myintent.putExtra("data", url);
            setResult(66, myintent);
        } else {

        }
        finish();
    }


    @Override
    protected void onResume() {
        /**
         * 设置为竖屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        super.onResume();
    }
}
