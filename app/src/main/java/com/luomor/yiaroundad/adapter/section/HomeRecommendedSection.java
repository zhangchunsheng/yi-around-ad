package com.luomor.yiaroundad.adapter.section;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.entity.recommend.RecommendInfo;
import com.luomor.yiaroundad.module.home.food.FoodScheduleActivity;
import com.luomor.yiaroundad.module.home.live.LivePlayerActivity;
import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.module.home.food.FoodIndexActivity;
import com.luomor.yiaroundad.module.home.discover.OriginalRankActivity;
import com.luomor.yiaroundad.module.video.VideoDetailsActivity;
import com.luomor.yiaroundad.utils.DisplayUtil;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 18/6/27 11:51
 * 1097692918@qq.com
 * <p/>
 * 首页推荐界面Section
 */
public class HomeRecommendedSection extends StatelessSection {
    private Context mContext;
    private String title;
    private String type;
    private int liveCount;
    private static final String TYPE_RECOMMENDED = "recommend";
    private static final String TYPE_LIVE = "live";
    private static final String TYPE_FOOD = "food_2";
    private static final String GOTO_FOOD = "food_list";
    private static final String TYPE_ACTIVITY = "activity";
    private List<RecommendInfo.ResultBean.BodyBean> datas = new ArrayList<>();
    private final Random mRandom;
    private int[] icons = new int[]{
            R.drawable.ic_header_hot, R.drawable.ic_head_live,
            R.drawable.ic_category_t13, R.drawable.ic_category_t1,
            R.drawable.ic_category_t3, R.drawable.ic_category_t129,
            R.drawable.ic_category_t4, R.drawable.ic_category_t119,
            R.drawable.ic_category_t36, R.drawable.ic_category_t160,
            R.drawable.ic_category_t155, R.drawable.ic_category_t5,
            R.drawable.ic_category_t11, R.drawable.ic_category_t23
    };


    public HomeRecommendedSection(Context context, String title, String type, int liveCount, List<RecommendInfo.ResultBean.BodyBean> datas) {
        super(R.layout.layout_home_recommend_head, R.layout.layout_home_recommend_foot, R.layout.layout_home_recommend_boby);
        this.mContext = context;
        this.title = title;
        this.type = type;
        this.liveCount = liveCount;
        this.datas = datas;
        mRandom = new Random();
    }


    @Override
    public int getContentItemsTotal() {
        return datas.size();
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
        final RecommendInfo.ResultBean.BodyBean bodyBean = datas.get(position);

        Glide.with(mContext)
                .load(Uri.parse(bodyBean.getCover()))
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.yiaa_default_image_tv)
                .dontAnimate()
                .into(itemViewHolder.mVideoImg);

        itemViewHolder.mVideoTitle.setText(bodyBean.getTitle());
        itemViewHolder.mCardView.setOnClickListener(v -> {
            String gotoX = bodyBean.getGotoX();
            switch (gotoX) {
                case TYPE_LIVE:
                    LivePlayerActivity.launch((Activity) mContext,
                            Integer.valueOf(bodyBean.getParam()), bodyBean.getTitle(),
                            bodyBean.getOnline(), bodyBean.getUpFace(), bodyBean.getUp(), 0);
                    break;
                case GOTO_FOOD:
                    break;
                default:
                    int aid = Integer.parseInt(bodyBean.getParam());// Integer.parseInt(bodyBean.getParam())
                    VideoDetailsActivity.launch((Activity) mContext,
                            aid, bodyBean.getCover());
                    break;
            }
        });

