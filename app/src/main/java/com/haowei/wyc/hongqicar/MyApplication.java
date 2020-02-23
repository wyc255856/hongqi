package com.haowei.wyc.hongqicar;

import android.app.Application;

//import com.faw.e115.E115API;
import com.faw.hongqi.C229API;
import com.raizlabs.android.dbflow.config.FlowManager;
//import com.raizlabs.android.dbflow.config.e115GeneratedDatabaseHolder;
import com.raizlabs.android.dbflow.config.hongqiGeneratedDatabaseHolder;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        C229API.init(this);
//        E115API.init(this);
//        FlowManager.initModule(e115GeneratedDatabaseHolder.class);
        FlowManager.initModule(hongqiGeneratedDatabaseHolder.class);
    }
}
