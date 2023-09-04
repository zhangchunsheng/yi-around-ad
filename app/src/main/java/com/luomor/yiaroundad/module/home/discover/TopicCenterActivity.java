package com.luomor.yiaroundad.module.home.discover;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;

import com.luomor.yiaroundad.adapter.TopicCenterAdapter;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.entity.discover.TopicCenterInfo;
import com.luomor.yiaroundad.module.common.BrowserActivity;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.ToastUtil;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 2018/06/3 15:47
 * 1097692918@qq.com
 * <p>
 * 话题中心界面
 */

public class TopicCenterActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;

    private TopicCenterAdapter mAdapter;
    private boolean mIsRefreshing = false;
    private List<TopicCenterInfo.ListBean> topicCenters = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_topic_center;
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
        mAdapter = new TopicCenterAdapter(mRecyclerView, topicCenters);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((position, holder) -> BrowserActivity.launch(
                TopicCenterActivity.this, topicCenters.get(position).getLink(),
                topicCenters.get(position).getTitle()));
        setRecycleNoScroll();
    }


    @Override
    public void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mIsRefreshing = true;
            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            mIsRefreshing = true;
            topicCenters.clear();
            loadData();
        });
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("话题中心");
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
    public void loadData() {
        RetrofitHelper.getYiAdAPI()
                .getTopicCenterList()
                .compose(bindToLifecycle())
                .map(TopicCenterInfo::getList)
                .map(listBeans -> {
                    List<TopicCenterInfo.ListBean> tempList = new ArrayList<>();
                    for (int i = 0, size = listBeans.size(); i < size; i++) {
                        TopicCenterInfo.ListBean listBean = listBeans.get(i);
                        if (!Objects.equals(listBean.getCover(), "")) {
                            tempList.add(listBean);
                        }
                    }
                    return tempList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listBeans -> {
                    topicCenters.addAll(listBeans);
                    finishTask();
                }, throwable -> {
                    mSwipeRefreshLayout.setRefreshing(false);
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }


    @Override
    public void finishTask() {
        mIsRefreshing = false;
        mSwipeRefreshLayout.setRefreshing(false);
        mAdapter.notifyDataSetChanged();
    }


    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
}
