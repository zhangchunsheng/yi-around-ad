package com.luomor.yiaroundad.module.user;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.luomor.yiaroundad.adapter.UserPlayGameAdapter;
import com.luomor.yiaroundad.base.RxLazyFragment;
import com.luomor.yiaroundad.entity.user.UserPlayGameInfo;
import com.luomor.yiaroundad.utils.ConstantUtil;
import com.luomor.yiaroundad.widget.CustomEmptyView;
import com.luomor.yiaroundad.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Peter on 2018/06/20 18:19
 * 1097692918@qq.com
 * <p>
 * 用户详情界面的游戏
 */

public class UserPlayGameFragment extends RxLazyFragment {
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;

    private UserPlayGameInfo userPlayGameInfo;
    private List<UserPlayGameInfo.DataBean.GamesBean> games = new ArrayList<>();


    public static UserPlayGameFragment newInstance(UserPlayGameInfo userPlayGameInfo) {
        UserPlayGameFragment mFragment = new UserPlayGameFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(ConstantUtil.EXTRA_DATA, userPlayGameInfo);
        mFragment.setArguments(bundle);
        return mFragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_user_play_game;
    }

    @Override
    public void finishCreateView(Bundle state) {
        userPlayGameInfo = getArguments().getParcelable(ConstantUtil.EXTRA_DATA);
        initRecyclerView();
    }


    @Override
    protected void initRecyclerView() {
        games.addAll(userPlayGameInfo.getData().getGames());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        UserPlayGameAdapter mAdapter = new UserPlayGameAdapter(mRecyclerView, games);
        mRecyclerView.setAdapter(mAdapter);
        if (games.isEmpty()) {
            initEmptyLayout();
        }
    }

    private void initEmptyLayout() {
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_space_no_data);
        mCustomEmptyView.setEmptyText("ㄟ( ▔, ▔ )ㄏ 再怎么找也没有啦");
    }
}
