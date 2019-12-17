package com.faw.hongqi.fragment;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.faw.hongqi.R;
import com.faw.hongqi.util.ResUtil;
import com.faw.hongqi.widget.HomeModelHotPointView;

import java.util.ArrayList;
import java.util.List;

public class ModelsFragment extends BaseFragment implements View.OnTouchListener {
    ImageView car_model;
    HomeModelHotPointView homeModelHotPointView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c229_model;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        car_model = view.findViewById(R.id.car_model);
        homeModelHotPointView = view.findViewById(R.id.home_model_hot_point_view);
        initPics();
        car_model.setOnTouchListener(this);
    }

    @Override
    protected void initWidgetActions() {

    }

    @Override
    public void refreshData() {

    }

    float start;
    boolean leftScreen;
    long startTime;
    float singleSpec = 15;

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                homeModelHotPointView.hideAllView();
                leftScreen = false;
                start = event.getX();
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                float distance = event.getX() - start;
                distance = distance > 0 ? distance : -distance;
                long partTime = System.currentTimeMillis() - startTime;
                float v = distance / partTime;
                v = v > 0 ? v : -v;
                float StandardV = singleSpec / 60;
                Log.e(Tag, "distance=" + distance + ">>singleSpec=" + singleSpec + ">>v=" + v + ">>StandardV=" + StandardV);
                if (distance < singleSpec) {
                    leftScreen = false;
                } else if (v < StandardV) {
                    leftScreen = false;
                } else {
                    leftScreen = true;
                    Log.e(Tag, "ACTION_UP>>");
//                    intertia(NowID, intertiaStart);
                }

                NowID = NowID == 0 ? 1 : NowID;
                OldID = NowID;
                homeModelHotPointView.showHotPointViewByResId(pics.get(NowID - 1));

                break;
            case MotionEvent.ACTION_MOVE:
                int nowX = (int) event.getX();
                //计算移动距离
                float spec = nowX - start;
                int mo;
                int id;
                if (spec > 0) {
                    //从左向右滑
                    mo = (int) ((spec / singleSpec) + (pics.size() + 1 - OldID));
                    Log.e(Tag, "左向右 mo =" + mo + "  OldID =" + OldID);
                    if (mo > pics.size()) {
                        mo = mo % pics.size();

                    }
                    mo = pics.size() + 1 - mo;
                } else {
                    //从右向左滑
//                    Log.e(Tag, "1  spec=" + spec + ">>singleSpec=" + singleSpec);
                    spec = 0 - spec;
                    mo = (int) ((spec / singleSpec) + OldID);
                    Log.e(Tag, "又向左 mo =" + mo + "  OldID =" + OldID);
                    if (mo > pics.size())
                        mo = mo % pics.size() + 1;
                }
                Log.e(Tag, "最后 mo =" + mo + "  OldID =" + OldID);
                if (mo != 0 && mo != 37) {
                    id = pics.get(mo - 1);
                    if (mo != NowID && !leftScreen) {
                        Log.e(Tag, "ACTION_MOVE>>" + NowID);
                        NowID = mo;
                        car_model.setImageResource(id);
                    }
                }
                break;
        }
        return true;

    }

    int NowID = 0;
    int OldID = 0;
    List<Integer> pics;

    private void initPics() {
        pics = new ArrayList<>();
        for (int i = 0; i < 36; i++) {
            pics.add(ResUtil.getDrawableResId("c229_car_" + (i + 1)));
        }
    }
}
