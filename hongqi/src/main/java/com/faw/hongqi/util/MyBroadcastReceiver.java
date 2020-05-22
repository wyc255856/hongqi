package com.faw.hongqi.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtil.logError("--------->" +intent.getStringExtra("Environment"));
//        if ("dev".equals(intent.getStringExtra("Environment"))||"pre".equals(intent.getStringExtra("Environment"))){
//            Constant.BASE_URL = "http://www.haoweisys.com";
//        }else if ("uat".equals(intent.getStringExtra("Environment"))||"pro".equals(intent.getStringExtra("Environment"))){
//            Constant.BASE_URL = "http://www.e-guides.faw.cn/";
//        }else{
//            Constant.BASE_URL = "http://www.e-guides.faw.cn/";
//        }
    }
}
