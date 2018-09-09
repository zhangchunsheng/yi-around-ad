package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.config.ConfigBean;
import com.luomor.yiaroundad.entity.shop.ShopListInfo;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by peterzhang on 09/09/2018.
 */

public interface ConfigService {
    /**
     * 首页美食
     */
    @GET("config/app")
    Observable<ConfigBean> getConfig();
}
