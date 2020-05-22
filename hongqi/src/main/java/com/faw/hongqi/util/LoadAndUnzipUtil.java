package com.faw.hongqi.util;

import android.app.Activity;
import android.os.Environment;
import android.util.Log;

import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.ui.C229LoadAndUnzipFileActivity;
import com.faw.hongqi.ui.C229MainActivity;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloadQueueSet;
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
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class LoadAndUnzipUtil {
    static BaseDownloadTask singleTask;
    private static FileDownloadListener downloadListener;
    // 多任务下载
    public static int singleTaskId = 0;
    public static int unzip_size = 0;
    public static int unzip_size_index = 0;
    private static String saveZipFilePath = FileUtil.getDownloadResPath() + File.separator + "imagesnew";
    private static String TAG = LoadAndUnzipUtil.class.getSimpleName();
//    private String mSaveFolder =saveZipFilePath = FileUtil.getDownloadResPath() + File.separator + "imagesnew";
    private static String fileName;
    private Activity mContext;
    public static void startDownload(final Activity context, String downloadUrl,final String code) {
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
                        LogUtil.logError("----->progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes
                                + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
                        super.progress(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        LogUtil.logError("----------->blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() +
                                ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        fileName = task.getFilename();
//                        context.runOnUiThread(new Runnable() {
//                            public void run() {
                        //下载完成
//                                if (fileIsExists(FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
//                                        + File.separator + "MyFolder"+File.separator +"zy_news.json")){
//                                if (true){
//                                    DBUtil.initData(context);
//                                }
                        unZipFile(new File(saveZipFilePath + File.separator + fileName), saveZipFilePath,context,code);
//                            }
//                        });
                        super.blockComplete(task);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.logError("---------->completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                        super.completed(task);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.logError("--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        super.error(task, e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
        singleTaskId = singleTask.start();
    }

    public static void startDownloadNews(final Activity context, String downloadUrl, final String code) {
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
                        LogUtil.logError("----->progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes
                                + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
                        super.progress(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        LogUtil.logError("----------->blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() +
                                ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        fileName = task.getFilename();
//                        context.runOnUiThread(new Runnable() {
//                            public void run() {
                        //下载完成
                        SharedpreferencesUtil.setVersionCode(context, code);
                        DBUtil.initDataNet(context, "news");
//                                if (fileIsExists(FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
//                                        + File.separator + "MyFolder"+File.separator +"zy_news.json")){
//                            }
//                        });
                        super.blockComplete(task);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.logError("---------->completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                        super.completed(task);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        e.printStackTrace();
                        LogUtil.logError("--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        super.error(task, e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
        singleTaskId = singleTask.start();

    }

    public static void startDownloadCategory(final Activity context, String downloadUrl) {
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
                        LogUtil.logError("----->progress taskId:" + task.getId() + ",soFarBytes:" + soFarBytes + ",totalBytes:" + totalBytes
                                + ",percent:" + soFarBytes * 1.0 / totalBytes + ",speed:" + task.getSpeed());
                        super.progress(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void blockComplete(BaseDownloadTask task) {
                        LogUtil.logError("----------->blockComplete taskId:" + task.getId() + ",filePath:" + task.getPath() +
                                ",fileName:" + task.getFilename() + ",speed:" + task.getSpeed() + ",isReuse:" + task.reuse());
                        fileName = task.getFilename();
//                        context.runOnUiThread(new Runnable() {
//                            public void run() {
                        //下载完成
                        DBUtil.initDataNet(context, "category");
//                                if (fileIsExists(FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
//                                        + File.separator + "MyFolder"+File.separator +"zy_news.json")){
//                            }
//                        });
                        super.blockComplete(task);
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        LogUtil.logError("---------->completed taskId:" + task.getId() + ",isReuse:" + task.reuse());
                        super.completed(task);
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        LogUtil.logError("--------->error taskId:" + task.getId() + ",e:" + e.getLocalizedMessage());
                        super.error(task, e);
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                    }
                });
        singleTaskId = singleTask.start();
//        DBUtil.initData(context);
    }

    /**
     * zipFile 解压文件
     * folderPath 解压后的文件路径
     */
    private static void unZipFile(File zipFile, String folderPath,Activity context,String code) {
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
                LogUtil.logError("---->getRealFileName(folderPath,ze.getName()): " + getRealFileName(folderPath, ze.getName()).getPath() + "  name:" + getRealFileName(folderPath, ze.getName()).getName());
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
            LogUtil.logError("---->解压失败" + e.getLocalizedMessage());
        }

        LogUtil.logError("unzip_size_index = " + unzip_size_index);
        LogUtil.logError("unzip_size = " + unzip_size);
        copyFolder(saveZipFilePathOld, saveZipFilePathNew);
        deleteDir(zipFile);
        SharedpreferencesUtil.setVersionCode(context, code);
    }

    private static String saveZipFilePathOld = FileUtil.getDownloadResPath() + File.separator + "HONGQIH9" + File.separator + "standard" + File.separator + "images";
    private static String saveZipFilePathNew = FileUtil.getDownloadResPath() + File.separator + "images";

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
                    LogUtil.logError("正在复制文件temp = " + temp + "到 新路径 " + newPath + "/" +
                            (temp.getName()).toString());
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
                    LogUtil.logError("正在创建目录 = " + newPath + "/" + file[i]);
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            }
            deleteDir(new File(FileUtil.getDownloadResPath() + File.separator + "HONGQIH9"));
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.logError("copy异常 = " + e.getLocalizedMessage());
        }
    }

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

    /**
     * 删除目录及目录下的文件
     *
     * @param filePath 要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String filePath) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!filePath.endsWith(File.separator))
            filePath = filePath + File.separator;
        File dirFile = new File(filePath);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
//            Toast.makeText(getApplicationContext(), "删除目录失败：" + filePath + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (File file : files) {
            // 删除子文件
            if (file.isFile()) {
                flag = deleteSingleFile(file.getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (file.isDirectory()) {
                flag = deleteDirectory(file
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
//            Toast.makeText(getApplicationContext(), "删除目录失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
            LogUtil.logError("Copy_Delete.deleteDirectory: 删除目录" + filePath + "成功！");
            return true;
        } else {
//            Toast.makeText(getApplicationContext(), "删除目录：" + filePath + "失败！", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath$Name 要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    private static boolean deleteSingleFile(String filePath$Name) {
        File file = new File(filePath$Name);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                LogUtil.logError("Copy_Delete.deleteSingleFile: 删除单个文件" + filePath$Name + "成功！");
                return true;
            } else {
//                Toast.makeText(getApplicationContext(), "删除单个文件" + filePath$Name + "失败！", Toast.LENGTH_SHORT).show();
                return false;
            }
        } else {
//            Toast.makeText(getApplicationContext(), "删除单个文件失败：" + filePath$Name + "不存在！", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    private static List<String> fileList = new ArrayList<>();
    private static int index_id = 0;
    private static boolean reuse = false;
    //串行下载
    public static FileDownloadListener createLis(final Activity context,final String code){
        return new FileDownloadSampleListener(){
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }
                LogUtil.logError("pending taskId:"+task.getId()+",fileName:"+task.getFilename()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);

            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }
                LogUtil.logError("progress taskId:"+task.getId()+",fileName:"+task.getFilename()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes+",speed:"+task.getSpeed());
            }

            @Override
            protected void blockComplete(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;

                }
                fileList.add(fileName);
                index_id++;
                 LogUtil.logError("解压filename:"+fileName);
                fileName = task.getFilename();
                unZipFile(new File(saveZipFilePath + File.separator + fileName), saveZipFilePath,context,code);
                reuse = task.reuse();
                LogUtil.logError("blockComplete taskId:"+task.getId()+",filePath:"+task.getPath()+",fileName:"+task.getFilename()+",speed:"+task.getSpeed()+",isReuse:"+task.reuse());
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;
                }

                Log.d("feifei","completed taskId:"+task.getId()+",isReuse:"+task.reuse());
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","paused taskId:"+task.getId()+",soFarBytes:"+soFarBytes+",totalBytes:"+totalBytes+",percent:"+soFarBytes*1.0/totalBytes);
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                if(task.getListener() != downloadListener){
                    return;
                }
                LogUtil.logError("error taskId:"+task.getId()+",e:"+e.getLocalizedMessage());
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                if(task.getListener() != downloadListener){
                    return;
                }
                Log.d("feifei","warn taskId:"+task.getId());
            }
        };
    }

    public static void start_multi(final Activity context, List<String> urlList,final String code){

        downloadListener = createLis(context,code);
        //(1) 创建 FileDownloadQueueSet
        final FileDownloadQueueSet queueSet = new FileDownloadQueueSet(downloadListener);

        //(2) 创建Task 队列
        final List<BaseDownloadTask> tasks = new ArrayList<>();
        for(int i = 0;i < urlList.size();i++){
            if (!"".equals(urlList.get(i))){
                tasks.add(FileDownloader.getImpl().create(urlList.get(i)).setPath(saveZipFilePath,true));
            }
        }


        //(3) 设置参数

        // 每个任务的进度 无回调
        //queueSet.disableCallbackProgressTimes();
        // do not want each task's download progress's callback,we just consider which task will completed.

        queueSet.setCallbackProgressTimes(100);
        queueSet.setCallbackProgressMinInterval(100);
        //失败 重试次数
        queueSet.setAutoRetryTimes(3);

        //避免掉帧
        FileDownloader.enableAvoidDropFrame();

        //(4)串行下载
        queueSet.downloadSequentially(tasks);

        //(5)任务启动
        queueSet.start();
    }

    public void stop_multi(){
        FileDownloader.getImpl().pause(downloadListener);
    }

    public void deleteAllFile(){

        //清除所有的下载任务
        FileDownloader.getImpl().clearAllTaskData();

        //清除所有下载的文件
        int count = 0;
        File file = new File(FileDownloadUtils.getDefaultSaveRootPath());
        do {
            if (!file.exists()) {
                break;
            }

            if (!file.isDirectory()) {
                break;
            }

            File[] files = file.listFiles();

            if (files == null) {
                break;
            }

            for (File file1 : files) {
                count++;
                file1.delete();
            }

        } while (false);
        Log.d("feifei",String.format("Complete delete %d files"+count));
//        Toast.makeText(this,String.format("Complete delete %d files", count), Toast.LENGTH_LONG).show();
    }
}
