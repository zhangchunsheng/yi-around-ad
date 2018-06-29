package com.luomor.yiaroundad.module.home.food;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.widget.CircleProgressView;
import com.luomor.yiaroundad.widget.sectioned.SectionedRecyclerViewAdapter;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.adapter.section.SeasonNewFoodSection;
import com.luomor.yiaroundad.entity.food.SeasonNewFoodInfo;
import com.luomor.yiaroundad.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 2016/9/25 15:08
 * 1097692918@qq.com
 * <p>
 * 更多新美食列表界面
 */

public class SeasonNewFoodActivity extends RxBaseActivity {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<SeasonNewFoodInfo.ResultBean> results = new ArrayList<>();


    @Override
    public int getLayoutId() {
        return R.layout.activity_season_new_food;
    }


    @Override
    public void initViews(Bundle savedInstanceState) {
        initRecyclerView();
        loadData();
    }


    @Override
    public void loadData() {
        RetrofitHelper.getFoodAPI()
                .getSeasonNewFoodList()
                .compose(bindToLifecycle())
                .doOnSubscribe(this::showProgressBar)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(seasonNewFoodInfo -> {
                    results.addAll(seasonNewFoodInfo.getResult().subList(0,50));
                    finishTask();
                }, throwable -> hideProgressBar());
    }


    @Override
    public void finishTask() {
        Observable.from(results)
                .compose(bindToLifecycle())
                .forEach(resultBean -> mSectionedRecyclerViewAdapter.addSection(
                        new SeasonNewFoodSection(SeasonNewFoodActivity.this,
                                resultBean.getSeason(), resultBean.getYear(), resultBean.getList())));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
        hideProgressBar();
    }


    @Override
    public void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(SeasonNewFoodActivity.this, 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("分季全部新美食");
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
