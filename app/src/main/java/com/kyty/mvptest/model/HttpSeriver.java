package com.kyty.mvptest.model;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface HttpSeriver {
    @GET("data.php")
    Observable<String> getMainInfo(@Query("is") String is);

    @GET("data.php")
    Observable<String> getChildInfo(@QueryMap Map<String,String> map);
}
