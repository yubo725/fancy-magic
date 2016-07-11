package com.magic.fancymagic.bean;

import android.util.MonthDisplayHelper;

import java.io.Serializable;

/**
 * Created by yubo on 2016/7/11.
 */
public class MonthBean implements Serializable {

    private String monthName;
    private int index;
    private boolean selected = false;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof MonthBean) {
            MonthBean monthBean = (MonthBean) o;
            return monthBean.getIndex() == index;
        }
        return super.equals(o);
    }
}
