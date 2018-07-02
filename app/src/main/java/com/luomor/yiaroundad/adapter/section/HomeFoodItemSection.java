package com.luomor.yiaroundad.adapter.section;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.luomor.yiaroundad.module.home.food.FoodIndexActivity;
import com.luomor.yiaroundad.module.home.food.FoodScheduleActivity;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;
import com.luomor.yiaroundad.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2018/06/14 19:15
 * 1097692918@qq.com
 * <p>
 * 首页美食顶部西餐，中餐，索引条目Section
 */

public class HomeFoodItemSection extends StatelessSection {
    private Context mContext;

    public HomeFoodItemSection(Context context) {
        super(R.layout.layout_home_food_top_item, R.layout.layout_home_recommend_empty);
        this.mContext = context;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new HomeFoodItemSection.EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeFoodItemSection.TopItemViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeFoodItemSection.TopItemViewHolder topItemViewHolder
                = (HomeFoodItemSection.TopItemViewHolder) holder;
        //前往追美食
        topItemViewHolder.mWesternFood.setOnClickListener(v -> {
        });
        //前往美食放送表
        topItemViewHolder.mChineseFood.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, FoodScheduleActivity.class)));
        //前往美食索引
        topItemViewHolder.mFoodIndex.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, FoodIndexActivity.class)));
    }


    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class TopItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.layout_western_food)
        TextView mWesternFood;
        @BindView(R.id.layout_chinese_food)
        TextView mChineseFood;
        @BindView(R.id.layout_food_index)
        TextView mFoodIndex;

        TopItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
