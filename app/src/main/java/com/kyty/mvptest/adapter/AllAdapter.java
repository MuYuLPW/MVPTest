package com.kyty.mvptest.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kyty.mvptest.PlayActivity;
import com.kyty.mvptest.bean.AllBean;
import com.kyty.mvptest.bean.ListBean;
import com.kyty.mvptest.custom.MyTabView;

import java.util.List;

public class AllAdapter extends BaseQuickAdapter<AllBean, BaseViewHolder> {
    Context context;
    public AllAdapter(int layoutResId, @Nullable List<AllBean> data,Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, AllBean item) {
        MyTabView myTabView= (MyTabView) helper.itemView;
        myTabView.setTitle(item.getTitle());
        myTabView.setList(item.getList());
        myTabView.setOnItemClickListener(new MyTabView.OnItemClickListener() {
            @Override
            public void onItemClick(ListBean listBean) {
                Gson gson=new Gson();
                List<String> strs=gson.fromJson(listBean.getUrl(),new TypeToken<List<String>>(){}.getType());
                String url = strs.get(0);
                Intent intent=new Intent(context, PlayActivity.class);
                intent.putExtra("url",url);
                context.startActivity(intent);
            }
        });
    }
}
