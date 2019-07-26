package com.kyty.mvptest.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.kyty.mvptest.BuildConfig;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyUtils {
    public static final String BaseUrl="http://juhe.co/";


    public static Retrofit getRetrofit(String url, final Context context){
        Interceptor requestInterceptor=new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Log.e("tag", "request====" + request.url());
                Response proceed = chain.proceed(request);
                Log.e("tag", "proceed====" + proceed.headers().toString());
                return proceed;
            }
        };
        //打印响应的json
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(
                new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        if (TextUtils.isEmpty(message)) {
                            return;
                        }
                        String s = message.substring(0, 1);
                        Log.e("tag",message);
                    }
                });
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }


        File mFile = new File(context.getCacheDir() + "http");//储存目录
        long maxSize = 10 * 1024 * 1024; // 10 MB 最大缓存数
        Cache mCache = new Cache(mFile, maxSize);

        OkHttpClient mClient = new OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(logging)//应用程序拦截器
                .cache(mCache)//添加缓存
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .client(mClient)//添加OK
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit;
    }
}
