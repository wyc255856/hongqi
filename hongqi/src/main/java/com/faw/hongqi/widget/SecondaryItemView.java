package com.faw.hongqi.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.faw.hongqi.R;
import com.faw.hongqi.event.BaseEvent;
import com.faw.hongqi.event.SecondaryOnclickEvent;
import com.faw.hongqi.event.SecondaryOnscollerEvent;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.SharedpreferencesUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SecondaryItemView extends LinearLayout implements View.OnClickListener {

    TextView title;
    int index;
    int type;

    private Activity mContext;

    public SecondaryItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public SecondaryItemView(Context context) {
        super(context);
        initView(context);
    }

    @SuppressLint("WrongViewCast")
    private void initView(Context context) {
        // TODO Auto-generated method stub
        EventBus.getDefault().register(this);
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.view_secondary_item,
                this, true);
        title = findViewById(R.id.title);
//        Glide.with(mContext).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(point_view_1_point1);
//        Glide.with(mContext).load(R.drawable.c229_point).into(point_view_1_point1);
//        Glide.with(mContext).load(R.drawable.c229_point)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).into(point_view_1_point1);
        setOnClickListener(this);
    }


    public void setTitle(String text, int index, int type) {
        this.type = type;
        this.index = index;

        title.setText(text);
    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof SecondaryOnclickEvent ) {
            SecondaryOnclickEvent secondaryOnclickEvent = (SecondaryOnclickEvent) event;
            if (type == secondaryOnclickEvent.getType()) {
                if (index == secondaryOnclickEvent.getIndex()) {
                    title.setTextAppearance(mContext, R.style.text_28_blue);
                } else {
                    title.setTextAppearance(mContext, R.style.text_28_white);
                }
            }

        }
        if(event instanceof SecondaryOnscollerEvent){
            SecondaryOnscollerEvent secondaryOnclickEvent = (SecondaryOnscollerEvent) event;
            if (type == secondaryOnclickEvent.getType()) {
                if (index == secondaryOnclickEvent.getIndex()) {
                    title.setTextAppearance(mContext, R.style.text_28_blue);
                } else {
                    title.setTextAppearance(mContext, R.style.text_28_white);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        EventBus.getDefault().post(new SecondaryOnclickEvent(type, index));
    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


}
