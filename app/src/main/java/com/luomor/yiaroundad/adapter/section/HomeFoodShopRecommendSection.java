package com.luomor.yiaroundad.adapter.section;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.adapter.HomeFoodShopRecommendAdapter;
import com.luomor.yiaroundad.entity.shop.FoodShopRecommendInfo;
import com.luomor.yiaroundad.module.common.BrowserActivity;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2018/06/14 20:02
 * 1097692918@qq.com
 * <p>
 * 首页美食推荐Section
 */

public class HomeFoodShopRecommendSection extends StatelessSection {
    private Context mContext;
    private List<FoodShopRecommendInfo.ResultBean> foodRecommends;
    private String shopTypeName;

    public HomeFoodShopRecommendSection(Context context, List<FoodShopRecommendInfo.ResultBean> foodRecommends, String shopTypeName) {
        super(R.layout.layout_home_food_recommend_head, R.layout.layout_home_recommend_empty);
        this.mContext = context;
        this.foodRecommends = foodRecommends;
        this.shopTypeName = shopTypeName;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeFoodShopRecommendSection.EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeFoodShopRecommendSection.RecyclerViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeFoodShopRecommendSection.RecyclerViewHolder recyclerViewHolder = (HomeFoodShopRecommendSection.RecyclerViewHolder) holder;
        recyclerViewHolder.mCardTitle.setText(this.shopTypeName + "推荐");
        recyclerViewHolder.mRecyclerView.setHasFixedSize(false);
        recyclerViewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        recyclerViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        HomeFoodShopRecommendAdapter mAdapter = new HomeFoodShopRecommendAdapter(
                recyclerViewHolder.mRecyclerView, foodRecommends);
        recyclerViewHolder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(
                (position, holder1) -> BrowserActivity.launch((Activity) mContext,
                        foodRecommends.get(position).getLink(), foodRecommends.get(position).getTitle()));
    }


    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_food_recommend)
        TextView mCardTitle;

        @BindView(R.id.home_food_recommend_recycler)
        RecyclerView mRecyclerView;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
