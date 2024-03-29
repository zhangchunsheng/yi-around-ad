package com.luomor.yiaroundad.adapter;

import android.annotation.SuppressLint;
import android.net.Uri;
import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.food.SpecialTopic;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Peter on 18/6/14 14:12
 * 1097692918@qq.com
 * <p/>
 * 专题视频Adapter
 */
public class SpecialVideoRecyclerAdapter extends AbsRecyclerViewAdapter {
    private List<SpecialTopic.Item> spItems = new ArrayList<>();

    public SpecialVideoRecyclerAdapter(RecyclerView recyclerView, List<SpecialTopic.Item> spItems) {
        super(recyclerView);
        this.spItems = spItems;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).
                inflate(R.layout.item_special_videos, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            SpecialTopic.Item item = spItems.get(position);
            itemViewHolder.mSpNum.setText(item.episode);

            Glide.with(getContext())
                    .load(Uri.parse(item.cover))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mPreviewImage);
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return spItems.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mPreviewImage;
        TextView mSpNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mPreviewImage = $(R.id.item_img);
            mSpNum = $(R.id.item_title);
        }
    }
}
