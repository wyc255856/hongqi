package com.faw.hongqi.adaptar;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.faw.hongqi.R;
import com.faw.hongqi.holder.RvHolder;
import com.faw.hongqi.model.RightBean;
import com.faw.hongqi.util.FileUtil;
import com.faw.hongqi.util.GlideRoundTransform;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.widget.RvListener;

import java.io.File;
import java.util.List;


public class ClassifyDetailAdapter extends RvAdapter<RightBean> {

    public ClassifyDetailAdapter(Context context, List<RightBean> list, RvListener listener) {
        super(context, list, listener);
    }


    @Override
    protected int getLayoutId(int viewType) {
        return viewType == 0 ? R.layout.item_title : R.layout.item_classify_detail;
    }

    @Override
    public int getItemViewType(int position) {
        return list.get(position).isTitle() ? 0 : 1;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new ClassifyHolder(view, viewType, listener);
    }

    public class ClassifyHolder extends RvHolder<RightBean> {
        TextView tvCity;
        ImageView avatar;
        TextView tvTitle;

        public ClassifyHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            switch (type) {
                case 0:
                    tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
                    break;
                case 1:
                    tvCity = (TextView) itemView.findViewById(R.id.tvCity);
                    avatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
                    break;
            }

        }

        @Override
        public void bindHolder(RightBean sortBean, int position) {
            int itemViewType = ClassifyDetailAdapter.this.getItemViewType(position);
            switch (itemViewType) {
                case 0:
                    tvTitle.setText(sortBean.getTopTitle());
                    break;
                case 1:
                    tvCity.setText(sortBean.getTitle());
                    if (!TextUtils.isEmpty(sortBean.getHead_image())) {
                        String url = FileUtil.getResPath() + sortBean.getHead_image().replace("HONGQIH9/standard/","");
                        LogUtil.logError("image url = " + url);
                        File file = new File(FileUtil.getResPath() + sortBean.getHead_image().replace("HONGQIH9/standard/",""));
                        Glide.with(mContext)
                                .load(Uri.fromFile(file)).apply(new RequestOptions()
                                .transform(new GlideRoundTransform(mContext, 10))).into(avatar);
                    } else {
                        String url = "images/2019-04-26/5cc2b440a0ab1.png";
                        File file = new File(url);
                        Glide.with(mContext)
                                .load(Uri.fromFile(file)).apply(new RequestOptions()
                                .transform(new GlideRoundTransform(mContext, 10))).into(avatar);
                    }
                    break;
            }

        }
    }
}
