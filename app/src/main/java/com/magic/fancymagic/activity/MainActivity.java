package com.magic.fancymagic.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.magic.fancymagic.R;
import com.magic.fancymagic.adapter.ViewPagerAdapter;
import com.magic.fancymagic.utils.PhoneUtils;
import com.magic.fancymagic.view.CityPagerView;
import com.magic.fancymagic.view.DotView;
import com.magic.fancymagic.view.MonthPagerView;
import com.magic.fancymagic.view.PwdDialog;
import com.magic.fancymagic.view.SettingsDialog;
import com.magic.fancymagic.view.SpiritPagerView;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    @ViewInject(id = R.id.view_pager_container)
    LinearLayout viewPagerContainer;

    @ViewInject(id = R.id.view_pager)
    ViewPager viewPager;

    @ViewInject(id = R.id.dot_view)
    DotView dotView;

    @ViewInject(id = R.id.btn_more, click = "moreBtnClick")
    ImageView moreBtn;

    private ViewPagerAdapter adapter;
    private CityPagerView cityPagerView;
    private MonthPagerView monthPagerView;
    private SpiritPagerView spiritPagerView;

    private SettingsDialog settingsDialog;

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
        spiritPagerView = new SpiritPagerView(this);
        viewList.add(cityPagerView);
        viewList.add(monthPagerView);
        viewList.add(spiritPagerView);
        adapter = new ViewPagerAdapter(viewList);
        viewPager.setAdapter(adapter);
        //去掉滑动到头的效果
        viewPager.setOverScrollMode(View.OVER_SCROLL_NEVER);

        viewPagerContainer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return viewPager.dispatchTouchEvent(event);
            }
        });
        dotView.setViewPager(viewPager);

        settingsDialog = new SettingsDialog(this, R.style.loading_dialog);
    }

    public void moreBtnClick(View view) {
        settingsDialog.show();
    }
}
