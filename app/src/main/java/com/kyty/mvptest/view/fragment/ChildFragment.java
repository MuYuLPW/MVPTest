package com.kyty.mvptest.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyty.mvptest.PlayActivity;
import com.kyty.mvptest.R;
import com.kyty.mvptest.adapter.ChildAdapter;
import com.kyty.mvptest.adapter.ShaiXuanAdapter;
import com.kyty.mvptest.bean.ListBean;
import com.kyty.mvptest.bean.MainBean;
import com.kyty.mvptest.mvp.BaseFragment;
import com.kyty.mvptest.mvp.ChildContract;
import com.kyty.mvptest.presenter.ChildPresent;
import com.kyty.mvptest.presenter.HomePresent;
import com.kyty.mvptest.view.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChildFragment extends BaseFragment<ChildPresent> implements ChildContract.ChildView, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private RecyclerView child_tag;
    private RecyclerView shaixuan_tag;
    private SwipeRefreshLayout swip;
    private RecyclerView recyclerView;
    private String is = "getmainlist";
    private int page = 0;
    private String fenlei = "";
    private String clazz = "";
    private String paixu = "新上架";
    private List<MainBean.ClassBean.TagArrBean> childList = new ArrayList<>();
    private List<MainBean.ClassBean.TagArrBean> shaixuanList = new ArrayList<>();
    private ShaiXuanAdapter childAdapter;
    private ShaiXuanAdapter shaiXuanAdapter;
    private List<ListBean> listBeans =new ArrayList<>();
    private ChildAdapter adapter;

    @Override
    protected ChildPresent bindPresenter() {
        return new ChildPresent(this);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_child, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        Bundle arguments = getArguments();
        MainBean.ClassBean classBean = (MainBean.ClassBean) arguments.getSerializable("classBean");
        child_tag = view.findViewById(R.id.child_tag);
        shaixuan_tag = view.findViewById(R.id.shaixuan_tag);
        swip = view.findViewById(R.id.swip);
        recyclerView = view.findViewById(R.id.recyclerView);
        child_tag.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        shaixuan_tag.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        clazz = classBean.getClass_id();
        childList.addAll(classBean.getTag_arr());
        fenlei = childList.get(0).getTitle();
        childList.get(0).setChecked(true);
        MainBean.ClassBean.TagArrBean arrBean = new MainBean.ClassBean.TagArrBean("新上架", "新上架");
        arrBean.setChecked(true);
        shaixuanList.add(arrBean);
        shaixuanList.add(new MainBean.ClassBean.TagArrBean("热播榜", "热播榜"));
        shaixuanList.add(new MainBean.ClassBean.TagArrBean("好评榜", "好评榜"));
    }

    @Override
    public void initAdapter() {
        childAdapter = new ShaiXuanAdapter(R.layout.item_tab, childList);
        shaiXuanAdapter = new ShaiXuanAdapter(R.layout.item_tab, shaixuanList);
        child_tag.setAdapter(childAdapter);
        shaixuan_tag.setAdapter(shaiXuanAdapter);

        adapter = new ChildAdapter(R.layout.item_recycler,listBeans,activity);
        recyclerView.setAdapter(adapter);
        getData();
    }

    @Override
    public void initListener() {
        childAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MainBean.ClassBean.TagArrBean tagArrBean = childList.get(position);
                if (tagArrBean.isChecked()){
                    return;
                }
                for (MainBean.ClassBean.TagArrBean bean:childList) {
                    bean.setChecked(false);
                }
                tagArrBean.setChecked(true);
                fenlei=tagArrBean.getInfo();
                page=0;
                childAdapter.notifyDataSetChanged();
                getData();
            }
        });
        shaiXuanAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MainBean.ClassBean.TagArrBean tagArrBean = shaixuanList.get(position);
                if (tagArrBean.isChecked()){
                    return;
                }
                for (MainBean.ClassBean.TagArrBean bean:shaixuanList) {
                    bean.setChecked(false);
                }
                tagArrBean.setChecked(true);
                paixu=tagArrBean.getInfo();
                page=0;
                shaiXuanAdapter.notifyDataSetChanged();
                getData();
            }
        });

        swip.setOnRefreshListener(this);
        adapter.setOnLoadMoreListener(this,recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ListBean listBean = listBeans.get(position);
                Gson gson=new Gson();
                List<String> strs=gson.fromJson(listBean.getUrl(),new TypeToken<List<String>>(){}.getType());
                String url = strs.get(0);
                Intent intent=new Intent(activity, PlayActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("is", is);
        map.put("page", page + "");
        map.put("fenlei", fenlei);
        map.put("class", clazz);
        map.put("paixu", paixu);
        mPresenter.getMianInfo(map);
    }

    @Override
    public void ShowLoadding() {
        if (page==0){
            swip.setRefreshing(true);
        }
    }

    @Override
    public void HideLoadding() {
        swip.setRefreshing(false);
    }

    @Override
    public void setError(String error) {
        swip.setEnabled(true);
        Toast.makeText(activity, error, Toast.LENGTH_LONG).show();
        if (page!=1){
            adapter.loadMoreFail();
        }

    }

    @Override
    public void setSuccess(String string) {
        swip.setEnabled(true);
        Gson gson = new Gson();
        List<ListBean> beans = gson.fromJson(string, new TypeToken<List<ListBean>>(){}.getType());
        if (beans.size()==0){
            //到底了
            adapter.loadMoreEnd();
            return;
        }
        if (page==0){
            recyclerView.scrollToPosition(0);
            listBeans.clear();
        }
        listBeans.addAll(beans);
        page=listBeans.size();
        adapter.loadMoreComplete();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh() {
        page=0;
        getData();
    }

    @Override
    public void onLoadMoreRequested() {
        swip.setEnabled(false);
        getData();
    }
}
