package com.faw.hongqi.widget;

import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.faw.hongqi.fragment.BrightSpotFragment;

public class C229NativeInterface {

    @JavascriptInterface
    public void JsTest(final String id){
        BrightSpotFragment.context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(BrightSpotFragment.context,id,Toast.LENGTH_LONG).show();
            }
        });
    }
}
