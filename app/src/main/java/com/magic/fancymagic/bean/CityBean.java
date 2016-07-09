package com.magic.fancymagic.bean;

import java.io.Serializable;

/**
 * Created by yubo on 2016/7/8.
 */
public class CityBean implements Serializable {

    private int id;
    private String cityName;
    private char firstLetter;
    private boolean selected = false;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public char getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(char firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof CityBean) {
            CityBean bean = (CityBean) o;
            return this.cityName.equals(bean.getCityName());
        }
        return super.equals(o);
    }
}
