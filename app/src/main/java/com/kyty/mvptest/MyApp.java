package com.kyty.mvptest;

import android.app.Application;

import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;
import com.kyty.mvptest.utils.MyUtils;

import retrofit2.Retrofit;

public class MyApp extends Application {
    public static MyApp myApp;
    public Retrofit retrofit;
    @Override
    public void onCreate() {
        super.onCreate();
        myApp=this;
        retrofit= MyUtils.getRetrofit(MyUtils.BaseUrl,this);

    }
}
