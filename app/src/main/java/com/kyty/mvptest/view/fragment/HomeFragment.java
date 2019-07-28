package com.kyty.mvptest.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kyty.mvptest.R;
import com.kyty.mvptest.adapter.HomeAdapter;
import com.kyty.mvptest.bean.AllBean;
import com.kyty.mvptest.bean.ListBean;
import com.kyty.mvptest.bean.MainBean;
import com.kyty.mvptest.mvp.BaseFragment;
import com.kyty.mvptest.mvp.HomeContract;
import com.kyty.mvptest.presenter.HomePresent;
import com.kyty.mvptest.view.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends BaseFragment<HomePresent> implements HomeContract.HomeView {

    private RecyclerView tabView;
    private FrameLayout home_child;
    private List<MainBean.ClassBean> tabList=new ArrayList<>();
    private HomeAdapter adapter;
    private Map<String,BaseFragment> fragmentList =new HashMap<>();

    @Override
    protected HomePresent bindPresenter() {
        return new HomePresent(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        tabView = view.findViewById(R.id.tab);
        home_child = view.findViewById(R.id.home_child);
        tabView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

    }

    @Override
    public void initAdapter() {
        adapter = new HomeAdapter(R.layout.item_tab,tabList,activity);
        tabView.setAdapter(adapter);
        mPresenter.getMianInfo("main");
    }

    @Override
    public void ShowLoadding() {
        ((MainActivity)activity).ShowLoadding();
    }

    @Override
    public void HideLoadding() {
        ((MainActivity)activity).HideLoadding();
    }

    @Override
    public void setError(String error) {

    }

    @Override
    public void setSuccess(String string) {
        Gson gson=new Gson();
        MainBean mainBean = gson.fromJson(string, MainBean.class);
        List<MainBean.ClassBean> classX = mainBean.getClassX();
        classX.get(0).setChecked(true);
        tabList.addAll(classX);
        adapter.notifyDataSetChanged();
        for (int i = 0; i < tabList.size(); i++) {
            if (i==0){
                List<AllBean> allList=new ArrayList<>();
                allList.add(new AllBean("观看",mainBean.getGuankan()));
                AllBean lunBobean=new AllBean("轮播",new ArrayList<ListBean>());
                for (int j = 0; j < mainBean.getLunbo().size(); j++) {
                    lunBobean.getList().add(mainBean.getLunbo().get(i).getVideodata());
                }
                allList.add(lunBobean);
                allList.add(new AllBean("最新",mainBean.getZuixin()));
                for (int j = 0; j < mainBean.getMain_class().size(); j++) {
                    MainBean.MainClassBean mainClassBean = mainBean.getMain_class().get(j);
                    allList.add(new AllBean(mainClassBean.getTitle(),mainClassBean.getList()));
                }
                AllFragment allFragment=new AllFragment();
                Bundle bundle=new Bundle();
                bundle.putSerializable("bean", (Serializable) allList);
                allFragment.setArguments(bundle);
                addFragment(allFragment,tabList.get(i).getTitle());
                fragmentList.put(tabList.get(i).getTitle(),allFragment);
            }
        }
    }
    private void addFragment(BaseFragment fragment, String name){
        FragmentManager manager = getFragmentManager();
        manager.beginTransaction().replace(R.id.home_child,fragment,name).commit();
    }
}
