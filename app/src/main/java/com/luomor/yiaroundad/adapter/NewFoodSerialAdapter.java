package com.luomor.yiaroundad.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.food.NewFoodSerialInfo;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 16/8/6 14:31
 * 1097692918@qq.com
 * <p/>
 * 首页美食新美食连载Adapter
 */
public class NewFoodSerialAdapter extends AbsRecyclerViewAdapter {
    private List<NewFoodSerialInfo.ListBean> newFoodSerials = new ArrayList<>();
    private boolean isShowAll = false;

    public NewFoodSerialAdapter(RecyclerView recyclerView, List<NewFoodSerialInfo.ListBean> newFoodSerials, boolean isShowAll) {
        super(recyclerView);
        this.newFoodSerials = newFoodSerials;
        this.isShowAll = isShowAll;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_recommend_food, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            NewFoodSerialInfo.ListBean listBean = newFoodSerials.get(position);

            Glide.with(getContext())
                    .load(listBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.bili_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mImage);

            itemViewHolder.mTitle.setText(listBean.getTitle());
            itemViewHolder.mPlay.setText(NumberUtil.converString(listBean.getPlay_count()) + "人在看");
            itemViewHolder.mUpdate.setText("更新至第" + listBean.getBgmcount() + "话");
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return isShowAll ? newFoodSerials.size() : 6;
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mImage;
        TextView mTitle;
        TextView mPlay;
        TextView mUpdate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImage = $(R.id.item_img);
            mTitle = $(R.id.item_title);
            mPlay = $(R.id.item_play);
            mUpdate = $(R.id.item_update);
        }
    }
}
