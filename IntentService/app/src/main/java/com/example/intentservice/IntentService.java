package com.example.intentservice;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class IntentService extends Activity {
    public static final String UPLOAD_RESULT = "com.zhy.blogcodes.intentservice.UPLOAD_RESULT";
    private LinearLayout TaskContainer;
    private BroadcastReceiver uploadImgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction() == UPLOAD_RESULT){
                String path = intent.getStringExtra(UploadImgService.EXTRA_IMG_PATH);
                handleResult(path);
            }
        }
    };

    private void handleResult(String path) {
        TextView tv = (TextView) TaskContainer.findViewWithTag(path);
        tv.setText(path+"下载成功~~");//没提示这一步是因为没在文件清单里注册下载服务，因为注册的时候提醒错误，不知道为什么不让注册。
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_service);
        TaskContainer = (LinearLayout) findViewById(R.id.container);
        registerReceiver();
    }

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPLOAD_RESULT);
        registerReceiver(uploadImgReceiver,filter);
    }
    int i =0;
    public void addTask(View view){
        String path = "/sdcard/imgs/"+(++i)+".png";
        UploadImgService.startUploadImg(this,path);

        TextView tv = new TextView(this);
        TaskContainer.addView(tv);
        tv.setText(path+"正在下载....");
        tv.setTag(path);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(uploadImgReceiver);
    }
}
