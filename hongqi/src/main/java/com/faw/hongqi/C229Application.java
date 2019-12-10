package com.faw.hongqi;

import android.app.Application;

import com.raizlabs.android.dbflow.config.FlowManager;

import org.xutils.x;

public class C229Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
    }
}
