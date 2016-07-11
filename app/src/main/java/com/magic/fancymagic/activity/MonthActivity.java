package com.magic.fancymagic.activity;

import android.os.Bundle;
import android.text.Html;
import android.widget.GridView;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.adapter.MonthGridAdapter;
import com.magic.fancymagic.bean.MonthBean;
import com.magic.fancymagic.listener.ShakeListener;
import com.magic.fancymagic.utils.PhoneUtils;
import com.magic.fancymagic.view.MonthResultDialog;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 喜欢的月份
 */
public class MonthActivity extends BaseActivity {

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @ViewInject(id = R.id.month_hint_tv)
    TextView monthTv;

    @ViewInject(id = R.id.month_grid_view)
    GridView monthGridView;

    private MonthGridAdapter monthGridAdapter;
    private List<MonthBean> monthList;

    private MonthResultDialog monthResultDialog;

    private ShakeListener shakeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);
        initView();
    }

    private void initView() {
        titleView.setTitle("喜欢的月份");
        monthTv.setTypeface(BaseApplication.getInstance().getTypeface());
        monthList = new ArrayList<>();
        for(int i = 1; i <= 12; i++) {
            MonthBean bean = new MonthBean();
            bean.setIndex(i);
            bean.setMonthName(String.format("%d月份", i));
            monthList.add(bean);
        }
        monthGridAdapter = new MonthGridAdapter(this, monthList, R.layout.city_grid_item);
        monthGridView.setAdapter(monthGridAdapter);
        monthGridAdapter.setOnMonthSelectedListener(onMonthSelectedListener);

        monthResultDialog = new MonthResultDialog(this, R.style.loading_dialog);
        monthResultDialog.setAllMonths(monthList);

        shakeListener = new ShakeListener(this);
        shakeListener.setOnShakeListener(onShakeListener);
        shakeListener.registerListener();
    }

    private MonthGridAdapter.OnMonthSelectedListener onMonthSelectedListener = new MonthGridAdapter.OnMonthSelectedListener() {
        @Override
        public void onMonthSelected(MonthBean bean) {
            monthResultDialog.setSelectedMonth(bean);
            monthResultDialog.showDialog();
            monthResultDialog.reorderMonth();
        }
    };

    private ShakeListener.OnShakeListener onShakeListener = new ShakeListener.OnShakeListener() {
        @Override
        public void onShake() {
            if(monthResultDialog.isShowing()) {
                PhoneUtils.vibrateOnce(MonthActivity.this, 200);
                monthResultDialog.reorderMonth();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shakeListener.unregisterListener();
    }
}
