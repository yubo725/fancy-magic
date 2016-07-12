package com.magic.fancymagic.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.magic.fancymagic.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/11.
 */
public class DotView extends LinearLayout {

    @ViewInject(id = R.id.dot_container)
    LinearLayout dotContainer;

    private ViewPager viewPager;

    public DotView(Context context) {
        super(context);
        init();
    }

    public DotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.dot_view_layout, this);
        FinalActivity.initInjectedView(this, view);
    }

    public void setViewPager(ViewPager viewPager) {
        dotContainer.removeAllViews();
        if(viewPager != null) {
            this.viewPager = viewPager;
        }
        int count = viewPager.getAdapter().getCount();
        for(int i = 0; i < count; i++) {
            addStar();
        }
        int currentIndex = viewPager.getCurrentItem();
        selectDot(currentIndex);
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void selectDot(int index) {
        for(int i = 0; i < dotContainer.getChildCount(); i++) {
            ImageView dotImage = (ImageView) dotContainer.getChildAt(i);
            if(i == index) {
                dotImage.setImageResource(R.mipmap.ic_star_white);
            }else {
                dotImage.setImageResource(R.mipmap.ic_star_dark);
            }
        }
    }

    private void addStar() {
        ImageView imageView = new ImageView(getContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.width = 25;
        params.height = 25;
        params.leftMargin = 10;
        params.rightMargin = 10;
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.mipmap.ic_star_dark);
        dotContainer.addView(imageView);
    }

}
