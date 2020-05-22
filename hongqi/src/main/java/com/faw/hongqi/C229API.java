package com.faw.hongqi;

import android.content.Context;
import android.content.Intent;

import com.faw.hongqi.ui.C229WelcomeActivity;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.TypefaceUtil;
import com.liulishuo.filedownloader.FileDownloader;
import com.raizlabs.android.dbflow.config.FlowManager;

public class C229API {
    public static void init(Context context) {
        FlowManager.init(context);
        FileDownloader.setup(context);
        Constant.initData();
    }

    public static void openManual(Context context) {
        context.startActivity(new Intent(context, C229WelcomeActivity.class));
    }
}
