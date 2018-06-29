package com.luomor.yiaroundad.module.home.food;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luomor.yiaroundad.adapter.section.HomeFoodBannerSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodBobySection;
import com.luomor.yiaroundad.adapter.section.HomeFoodItemSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodNewSerialSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodRecommendSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodSeasonNewSection;
import com.luomor.yiaroundad.base.RxLazyFragment;
import com.luomor.yiaroundad.entity.food.FoodAppIndexInfo;
import com.luomor.yiaroundad.entity.food.FoodRecommendInfo;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.SnackbarUtil;
import com.luomor.yiaroundad.widget.CustomEmptyView;
import com.luomor.yiaroundad.widget.banner.BannerEntity;
import com.luomor.yiaroundad.widget.sectioned.SectionedRecyclerViewAdapter;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 16/8/4 21:18
 * 1097692918@qq.com
 * <p/>
 * 首页美食界面
 */
public class HomeFoodFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    private int season;
    private boolean mIsRefreshing = false;
    private List<BannerEntity> bannerList = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<FoodRecommendInfo.ResultBean> foodRecommends = new ArrayList<>();
    private List<FoodAppIndexInfo.ResultBean.AdBean.HeadBean> banners = new ArrayList<>();
    private List<FoodAppIndexInfo.ResultBean.AdBean.BodyBean> foodbobys = new ArrayList<>();
    private List<FoodAppIndexInfo.ResultBean.PreviousBean.ListBean> seasonNewFoods = new ArrayList<>();
    private List<FoodAppIndexInfo.ResultBean.SerializingBean> newFoodSerials = new ArrayList<>();

    public static HomeFoodFragment newInstance() {
        return new HomeFoodFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_food;
    }

    @Override
    public void finishCreateView(Bundle state) {
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }


    @Override
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
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
        setRecycleNoScroll();
    }


    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            clearData();
            loadData();
        });
    }

    private void clearData() {
        mIsRefreshing = true;
        banners.clear();
        bannerList.clear();
        foodbobys.clear();
        foodRecommends.clear();
        newFoodSerials.clear();
        seasonNewFoods.clear();
        mSectionedRecyclerViewAdapter.removeAllSections();
    }


    @Override
    protected void loadData() {
        RetrofitHelper.getFoodAPI()
                .getFoodAppIndex()
                .compose(bindToLifecycle())
                .flatMap(new Func1<FoodAppIndexInfo, Observable<FoodRecommendInfo>>() {
                    @Override
                    public Observable<FoodRecommendInfo> call(FoodAppIndexInfo foodAppIndexInfo) {
                        banners.addAll(foodAppIndexInfo.getResult().getAd().getHead());
                        foodbobys.addAll(foodAppIndexInfo.getResult().getAd().getBody());
                        seasonNewFoods.addAll(foodAppIndexInfo.getResult().getPrevious().getList());
                        season = foodAppIndexInfo.getResult().getPrevious().getSeason();
                        newFoodSerials.addAll(foodAppIndexInfo.getResult().getSerializing());
                        return RetrofitHelper.getFoodAPI().getFoodRecommended();
                    }
                })
                .compose(bindToLifecycle())
                .map(FoodRecommendInfo::getResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultBeans -> {
                    foodRecommends.addAll(resultBeans);
                    finishTask();
                }, throwable -> initEmptyView());
    }


    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        hideEmptyView();
        Observable.from(banners)
                .compose(bindToLifecycle())
                .forEach(bannersBean -> bannerList.add(new BannerEntity(
                        bannersBean.getLink(), bannersBean.getTitle(), bannersBean.getImg())));
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodBannerSection(bannerList));
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodItemSection(getActivity()));
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodNewSerialSection(getActivity(), newFoodSerials));
        if (!foodbobys.isEmpty()) {
            mSectionedRecyclerViewAdapter.addSection(new HomeFoodBobySection(getActivity(), foodbobys));
        }
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodSeasonNewSection(getActivity(), season, seasonNewFoods));
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodRecommendSection(getActivity(), foodRecommends));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }


    public void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }


    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
}
