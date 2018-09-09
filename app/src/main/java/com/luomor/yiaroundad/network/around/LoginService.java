package com.luomor.yiaroundad.network.around;

import com.luomor.yiaroundad.entity.config.ConfigBean;
import com.luomor.yiaroundad.entity.login.LoginBean;
import com.luomor.yiaroundad.entity.login.RegisterBean;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by peterzhang on 09/09/2018.
 */

public interface LoginService {
    /**
     * 首页美食
     */
    @GET("login/login")
    Observable<LoginBean> login();

    /**
     * 首页美食
     */
    @GET("login/register")
    Observable<RegisterBean> register();
}
