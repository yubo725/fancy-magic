package com.magic.fancymagic.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.WindowManager;

import com.magic.fancymagic.R;
import com.magic.fancymagic.fragment.SecretCityFragment;
import com.magic.fancymagic.fragment.SecretMonthFragment;
import com.magic.fancymagic.fragment.SecretSpiritFragment;
import com.magic.fancymagic.view.TabView;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 魔术揭秘
 */
public class SecretActivity extends FragmentActivity {

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @ViewInject(id = R.id.tab_view)
    TabView tabView;

    private FragmentManager manager;
    private FragmentTransaction transaction;
    private SecretCityFragment secretCityFragment;
    private SecretMonthFragment secretMonthFragment;
    private SecretSpiritFragment secretSpiritFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret);
        //判断当前SDK版本号，如果是4.4以上，就是支持沉浸式状态栏的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        FinalActivity.initInjectedView(this);
        initView();
    }

    private void initView() {
        titleView.setTitle("魔术揭秘");
        tabView.setOnTabSelectedListener(onTabSelectedListener);

        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();

        secretCityFragment = new SecretCityFragment();
        secretMonthFragment = new SecretMonthFragment();
        secretSpiritFragment = new SecretSpiritFragment();
        transaction.replace(R.id.container, secretCityFragment).commit();
    }

    private TabView.OnTabSelectedListener onTabSelectedListener = new TabView.OnTabSelectedListener() {
        @Override
        public void onTabSelected(int index) {
            transaction = manager.beginTransaction();
            switch(index) {
                case 0:
                    transaction.replace(R.id.container, secretCityFragment).commit();
                    break;
                case 1:
                    transaction.replace(R.id.container, secretMonthFragment).commit();
                    break;
                case 2:
                    transaction.replace(R.id.container, secretSpiritFragment).commit();
                    break;
            }
        }
    };

}
