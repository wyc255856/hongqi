package com.faw.e115.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.faw.e115.R;
import com.faw.e115.model.ContentItemModel;

public class ImageContentView extends BaseContentView {

    TextView text_content;
    ImageView image_content;

    private Activity mContext;

    public ImageContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ImageContentView(Context context) {
        super(context);
        initView(context);
    }

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
