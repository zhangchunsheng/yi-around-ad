package com.luomor.yiaroundad.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.search.SearchFoodInfo;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 18/6/29 21:18
 * 1097692918@qq.com
 * <p/>
 * 美食搜索数据Adapter
 */
public class FoodResultsAdapter extends AbsRecyclerViewAdapter {
    private List<SearchFoodInfo.DataBean.ItemsBean> foods;

    public FoodResultsAdapter(RecyclerView recyclerView, List<SearchFoodInfo.DataBean.ItemsBean> foods) {
        super(recyclerView);
        this.foods = foods;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).
                inflate(R.layout.item_search_food, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            SearchFoodInfo.DataBean.ItemsBean itemsBean = foods.get(position);

            Glide.with(getContext())
                    .load(itemsBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mFoodPic);

            itemViewHolder.mFoodTitle.setText(itemsBean.getTitle());
            if (itemsBean.getFinish() == 1) {
                itemViewHolder.mFoodCount.setText(
                        itemsBean.getNewest_season() + "," + itemsBean.getTotal_count() + "话全");
            } else {
                itemViewHolder.mFoodCount.setText(
                        itemsBean.getNewest_season() + "," + "更新至第" + itemsBean.getTotal_count() + "话");
            }
            itemViewHolder.mFoodDetails.setText(itemsBean.getCat_desc());
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return foods.size();
    }


    public class ItemViewHolder extends ClickableViewHolder {

        ImageView mFoodPic;
        TextView mFoodTitle;
        TextView mFoodDetails;
        TextView mFoodCount;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mFoodPic = $(R.id.item_img);
            mFoodTitle = $(R.id.item_title);
            mFoodDetails = $(R.id.item_details);
            mFoodCount = $(R.id.item_count);
        }
    }
}
