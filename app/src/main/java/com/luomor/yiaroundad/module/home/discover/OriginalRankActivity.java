package com.luomor.yiaroundad.module.home.discover;

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
import com.luomor.yiaroundad.widget.NoScrollViewPager;
import com.luomor.yiaroundad.R;

import butterknife.BindView;

/**
 * Created by Peter on 18/6/12 20:20
 * 1097692918@qq.com
 * <p/>
 * 美食排行榜界面
 */
public class OriginalRankActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.sliding_tabs)
    SlidingTabLayout mSlidingTabLayout;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;

    private String[] titles = new String[]{"美食", "全站", "湘菜"};
    private String[] orders = new String[]{"001", "0", "001001016"};

    @Override
    public int getLayoutId() {
        return R.layout.activity_original_rank;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        mViewPager.setAdapter(new OriginalRankPagerAdapter(getSupportFragmentManager(), titles, orders));
        mViewPager.setOffscreenPageLimit(orders.length);
        mSlidingTabLayout.setViewPager(mViewPager);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle("排行榜");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
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


    private static class OriginalRankPagerAdapter extends FragmentStatePagerAdapter {
        private String[] titles;
        private String[] orders;

        OriginalRankPagerAdapter(FragmentManager fm, String[] titles, String[] orders) {
            super(fm);
            this.titles = titles;
            this.orders = orders;
        }

        @Override
        public Fragment getItem(int position) {
            return OriginalRankFragment.newInstance(orders[position]);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
