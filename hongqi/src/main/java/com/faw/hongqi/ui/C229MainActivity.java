package com.faw.hongqi.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import com.faw.hongqi.R;
import com.faw.hongqi.fragment.BaseFragment;
import com.faw.hongqi.model.VersionModel;
import com.faw.hongqi.model.VersionUpdateModel;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.FragmentUtil;
import com.faw.hongqi.util.LoadAndUnzipUtil;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.MyBroadcastReceiver;
import com.faw.hongqi.util.NetWorkCallBack;
import com.faw.hongqi.util.PhoneUtil;
import com.faw.hongqi.util.SharedpreferencesUtil;
import com.faw.hongqi.widget.TabView;
import com.faw.hqzl3.datagatherproxy.HQDataGatherProxy;
import com.google.gson.Gson;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.json.JSONObject;

public class C229MainActivity extends BaseActivity {
    private BaseFragment currentFragment;
    private String currentTag;
    TabView tabView;
    View main_layout;
    private VersionModel bean = null;
    PullToRefreshRecyclerView pullToRefreshRecyclerView;

    @Override
    protected void initData() {
        LogUtil.logError("activity onCreat");
        requestWritePermission();

        deleteDir(new File(FileUtil.getDownloadResPath() + File.separator + "imagesnew" + "/news.json"));
        deleteDir(new File(FileUtil.getDownloadResPath() + File.separator + "imagesnew" + "/category.json"));
//        deleteDir(new File(FileUtil.getDownloadResPath() + File.separator + "imagesnew"));

        deleteFile();
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
        LogUtil.logError("data = " + "删除成功");
    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_c229_main);
        tabView = findViewById(R.id.tab_view);
        tabView.setTabOnClickListener(new TabView.TabOnClickListener() {
            @Override
            public void onTabClickCallBack(String tag) {
                changeTabs(tag);
                Constant.TAG_TOP = tag;
                Map<String,Object> mapCommondata = new LinkedHashMap<>();
                JSONObject gatherObject;
                if ("0".equals(tag)){
                    mapCommondata.put("tabname","车型概览");
                    gatherObject = new JSONObject(mapCommondata);
                    HQDataGatherProxy.getInstance(C229MainActivity.this).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250001",gatherObject.toString());
                }else if ("1".equals(tag)){
                    mapCommondata.put("tabname","快速入门");
                    gatherObject = new JSONObject(mapCommondata);
                    HQDataGatherProxy.getInstance(C229MainActivity.this).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250002",gatherObject.toString());
                }else if ("2".equals(tag)){
                    mapCommondata.put("tabname","车型亮点");
                    gatherObject = new JSONObject(mapCommondata);
                    HQDataGatherProxy.getInstance(C229MainActivity.this).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250003",gatherObject.toString());
                }else if ("3".equals(tag)){
                    mapCommondata.put("tabname","手册");
                    gatherObject = new JSONObject(mapCommondata);
                    HQDataGatherProxy.getInstance(C229MainActivity.this).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250004",gatherObject.toString());
                }else if ("4".equals(tag)){
                    mapCommondata.put("tabname","搜索");
                    gatherObject = new JSONObject(mapCommondata);
                    HQDataGatherProxy.getInstance(C229MainActivity.this).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250005",gatherObject.toString());
                }

            }

        });
        main_layout = findViewById(R.id.main_layout);
        changeTabs("0");
//        setBreoadcast();
    }

    @Override
    protected void initWidgetActions() {
        findViewById(R.id.main_back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.anim_fade_in,
                        R.anim.anim_fade_out);
            }
        });
    }

    @Override
    boolean isHasTitle() {
        return true;
    }

    public void changeTabs(String tag) {
        if ("0".equals(tag)) {
            main_layout.setBackgroundResource(R.mipmap.c229_model_bg);
        } else {
            main_layout.setBackgroundResource(R.mipmap.theme1_main_bg);
        }
        FragmentManager fm = this.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if (currentFragment != null && !currentTag.equals(tag)) {
            BaseFragment subViewFragment = null;
            if (fm.findFragmentByTag(tag) != null) {
                subViewFragment = (BaseFragment) fm
                        .findFragmentByTag(tag);
                ft.hide(currentFragment).show(subViewFragment)
                        .commitAllowingStateLoss();
                currentTag = tag;
                currentFragment = subViewFragment;
            } else {
                subViewFragment = FragmentUtil.getFragment(Integer.valueOf(tag));

                if (subViewFragment != null) {
                    ft.hide(currentFragment)
                            .add(R.id.container, subViewFragment, tag)
                            .commitAllowingStateLoss();
                    currentTag = tag;
                    currentFragment = subViewFragment;
                }
            }
        } else if (currentFragment == null) {
            if (currentTag != null && currentTag.equals(tag)) {

            } else {
                BaseFragment subViewFragment = FragmentUtil.getFragment(Integer.valueOf(tag));

                if (subViewFragment != null) {
                    ft.replace(R.id.container, subViewFragment, tag);
                    ft.commit();// 提交
                    currentTag = tag;
                    currentFragment = subViewFragment;
                }
            }
        }

    }


    private static final int WRITE_PERMISSION = 0x01;

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (requestCode == WRITE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Log.d("tag", "Write Permission Failed");
                Toast.makeText(this, "You must allow permission write external storage to your mobile device.", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void requestWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, WRITE_PERMISSION);
            }
        }
    }

    private void setBreoadcast() {
        BroadcastReceiver receiver=new MyBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.faw.hqzl3.tspservice.change.environment");
        registerReceiver(receiver, filter);
    }

    public static void goC229MainActivity(Context context, String tag) {
        Intent intent = new Intent(context, C229MainActivity.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    public void deleteFile() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                VersionUpdateModel model = (VersionUpdateModel) getIntent().getSerializableExtra("model");
//                SharedpreferencesUtil.setVersionCode(C229MainActivity.this, "121");
                final String id = SharedpreferencesUtil.getVersionCode(C229MainActivity.this).replace(".0", "");
                final String url = Constant.BASE_URL+"/hongqih9_admin/index.php?m=home&c=index&a=get_new_info&version_no=" + id;
                LogUtil.logError("url = " + url);
                PhoneUtil.requestGet(url, new NetWorkCallBack() {
                    @Override
                    public void onSuccess(Object data) {
                        LogUtil.logError("data = " + data);
                        bean = new Gson().fromJson((String) data, VersionModel.class);
                        if ("".equals(bean.getVersion())) {
                            //如果相同版本无需更新
                        } else {
//                            LoadAndUnzipUtil.unzip_size = bean.getZip_address().size();
//                            for (int i = 0; i < bean.getZip_address().size(); i++) {
//                              LoadAndUnzipUtil.startDownload(C229MainActivity.this, bean.getZip_address().get(i),bean.getVersion());
//                            }
                            //判断静默更新是否是json，还是图片视频资源更新
                            LogUtil.logError("资源数组 = " + bean.getZip_address().size());
                            if (bean.getZip_address().get(0).equals("")){
                                LoadAndUnzipUtil.startDownloadNews(C229MainActivity.this, bean.getNews(), bean.getVersion());
                                LoadAndUnzipUtil.startDownloadCategory(C229MainActivity.this, bean.getCategory());
                                LogUtil.logError("资源数组 = " + "w");
                            }else{
                                //队列
                                LogUtil.logError("资源数组 = " + "T");
                                LoadAndUnzipUtil.start_multi(C229MainActivity.this, bean.getZip_address(), bean.getVersion());
                                LoadAndUnzipUtil.startDownloadNewsJson(C229MainActivity.this, bean.getNews(), bean.getVersion());
                                LoadAndUnzipUtil.startDownloadCategoryJson(C229MainActivity.this, bean.getCategory());
                            }
                        }
                    }

                    @Override
                    public void onFail(Object error) {
                        LogUtil.logError("error======" + error.toString());
                    }
                });
            }
        }.start();
    }


    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.logError("activity onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.logError("activity onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.logError("activity onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.logError("activity onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.logError("activity onDestroy");
    }
}
