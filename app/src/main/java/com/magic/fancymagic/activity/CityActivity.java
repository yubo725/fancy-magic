package com.magic.fancymagic.activity;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.adapter.CityGridAdapter;
import com.magic.fancymagic.view.TitleView;

import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市读心术
 */
public class CityActivity extends BaseActivity {

    @ViewInject(id = R.id.title_view)
    TitleView titleView;

    @ViewInject(id = R.id.city_hint_tv)
    TextView cityHintTv;

    @ViewInject(id = R.id.city_grid_view)
    GridView gridView;

    private CityGridAdapter cityGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        initView();
    }

    private void initView() {
        titleView.setTitle("城市读心术");
        cityHintTv.setTypeface(BaseApplication.getInstance().getTypeface());

        List<String> data = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            data.add("item" + i);
        }
        cityGridAdapter = new CityGridAdapter(this, data, R.layout.city_grid_item);
        gridView.setAdapter(cityGridAdapter);
    }

}
