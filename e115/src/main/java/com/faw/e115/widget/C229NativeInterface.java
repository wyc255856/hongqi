package com.faw.e115.widget;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.faw.e115.fragment.BrightSpotFragment;
import com.faw.e115.ui.E115PlayVideoActivity;

public class C229NativeInterface {

    @JavascriptInterface
    public void JsTest(final String id){
        BrightSpotFragment.context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                BrightSpotFragment.context.startActivity(new Intent(BrightSpotFragment.context, E115PlayVideoActivity.class));
            }
        });
    }
}
