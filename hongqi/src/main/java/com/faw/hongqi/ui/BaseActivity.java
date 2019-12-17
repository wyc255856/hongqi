package com.faw.hongqi.ui;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Window;
import android.view.WindowManager;

import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.util.Constant;

import org.xutils.x;

import androidx.fragment.app.FragmentActivity;


public abstract class BaseActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isHasTitle()) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        if (Constant.IS_PHONE) {
            // 隐藏状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } else {
            // 显示状态栏
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        DBUtil.initData(this);
        initData();
        initViews();
        initWidgetActions();
    }

    abstract protected void initData();

    abstract protected void initViews();

    abstract protected void initWidgetActions();

    abstract boolean isHasTitle();

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}
