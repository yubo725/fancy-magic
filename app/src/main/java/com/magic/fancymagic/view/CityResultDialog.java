package com.magic.fancymagic.view;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.magic.fancymagic.R;
import com.magic.fancymagic.bean.CityBean;

import net.tsz.afinal.FinalActivity;

import java.util.List;

/**
 * Created by yubo on 2016/7/8.
 */
public class CityResultDialog extends Dialog {

    private CityBean selectedCity;
    private List<CityBean> converseCityList;
    private View rootView;

    public CityResultDialog(Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        rootView = View.inflate(getContext(), R.layout.city_result_dialog, null);
        FinalActivity.initInjectedView(this, rootView);
    }

    public void setData(List<CityBean> converseCityList, CityBean selectedCity) {
        this.selectedCity = selectedCity;
        this.converseCityList = converseCityList;
    }

    public void showDialog() {
        setContentView(rootView);
        show();
    }

}
