package com.faw.hongqi.widget;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.faw.hongqi.R;
import com.faw.hongqi.model.BaseModel;
import com.faw.hongqi.util.Constant;

import java.util.ArrayList;
import java.util.List;

public class HomeModelHotPointView extends LinearLayout implements View.OnClickListener {

    FrameLayout view_layout;
//    ImageView point_view_1_point1;

    private Activity mContext;

    public HomeModelHotPointView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public HomeModelHotPointView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.view_home_hot_point,
                this, true);
//        point_view_1 = findViewById(R.id.point_view_1);
//        point_view_1_point1 = findViewById(R.id.point_view_1_point1);
//        Glide.with(mContext).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.RESOURCE).into(point_view_1_point1);
//        Glide.with(mContext).load(R.drawable.c229_point).into(point_view_1_point1);
//        Glide.with(mContext).load(R.drawable.c229_point)
//                .diskCacheStrategy(DiskCacheStrategy.ALL).into(point_view_1_point1);
        view_layout = findViewById(R.id.view_layout);
        view_layout.setVisibility(INVISIBLE);
    }


    @Override
    public void onClick(View v) {

    }


    public void hideAllView() {
        view_layout.setVisibility(GONE);
    }

    public void showHotPointViewByResId(int resID) {
        //index 0:轮胎，1：远光灯，2，后备箱
        view_layout.setVisibility(VISIBLE);
        if (Constant.IS_PHONE) {
            setPhonePointView(resID);
        } else {
            setCarPointView(resID);
        }

    }


    private void setPhonePointView(int resID) {
        if (resID == R.drawable.c229_car_1) {
            view_list.get(0).setPosition(0, 0, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_2) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_3) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_4) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_5) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_6) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_7) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_8) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_9) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_10) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_11) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_12) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_13) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_14) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_15) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_16) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_17) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_18) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_19) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_20) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_21) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_22) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_23) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_24) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_25) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_26) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_27) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_28) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_29) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_30) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_31) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_32) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_33) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_34) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_35) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_36) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        }
    }


    double k;
    private final double WIDTH = 1920;//手机端模型图片宽度常量

    List<BaseModelItem> view_list = new ArrayList<>();

    public void setItem(double width) {
        if (Constant.IS_PHONE) {
            k = width / WIDTH;
        } else {
            k = 0.75;
        }
        BaseModelItem baseModelItem1 = new BaseModelItem(mContext, "1064", mContext.getString(R.string.model_item_text_1), R.mipmap.test_point_view_icon_2);
        BaseModelItem baseModelItem2 = new BaseModelItem(mContext, "906", mContext.getString(R.string.model_item_text_2), R.mipmap.test_point_view_icon_l);
        BaseModelItem baseModelItem3 = new BaseModelItem(mContext, "1152", mContext.getString(R.string.model_item_text_3), R.mipmap.test_point_view_icon_3);
        view_list.add(baseModelItem1);
        view_list.add(baseModelItem2);
        view_list.add(baseModelItem3);
        for (BaseModelItem item : view_list) {
            view_layout.addView(item);
        }
        showHotPointViewByResId(R.drawable.c229_car_1);
    }


    private void setCarPointView(int resID) {
        if (resID == R.drawable.c229_car_1) {
            view_list.get(0).setPosition(150, 130, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_2) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_3) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_4) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_5) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_6) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_7) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_8) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_9) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_10) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_11) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_12) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_13) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_14) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_15) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_16) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_17) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_18) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_19) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_20) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_21) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_22) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_23) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_24) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_25) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_26) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_27) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_28) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_29) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_30) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_31) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_32) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_33) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_34) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_35) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        } else if (resID == R.drawable.c229_car_36) {
            view_list.get(0).setPosition(100, 100, 1, k);
            view_list.get(1).setPosition(100, 200, 2, k);
            view_list.get(2).setPosition(1000, 100, 3, k);
        }
    }


}
