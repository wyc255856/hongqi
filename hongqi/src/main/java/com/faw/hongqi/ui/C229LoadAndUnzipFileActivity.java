package com.faw.hongqi.ui;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.faw.hongqi.R;
import com.faw.hongqi.model.VersionModel;
import com.faw.hongqi.util.PhoneUtil;
import com.faw.hongqi.util.SharedpreferencesUtil;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class C229LoadAndUnzipFileActivity extends BaseActivity {

    private String TAG = getClass().getSimpleName();
    private LinearLayout ll_is_no_wifi;
    private LinearLayout ll_is_download;
    private LinearLayout ll_is_wifi;
    private FrameLayout fl_download;
    private RelativeLayout rl_is_wifi_yes;
    private RelativeLayout rl_is_wifi_no;
    private TextView tv_download_title;
    private ProgressBar progress_bar;
    private boolean isNextDownload = true;
    private VersionModel data;
    private String tag;
    @Override
    protected void initData() {
        data = (VersionModel) getIntent().getSerializableExtra("data");
        tag = getIntent().getStringExtra("tag");
        //如果是增量更新隐藏更新UI
        if ("gone".equals(tag)){
            fl_download.setVisibility(View.GONE);
        }else{
            fl_download.setVisibility(View.VISIBLE);
        }
        //增量更新资源包
        for (int i = 0;i < data.getZip_address().size(); i++){

        }
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_load_and_unzip_file);
        fl_download = findViewById(R.id.fl_download);
        ll_is_no_wifi = findViewById(R.id.ll_is_no_wifi);
        tv_download_title = findViewById(R.id.tv_download_title);
        rl_is_wifi_yes = findViewById(R.id.rl_is_wifi_yes);
        ll_is_download = findViewById(R.id.ll_is_download);
        ll_is_wifi = findViewById(R.id.ll_is_wifi);
        rl_is_wifi_no = findViewById(R.id.rl_is_wifi_no);
        progress_bar = findViewById(R.id.progress_bar);
        ll_is_no_wifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload();
            }
        });
        rl_is_wifi_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ll_is_wifi.setVisibility(View.GONE);
                ll_is_no_wifi.setVisibility(View.GONE);
                ll_is_download.setVisibility(View.VISIBLE);
                isNextDownload = false;
                startDownload();
            }
        });
        rl_is_wifi_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isNextDownload = true;
                finish();//不下载是否退出
            }
        });
        if (PhoneUtil.isWifi(C229LoadAndUnzipFileActivity.this)) {
            startDownload();
        } else {
            ll_is_wifi.setVisibility(View.VISIBLE);
            ll_is_no_wifi.setVisibility(View.GONE);
            ll_is_download.setVisibility(View.GONE);
        }
    }
    @Override
    protected void initWidgetActions() {}
    @Override
    boolean isHasTitle() {return false;}

    BaseDownloadTask singleTask;
    public int singleTaskId = 0;
    private String downloadUrl = "https://www.haoweisys.com/A6/11.zip";
    private String saveZipFilePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder";

    //下载下来的文件名称
    private String fileName;
    private void startDownload() {
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
                        runOnUiThread(new Runnable() {
                            public void run() {
                                ll_is_wifi.setVisibility(View.GONE);
                                ll_is_no_wifi.setVisibility(View.GONE);
                                ll_is_download.setVisibility(View.VISIBLE);
                                if (isNextDownload) {
                                    if (PhoneUtil.isWifi(C229LoadAndUnzipFileActivity.this)) {
                                        startDownload();
                                    } else {
                                        ll_is_wifi.setVisibility(View.VISIBLE);
                                        ll_is_no_wifi.setVisibility(View.GONE);
                                        ll_is_download.setVisibility(View.GONE);
                                    }
                                }
                                tv_download_title.setText(C229LoadAndUnzipFileActivity.this.getResources().getString(R.string.download_assest_text_pack));
                            }
                        });
                        progress_bar.setProgress((int) ((soFarBytes * 1.0 / totalBytes) * 100));
                    }
                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        Log.e(TAG, "----------->blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() +
                                ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        fileName = task.getFilename();
                        runOnUiThread(new Runnable() {
                            public void run() {
                                tv_download_title.setText(C229LoadAndUnzipFileActivity.this.getResources().getString(R.string.download_assest_text_unzip));
                                Toast.makeText(C229LoadAndUnzipFileActivity.this, "下载完成", Toast.LENGTH_LONG).show();
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
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(C229LoadAndUnzipFileActivity.this, "暂停", Toast.LENGTH_LONG).show();
                            }
                        });
                        super.paused(task, soFarBytes, totalBytes);
                    }
                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e(TAG, "--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(C229LoadAndUnzipFileActivity.this, "没有网络", Toast.LENGTH_LONG).show();
                                ll_is_download.setVisibility(View.GONE);
                                ll_is_no_wifi.setVisibility(View.VISIBLE);
                            }
                        });
                        super.error(task, e);
                    }
                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        continueDownLoad(task);//如果存在了相同的任务，那么就继续下载
                    }
                });
        singleTaskId = singleTask.start();
    }
    private void continueDownLoad(BaseDownloadTask task) {
        while (task.getSmallFileSoFarBytes() != task.getSmallFileTotalBytes()) {
            int percent = (int) ((double) task.getSmallFileSoFarBytes() / (double) task.getSmallFileTotalBytes() * 100);
        }
    }
    public static void goC229LoadAndUnzipFileActivity(Context context, VersionModel model,String tag) {
            Intent intent = new Intent(context, C229LoadAndUnzipFileActivity.class);
            intent.putExtra("data", model);
            intent.putExtra("tag", tag);
            context.startActivity(intent);
    }
    /**
     * zipFile 压缩文件
     * folderPath 解压后的文件路径
     */
    private void unZipFile(File zipFile, String folderPath) {
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
        SharedpreferencesUtil.setIsUnzip(C229LoadAndUnzipFileActivity.this, "true");
        SharedpreferencesUtil.setVersionCode(C229LoadAndUnzipFileActivity.this, "code");
        //解压完成之后删除压缩包
        deleteDir(zipFile);
        //将下载下来的文件统一复制到另一个文件夹
        copyFolder(saveZipFilePathOld, saveZipFilePathNew);
        progress_bar.setProgress(110);
    }
    private String saveZipFilePathNew = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder" + File.separator + "NewFile"+ File.separator + "images";
    private String saveZipFilePathOld = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder" + File.separator + "images";
    /**
     * 根据保存zip的文件路径和zip文件相对路径名，返回一个实际的文件，因为zip文件解压后，里边可能是多重文件结构
     */
    public File getRealFileName(String baseDir, String absFileName) {
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

    public void deleteDir(File file) {
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

    public void copyFolder(String oldPath, String newPath) {
        try {
            (new File(newPath)).mkdirs(); //如果文件夹不存在 则建立新文件夹
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
        finish();
    }

}
