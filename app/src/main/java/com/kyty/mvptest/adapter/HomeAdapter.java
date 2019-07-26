package com.kyty.mvptest.adapter;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kyty.mvptest.R;
import com.kyty.mvptest.bean.MainBean;

import java.util.List;

public class HomeAdapter extends BaseQuickAdapter<MainBean.ClassBean, BaseViewHolder> {
    private Context context;
    public HomeAdapter(int layoutResId, @Nullable List<MainBean.ClassBean> data,Context context) {
        super(layoutResId, data);
        this.context=context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MainBean.ClassBean item) {
        TextView textView=helper.getView(R.id.tv);
        textView.setText(item.getTitle());
        if (item.isChecked()){
            textView.setTextColor(context.getResources().getColor(R.color.colorAccent));
        }else {
            textView.setTextColor(Color.parseColor("#666666"));
        }
    }
}
