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
import com.luomor.yiaroundad.entity.user.UserContributeInfo;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 18/6/14 14:12
 * 1097692918@qq.com
 * <p/>
 * 用户投稿视频adapter
 */
public class UserContributeVideoAdapter extends AbsRecyclerViewAdapter {
    private List<UserContributeInfo.DataBean.VlistBean> userVideoList;

    public UserContributeVideoAdapter(RecyclerView recyclerView, List<UserContributeInfo.DataBean.VlistBean> userVideoList) {
        super(recyclerView);
        this.userVideoList = userVideoList;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_video_strip, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            UserContributeInfo.DataBean.VlistBean vlistBean = userVideoList.get(position);

            Glide.with(getContext())
                    .load("http:" + vlistBean.getPic())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mVideoPic);

            itemViewHolder.mVideoTitle.setText(vlistBean.getTitle());
            itemViewHolder.mVideoUserInfo.setText(vlistBean.getAuthor());
            itemViewHolder.mVideoPlayNum.setText(String.valueOf(vlistBean.getPlay()));
            itemViewHolder.mVideoReviewNum.setText(String.valueOf(vlistBean.getVideo_review()));
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return userVideoList.size();
    }


    public class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mVideoPic;
        TextView mVideoTitle;
        TextView mVideoUserInfo;
        TextView mVideoPlayNum;
        TextView mVideoReviewNum;


        public ItemViewHolder(View itemView) {
            super(itemView);
            mVideoPic = $(R.id.item_img);
            mVideoTitle = $(R.id.item_title);
            mVideoUserInfo = $(R.id.item_user_name);
            mVideoPlayNum = $(R.id.item_play);
            mVideoReviewNum = $(R.id.item_review);
        }
    }
}
