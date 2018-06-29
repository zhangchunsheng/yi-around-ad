package com.luomor.yiaroundad.module.common;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.R;

import butterknife.BindView;

/**
 * Created by Peter on 16/8/7 14:12
 * 1097692918@qq.com
 * <p/>
 * 关于我
 */
public class HotBitmapGGInfoActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    public int getLayoutId() {
        return R.layout.activity_hotbitmapgg;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("关于我");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
