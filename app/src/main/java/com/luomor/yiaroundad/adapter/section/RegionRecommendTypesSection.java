package com.luomor.yiaroundad.adapter.section;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.luomor.yiaroundad.adapter.RegionRecommendTypesAdapter;
import com.luomor.yiaroundad.rx.RxBus;
import com.luomor.yiaroundad.widget.sectioned.StatelessSection;
import com.luomor.yiaroundad.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Peter on 2018/06/21 21:15
 * 1097692918@qq.com
 * <p>
 * 分区推荐分类section
 */

public class RegionRecommendTypesSection extends StatelessSection {
    private Context mContext;
    private String shopType;
    //美食类型Icons
    private int[] foodIcons = new int[]{
            R.drawable.ic_category_t33, R.drawable.ic_category_t32,
            R.drawable.ic_category_t153, R.drawable.ic_category_t51, R.drawable.ic_category_t152
    };
    //美食类型titles
    // todo
    private String[] foodTitles = new String[]{"火锅", "本帮江浙菜", "日本菜", "面包甜点", "咖啡厅"};
    //动画类型Icons
    private int[] animationIcons = new int[]{
            R.drawable.ic_category_t24, R.drawable.ic_category_t25,
            R.drawable.ic_category_t47, R.drawable.ic_category_t27
    };
    //动画类型titles
    private String[] animationTitles = new String[]{"足疗按摩", "洗浴/汗蒸", "KTV", "酒吧"};
    //音乐类型Icons
    private int[] musicIcons = new int[]{
            R.drawable.ic_category_t31, R.drawable.ic_category_t30,
            R.drawable.ic_category_t59, R.drawable.ic_category_t54,
            R.drawable.ic_category_t28, R.drawable.ic_category_t29,
            R.drawable.ic_category_t130
    };
    //音乐类型titles
    private String[] musicTitles = new String[]{"婚纱摄影", "旅拍", "婚宴", "婚礼策划", "婚纱礼服",
            "西服定制", "婚戒首饰"};
    //舞蹈类型Icons
    private int[] danceIcons = new int[]{
            R.drawable.ic_category_t20, R.drawable.ic_category_t154, R.drawable.ic_category_t156
    };
    //舞蹈类型titles
    private String[] danceTitles = new String[]{"电影院", "演出场馆", "剧场/影院"};
    //游戏类型Icons
    private int[] gameIcons = new int[]{
            R.drawable.ic_category_t17, R.drawable.ic_category_t65,
            R.drawable.ic_category_t136, R.drawable.ic_category_t19,
            R.drawable.ic_category_t121, R.drawable.ic_category_game_center2
    };
    //游戏类型titles
    private String[] gameTitles = new String[]{"美发", "美甲美睫", "美容／SPA", "瘦身纤体", "瑜伽", "舞蹈"};
    //科技类型Icons
    private int[] scienceIcons = new int[]{
            R.drawable.ic_category_t37, R.drawable.ic_category_t124,
            R.drawable.ic_category_t122, R.drawable.ic_category_t96,
            R.drawable.ic_category_t95, R.drawable.ic_category_t98
    };
    //科技类型titles
    private String[] scienceTitles = new String[]{"五星级/豪华型", "四星级/高档型", "三星级/舒适型",
            "经济连锁", "情侣酒店", "青年旅社", "客栈"};
    //生活类型Icons
    private int[] lifeIcons = new int[]{
            R.drawable.ic_category_t138, R.drawable.ic_category_t21,
            R.drawable.ic_category_t76, R.drawable.ic_category_t75,
            R.drawable.ic_category_t161, R.drawable.ic_category_t162,
            R.drawable.ic_category_t163
    };
    //生活类型titles
    private String[] lifeTitles = new String[]{"儿童摄影", "孕妇写真", "满月照", "百天照", "上门拍", "全家福", "儿童乐园"};
    //鬼畜类型Icons
    private int[] kichikuIcons = new int[]{
            R.drawable.ic_category_t22, R.drawable.ic_category_t26,
            R.drawable.ic_category_t126, R.drawable.ic_category_t127
    };
    //鬼畜类型titles
    private String[] kichikuTitles = new String[]{"展馆展览", "采摘/农家乐", "温泉", "动植物园"};
    //时尚类型Icons
    private int[] fashionIcons = new int[]{
            R.drawable.ic_category_t157, R.drawable.ic_category_t158,
            R.drawable.ic_category_t159, R.drawable.ic_category_t164
    };
    //时尚类型titles
    private String[] fashionTitles = new String[]{"健身中心", "武术场馆", "游泳馆", "羽毛球馆"};
    //娱乐类型Icons
    private int[] entertainmentIcons = new int[]{
            R.drawable.ic_category_t71, R.drawable.ic_category_t137, R.drawable.ic_category_t131
    };
    //娱乐类型titles
    private String[] entertainmentTitles = new String[]{"综合商场", "服饰鞋包", "运动户外"};
    //电影类型Icons
    private int[] movieIcons = new int[]{
            R.drawable.ic_category_t82, R.drawable.ic_category_t85,
            R.drawable.ic_category_t145, R.drawable.ic_category_t146,
            R.drawable.ic_category_t147, R.drawable.ic_category_t83
    };
    //电影类型titles
    private String[] movieTitles = new String[]{"装修公司", "装修美图", "装修资讯", "全屋定制", "家具定制", "地板"};
    //电视剧类型Icons
    private int[] tvIcons = new int[]{
            R.drawable.ic_category_t15, R.drawable.ic_category_t34,
            R.drawable.ic_category_t86, R.drawable.ic_category_t128
    };
    //电视剧类型titles
    private String[] tvTitles = new String[]{"英语", "日语", "韩语", "雅思托福"};

