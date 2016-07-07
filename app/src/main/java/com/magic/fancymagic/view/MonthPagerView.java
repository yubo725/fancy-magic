package com.magic.fancymagic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/5.
 */
public class MonthPagerView extends LinearLayout {

    @ViewInject(id = R.id.start_btn)
    Button startBtn;

    public MonthPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MonthPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MonthPagerView(Context context) {
        super(context);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.month_pager_view_layout, this);
        FinalActivity.initInjectedView(this, view);
        startBtn.setTypeface(BaseApplication.getInstance().getTypeface());
    }
}
