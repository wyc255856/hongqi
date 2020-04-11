package com.faw.hongqi.adaptar;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.faw.hongqi.R;
import com.faw.hongqi.holder.RvHolder;
import com.faw.hongqi.util.PhoneUtil;
import com.faw.hongqi.widget.RvListener;

import java.util.List;

public class SortAdapter extends RvAdapter<String> {

    private int checkedPosition;

    public void setCheckedPosition(int checkedPosition) {
        this.checkedPosition = checkedPosition;
        notifyDataSetChanged();
    }

    public SortAdapter(Context context, List<String> list, RvListener listener) {
        super(context, list, listener);
    }

    @Override
    protected int getLayoutId(int viewType) {
        return R.layout.item_sort_list;
    }

    @Override
    protected RvHolder getHolder(View view, int viewType) {
        return new SortHolder(view, viewType, listener);
    }

    private class SortHolder extends RvHolder<String> {

        private TextView tvName;
        private ImageView iv_left;
        private View mView;

        SortHolder(View itemView, int type, RvListener listener) {
            super(itemView, type, listener);
            this.mView = itemView;
            tvName = (TextView) itemView.findViewById(R.id.tv_sort);
            iv_left = (ImageView) itemView.findViewById(R.id.iv_left);
        }
        private boolean tag = false;
        @Override
        public void bindHolder(String string, int position) {
            tvName.setText(string);
            if (position == checkedPosition) {
                if (position == 0) {
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) iv_left.getLayoutParams();
                    rl.height = PhoneUtil.dip2px(mContext,120f);
                    tvName.setPadding(0, PhoneUtil.dip2px(mContext, 37f), 0, 0);
                    tvName.setTextAppearance(mContext, R.style.text_28_blue);
                    iv_left.setImageResource(R.mipmap.test_head);
                    tag = false;
                }else if (position == 11){
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) iv_left.getLayoutParams();
                    rl.height = PhoneUtil.dip2px(mContext,175f);
                    tvName.setPadding(0, 0, 0, PhoneUtil.dip2px(mContext, 70f));
                    tvName.setTextAppearance(mContext, R.style.text_28_blue);
                    iv_left.setImageResource(R.mipmap.test_foot_check);
                    tag = true;
                }else {
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) iv_left.getLayoutParams();
                    rl.height = PhoneUtil.dip2px(mContext,83f);
                    tvName.setTextAppearance(mContext, R.style.text_28_blue);
                    tvName.setPadding(0,0,0,0);
                    iv_left.setImageResource(R.mipmap.test_check);
                    tag = false;
                }
            } else if (checkedPosition > position) {
                if (position == 0){
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) iv_left.getLayoutParams();
                    rl.height = PhoneUtil.dip2px(mContext,120f);
                    iv_left.setImageResource(R.mipmap.test_head_check);
                    tvName.setPadding(0, PhoneUtil.dip2px(mContext, 37f), 0, 0);
                    tvName.setTextAppearance(mContext, R.style.text_28_white);
                }else{
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) iv_left.getLayoutParams();
                    rl.height = PhoneUtil.dip2px(mContext,83f);
                    iv_left.setImageResource(R.mipmap.test_check_ed);
                    tvName.setTextAppearance(mContext, R.style.text_28_white);
                    tvName.setPadding(0,0,0,0);
                }
             } else {
                RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) iv_left.getLayoutParams();
                rl.height = PhoneUtil.dip2px(mContext,83f);
                tvName.setTextAppearance(mContext, R.style.text_28_white);
                tvName.setPadding(0,0,0,0);
                iv_left.setImageResource(R.mipmap.test);
            }
            if (!tag) {
                if (position == 11) {
                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) iv_left.getLayoutParams();
                    rl.height = PhoneUtil.dip2px(mContext,175f);
                    tvName.setPadding(0, 0, 0, PhoneUtil.dip2px(mContext, 70f));
                    tvName.setTextAppearance(mContext, R.style.text_28_white);
                    iv_left.setImageResource(R.mipmap.test_foot);
                }
            }
        }
    }
}
