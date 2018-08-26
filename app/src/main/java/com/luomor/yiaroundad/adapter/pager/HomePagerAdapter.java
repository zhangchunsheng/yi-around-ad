package com.luomor.yiaroundad.adapter.pager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luomor.yiaroundad.module.home.attention.HomeAttentionFragment;
import com.luomor.yiaroundad.module.home.food.HomeFoodFragment;
import com.luomor.yiaroundad.module.home.discover.HomeDiscoverFragment;
import com.luomor.yiaroundad.module.home.live.HomeLiveFragment;
import com.luomor.yiaroundad.module.home.recommend.HomeRecommendedFragment;
import com.luomor.yiaroundad.module.home.region.HomeRegionFragment;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.module.home.shop.HomeFoodShopFragment;

/**
 * Created by Peter on 18/6/14 14:12
 * 1097692918@qq.com
 * <p/>
 * 主界面Fragment模块Adapter
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.sections);
        fragments = new Fragment[TITLES.length];
    }


    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            switch (position) {
                case 0:
                    fragments[position] = HomeLiveFragment.newInstance();
                    break;
                case 1:
                    fragments[position] = HomeRecommendedFragment.newInstance();
                    break;
                case 2:
                    fragments[position] = HomeFoodShopFragment.newInstance("001");
                    break;
                case 3:
                    fragments[position] = HomeFoodShopFragment.newInstance("004");
                    break;
                case 4:
                    fragments[position] = HomeFoodShopFragment.newInstance("012");
                    break;
                case 5:
                    fragments[position] = HomeAttentionFragment.newInstance();
                    break;
                case 6:
                    fragments[position] = HomeDiscoverFragment.newInstance();
                    break;
                case 7:
                    fragments[position] = HomeRegionFragment.newInstance();
                    break;
                default:
                    break;
            }
        }
        return fragments[position];
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
