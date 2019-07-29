package com.kyty.mvptest.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kyty.mvptest.R;
import com.kyty.mvptest.bean.ListBean;

import java.util.ArrayList;
import java.util.List;

public class MyTabView extends LinearLayout {

    private TextView title;
    private RecyclerView recyclerView;
    private List<ListBean> list=new ArrayList<>();
    private MyAdapter adapter;
    private OnItemClickListener onItemClickListener;

    public MyTabView(Context context) {
        super(context);
        init(context);
    }

    public MyTabView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void setOnItemClickListener(OnItemClickListener clickListener){
        this.onItemClickListener=clickListener;
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.my_tab_view,this,true);
        title = findViewById(R.id.title);
        recyclerView = findViewById(R.id.view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        adapter = new MyAdapter(R.layout.item_recycler,list,context);
        recyclerView.setAdapter(adapter);
        initListener();
    }

    private void initListener() {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ListBean listBean = list.get(position);
                if (onItemClickListener!=null){
                    onItemClickListener.onItemClick(listBean);
                }
            }
        });
    }

    public void setTitle(String string){
        title.setText(string);
    }
    public void setList(List<ListBean> list){
        this.list.clear();
        this.list.addAll(list);
        adapter.notifyDataSetChanged();
    }


    public interface OnItemClickListener{
        void onItemClick(ListBean listBean);
    }

    class MyAdapter extends BaseQuickAdapter<ListBean, BaseViewHolder>{
        Context context;
        public MyAdapter(int layoutResId, @Nullable List<ListBean> data,Context context) {
            super(layoutResId, data);
            this.context=context;
        }

        @Override
        protected void convert(BaseViewHolder helper, ListBean item) {
            helper.setText(R.id.title,item.getTitle());
            helper.setText(R.id.cont,item.getPlaynum());
            ImageView iv=helper.getView(R.id.iv);
            Glide.with(context).load(item.getImg()).into(iv);
        }
    }
}
