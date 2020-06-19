package com.faw.hongqi.widget;

import android.content.Intent;
import android.webkit.JavascriptInterface;

import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.fragment.BrightSpotFragment;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.ui.C229ContentActivity;
import com.faw.hongqi.ui.C229InteractionGameActivity;
import com.faw.hongqi.ui.C229MainActivity;
import com.faw.hongqi.ui.C229PlayVideoActivity;
import com.faw.hqzl3.datagatherproxy.HQDataGatherProxy;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class C229NativeInterface {

    @JavascriptInterface
    public void JsTest(final String id) {
        BrightSpotFragment.context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if ("213".equals(id)){
                    Map<String,Object> mapCommondata = new LinkedHashMap<>();
                    JSONObject gatherObject;
                    mapCommondata.put("gogame","自动泊车");
                    gatherObject = new JSONObject(mapCommondata);
                    HQDataGatherProxy.getInstance(BrightSpotFragment.context).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250006",gatherObject.toString());
                    BrightSpotFragment.context.startActivity(new Intent(BrightSpotFragment.context, C229InteractionGameActivity.class));
                }else{
                    getFastNewsList(id);
                }
            }
        });
    }
    private void getFastNewsList(String  id) {
        final NewsModel newsModel=DBUtil.getInstance().getNewsListById(id);
        BrightSpotFragment.context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (newsModel.getTemplate1() != 6) {
                    C229ContentActivity.goContentActivity(BrightSpotFragment.context, newsModel);

                }else {
                    C229PlayVideoActivity.goVideoActivity(BrightSpotFragment.context,newsModel);

                }
              //  C229ContentActivity.goContentActivity(BrightSpotFragment.context, newsModel);
            }
        });
//        DBUtil.getNewsListById(BrightSpotFragment.context,id, new TransactionListener() {
//            @Override
//            public void onResultReceived(Object result) {
//
//            }
//
//            @Override
//            public boolean onReady(BaseTransaction transaction) {
//                return false;
//            }
//
//            @Override
//            public boolean hasResult(BaseTransaction transaction, Object result) {
//                List<NewsModel> result1List = new ArrayList<>();
//                if (result != null)
//                    result1List = (List<NewsModel>) result;
//                final List<NewsModel> finalResult1List = result1List;
//                if (finalResult1List.size() == 0){
//
//                }else {
//                    BrightSpotFragment.context.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            C229ContentActivity.goContentActivity(BrightSpotFragment.context, finalResult1List.get(0));
//                        }
//                    });
//                }
//                return false;
//            }
//        });
    }
}
