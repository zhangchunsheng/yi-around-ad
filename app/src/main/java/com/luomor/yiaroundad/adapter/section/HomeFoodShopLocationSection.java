package com.luomor.yiaroundad.adapter.section;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.entity.shop.ShopListInfo;
import com.luomor.yiaroundad.widget.banner.BannerEntity;
import com.luomor.yiaroundad.widget.banner.BannerView;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 18/6/27 14:06
 * 1097692918@qq.com
 * <p/>
 * 首页定位信息Section
 */
public class HomeFoodShopLocationSection extends StatelessSection {
    private ShopListInfo.ResultBean.LocationBean location;

    public HomeFoodShopLocationSection(ShopListInfo.ResultBean.LocationBean location) {
        super(R.layout.layout_location, R.layout.layout_home_recommend_empty);
        this.location = location;
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
        return new LocationViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        LocationViewHolder bannerViewHolder = (LocationViewHolder) holder;
        bannerViewHolder.mAddressView.setText("位置：" + location.getFormatted_address());
    }


    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class LocationViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.location_text)
        TextView mAddressView;

        LocationViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
