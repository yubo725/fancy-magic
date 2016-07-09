package com.magic.fancymagic.adapter;

import android.content.Context;
import android.widget.Button;

import com.magic.fancymagic.R;
import com.magic.fancymagic.bean.CityBean;

import java.util.List;

/**
 * Created by yubo on 2016/7/9.
 */
public class CityResultGridAdapter extends CommonAdapter<CityBean> {

    public CityResultGridAdapter(Context context, List<CityBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, CityBean item, int position) {
        Button btn = holder.getView(R.id.city_result_grid_item_btn);
        btn.setText(item.getCityName());
        if(item.isSelected()) {
            btn.setBackgroundResource(R.drawable.ok_btn_selector);
        }else {
            btn.setBackgroundResource(R.drawable.cancel_btn_selector);
        }
    }
}
