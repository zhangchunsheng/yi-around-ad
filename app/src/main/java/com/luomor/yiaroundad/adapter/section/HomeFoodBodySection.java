package com.luomor.yiaroundad.adapter.section;

import android.app.Activity;
import android.content.Context;
import androidx.appcompat.widget.CardView;
import androidx.appcompat.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.entity.food.FoodAppIndexInfo;
import com.luomor.yiaroundad.module.common.BrowserActivity;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 18/6/27 14:06
 * 1097692918@qq.com
 * <p/>
 * 首页美食界面内容Section
 */
public class HomeFoodBodySection extends StatelessSection {
    private Context mContext;
    private List<FoodAppIndexInfo.ResultBean.AdBean.BodyBean> foodbobys;

    public HomeFoodBodySection(Context context, List<FoodAppIndexInfo.ResultBean.AdBean.BodyBean> foodbobys) {
        super(R.layout.layout_home_food_body, R.layout.layout_home_recommend_empty);
        this.mContext = context;
        this.foodbobys = foodbobys;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new FoodBobyViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        FoodBobyViewHolder foodBobyViewHolder = (FoodBobyViewHolder) holder;

        Glide.with(mContext)
                .load(foodbobys.get(0).getImg())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.yiaa_default_image_tv)
                .dontAnimate()
                .into(foodBobyViewHolder.mBobyImage);

        foodBobyViewHolder.mCardView.setOnClickListener(v -> BrowserActivity.launch(
                (Activity) mContext, foodbobys.get(0).getLink(), foodbobys.get(0).getTitle()));
    }


    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }

    static class FoodBobyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.home_food_boby_image)
        ImageView mBobyImage;
        @BindView(R.id.card_view)
        CardView mCardView;

        FoodBobyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
