package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.discover.VipGameInfo;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peterzhang on 09/09/2018.
 */

public interface YiVipService {
    @GET("game/vip")
    Observable<VipGameInfo> getVipGame();
}
