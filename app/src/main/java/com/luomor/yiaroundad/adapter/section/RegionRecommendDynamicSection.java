package com.luomor.yiaroundad.adapter.section;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.widget.CardView;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.entity.region.RegionRecommendInfo;
import com.luomor.yiaroundad.module.video.VideoDetailsActivity;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;
import com.luomor.yiaroundad.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2018/06/21 23:28
 * 1097692918@qq.com
 * <p>
 * 分区推荐全区动态section
 */

public class RegionRecommendDynamicSection extends StatelessSection {
    private Context mContext;
    private List<RegionRecommendInfo.DataBean.DynamicBean> dynamics;

    public RegionRecommendDynamicSection(Context context, List<RegionRecommendInfo.DataBean.DynamicBean> dynamics) {
        super(R.layout.layout_region_recommend_dynamic_head, R.layout.layout_region_recommend_card_item);
        this.dynamics = dynamics;
        this.mContext = context;
    }


    @Override
    public int getContentItemsTotal() {
        return dynamics.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        RegionRecommendInfo.DataBean.DynamicBean dynamicBean = dynamics.get(position);

        Glide.with(mContext)
                .load(dynamicBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.yiaa_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);

        itemViewHolder.mTitle.setText(dynamicBean.getTitle());
        itemViewHolder.mPlay.setText(NumberUtil.converString(dynamicBean.getPlay()));
        itemViewHolder.mReview.setText(NumberUtil.converString(dynamicBean.getDanmaku()));
        itemViewHolder.mCardView.setOnClickListener(
                v -> VideoDetailsActivity.launch((Activity) mContext,
                        Integer.valueOf(dynamicBean.getParam()), dynamicBean.getCover()));
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeadViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        super.onBindHeaderViewHolder(holder);
    }


    static class HeadViewHolder extends RecyclerView.ViewHolder {
        HeadViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.item_img)
        ImageView mImage;
        @BindView(R.id.item_title)
        TextView mTitle;
        @BindView(R.id.item_play)
        TextView mPlay;
        @BindView(R.id.item_review)
        TextView mReview;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
