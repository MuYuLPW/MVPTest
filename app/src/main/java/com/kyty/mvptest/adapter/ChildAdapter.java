package com.kyty.mvptest.adapter;

import android.content.Context;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kyty.mvptest.R;
import com.kyty.mvptest.bean.ListBean;

import java.util.List;

public class ChildAdapter extends BaseQuickAdapter<ListBean, BaseViewHolder> {
    private Context context;
    public ChildAdapter(int layoutResId, @Nullable List<ListBean> data, Context context) {
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
