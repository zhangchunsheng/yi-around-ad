package com.luomor.yiaroundad.module.home.food;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.luomor.yiaroundad.adapter.FoodIndexAdapter;
import com.luomor.yiaroundad.adapter.helper.HeaderViewRecyclerAdapter;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.entity.food.FoodIndexInfo;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.widget.CircleProgressView;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 18/6/14 14:12
 * 1097692918@qq.com
 * <p/>
 * 美食索引界面
 */
public class FoodIndexActivity extends RxBaseActivity {
  @BindView(R.id.recycle)
  RecyclerView mRecyclerView;
  @BindView(R.id.toolbar)
  Toolbar mToolbar;
  @BindView(R.id.circle_progress)
  CircleProgressView mCircleProgressView;

  private GridLayoutManager mGridLayoutManager;
  private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
  private List<FoodIndexInfo.ResultBean.CategoryBean> categorys = new ArrayList<>();

  @Override
  public int getLayoutId() {
    return R.layout.activity_food_index;
  }

  @Override
  public void initViews(Bundle savedInstanceState) {
    loadData();
  }


  @Override
  public void initRecyclerView() {
    mRecyclerView.setHasFixedSize(true);
    mGridLayoutManager = new GridLayoutManager(FoodIndexActivity.this, 3);
    mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        return (0 == position) ? mGridLayoutManager.getSpanCount() : 1;
      }
    });
    mRecyclerView.setLayoutManager(mGridLayoutManager);
    FoodIndexAdapter mAdapter = new FoodIndexAdapter(mRecyclerView, categorys);
    mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
    createHeadLayout();
    mRecyclerView.setAdapter(mHeaderViewRecyclerAdapter);
  }


  @Override
  public void initToolBar() {
    mToolbar.setTitle("美食索引");
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


  @Override
  public void loadData() {
    RetrofitHelper.getFoodShopAPI()
        .getFoodIndex()
        .compose(this.bindToLifecycle())
        .doOnSubscribe(this::showProgressBar)
        .subscribeOn(Schedulers.io())
        .delay(2000, TimeUnit.MILLISECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(foodIndexInfo -> {
          categorys.addAll(foodIndexInfo.getResult().getCategory());
          finishTask();
        }, throwable -> hideProgressBar());
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


  @Override
  public void finishTask() {
    initRecyclerView();
    hideProgressBar();
  }


  private void createHeadLayout() {
    View headView = LayoutInflater.from(this).inflate(R.layout.layout_food_index_head, mRecyclerView, false);
    mHeaderViewRecyclerAdapter.addHeaderView(headView);
  }
}
