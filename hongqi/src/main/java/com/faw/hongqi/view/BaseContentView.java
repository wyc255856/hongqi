package com.faw.hongqi.view;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.faw.hongqi.model.ContentItemModel;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.Nullable;

public abstract class BaseContentView extends LinearLayout {

    public BaseContentView(Context context) {
        super(context);
    }

    public BaseContentView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public abstract void setContent(ContentItemModel data);

    public Bitmap getBitmap(Context mContext, String fileName) {
        Bitmap bitmap = null;
        AssetManager assetManager = mContext.getAssets();
        try {
            InputStream inputStream = assetManager.open(fileName);//filename是assets目录下的图片名
            bitmap = BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public void setHtmlText(TextView textView, String text) {

        textView.setText(Html.fromHtml(text));
    }

    public void setImage(Context mContext, ImageView imageView, String fileName) {
        Bitmap bitmap = getBitmap(mContext, fileName);
        if (bitmap != null)
            imageView.setImageBitmap(getBitmap(mContext, fileName));
    }
}
