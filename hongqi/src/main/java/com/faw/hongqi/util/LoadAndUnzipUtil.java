package com.faw.hongqi.util;

import android.app.Activity;
import android.util.Log;

import com.faw.hongqi.ui.C229LoadAndUnzipFileActivity;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LoadAndUnzipUtil {
    static BaseDownloadTask singleTask;
    public static int singleTaskId = 0;
    private static String saveZipFilePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder";
    private static String TAG = LoadAndUnzipUtil.class.getSimpleName();
    private static String fileName;
    public static void startDownload(final Activity content,String downloadUrl) {
        singleTask = FileDownloader.getImpl().create(downloadUrl)
                .setPath(saveZipFilePath, true)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                    }
                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.e(TAG, "----->progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes
                                + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
                        super.progress(task, soFarBytes, totalBytes);
                    }
                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e(TAG, "----------->blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() +
                                ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        fileName = task.getFilename();
                        content.runOnUiThread(new Runnable() {
                            public void run() {
                                //下载完成
                                unZipFile(new File(saveZipFilePath + File.separator + "images.zip"), saveZipFilePath);
                            }
                        });
                        super.blockComplete(task);
                    }
                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.e(TAG, "---------->completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                        super.completed(task);
                    }
                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }
                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e(TAG, "--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        super.error(task, e);
                    }
                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
        singleTaskId = singleTask.start();
    }
    /**
     * zipFile 压缩文件
     * folderPath 解压后的文件路径
     */
    private static void unZipFile(File zipFile, String folderPath) {
        try {
            ZipFile zfile = new ZipFile(zipFile);
            Enumeration zList = zfile.entries();
            ZipEntry ze = null;
            long count = 0;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();
                if (ze.isDirectory()) {
                    String dirstr = folderPath + File.separator + ze.getName();
                    dirstr = new String(dirstr.getBytes("8859_1"), "GB2312");
                    File f = new File(dirstr);
                    boolean mdir = f.mkdir();
                    continue;
                }
                OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
                Log.e(TAG, "---->getRealFileName(folderPath,ze.getName()): " + getRealFileName(folderPath, ze.getName()).getPath() + "  name:" + getRealFileName(folderPath, ze.getName()).getName());
                InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
                int readLen = 0;
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                    count += readLen;
                }
                is.close();
                os.close();
            }
            zfile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //判断是否有未解压的zip包
//        SharedpreferencesUtil.setIsUnzip(C229LoadAndUnzipFileActivity.this, "true");
//        SharedpreferencesUtil.setVersionCode(C229LoadAndUnzipFileActivity.this, "code");
        //解压完成之后删除压缩包
        deleteDir(zipFile);
        //将下载下来的文件统一复制到另一个文件夹
        copyFolder(saveZipFilePathOld, saveZipFilePathNew);
    }

    private static String saveZipFilePathOld = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder" + File.separator + "images";
    private static String saveZipFilePathNew = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder" + File.separator + "NewFile"+ File.separator + "images";

    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        File ret = new File(baseDir);
        String substr = null;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                substr = dirs[i];
                try {
                    substr = new String(substr.getBytes("8859_1"), "GB2312");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ret = new File(ret, substr);
            }
            if (!ret.exists()) {
                ret.mkdirs();
            }
            substr = dirs[dirs.length - 1];
            try {
                substr = new String(substr.getBytes("8859_1"), "GB2312");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            ret = new File(ret, substr);
            return ret;
        }
        return ret;
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

    public static void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" +
                            (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
