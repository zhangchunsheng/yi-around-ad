package com.luomor.yiaroundad.adapter.section;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.entity.food.FoodAppIndexInfo;
import com.luomor.yiaroundad.module.home.food.FoodDetailsActivity;
import com.luomor.yiaroundad.module.home.food.NewFoodSerialActivity;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;
import com.luomor.yiaroundad.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2018/06/14 19:29
 * 1097692918@qq.com
 * <p>
 * 首页美食新美食系列Section
 */

public class HomeFoodNewSerialSection extends StatelessSection {
    private Context mContext;
    private List<FoodAppIndexInfo.ResultBean.SerializingBean> newFoodSerials;

    public HomeFoodNewSerialSection(Context context, List<FoodAppIndexInfo.ResultBean.SerializingBean> newFoodSerials) {
        super(R.layout.layout_home_food_new_serial_head, R.layout.layout_home_food_new_serial_body);
        this.mContext = context;
        this.newFoodSerials = newFoodSerials;
    }


    @Override
    public int getContentItemsTotal() {
        return newFoodSerials.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        FoodAppIndexInfo.ResultBean.SerializingBean serializingBean = newFoodSerials.get(
                position);

        Glide.with(mContext)
                .load(serializingBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.yiaa_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);

        itemViewHolder.mTitle.setText(serializingBean.getTitle());
        itemViewHolder.mPlay.setText(
                NumberUtil.converString(serializingBean.getWatching_count()) + "人在看");
        itemViewHolder.mUpdate.setText(serializingBean.getNewest_ep_index());
        itemViewHolder.mCardView.setOnClickListener(v -> FoodDetailsActivity.launch(
                (Activity) mContext, serializingBean.getSeason_id()));
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HomeFoodNewSerialSection.HeaderViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HomeFoodNewSerialSection.HeaderViewHolder headerViewHolder
                = (HomeFoodNewSerialSection.HeaderViewHolder) holder;
        headerViewHolder.mAllSerial.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, NewFoodSerialActivity.class)));
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_all_serial)
        TextView mAllSerial;

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

        @BindView(R.id.item_update)
        TextView mUpdate;


        public ItemViewHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