    public RegionRecommendTypesSection(Context context, String shopType) {
        super(R.layout.layout_region_recommend_types, R.layout.layout_home_recommend_empty);
        this.mContext = context;
        this.shopType = shopType;
    }


    @Override
    public int getContentItemsTotal() {
        return 1;
    }


    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new RegionRecommendTypesSection.EmptyViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
    }


    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new RegionRecommendTypesSection.TypesViewHolder(view);
    }


    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        TypesViewHolder typesViewHolder = (TypesViewHolder) holder;
        typesViewHolder.mRecyclerView.setHasFixedSize(false);
        typesViewHolder.mRecyclerView.setNestedScrollingEnabled(false);
        setRecyclerAdapter(typesViewHolder);
    }


    private void setRecyclerAdapter(TypesViewHolder typesViewHolder) {
        RegionRecommendTypesAdapter mAdapter = null;
        switch (shopType) {
            case "001":
                //美食
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, foodIcons, foodTitles);
                break;
            case "002":
                //动画
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, animationIcons, animationTitles);
                break;
            case "003":
                //音乐
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, musicIcons, musicTitles);
                break;
            case "004":
                //舞蹈
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, danceIcons,
                        danceTitles);
                break;
            case "005":
                //游戏
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, gameIcons, gameTitles);
                break;
            case "006":
                //科技
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, scienceIcons, scienceTitles);
                break;
            case "007":
                //生活
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, lifeIcons, lifeTitles);
                break;
            case "008":
                //鬼畜
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, kichikuIcons, kichikuTitles);
                break;
            case "009":
                //时尚
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, fashionIcons, fashionTitles);
                break;
            case "010":
                //娱乐
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 3));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, entertainmentIcons, entertainmentTitles);
                break;
            case "011":
                //电影
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, movieIcons, movieTitles);
                break;
            case "012":
                //电视剧
                typesViewHolder.mRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 4));
                mAdapter = new RegionRecommendTypesAdapter(typesViewHolder.mRecyclerView, tvIcons, tvTitles);
                break;
        }
        typesViewHolder.mRecyclerView.setAdapter(mAdapter);
        assert mAdapter != null;
        mAdapter.setOnItemClickListener((position, holder) -> RxBus.getInstance().post(position));
    }


    static class TypesViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.types_recycler)
        RecyclerView mRecyclerView;

        TypesViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    static class EmptyViewHolder extends RecyclerView.ViewHolder {
        EmptyViewHolder(View itemView) {
            super(itemView);
        }
    }
}
