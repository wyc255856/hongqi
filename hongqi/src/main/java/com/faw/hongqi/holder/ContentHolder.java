package com.faw.hongqi.holder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.model.CategoryListModel;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.widget.GridItemView;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;


public class ContentHolder extends BaseHolder {
    NewsListModel bean;
    LinearLayout layout;
    View itemView;


    public ContentHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
        layout = itemView.findViewById(R.id.layout);
    }

    @Override
    public void upDate(final Context context, BaseModel mode1l, final int position) {
        itemView.setTag(position + "");
        bean = (NewsListModel) mode1l;
        if (layout != null)
            layout.removeAllViews();
        List<NewsModel> allList = bean.getRECORDS();
        int lineCount = Constant.IS_PHONE ? 3 : 4;
        LinearLayout hLayout = null;
        for (int i = 0; i < allList.size(); i++) {
            NewsModel model = allList.get(i);

            if (i % lineCount == 0 || i == 0) {

                hLayout = new LinearLayout(context);
                hLayout.setOrientation(LinearLayout.HORIZONTAL);
            }
            GridItemView starItem = new GridItemView(context);
            starItem.setData(model, i);

            starItem.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            hLayout.addView(starItem);
            if (i % lineCount == lineCount - 1 || i == allList.size() - 1) {
                layout.addView(hLayout);
            }

        }

    }


}
