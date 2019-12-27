package com.faw.hongqi.widget;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.faw.hongqi.fragment.BrightSpotFragment;
import com.faw.hongqi.ui.C229PlayVideoActivity;

public class C229NativeInterface {

    @JavascriptInterface
    public void JsTest(final String id){
        BrightSpotFragment.context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BrightSpotFragment.context.startActivity(new Intent(BrightSpotFragment.context, C229PlayVideoActivity.class));
            }
        });
    }
}
