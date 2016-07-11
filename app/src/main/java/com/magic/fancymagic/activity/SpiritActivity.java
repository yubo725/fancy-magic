package com.magic.fancymagic.activity;

import android.os.Bundle;

import com.magic.fancymagic.R;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 心灵密码
 */
public class SpiritActivity extends BaseActivity {

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spirit);
        initView();
    }

    private void initView() {
        titleView.setTitle("心灵密码");
    }

}
