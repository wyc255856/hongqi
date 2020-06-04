package com.faw.hongqi.util;

import android.content.Context;

import com.faw.hongqi.model.HotWord;
import com.faw.hongqi.model.NewsModel_Table;
import com.raizlabs.android.dbflow.sql.language.property.IntProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wyc on 17/3/1.
 */

public class Constant {
    public static String BASE_URL = "http://www.e-guides.faw.cn";//正式环境
//    public static String BASE_URL = "http://www.haoweisys.com";//测试环境

    public final static boolean IS_PHONE = false;//判断包是否是手机应用
    public final static boolean IS_PRO = false;//判断包是生产环境

    public final static boolean DEBUG = true;//是否是调试包
    public final static boolean TEST = true;//是否是不带资源的测试包

    private static Map<String, IntProperty> intPropertyList = new HashMap<>();


    public static void initData() {
        intPropertyList.put("C229_1", NewsModel_Table.sdss);
        intPropertyList.put("C229_2", NewsModel_Table.sdhh);
        intPropertyList.put("C229_3", NewsModel_Table.sdzg);
        intPropertyList.put("C229_4", NewsModel_Table.zdss);

        intPropertyList.put("C229_5", NewsModel_Table.zdhh);
        intPropertyList.put("C229_6", NewsModel_Table.zdzg);
        intPropertyList.put("C229_7", NewsModel_Table.zdqj);
    }

    public static IntProperty getCurrentIntProperty(Context context) {
        String modle=SharedpreferencesUtil.getCarModel(context);
        return intPropertyList.get(modle);
    }


    public static void initHotWord(){
        HotWord hotWord1=new HotWord();
        hotWord1.setWord("爆胎");
        hotWord1.save();

        HotWord hotWord2=new HotWord();
        hotWord2.setWord("雾灯");
        hotWord2.save();

        HotWord hotWord3=new HotWord();
        hotWord3.setWord("安全带");
        hotWord3.save();

        HotWord hotWord4=new HotWord();
        hotWord4.setWord("方向盘");
        hotWord4.save();
    }

}