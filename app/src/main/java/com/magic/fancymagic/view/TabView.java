package com.magic.fancymagic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magic.fancymagic.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/14.
 */
public class TabView extends LinearLayout {

    @ViewInject(id = R.id.tab1, click = "tabClicked")
    TextView tab1;

    @ViewInject(id = R.id.tab2, click = "tabClicked")
    TextView tab2;

    @ViewInject(id = R.id.tab3, click = "tabClicked")
    TextView tab3;

    private OnTabSelectedListener onTabSelectedListener;

    public TabView(Context context) {
        super(context);
        init();
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.tab_view_layout, this);
        FinalActivity.initInjectedView(this, view);
    }

    public void tabClicked(View view) {
        int index = 0;
        switch(view.getId()) {
            case R.id.tab1:
                index = 0;
                break;
            case R.id.tab2:
                index = 1;
                break;
            case R.id.tab3:
                index = 2;
                break;
        }
        if(onTabSelectedListener != null) {
            onTabSelectedListener.onTabSelected(index);
        }
    }

    public void setOnTabSelectedListener(OnTabSelectedListener listener) {
        if(listener != null) {
            this.onTabSelectedListener = listener;
        }
    }

    public interface OnTabSelectedListener {
        void onTabSelected(int index);
    }
}
