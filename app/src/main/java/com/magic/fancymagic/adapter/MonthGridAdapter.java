package com.magic.fancymagic.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.magic.fancymagic.R;
import com.magic.fancymagic.bean.MonthBean;

import java.util.List;

/**
 * Created by yubo on 2016/7/11.
 */
public class MonthGridAdapter extends CommonAdapter<MonthBean> {

    private OnMonthSelectedListener onMonthSelectedListener;

    public MonthGridAdapter(Context context, List<MonthBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, MonthBean item, int position) {
        TextView btn = holder.getView(R.id.city_grid_item_btn);
        btn.setText(item.getMonthName());
        btn.setTag(item);
        btn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(onMonthSelectedListener != null) {
                onMonthSelectedListener.onMonthSelected((MonthBean) v.getTag());
            }
        }
    };

    public interface OnMonthSelectedListener {
        void onMonthSelected(MonthBean bean);
    }

    public void setOnMonthSelectedListener(OnMonthSelectedListener listener) {
        if(listener != null) {
            this.onMonthSelectedListener = listener;
        }
    }
}
