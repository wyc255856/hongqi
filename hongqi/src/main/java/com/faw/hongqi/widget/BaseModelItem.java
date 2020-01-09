package com.faw.hongqi.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.sax.RootElement;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.model.NewsModel_Table;
import com.faw.hongqi.ui.C229ContentActivity;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.PhoneUtil;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class BaseModelItem extends LinearLayout {
    protected Activity mContext;
    protected TextView textView;
    protected ImageView line;

    protected PointView pointView;
    int resID;
    private NewsModel model;
    private void getFastNewsList(int id) {
        DBUtil.getNewsListById(id, new TransactionListener() {
            @Override
            public void onResultReceived(Object result) {

            }

            @Override
            public boolean onReady(BaseTransaction transaction) {
                return false;
            }

            @Override
            public boolean hasResult(BaseTransaction transaction, Object result) {
                List<NewsModel> result1List = new ArrayList<>();
                if (result != null)
                    result1List = (List<NewsModel>) result;
                final List<NewsModel> finalResult1List = result1List;
                mContext.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        C229ContentActivity.goContentActivity(mContext, finalResult1List.get(0));
                    }
                });
                return false;
            }
        });
    }
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

    private void initView(final Context context, String id, String text, int icon) {
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
                getFastNewsList(1152);
            }
        });
    }

    private double k;

    public void setPosition(int x, int y, int style, double k) {
        this.k = k;
        positionX = x;
        positionY = y;
        setStyle(style);
//        if (!Constant.IS_PHONE) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.leftMargin = (int) (x * k);
        layoutParams.topMargin = (int) (y * k);
        setLayoutParams(layoutParams);
//        }
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
                    setPhoneLine(100, 0, style);
                    setPhonePoint(490, 0, style);
                    setPhoneText(20, 20, style);
                } else {
                    setLine(0, 10, style);
                    setPoint(350, 105, style);
                    setText(10, 0, style);
                }
                break;
            case 2:
                if (Constant.IS_PHONE) {
                    setPhoneLine(0, 200, style);
                    setPhonePoint(420, 315, style);
                    setPhoneText(0, 170, style);
//                    setPhoneLine(120, 255, style);
//                    setPhonePoint(420, 315, style);
//                    setPhoneText(0, 170, style);
                } else {
                    setLine(0, 15, style);
                    setPoint(380, 0, style);
                    setText(0, 30, style);
                }
                break;
            case 3:
                if (Constant.IS_PHONE) {
                    setPhoneLine(250, 200, style);
                    setPhonePoint(220, 320, style);
                    setPhoneText(450, 200, style);
                } else {
                    setLine(15, 0, style);
                    setPoint(0, 85, style);
                    setText(130, 0, style);
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
            line.setImageResource(R.mipmap.point_view_line_3);
        } else if (style == 3) {
            line.setImageResource(R.mipmap.point_view_line_1);
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

    double cha;
    int positionX = 0;
    int positionY = 0;

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
//        ViewTreeObserver vto = line.getViewTreeObserver();
//        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void
//            onGlobalLayout() {
//                line.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) line.getLayoutParams();
//                cha = lp.width * (1 - k);
//                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin = (int) (positionX * k - cha);
//                layoutParams.topMargin = (int) (positionY * k - cha);
//                setLayoutParams(layoutParams);
//
//
//            }
//
//        });



        RelativeLayout.LayoutParams lpline = (RelativeLayout.LayoutParams) line.getLayoutParams();
        lpline.leftMargin = (int) (x * k);
        lpline.topMargin = (int) (y * k);
//
//
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        BitmapFactory.decodeResource(getResources(),R.mipmap.point_view_line_1,options);
//
//        //获取图片的宽高
//        int height = options.outHeight;
//        int width = options.outWidth;
//        lpline.width = (int) (width*k);
//        lpline.height = (int) (height*k);
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
        lppoint.leftMargin = (int) (x * k);
        lppoint.topMargin = (int) (y * k);
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
        lptext.leftMargin = (int) (x * k);
        lptext.topMargin = (int) (y * k);
        textView.setLayoutParams(lptext);
    }
}
