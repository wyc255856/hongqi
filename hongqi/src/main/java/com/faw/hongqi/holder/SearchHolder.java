package com.faw.hongqi.holder;


import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.fragment.SearchFragment;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.ui.C229ContentActivity;
import com.raizlabs.android.dbflow.structure.BaseModel;


public class SearchHolder extends BaseHolder {

    NewsModel bean;
    TextView textView;
    Context mContext;


    public SearchHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.word);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                C229ContentActivity.goContentActivity(mContext, bean);
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
