package com.faw.e115.ui;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.faw.e115.R;
import com.faw.e115.adaptar.PlanePagerAdapter;
import com.faw.e115.dbutil.DBUtil;
import com.faw.e115.fragment.BaseFragment;
import com.faw.e115.fragment.ContentFragment;
import com.faw.e115.model.ContentItemModel;
import com.faw.e115.model.NewsModel;
import com.faw.e115.util.Constant;
import com.faw.e115.widget.CirclePageIndicator;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.ViewPager;

//import com.viewpagerindicator.CirclePageIndicator;


public class E115ContentActivity extends BaseActivity {

    ViewPager viewPager;
    CirclePageIndicator circleView;
    PlanePagerAdapter adapter;
    ArrayList<BaseFragment> _dataList = new ArrayList<>();

    @Override
    protected void initData() {

    }

    @Override
    protected void initViews() {
        setContentView(R.layout.activity_c229_content);
        changeModelToList();
        circleView = findViewById(R.id.circleView);
        viewPager = findViewById(R.id.viewpager);
        for (int i = 0; i < data_list.size(); i++) {
            ContentItemModel contentItemModel = data_list.get(i);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", contentItemModel);
            ContentFragment contentFragment = new ContentFragment();
            contentFragment.setArguments(bundle);
            _dataList.add(contentFragment);

        }
        adapter = new PlanePagerAdapter((this).getSupportFragmentManager(), _dataList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setCurrentItem(0);
    }

    @Override
    protected void initWidgetActions() {
        circleView.setViewPager(viewPager);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
//                exitAPP();

            }
        });
    }

    @Override
    boolean isHasTitle() {
        return true;
    }

    List<ContentItemModel> data_list = new ArrayList<>();

    private void changeModelToList() {
        NewsModel newsModel = (NewsModel) getIntent().getSerializableExtra("data");
        //第一组
        if (newsModel.getTemplate1() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage1());
            contentItemMode.setContent(newsModel.getContent1());
            contentItemMode.setTemplate(newsModel.getTemplate1());
            contentItemMode.setVideo(newsModel.getVideo1());
            data_list.add(contentItemMode);
        }
        //第二组
        if (newsModel.getTemplate2() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage2());
            contentItemMode.setContent(newsModel.getContent2());
            contentItemMode.setTemplate(newsModel.getTemplate2());
            contentItemMode.setVideo(newsModel.getVideo2());
            data_list.add(contentItemMode);
        }
        //第三组
        if (newsModel.getTemplate3() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage3());
            contentItemMode.setContent(newsModel.getContent3());
            contentItemMode.setTemplate(newsModel.getTemplate3());
            contentItemMode.setVideo(newsModel.getVideo3());
            data_list.add(contentItemMode);
        }
        //第四组
        if (newsModel.getTemplate4() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage4());
            contentItemMode.setContent(newsModel.getContent4());
            contentItemMode.setTemplate(newsModel.getTemplate4());
            contentItemMode.setVideo(newsModel.getVideo4());
            data_list.add(contentItemMode);
        }
        //第五组
        if (newsModel.getTemplate5() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage5());
            contentItemMode.setContent(newsModel.getContent5());
            contentItemMode.setTemplate(newsModel.getTemplate5());
            contentItemMode.setVideo(newsModel.getVideo5());
            data_list.add(contentItemMode);
        }
        //第六组
        if (newsModel.getTemplate6() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage6());
            contentItemMode.setContent(newsModel.getContent6());
            contentItemMode.setTemplate(newsModel.getTemplate6());
            contentItemMode.setVideo(newsModel.getVideo6());
            data_list.add(contentItemMode);
        }
        //第七组
        if (newsModel.getTemplate7() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage7());
            contentItemMode.setContent(newsModel.getContent7());
            contentItemMode.setTemplate(newsModel.getTemplate7());
            contentItemMode.setVideo(newsModel.getVideo7());
            data_list.add(contentItemMode);
        }
        //第八组
        if (newsModel.getTemplate8() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage8());
            contentItemMode.setContent(newsModel.getContent8());
            contentItemMode.setTemplate(newsModel.getTemplate8());
            contentItemMode.setVideo(newsModel.getVideo8());
            data_list.add(contentItemMode);
        }
        //第九组
        if (newsModel.getTemplate9() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage9());
            contentItemMode.setContent(newsModel.getContent9());
            contentItemMode.setTemplate(newsModel.getTemplate9());
            contentItemMode.setVideo(newsModel.getVideo9());
            data_list.add(contentItemMode);
        }
        //第十组
        if (newsModel.getTemplate10() != -1) {
            ContentItemModel contentItemMode = new ContentItemModel();
            contentItemMode.setImage(newsModel.getImage10());
            contentItemMode.setContent(newsModel.getContent10());
            contentItemMode.setTemplate(newsModel.getTemplate10());
            contentItemMode.setVideo(newsModel.getVideo10());
            data_list.add(contentItemMode);
        }
    }

    public static void goContentActivity(Context context, NewsModel newsModel) {
        if (Constant.TEST) {
            newsModel = DBUtil.getTestModel(context);
        }
        Intent intent = new Intent(context, E115ContentActivity.class);
        intent.putExtra("data", newsModel);
        context.startActivity(intent);
    }

    private void exitAPP() {
        ActivityManager activityManager = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.AppTask> appTaskList = activityManager.getAppTasks();
        for (ActivityManager.AppTask appTask : appTaskList) {
            appTask.finishAndRemoveTask();
        }
    }
}
