package com.luomor.yiaroundad.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.food.FoodDetailsInfo;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 2018/06/1 20:34
 * 1097692918@qq.com
 * <p>
 * 美食详情分季版本adapter
 */

public class FoodDetailsSeasonsAdapter extends AbsRecyclerViewAdapter {
    private int layoutPosition = 0;
    private List<FoodDetailsInfo.ResultBean.SeasonsBean> seasons;

    public FoodDetailsSeasonsAdapter(RecyclerView recyclerView, List<FoodDetailsInfo.ResultBean.SeasonsBean> seasons) {
        super(recyclerView);
        this.seasons = seasons;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_food_details_seasons, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            FoodDetailsInfo.ResultBean.SeasonsBean seasonsBean = seasons.get(position);
            itemViewHolder.mSeasons.setText(seasonsBean.getTitle());

            if (position == layoutPosition) {
                itemViewHolder.mCardView.setForeground(
                        getContext().getResources().getDrawable(R.drawable.bg_selection));
                itemViewHolder.mSeasons.setTextColor(
                        getContext().getResources().getColor(R.color.colorPrimary));
            } else {
                itemViewHolder.mCardView.setForeground(
                        getContext().getResources().getDrawable(R.drawable.bg_normal));
                itemViewHolder.mSeasons.setTextColor(
                        getContext().getResources().getColor(R.color.font_normal));
            }
        }
        super.onBindViewHolder(holder, position);
    }


    public void notifyItemForeground(int clickPosition) {
        layoutPosition = clickPosition;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return seasons.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        CardView mCardView;
        TextView mSeasons;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mCardView = $(R.id.card_view);
            mSeasons = $(R.id.tv_seasons);
        }
    }
}
