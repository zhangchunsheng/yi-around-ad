package com.luomor.yiaroundad.module.home.bangumi;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.luomor.yiaroundad.adapter.NewBangumiSerialAdapter;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.entity.bangumi.NewBangumiSerialInfo;
import com.luomor.yiaroundad.widget.CircleProgressView;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 2016/9/25 15:41
 * 1097692918@qq.com
 * <p>
 * 新番连载全部界面
 */

public class NewBangumiSerialActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private NewBangumiSerialAdapter mAdapter;
    private List<NewBangumiSerialInfo.ListBean> newBangumiSerials = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_bangumi_serial;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        initRecyclerView();
        loadData();
    }

    @Override
    public void loadData() {
        RetrofitHelper.getBiliGoAPI()
                .getNewBangumiSerialList()
                .compose(this.bindToLifecycle())
                .doOnSubscribe(this::showProgressBar)
                .map(NewBangumiSerialInfo::getList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBeans -> {
                    newBangumiSerials.addAll(listBeans);
                    finishTask();
                }, throwable -> hideProgressBar());
    }


    @Override
    public void finishTask() {
        hideProgressBar();
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("全部新番连载");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(NewBangumiSerialActivity.this, 3));
        mAdapter = new NewBangumiSerialAdapter(mRecyclerView, newBangumiSerials, true);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> {

        });
    }

    @Override
    public void showProgressBar() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
    }

    @Override
    public void hideProgressBar() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
    }
}
