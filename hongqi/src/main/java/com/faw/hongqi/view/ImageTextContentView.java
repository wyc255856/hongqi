package com.faw.hongqi.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.model.ContentItemModel;

import androidx.annotation.Nullable;

public class ImageTextContentView extends BaseContentView {
    TextView text_content;
    ImageView image_content;
    private Activity mContext;

    public ImageTextContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ImageTextContentView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.view_c229_imagetext_content,
                this, true);
        image_content = findViewById(R.id.image_content);
        text_content = findViewById(R.id.text_content);
    }


    public void setContent(ContentItemModel data) {
        setImage(mContext, image_content, data.getImage());
        setHtmlText(text_content, data.getContent());
    }
}
