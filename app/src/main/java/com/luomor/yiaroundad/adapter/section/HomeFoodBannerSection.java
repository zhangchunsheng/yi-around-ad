package com.luomor.yiaroundad.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luomor.yiaroundad.widget.banner.BannerEntity;
import com.luomor.yiaroundad.widget.banner.BannerView;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 18/6/27 14:06
 * 1097692918@qq.com
 * <p/>
 * 首页美食界面轮播图Section
 */
public class HomeFoodBannerSection extends StatelessSection {
    private List<BannerEntity> banners;

    public HomeFoodBannerSection(List<BannerEntity> banners) {
        super(R.layout.layout_banner, R.layout.layout_home_recommend_empty);
        this.banners = banners;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new BannerViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
        bannerViewHolder.mBannerView.delayTime(5).build(banners);
    }


    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class BannerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_recommended_banner)
        BannerView mBannerView;

        BannerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
