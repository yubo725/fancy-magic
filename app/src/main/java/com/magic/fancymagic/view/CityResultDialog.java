package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.adapter.CityResultGridAdapter;
import com.magic.fancymagic.bean.CityBean;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yubo on 2016/7/8.
 */
public class CityResultDialog extends Dialog {

    @ViewInject(id = R.id.city_result_grid_view)
    GridView resultGridView;

    @ViewInject(id = R.id.hint_text)
    TextView hintTv;

    private CityBean selectedCity;
    private List<CityBean> converseCityList;
    private CityResultGridAdapter cityResultGridAdapter;
    private View rootView;
    private List<CityBean> cities;

    public CityResultDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        rootView = View.inflate(getContext(), R.layout.city_result_dialog, null);
        FinalActivity.initInjectedView(this, rootView);
        hintTv.setTypeface(BaseApplication.getInstance().getTypeface());
    }

    public void setData(List<CityBean> converseCityList, CityBean selectedCity) {
        this.selectedCity = selectedCity;
        this.converseCityList = converseCityList;
        this.selectedCity.setSelected(true);
        this.converseCityList.add(this.selectedCity);
        if(cityResultGridAdapter == null) {
            cityResultGridAdapter = new CityResultGridAdapter(getContext(), converseCityList, R.layout.city_result_grid_item);
            resultGridView.setAdapter(cityResultGridAdapter);
            cities = cityResultGridAdapter.getData();
        }else {
            cityResultGridAdapter.setData(this.converseCityList);
            cityResultGridAdapter.notifyDataSetChanged();
        }
    }

    private List<CityBean> list = new ArrayList<>();
    private Random random = new Random();

    //重新排序9个城市
    public void reorderCities() {
        CityBean cityBean;
        list.clear();
        while(list.size() != cities.size()) {
            cityBean = cities.get(random.nextInt(cities.size()));
            if(!list.contains(cityBean)) {
                list.add(cityBean);
            }
        }
        cityResultGridAdapter.setData(list);
        cityResultGridAdapter.notifyDataSetChanged();
    }

    public void showDialog() {
        setContentView(rootView);
        show();
    }

}
