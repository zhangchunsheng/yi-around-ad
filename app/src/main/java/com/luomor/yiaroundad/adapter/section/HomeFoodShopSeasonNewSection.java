package com.luomor.yiaroundad.adapter.section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.entity.food.FoodAppIndexInfo;
import com.luomor.yiaroundad.entity.shop.ShopListInfo;
import com.luomor.yiaroundad.module.home.food.SeasonNewFoodActivity;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2018/06/14 19:44
 * 1097692918@qq.com
 * <p>
 * 首页美食分季新美食Section
 */

public class HomeFoodShopSeasonNewSection extends StatelessSection {
    private Context mContext;
    private int season;
    private List<ShopListInfo.ResultBean.PreviousBean.ListBean> seasonNewFoods;

    public HomeFoodShopSeasonNewSection(Context context, int season, List<ShopListInfo.ResultBean.PreviousBean.ListBean> seasonNewFoods) {
        super(R.layout.layout_home_food_shop_season_new_head, R.layout.layout_home_food_shop_season_new_body);
        this.mContext = context;
        this.season = season;
        this.seasonNewFoods = seasonNewFoods;
    }


    @Override
    public int getContentItemsTotal() {
        return seasonNewFoods.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeFoodShopSeasonNewSection.ItemViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        HomeFoodShopSeasonNewSection.ItemViewHolder itemViewHolder = (HomeFoodShopSeasonNewSection.ItemViewHolder) holder;
        ShopListInfo.ResultBean.PreviousBean.ListBean listBean = seasonNewFoods.get(position);

        Glide.with(mContext)
                .load(listBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.yiaa_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);

        itemViewHolder.mTitle.setText(listBean.getTitle());
        itemViewHolder.mPlay.setText(
                NumberUtil.converString(Integer.valueOf(listBean.getFavourites())) + "人追推荐");
        itemViewHolder.mCardView.setOnClickListener(v -> {
        });
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeFoodShopSeasonNewSection.HeaderViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeFoodShopSeasonNewSection.HeaderViewHolder headerViewHolder = (HomeFoodShopSeasonNewSection.HeaderViewHolder) holder;
        setSeasonIcon(headerViewHolder);
        headerViewHolder.mAllNewFood.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, SeasonNewFoodActivity.class)));
    }


    @SuppressLint("SetTextI18n")
    private void setSeasonIcon(HomeFoodShopSeasonNewSection.HeaderViewHolder headViewHolder) {
        switch (season) {
            case 1:
                //冬季
                headViewHolder.mSeasonText.setText("1月新美食");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_1);
                break;
            case 2:
                //春季
                headViewHolder.mSeasonText.setText("4月新美食");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_2);
                break;
            case 3:
                //夏季
                headViewHolder.mSeasonText.setText("7月新美食");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_3);
                break;
            case 4:
                //秋季
                headViewHolder.mSeasonText.setText("10月新美食");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_4);
                break;
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_all_new_food)
        TextView mAllNewFood;
        @BindView(R.id.iv_season)
        ImageView mSeasonIcon;
        @BindView(R.id.tv_season)
        TextView mSeasonText;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        LinearLayout mCardView;
        @BindView(R.id.item_img)
        ImageView mImage;
        @BindView(R.id.item_title)
        TextView mTitle;
        @BindView(R.id.item_play)
        TextView mPlay;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
