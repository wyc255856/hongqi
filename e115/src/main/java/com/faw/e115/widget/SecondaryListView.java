package com.faw.e115.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.faw.e115.R;
import com.faw.e115.event.BaseEvent;
import com.faw.e115.event.SecondaryOnclickEvent;
import com.faw.e115.event.SecondaryOnscollerEvent;
import com.faw.e115.model.CategoryModel;
import com.faw.e115.util.LogUtil;
import com.faw.e115.util.ResUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SecondaryListView extends LinearLayout implements View.OnClickListener {

    LinearLayout item_layout;

    private Activity mContext;
    int type;

    public SecondaryListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public SecondaryListView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        EventBus.getDefault().register(this);
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.view_secondary_list,
                this, true);
        item_layout = findViewById(R.id.item_layout);

    }

    @Override
    public void onClick(View v) {

    }

    public void setDataList(List<CategoryModel> list, int type) {
        this.type = type;
        item_layout.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            SecondaryItemView item = new SecondaryItemView(mContext);
            CategoryModel categoryModel = list.get(i);
//            LogUtil.logError("categoryModel = " + categoryModel.getCatname());
//            LogUtil.logError("categoryModelid = " + categoryModel.getParentid());
            item.setTitle(categoryModel.getCatname(), i, type);
            item_layout.addView(item);

        }

    }

    @Subscribe
    public void onEvent(BaseEvent event) {
    }

    public void onDestory() {
        EventBus.getDefault().unregister(this);
        for (int i = 0; i < item_layout.getChildCount(); i++) {
            SecondaryItemView item = (SecondaryItemView) item_layout.getChildAt(i);
            item.onDestroy();
        }
    }

}
