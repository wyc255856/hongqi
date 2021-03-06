package com.faw.hongqi.holder;


import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.fragment.BrightSpotFragment;
import com.faw.hongqi.fragment.SearchFragment;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.ui.C229ContentActivity;
import com.faw.hongqi.ui.C229PlayVideoActivity;
import com.faw.hongqi.widget.NoDoubleClickListener;
import com.faw.hqzl3.datagatherproxy.HQDataGatherProxy;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class SearchHolder extends BaseHolder {

    NewsModel bean;
    TextView textView;
    Context mContext;


    public SearchHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.word);
//        itemView.setOnClickListener(new {
//            @Override
//            public void onClick(View v) {
//                C229ContentActivity.goContentActivity(mContext, bean);
//            }
//        });
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoDoubleClickListener.isFastClick()) {
                    return ;
                }
                if (bean.getTemplate1() != 6) {
                    C229ContentActivity.goContentActivity(mContext, bean);

                }else {
                    C229PlayVideoActivity.goVideoActivity(mContext,bean);

                }
               // C229ContentActivity.goContentActivity(mContext, bean);
                CategoryModel categoryModel=DBUtil.getInstance().getCatgoryByCatid(bean.getCatid());
                Map<String,Object> mapCommondata = new LinkedHashMap<>();
                JSONObject gatherObject;
                mapCommondata.put("manualcategaryid",bean.getCatid());
                mapCommondata.put("manualcategary",categoryModel.getCatname());
                mapCommondata.put("manualcontent",bean.getTitle());
                mapCommondata.put("manualcontentid",bean.getId());
                mapCommondata.put("source","5");
                gatherObject = new JSONObject(mapCommondata);
                HQDataGatherProxy.getInstance(BrightSpotFragment.context).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250011",gatherObject.toString());

//                DBUtil.getCatgoryByCatid(bean.getCatid(), new TransactionListener() {
//                    @Override
//                    public void onResultReceived(Object result) {
//
//                    }
//
//                    @Override
//                    public boolean onReady(BaseTransaction transaction) {
//                        return false;
//                    }
//
//                    @Override
//                    public boolean hasResult(BaseTransaction transaction, Object result) {
//                        List<CategoryModel> categoryModelList = new ArrayList<>();
//                        categoryModelList= (List<CategoryModel>) result;
//                        CategoryModel categoryModel=   categoryModelList.get(0);
//                        Map<String,Object> mapCommondata = new LinkedHashMap<>();
//                        JSONObject gatherObject;
//                        mapCommondata.put("manualcategaryid",bean.getCatid());
//                        mapCommondata.put("manualcategary",categoryModel.getCatname());
//                        mapCommondata.put("manualcontent",bean.getTitle());
//                        mapCommondata.put("manualcontentid",bean.getId());
//                        mapCommondata.put("source","5");
//                        gatherObject = new JSONObject(mapCommondata);
//                        HQDataGatherProxy.getInstance(BrightSpotFragment.context).sendGatherData(HQDataGatherProxy.TYPE_REALTIME,"20250011",gatherObject.toString());
//                        return false;
//                    }
//                });
            }
        });
    }

    @Override
    public void upDate(final Context context, BaseModel mode1l, final int position) {
        mContext = context;
        bean = (NewsModel) mode1l;
        if (!TextUtils.isEmpty(bean.getTitle())) {
//            textView.setText(bean.getTitle());
            textView.setText(getColorTextByKeywords(bean.getTitle(), context.getResources().getColor(R.color.theme1_text_color_blue), SearchFragment.WORD));
        }

    }

    public static SpannableString getColorTextByKeywords(String text, int colorId, String keyword) {

        SpannableString ss = new SpannableString(text);

        int start = text.indexOf(keyword);

        int end = start + keyword.length();

        if (start != -1 && end != -1) {

            //用颜色标记文本
            ss.setSpan(new ForegroundColorSpan(colorId), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
        return ss;
    }
}
