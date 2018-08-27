package com.luomor.yiaroundad.module.home.shop;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.luomor.yiaroundad.R;
import com.luomor.yiaroundad.adapter.section.HomeFoodBannerSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodBodySection;
import com.luomor.yiaroundad.adapter.section.HomeFoodItemSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodRecommendSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodSeasonNewSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopBodySection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopNewSerialSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopRecommendSection;
import com.luomor.yiaroundad.adapter.section.HomeFoodShopSeasonNewSection;
import com.luomor.yiaroundad.base.RxLazyFragment;
import com.luomor.yiaroundad.entity.shop.FoodShopRecommendInfo;
import com.luomor.yiaroundad.entity.shop.ShopListInfo;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.SnackbarUtil;
import com.luomor.yiaroundad.widget.CustomEmptyView;
import com.luomor.yiaroundad.widget.banner.BannerEntity;
import com.luomor.yiaroundad.widget.sectioned.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

/**
 * Created by peterzhang on 20/08/2018.
 *
 * 首页美食店铺页面
 */

public class HomeFoodShopFragment extends RxLazyFragment {
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycle)
    RecyclerView mRecyclerView;
    @BindView(R.id.empty_layout)
    CustomEmptyView mCustomEmptyView;

    private int season;
    private boolean mIsRefreshing = false;
    private String shopType = "001";
    private static Map<String, String> SHOPTYPES = new HashMap<String, String>();
    private List<BannerEntity> bannerList = new ArrayList<>();
    private SectionedRecyclerViewAdapter mSectionedRecyclerViewAdapter;
    private List<FoodShopRecommendInfo.ResultBean> foodRecommends = new ArrayList<>();
    private List<ShopListInfo.ResultBean.AdBean.HeadBean> banners = new ArrayList<>();
    private List<ShopListInfo.ResultBean.AdBean.BodyBean> foodbodys = new ArrayList<>();
    private List<ShopListInfo.ResultBean.PreviousBean.ListBean> seasonNewFoods = new ArrayList<>();
    private List<ShopListInfo.ResultBean.SerializingBean> newFoodSerials = new ArrayList<>();
    private List<ShopListInfo.ResultBean.ShopBean> shops = new ArrayList<>();

    public static final int LOCATION_CODE = 10;

    //定位都要通过LocationManager这个类实现
    private LocationManager locationManager;
    LocationProvider locationProvider;
    private String provider;
    Location location = null;
    private double latitude = 0;
    private double longitude = 0;

    public static HomeFoodShopFragment newInstance(String shopType) {
        HomeFoodShopFragment fragment = new HomeFoodShopFragment();
        fragment.shopType = shopType;
        HomeFoodShopFragment.SHOPTYPES.put("001", "美食");
        HomeFoodShopFragment.SHOPTYPES.put("004", "电影");
        HomeFoodShopFragment.SHOPTYPES.put("012", "书店");
        return fragment;
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_home_food_shop;
    }

    @Override
    public void finishCreateView(Bundle state) {
        //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
        if (ContextCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            ActivityCompat.requestPermissions(this.getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
        } else {
            accessLocation();
        }
        isPrepared = true;
        lazyLoad();
    }

    private void accessLocation() {
        this.locationManager = (LocationManager) this.getActivity().getSystemService(Context.LOCATION_SERVICE);
        this.locationProvider = this.locationManager.getProvider(LocationManager.GPS_PROVIDER);
        if (locationProvider != null) {
            Toast.makeText(this.getActivity(), "Location listener registered!", Toast.LENGTH_SHORT).show();
            try {
                this.locationManager.requestLocationUpdates(locationProvider.getName(), 0, 0,
                        this.locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this.getActivity(),
                    "Location Provider is not avilable at the moment!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            final double latitude = (location.getLatitude());
            final double longitude = location.getLongitude();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d("com.luomor.yiaroundad", "latitude: " + latitude + ", longitude: " + longitude);
                }
            }).start();
            //kod obslugi najlepiej w osobnym wątku...
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Override
    public void onStop() {
        super.onStop();
        if (locationProvider != null) {
            Toast.makeText(this.getActivity(), "Location listener unregistered!", Toast.LENGTH_SHORT).show();
            try {
                this.locationManager.removeUpdates(this.locationListener);
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this.getActivity(), "Location Provider is not avilable at the moment!",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 权限被用户同意。
                    // 执形我们想要的操作
                    accessLocation();
                } else {
                    // 权限被用户拒绝了。
                    //若是点击了拒绝和不再提醒
                    //关于shouldShowRequestPermissionRationale
                    // 1、当用户第一次被询问是否同意授权的时候，返回false
                    // 2、当之前用户被询问是否授权，点击了false,并且点击了不在询问（第一次询问不会出现“不再询问”的选项），
                    // 之后便会返回false
                    // 3、当用户被关闭了app的权限，该app不允许授权的时候，返回false
                    // 4、当用户上一次不同意授权，没有点击“不再询问”的时候，下一次返回true
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                            || !ActivityCompat.shouldShowRequestPermissionRationale(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {
                        //提示用户前往设置界面自己打开权限
                        Toast.makeText(this.getActivity(), "请前往设置界面打开定位权限", Toast.LENGTH_SHORT).show();
                        return;
                    }

                }
            }
        }
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        initRefreshLayout();
        initRecyclerView();
        isPrepared = false;
    }


    @Override
    protected void initRecyclerView() {
        mSectionedRecyclerViewAdapter = new SectionedRecyclerViewAdapter();
        GridLayoutManager mGridLayoutManager = new GridLayoutManager(getActivity(), 3);
        mGridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                switch (mSectionedRecyclerViewAdapter.getSectionItemViewType(position)) {
                    case SectionedRecyclerViewAdapter.VIEW_TYPE_HEADER:
                        return 3;
                    default:
                        return 1;
                }
            }
        });
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mSectionedRecyclerViewAdapter);
        setRecycleNoScroll();
    }


    @Override
    protected void initRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(() -> {
            mSwipeRefreshLayout.setRefreshing(true);
            mIsRefreshing = true;
            loadData();
        });
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            clearData();
            loadData();
        });
    }

    private void clearData() {
        mIsRefreshing = true;
        banners.clear();
        bannerList.clear();
        foodbodys.clear();
        foodRecommends.clear();
        newFoodSerials.clear();
        shops.clear();
        seasonNewFoods.clear();
        mSectionedRecyclerViewAdapter.removeAllSections();
    }


    @Override
    protected void loadData() {
        String shopType = this.shopType;
        RetrofitHelper.getFoodShopAPI()
                .getShopList(shopType, this.latitude, this.longitude)
                .compose(bindToLifecycle())
                .flatMap(new Func1<ShopListInfo, Observable<FoodShopRecommendInfo>>() {
                    @Override
                    public Observable<FoodShopRecommendInfo> call(ShopListInfo shopListInfo) {
                        banners.addAll(shopListInfo.getResult().getAd().getHead());
                        foodbodys.addAll(shopListInfo.getResult().getAd().getBody());
                        seasonNewFoods.addAll(shopListInfo.getResult().getPrevious().getList());
                        season = shopListInfo.getResult().getPrevious().getSeason();
                        newFoodSerials.addAll(shopListInfo.getResult().getSerializing());
                        shops.addAll(shopListInfo.getResult().getShops());
                        return RetrofitHelper.getFoodShopAPI().getFoodRecommended(shopType);
                    }
                })
                .compose(bindToLifecycle())
                .map(FoodShopRecommendInfo::getResult)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resultBeans -> {
                    foodRecommends.addAll(resultBeans);
                    finishTask();
                }, throwable -> initEmptyView());
    }


    @Override
    protected void finishTask() {
        mSwipeRefreshLayout.setRefreshing(false);
        mIsRefreshing = false;
        hideEmptyView();
        Observable.from(banners)
                .compose(bindToLifecycle())
                .forEach(bannersBean -> bannerList.add(new BannerEntity(
                        bannersBean.getLink(), bannersBean.getTitle(), bannersBean.getImg())));
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodBannerSection(bannerList));
        if(this.shopType.equals("001")) {
            mSectionedRecyclerViewAdapter.addSection(new HomeFoodItemSection(getActivity()));
        }
        String shopTypeName = HomeFoodShopFragment.SHOPTYPES.get(this.shopType);
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopNewSerialSection(getActivity(), shops, shopTypeName));
        if (!foodbodys.isEmpty()) {
            mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopBodySection(getActivity(), foodbodys));
        }
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopSeasonNewSection(getActivity(), season, seasonNewFoods, shopTypeName));
        mSectionedRecyclerViewAdapter.addSection(new HomeFoodShopRecommendSection(getActivity(), foodRecommends, shopTypeName));
        mSectionedRecyclerViewAdapter.notifyDataSetChanged();
    }


    public void initEmptyView() {
        mSwipeRefreshLayout.setRefreshing(false);
        mCustomEmptyView.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
        mCustomEmptyView.setEmptyImage(R.drawable.img_tips_error_load_error);
        mCustomEmptyView.setEmptyText("加载失败~(≧▽≦)~啦啦啦.");
        SnackbarUtil.showMessage(mRecyclerView, "数据加载失败,请重新加载或者检查网络是否链接");
    }


    public void hideEmptyView() {
        mCustomEmptyView.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private void setRecycleNoScroll() {
        mRecyclerView.setOnTouchListener((v, event) -> mIsRefreshing);
    }
}
