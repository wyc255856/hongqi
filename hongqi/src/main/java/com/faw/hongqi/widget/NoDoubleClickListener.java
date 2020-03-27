package com.faw.hongqi.widget;

import android.view.View;

import java.util.Calendar;

/**
 * Created by Administrator on 2018/1/24.
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    private static long lastClickTime;
    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if ( time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

}