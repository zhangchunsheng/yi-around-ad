package com.luomor.yiaroundad.module.home.discover;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.flyco.tablayout.SlidingTabLayout;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.utils.ConstantUtil;
import com.luomor.yiaroundad.widget.NoScrollViewPager;
import com.luomor.yiaroundad.R;

import butterknife.BindView;

/**
 * Created by Peter on 18/6/12 10:16
 * 1097692918@qq.com
 * <p/>
 * 全区排行榜界面
 */
public class AllAreasRankActivity extends RxBaseActivity {
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private int position;
    private String[] titles = new String[]{
            "美食", "休闲娱乐", "结婚", "电影演出赛事", "丽人",
            "酒店", "亲子", "周边游", "运动健身", "购物", "家装", "学习培训"
    };
    private String[] types = new String[]{
            "001", "002", "003",
            "004", "005", "006",
            "007", "008", "009",
            "010", "011", "012",
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_all_areas_rank;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            position = intent.getIntExtra(ConstantUtil.EXTRA_POSITION, 0);
        }
        AllAreasRankPagerAdapter mAdapter = new AllAreasRankPagerAdapter(getSupportFragmentManager(), titles, types);
        mViewPager.setAdapter(mAdapter);
        mSlidingTabLayout.setViewPager(mViewPager);
        switchPager();
    }


    private void switchPager() {
        switch (position) {
            case 0:
                mViewPager.setCurrentItem(0);
                break;
            case 1:
                mViewPager.setCurrentItem(1);
                break;
            case 2:
                mViewPager.setCurrentItem(2);
                break;
            case 3:
                mViewPager.setCurrentItem(3);
                break;
            case 4:
                mViewPager.setCurrentItem(4);
                break;
            case 5:
                mViewPager.setCurrentItem(5);
                break;
            case 6:
                mViewPager.setCurrentItem(6);
                break;
            case 7:
                mViewPager.setCurrentItem(7);
                break;
            case 8:
                mViewPager.setCurrentItem(8);
                break;
            case 9:
                mViewPager.setCurrentItem(9);
                break;
            case 10:
                mViewPager.setCurrentItem(10);
                break;
            case 11:
                mViewPager.setCurrentItem(11);
                break;
        }
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("全区排行榜");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_rank, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    public static void launch(Activity activity, int position) {
        Intent intent = new Intent(activity, AllAreasRankActivity.class);
        intent.putExtra(ConstantUtil.EXTRA_POSITION, position);
        activity.startActivity(intent);
    }


    private static class AllAreasRankPagerAdapter extends FragmentStatePagerAdapter {
        private String[] titles;
        private String[] types;

        AllAreasRankPagerAdapter(FragmentManager fm, String[] titles, String[] types) {
            super(fm);
            this.titles = titles;
            this.types = types;
        }

        @Override
        public Fragment getItem(int position) {
            return AllAreasRankFragment.newInstance(types[position]);
        }

        @Override
        public int getCount() {
            return types.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}
