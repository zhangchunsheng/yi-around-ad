package com.luomor.yiaroundad.adapter;

import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.food.FoodRecommendInfo;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 2018/06/2 17:06
 * 1097692918@qq.com
 * <p>
 * 首页美食推荐adapter
 */

public class HomeFoodRecommendAdapter extends AbsRecyclerViewAdapter {
    private List<FoodRecommendInfo.ResultBean> mFoodDetailsRecommends;

    public HomeFoodRecommendAdapter(RecyclerView recyclerView, List<FoodRecommendInfo.ResultBean> mFoodDetailsRecommends) {
        super(recyclerView);
        this.mFoodDetailsRecommends = mFoodDetailsRecommends;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_food_recommend, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            FoodRecommendInfo.ResultBean resultBean = mFoodDetailsRecommends.get(position);

            Glide.with(getContext())
                    .load(resultBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mImage);

            itemViewHolder.mTitle.setText(resultBean.getTitle());
            itemViewHolder.mDesc.setText(resultBean.getDesc());
            if (resultBean.getIs_new() == 1) {
                itemViewHolder.mIsNew.setVisibility(View.VISIBLE);
            } else {
                itemViewHolder.mIsNew.setVisibility(View.GONE);
            }
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return mFoodDetailsRecommends.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mImage;
        TextView mTitle;
        TextView mDesc;
        ImageView mIsNew;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImage = $(R.id.item_img);
            mTitle = $(R.id.item_title);
            mDesc = $(R.id.item_desc);
            mIsNew = $(R.id.item_is_new);
        }
    }
}