        switch (type) {
            case TYPE_LIVE:
                // 直播item
                itemViewHolder.mLiveLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mFoodLayout.setVisibility(View.GONE);
                itemViewHolder.mLiveUp.setText(bodyBean.getUp());
                itemViewHolder.mLiveOnline.setText(String.valueOf(bodyBean.getOnline()));
                break;
            case TYPE_FOOD:
                // 美食item
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mFoodLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mFoodUpdate.setText(bodyBean.getDesc1());
                break;
            case TYPE_ACTIVITY:
                ViewGroup.LayoutParams layoutParams = itemViewHolder.mCardView.getLayoutParams();
                layoutParams.height = DisplayUtil.dp2px(mContext, 200f);
                itemViewHolder.mCardView.setLayoutParams(layoutParams);
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.GONE);
                itemViewHolder.mFoodLayout.setVisibility(View.GONE);
                break;
            default:
                itemViewHolder.mLiveLayout.setVisibility(View.GONE);
                itemViewHolder.mFoodLayout.setVisibility(View.GONE);
                itemViewHolder.mVideoLayout.setVisibility(View.VISIBLE);
                itemViewHolder.mVideoPlayNum.setText(bodyBean.getPlay());
                itemViewHolder.mVideoReviewCount.setText(bodyBean.getDanmaku());
                break;
        }
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
        setTypeIcon(headerViewHolder);
        headerViewHolder.mTypeTv.setText(title);
        headerViewHolder.mTypeRankBtn.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, OriginalRankActivity.class)));
        switch (type) {
            case TYPE_RECOMMENDED:
                headerViewHolder.mTypeMore.setVisibility(View.GONE);
                headerViewHolder.mTypeRankBtn.setVisibility(View.VISIBLE);
                headerViewHolder.mAllLiveNum.setVisibility(View.GONE);
                break;
            case TYPE_LIVE:
                headerViewHolder.mTypeRankBtn.setVisibility(View.GONE);
                headerViewHolder.mTypeMore.setVisibility(View.VISIBLE);
                headerViewHolder.mAllLiveNum.setVisibility(View.VISIBLE);
                SpannableStringBuilder stringBuilder = new SpannableStringBuilder("当前" + liveCount + "个直播");
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(
                        mContext.getResources().getColor(R.color.pink_text_color));
                stringBuilder.setSpan(foregroundColorSpan, 2,
                        stringBuilder.length() - 3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                headerViewHolder.mAllLiveNum.setText(stringBuilder);
                break;
            default:
                headerViewHolder.mTypeRankBtn.setVisibility(View.GONE);
                headerViewHolder.mTypeMore.setVisibility(View.VISIBLE);
                headerViewHolder.mAllLiveNum.setVisibility(View.GONE);
                break;
        }
    }


    /**
     * 根据title设置typeIcon
     */
    private void setTypeIcon(HeaderViewHolder headerViewHolder) {
        switch (title) {
            case "美食":
                headerViewHolder.mTypeImg.setImageResource(icons[0]);
                break;
            case "休闲娱乐":
                headerViewHolder.mTypeImg.setImageResource(icons[1]);
                break;
            case "结婚":
                headerViewHolder.mTypeImg.setImageResource(icons[2]);
                break;
            case "电影演出赛事":
                headerViewHolder.mTypeImg.setImageResource(icons[3]);
                break;
            case "丽人":
                headerViewHolder.mTypeImg.setImageResource(icons[4]);
                break;
            case "酒店":
                headerViewHolder.mTypeImg.setImageResource(icons[5]);
                break;
            case "亲子":
                headerViewHolder.mTypeImg.setImageResource(icons[6]);
                break;
            case "周边游":
                headerViewHolder.mTypeImg.setImageResource(icons[7]);
                break;
            case "运动健身":
                headerViewHolder.mTypeImg.setImageResource(icons[8]);
                break;
            case "购物":
                headerViewHolder.mTypeImg.setImageResource(icons[9]);
                break;
            case "家装":
                headerViewHolder.mTypeImg.setImageResource(icons[10]);
                break;
            case "学习培训":
                headerViewHolder.mTypeImg.setImageResource(icons[11]);
                break;
            case "生活服务":
                headerViewHolder.mTypeImg.setImageResource(icons[12]);
                break;
            case "医疗健康":
                headerViewHolder.mTypeImg.setImageResource(icons[13]);
                break;
        }
    }


    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(View view) {
        return new FootViewHolder(view);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onBindFooterViewHolder(RecyclerView.ViewHolder holder) {
        final FootViewHolder footViewHolder = (FootViewHolder) holder;
        footViewHolder.mDynamic.setText(String.valueOf(mRandom.nextInt(200)) + "条新动态,点这里刷新");
        footViewHolder.mRefreshBtn.setOnClickListener(v -> footViewHolder.mRefreshBtn
                .animate()
                .rotation(360)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000).start());
        footViewHolder.mRecommendRefresh.setOnClickListener(v -> footViewHolder.mRecommendRefresh
                .animate()
                .rotation(360)
                .setInterpolator(new LinearInterpolator())
                .setDuration(1000).start());
        footViewHolder.mFoodIndexBtn.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, FoodIndexActivity.class)));
        footViewHolder.mFoodTimelineBtn.setOnClickListener(v -> mContext.startActivity(
                new Intent(mContext, FoodScheduleActivity.class)));
        switch (type) {
            case TYPE_RECOMMENDED:
                footViewHolder.mMoreBtn.setVisibility(View.GONE);
                footViewHolder.mRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mFoodLayout.setVisibility(View.GONE);
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.VISIBLE);
                break;
            case TYPE_FOOD:
                footViewHolder.mMoreBtn.setVisibility(View.GONE);
                footViewHolder.mRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mFoodLayout.setVisibility(View.VISIBLE);
                break;
            case TYPE_ACTIVITY:
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mFoodLayout.setVisibility(View.GONE);
                footViewHolder.mMoreBtn.setVisibility(View.GONE);
                footViewHolder.mRefreshLayout.setVisibility(View.GONE);
                break;
            default:
                footViewHolder.mRecommendRefreshLayout.setVisibility(View.GONE);
                footViewHolder.mFoodLayout.setVisibility(View.GONE);
                footViewHolder.mMoreBtn.setVisibility(View.VISIBLE);
                footViewHolder.mRefreshLayout.setVisibility(View.VISIBLE);
                break;
        }
    }


    static class HeaderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_type_img)
        ImageView mTypeImg;
        @BindView(R.id.item_type_tv)
        TextView mTypeTv;
        @BindView(R.id.item_type_more)
        TextView mTypeMore;
        @BindView(R.id.item_type_rank_btn)
        TextView mTypeRankBtn;
        @BindView(R.id.item_live_all_num)
        TextView mAllLiveNum;

        HeaderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.card_view)
        CardView mCardView;
        @BindView(R.id.video_preview)
        ImageView mVideoImg;
        @BindView(R.id.video_title)
        TextView mVideoTitle;
        @BindView(R.id.video_play_num)
        TextView mVideoPlayNum;
        @BindView(R.id.video_review_count)
        TextView mVideoReviewCount;
        @BindView(R.id.layout_live)
        RelativeLayout mLiveLayout;
        @BindView(R.id.layout_video)
        LinearLayout mVideoLayout;
        @BindView(R.id.item_live_up)
        TextView mLiveUp;
        @BindView(R.id.item_live_online)
        TextView mLiveOnline;
        @BindView(R.id.layout_food)
        RelativeLayout mFoodLayout;
        @BindView(R.id.item_food_update)
        TextView mFoodUpdate;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class FootViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_btn_more)
        Button mMoreBtn;
        @BindView(R.id.item_dynamic)
        TextView mDynamic;
        @BindView(R.id.item_btn_refresh)
        ImageView mRefreshBtn;
        @BindView(R.id.item_refresh_layout)
        LinearLayout mRefreshLayout;
        @BindView(R.id.item_recommend_refresh_layout)
        LinearLayout mRecommendRefreshLayout;
        @BindView(R.id.item_recommend_refresh)
        ImageView mRecommendRefresh;
        @BindView(R.id.item_food_layout)
        LinearLayout mFoodLayout;
        @BindView(R.id.item_btn_food_index)
        ImageView mFoodIndexBtn;
        @BindView(R.id.item_btn_food_timeline)
        ImageView mFoodTimelineBtn;

        FootViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
