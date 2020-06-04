package com.faw.hongqi.fragment;

import android.app.Activity;
import android.os.Build;

import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faw.hongqi.R;
import com.faw.hongqi.adaptar.SortAdapter;
import com.faw.hongqi.dbutil.DBUtil;
import com.faw.hongqi.event.BaseEvent;
import com.faw.hongqi.event.SecondaryOnclickEvent;
import com.faw.hongqi.model.CategoryModel;
import com.faw.hongqi.model.NewsListModel;
import com.faw.hongqi.model.NewsModel;
import com.faw.hongqi.util.Constant;
import com.faw.hongqi.util.LogUtil;
import com.faw.hongqi.util.PhoneUtil;
import com.faw.hongqi.widget.CheckListener;
import com.faw.hongqi.widget.ItemHeaderDecoration;
import com.faw.hongqi.widget.RvListener;
import com.raizlabs.android.dbflow.runtime.transaction.BaseTransaction;
import com.raizlabs.android.dbflow.runtime.transaction.TransactionListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class ManualFragment extends BaseFragment implements CheckListener {

    List<CategoryModel> list = new ArrayList<>();
    List<NewsListModel> newsList = new ArrayList<>();
    /////
    private RecyclerView rvSort;
    private SortAdapter mSortAdapter;
    private SortDetailFragment mSortDetailFragment;
    private LinearLayoutManager mLinearLayoutManager;
    private int targetPosition;//点击左边某一个具体的item的位置
    private boolean isMoved;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c229_mauna;
    }

    public void createFragment() {
        if (getActivity().getSupportFragmentManager() != null) {
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            mSortDetailFragment = new SortDetailFragment(newsList, list);
            mSortDetailFragment.setListener(this);
            fragmentTransaction.add(R.id.lin_fragment, mSortDetailFragment);
            fragmentTransaction.commit();
        }
    }

    private void setChecked(int position, boolean isLeft) {
        Log.d("p-------->", String.valueOf(position));
        if (isLeft) {
            mSortAdapter.setCheckedPosition(position);
            //此处的位置需要根据每个分类的集合来进行计算
            int count = 0;
            for (int i = 0; i < position; i++) {
                count += newsList.get(i).getRECORDS().size();
            }
            count += position;
            mSortDetailFragment.setData(count);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(targetPosition));//凡是点击左边，将左边点击的位置作为当前的tag
        } else {
            if (isMoved) {
                isMoved = false;
            } else
                mSortAdapter.setCheckedPosition(position);
            ItemHeaderDecoration.setCurrentTag(String.valueOf(position));//如果是滑动右边联动左边，则按照右边传过来的位置作为tag

        }
        moveToCenter(position);


    }
    @Override
    public void check(int position, boolean isScroll) {
        setChecked(position, isScroll);
    }

    //将当前选中的item居中
    private void moveToCenter(int position) {
        //将点击的position转换为当前屏幕上可见的item的位置以便于计算距离顶部的高度，从而进行移动居中
        View childAt = rvSort.getChildAt(position - mLinearLayoutManager.findFirstVisibleItemPosition());
        if (childAt != null) {
            int y = (childAt.getTop() - (rvSort.getHeight() / 2)+PhoneUtil.dip2px(getActivity(),60f));
            rvSort.smoothScrollBy(0, y);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
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
    private void initData1() {
        List<String> lists = new ArrayList<>();
        //初始化左侧列表数据
        for (int i = 0; i < list.size(); i++) {
            lists.add(list.get(i).getCatname());
        }
        mSortAdapter = new SortAdapter(mContext, lists, new RvListener() {
            @Override
            public void onItemClick(int id, int position) {
                if (mSortDetailFragment != null) {
                    isMoved = true;
                    targetPosition = position;
                    setChecked(position, true);
                }
            }
        });
        rvSort.setAdapter(mSortAdapter);
        createFragment();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView(View view) {
        rvSort = view.findViewById(R.id.rv_sort);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        rvSort.setLayoutManager(mLinearLayoutManager);
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
        list = list.subList(0, 12);
//        secondaryListView.setDataList(list, SecondaryOnclickEvent.MANUAL);
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
                if (secondaryOnclickEvent.getIndex() == 0) {
                    dyCount = 0;
                } else {
                    dyCount = scrollerUpIndexs.get(secondaryOnclickEvent.getIndex() - 1);
                }

//                recyclerView.scrollToPosition(secondaryOnclickEvent.getIndex());
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 获取二级目录
     */
    private void getFastNewsList() {

        CategoryModel categoryModel = list.get(newIndex);
        DBUtil.getNewsListByCatId(mContext,categoryModel.getCatid(), new TransactionListener() {
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
//        mAdapter.refreshData(newsList);
        int downIndex = 0;
//        List<Integer> lines = new ArrayList<>();
        for (int i = 0; i < newsList.size(); i++) {
            NewsListModel newsListModel = newsList.get(i);
            int newsSize = newsListModel.getRECORDS().size();
            int lineCount = Constant.IS_PHONE ? 4 :4;
            int lineNum = 0;
            if (newsSize % lineCount == 0) {
                lineNum = newsSize / lineCount;
            } else {
                lineNum = (newsSize / lineCount) + 1;
            }
            downIndex += lineNum * PhoneUtil.dip2px(mContext, 240f);
            LogUtil.logError("downIndex = " + downIndex);
            scrollerUpIndexs.add(downIndex);
        }
        initData1();
    }
}
