package com.magic.fancymagic.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.magic.fancymagic.R;
import com.magic.fancymagic.adapter.ViewPagerAdapter;
import com.magic.fancymagic.view.CityPagerView;
import com.magic.fancymagic.view.MonthPagerView;
import com.magic.fancymagic.view.PagerView;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @ViewInject(id = R.id.view_pager_container)
    LinearLayout viewPagerContainer;

    @ViewInject(id = R.id.view_pager)
    ViewPager viewPager;

    private ViewPagerAdapter adapter;
    private CityPagerView cityPagerView;
    private MonthPagerView monthPagerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager.setPageMargin(20);
        viewPager.setOffscreenPageLimit(3);

        List<View> viewList = new ArrayList<>();
        cityPagerView = new CityPagerView(this);
        monthPagerView = new MonthPagerView(this);
        viewList.add(cityPagerView);
        viewList.add(monthPagerView);
        for(int i = 0; i < 3; i++) {
            viewList.add(new PagerView(this));
        }
        adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);

        viewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
    }
}
