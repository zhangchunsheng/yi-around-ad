package com.luomor.yiaroundad.adapter.section;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.luomor.yiaroundad.module.home.food.FoodDetailsActivity;
import com.luomor.yiaroundad.module.home.food.NewFoodSerialActivity;
import com.luomor.yiaroundad.module.home.shop.ShopDetailsActivity;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2018/06/14 19:29
 * 1097692918@qq.com
 * <p>
 * 首页美食新美食连载Section
 */

public class HomeFoodShopNewSerialSection extends StatelessSection {
    private Context mContext;
    private List<ShopListInfo.ResultBean.ShopBean> shops;

    public HomeFoodShopNewSerialSection(Context context, List<ShopListInfo.ResultBean.ShopBean> shops) {
        super(R.layout.layout_home_food_shop_new_serial_head, R.layout.layout_home_food_shop_new_serial_body);
        this.mContext = context;
        this.shops = shops;
    }


    @Override
    public int getContentItemsTotal() {
        return shops.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        ShopListInfo.ResultBean.ShopBean shopBean = shops.get(position);

        Glide.with(mContext)
                .load(shopBean.getShop_image())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.yiaa_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);

        itemViewHolder.mTitle.setText(shopBean.getShop_name());
        itemViewHolder.mPlay.setText(
                NumberUtil.converString(shopBean.getComment_num()) + "人推荐");
        itemViewHolder.mUpdate.setText("距离您100米");
        itemViewHolder.mCardView.setOnClickListener(v -> ShopDetailsActivity.launch(
                (Activity) mContext, shopBean.getShop_id()));
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeFoodShopNewSerialSection.HeaderViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeFoodShopNewSerialSection.HeaderViewHolder headerViewHolder
                = (HomeFoodShopNewSerialSection.HeaderViewHolder) holder;
        headerViewHolder.mAllShop.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, NewFoodSerialActivity.class)));
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_all_shop)
        TextView mAllShop;

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

        @BindView(R.id.item_recommend_num)
        TextView mPlay;

        @BindView(R.id.item_distance)
        TextView mUpdate;


        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
