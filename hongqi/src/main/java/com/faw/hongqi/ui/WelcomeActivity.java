package com.faw.hongqi.ui;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;

import com.faw.hongqi.R;


public class WelcomeActivity extends BaseActivity {
    public Handler handler = new Handler();

    public int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1000;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_welcome);
        goMainActivity();

    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initWidgetActions() {

    }

    @Override
    boolean isHasTitle() {
        return false;
    }


    private void goMainActivity() {
        new Handler() {
            public void handleMessage(Message msg) {
                Intent intent = new Intent(WelcomeActivity.this, C229MainActivity.class);
                startActivity(intent);
                finish();
            }

        }.sendEmptyMessageDelayed(0, 2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE)
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.
                goMainActivity();

            } else {

                finish();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
