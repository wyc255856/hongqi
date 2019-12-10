package com.faw.hongqi.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.faw.hongqi.R;
import com.faw.hongqi.event.BaseEvent;
import com.faw.hongqi.event.SecondaryOnclickEvent;
import com.faw.hongqi.event.SecondaryOnscollerEvent;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.ResUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class SecondaryListView extends LinearLayout implements View.OnClickListener {

    View cecondary_seek_bg;
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
        cecondary_seek_bg = findViewById(R.id.cecondary_seek_bg);

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
        if (SecondaryOnclickEvent.FAST == type) {
            cecondary_seek_bg.setBackgroundResource(R.mipmap.fast_list_item_bg_1);
        } else {
            cecondary_seek_bg.setBackgroundResource(R.mipmap.fast_list_item_bg_1);
        }

    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof SecondaryOnclickEvent  ) {
            LogUtil.logError("");
            SecondaryOnclickEvent secondaryOnclickEvent = (SecondaryOnclickEvent) event;
            if (SecondaryOnclickEvent.FAST == secondaryOnclickEvent.getType()) {
                cecondary_seek_bg.setBackgroundResource(ResUtil.getMipmapResId("fast_list_item_bg_" + (secondaryOnclickEvent.getIndex() + 1)));
            } else {

            }
        }
        if(event instanceof SecondaryOnscollerEvent){
            SecondaryOnscollerEvent secondaryOnclickEvent = (SecondaryOnscollerEvent) event;
            if (SecondaryOnclickEvent.FAST == secondaryOnclickEvent.getType()) {
                cecondary_seek_bg.setBackgroundResource(ResUtil.getMipmapResId("fast_list_item_bg_" + (secondaryOnclickEvent.getIndex() + 1)));
            } else {

            }
        }
    }

    public void onDestory() {
        EventBus.getDefault().unregister(this);
        for (int i = 0; i < item_layout.getChildCount(); i++) {
            SecondaryItemView item = (SecondaryItemView) item_layout.getChildAt(i);
            item.onDestroy();
        }
    }

}
