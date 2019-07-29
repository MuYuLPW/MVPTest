package com.kyty.mvptest.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.kyty.mvptest.R;
import com.kyty.mvptest.adapter.AllAdapter;
import com.kyty.mvptest.bean.AllBean;
import com.kyty.mvptest.mvp.BaseFragment;
import com.kyty.mvptest.presenter.HomePresent;

import java.io.Serializable;
import java.util.List;

public class AllFragment extends BaseFragment<HomePresent> {

    private List<AllBean> allList;
    private AllAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected HomePresent bindPresenter() {
        return null;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        recyclerView = (RecyclerView) inflater.inflate(R.layout.fragment_all,container,false);
        Bundle bundle = getArguments();
        allList = (List<AllBean>) bundle.getSerializable("bean");
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new AllAdapter(R.layout.item_all,allList,activity);
        recyclerView.setAdapter(adapter);
        return recyclerView;
    }

    @Override
    public void initListener() {

    }
}
