package com.faw.e115.ui;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.faw.e115.R;
import com.faw.e115.util.PhoneUtil;
import com.faw.e115.util.SharedpreferencesUtil;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class E115LoadAndUnzipFileActivity extends BaseActivity {
    private String TAG = getClass().getSimpleName();

    private LinearLayout ll_is_no_wifi;
    private LinearLayout ll_is_download;
    private LinearLayout ll_is_wifi;
    private RelativeLayout rl_is_wifi_yes;
    private RelativeLayout rl_is_wifi_no;
    private TextView tv_download_title;
    private ProgressBar progress_bar;

    @Override
    protected void initData() {

    }

    private boolean isNextDownload = true;

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_load_and_unzip_file);
        ll_is_no_wifi = findViewById(R.id.ll_is_no_wifi);
        tv_download_title = findViewById(R.id.tv_download_title);
        rl_is_wifi_yes = findViewById(R.id.rl_is_wifi_yes);
        ll_is_download = findViewById(R.id.ll_is_download);
        ll_is_wifi = findViewById(R.id.ll_is_wifi);
        rl_is_wifi_no = findViewById(R.id.rl_is_wifi_no);
        progress_bar = findViewById(R.id.progress_bar);

//        ll_is_download.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                singleTask.pause();
//            }
//        });

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

        if (PhoneUtil.isWifi(E115LoadAndUnzipFileActivity.this)) {
            startDownload();
        } else {
            ll_is_wifi.setVisibility(View.VISIBLE);
            ll_is_no_wifi.setVisibility(View.GONE);
            ll_is_download.setVisibility(View.GONE);
        }

    }

    @Override
    protected void initWidgetActions() {

    }

    @Override
    boolean isHasTitle() {
        return false;
    }

    private void requestGet() {
        try {
            String baseUrl = "http://www.hi-watch.com.cn/hiwatchclient/getWatchOnlineStatus.htm?deviceid=626160002649032";
            StringBuilder tempParams = new StringBuilder();
            String requestUrl = baseUrl + tempParams.toString();
            // 新建一个URL对象
            URL url = new URL(requestUrl);
            // 打开一个HttpURLConnection连接
            HttpURLConnection urlConn = (HttpURLConnection) url.openConnection();
            // 设置连接主机超时时间
            urlConn.setConnectTimeout(5 * 1000);
            //设置从主机读取数据超时
            urlConn.setReadTimeout(5 * 1000);
            // 设置是否使用缓存  默认是true
            urlConn.setUseCaches(true);
            // 设置为Post请求
            urlConn.setRequestMethod("GET");
            //urlConn设置请求头信息
            //设置请求中的媒体类型信息。
            urlConn.setRequestProperty("Content-Type", "application/json");
            //设置客户端与服务连接类型
            urlConn.addRequestProperty("Connection", "Keep-Alive");
            // 开始连接
            urlConn.connect();
            // 判断请求是否成功
            if (urlConn.getResponseCode() == 200) {
                // 获取返回的数据
                String result = streamToString(urlConn.getInputStream());
            } else {

            }
            // 关闭连接
            urlConn.disconnect();
        } catch (Exception e) {

        }
    }

    /**
     * 将输入流转换成字符串
     *
     * @param is 从网络获取的输入流
     * @return
     */
    public String streamToString(InputStream is) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            is.close();
            byte[] byteArray = baos.toByteArray();
            return new String(byteArray);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 存放当前文件夹下所有图片文件的路径的集合
     **/
    private ArrayList<String> paths = new ArrayList<String>();

    /**
     * 从解压后的文件中获取图片进行展示
     */
    private void getAndShowPicture() {
        paths.clear();

        Map<String, Bitmap> maps = new TreeMap<String, Bitmap>();
        try {
            maps = getImages(saveZipFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        //将获取到的图片放在ImageView中用Viewpager展示
        final List<ImageView> images = new ArrayList<>();
        for (Map.Entry<String, Bitmap> entry : maps.entrySet()) {
            if (null == entry.getValue()) {
                continue;
            }
            ImageView iv = new ImageView(this);
            iv.setImageBitmap(entry.getValue());
            images.add(iv);
        }
    }

    /**
     * 获取指定文件夹下面的所有图片的文件目录和其Bitmap对象
     */
    private Map<String, Bitmap> getImages(String filePath) throws FileNotFoundException {
        File baseFile = new File(filePath);

        Map<String, Bitmap> maps = new TreeMap<String, Bitmap>();
        if (baseFile != null && baseFile.exists()) {
            getFiles(paths, baseFile);
            if (!paths.isEmpty()) {
                for (int i = 0; i < paths.size(); i++) {
                    Bitmap bitmap = BitmapFactory.decodeFile(paths.get(i));
                    maps.put(paths.get(i), bitmap);
                }
            }
        }
        return maps;
    }


    /**
     * 获取图片路径
     */
    private void getFiles(List list, File file) {
        File[] fs = file.listFiles();
        for (File f : fs) {
            if (f.isDirectory()) {
                getFiles(list, f);
            }
            if (f.isFile()) {
                Log.e(TAG, "------------->getFiles  f:" + f.getAbsolutePath());
                if (isImageFile(f.getAbsolutePath())) {
                    list.add(f.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 判断文件是否是图片
     */
    private boolean isImageFile(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (options.outWidth == -1) {
            return false;
        }
        return true;
    }


    /**
     * zipFile 压缩文件
     * folderPath 解压后的文件路径
     */
    private void unZipFile(File zipFile, String folderPath) {
        try {
            ZipFile zfile = new ZipFile(zipFile);
            long ziplength = getZipTrueSize(folderPath);
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
                    int progress = (int) ((count * 100) / zfile.size());
                }
                is.close();
                os.close();
            }
            zfile.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        SharedpreferencesUtil.setIsUnzip(E115LoadAndUnzipFileActivity.this, "true");
        SharedpreferencesUtil.setVersionCode(E115LoadAndUnzipFileActivity.this, "code");
        //解压完成之后删除压缩包
        deleteDir(zipFile);
        copyFolder(saveZipFilePathOld, saveZipFilePathNew);
        progress_bar.setProgress(110);
    }

    public long getZipTrueSize(String filePath) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 根据保存zip的文件路径和zip文件相对路径名，返回一个实际的文件
     * 因为zip文件解压后，里边可能是多重文件结构
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

    /**
     * 删除整个文件夹 或者 文件
     */
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
                if (temp.isDirectory()) {//如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
        } catch (Exception e) {
            System.out.println("复制整个文件夹内容操作出错");
            e.printStackTrace();
        }
        finish();
    }

    /**
     * 判断文件夹下是否存在该文件
     * @param strFile
     * @return
     */
    public boolean fileIsExists(String strFile) {
        try {
            File f = new File(strFile);
            if (!f.exists()) {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

        return true;
    }

    BaseDownloadTask singleTask;
    public int singleTaskId = 0;
    //文件下载地址
    private String downloadUrl = "https://www.haoweisys.com/A6/11.zip";
    //文件保存路径
    private String saveZipFilePath = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder";
    private String saveZipFilePathNew = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder" + File.separator + "NewFile";
    private String saveZipFilePathOld = FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
            + File.separator + "MyFolder" + File.separator + "images" + File.separator + "images";
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
                                    if (PhoneUtil.isWifi(E115LoadAndUnzipFileActivity.this)) {
                                        startDownload();
                                    } else {
                                        ll_is_wifi.setVisibility(View.VISIBLE);
                                        ll_is_no_wifi.setVisibility(View.GONE);
                                        ll_is_download.setVisibility(View.GONE);
                                    }
                                }
                                tv_download_title.setText(E115LoadAndUnzipFileActivity.this.getResources().getString(R.string.download_assest_text_pack));
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
                                tv_download_title.setText(E115LoadAndUnzipFileActivity.this.getResources().getString(R.string.download_assest_text_unzip));
                                Toast.makeText(E115LoadAndUnzipFileActivity.this, "下载完成", Toast.LENGTH_LONG).show();
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
                                Toast.makeText(E115LoadAndUnzipFileActivity.this, "暂停", Toast.LENGTH_LONG).show();

                            }
                        });
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.e(TAG, "--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(E115LoadAndUnzipFileActivity.this, "没有网络", Toast.LENGTH_LONG).show();
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
//            textView.setText("("+percent+"%"+")");
        }
    }
}
