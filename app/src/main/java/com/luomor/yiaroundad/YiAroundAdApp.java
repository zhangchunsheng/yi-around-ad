package com.luomor.yiaroundad;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Peter on 18/6/7 21:18
 * 1097692918@qq.com
 * <p/>
 * 易周围动画App
 */
public class YiAroundAdApp extends Application {

    public static YiAroundAdApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        init();
    }

    private void init() {
        //初始化Leak内存泄露检测工具
        LeakCanary.install(this);
        //初始化Stetho调试工具
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                        .build());
    }

    public static YiAroundAdApp getInstance() {
        return mInstance;
    }

}
