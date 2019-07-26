package com.kyty.mvptest.model;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HttpSeriver {
    @GET("data.php")
    Observable<String> getMainInfo(@Query("is") String is);
}
