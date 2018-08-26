package com.luomor.yiaroundad.network.api;

import com.luomor.yiaroundad.entity.food.NewFoodSerialInfo;
import com.luomor.yiaroundad.entity.video.HDVideoInfo;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Peter on 18/6/30 19:52
 * 1097692918@qq.com
 * <p>
 * yiaroundad-go相关api
 */
public interface YiAdGoService {

    /**
     * 网站高清视频
     * quailty:清晰度(1~2，根据视频有不同)
     * type: 格式(mp4/flv)
     */
    @GET("/video/{cid}")
    Observable<HDVideoInfo> getHDVideoUrl(@Path("cid") int cid, @Query("quailty") int quailty, @Query("type") String type);

    @GET("video/getHDVideo")
    Observable<HDVideoInfo> getHDVideo(@Query("cid") int cid, @Query("quailty") int quailty, @Query("type") String type);

    /**
     * 新美食系列
     */
    @GET("food")
    Observable<NewFoodSerialInfo> getNewFoodSerialList();
}