package com.magic.fancymagic.view;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/7.
 */
public class TitleView extends RelativeLayout {

    @ViewInject(id = R.id.title_view_back_btn)
    ImageButton backBtn;

    @ViewInject(id = R.id.title_view_title)
    TextView titleTv;

    public TitleView(Context context) {
        super(context);
        init();
    }

    public TitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.title_view_layout, this);
        FinalActivity.initInjectedView(this, view);
        titleTv.setTypeface(BaseApplication.getInstance().getTypeface());
        backBtn.setOnClickListener(backBtnDefaultClickListener);
    }

    public void setTitle(String title) {
        if(!TextUtils.isEmpty(title)) {
            titleTv.setText(title);
        }
    }

    private OnClickListener backBtnDefaultClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ((Activity)getContext()).finish();
        }
    };

}
