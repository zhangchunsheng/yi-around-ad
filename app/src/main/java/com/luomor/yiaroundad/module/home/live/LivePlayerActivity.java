package com.luomor.yiaroundad.module.home.live;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.luomor.yiaroundad.base.RxBaseActivity;
import com.luomor.yiaroundad.module.user.UserInfoDetailsActivity;
import com.luomor.yiaroundad.network.RetrofitHelper;
import com.luomor.yiaroundad.utils.ConstantUtil;
import com.luomor.yiaroundad.utils.LogUtil;
import com.luomor.yiaroundad.widget.CircleImageView;
import com.luomor.yiaroundad.widget.livelike.LoveLikeLayout;
import com.luomor.yiaroundad.R;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

/**
 * Created by Peter on 18/6/14 21:18
 * 1097692918@qq.com
 * <p/>
 * 直播播放界面
 */
public class LivePlayerActivity extends RxBaseActivity {
    @BindView(R.id.video_view)
    SurfaceView videoView;
    @BindView(R.id.yiaa_anim)
    ImageView mAnimView;
    @BindView(R.id.right_play)
    ImageView mRightPlayBtn;
    @BindView(R.id.bottom_layout)
    RelativeLayout mBottomLayout;
    @BindView(R.id.bottom_play)
    ImageView mBottomPlayBtn;
    @BindView(R.id.bottom_fullscreen)
    ImageView mBottomFullscreen;
    @BindView(R.id.video_start_info)
    TextView mLoadTv;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.user_pic)
    CircleImageView mUserPic;
    @BindView(R.id.user_name)
    TextView mUserName;
    @BindView(R.id.live_num)
    TextView mLiveNum;
    @BindView(R.id.love_layout)
    LoveLikeLayout mLoveLikeLayout;
    @BindView(R.id.bottom_love)
    ImageView mlove;

    private int flag = 0;
    private int cid;
    private String title;
    private int online;
    private String face;
    private String name;
    private int mid;
    private SurfaceHolder holder;
    private boolean isPlay = false;
    private IjkMediaPlayer ijkMediaPlayer;
    private AnimationDrawable mAnimViewBackground;

    @Override
    public int getLayoutId() {
        return R.layout.activity_live_details;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        Intent intent = getIntent();
        if (intent != null) {
            cid = intent.getIntExtra(ConstantUtil.EXTRA_CID, 0);
            title = intent.getStringExtra(ConstantUtil.EXTRA_TITLE);
            online = intent.getIntExtra(ConstantUtil.EXTRA_ONLINE, 0);
            face = intent.getStringExtra(ConstantUtil.EXTRA_FACE);
            name = intent.getStringExtra(ConstantUtil.EXTRA_NAME);
            mid = intent.getIntExtra(ConstantUtil.EXTRA_MID, 0);
        }
        initVideo();
        initUserInfo();
        startAnim();
    }

    /**
     * 设置用户信息
     */
    private void initUserInfo() {
        Glide.with(LivePlayerActivity.this)
                .load(face)
                .centerCrop()
                .dontAnimate()
                .placeholder(R.drawable.ico_user_default)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(mUserPic);
        mUserName.setText(name);
        mLiveNum.setText(String.valueOf(online));
    }

    private void startAnim() {
        mAnimViewBackground = (AnimationDrawable) mAnimView.getBackground();
        mAnimViewBackground.start();
    }

    private void stopAnim() {
        mAnimViewBackground.stop();
        mAnimView.setVisibility(View.GONE);
        mLoadTv.setVisibility(View.GONE);
    }

    @Override
    public void initToolBar() {
        mToolbar.setTitle(title);
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


    private void initVideo() {
        holder = videoView.getHolder();
        ijkMediaPlayer = new IjkMediaPlayer();
        getLiveUrl();
    }


    private void getLiveUrl() {
        RetrofitHelper.getYiLiveAPI()
                .getLiveUrl(cid)
                .compose(this.bindToLifecycle())
                .map(responseBody -> {
                    try {
                        String str = responseBody.string();
                        //http://i3.yongche.name/media/g2/M01/1D/26/rBEBP1q-I9yIFhbsACHbIxXLhwMAALhDACGphUAIds7350.mp4
                        //http://js.live-play.acgvideo.com/live-js/341670/live_1757863_6687518.flv?wsSecret=6370839b32912657234d31c90d7b5fb5&wsTime=1534939802
                        //http://vali-dns.cp31.ott.cibntv.net/656F990A3B307131C73C5AFD/03000806005A6B2E579A2F2F8F11991B1F0665-871F-44B1-2AE6-BACCF96ACB33.mp4?ccode=0501&duration=391&expire=18000&psid=525123228a042d0995f6d69b818bb316&sp=&ups_client_netip=794556f2&ups_ts=1534939258&ups_userid=18207299&utid=UKaGEpD8Ow8CAXVJkO2XaxCD&vid=XMzM1NjA2OTEyNA%3D%3D&vkey=B4256243befba9655fec62060ead28c3f&s=efbfbd206001d98711ef
                        //https://i2.yongche.name/media/g3/M02/00/00/rBEDA1t9eECAc94qAbA4GwHg_W0365.mp4
                        String result = "http://vali-dns.cp31.ott.cibntv.net/656F990A3B307131C73C5AFD/03000806005A6B2E579A2F2F8F11991B1F0665-871F-44B1-2AE6-BACCF96ACB33.mp4?ccode=0501&duration=391&expire=18000&psid=525123228a042d0995f6d69b818bb316&sp=&ups_client_netip=794556f2&ups_ts=1534939258&ups_userid=18207299&utid=UKaGEpD8Ow8CAXVJkO2XaxCD&vid=XMzM1NjA2OTEyNA%3D%3D&vkey=B4256243befba9655fec62060ead28c3f&s=efbfbd206001d98711ef";
                        return result;
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap(new Func1<String, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(String s) {
                        playVideo(s);
                        return Observable.timer(2000, TimeUnit.MILLISECONDS);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    stopAnim();
                    isPlay = true;
                    videoView.setVisibility(View.VISIBLE);
                    mRightPlayBtn.setImageResource(R.drawable.ic_tv_stop);
                    mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_stop);
                }, throwable -> LogUtil.all("直播地址url获取失败" + throwable.getMessage()));
    }


    private void playVideo(String uri) {
        try {
            ijkMediaPlayer.setDataSource(this, Uri.parse(uri));
            ijkMediaPlayer.setDisplay(holder);
            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                    ijkMediaPlayer.setDisplay(holder);
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                }
            });
            ijkMediaPlayer.prepareAsync();
            ijkMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ijkMediaPlayer.setKeepInBackground(false);
    }


    private void startBottomShowAnim() {
        mBottomLayout.setVisibility(View.VISIBLE);
        mRightPlayBtn.setVisibility(View.VISIBLE);
    }


    private void startBottomHideAnim() {
        mBottomLayout.setVisibility(View.GONE);
        mRightPlayBtn.setVisibility(View.GONE);
    }

    @OnClick({R.id.right_play, R.id.bottom_play, R.id.bottom_fullscreen,
            R.id.video_view, R.id.user_pic, R.id.bottom_love})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.right_play:
                ControlVideo();
                break;
            case R.id.bottom_play:
                ControlVideo();
                break;
            case R.id.bottom_fullscreen:
                break;
            case R.id.video_view:
                if (flag == 0) {
                    startBottomShowAnim();
                    flag = 1;
                } else {
                    startBottomHideAnim();
                    flag = 0;
                }
                break;
            case R.id.user_pic:
                UserInfoDetailsActivity.launch(LivePlayerActivity.this, name, mid, face);
                ControlVideo();
                mRightPlayBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.bottom_love:
                mLoveLikeLayout.addLove();
                break;
        }
    }

    private void ControlVideo() {
        if (isPlay) {
            ijkMediaPlayer.pause();
            isPlay = false;
            mRightPlayBtn.setImageResource(R.drawable.ic_tv_play);
            mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_play);
        } else {
            ijkMediaPlayer.start();
            isPlay = true;
            mRightPlayBtn.setImageResource(R.drawable.ic_tv_stop);
            mBottomPlayBtn.setImageResource(R.drawable.ic_portrait_stop);
        }
    }


    public static void launch(Activity activity, int cid, String title, int online, String face, String name, int mid) {
        Intent mIntent = new Intent(activity, LivePlayerActivity.class);
        mIntent.putExtra(ConstantUtil.EXTRA_CID, cid);
        mIntent.putExtra(ConstantUtil.EXTRA_TITLE, title);
        mIntent.putExtra(ConstantUtil.EXTRA_ONLINE, online);
        mIntent.putExtra(ConstantUtil.EXTRA_FACE, face);
        mIntent.putExtra(ConstantUtil.EXTRA_NAME, name);
        mIntent.putExtra(ConstantUtil.EXTRA_MID, mid);
        activity.startActivity(mIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ijkMediaPlayer.release();
    }
}
