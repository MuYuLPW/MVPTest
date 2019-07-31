package com.kyty.mvptest.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyty.mvptest.PlayActivity;
import com.kyty.mvptest.R;
import com.kyty.mvptest.adapter.ChildAdapter;
import com.kyty.mvptest.bean.ListBean;
import com.kyty.mvptest.mvp.BaseFragment;
import com.kyty.mvptest.mvp.ChildContract;
import com.kyty.mvptest.presenter.ChildPresent;
import com.kyty.mvptest.view.MainActivity;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScarchFragment extends BaseFragment<ChildPresent> implements ChildContract.ChildView, BaseQuickAdapter.RequestLoadMoreListener {

    private MaterialSearchView searchView;
    private Toolbar toolbar;
    private FrameLayout toolbarContainer;
    private List<ListBean> list=new ArrayList<>();
    private RecyclerView recyclerView;
    private ChildAdapter adapter;
    private int num=0;
    private String query=null;

    @Override
    protected ChildPresent bindPresenter() {
        return new ChildPresent(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container) {
        View inflate = inflater.inflate(R.layout.fragment_search, container, false);
        init(inflate);
        initMenu();
        return inflate;
    }

    private void initMenu() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("搜索");
    }

    private void init(View view) {
        toolbarContainer = view.findViewById(R.id.toolbar_container);
        toolbar = view.findViewById(R.id.toolbar);
        searchView = view.findViewById(R.id.search_view);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void initAdapter() {
        adapter = new ChildAdapter(R.layout.item_recycler,list,activity);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void initListener() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ScarchFragment.this.query=query;
                getData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchView.setSuggestions(new String[]{"a","ab","abc","bcd","bbbd"});

        adapter.setOnLoadMoreListener(this,recyclerView);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ListBean listBean = list.get(position);
                Gson gson=new Gson();
                List<String> strs=gson.fromJson(listBean.getUrl(),new TypeToken<List<String>>(){}.getType());
                String url = strs.get(0);
                Intent intent=new Intent(activity, PlayActivity.class);
                intent.putExtra("url",url);
                startActivity(intent);
            }
        });
    }

    private void getData(String query) {
        Map<String, String> map = new HashMap<>();
        map.put("is", "soudata");
        map.put("sou_class", "视频");
        map.put("info", query);
        map.put("paixu", "0");
        map.put("num",num+"");
        mPresenter.getMianInfo(map);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_item,menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        super.onCreateOptionsMenu(menu, inflater);
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
        Toast.makeText(activity,error,Toast.LENGTH_LONG).show();
        adapter.loadMoreFail();
    }

    @Override
    public void setSuccess(String string) {
        Gson gson = new Gson();
        List<ListBean> beans = gson.fromJson(string, new TypeToken<List<ListBean>>(){}.getType());
        if (beans.size()==0){
            //到底了
            adapter.loadMoreEnd();
            return;
        }
        if (num==0){
            recyclerView.scrollToPosition(0);
            list.clear();
        }
        list.addAll(beans);
        num=list.size();
        adapter.loadMoreComplete();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadMoreRequested() {
        getData(query);
    }
}
