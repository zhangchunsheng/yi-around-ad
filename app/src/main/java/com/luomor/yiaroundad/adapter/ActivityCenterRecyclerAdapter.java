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
import com.luomor.yiaroundad.entity.recommend.RecommendInfo;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 18/6/27 19:19
 * 1097692918@qq.com
 * <p/>
 * 活动中心横向滑动RecyclerAdapter
 */
public class ActivityCenterRecyclerAdapter extends AbsRecyclerViewAdapter {
    private List<RecommendInfo.ResultBean.BodyBean> activitys;

    public ActivityCenterRecyclerAdapter(RecyclerView recyclerView, List<RecommendInfo.ResultBean.BodyBean> activitys) {
        super(recyclerView);
        this.activitys = activitys;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_activity_center, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Glide.with(getContext())
                    .load(activitys.get(position).getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mVideoImg);

            itemViewHolder.mVideoTitle.setText(activitys.get(position).getTitle());
        }
    }


    @Override
    public int getItemCount() {
        return activitys.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mVideoImg;

        TextView mVideoTitle;


        public ItemViewHolder(View itemView) {

            super(itemView);
            mVideoImg = $(R.id.video_preview);
            mVideoTitle = $(R.id.video_title);
        }
    }
}