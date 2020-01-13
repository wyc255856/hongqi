package com.faw.e115.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faw.e115.R;
import com.faw.e115.event.HideKeyboardEvent;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by wyc on 16/8/5.
 */
public class TabView extends LinearLayout implements View.OnClickListener {


    private Activity mContext;
    private LinearLayout layout;
    View tab_item_1, tab_item_2, tab_item_3, tab_item_4, tab_item_5;
    TextView tab_text_1, tab_text_2, tab_text_3, tab_text_4, tab_text_5;
    View tab_line_1, tab_line_2, tab_line_3, tab_line_4, tab_line_5;

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public TabView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.tab_view,
                this, true);
        tab_item_1 = findViewById(R.id.tab_item_1);
        tab_item_2 = findViewById(R.id.tab_item_2);
        tab_item_3 = findViewById(R.id.tab_item_3);
        tab_item_4 = findViewById(R.id.tab_item_4);
        tab_item_5 = findViewById(R.id.tab_item_5);
        tab_text_1 = findViewById(R.id.tab_text_1);
        tab_text_2 = findViewById(R.id.tab_text_2);
        tab_text_3 = findViewById(R.id.tab_text_3);
        tab_text_4 = findViewById(R.id.tab_text_4);
        tab_text_5 = findViewById(R.id.tab_text_5);
        tab_line_1 = findViewById(R.id.tab_line_1);
        tab_line_2 = findViewById(R.id.tab_line_2);
        tab_line_3 = findViewById(R.id.tab_line_3);
        tab_line_4 = findViewById(R.id.tab_line_4);
        tab_line_5 = findViewById(R.id.tab_line_5);
        tab_item_1.setOnClickListener(this);
        tab_item_2.setOnClickListener(this);
        tab_item_3.setOnClickListener(this);
        tab_item_4.setOnClickListener(this);
        tab_item_5.setOnClickListener(this);

//                lp= (FrameLayout.LayoutParams) tab_text_1.getLayoutParams();
        tab_text_1.setTextAppearance(mContext, R.style.text_36_white);
        tab_text_2.setTextAppearance(mContext, R.style.text_32_gray);
        tab_text_3.setTextAppearance(mContext, R.style.text_32_gray);
        tab_text_4.setTextAppearance(mContext, R.style.text_32_gray);
        tab_text_5.setTextAppearance(mContext, R.style.text_32_gray);
        tab_line_1.setVisibility(VISIBLE);
        tab_line_2.setVisibility(GONE);
        tab_line_3.setVisibility(GONE);
        tab_line_4.setVisibility(GONE);
        tab_line_5.setVisibility(GONE);
    }


    private TabOnClickListener lisetener;

    public void setTabOnClickListener(TabOnClickListener lisetener) {
        this.lisetener = lisetener;
    }

    public interface TabOnClickListener {
        void onTabClickCallBack(String tag);

    }

    @Override
    public void onClick(View view) {
        if (lisetener != null) {
//            FrameLayout.LayoutParams lp = null;
            EventBus.getDefault().post(new HideKeyboardEvent());
            int tag = 0;
            if (view == tab_item_1) {
                tag = 0;
//                lp= (FrameLayout.LayoutParams) tab_text_1.getLayoutParams();
                tab_text_1.setTextAppearance(mContext, R.style.text_36_white);
                tab_text_2.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_3.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_4.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_5.setTextAppearance(mContext, R.style.text_32_gray);
                tab_line_1.setVisibility(VISIBLE);
                tab_line_2.setVisibility(GONE);
                tab_line_3.setVisibility(GONE);
                tab_line_4.setVisibility(GONE);
                tab_line_5.setVisibility(GONE);
            } else if (view == tab_item_2) {
                tag = 1;
                tab_text_1.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_2.setTextAppearance(mContext, R.style.text_36_white);
                tab_text_3.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_4.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_5.setTextAppearance(mContext, R.style.text_32_gray);
                tab_line_1.setVisibility(GONE);
                tab_line_2.setVisibility(VISIBLE);
                tab_line_3.setVisibility(GONE);
                tab_line_4.setVisibility(GONE);
                tab_line_5.setVisibility(GONE);
            } else if (view == tab_item_3) {
                tag = 2;
                tab_text_1.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_2.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_3.setTextAppearance(mContext, R.style.text_36_white);
                tab_text_4.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_5.setTextAppearance(mContext, R.style.text_32_gray);
                tab_line_1.setVisibility(GONE);
                tab_line_2.setVisibility(GONE);
                tab_line_3.setVisibility(VISIBLE);
                tab_line_4.setVisibility(GONE);
                tab_line_5.setVisibility(GONE);
            } else if (view == tab_item_4) {
                tag = 3;
                tab_text_1.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_2.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_3.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_4.setTextAppearance(mContext, R.style.text_36_white);
                tab_text_5.setTextAppearance(mContext, R.style.text_32_gray);
                tab_line_1.setVisibility(GONE);
                tab_line_2.setVisibility(GONE);
                tab_line_3.setVisibility(GONE);
                tab_line_4.setVisibility(VISIBLE);
                tab_line_5.setVisibility(GONE);
            } else if (view == tab_item_5) {
                tag = 4;
                tab_text_1.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_2.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_3.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_4.setTextAppearance(mContext, R.style.text_32_gray);
                tab_text_5.setTextAppearance(mContext, R.style.text_36_white);
                tab_line_1.setVisibility(GONE);
                tab_line_2.setVisibility(GONE);
                tab_line_3.setVisibility(GONE);
                tab_line_4.setVisibility(GONE);
                tab_line_5.setVisibility(VISIBLE);
            }
            lisetener.onTabClickCallBack(tag + "");
        }


    }


    public void changeStyle(int tag) {

    }

    public void onDestroy() {

    }
}
