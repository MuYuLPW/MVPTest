package com.kyty.mvptest.model;

import android.annotation.SuppressLint;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class HttpUtils {
    @SuppressLint("all")
    public static void getHttpRequest(Observable<String> observable, final HttpCallBack httpCallBack){
        Disposable subscribe = observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
//                .onErrorReturn(new Function<Throwable, String>() {
//                    @Override
//                    public String apply(Throwable throwable) throws Exception {
//                        return throwable.getMessage();
//                    }
//                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        httpCallBack.onSuccsee(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        httpCallBack.onFail(throwable.getMessage());
                    }
                });
    }
}
