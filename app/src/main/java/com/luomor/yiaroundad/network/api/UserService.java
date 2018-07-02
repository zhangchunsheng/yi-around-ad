package com.luomor.yiaroundad.network.api;

import com.luomor.yiaroundad.entity.user.UserCoinsInfo;
import com.luomor.yiaroundad.entity.user.UserContributeInfo;
import com.luomor.yiaroundad.entity.user.UserPlayGameInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Peter on 2018/06/20 22:40
 * 1097692918@qq.com
 * <p>
 * 用户相关api
 */

public interface UserService {

    /**
     * 用户所玩游戏
     */
    @GET("ajax/game/GetLastPlay")
    Observable<UserPlayGameInfo> getUserPlayGames(@Query("mid") int mid);

    /**
     * 用户投币视频
     */
    @GET("ajax/member/getCoinVideos")
    Observable<UserCoinsInfo> getUserCoinVideos(@Query("mid") int mid);


    /**
     * 用户投稿视频
     */
    @GET("ajax/member/getSubmitVideos")
    Observable<UserContributeInfo> getUserContributeVideos(
            @Query("mid") int mid, @Query("page") int page, @Query("pagesize") int pageSize);
}
