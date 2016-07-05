package com.magic.fancymagic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.magic.fancymagic.R;

/**
 * Created by yubo on 2016/7/5.
 */
public class SpiritPagerView extends LinearLayout {

    public SpiritPagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public SpiritPagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SpiritPagerView(Context context) {
        super(context);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.spirit_pager_view_layout, this);
    }
}
