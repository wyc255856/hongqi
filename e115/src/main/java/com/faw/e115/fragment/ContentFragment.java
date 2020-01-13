package com.faw.e115.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.faw.e115.R;
import com.faw.e115.model.ContentItemModel;
import com.faw.e115.view.ImageContentView;
import com.faw.e115.view.ImageTextContentView;
import com.faw.e115.view.TextContentView;
import com.faw.e115.view.VideoContentView;

public class ContentFragment extends BaseFragment {
    ContentItemModel contentItemModel;
    FrameLayout container;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c229_content;
    }

    @Override
    protected void initData() {
        Bundle data = getArguments();
        contentItemModel = (ContentItemModel) data.getSerializable("data");
    }

    @Override
    protected void initView(View view) {
        container = view.findViewById(R.id.container);
        initContent();
    }


    @Override
    protected void initWidgetActions() {

    }

    @Override
    public void refreshData() {

    }

    private void initContent() {
        //0 图文 1 文本  3 图片  6视频
        switch (contentItemModel.getTemplate()) {
            case 0:
                ImageTextContentView imageTextContentView = new ImageTextContentView(mContext);
                imageTextContentView.setContent(contentItemModel);
                container.addView(imageTextContentView);
                break;
            case 1:
                TextContentView textContentView = new TextContentView(mContext);
                textContentView.setContent(contentItemModel);
                container.addView(textContentView);
                break;
            case 3:
                ImageContentView imageContentView = new ImageContentView(mContext);
                imageContentView.setContent(contentItemModel);
                container.addView(imageContentView);
                break;
            case 6:
                VideoContentView videoContentView = new VideoContentView(mContext);
                videoContentView.setContent(contentItemModel);
                container.addView(videoContentView);
                break;
            default:
                break;
        }
    }
}
