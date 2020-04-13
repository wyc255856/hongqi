package com.faw.hongqi.ui;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.faw.hongqi.R;
import com.faw.hongqi.model.VersionUpdateModel;
import com.faw.hongqi.util.NetWorkCallBack;
import com.faw.hongqi.util.PhoneUtil;
import com.google.gson.Gson;

import static com.faw.hongqi.ui.C229MainActivity.goC229MainActivity;

/**
 * welcome页，免责声明页共用
 */
public class C229WelcomeActivity extends BaseActivity {
    public Handler handler = new Handler();

    public int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 1000;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_welcome);
//        goMainActivity();
//        isUpdate();
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

    private void isUpdate(){
        new Thread() {
            @Override
            public void run() {
                PhoneUtil.requestGet("http://www.haoweisys.com/hongqih9_admin/index.php?m=home&c=index&a=get_first_version",new NetWorkCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        final VersionUpdateModel model = new Gson().fromJson((String) data, VersionUpdateModel.class);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                if (!"2".equals(model.getVersion())){
                                    goC229MainActivity(C229WelcomeActivity.this,"update");
                                    finish();
                                }else{
                                    goC229MainActivity(C229WelcomeActivity.this,"Unupdate");
                                }
                            }
                        });
                    }

                    @Override
                    public void onFail(Object error) {

                    }
                });
            }
        }.start();
    }
    private void goMainActivity() {
        new Handler() {
            public void handleMessage(Message msg) {
                Intent intent = new Intent(C229WelcomeActivity.this, C229MainActivity.class);
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
