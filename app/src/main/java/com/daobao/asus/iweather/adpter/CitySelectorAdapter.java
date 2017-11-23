package com.daobao.asus.iweather.adpter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daobao.asus.iweather.R;

import java.util.List;

/**
 * Created by db on 2017/11/23.
 */

public class CitySelectorAdapter extends BaseQuickAdapter<String,BaseViewHolder>{

    public CitySelectorAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_select_cityName,item);
    }
}
