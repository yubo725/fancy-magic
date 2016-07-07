package com.magic.fancymagic.adapter;

import android.content.Context;

import java.util.List;

/**
 * Created by yubo on 2016/7/7.
 */
public class CityGridAdapter extends CommonAdapter<String> {

    public CityGridAdapter(Context context, List<String> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, String item, int position) {

    }
}
