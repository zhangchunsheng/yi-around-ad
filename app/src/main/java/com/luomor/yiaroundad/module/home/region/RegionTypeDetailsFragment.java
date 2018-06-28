package com.luomor.yiaroundad.module.home.region;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luomor.yiaroundad.adapter.section.RegionDetailsHotVideoSection;
import com.luomor.yiaroundad.adapter.section.RegionDetailsNewsVideoSection;
import com.luomor.yiaroundad.base.RxLazyFragment;
import com.luomor.yiaroundad.entity.region.RegionDetailsInfo;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.ConstantUtil;
import com.luomor.yiaroundad.utils.LogUtil;
import com.luomor.yiaroundad.utils.ToastUtil;
import com.luomor.yiaroundad.widget.CircleProgressView;
import com.luomor.yiaroundad.widget.sectioned.SectionedRecyclerViewAdapter;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by hcc on 16/8/4 21:18
 * 100332338@qq.com
 * <p/>
 * 分区对应类型列表详情界面
 */
public class RegionTypeDetailsFragment extends RxLazyFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private int rid;
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<RegionDetailsInfo.DataBean.NewBean> newsVideos = new ArrayList<>();
    private List<RegionDetailsInfo.DataBean.RecommendBean> recommendVideos = new ArrayList<>();


    public static RegionTypeDetailsFragment newInstance(int rid) {
        RegionTypeDetailsFragment fragment = new RegionTypeDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ConstantUtil.EXTRA_RID, rid);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_region_details;
    }

    @Override
    public void finishCreateView(Bundle state) {
        rid = getArguments().getInt(ConstantUtil.EXTRA_RID);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        showProgressBar();
        initRecyclerView();
        loadData();
        isPrepared = false;
    }


    @Override
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 1);
        mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 1;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
    }


    @Override
    protected void loadData() {
        RetrofitHelper.getBiliAppAPI()
                .getRegionDetails(rid)
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(regionDetailsInfo -> {
                    recommendVideos.addAll(regionDetailsInfo.getData().getRecommend());
                    newsVideos.addAll(regionDetailsInfo.getData().getNewX());
                    finishTask();
                }, throwable -> {
                    LogUtil.all(throwable.getMessage());
                    hideProgressBar();
                    ToastUtil.ShortToast("加载失败啦,请重新加载~");
                });
    }


    @Override
    protected void finishTask() {
        hideProgressBar();
        mSectionedRecyclerViewAdapter.addSection(new RegionDetailsHotVideoSection(getActivity(), recommendVideos));
        mSectionedRecyclerViewAdapter.addSection(new RegionDetailsNewsVideoSection(getActivity(), newsVideos));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }


    @Override
    protected void showProgressBar() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
    }


    @Override
    protected void hideProgressBar() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
    }
}
