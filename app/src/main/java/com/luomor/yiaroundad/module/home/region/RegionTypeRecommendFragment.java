package com.luomor.yiaroundad.module.home.region;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luomor.yiaroundad.adapter.section.RegionRecommendBannerSection;
import com.luomor.yiaroundad.adapter.section.RegionRecommendDynamicSection;
import com.luomor.yiaroundad.adapter.section.RegionRecommendHotSection;
import com.luomor.yiaroundad.adapter.section.RegionRecommendNewSection;
import com.luomor.yiaroundad.adapter.section.RegionRecommendTypesSection;
import com.luomor.yiaroundad.base.RxLazyFragment;
import com.luomor.yiaroundad.entity.region.RegionRecommendInfo;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.ConstantUtil;
import com.luomor.yiaroundad.utils.LogUtil;
import com.luomor.yiaroundad.utils.ToastUtil;
import com.luomor.yiaroundad.widget.banner.BannerEntity;
import com.luomor.yiaroundad.widget.sectioned.SectionedRecyclerViewAdapter;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 2018/06/21 20:39
 * 1097692918@qq.com
 * <p>
 * 分区推荐页面
 */

public class RegionTypeRecommendFragment extends RxLazyFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;

    private String shopType;
    private boolean mIsRefreshing = false;
    private List<BannerEntity> bannerEntities = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<RegionRecommendInfo.DataBean.BannerBean.TopBean> banners = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.RecommendBean> recommends = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.NewBean> news = new ArrayList<>();
    private List<RegionRecommendInfo.DataBean.DynamicBean> dynamics = new ArrayList<>();


    public static RegionTypeRecommendFragment newInstance(String shopType) {
        RegionTypeRecommendFragment fragment = new RegionTypeRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtil.EXTRA_SHOP_TYPE, shopType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_region_recommend;
    }

    @Override
    public void finishCreateView(Bundle state) {
        shopType = getArguments().getString(ConstantUtil.EXTRA_SHOP_TYPE);
        initRefreshLayout();
        initRecyclerView();
    }

    @Override
    protected void initRefreshLayout() {
        mRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mRecyclerView.post(() -> {
            mRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
            loadData();
        });
        mRefreshLayout.setOnRefreshListener(() -> {
            clearData();
            loadData();
        });
    }


    private void clearData() {
        bannerEntities.clear();
        banners.clear();
        recommends.clear();
        news.clear();
        dynamics.clear();
        mIsRefreshing = true;
        mSectionedRecyclerViewAdapter.removeAllSections();
    }


    @Override
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 2;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
        setRecycleNoScroll();
    }


    @Override
    protected void loadData() {
        RetrofitHelper.getAdAppAPI()
                .getRegionRecommends(shopType)
                .compose(bindToLifecycle())
                .map(RegionRecommendInfo::getData)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dataBean -> {
                    banners.addAll(dataBean.getBanner().getTop());
                    recommends.addAll(dataBean.getRecommend());
                    news.addAll(dataBean.getNewX());
                    dynamics.addAll(dataBean.getDynamic());
                    finishTask();
                }, throwable -> {
                    LogUtil.all(throwable.getMessage());
                    mRefreshLayout.setRefreshing(false);
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }


    @Override
    protected void finishTask() {
        setBanner();
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendBannerSection(bannerEntities));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendTypesSection(getActivity(), shopType));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendHotSection(getActivity(), shopType, recommends));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendNewSection(getActivity(), shopType, news));
        mSectionedRecyclerViewAdapter.addSection(new RegionRecommendDynamicSection(getActivity(), dynamics));
        mIsRefreshing = false;
        mRefreshLayout.setRefreshing(false);
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }


    private void setBanner() {
        Observable.from(banners)
                .compose(bindToLifecycle())
                .forEach(topBean -> bannerEntities.add(new BannerEntity(topBean.getUri(),
                        topBean.getTitle(), topBean.getImage())));
    }


    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
}
