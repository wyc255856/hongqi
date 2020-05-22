package com.faw.hongqi.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.faw.hongqi.R;
import com.faw.hongqi.model.ContentItemModel;

import androidx.annotation.Nullable;

public class ImageContentView extends BaseContentView {

    TextView text_content;
    SubsamplingScaleImageView image_content;
//    ImageView image_content;

    private Activity mContext;

    public ImageContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ImageContentView(Context context) {
        super(context);
        initView(context);
    }

    @SuppressLint("WrongViewCast")
    private void initView(Context context) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.view_c229_image_content,
                this, true);
        image_content = findViewById(R.id.image_content);
    }


    @Override
    public void setContent(ContentItemModel data) {
        setLongImage(mContext, image_content, data.getImage());
    }
}
