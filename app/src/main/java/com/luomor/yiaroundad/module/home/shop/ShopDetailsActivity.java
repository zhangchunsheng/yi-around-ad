package com.luomor.yiaroundad.module.home.shop;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.adapter.FoodDetailsCommentAdapter;
import com.luomor.yiaroundad.adapter.FoodDetailsHotCommentAdapter;
import com.luomor.yiaroundad.adapter.FoodDetailsRecommendAdapter;
import com.luomor.yiaroundad.adapter.FoodDetailsSeasonsAdapter;
import com.luomor.yiaroundad.adapter.FoodDetailsSelectionAdapter;
import com.luomor.yiaroundad.adapter.helper.HeaderViewRecyclerAdapter;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.entity.food.FoodDetailsCommentInfo;
import com.luomor.yiaroundad.entity.food.FoodDetailsInfo;
import com.luomor.yiaroundad.entity.food.FoodDetailsRecommendInfo;
import com.luomor.yiaroundad.module.video.VideoDetailsActivity;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.ConstantUtil;
import com.luomor.yiaroundad.utils.LogUtil;
import com.luomor.yiaroundad.utils.NumberUtil;
import com.luomor.yiaroundad.utils.SystemBarHelper;
import com.luomor.yiaroundad.widget.CircleProgressView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import jp.wasabeef.glide.transformations.BlurTransformation;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Peter on 18/6/14 17:51
 * 1097692918@qq.com
 * <p/>
 * 美食店铺详情界面
 */
