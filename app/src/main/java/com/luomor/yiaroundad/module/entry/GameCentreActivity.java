package com.luomor.yiaroundad.module.entry;

import android.content.res.AssetManager;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.LinearLayoutManager;
import androidx.appcompat.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.widget.CircleProgressView;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.adapter.GameCentreAdapter;
import com.luomor.yiaroundad.adapter.helper.HeaderViewRecyclerAdapter;
import com.luomor.yiaroundad.entity.discover.GameCenterInfo;
import com.luomor.yiaroundad.entity.discover.VipGameInfo;
import com.luomor.yiaroundad.module.common.BrowserActivity;
import com.luomor.yiaroundad.network.RetrofitHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 18/6/12 10:16
 * 1097692918@qq.com
 * <p/>
 * 游戏中心界面
 */
public class GameCentreActivity extends RxBaseActivity {
    @BindView(R.id.recycle)
    RecyclerView mRecycle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;

    private List<GameCenterInfo.ItemsBean> items = new ArrayList<>();
    private HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter;
    private VipGameInfo.DataBean mVipGameInfoData;


    @Override
    public int getLayoutId() {
        return R.layout.activity_game_center;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        loadData();
    }


    @Override
    public void loadData() {
        RetrofitHelper.getYiVipAPI()
                .getVipGame()
                .compose(bindToLifecycle())
                .doOnSubscribe(this::showProgressBar)
                .delay(2000, TimeUnit.MILLISECONDS)
                .flatMap(new Func1<VipGameInfo, Observable<String>>() {
                    @Override
                    public Observable<String> call(VipGameInfo vipGameInfo) {
                        mVipGameInfoData = vipGameInfo.getResult();
                        return Observable.just(readAssetsJson());
                    }
                })
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    GameCenterInfo gameCenterInfo = new Gson().fromJson(s, GameCenterInfo.class);
                    items.addAll(gameCenterInfo.getItems());
                    finishTask();
                }, throwable -> hideProgressBar());
    }


    /**
     * 读取assets下的json数据
     */
    private String readAssetsJson() {
        AssetManager assetManager = getAssets();
        try {
            InputStream is = assetManager.open("gamecenter.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder stringBuilder = new StringBuilder();
            String str;
            while ((str = br.readLine()) != null) {
                stringBuilder.append(str);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("游戏中心");
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void showProgressBar() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
        mRecycle.setVisibility(View.GONE);
    }


    @Override
    public void hideProgressBar() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
        mRecycle.setVisibility(View.VISIBLE);
    }


    @Override
    public void finishTask() {
        initRecyclerView();
        hideProgressBar();
    }


    @Override
    public void initRecyclerView() {
        mRecycle.setHasFixedSize(true);
        mRecycle.setLayoutManager(new LinearLayoutManager(GameCentreActivity.this));
        GameCentreAdapter mAdapter = new GameCentreAdapter(mRecycle, items);
        mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mAdapter);
        createHeadView();
        mRecycle.setAdapter(mHeaderViewRecyclerAdapter);
    }


    private void createHeadView() {
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_vip_game_head_view, mRecycle, false);
        ImageView mVipGameImage = (ImageView) headView.findViewById(R.id.vip_game_image);
        Glide.with(GameCentreActivity.this).load(mVipGameInfoData.getImgPath())
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(mVipGameImage);
        mVipGameImage.setOnClickListener(v -> BrowserActivity.launch(GameCentreActivity.this,
                mVipGameInfoData.getLink(), "年度易会员游戏礼包专区"));
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
    }
}
