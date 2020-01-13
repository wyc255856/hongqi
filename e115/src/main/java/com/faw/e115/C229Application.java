package com.faw.e115;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;
import com.raizlabs.android.dbflow.config.FlowManager;

public class C229Application extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FlowManager.init(this);
        FileDownloader.setup(this);
    }
}
