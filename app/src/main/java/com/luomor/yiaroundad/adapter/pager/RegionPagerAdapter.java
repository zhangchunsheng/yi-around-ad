package com.luomor.yiaroundad.adapter.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.luomor.yiaroundad.entity.region.RegionTypesInfo;
import com.luomor.yiaroundad.module.home.region.RegionTypeDetailsFragment;
import com.luomor.yiaroundad.module.home.region.RegionTypeRecommendFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;

/**
 * Created by Peter on 18/6/14 14:12
 * 1097692918@qq.com
 * <p/>
 * 分区界面PagerAdapter
 */
public class RegionPagerAdapter extends FragmentStatePagerAdapter {

    private String shopType;
    private List<String> titles;
    private List<RegionTypesInfo.DataBean.ChildrenBean> childrens;
    private List<Fragment> fragments = new ArrayList<>();

    public RegionPagerAdapter(FragmentManager fm, String shopType, List<String> titles,
                              List<RegionTypesInfo.DataBean.ChildrenBean> childrens) {
        super(fm);
        this.shopType = shopType;
        this.titles = titles;
        this.childrens = childrens;
        initFragments();
    }


    private void initFragments() {
        fragments.add(RegionTypeRecommendFragment.newInstance(shopType));
        Observable.from(childrens)
            .subscribe(childrenBean -> fragments.add(RegionTypeDetailsFragment.
                newInstance(childrenBean.getShop_type())));
    }


    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
