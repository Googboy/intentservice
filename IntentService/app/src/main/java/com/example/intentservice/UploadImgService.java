package com.example.intentservice;

import android.content.Intent;
import android.util.Log;

/**
 * Created by 潘硕 on 2017/10/16.
 */

class UploadImgService extends android.app.IntentService {
    public static final String EXTRA_IMG_PATH = "com.zhy.blogcodes.intentservice.extra.IMG_PATH" ;
    public static final String ACTION_UPLOAD_IMG = "com.zhy.blogcodes.intentservice.action.UPLOAD_IMAGE";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadImgService(String name) {
        super(name);
    }

    public static void startUploadImg(IntentService intentService, String path) {
        Intent intent = new Intent(intentService,UploadImgService.class);
        intent.setAction(ACTION_UPLOAD_IMG);
        intent.putExtra(EXTRA_IMG_PATH,path);
        intentService.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null){
            final String action = intent.getAction();
            if (ACTION_UPLOAD_IMG.equals(action)){
                final String path = intent.getStringExtra(EXTRA_IMG_PATH);
                handleUploadImg(path);
            }
        }
    }

    private void handleUploadImg(String path) {
        try {
            Thread.sleep(3000);
            Intent intent = new Intent(IntentService.UPLOAD_RESULT);
            intent.putExtra(EXTRA_IMG_PATH,path);
            sendBroadcast(intent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG","onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG","onDestroy");
    }
}
