package com.faw.hongqi.util;


import com.faw.hongqi.fragment.BaseFragment;
import com.faw.hongqi.fragment.BrightSpotFragment;
import com.faw.hongqi.fragment.FastFragment;
import com.faw.hongqi.fragment.ManualFragment;
import com.faw.hongqi.fragment.ModelsFragment;
import com.faw.hongqi.fragment.SearchFragment;

/**
 * Created by wyc on 16/8/8.
 */
public class FragmentUtil {
    public static final int HOME_PAGE1 = 0;
    public static final int HOME_PAGE2 = 1;
    public static final int HOME_PAGE3 = 2;
    public static final int HOME_PAGE4 = 3;
    public static final int HOME_PAGE5 = 4;


    public static BaseFragment getFragment(int type) {
        BaseFragment fragment;
        switch (type) {
            case HOME_PAGE1:
//                fragment = new HomeFragment();
                fragment = new ModelsFragment();

                break;
            case HOME_PAGE2:
//                fragment = new MessageFragment();
                fragment = new FastFragment();
                break;
            case HOME_PAGE3:
                fragment = new BrightSpotFragment();
//                fragment = new ShoppingFragment();
                break;
            case HOME_PAGE4:
                fragment = new ManualFragment();
                break;
            case HOME_PAGE5:
                fragment = new SearchFragment();
                break;

            default:
                fragment = new ModelsFragment();
                break;
        }
        return fragment;
    }
}
