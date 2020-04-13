package com.faw.hongqi.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.model.ContentItemModel;
import com.faw.hongqi.widget.TypesetTextView;

import androidx.annotation.Nullable;

public class TextContentView extends BaseContentView {
    TypesetTextView text_content;
    private Activity mContext;

    public TextContentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public TextContentView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        // TODO Auto-generated method stub
        this.mContext = (Activity) context;
        LayoutInflater.from(context).inflate(R.layout.view_c229_text_content,
                this, true);
        text_content = findViewById(R.id.text_content);
    }

    @Override
    public void setContent(ContentItemModel data) {
        setHtmlText(text_content, data.getContent());
    }
}
