package com.kyty.mvptest.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment<P extends IPresenter> extends Fragment implements IView {

    private View rootView;
    protected P mPresenter;
    protected Activity activity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView==null){
            mPresenter=bindPresenter();
            rootView=initView(inflater,container);
            initAdapter();
            initListener();
        }
        return rootView;
    }
    protected abstract P bindPresenter();

    public void initListener() {

    }

    public void initAdapter() {

    }

    public abstract View initView(LayoutInflater inflater, ViewGroup container);

    @Override
    public Activity getSelfActivity() {
        return activity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity=activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mPresenter!=null){
            mPresenter.detachView();
        }
    }
}
