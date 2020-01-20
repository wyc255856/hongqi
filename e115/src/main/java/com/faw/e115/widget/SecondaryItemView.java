package com.faw.e115.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faw.e115.R;
import com.faw.e115.event.BaseEvent;
import com.faw.e115.event.SecondaryOnclickEvent;
import com.faw.e115.event.SecondaryOnscollerEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class SecondaryItemView extends LinearLayout implements View.OnClickListener {

    TextView title;
    int index;
    int type;
    View second_item_line, second_item_line_n;

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
        second_item_line = findViewById(R.id.second_item_line);
        second_item_line_n = findViewById(R.id.second_item_line_n);
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
        if (event instanceof SecondaryOnclickEvent) {
            SecondaryOnclickEvent secondaryOnclickEvent = (SecondaryOnclickEvent) event;
            if (type == secondaryOnclickEvent.getType()) {
                if (index == secondaryOnclickEvent.getIndex()) {
                    title.setTextAppearance(mContext, R.style.text_32_blue);
                    second_item_line.setVisibility(VISIBLE);
                    second_item_line_n.setVisibility(GONE);
                } else {
                    title.setTextAppearance(mContext, R.style.text_32_white);
                    second_item_line.setVisibility(GONE);
                    second_item_line_n.setVisibility(VISIBLE);
                }
            }

        }
        if (event instanceof SecondaryOnscollerEvent) {
            SecondaryOnscollerEvent secondaryOnclickEvent = (SecondaryOnscollerEvent) event;
            if (type == secondaryOnclickEvent.getType()) {
                if (index == secondaryOnclickEvent.getIndex()) {
                    title.setTextAppearance(mContext, R.style.text_32_blue);
                    second_item_line.setVisibility(VISIBLE);
                    second_item_line_n.setVisibility(GONE);
                } else {
                    title.setTextAppearance(mContext, R.style.text_32_white);
                    second_item_line.setVisibility(GONE);
                    second_item_line_n.setVisibility(VISIBLE);
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
