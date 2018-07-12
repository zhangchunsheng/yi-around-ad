package com.luomor.yiaroundad.module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.luomor.yiaroundad.adapter.ActivityCenterAdapter;
import com.luomor.yiaroundad.adapter.helper.EndlessRecyclerOnScrollListener;
import com.luomor.yiaroundad.adapter.helper.HeaderViewRecyclerAdapter;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.entity.discover.ActivityCenterInfo;
import com.luomor.yiaroundad.module.common.BrowserActivity;
import com.luomor.yiaroundad.utils.ToastUtil;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 2018/06/3 19:01
 * 1097692918@qq.com
 * <p>
 * 活动中心界面
 */

public class ActivityCenterActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private int pageNum = 1;
    private int pageSize = 20;
    private View loadMoreView;
    private boolean mIsRefreshing = false;
    private ActivityCenterAdapter mAdapter;
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private EndlessRecyclerOnScrollListener mEndlessRecyclerOnScrollListener;
    private List<ActivityCenterInfo.ListBean> activityCenters = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_activity_center;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        initRefreshLayout();
        initRecyclerView();
    }


    @Override
    public void initRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mAdapter = new ActivityCenterAdapter(mRecyclerView, activityCenters);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
        createLoadMoreView();
        mEndlessRecyclerOnScrollListener = new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                pageNum++;
                loadData();
                loadMoreView.setVisibility(View.VISIBLE);
            }
        };
        mRecyclerView.addOnScrollListener(mEndlessRecyclerOnScrollListener);
        mAdapter.setOnItemClickListener((position, holder) -> BrowserActivity.launch(
                ActivityCenterActivity.this, activityCenters.get(position).getLink(),
                activityCenters.get(position).getTitle()));
        setRecycleNoScroll();
    }


    @Override
    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            pageNum = 1;
            mIsRefreshing = true;
            activityCenters.clear();
            mEndlessRecyclerOnScrollListener.refresh();
            loadData();
        });
    }


    @Override
    public void loadData() {
        RetrofitHelper.getYiAdAPI()
                .getActivityCenterList(pageNum, pageSize)
                .compose(bindToLifecycle())
                .delay(1000, TimeUnit.MILLISECONDS)
                .map(ActivityCenterInfo::getList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(listBeans -> {
                    if (listBeans.size() < pageSize) {
                        loadMoreView.setVisibility(View.GONE);
                        mHeaderViewRecyclerAdapter.removeFootView();
                    }
                })
                .subscribe(listBeans -> {
                    activityCenters.addAll(listBeans);
                    finishTask();
                }, throwable -> {
                    if (mSwipeRefreshLayout.isRefreshing()) {
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                    loadMoreView.setVisibility(View.GONE);
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }


    @Override
    public void finishTask() {
        mIsRefreshing = false;
        loadMoreView.setVisibility(View.GONE);
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
        if (pageNum * pageSize - pageSize - 1 > 0) {
            mAdapter.notifyItemRangeChanged(pageNum * pageSize - pageSize - 1, pageSize);
        } else {
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("活动中心");
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


    private void createLoadMoreView() {
        loadMoreView = LayoutInflater.from(ActivityCenterActivity.this).inflate(R.layout.layout_load_more, mRecyclerView, false);
        mHeaderViewRecyclerAdapter.addFooterView(loadMoreView);
        loadMoreView.setVisibility(View.GONE);
    }

    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
}
