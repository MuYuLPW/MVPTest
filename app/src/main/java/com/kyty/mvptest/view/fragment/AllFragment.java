package com.kyty.mvptest.view.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.kyty.mvptest.R;
import com.kyty.mvptest.mvp.BaseFragment;
import com.kyty.mvptest.presenter.HomePresent;

public class AllFragment extends BaseFragment<HomePresent> {
    @Override
    protected HomePresent bindPresenter() {
        return null;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        RecyclerView recyclerView= (RecyclerView) inflater.inflate(R.layout.fragment_all,container,false);
        return recyclerView;
    }
}
