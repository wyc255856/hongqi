package com.faw.hongqi.fragment;

import android.annotation.SuppressLint;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faw.hongqi.R;
import com.faw.hongqi.adaptar.ClassifyDetailAdapter;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.model.RightBean;
import com.faw.hongqi.ui.C229ContentActivity;
import com.faw.hongqi.ui.C229PlayVideoActivity;
import com.faw.hongqi.widget.CheckListener;
import com.faw.hongqi.widget.ItemHeaderDecoration;
import com.faw.hongqi.widget.RvListener;
import com.faw.hongqi.widget.SortDetailPresenter;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class SortDetailFragment extends BaseListFragment<SortDetailPresenter, String> implements CheckListener {
    private RecyclerView mRv;
    private ClassifyDetailAdapter mAdapter;
    private GridLayoutManager mManager;
    private List<RightBean> mDatas = new ArrayList<>();
    private ItemHeaderDecoration mDecoration;
    private boolean move = false;
    private int mIndex = 0;
    private CheckListener checkListener;
    List<NewsListModel> newsList = new ArrayList<>();
    List<CategoryModel> list = new ArrayList<>();

    @SuppressLint("ValidFragment")
    public SortDetailFragment() {

    }
    @SuppressLint("ValidFragment")
    public SortDetailFragment(List<NewsListModel > newsList, List<CategoryModel> list) {
        this.newsList = newsList;
        this.list = list;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_sort_detail;
    }

    @Override
    protected void initCustomView(View view) {
        mRv = (RecyclerView) view.findViewById(R.id.rv);

    }

    @Override
    protected void initListener() {
        mRv.addOnScrollListener(new RecyclerViewListener());
    }

    @Override
    protected SortDetailPresenter initPresenter() {
        showRightPage(1);
        mManager = new GridLayoutManager(mContext, 4);
        //通过isTitle的标志来判断是否是title
        mManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mDatas.get(position).isTitle() ? 4 : 1;
            }
        });
        mRv.setLayoutManager(mManager);
        mAdapter = new ClassifyDetailAdapter(mContext, mDatas, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                NewsModel newsModel = new NewsModel();
                RightBean rightBean =mDatas.get(position);
                newsModel.setContent1(rightBean.getContent1());
                newsModel.setContent2(rightBean.getContent2());
                newsModel.setContent3(rightBean.getContent3());
                newsModel.setContent4(rightBean.getContent4());
                newsModel.setContent5(rightBean.getContent5());
                newsModel.setContent6(rightBean.getContent6());
                newsModel.setContent7(rightBean.getContent7());
                newsModel.setContent8(rightBean.getContent8());
                newsModel.setContent9(rightBean.getContent9());
                newsModel.setContent10(rightBean.getContent10());
                newsModel.setVideo1(rightBean.getVideo1());
                newsModel.setVideo2(rightBean.getVideo2());
                newsModel.setVideo3(rightBean.getVideo3());
                newsModel.setVideo4(rightBean.getVideo4());
                newsModel.setVideo5(rightBean.getVideo5());
                newsModel.setVideo6(rightBean.getVideo6());
                newsModel.setVideo7(rightBean.getVideo7());
                newsModel.setVideo8(rightBean.getVideo8());
                newsModel.setVideo9(rightBean.getVideo9());
                newsModel.setVideo10(rightBean.getVideo10());
                newsModel.setImage1(rightBean.getImage1());
                newsModel.setImage2(rightBean.getImage2());
                newsModel.setImage3(rightBean.getImage3());
                newsModel.setImage4(rightBean.getImage4());
                newsModel.setImage5(rightBean.getImage5());
                newsModel.setImage6(rightBean.getImage6());
                newsModel.setImage7(rightBean.getImage7());
                newsModel.setImage8(rightBean.getImage8());
                newsModel.setImage9(rightBean.getImage9());
                newsModel.setImage10(rightBean.getImage10());
                newsModel.setTemplate1(rightBean.getTemplate1());
                newsModel.setTemplate2(rightBean.getTemplate2());
                newsModel.setTemplate3(rightBean.getTemplate3());
                newsModel.setTemplate4(rightBean.getTemplate4());
                newsModel.setTemplate5(rightBean.getTemplate5());
                newsModel.setTemplate6(rightBean.getTemplate6());
                newsModel.setTemplate7(rightBean.getTemplate7());
                newsModel.setTemplate8(rightBean.getTemplate8());
                newsModel.setTemplate9(rightBean.getTemplate9());
                newsModel.setTemplate10(rightBean.getTemplate10());
                newsModel.setTitle(rightBean.getTitle());
                newsModel.setHead_image(rightBean.getHead_image());

                if (mDatas.get(position).getTemplate1() != 6) {
                    if (newsModel.getHead_image()!=null) {
                        C229ContentActivity.goContentActivity(mContext, newsModel);
                    }
                } else {
                    C229PlayVideoActivity.goVideoActivity(mContext,newsModel);
                }
            }
        });

        mRv.setAdapter(mAdapter);
        mDecoration = new ItemHeaderDecoration(mContext, mDatas);
        mRv.addItemDecoration(mDecoration);
        mDecoration.setCheckListener(checkListener);
        initData();
        return new SortDetailPresenter();
    }


    private void initData() {

        for (int i = 0; i < list.size(); i++) {
            RightBean head = new RightBean();
            //头部设置为true
            head.setTitle(true);
            head.setTopTitle(list.get(i).getCatname());
            head.setTag(String.valueOf(i));
            mDatas.add(head);
            List<NewsModel> categoryTwoArray = newsList.get(i).getRECORDS();
            for (int j = 0; j < categoryTwoArray.size(); j++) {
                RightBean body = new RightBean();
                body.setTag(String.valueOf(i));
                body.setImage1(categoryTwoArray.get(j).getImage1());
                body.setImage2(categoryTwoArray.get(j).getImage2());
                body.setImage3(categoryTwoArray.get(j).getImage3());
                body.setImage4(categoryTwoArray.get(j).getImage4());
                body.setImage5(categoryTwoArray.get(j).getImage5());
                body.setImage6(categoryTwoArray.get(j).getImage6());
                body.setImage7(categoryTwoArray.get(j).getImage7());
                body.setImage8(categoryTwoArray.get(j).getImage8());
                body.setImage9(categoryTwoArray.get(j).getImage9());
                body.setImage10(categoryTwoArray.get(j).getImage10());
                body.setContent1(categoryTwoArray.get(j).getContent1());
                body.setContent2(categoryTwoArray.get(j).getContent2());
                body.setContent3(categoryTwoArray.get(j).getContent3());
                body.setContent4(categoryTwoArray.get(j).getContent4());
                body.setContent5(categoryTwoArray.get(j).getContent5());
                body.setContent6(categoryTwoArray.get(j).getContent6());
                body.setContent7(categoryTwoArray.get(j).getContent7());
                body.setContent8(categoryTwoArray.get(j).getContent8());
                body.setContent9(categoryTwoArray.get(j).getContent9());
                body.setContent10(categoryTwoArray.get(j).getContent10());
                body.setVideo1(categoryTwoArray.get(j).getVideo1());
                body.setVideo2(categoryTwoArray.get(j).getVideo2());
                body.setVideo3(categoryTwoArray.get(j).getVideo3());
                body.setVideo4(categoryTwoArray.get(j).getVideo4());
                body.setVideo5(categoryTwoArray.get(j).getVideo5());
                body.setVideo6(categoryTwoArray.get(j).getVideo6());
                body.setVideo7(categoryTwoArray.get(j).getVideo7());
                body.setVideo8(categoryTwoArray.get(j).getVideo8());
                body.setVideo9(categoryTwoArray.get(j).getVideo9());
                body.setVideo10(categoryTwoArray.get(j).getVideo10());
                body.setTemplate1(categoryTwoArray.get(j).getTemplate1());
                body.setTemplate2(categoryTwoArray.get(j).getTemplate2());
                body.setTemplate3(categoryTwoArray.get(j).getTemplate3());
                body.setTemplate4(categoryTwoArray.get(j).getTemplate4());
                body.setTemplate5(categoryTwoArray.get(j).getTemplate5());
                body.setTemplate6(categoryTwoArray.get(j).getTemplate6());
                body.setTemplate7(categoryTwoArray.get(j).getTemplate7());
                body.setTemplate8(categoryTwoArray.get(j).getTemplate8());
                body.setTemplate9(categoryTwoArray.get(j).getTemplate9());
                body.setTemplate10(categoryTwoArray.get(j).getTemplate10());
                body.setHead_image(categoryTwoArray.get(j).getHead_image());
                body.setTitle(categoryTwoArray.get(j).getTitle());
                mDatas.add(body);
            }
        }
        mAdapter.notifyDataSetChanged();
        mDecoration.setData(mDatas);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void refreshView(int code, String data) {

    }

    public void setData(int n) {
        mIndex = n;
        mRv.stopScroll();
        smoothMoveToPosition(n);
    }

    @Override
    protected void getData() {

    }

    public void setListener(CheckListener listener) {
        this.checkListener = listener;
    }

    private void smoothMoveToPosition(int n) {
        int firstItem = mManager.findFirstVisibleItemPosition();
        int lastItem = mManager.findLastVisibleItemPosition();
        Log.d("first--->", String.valueOf(firstItem));
        Log.d("last--->", String.valueOf(lastItem));
        if (n <= firstItem) {
            mRv.scrollToPosition(n);
        } else if (n <= lastItem) {
            Log.d("pos---->", String.valueOf(n) + "VS" + firstItem);
            int top = mRv.getChildAt(n - firstItem).getTop();
            Log.d("top---->", String.valueOf(top));
            mRv.scrollBy(0, top);
        } else {
            mRv.scrollToPosition(n);
            move = true;
        }
    }


    @Override
    public void check(int position, boolean isScroll) {
        checkListener.check(position, isScroll);

    }


    private class RecyclerViewListener extends RecyclerView.OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (move && newState == RecyclerView.SCROLL_STATE_IDLE) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                Log.d("n---->", String.valueOf(n));
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    Log.d("top--->", String.valueOf(top));
                    mRv.smoothScrollBy(0, top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (move) {
                move = false;
                int n = mIndex - mManager.findFirstVisibleItemPosition();
                if (0 <= n && n < mRv.getChildCount()) {
                    int top = mRv.getChildAt(n).getTop();
                    mRv.scrollBy(0, top);
                }
            }
        }
    }


}
