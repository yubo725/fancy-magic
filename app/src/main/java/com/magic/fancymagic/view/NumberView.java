package com.magic.fancymagic.view;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.magic.fancymagic.BaseApplication;
import com.magic.fancymagic.R;
import com.magic.fancymagic.utils.SPUtils;

import net.tsz.afinal.FinalActivity;
import net.tsz.afinal.annotation.view.ViewInject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private int num1, num2, num3, num4;

    private static SparseArray<String> numberMap;

    private List<TextView> list;

    static {
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
        list = new ArrayList<>();
        list.add(tv1);
        list.add(tv2);
        list.add(tv3);
        list.add(tv4);
    }

    public void setPhoneNumber(String phoneStr) {
        if(phoneStr.length() == 11) {
            String num1Str = phoneStr.substring(3, 5);
            num1 = Integer.parseInt(num1Str);
            tv1.setText(convertNum(num1Str));
            tv1.setTag(num1);

            String num2Str = phoneStr.substring(5, 7);
            num2 = Integer.parseInt(num2Str);
            tv2.setText(convertNum(num2Str));
            tv2.setTag(num2);

            String num3Str = phoneStr.substring(7, 9);
            num3 = Integer.parseInt(num3Str);
            tv3.setText(convertNum(num3Str));
            tv3.setTag(num3);

            String num4Str = phoneStr.substring(9, 11);
            num4 = Integer.parseInt(num4Str);
            tv4.setText(convertNum(num4Str));
            tv4.setTag(num4);

            Typeface typeFace = BaseApplication.getInstance().getTypeface();
            tv1.setTypeface(typeFace);
            tv2.setTypeface(typeFace);
            tv3.setTypeface(typeFace);
            tv4.setTypeface(typeFace);
        }else {
            Toast.makeText(NumberView.this.getContext(), "手机号不合法", Toast.LENGTH_SHORT).show();
        }
    }

    public void setResultNumberView(boolean b) {
        if(b) {
            tv1.setText("");
            tv2.setText("");
            tv3.setText("");
            tv4.setText("");
        }
    }

    public void showNumber(int num) {
        for(TextView tv : list) {
            int n = (int) tv.getTag();
            if(n == num) {
                tv.setText(convertNum(String.valueOf(num)));
            }
        }
    }

    public void setHighLight(int index) {
        for(int i = 0; i < list.size(); i++) {
            TextView tv = list.get(i);
            if(i == index) {
                tv.setBackgroundColor(getResources().getColor(R.color.red));
                tv.setTextColor(getResources().getColor(R.color.white));
            }else {
                tv.setBackgroundColor(getResources().getColor(R.color.white));
                tv.setTextColor(getResources().getColor(R.color.mainBackgroundColor));
            }
        }
    }

    public int[] getNumbers() {
        return new int[]{num1, num2, num3, num4};
    }

    private String convertNum(String numStr) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < numStr.length(); i++) {
            sb.append(numberMap.get(numStr.charAt(i) - 48));
        }
        return sb.toString();
    }

}
