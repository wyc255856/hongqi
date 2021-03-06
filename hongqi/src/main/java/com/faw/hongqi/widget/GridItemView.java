package com.faw.hongqi.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.faw.hongqi.R;
import com.faw.hongqi.event.BaseEvent;
import com.faw.hongqi.event.SecondaryOnclickEvent;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.ui.C229ContentActivity;
import com.faw.hongqi.ui.C229PlayVideoActivity;
import com.faw.hongqi.ui.C229VideoActivity;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.GlideRoundTransform;
import com.faw.hongqi.util.LogUtil;
import com.raizlabs.android.dbflow.sql.language.Condition;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class GridItemView extends LinearLayout implements View.OnClickListener {

    ImageView imageView;
    TextView title;
    int index;

    private Activity mContext;

    public GridItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public GridItemView(Context context) {
        super(context);

        initView(context);
    }

    @SuppressLint("WrongViewCast")
    private void initView(Context context) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.item_grid,
                this, true);
        imageView = findViewById(R.id.imageView);
        title = findViewById(R.id.title);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NoDoubleClickListener.isFastClick()) {
                    return ;
                }
                if (TextUtils.isEmpty(data.getVideo1())) {
                    C229ContentActivity.goContentActivity(mContext, data);
                } else {
//            mContext.startActivity(new Intent(mContext, C229PlayVideoActivity.class));
                    Intent intent = new Intent("com.haowei.wyc.hongqicar.sound.source");
                    mContext.sendBroadcast(intent);
                    C229PlayVideoActivity.goVideoActivity(mContext,data);
                }
            }
        });
    }

    public NewsModel data;

    public void setData(NewsModel model, int index) {
        data = model;
        data.setStatus(index);
        this.index = index;
        String url = (FileUtil.getResPath() + model.getHead_image()).replace("/HONGQIH9/standard","");
        LogUtil.logError("image url = " + url);
        if (!TextUtils.isEmpty(model.getHead_image())) {

            File file = new File(url);
//            LogUtil.logError("file url = " + file.exists());
            Glide.with(mContext)
                    .load(Uri.fromFile(file)).apply(new RequestOptions()
                    .transform(new GlideRoundTransform(mContext, 10))).into(imageView);
        }
        title.setText(model.getTitle() + "");
    }

    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof SecondaryOnclickEvent) {

        }
    }

    @Override
    public void onClick(View v) {
//        EventBus.getDefault().post(new SecondaryOnclickEvent(SecondaryOnclickEvent.FAST, index));


    }

    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


}
