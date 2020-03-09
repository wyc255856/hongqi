package com.faw.hongqi.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faw.hongqi.R;

public class HotWordItemView extends LinearLayout {
    private Activity mContext;
    private TextView textView;

    public HotWordItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public HotWordItemView(Context context) {
        super(context);
        initView(context);
    }

    @SuppressLint("WrongViewCast")
    private void initView(Context context) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.c229_item_hot_word,
                this, true);
        textView = findViewById(R.id.word);

    }

    public void setText(String text) {
        textView.setText(text);
    }


}
