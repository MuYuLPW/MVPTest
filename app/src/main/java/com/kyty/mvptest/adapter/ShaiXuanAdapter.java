package com.kyty.mvptest.adapter;

import android.graphics.Color;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.kyty.mvptest.R;
import com.kyty.mvptest.bean.MainBean;

import java.util.List;

public class ShaiXuanAdapter extends BaseQuickAdapter<MainBean.ClassBean.TagArrBean, BaseViewHolder> {
    public ShaiXuanAdapter(int layoutResId, @Nullable List<MainBean.ClassBean.TagArrBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MainBean.ClassBean.TagArrBean item) {
        helper.setText(R.id.tv,item.getTitle());
        TextView tv=helper.getView(R.id.tv);
        if (item.isChecked()){
            tv.setTextColor(Color.parseColor("#D81B60"));
        }else {
            tv.setTextColor(Color.parseColor("#666666"));
        }
    }
}
