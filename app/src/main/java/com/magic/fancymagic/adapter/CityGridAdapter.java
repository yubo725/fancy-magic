package com.magic.fancymagic.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.magic.fancymagic.R;
import com.magic.fancymagic.bean.CityBean;

import java.util.List;

/**
 * Created by yubo on 2016/7/7.
 */
public class CityGridAdapter extends CommonAdapter<CityBean> {

    private OnCitySelectedListener onCitySelectedListener;

    public CityGridAdapter(Context context, List<CityBean> mDatas, int itemLayoutId) {
        super(context, mDatas, itemLayoutId);
    }

    @Override
    public void convert(ViewHolder holder, CityBean item, int position) {
        TextView btn = holder.getView(R.id.city_grid_item_btn);
        btn.setText(item.getCityName());
        btn.setTag(item);
        btn.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CityBean bean = (CityBean) v.getTag();
            if(onCitySelectedListener != null) {
                onCitySelectedListener.onCitySelected(bean);
            }
        }
    };

    public interface OnCitySelectedListener{
        void onCitySelected(CityBean bean);
    }

    public void setOnCitySelectedListener(OnCitySelectedListener listener) {
        if(listener != null) {
            this.onCitySelectedListener = listener;
        }
    }
}
