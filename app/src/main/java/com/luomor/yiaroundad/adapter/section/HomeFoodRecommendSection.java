package com.luomor.yiaroundad.adapter.section;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luomor.yiaroundad.adapter.HomeFoodRecommendAdapter;
import com.luomor.yiaroundad.entity.food.FoodRecommendInfo;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.module.common.BrowserActivity;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2016/10/14 20:02
 * 1097692918@qq.com
 * <p>
 * 首页美食推荐Section
 */

public class HomeFoodRecommendSection extends StatelessSection {
    private Context mContext;
    private List<FoodRecommendInfo.ResultBean> foodRecommends;

    public HomeFoodRecommendSection(Context context, List<FoodRecommendInfo.ResultBean> foodRecommends) {
        super(R.layout.layout_home_food_recommend_head, R.layout.layout_home_recommend_empty);
        this.mContext = context;
        this.foodRecommends = foodRecommends;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeFoodRecommendSection.EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeFoodRecommendSection.RecyclerViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeFoodRecommendSection.RecyclerViewHolder recyclerViewHolder = (HomeFoodRecommendSection.RecyclerViewHolder) holder;
        recyclerViewHolder.mRecyclerView.setHasFixedSize(false);
        recyclerViewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        recyclerViewHolder.mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        HomeFoodRecommendAdapter mAdapter = new HomeFoodRecommendAdapter(
                recyclerViewHolder.mRecyclerView, foodRecommends);
        recyclerViewHolder.mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(
                (position, holder1) -> BrowserActivity.launch((Activity) mContext,
                        foodRecommends.get(position).getLink(), foodRecommends.get(position).getTitle()));
    }


    static class RecyclerViewHolder extends RecyclerView.ViewHolder {
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
