package com.luomor.yiaroundad.module.entry;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.luomor.yiaroundad.base.RxLazyFragment;
import com.luomor.yiaroundad.module.common.MainActivity;
import com.luomor.yiaroundad.widget.CustomEmptyView;
import com.luomor.yiaroundad.R;

import butterknife.BindView;

/**
 * Created by Peter on 16/8/7 14:12
 * 1097692918@qq.com
 * <p/>
 * 关注的人
 */
public class AttentionPeopleFragment extends RxLazyFragment {
    @BindView(R.id.empty_view)
    CustomEmptyView mCustomEmptyView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    public static AttentionPeopleFragment newInstance() {
        return new AttentionPeopleFragment();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_empty;
    }

    @Override
    public void finishCreateView(Bundle state) {
        mToolbar.setTitle("关注的人");
        mToolbar.setNavigationIcon(R.drawable.ic_navigation_drawer);
        mToolbar.setNavigationOnClickListener(v -> {
            Activity activity1 = getActivity();
            if (activity1 instanceof MainActivity) {
                ((MainActivity) activity1).toggleDrawer();
            }
        });
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_no_following_person);
        mCustomEmptyView.setEmptyText("你还没有关注的人哟");
    }
}
