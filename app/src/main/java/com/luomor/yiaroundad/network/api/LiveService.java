package com.luomor.yiaroundad.network.api;

import com.luomor.yiaroundad.entity.live.LiveAppIndexInfo;
import com.luomor.yiaroundad.entity.user.UserLiveRoomStatusInfo;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Peter on 18/6/14 12:03
 * 1097692918@qq.com
 * <p>
 * 直播相关api
 */
public interface LiveService {

    /**
     * 首页直播
     */
    @GET("AppIndex/home?_device=android&_hwid=51e96f5f2f54d5f9&_ulv=10000&access_key=563d6046f06289cbdcb472601ce5a761&appkey=c1b107428d337928&build=410000&platform=android&scale=xxhdpi&sign=fbdcfe141853f7e2c84c4d401f6a8758")
    Observable<LiveAppIndexInfo> getLiveAppIndex();

    /**
     * 直播url
     */
    @GET("api/playurl?player=1&quality=0")
    Observable<ResponseBody> getLiveUrl(@Query("cid") int cid);

    /**
     * 获取直播状态
     */
    @GET("AppRoom/getRoomInfo")
    Observable<UserLiveRoomStatusInfo> getUserLiveRoomStatus(@Query("mid") int mid);
}
