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
import com.luomor.yiaroundad.entity.user.UserPlayGameInfo;
import com.luomor.yiaroundad.R;

import java.util.List;

/**
 * Created by Peter on 2018/06/20 22:53
 * 1097692918@qq.com
 * <p>
 * 用户详情所玩游戏adapter
 */

public class UserPlayGameAdapter extends AbsRecyclerViewAdapter {
    private List<UserPlayGameInfo.DataBean.GamesBean> games;

    public UserPlayGameAdapter(RecyclerView recyclerView, List<UserPlayGameInfo.DataBean.GamesBean> games) {
        super(recyclerView);
        this.games = games;
    }


    @Override
    public ClickableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        bindContext(parent.getContext());
        return new ItemViewHolder(
                LayoutInflater.from(getContext()).inflate(R.layout.item_user_play_game, parent, false));
    }


    @Override
    public void onBindViewHolder(ClickableViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            UserPlayGameInfo.DataBean.GamesBean gamesBean = games.get(position);

            Glide.with(getContext())
                    .load(gamesBean.getImage())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.yiaa_default_image_tv)
                    .into(itemViewHolder.mImage);

            itemViewHolder.mTitle.setText(gamesBean.getName());
            itemViewHolder.mDesc.setText(gamesBean.getName());
        }
        super.onBindViewHolder(holder, position);
    }


    @Override
    public int getItemCount() {
        return games.size();
    }


    private class ItemViewHolder extends AbsRecyclerViewAdapter.ClickableViewHolder {

        ImageView mImage;
        TextView mTitle;
        TextView mDesc;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mImage = $(R.id.item_img);
            mTitle = $(R.id.item_title);
            mDesc = $(R.id.item_desc);
        }
    }
}
