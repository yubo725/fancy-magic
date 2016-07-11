package com.magic.fancymagic.adapter;

import android.content.Context;
import android.widget.Button;

import com.magic.fancymagic.R;
import com.magic.fancymagic.bean.MonthBean;

import java.util.List;

/**
 * Created by yubo on 2016/7/11.
 */
public class MonthResultGridAdapter extends CommonAdapter<MonthBean> {

    public MonthResultGridAdapter(Context context, List<MonthBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, MonthBean item, int position) {
        Button btn = holder.getView(R.id.month_result_grid_item_btn);
        btn.setText(item.getMonthName());
        if(item.isSelected()) {
            btn.setBackgroundResource(R.drawable.ok_btn_selector);
        }else {
            btn.setBackgroundResource(R.drawable.cancel_btn_selector);
        }
    }

}
