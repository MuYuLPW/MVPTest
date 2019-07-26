package com.kyty.mvptest;

import android.app.Application;

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
