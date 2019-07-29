package com.kyty.mvptest.presenter;

import com.kyty.mvptest.MyApp;
import com.kyty.mvptest.model.HttpCallBack;
import com.kyty.mvptest.model.HttpSeriver;
import com.kyty.mvptest.model.HttpUtils;
import com.kyty.mvptest.mvp.BasePresenter;
import com.kyty.mvptest.mvp.ChildContract;

import java.util.Map;

import io.reactivex.Observable;

public class ChildPresent extends BasePresenter<ChildContract.ChildView> implements ChildContract.ChildPresent, HttpCallBack {
    public ChildPresent(ChildContract.ChildView view) {
        super(view);
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

    @Override
    public void getMianInfo(Map<String, String> map) {
        HttpSeriver httpSeriver = MyApp.myApp.retrofit.create(HttpSeriver.class);
        Observable<String> mainInfo = httpSeriver.getChildInfo(map);
        mView.get().ShowLoadding();
        HttpUtils.getHttpRequest(mainInfo,this);
    }
}
