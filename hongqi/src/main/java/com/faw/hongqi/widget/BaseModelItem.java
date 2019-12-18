package com.faw.hongqi.widget;

import android.app.Activity;
import android.content.Context;
import android.sax.RootElement;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.util.Constant;

import androidx.annotation.Nullable;

public class BaseModelItem extends LinearLayout {
    protected Activity mContext;
    protected TextView textView;
    protected ImageView line;
    protected ImageView icon;
    protected PointView pointView;
    int resID;

    public BaseModelItem(Context context) {
        super(context);
    }

    public BaseModelItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseModelItem(Context context, String id, String text, int icon) {
        super(context);
        initView(context, id, text, icon);
    }

    private void initView(Context context, String id, String text, int icon) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.view_model_item,
                this, true);
        resID = icon;
        textView = findViewById(R.id.text);
        line = findViewById(R.id.line);
        pointView = findViewById(R.id.point);
        if (!TextUtils.isEmpty(text)) {
            textView.setText(text);
        }
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 跳转到内容页
            }
        });
    }

    public void setPosition(int x, int y, int style, double k) {

        setStyle(style);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.leftMargin = (int) (x * k);
        layoutParams.topMargin = (int) (y * k);
        setLayoutParams(layoutParams);
    }

    /**
     * 通过style更改文字、亮点、底线的坐标和样式
     *
     * @param style
     */
    private void setStyle(int style) {

        switch (style) {
            case 1:
                if (Constant.IS_PHONE) {
                    setPhoneLine(50, 200, style);
                    setPhonePoint(450, 300, style);
                    setPhoneText(60, 200, style);
                } else {
                    setLine(50, 200, style);
                    setPoint(400, 295, style);
                    setText(60, 200, style);
                }
                break;
            case 2:
                if (Constant.IS_PHONE) {
                    setPhoneLine(50, 400, style);
                    setPhonePoint(250, 500, style);
                    setPhoneText(60, 400, style);
                } else {
                    setLine(50, 400, style);
                    setPoint(250, 500, style);
                    setText(60, 400, style);
                }
                break;
            case 3:
                if (Constant.IS_PHONE) {
                    setPhoneLine(250, 200, style);
                    setPhonePoint(250, 300, style);
                    setPhoneText(260, 200, style);
                } else {
                    setLine(250, 200, style);
                    setPoint(250, 300, style);
                    setText(260, 200, style);
                }
                break;
        }

    }


    /**
     * 设置车机文字底的横线坐标和样式
     *
     * @param x
     * @param y
     * @param style
     */
    private void setLine(int x, int y, int style) {
        if (style == 1) {
            line.setImageResource(R.mipmap.point_view_line_2);
        } else if (style == 2) {
            line.setImageResource(R.mipmap.point_view_line_2);
        } else if (style == 3) {
            line.setImageResource(R.mipmap.point_view_line_3);
        }
        RelativeLayout.LayoutParams lpline = (RelativeLayout.LayoutParams) line.getLayoutParams();
        lpline.leftMargin = x;
        lpline.topMargin = y;
        line.setLayoutParams(lpline);
    }

    /**
     * 设置车机亮点坐标和样式
     *
     * @param x
     * @param y
     * @param style
     */
    private void setPoint(int x, int y, int style) {

        RelativeLayout.LayoutParams lppoint = (RelativeLayout.LayoutParams) pointView.getLayoutParams();
        lppoint.leftMargin = x;
        lppoint.topMargin = y;
        pointView.setLayoutParams(lppoint);
    }

    /**
     * 设置车机文字的坐标和样式
     *
     * @param x
     * @param y
     * @param style
     */
    private void setText(int x, int y, int style) {
        textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID, null),
                null, null, null);
        textView.setCompoundDrawablePadding(10);
        RelativeLayout.LayoutParams lptext = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        lptext.leftMargin = x;
        lptext.topMargin = y;
        textView.setLayoutParams(lptext);
    }

    /**
     * 设置手机文字底的横线坐标和样式
     *
     * @param x
     * @param y
     * @param style
     */
    private void setPhoneLine(int x, int y, int style) {
        if (style == 1) {
            line.setImageResource(R.mipmap.point_view_line_1);
        } else if (style == 2) {
            line.setImageResource(R.mipmap.point_view_line_2);
        } else if (style == 3) {
            line.setImageResource(R.mipmap.point_view_line_3);
        }
        RelativeLayout.LayoutParams lpline = (RelativeLayout.LayoutParams) line.getLayoutParams();
        lpline.leftMargin = x;
        lpline.topMargin = y;
        line.setLayoutParams(lpline);
    }

    /**
     * 设置手机亮点坐标和样式
     *
     * @param x
     * @param y
     * @param style
     */
    private void setPhonePoint(int x, int y, int style) {
        RelativeLayout.LayoutParams lppoint = (RelativeLayout.LayoutParams) pointView.getLayoutParams();
        lppoint.leftMargin = x;
        lppoint.topMargin = y;
        pointView.setLayoutParams(lppoint);
    }

    /**
     * 设置手机文字的坐标和样式
     *
     * @param x
     * @param y
     * @param style
     */
    private void setPhoneText(int x, int y, int style) {
        textView.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(resID, null),
                null, null, null);
        textView.setCompoundDrawablePadding(10);
        RelativeLayout.LayoutParams lptext = (RelativeLayout.LayoutParams) textView.getLayoutParams();
        lptext.leftMargin = x;
        lptext.topMargin = y;
        textView.setLayoutParams(lptext);
    }
}
