package com.faw.e115;

import android.content.Context;
import android.content.Intent;

import com.faw.e115.ui.E115MainActivity;
import com.liulishuo.filedownloader.FileDownloader;
import com.raizlabs.android.dbflow.config.FlowManager;

public class E115API {
    public static void init(Context context) {
        FlowManager.init(context);
        FileDownloader.setup(context);
//        FlowManager.initModule(e115GeneratedDatabaseHolder.class);
    }

    public static void openManual(Context context) {
        context.startActivity(new Intent(context, E115MainActivity.class));

    }
}
