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
import com.luomor.yiaroundad.entity.food.FoodIndexInfo;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 16/8/4 14:12
 * 1097692918@qq.com
 * <p/>
 * 美食索引Adapter
 */
public class FoodIndexAdapter extends AbsRecyclerViewAdapter {
    private List<FoodIndexInfo.ResultBean.CategoryBean> categorys;

    public FoodIndexAdapter(RecyclerView recyclerView, List<FoodIndexInfo.ResultBean.CategoryBean> categorys) {
        super(recyclerView);
        this.categorys = categorys;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).
                inflate(R.layout.item_food_index, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            FoodIndexInfo.ResultBean.CategoryBean categoryBean = categorys.get(position);

            Glide.with(getContext())
                    .load(categoryBean.getCover())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mImageView);

            itemViewHolder.mTextView.setText(categoryBean.getTag_name());
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return categorys.size();
    }


    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mImageView;
        TextView mTextView;


        public ItemViewHolder(View itemView) {
            super(itemView);
            mImageView = $(R.id.item_img);
            mTextView = $(R.id.item_title);
        }
    }
}
