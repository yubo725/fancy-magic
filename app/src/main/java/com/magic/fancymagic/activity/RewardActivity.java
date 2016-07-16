package com.magic.fancymagic.activity;

import android.os.Bundle;

import com.magic.fancymagic.R;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.annotation.view.ViewInject;

/**
 * 打赏
 */
public class RewardActivity extends BaseActivity {

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        initView();
    }

    private void initView() {
        titleView.setTitle("打赏开发者");
    }
}
