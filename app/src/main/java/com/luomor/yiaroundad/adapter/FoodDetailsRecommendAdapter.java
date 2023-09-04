package com.luomor.yiaroundad.adapter;

import android.annotation.SuppressLint;
import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.food.FoodDetailsRecommendInfo;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 18/6/6 11:51
 * 1097692918@qq.com
 * <p/>
 * 美食详情美食推荐adapter
 */
public class FoodDetailsRecommendAdapter extends AbsRecyclerViewAdapter {
    private List<FoodDetailsRecommendInfo.ResultBean.ListBean> foodRecommends;

    public FoodDetailsRecommendAdapter(RecyclerView recyclerView, List<FoodDetailsRecommendInfo.ResultBean.ListBean> foodRecommends) {
        super(recyclerView);
        this.foodRecommends = foodRecommends;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_food_details_recommend, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {

        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            FoodDetailsRecommendInfo.ResultBean.ListBean listBean = foodRecommends.get(position);

            Glide.with(getContext())
                    .load(listBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mImage);

            itemViewHolder.mTitle.setText(listBean.getTitle());
            itemViewHolder.mFollow.setText(
                    NumberUtil.converString(Integer.valueOf(listBean.getFollow())) + "人追美食");
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {

        return foodRecommends.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mImage;

        TextView mTitle;

        TextView mFollow;


        public ItemViewHolder(View itemView) {

            super(itemView);
            mImage = $(R.id.item_img);
            mTitle = $(R.id.item_title);
            mFollow = $(R.id.item_follow);
        }
    }
}
