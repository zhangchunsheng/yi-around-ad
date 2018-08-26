package com.luomor.yiaroundad.module.home.shop;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.adapter.section.HomeFoodBannerSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodBodySection;
import com.luomor.yiaroundad.adapter.section.HomeFoodItemSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodRecommendSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodSeasonNewSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopBodySection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopNewSerialSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopRecommendSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopSeasonNewSection;
import com.luomor.yiaroundad.base.RxLazyFragment;
import com.luomor.yiaroundad.entity.shop.FoodShopRecommendInfo;
import com.luomor.yiaroundad.entity.shop.ShopListInfo;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.SnackbarUtil;
import com.luomor.yiaroundad.widget.CustomEmptyView;
import com.luomor.yiaroundad.widget.banner.BannerEntity;
import com.luomor.yiaroundad.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by peterzhang on 20/08/2018.
 *
 * 首页美食店铺页面
 */

public class HomeFoodShopFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    private int season;
    private boolean mIsRefreshing = false;
    private String shopType = "001";
    private static Map<String, String> SHOPTYPES = new HashMap<String, String>();
    private List<BannerEntity> bannerList = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<FoodShopRecommendInfo.ResultBean> foodRecommends = new ArrayList<>();
    private List<ShopListInfo.ResultBean.AdBean.HeadBean> banners = new ArrayList<>();
    private List<ShopListInfo.ResultBean.AdBean.BodyBean> foodbodys = new ArrayList<>();
    private List<ShopListInfo.ResultBean.PreviousBean.ListBean> seasonNewFoods = new ArrayList<>();
    private List<ShopListInfo.ResultBean.SerializingBean> newFoodSerials = new ArrayList<>();
    private List<ShopListInfo.ResultBean.ShopBean> shops = new ArrayList<>();

    public static HomeFoodShopFragment newInstance(String shopType) {
        HomeFoodShopFragment fragment = new HomeFoodShopFragment();
        fragment.shopType = shopType;
        HomeFoodShopFragment.SHOPTYPES.put("001", "美食");
        HomeFoodShopFragment.SHOPTYPES.put("004", "电影");
        HomeFoodShopFragment.SHOPTYPES.put("012", "书店");
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_food_shop;
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
        foodbodys.clear();
        foodRecommends.clear();
        newFoodSerials.clear();
        shops.clear();
        seasonNewFoods.clear();
        mSectionedRecyclerViewAdapter.removeAllSections();
    }


    @Override
    protected void loadData() {
        RetrofitHelper.getFoodShopAPI()
                .getShopList(this.shopType)
                .compose(bindToLifecycle())
                .flatMap(new Func1<ShopListInfo, Observable<FoodShopRecommendInfo>>() {
                    @Override
                    public Observable<FoodShopRecommendInfo> call(ShopListInfo shopListInfo) {
                        banners.addAll(shopListInfo.getResult().getAd().getHead());
                        foodbodys.addAll(shopListInfo.getResult().getAd().getBody());
                        seasonNewFoods.addAll(shopListInfo.getResult().getPrevious().getList());
                        season = shopListInfo.getResult().getPrevious().getSeason();
                        newFoodSerials.addAll(shopListInfo.getResult().getSerializing());
                        shops.addAll(shopListInfo.getResult().getShops());
                        return RetrofitHelper.getFoodShopAPI().getFoodRecommended();
                    }
                })
                .compose(bindToLifecycle())
                .map(FoodShopRecommendInfo::getResult)
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
        if(this.shopType.equals("001")) {
            mSectionedRecyclerViewAdapter.addSection(new HomeFoodItemSection(getActivity()));
        }
        String shopTypeName = HomeFoodShopFragment.SHOPTYPES.get(this.shopType);
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopNewSerialSection(getActivity(), shops, shopTypeName));
        if (!foodbodys.isEmpty()) {
            mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopBodySection(getActivity(), foodbodys));
        }
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopSeasonNewSection(getActivity(), season, seasonNewFoods, shopTypeName));
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopRecommendSection(getActivity(), foodRecommends, shopTypeName));
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