public class ShopDetailsActivity extends RxBaseActivity {
    @BindView(R.id.nested_scroll_view)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.food_bg)
    ImageView mFoodBackgroundImage;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.food_pic)
    ImageView mFoodPic;
    @BindView(R.id.food_details_layout)
    LinearLayout mDetailsLayout;
    @BindView(R.id.circle_progress)
    CircleProgressView mCircleProgressView;
    @BindView(R.id.food_title)
    TextView mFoodTitle;
    @BindView(R.id.food_update)
    TextView mFoodUpdate;
    @BindView(R.id.food_play)
    TextView mFoodPlay;
    @BindView(R.id.food_selection_recycler)
    RecyclerView mFoodSelectionRecycler;
    @BindView(R.id.tags_layout)
    TagFlowLayout mTagsLayout;
    @BindView(R.id.food_details_introduction)
    TextView mFoodIntroduction;
    @BindView(R.id.tv_update_index)
    TextView mUpdateIndex;
    @BindView(R.id.food_seasons_recycler)
    RecyclerView mFoodSeasonsRecycler;
    @BindView(R.id.food_comment_recycler)
    RecyclerView mFoodCommentRecycler;
    @BindView(R.id.food_recommend_recycler)
    RecyclerView mFoodRecommendRecycler;
    @BindView(R.id.tv_food_comment_count)
    TextView mFoodCommentCount;

    private int seasonId;
    private FoodDetailsInfo.ResultBean result;
    private FoodDetailsCommentInfo.DataBean.PageBean mPageInfo;
    private List<FoodDetailsCommentInfo.DataBean.RepliesBean> replies = new ArrayList<>();
    private List<FoodDetailsCommentInfo.DataBean.HotsBean> hotComments = new ArrayList<>();
    private List<FoodDetailsRecommendInfo.ResultBean.ListBean> foodRecommends = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.activity_food_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            seasonId = intent.getIntExtra(ConstantUtil.EXTRA_BANGUMI_KEY, 0);
        }
        loadData();
    }


    @Override
    public void loadData() {
        RetrofitHelper.getFoodShopAPI()
                .getShopDetails()
                .compose(bindToLifecycle())
                .doOnSubscribe(this::showProgressBar)
                .flatMap(new Func1<FoodDetailsInfo, Observable<FoodDetailsRecommendInfo>>() {
                    @Override
                    public Observable<FoodDetailsRecommendInfo> call(FoodDetailsInfo foodDetailsInfo) {
                        result = foodDetailsInfo.getResult();
                        return RetrofitHelper.getFoodShopAPI().getFoodDetailsRecommend();
                    }
                })
                .compose(bindToLifecycle())
                .map(foodDetailsRecommendInfo -> foodDetailsRecommendInfo.getResult().getLists())
                .flatMap(new Func1<List<FoodDetailsRecommendInfo.ResultBean.ListBean>, Observable<FoodDetailsCommentInfo>>() {
                    @Override
                    public Observable<FoodDetailsCommentInfo> call(List<FoodDetailsRecommendInfo.ResultBean.ListBean> listBeans) {
                        foodRecommends.addAll(listBeans);
                        return RetrofitHelper.getAdAPI().getFoodDetailsComments();
                    }
                })
                .compose(bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(foodDetailsCommentInfo -> {
                    hotComments.addAll(foodDetailsCommentInfo.getResult().getHots());
                    replies.addAll(foodDetailsCommentInfo.getResult().getReplies());
                    mPageInfo = foodDetailsCommentInfo.getResult().getPage();
                    finishTask();
                }, throwable -> {
                    LogUtil.all(throwable.getMessage());
                    hideProgressBar();
                });
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void finishTask() {
        //设置美食封面
        Glide.with(this)
                .load(result.getCover())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.yiaa_default_image_tv)
                .dontAnimate()
                .into(mFoodPic);
        //设置背景高斯模糊图片
        Glide.with(this)
                .load(result.getCover())
                .bitmapTransform(new BlurTransformation(this))
                .into(mFoodBackgroundImage);
        //设置美食标题
        mFoodTitle.setText(result.getTitle());
        //设置美食更新状态
        if (result.getIs_finish().equals("0")) {
            mUpdateIndex.setText("100人推荐");
            mFoodUpdate.setText("推荐");
        } else {
            mUpdateIndex.setText("推荐");
            mFoodUpdate.setText("100人推荐");
        }
        //设置美食播放和追美食数量
        mFoodPlay.setText("播放：" + NumberUtil.converString(Integer.valueOf(result.getPlay_count()))
                + "  " + "收藏：" + NumberUtil.converString(Integer.valueOf(result.getFavorites())));
        //设置美食简介
        mFoodIntroduction.setText(result.getEvaluate());
        //设置评论数量
        mFoodCommentCount.setText("评论 (" + mPageInfo.getAcount() + ")");
        //设置标签布局
        List<FoodDetailsInfo.ResultBean.TagsBean> tags = result.getTags();
        mTagsLayout.setAdapter(new TagAdapter<FoodDetailsInfo.ResultBean.TagsBean>(tags) {
            @Override
            public View getView(FlowLayout parent, int position, FoodDetailsInfo.ResultBean.TagsBean tagsBean) {
                TextView mTags = (TextView) LayoutInflater.from(ShopDetailsActivity.this)
                        .inflate(R.layout.layout_tags_item, parent, false);
                mTags.setText(tagsBean.getTag_name());
                return mTags;
            }
        });
        //设置美食分季版本
        initSeasonsRecycler();
        //设置美食选集
        initSelectionRecycler();
        //设置美食推荐
        initRecommendRecycler();
        //设置美食评论
        initCommentRecycler();
        //加载完毕隐藏进度条
        hideProgressBar();
    }


    /**
     * 初始化评论recyclerView
     */
    private void initCommentRecycler() {
        mFoodCommentRecycler.setHasFixedSize(false);
        mFoodCommentRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mFoodCommentRecycler.setLayoutManager(mLinearLayoutManager);
        FoodDetailsCommentAdapter mCommentAdapter = new FoodDetailsCommentAdapter(mFoodCommentRecycler, replies);
        HeaderViewRecyclerAdapter mHeaderViewRecyclerAdapter = new HeaderViewRecyclerAdapter(mCommentAdapter);
        View headView = LayoutInflater.from(this).inflate(R.layout.layout_video_hot_comment_head, mFoodCommentRecycler, false);
        RecyclerView mHotCommentRecycler = (RecyclerView) headView.findViewById(R.id.hot_comment_recycler);
        mHotCommentRecycler.setHasFixedSize(false);
        mHotCommentRecycler.setNestedScrollingEnabled(false);
        mHotCommentRecycler.setLayoutManager(new LinearLayoutManager(this));
        FoodDetailsHotCommentAdapter mVideoHotCommentAdapter = new FoodDetailsHotCommentAdapter(mHotCommentRecycler, hotComments);
        mHotCommentRecycler.setAdapter(mVideoHotCommentAdapter);
        mHeaderViewRecyclerAdapter.addHeaderView(headView);
        mFoodCommentRecycler.setAdapter(mHeaderViewRecyclerAdapter);
    }


    /**
     * 初始化分季版本recyclerView
     */
    private void initSeasonsRecycler() {
        List<FoodDetailsInfo.ResultBean.SeasonsBean> seasons = result.getSeasons();
        mFoodSeasonsRecycler.setHasFixedSize(false);
        mFoodSeasonsRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mFoodSeasonsRecycler.setLayoutManager(mLinearLayoutManager);
        FoodDetailsSeasonsAdapter mFoodDetailsSeasonsAdapter = new FoodDetailsSeasonsAdapter(mFoodSeasonsRecycler, seasons);
        mFoodSeasonsRecycler.setAdapter(mFoodDetailsSeasonsAdapter);
        for (int i = 0, size = seasons.size(); i < size; i++) {
            if (seasons.get(i).getSeason_id().equals(result.getSeason_id())) {
                mFoodDetailsSeasonsAdapter.notifyItemForeground(i);
            }
        }
    }


    /**
     * 初始化选集recyclerView
     */
    private void initSelectionRecycler() {
        List<FoodDetailsInfo.ResultBean.EpisodesBean> episodes = result.getEpisodes();
        mFoodSelectionRecycler.setHasFixedSize(false);
        mFoodSelectionRecycler.setNestedScrollingEnabled(false);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mLinearLayoutManager.setReverseLayout(true);
        mFoodSelectionRecycler.setLayoutManager(mLinearLayoutManager);
        FoodDetailsSelectionAdapter mFoodDetailsSelectionAdapter = new FoodDetailsSelectionAdapter(mFoodSelectionRecycler, episodes);
        mFoodSelectionRecycler.setAdapter(mFoodDetailsSelectionAdapter);
        mFoodDetailsSelectionAdapter.notifyItemForeground(episodes.size() - 1);
        mFoodSelectionRecycler.scrollToPosition(episodes.size() - 1);
        mFoodDetailsSelectionAdapter.setOnItemClickListener((position, holder) -> {
            mFoodDetailsSelectionAdapter.notifyItemForeground(holder.getLayoutPosition());
            VideoDetailsActivity.launch(ShopDetailsActivity.this,
                    Integer.valueOf(episodes.get(position).getAv_id()), episodes.get(position).getCover());
        });
    }


    /**
     * 初始化美食推荐recyclerView
     */
    private void initRecommendRecycler() {
        mFoodRecommendRecycler.setHasFixedSize(false);
        mFoodRecommendRecycler.setNestedScrollingEnabled(false);
        mFoodRecommendRecycler.setLayoutManager(new GridLayoutManager(ShopDetailsActivity.this, 3));
        FoodDetailsRecommendAdapter mFoodDetailsRecommendAdapter = new FoodDetailsRecommendAdapter(mFoodRecommendRecycler, foodRecommends);
        mFoodRecommendRecycler.setAdapter(mFoodDetailsRecommendAdapter);
    }


    @Override
    public void initToolBar() {
        mToolbar.setTitle("店铺详情");
        setSupportActionBar(mToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }
        //设置Toolbar的透明度
        mToolbar.setBackgroundColor(Color.argb(0, 251, 114, 153));
        //设置StatusBar透明
        SystemBarHelper.immersiveStatusBar(this);
        SystemBarHelper.setPadding(this, mToolbar);
        //获取顶部image高度和toolbar高度
        float imageHeight = getResources().getDimension(R.dimen.food_details_top_layout_height);
        float toolBarHeight = getResources().getDimension(R.dimen.action_bar_default_height);
        mNestedScrollView.setNestedScrollingEnabled(true);
        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {

            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                //根据NestedScrollView滑动改变Toolbar的透明度
                float offsetY = toolBarHeight - imageHeight;
                //计算滑动距离的偏移量
                float offset = 1 - Math.max((offsetY - scrollY) / offsetY, 0f);
                float absOffset = Math.abs(offset);
                //如果滑动距离大于1就设置完全不透明
                if (absOffset >= 1) {
                    absOffset = 1;
                }
                mToolbar.setBackgroundColor(Color.argb((int) (absOffset * 255), 251, 114, 153));
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_food_details, menu);
        return true;
    }


    @Override
    public void showProgressBar() {
        mCircleProgressView.setVisibility(View.VISIBLE);
        mCircleProgressView.spin();
        mDetailsLayout.setVisibility(View.GONE);
    }


    @Override
    public void hideProgressBar() {
        mCircleProgressView.setVisibility(View.GONE);
        mCircleProgressView.stopSpinning();
        mDetailsLayout.setVisibility(View.VISIBLE);
    }


    public static void launch(Activity activity, long shopId) {
        Intent mIntent = new Intent(activity, ShopDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(ConstantUtil.SHOP_ID, shopId);
        mIntent.putExtras(bundle);
        activity.startActivity(mIntent);
    }
}
