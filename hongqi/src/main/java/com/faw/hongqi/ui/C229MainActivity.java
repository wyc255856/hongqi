package com.faw.hongqi.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.faw.hongqi.R;
import com.faw.hongqi.fragment.BaseFragment;
import com.faw.hongqi.model.VersionModel;
import com.faw.hongqi.model.VersionUpdateModel;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.FragmentUtil;
import com.faw.hongqi.util.LoadAndUnzipUtil;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.NetWorkCallBack;
import com.faw.hongqi.util.PhoneUtil;
import com.faw.hongqi.util.SharedpreferencesUtil;
import com.faw.hongqi.widget.TabView;
import com.google.gson.Gson;
import com.liulishuo.filedownloader.util.FileDownloadUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.faw.hongqi.ui.C229LoadAndUnzipFileActivity.goC229LoadAndUnzipFileActivity;

public class C229MainActivity extends BaseActivity {
    private BaseFragment currentFragment;
    private String currentTag;
    TabView tabView;
    View main_layout;
    private VersionModel bean = null;

    @Override
    protected void initData() {
        requestWritePermission();
        VersionUpdateModel model = (VersionUpdateModel) getIntent().getSerializableExtra("model");
        if ("update".equals(getIntent().getStringExtra("tag"))) {
            goC229LoadAndUnzipFileActivity(C229MainActivity.this, model);
        } else {
            final String id = SharedpreferencesUtil.getVersionCode(C229MainActivity.this).replace(".0", "");
            //增量更新
            new Thread() {
                @Override
                public void run() {
                    PhoneUtil.requestGet("http://www.haoweisys.com/hongqih9_admin/index.php?m=home&c=index&a=get_new_info&version_no=" + id, new NetWorkCallBack() {
                        @Override
                        public void onSuccess(Object data) {
                            bean = new Gson().fromJson((String) data, VersionModel.class);
                            if ("".equals(bean.getVersion())) {
                                //如果相同版本无需更新
                            } else {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        for (int i = 0; i < bean.getZip_address().size(); i++) {
                                            LoadAndUnzipUtil.startDownload(C229MainActivity.this, bean.getZip_address().get(i));
                                        }
                                        SharedpreferencesUtil.setVersionCode(C229MainActivity.this, bean.getVersion());
                                        //判断路径是否有增量更新文件夹如果有下载json文件，并删除文件夹及内容
                                        LoadAndUnzipUtil.deleteDirectory(FileDownloadUtils.getDefaultSaveRootPath() + File.separator + "horizon"
                                                + File.separator + "HONGQIH9");
                                        LoadAndUnzipUtil.startDownloadNews(C229MainActivity.this, bean.getNews());
                                        LoadAndUnzipUtil.startDownloadCategory(C229MainActivity.this, bean.getCategory());
                                    }
                                });
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

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_c229_main);
        tabView = findViewById(R.id.tab_view);
        tabView.setTabOnClickListener(new TabView.TabOnClickListener() {
            @Override
            public void onTabClickCallBack(String tag) {
                changeTabs(tag);

            }

        });
        main_layout = findViewById(R.id.main_layout);
        changeTabs("0");
    }

    @Override
    protected void initWidgetActions() {
        findViewById(R.id.main_back_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_PERMISSION);
            }
        }
    }

    public static void goC229MainActivity(Context context, String tag) {
        Intent intent = new Intent(context, C229MainActivity.class);
        intent.putExtra("tag", tag);
        context.startActivity(intent);
    }

    //判断文件夹下是否存在该文件
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
}
