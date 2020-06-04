package com.faw.hongqi.ui;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.faw.hongqi.R;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.widget.C229NativeInterface;

public class C229InteractionGameActivity extends BaseActivity {
    WebView webView;

    @Override
    protected void initData() {
        setContentView(R.layout.activity_c229_game);

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

    @Override
    protected void initViews() {

        webView = findViewById(R.id.web_view);
        webView.getSettings().setAllowFileAccess(true);
        webView.setBackgroundColor(0);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError
                    error) {
                super.onReceivedError(view, request, error);
            }


            @Override

            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webView.loadUrl("javascript:itemLoaderHide()");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtil.logError("url = " + url);
                if (url != null) {
//                    if (!url.contains("mp4")) {
//                        LogUtil.logError("url = vr");
//                        webView.setLayerType(View.LAYER_TYPE_NONE, null);
//                    } else {
//                        LogUtil.logError("url = LAYER_TYPE_HARDWARE");
//                        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
//                    }
                }
                if (Build.VERSION.SDK_INT < 26) {
                    view.loadUrl(url);
                    return true;
                }
                LogUtil.logError("url = " + url);
                return false;
            }
        });
        //支持App内部javascript交互
        webView.getSettings().setJavaScriptEnabled(true);
        //自适应屏幕
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置可以支持缩放
        webView.getSettings().setSupportZoom(true);
        //扩大比例的缩放
        webView.getSettings().setUseWideViewPort(true);
        //设置是否出现缩放工具
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setLayerType(View.LAYER_TYPE_NONE, null);
        webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            webView.getSettings().setMediaPlaybackRequiresUserGesture(false);
        }


        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAllowContentAccess(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.addJavascriptInterface(new C229NativeInterface(), "Android");
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        String appCachePath = getApplicationContext().getCacheDir().getAbsolutePath();
        webView.getSettings().setAppCachePath(appCachePath);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setAppCacheEnabled(true);
        loadUrl();
    }

    private void loadUrl() {
        webView.loadUrl("file:///android_asset/web_mobile/index.html");
    }
}
