package com.luomor.yiaroundad.adapter;

import android.annotation.SuppressLint;
import androidx.appcompat.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.adapter.helper.AbsRecyclerViewAdapter;
import com.luomor.yiaroundad.entity.search.SearchUpperInfo;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.widget.CircleImageView;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 18/6/4 12:28
 * 1097692918@qq.com
 * <p/>
 * up主搜索结果Adapter
 */
public class UpperResultsAdapter extends AbsRecyclerViewAdapter {
    private List<SearchUpperInfo.DataBean.ItemsBean> uppers;


    public UpperResultsAdapter(RecyclerView recyclerView, List<SearchUpperInfo.DataBean.ItemsBean> uppers) {
        super(recyclerView);
        this.uppers = uppers;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext()).
                inflate(R.layout.item_search_upper, parent, false));
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            SearchUpperInfo.DataBean.ItemsBean itemsBean = uppers.get(position);

            Glide.with(getContext())
                    .load(itemsBean.getCover())
                    .centerCrop()
                    .dontAnimate()
                    .placeholder(R.drawable.ico_user_default)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(itemViewHolder.mUserAvatar);

            itemViewHolder.mUserName.setText(itemsBean.getTitle());
            itemViewHolder.mUserFans.setText("粉丝：" + NumberUtil.converString(itemsBean.getFans()));
            itemViewHolder.mUserVideos.setText("视频数：" + NumberUtil.converString(itemsBean.getArchives()));
            itemViewHolder.mDesc.setText(itemsBean.getSign());
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return uppers.size();
    }


    public class ItemViewHolder extends ClickableViewHolder {

        CircleImageView mUserAvatar;
        TextView mUserName;
        TextView mUserFans;
        TextView mUserVideos;
        TextView mDesc;


        public ItemViewHolder(View itemView) {
            super(itemView);
            mUserAvatar = $(R.id.item_avatar_view);
            mUserName = $(R.id.item_user_name);
            mUserFans = $(R.id.item_user_fans);
            mUserVideos = $(R.id.item_user_videos);
            mDesc = $(R.id.item_user_details);
        }
    }
}
