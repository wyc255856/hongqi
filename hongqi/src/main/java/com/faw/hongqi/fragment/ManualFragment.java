package com.faw.hongqi.fragment;

import android.app.Activity;
import android.view.View;

import com.faw.hongqi.R;
import com.faw.hongqi.adaptar.PtrrvAdapter;
import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.event.BaseEvent;
import com.faw.hongqi.event.SecondaryOnclickEvent;
import com.faw.hongqi.event.SecondaryOnscollerEvent;
import com.faw.hongqi.holder.ContentHolder;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.PhoneUtil;
import com.faw.hongqi.widget.SecondaryListView;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ManualFragment extends BaseFragment {

    List<CategoryModel> list = new ArrayList<>();
    SecondaryListView secondaryListView;
    RecyclerView recyclerView;
    public PtrrvAdapter mAdapter;
    List<NewsListModel> newsList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c229_mauna;
    }
    @Override
    protected void initData() {
        EventBus.getDefault().register(this);

        DBUtil.getManuaCategoryList(new TransactionListener() {
            @Override
            public void onResultReceived(Object result) {

            }

            @Override
            public boolean onReady(BaseTransaction transaction) {
                return false;
            }

            @Override
            public boolean hasResult(BaseTransaction transaction, Object result) {
                if (result != null)
                    list = (List<CategoryModel>) result;
                LogUtil.logError("list size = " + list.size());
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initList();
                    }
                });

                return false;
            }
        });

    }

    @Override
    protected void initView(View view) {
        secondaryListView = view.findViewById(R.id.secondary_list_view);
        recyclerView = view.findViewById(R.id.ptrrv);
        //        recyclerView.removeHeader();
//        recyclerView.setSwipeEnable(true);
        mAdapter = new PtrrvAdapter(mContext, R.layout.item_list, ContentHolder.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
//        recyclerView.onFinishLoading(true, false);
        recyclerView.setAdapter(mAdapter);


        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(@NonNull View view) {
                int index = Integer.valueOf((String) view.getTag());
                LogUtil.logError("onChildViewAttachedToWindow index = " + index);
            }

            @Override
            public void onChildViewDetachedFromWindow(@NonNull View view) {
                int index = Integer.valueOf((String) view.getTag());
                LogUtil.logError("onChildViewDetachedFromWindow index = " + index);
            }
        });
        recyclerView.setOnFlingListener(new RecyclerView.OnFlingListener() {
            @Override
            public boolean onFling(int velocityX, int velocityY) {
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LogUtil.logError("onScrollStateChanged newState = " + newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

//                if (isBanScorller) {
//
//                } else {
                dyCount += dy;
                LogUtil.logError("onScrolled dyCount = " + dyCount);
                refreshMenueState();
                if (recyclerView.canScrollVertically(1)) {
                } else {
                    //滑动到底部
                    EventBus.getDefault().post(new SecondaryOnscollerEvent(SecondaryOnclickEvent.MANUAL, list.size() - 1));
                }
                if (recyclerView.canScrollVertically(-1)) {
                } else {
                    //滑动到顶部
                    EventBus.getDefault().post(new SecondaryOnscollerEvent(SecondaryOnclickEvent.MANUAL, 0));
                }
//                }


            }
        });
    }

    int dyCount = 0;

    @Override
    protected void initWidgetActions() {

    }

    @Override
    public void refreshData() {

    }

    //遍历二级级目录的index
    int newIndex = 0;
    //记录查询所需时间的起始时间
    long startTime = 0;

    private void initList() {
        secondaryListView.setDataList(list, SecondaryOnclickEvent.MANUAL);
        startTime = System.currentTimeMillis();
        getFastNewsList();

    }

    //是否禁止监听scoller事件
    private boolean isBanScorller = false;


    private int oldScollerDistance = 0;

    /**
     * 处理左边一级目录点击事件
     *
     * @param event
     */
    @Subscribe
    public void onEvent(BaseEvent event) {
        if (event instanceof SecondaryOnclickEvent) {
            SecondaryOnclickEvent secondaryOnclickEvent = (SecondaryOnclickEvent) event;
            if (SecondaryOnclickEvent.MANUAL == secondaryOnclickEvent.getType()) {
//                Toast.makeText(mContext, "点击了" + secondaryOnclickEvent.getIndex(), Toast.LENGTH_SHORT).show();
                LogUtil.logError("点击了 = " + +secondaryOnclickEvent.getIndex());
                //禁止监听scroller事件500毫秒
                isBanScorller = true;
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        try {
                            sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        isBanScorller = false;
                    }
                }.start();

//                if (secondaryOnclickEvent.getIndex() == 0) {
//////                    recyclerView.scrollTo(0, 0);
////                    recyclerView.scrollBy(0, 0 - oldScollerDistance);
////                    oldScollerDistance = 0;
////                } else {
////                    int scrollerDistance = scrollerUpIndexs.get(secondaryOnclickEvent.getIndex() - 1);
////                    LogUtil.logError("滑动距离 = " + scrollerDistance);
////                    if (oldScollerDistance != scrollerDistance) {
////                        recyclerView.scrollBy(0, scrollerDistance - oldScollerDistance);
////                        oldScollerDistance = scrollerDistance;
////                    }
//////                    recyclerView.scrollTo(0, scrollerUpIndexs.get(secondaryOnclickEvent.getIndex()-1));
////                }
                if (secondaryOnclickEvent.getIndex() == 0) {
                    dyCount = 0;
                } else {
                    dyCount = scrollerUpIndexs.get(secondaryOnclickEvent.getIndex() - 1);
                }

                recyclerView.scrollToPosition(secondaryOnclickEvent.getIndex());

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        secondaryListView.onDestory();
    }

    /**
     * 获取二级目录
     */
    private void getFastNewsList() {

        CategoryModel categoryModel = list.get(newIndex);
        DBUtil.getNewsListByCatId(categoryModel.getCaid(), new TransactionListener() {
            @Override
            public void onResultReceived(Object result) {

            }

            @Override
            public boolean onReady(BaseTransaction transaction) {
                return false;
            }

            @Override
            public boolean hasResult(BaseTransaction transaction, Object result) {
                List<NewsModel> result1List = new ArrayList<>();
                if (result != null)
                    result1List = (List<NewsModel>) result;
                LogUtil.logError("news list size = " + result1List.size());
                NewsListModel newsListModel = new NewsListModel();
                newsListModel.setRECORDS(result1List);
                newsList.add(newsListModel);
                newIndex++;
                if (newIndex < list.size()) {
                    getFastNewsList();
                } else {
                    ((Activity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            onDone();
                        }
                    });

                }
                return false;
            }
        });


    }

    //记录每段内容的所在y轴坐标
    private List<Integer> scrollerUpIndexs = new ArrayList<>();

    //当结束通过一级标签遍历二级目录
    private void onDone() {
        LogUtil.logError("查询栏目数据耗时" + (System.currentTimeMillis() - startTime) + "毫秒");
        LogUtil.logError("查询栏目数据长度 = " + newsList.size());
        mAdapter.refreshData(newsList);
        int downIndex = 0;
//        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            NewsListModel newsListModel = newsList.get(i);
            int newsSize = newsListModel.getRECORDS().size();
            int lineCount = Constant.IS_PHONE ? 4 : 6;
            int lineNum = 0;
            if (newsSize % lineCount == 0) {
                lineNum = newsSize / lineCount;
            } else {
                lineNum = (newsSize / lineCount) + 1;
            }
            downIndex += lineNum * PhoneUtil.dip2px(mContext, 191f);
            LogUtil.logError("downIndex = " + downIndex);
            scrollerUpIndexs.add(downIndex);
        }


    }

    //记录上一次滑动切换的标记
    int oldIndex = 0;

    /**
     * 更新左侧item选中状态
     */
    public void refreshMenueState() {
        //当前中线的滑动距离
        int scrollerLengh = dyCount + PhoneUtil.dip2px(mContext, 191f);
        int index = 0;
        for (int i = 0; i < scrollerUpIndexs.size(); i++) {
            if (i + 1 != scrollerUpIndexs.size()) {
                //判断当前中线滑动距离在哪段内容的范围内
                if (scrollerLengh < scrollerUpIndexs.get(i + 1) && scrollerLengh > scrollerUpIndexs.get(i)) {
                    index = i + 1;
                    break;
                }
            }
        }
        //判断是否和上一次切换的标记一样 避免重复调用SecondaryOnscollerEvent事件
        if (oldIndex != index) {
            if (!isBanScorller) {

                EventBus.getDefault().post(new SecondaryOnscollerEvent(SecondaryOnclickEvent.MANUAL, index));
            }


            if (index == 0) {
//                    recyclerView.scrollTo(0, 0);
                oldScollerDistance = 0;
            } else {
                int scrollerDistance = scrollerUpIndexs.get(index - 1);
                if (oldScollerDistance != scrollerDistance) {
                    LogUtil.logError("滑动距离 = " + scrollerDistance);
                    oldScollerDistance = scrollerDistance;
                }
//                    recyclerView.scrollTo(0, scrollerUpIndexs.get(secondaryOnclickEvent.getIndex()-1));
            }
            oldIndex = index;
        }

    }

}
