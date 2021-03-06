package com.faw.hongqi.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileUtil {

    public static String getResPath() {
        if (Constant.IS_PHONE) {
            //TODO 修改成手机的资源地址
            String path = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
                    + File.separator + "MyFolder";
            return path + "/";
        } else {
            String path = Environment.getExternalStorageDirectory().getPath();
            if(Constant.TEST){
                path = "/vendor/mnt/presetdata/manual";
            }else {
                path = Environment.getExternalStorageDirectory().getPath();
            }
            return path + "/";
        }

    }
    public static String getDownloadResPath() {

            String path = Environment.getExternalStorageDirectory().getPath();
            if(Constant.TEST){
                path = "/vendor/mnt/presetdata/manual";
            }else {
                path = Environment.getExternalStorageDirectory().getPath();
            }
            return path ;


    }
    /**
     * 加载本地图片
     *
     * @param url
     * @return
     */
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void deleteDir(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                file.delete();
            }
            for (int index = 0; index < childFiles.length; index++) {
                deleteDir(childFiles[index]);
            }
        }
        file.delete();
    }
}
