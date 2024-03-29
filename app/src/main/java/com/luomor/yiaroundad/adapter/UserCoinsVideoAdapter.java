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
import com.luomor.yiaroundad.entity.user.UserCoinsInfo;
import com.luomor.yiaroundad.network.auxiliary.UrlHelper;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 18/6/14 14:12
 * 1097692918@qq.com
 * <p/>
 * 用户投稿视频adapter
 */
public class UserCoinsVideoAdapter extends AbsRecyclerViewAdapter {
    private List<UserCoinsInfo.DataBean.ListBean> userCoins;

    public UserCoinsVideoAdapter(RecyclerView recyclerView, List<UserCoinsInfo.DataBean.ListBean> userCoins) {
        super(recyclerView);
        this.userCoins = userCoins;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(LayoutInflater.from(getContext())
                .inflate(R.layout.item_user_coins_video, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            UserCoinsInfo.DataBean.ListBean listBean = userCoins.get(position);

            Glide.with(getContext())
                    .load(UrlHelper.getClearVideoPreviewUrl(listBean.getPic()))
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .dontAnimate()
                    .into(itemViewHolder.mVideoPic);

            itemViewHolder.mVideoTitle.setText(listBean.getTitle());
            itemViewHolder.mVideoPlayNum.setText(String.valueOf(listBean.getStat().getView()));
            itemViewHolder.mVideoReviewNum.setText(String.valueOf(listBean.getStat().getDanmaku()));
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return userCoins.size();
    }


    public class ItemViewHolder extends ClickableViewHolder {

        ImageView mVideoPic;
        TextView mVideoTitle;
        TextView mVideoPlayNum;
        TextView mVideoReviewNum;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mVideoPic = $(R.id.item_img);
            mVideoTitle = $(R.id.item_title);
            mVideoPlayNum = $(R.id.item_play);
            mVideoReviewNum = $(R.id.item_review);
        }
    }
}
