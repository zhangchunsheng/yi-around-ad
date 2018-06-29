package com.luomor.yiaroundad.adapter.section;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.entity.food.SeasonNewFoodInfo;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;
import com.luomor.yiaroundad.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2016/11/1 12:52
 * 1097692918@qq.com
 * <p>
 * 分季新美食Section
 */

public class SeasonNewFoodSection extends StatelessSection {
    private Context mContext;
    private int season;
    private int year;
    private List<SeasonNewFoodInfo.ResultBean.ListBean> seasonNewFoods;

    public SeasonNewFoodSection(Context context, int season, int year, List<SeasonNewFoodInfo.ResultBean.ListBean> seasonNewFoods) {
        super(R.layout.layout_season_new_food_head, R.layout.layout_home_food_season_new_body);
        this.mContext = context;
        this.season = season;
        this.year = year;
        this.seasonNewFoods = seasonNewFoods;
    }


    @Override
    public int getContentItemsTotal() {
        return seasonNewFoods.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new SeasonNewFoodSection.ItemViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        SeasonNewFoodSection.ItemViewHolder itemViewHolder = (SeasonNewFoodSection.ItemViewHolder) holder;
        SeasonNewFoodInfo.ResultBean.ListBean listBean = seasonNewFoods.get(position);

        Glide.with(mContext)
                .load(listBean.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.bili_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mImage);

        itemViewHolder.mTitle.setText(listBean.getTitle());
        itemViewHolder.mPlay.setText(NumberUtil.converString(Integer.valueOf(listBean.getFavourites())) + "人追美食");
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new SeasonNewFoodSection.HeadViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        SeasonNewFoodSection.HeadViewHolder headViewHolder = (SeasonNewFoodSection.HeadViewHolder) holder;
        setSeasonIcon(headViewHolder);
    }


    @SuppressLint("SetTextI18n")
    private void setSeasonIcon(HeadViewHolder headViewHolder) {
        switch (season) {
            case 1:
                //冬季
                headViewHolder.mSeasonText.setText(year + "年1月");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_1);
                break;
            case 2:
                //春季
                headViewHolder.mSeasonText.setText(year + "年4月");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_2);
                break;
            case 3:
                //夏季
                headViewHolder.mSeasonText.setText(year + "年7月");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_3);
                break;
            case 4:
                //秋季
                headViewHolder.mSeasonText.setText(year + "年10月");
                headViewHolder.mSeasonIcon.setImageResource(R.drawable.food_home_ic_season_4);
                break;
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

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class HeadViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_season)
        ImageView mSeasonIcon;
        @BindView(R.id.tv_season)
        TextView mSeasonText;
        @BindView(R.id.tv_more)
        TextView mMore;

        public HeadViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
