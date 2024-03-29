package com.luomor.yiaroundad.adapter;

import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.search.SearchArchiveInfo;

import java.util.List;

/**
 * Created by Peter on 18/6/29 21:18
 * 1097692918@qq.com
 * <p/>
 * 综合搜索头部美食Adapter
 */
public class ArchiveHeadFoodAdapter extends AbsRecyclerViewAdapter {
    private List<SearchArchiveInfo.DataBean.ItemsBean.SeasonBean> seasons;

    public ArchiveHeadFoodAdapter(RecyclerView recyclerView, List<SearchArchiveInfo.DataBean.ItemsBean.SeasonBean> seasons) {
        super(recyclerView);
        this.seasons = seasons;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).
                inflate(R.layout.item_archive_head_food, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            SearchArchiveInfo.DataBean.ItemsBean.SeasonBean seasonBean = seasons.get(position);

            Glide.with(getContext())
                    .load(seasonBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mFoodPic);

            itemViewHolder.mFoodTitle.setText(seasonBean.getTitle());
            if (seasonBean.getFinish() == 1) {
                itemViewHolder.mFoodCount.setText(
                        seasonBean.getNewest_season() + "," + seasonBean.getTotal_count());
            } else {
                itemViewHolder.mFoodCount.setText(
                        seasonBean.getNewest_season() + "," + seasonBean.getTotal_count());
            }
            itemViewHolder.mFoodDetails.setText(seasonBean.getCat_desc());
        }

        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return seasons.size();
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
