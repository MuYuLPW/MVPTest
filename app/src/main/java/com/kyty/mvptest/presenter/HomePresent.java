package com.kyty.mvptest.presenter;

import com.kyty.mvptest.MyApp;
import com.kyty.mvptest.model.HttpCallBack;
import com.kyty.mvptest.model.HttpSeriver;
import com.kyty.mvptest.model.HttpUtils;
import com.kyty.mvptest.mvp.BasePresenter;
import com.kyty.mvptest.mvp.HomeContract;
import com.kyty.mvptest.utils.MyUtils;

import io.reactivex.Observable;

public class HomePresent extends BasePresenter<HomeContract.HomeView> implements HomeContract.HomePresent, HttpCallBack {

    public HomePresent(HomeContract.HomeView view) {
        super(view);
    }

    @Override
    public void getMianInfo(String is) {
        HttpSeriver httpSeriver = MyApp.myApp.retrofit.create(HttpSeriver.class);
        Observable<String> mainInfo = httpSeriver.getMainInfo(is);
        mView.get().ShowLoadding();
        HttpUtils.getHttpRequest(mainInfo,this);
    }

    @Override
    public void onFail(String s) {
        mView.get().HideLoadding();
        mView.get().setError(s);
    }

    @Override
    public void onSuccsee(String s) {
        mView.get().HideLoadding();
        mView.get().setSuccess(s);
    }
}
