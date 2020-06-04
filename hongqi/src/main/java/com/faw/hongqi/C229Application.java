package com.faw.hongqi;

import android.app.Application;

import com.faw.hongqi.ui.C229MainActivity;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.SharedpreferencesUtil;
import com.faw.hongqi.util.TypefaceUtil;
import com.faw.hqzl3.hqextendsproxy.HQExtendsProxy;
import com.faw.hqzl3.hqextendsproxy.Interfaces.IExtendsListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.raizlabs.android.dbflow.config.FlowManager;

import org.xutils.x;

public class C229Application extends Application {
    private HQExtendsProxy mHQExtendsProxy;
    private String vehicleCfg = "";
    @Override
    public void onCreate() {
        super.onCreate();
        C229API.init(this);
        TypefaceUtil.replaceSystemDefaultFont(this,"fonts/fzlt.TTF");
        mHQExtendsProxy = HQExtendsProxy.getInstance(getApplicationContext());
        mHQExtendsProxy.initCarApi(new IExtendsListener() {
            @Override
            public void onInitSuccess() {
                //一定要在初始化成功以后再调用一项这些方法
                vehicleCfg = mHQExtendsProxy.getVehicleCfg();//获取车辆高低配，18个字符，已转换为String
                LogUtil.logError("car_model= " + vehicleCfg);
                if (vehicleCfg != null) {
                    if (vehicleCfg.equals("DBCCCBCC0000000000")) {
                        SharedpreferencesUtil.setCarModel(getApplicationContext(), "C229_1");
                    } else if (vehicleCfg.equals("DBCMCCCC0000000000") || vehicleCfg.equals("DBCMCCCC0001000000")
                            || vehicleCfg.equals("DBCMCCCC0002000000") || vehicleCfg.equals("DBCMCCCC0003000000") ||
                            vehicleCfg.equals("DBCMCCCC0004000000") || vehicleCfg.equals("DBCMCCCC0005000000") ||
                            vehicleCfg.equals("DBCMCCCC0006000000") || vehicleCfg.equals("DBCMCCCC0007000000")) {
                        SharedpreferencesUtil.setCarModel(getApplicationContext(), "C229_2");
                    } else if (vehicleCfg.equals("DBCSCCCC0000000000") || vehicleCfg.equals("DBCSCCCC0001000000")
                            || vehicleCfg.equals("DBCSCCCC0002000000") || vehicleCfg.equals("DBCSCCCC0003000000")) {
                        SharedpreferencesUtil.setCarModel(getApplicationContext(), "C229_3");
                    } else if (vehicleCfg.equals("DBCSCDCC0000000000") || vehicleCfg.equals("DBCSCDCC0001000000")
                            || vehicleCfg.equals("DBCSCDCC0002000000") || vehicleCfg.equals("DBCSCDCC0003000000")) {
                        SharedpreferencesUtil.setCarModel(getApplicationContext(), "C229_4");
                    } else if (vehicleCfg.equals("DBCBCDCC0000000000") || vehicleCfg.equals("DBCBCDCC0001000000")) {
                        SharedpreferencesUtil.setCarModel(getApplicationContext(), "C229_5");
                    }
                }

            }


            @Override
            public void onInitFailed() {
                LogUtil.logError("car_model= " + "error");

            }
        });
    }
}
