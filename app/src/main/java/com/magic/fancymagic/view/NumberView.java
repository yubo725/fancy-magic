package com.magic.fancymagic.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

/**
 * Created by yubo on 2016/7/12.
 */
public class NumberView extends LinearLayout {

    @ViewInject(id = R.id.tv1)
    TextView tv1;

    @ViewInject(id = R.id.tv2)
    TextView tv2;

    @ViewInject(id = R.id.tv3)
    TextView tv3;

    @ViewInject(id = R.id.tv4)
    TextView tv4;

    private SparseArray<String> numberMap;

    public NumberView(Context context) {
        super(context);
        init();
    }

    public NumberView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View view = inflate(getContext(), R.layout.number_view_layout, this);
        FinalActivity.initInjectedView(this, view);
        numberMap = new SparseArray<>();
        numberMap.put(0, "零");
        numberMap.put(1, "一");
        numberMap.put(2, "二");
        numberMap.put(3, "三");
        numberMap.put(4, "四");
        numberMap.put(5, "五");
        numberMap.put(6, "六");
        numberMap.put(7, "七");
        numberMap.put(8, "八");
        numberMap.put(9, "九");
    }

    public void setPhoneNumber(String phoneStr) {
        if(phoneStr.length() == 11) {
            tv1.setText(convertNum(phoneStr.substring(3, 5)));
            tv2.setText(convertNum(phoneStr.substring(5, 7)));
            tv3.setText(convertNum(phoneStr.substring(7, 9)));
            tv4.setText(convertNum(phoneStr.substring(9, 11)));
            Typeface typeFace = BaseApplication.getInstance().getTypeface();
            tv1.setTypeface(typeFace);
            tv2.setTypeface(typeFace);
            tv3.setTypeface(typeFace);
            tv4.setTypeface(typeFace);
        }else {
            Toast.makeText(NumberView.this.getContext(), "手机号不合法", Toast.LENGTH_SHORT).show();
        }
    }

    private String convertNum(String numStr) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < numStr.length(); i++) {
            sb.append(numberMap.get(numStr.charAt(i) - 48));
        }
        return sb.toString();
    }

}
